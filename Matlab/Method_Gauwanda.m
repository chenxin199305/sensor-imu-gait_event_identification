%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：Gauwanda 找到 TOHS 的算法复现
%
%   特点： 非实时，用 threshold 划分
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. Clear Past Data
clear;
clc;

% 2. angularVelocity Data
angularVelocity_fid = fopen('normal_IMU_1kmh_3.txt', 'r');

if angularVelocity_fid >= 0
else
    return;
end

angularVelocityRawValue = [];

while ~feof(angularVelocity_fid)
    
    line = fgetl(angularVelocity_fid);
    
    if strcmp(line, 'gyoRawY')
        line = fgetl(angularVelocity_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            angularVelocityRawValue = [angularVelocityRawValue lineNum];
            
            line = fgetl(angularVelocity_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
end

timeInterval = 2 * 60 * 1000 / length(angularVelocityRawValue);

tic

% 3. Low Pass Filter
% order = 2;
% f_cut_off = 5;
% f_sample = 1 / (2 * 60 / length(angularVelocityRawValue));
% wn = f_cut_off / (f_sample / 2);
% [butter_b, butter_a] = butter(order, wn, 'low');
% angularVelocityFilteredValue = filter(butter_b, butter_a, angularVelocityRawValue);
angularVelocityFilteredValue = angularVelocityRawValue;

% 4. Skewness
for i = 1 : 1 : length(angularVelocityRawValue)
    angularVelocityRawValue(i) = - angularVelocityRawValue(i);
end

for i = 1 : 1 : length(angularVelocityFilteredValue)
    angularVelocityFilteredValue(i) = - angularVelocityFilteredValue(i);
end

% 5. Adaptive Threshold Calculation
MSFindFlag = 0;
HSFindFlag = 0;
TOFindFlag = 0;
ZeroCrossingFlag = 0;

GlobalPPPositions = [];
GlobalPPValues = [];
tempPPPositions = [];
tempPPValues = [];

MSThreshold = 28.65;
MSPositions = [];
MSValues = [];
HSPositions = [];
HSValues = [];
TOPositions = [];
TOValues = [];

tempPPPositionsSatisfyTimeRequestPositions = [];
tempPPPositionsSatisfyTimeRequestValues = [];

for i = 1 + 2 : 1 : length(angularVelocityFilteredValue)
    
    angularVelocityFilteredValue_i_2 = angularVelocityFilteredValue(i - 2);
    angularVelocityFilteredValue_i_1 = angularVelocityFilteredValue(i - 1);
    angularVelocityFilteredValue_i_0 = angularVelocityFilteredValue(i - 0);
    
    angularVelocityFilteredGradient_i_1 = ...
        (angularVelocityFilteredValue_i_1 - angularVelocityFilteredValue_i_2);
    angularVelocityFilteredGradient_i_0 = ...
        (angularVelocityFilteredValue_i_0 - angularVelocityFilteredValue_i_1);
    
    angularVelocityFilteredGradient_a_i_0 = ...
        angularVelocityFilteredGradient_i_0 * angularVelocityFilteredGradient_i_1;
    
    % Pp
    if angularVelocityFilteredGradient_a_i_0 < 0
        
        GlobalPPPositions = [GlobalPPPositions  i];
        GlobalPPValues    = [GlobalPPValues     angularVelocityFilteredValue(i)];
        
        tempPPPositions = [tempPPPositions    i];
        tempPPValues    = [tempPPValues       angularVelocityFilteredValue(i)];
    end
    
    % MS
    % these parameters are reasonable considering that backward swing
    % of the shank would be greater than preset threshold and one gait
    % cycle generally lasts for a duration greater than 1s.
    if length(tempPPValues) > 0 ...
            && tempPPValues(end) >= MSThreshold
        
        if length(MSPositions) == 0
            MSPositions = [MSPositions    tempPPPositions(end)];
            MSValues    = [MSValues       tempPPValues(end)];
            MSFindFlag  = 1;
            continue;
        elseif length(MSPositions) >= 1 ...
                && (tempPPPositions(end) - MSPositions(end)) > (350 / timeInterval)
            MSPositions = [MSPositions    tempPPPositions(end)];
            MSValues    = [MSValues       tempPPValues(end)];
            MSFindFlag  = 1;
            continue;
        else
        end
    end
    
    % HS
    if MSFindFlag == 1
        
        if tempPPValues(end) < 0 && tempPPValues(end - 1) > MSThreshold
            if length(HSPositions) == 0 ...
                    || (HSPositions(end) ~= tempPPPositions(end))
                HSPositions = [HSPositions  tempPPPositions(end)];
                HSValues    = [HSValues     tempPPValues(end)];
                HSFindFlag  = 1;
            end
            continue;
        else
        end
    end
    
    % TO
    if MSFindFlag == 1 && HSFindFlag == 1
        
        % △t > 100ms
        if (tempPPPositions(end) - HSPositions(end)) > (100 / timeInterval)
            tempPPPositionsSatisfyTimeRequestPositions = [...
                tempPPPositionsSatisfyTimeRequestPositions tempPPPositions(end)];
            tempPPPositionsSatisfyTimeRequestValues = [...
                tempPPPositionsSatisfyTimeRequestValues tempPPValues(end)];
        else
            continue;
        end
        
        % zero crossing
        if angularVelocityFilteredValue_i_0 > 0 ...
                && angularVelocityFilteredValue_i_1 < 0
            
            % local minimum
            localMinimumPosition = tempPPPositionsSatisfyTimeRequestPositions(1);
            localMinimumValue = tempPPPositionsSatisfyTimeRequestValues(1);
            for j = 1 : 1 : length(tempPPPositionsSatisfyTimeRequestPositions)
                if localMinimumValue > tempPPPositionsSatisfyTimeRequestValues(j)
                    localMinimumPosition = tempPPPositionsSatisfyTimeRequestPositions(j);
                    localMinimumValue = tempPPPositionsSatisfyTimeRequestValues(j);
                end
            end
            
            TOPositions = [TOPositions  localMinimumPosition];
            TOValues    = [TOValues     localMinimumValue];
            TOFindFlag  = 1;
            
            % clear all flags and temp values
            if TOFindFlag == 1
                MSFindFlag = 0;
                HSFindFlag = 0;
                TOFindFlag = 0;
                tempPPPositions = [];
                tempPPValues = [];
                tempPPPositionsSatisfyTimeRequestPositions = [];
                tempPPPositionsSatisfyTimeRequestValues = [];
            end
        else
            continue;
        end
    end
end

toc

%   display data
t = 1 : 1 : length(angularVelocityFilteredValue);
figure();
plot(t, angularVelocityFilteredValue);

hold on
% t = 1 : 1 : length(angularVelocityRawValue);
% plot(t, angularVelocityRawValue, 'r');
% plot(GlobalPPPositions, GlobalPPValues, '*b')
plot(HSPositions, HSValues, '*r');
plot(TOPositions, TOValues, '*g');
plot(MSPositions, MSValues, '*y');
hold off