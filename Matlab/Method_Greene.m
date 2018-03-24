%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：Greene 找到 TOHS 的算法复现
%
%   特点： 非实时，用 threshold 划分
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. Clear Past Data
clear;
clc;

% 2. angularVelocity Data
angularVelocity_fid = fopen('normal_IMU_1kmh_1.txt', 'r');

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

t_start = clock;

% 3. Low Pass Filter
order = 5;
f_cut_off = 5;
f_sample = 1 / (2 * 60 / length(angularVelocityRawValue));
wn = f_cut_off / (f_sample / 2);
[butter_b, butter_a] = butter(order, wn, 'low');
angularVelocityFilteredValue = filter(butter_b, butter_a, angularVelocityRawValue);

% 4. Skewness
for i = 1 : 1 : length(angularVelocityRawValue)
    angularVelocityRawValue(i) = - angularVelocityRawValue(i);
end

for i = 1 : 1 : length(angularVelocityFilteredValue)
    angularVelocityFilteredValue(i) = - angularVelocityFilteredValue(i);
end

% 5. Adaptive Threshold Calculation
timeInterval = 2 * 60 / length(angularVelocityFilteredValue);

MSPositions = [];
MSValues = [];
ICPositions = [];
ICValues = [];
TCPositions = [];
TCValues = [];

% calculate mean value
meanAngularVelocityValue = 0;
for i = 1 : 1 : length(angularVelocityFilteredValue)
    meanAngularVelocityValue = meanAngularVelocityValue + angularVelocityFilteredValue(i);
end
meanAngularVelocityValue = meanAngularVelocityValue / length(angularVelocityFilteredValue);

% calculate mean value greater than mean angular velocity
meanValueGreaterThanMeanAngularVelocityValue = 0;
meanValueGreaterThanMeanAngularVelocityCount = 0;
for i = 1 : 1 : length(angularVelocityFilteredValue)
    if angularVelocityFilteredValue(i) > meanAngularVelocityValue
        meanValueGreaterThanMeanAngularVelocityValue = ...
            meanValueGreaterThanMeanAngularVelocityValue + ...
            angularVelocityFilteredValue(i);
        meanValueGreaterThanMeanAngularVelocityCount = ...
            meanValueGreaterThanMeanAngularVelocityCount + 1;
    end
end
meanValueGreaterThanMeanAngularVelocityValue = ...
    meanValueGreaterThanMeanAngularVelocityValue / ...
    meanValueGreaterThanMeanAngularVelocityCount;

% calculate mean value less than mean angular velocity
meanValueLessThanMeanAngularVelocityValue = 0;
meanValueLessThanMeanAngularVelocityCount = 0;
for i = 1 : 1 : length(angularVelocityFilteredValue)
    if angularVelocityFilteredValue(i) < meanAngularVelocityValue
        meanValueLessThanMeanAngularVelocityValue = ...
            meanValueLessThanMeanAngularVelocityValue + ...
            angularVelocityFilteredValue(i);
        meanValueLessThanMeanAngularVelocityCount = ...
            meanValueLessThanMeanAngularVelocityCount + 1;
    end
end
meanValueLessThanMeanAngularVelocityValue = ...
    meanValueLessThanMeanAngularVelocityValue ...
    / meanValueLessThanMeanAngularVelocityCount;

precedingMaximumValue = 0;
precedingMinimumValue = 0;

TH1_Flag = 0;
TH2_Flag = 0;
TH3_Flag = 0;
TH4_Flag = 0;
TH5_Flag = 0;
TH6_Flag = 0;
T1_Flag = 0;
T2_Flag = 0;

for i = 1 + 1 : 1 : length(angularVelocityFilteredValue) - 1
    
    % calculate preceding maximum
    if angularVelocityFilteredValue(i) > precedingMaximumValue
        precedingMaximumValue = angularVelocityFilteredValue(i);
    end
    
    % calculate preceding minimum
    if angularVelocityFilteredValue(i) < precedingMinimumValue
        precedingMinimumValue = angularVelocityFilteredValue(i);
    end
    
    % th1
    th1 = 0.6 * precedingMaximumValue;
    
    % th2
    th2 = 0.8 * meanValueGreaterThanMeanAngularVelocityValue;
    
    % th3
    th3 = 0.8 * abs(meanValueLessThanMeanAngularVelocityValue);
    
    % th4
    th4 = 0.8 * meanValueLessThanMeanAngularVelocityValue;
    
    % th5
    th5 = meanAngularVelocityValue;
    
    % th6
    th6 = 2 * th3;
    
    % t1 = 0.5s
    t1 = 0.5 / timeInterval;
    
    % t2 = 1.5s
    t2 = 1.5 / timeInterval;
    
    % MS
    if (angularVelocityFilteredValue(i - 1) < angularVelocityFilteredValue(i)) ...
            && (angularVelocityFilteredValue(i) > angularVelocityFilteredValue(i + 1))
        localMaximumPosition = i;
        localMaximumValue = angularVelocityFilteredValue(i);
        
        % th1
        if precedingMinimumValue < (localMaximumValue - th1)
            TH1_Flag = 1;
        else
            TH1_Flag = 0;
        end
        
        % th2
        if localMaximumValue > th2
            TH2_Flag = 1;
        else
            TH1_Flag = 0;
        end
        
        % t1
        if length(MSPositions) == 0
            T1_Flag = 1;
        elseif abs(localMaximumPosition - MSPositions(end)) > t1
            T1_Flag = 1;
        else
            T1_Flag = 0;
        end
        
        if TH1_Flag == 1 && TH2_Flag == 1 && T1_Flag == 1
            MSPositions = [MSPositions localMaximumPosition];
            MSValues    = [MSValues localMaximumValue];
            
            % IC (HS)
            for j = i : 1 : round(i + t2)
                
                if round(i + t2) > length(angularVelocityFilteredValue)
                    break;
                end
                
                if (angularVelocityFilteredValue(j - 1) > angularVelocityFilteredValue(j)) ...
                        && (angularVelocityFilteredValue(j) < angularVelocityFilteredValue(j + 1))
                    localMinimumPosition = j;
                    localMinimumValue = angularVelocityFilteredValue(j);
                    
                    % th3
                    if precedingMaximumValue > (localMinimumValue + th3)
                        TH3_Flag = 1;
                    else
                        TH3_Flag = 0;
                    end
                    
                    % th5
                    if localMinimumValue < th5
                        TH5_Flag = 1;
                    else
                        TH5_Flag = 0;
                    end
                    
                    if TH3_Flag == 1 && TH5_Flag == 1
                        ICPositions = [ICPositions  localMinimumPosition];
                        ICValues    = [ICValues     localMinimumValue];
                        break;
                    end
                end
            end
            
            % TC (TO)
            for j = i : -1 : round(i - t2)
                
                if round(i - t2) < 1
                    break;
                end
                
                if (angularVelocityFilteredValue(j - 1) > angularVelocityFilteredValue(j)) ...
                        && (angularVelocityFilteredValue(j) < angularVelocityFilteredValue(j + 1))
                    localMinimumPosition = j;
                    localMinimumValue = angularVelocityFilteredValue(j);
                    
                    % th4
                    if localMinimumValue < th4
                        TH4_Flag = 1;
                    else
                        TH4_Flag = 0;
                    end
                    
                    % th6
                    if precedingMaximumValue > (localMinimumValue + th6)
                        TH6_Flag = 1;
                    else
                        TH6_Flag = 0;
                    end
                    
                    if TH4_Flag == 1 && TH6_Flag == 1
                        TCPositions = [TCPositions localMinimumPosition];
                        TCValues = [TCValues localMinimumValue];
                        break;
                    end
                end
            end
        end
    end
end

t_end = clock;
etime(t_end, t_start)

%   display data
t = 1 : 1 : length(angularVelocityFilteredValue);
figure();
plot(t, angularVelocityFilteredValue);

hold on
t = 1 : 1 : length(angularVelocityRawValue);
plot(t, angularVelocityRawValue, 'r');
plot(ICPositions, ICValues, '*r');
plot(TCPositions, TCValues, '*g');
plot(MSPositions, MSValues, '*y');
hold off