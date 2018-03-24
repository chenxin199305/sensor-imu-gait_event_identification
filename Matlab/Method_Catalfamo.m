%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：Catalfamo 找到 TOHS 的算法复现
%
%   特点：
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. Clear Past Data
clear;
clc;

% 2. angularVelocity Data
angularVelocity_fid = fopen('normal_IMU_3kmh_1.txt', 'r');

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

timeInterval = 2 * 60 / length(angularVelocityRawValue);

t_start = clock;

% 3. Low Pass Filter
%   second order Butterworth low pass filter applied backwards and
%   forwards, with a cut off frequency of 35Hz
order = 2;
f_cut_off = 3;
f_sample = 1 / (2 * 60 / length(angularVelocityRawValue));
wn = f_cut_off / (f_sample / 2);
[butter_b, butter_a] = butter(order, wn, 'low');
angularVelocityFilteredValue = filter(butter_b, butter_a, angularVelocityRawValue);

% 4. Skewness
for i = 1 : 1 : length(angularVelocityFilteredValue)
    angularVelocityFilteredValue(i) = - angularVelocityFilteredValue(i);
end

% 5. Adaptive Threshold Calculation
AZPosition = 0;
AZValue = 0;
DZPosition = 0;
DZValue = 0;
gnGreaterThan20Count = 0;
DetectionState = 1;

FO_Flag_a = 0;
FO_Flag_b = 0;
FO_Flag_c = 0;
FO_Flag_d = 0;
FO_Flag_e = 0;

gaitCycleStartPosition = 3;
gaitCycleEndPosition = 3;

StepPositions = [];
StepValues = [];
ICPositions = [];
ICValues = [];
TCPositions = [];
TCValues = [];

for i = 1 + 10 : 1 : length(angularVelocityFilteredValue) - 16
    
    angularVelocityFilteredValue_i_minus_1 = angularVelocityFilteredValue(i - 1);
    angularVelocityFilteredValue_i = angularVelocityFilteredValue(i);
    angularVelocityFilteredValue_i_add_1 = angularVelocityFilteredValue(i + 1);
    
    angularVelocityFilteredGradient_i = ...
        (angularVelocityFilteredValue_i_add_1 - angularVelocityFilteredValue_i_minus_1) ...
        / (2 * timeInterval);
    
    switch DetectionState
        case 1
            % 1. detection of zero crossing when the signal is ascending
            if angularVelocityFilteredValue(i - 1) <= 0 ...
                    && angularVelocityFilteredValue(i) >= 0
                AZPosition      = i;
                AZValue         = angularVelocityFilteredValue(i);
                
                StepPositions   = [StepPositions i];
                StepValues      = [StepValues angularVelocityFilteredValue(i)];
                
                DetectionState  = 2;
            else
                DetectionState = 1;
            end
        case 2
            % 2. is g(n) ascending and g(n) > 20?
            if angularVelocityFilteredGradient_i > 0
                if angularVelocityFilteredValue(i) > 20
                    gnGreaterThan20Count = 1;
                    DetectionState = 3;
                else
                end
            else
                DetectionState = 1;
            end
        case 3
            % 3. count the samples for which g(n) > 20
            if angularVelocityFilteredValue(i) > 20
                gnGreaterThan20Count = gnGreaterThan20Count + 1;
            else
                if angularVelocityFilteredValue(i - 1) > 0 ...
                        && angularVelocityFilteredValue(i) < 0
                    DZPosition  = i;
                    DZValue     = angularVelocityFilteredValue(i);
                    
                    DetectionState = 5;
                    continue;
                end
                DetectionState = 4;
            end
        case 4
            % 4. detection of zero crossing when the signal is descending
            if angularVelocityFilteredValue(i - 1) > 0 ...
                    && angularVelocityFilteredValue(i) < 0
                DZPosition  = i;
                DZValue     = angularVelocityFilteredValue(i);
                DetectionState = 5;
            end
        case 5
            % 5. is count >= 5? --> swing phase detected?
            if gnGreaterThan20Count >= 5
                DetectionState = 6;
            else
                DetectionState = 1;
            end
        case 6
            % 6. IC detection. IC is the first sample, that is a local minimum
            % after DZ
            if angularVelocityFilteredValue(i - 1) > angularVelocityFilteredValue(i) ...
                    && angularVelocityFilteredValue(i) < angularVelocityFilteredValue(i + 1)
                ICPositions = [ICPositions i];
                ICValues    = [ICValues angularVelocityFilteredValue(i)];
                DetectionState = 7;
            else
            end
        case 7
            % 7. waiting time, pass stance phase
            if ((i - ICPositions(end)) - (0.5 * (DZPosition - AZPosition))) > 0
                DetectionState = 8;
            else
            end
        case 8
            % 8. is g(n) < - 20?
            if angularVelocityFilteredValue(i) < - 20
                DetectionState = 9;
            else
                DetectionState = 1;
            end
        case 9
            % 9. FO detection
            if angularVelocityFilteredValue(i) < - 20
                FO_Flag_a = 1;
            else
                FO_Flag_a = 0;
            end
            
            for j = i - 10 : 1 : i + 15
                if angularVelocityFilteredValue(j) >= angularVelocityFilteredValue(i)
                    FO_Flag_b = 1;
                else
                    FO_Flag_b = 0;
                    break;
                end
            end
            
            decreasingPatternCount = 0;
            for j = i - 10 : 1 : i
                if angularVelocityFilteredValue(j - 1) >= angularVelocityFilteredValue(j)
                    decreasingPatternCount = decreasingPatternCount + 1;
                else
                end
            end
            if decreasingPatternCount >= 5
                FO_Flag_c = 1;
            else
                FO_Flag_c = 0;
            end
            
            increasingPatternCount = 0;
            for j = i : 1 : i + 15
                if angularVelocityFilteredValue(j) < angularVelocityFilteredValue(j + 1)
                    increasingPatternCount = increasingPatternCount + 1;
                else
                end
            end
            if increasingPatternCount >= 10
                FO_Flag_d = 1;
            else
                FO_Flag_d = 0;
            end
            
            if angularVelocityFilteredValue(i + 15) - angularVelocityFilteredValue(i) > 20
                FO_Flag_e = 1;
            else
                FO_Flag_e = 0;
            end
            
            if (FO_Flag_a == 1 && FO_Flag_b == 1 && FO_Flag_c == 1 && FO_Flag_d == 1) ...
                    || (FO_Flag_a == 1 && FO_Flag_e == 1)
                TCPositions = [TCPositions i];
                TCValues    = [TCValues angularVelocityFilteredValue(i)];
                DetectionState = 1;
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
% plot(t, trainingRawValue, 'r');
plot(ICPositions, ICValues, '*r');
plot(TCPositions, TCValues, '*g');
plot(StepPositions, StepValues, '*y');
hold off