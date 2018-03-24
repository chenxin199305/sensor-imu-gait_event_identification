%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：Lee 找到 TOHS 的算法复现
%
%   特点： 非实时，用 threshold 划分
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. Clear Past Data
clear;
clc;

% 2. Gyroscope Data
gyroscope_fid = fopen('normal_IMU_3kmh_1.txt', 'r');

if gyroscope_fid >= 0
else
    return;
end

angularVelocityRawValue = [];
angularAccelerationRawValue = [];

while ~feof(gyroscope_fid)
    
    line = fgetl(gyroscope_fid);
    
    if strcmp(line, 'gyoRawY')
        line = fgetl(gyroscope_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            angularVelocityRawValue = [angularVelocityRawValue lineNum];
            
            line = fgetl(gyroscope_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
end

timeInterval = 2 * 60 / length(angularVelocityRawValue);

for i = 1 : 1 : length(angularVelocityRawValue) - 1
    angularAccelerationRawValue = [angularAccelerationRawValue ...
        (angularVelocityRawValue(i + 1) - angularVelocityRawValue(i) / timeInterval)];
end

t_start = clock;

% 3. Low Pass Filter and Differentiation
angularVelocityFilteredWith3HzValue = [];
angularAccelerationFilteredWith3HzValue = [];
angularVelocityFilteredWith10HzValue = [];
angularAccelerationFilteredWith10HzValue = [];

order = 2;
f_cut_off = 3;
f_sample = 1 / (2 * 60 / length(angularVelocityRawValue));
wn = f_cut_off / (f_sample / 2);
[butter_b, butter_a] = butter(order, wn, 'low');
angularVelocityFilteredWith3HzValue = filter(butter_b, butter_a, angularVelocityRawValue);

order = 2;
f_cut_off = 10;
f_sample = 1 / (2 * 60 / length(angularVelocityRawValue));
wn = f_cut_off / (f_sample / 2);
[butter_b, butter_a] = butter(order, wn, 'low');
angularVelocityFilteredWith10HzValue = filter(butter_b, butter_a, angularVelocityRawValue);

for i = 1 : 1 : length(angularVelocityFilteredWith3HzValue) - 1
    angularAccelerationFilteredWith3HzValue = [angularAccelerationFilteredWith3HzValue ...
        (angularVelocityFilteredWith3HzValue(i + 1) - angularVelocityFilteredWith3HzValue(i) / timeInterval)];
end

for i = 1 : 1 : length(angularVelocityFilteredWith10HzValue) - 1
    angularAccelerationFilteredWith10HzValue = [angularAccelerationFilteredWith10HzValue ...
        (angularVelocityFilteredWith10HzValue(i + 1) - angularVelocityFilteredWith10HzValue(i) / timeInterval)];
end

% 4. Skewness
for i = 1 : 1 : length(angularVelocityRawValue)
    angularVelocityRawValue(i) = - angularVelocityRawValue(i);
end

for i = 1 : 1 : length(angularVelocityFilteredWith3HzValue)
    angularVelocityFilteredWith3HzValue(i) = - angularVelocityFilteredWith3HzValue(i);
end

for i = 1 : 1 : length(angularVelocityFilteredWith10HzValue)
    angularVelocityFilteredWith10HzValue(i) = - angularVelocityFilteredWith10HzValue(i);
end

% 5. Detection Method
Df1 = round(0.2 / timeInterval);
Df2 = round(0.1 / timeInterval);
MS_Event_Flag = 0;

angularVelocityRawValue_ECPositions = [];
angularVelocityRawValue_ECValues = [];

angularVelocityRawValue_MSPositions = [];
angularVelocityRawValue_MSValues = [];

angularVelocityRawValue_ICPositions = [];
angularVelocityRawValue_ICValues = [];

angularVelocityFilteredWith3HzValue_ECPositions_temp = [];
angularVelocityFilteredWith3HzValue_ECValues_temp = [];

angularVelocityFilteredWith3HzValue_ECPositions = [];
angularVelocityFilteredWith3HzValue_ECValues = [];

angularVelocityFilteredWith3HzValue_MSPositions = [];
angularVelocityFilteredWith3HzValue_MSValues = [];

angularVelocityFilteredWith3HzValue_ICPositions = [];
angularVelocityFilteredWith3HzValue_ICValues = [];

angularVelocityFilteredWith10HzValue_ECPositions = [];
angularVelocityFilteredWith10HzValue_ECValues = [];

angularVelocityFilteredWith10HzValue_MSPositions = [];
angularVelocityFilteredWith10HzValue_MSValues = [];

angularVelocityFilteredWith10HzValue_ICPositions = [];
angularVelocityFilteredWith10HzValue_ICValues = [];

angularAccelerationFilteredWith3HzValue_PeakPositions = [];
angularAccelerationFilteredWith3HzValue_PeakValues = [];

for i = 1 + 1 : 1 : length(angularVelocityRawValue) - 2
    
    if MS_Event_Flag == 0
        % Detect EC(f1) candidates
        if angularVelocityFilteredWith3HzValue(i - 1) > angularVelocityFilteredWith3HzValue(i) ...
                && angularVelocityFilteredWith3HzValue(i) < angularVelocityFilteredWith3HzValue(i + 1)
            angularVelocityFilteredWith3HzValue_ECPositions_temp = [...
                angularVelocityFilteredWith3HzValue_ECPositions_temp i];
            angularVelocityFilteredWith3HzValue_ECValues_temp = [...
                angularVelocityFilteredWith3HzValue_ECValues_temp angularVelocityFilteredWith3HzValue(i)];
            
            angularVelocityFilteredWith3HzValue_ECPositions = [...
                angularVelocityFilteredWith3HzValue_ECPositions i];
            angularVelocityFilteredWith3HzValue_ECValues = [...
                angularVelocityFilteredWith3HzValue_ECValues angularVelocityFilteredWith3HzValue(i)];
        else
        end
        
        % Detect peak angular acceleration
        if  angularAccelerationFilteredWith3HzValue(i - 1) < angularAccelerationFilteredWith3HzValue(i) ...
                && angularAccelerationFilteredWith3HzValue(i) > angularAccelerationFilteredWith3HzValue(i + 1)
            angularAccelerationFilteredWith3HzValue_PeakPositions = [...
                angularAccelerationFilteredWith3HzValue_PeakPositions i];
            angularAccelerationFilteredWith3HzValue_PeakValues = [...
                angularAccelerationFilteredWith3HzValue_PeakValues angularAccelerationFilteredWith3HzValue(i)];
        end
        
        % Detect MS points
        if length(angularVelocityFilteredWith3HzValue_ECPositions_temp) ~= 0 ...
                && length(angularAccelerationFilteredWith3HzValue_PeakPositions) ~= 0
        else
            continue;
        end
        
        % detect MS(f1) points
        if angularVelocityFilteredWith3HzValue(i - 1) < angularVelocityFilteredWith3HzValue(i) ...
                && angularVelocityFilteredWith3HzValue(i) > angularVelocityFilteredWith3HzValue(i + 1)
            angularVelocityFilteredWith3HzValue_MSPositions = [...
                angularVelocityFilteredWith3HzValue_MSPositions i];
            angularVelocityFilteredWith3HzValue_MSValues = [...
                angularVelocityFilteredWith3HzValue_MSValues angularVelocityFilteredWith3HzValue(i)];
            
            % detect MS(f2) points
            for j = i - Df1 : 1 : i
                if (j - 1) < 1 || j < 1 || (j + 1) < 1
                else
                    if angularVelocityFilteredWith10HzValue(j - 1) < angularAccelerationFilteredWith10HzValue(j) ...
                            && angularAccelerationFilteredWith10HzValue(i) > angularAccelerationFilteredWith10HzValue(j + 1)
                        angularVelocityFilteredWith10HzValue_MSPositions = [...
                            angularVelocityFilteredWith10HzValue_MSPositions j];
                        angularVelocityFilteredWith10HzValue_ECValues = [...
                            angularVelocityFilteredWith10HzValue_ECValues angularAccelerationFilteredWith10HzValue(i)];
                        
                        % detect MS(unf) points
                        for k = j - Df2 : 1 : j
                            if (k - 1) < 1 || k < 1 || (k + 1) < 1
                            else
                                if angularVelocityRawValue(k - 1) < angularVelocityRawValue(k) ...
                                        && angularVelocityRawValue(k) > angularVelocityRawValue(k + 1)
                                    angularVelocityRawValue_MSPositions = [...
                                        angularVelocityRawValue_MSPositions k];
                                    angularVelocityRawValue_MSValues = [...
                                        angularVelocityRawValue_MSValues angularVelocityRawValue(k)];
                                end
                            end
                        end
                    end
                end
            end
            
            % Detect EC points
            % detect EC(f1) points
            EC_f1_position = angularVelocityFilteredWith3HzValue_MSPositions(end);
            
            % detect EC(f2) points
            for j = EC_f1_position - Df1 : 1 : EC_f1_position
                if (j - 1) < 1 || j < 1 || (j + 1) < 1
                else
                    if angularVelocityFilteredWith10HzValue(j - 1) > angularVelocityFilteredWith10HzValue(j) ...
                            && angularVelocityFilteredWith10HzValue(j) < angularVelocityFilteredWith10HzValue(j + 1)
                        angularVelocityFilteredWith10HzValue_ECPositions = [...
                            angularVelocityFilteredWith10HzValue_ECPositions j];
                        angularVelocityFilteredWith10HzValue_ECValues = [...
                            angularVelocityFilteredWith10HzValue_ECValues angularVelocityFilteredWith10HzValue(j)];
                        
                        % detect EC(unf) points
                        for k = j - Df2 : 1 : j
                            if (k - 1) < 1 || k < 1 || (k + 1) < 1
                            else
                                if angularVelocityRawValue(k - 1) > angularVelocityRawValue(k) ...
                                        && angularVelocityRawValue(k) < angularVelocityRawValue(k + 1)
                                    angularVelocityRawValue_ECPositions = [...
                                        angularVelocityRawValue_ECPositions k];
                                    angularVelocityRawValue_ECValues = [...
                                        angularVelocityRawValue_ECValues angularVelocityRawValue(k)];
                                end
                            end
                            
                        end
                    end
                end
                
            end
            
            % clear temp buffer
            angularVelocityFilteredWith3HzValue_ECPositions_temp = [];
            angularAccelerationFilteredWith3HzValue_PeakPositions = [];
            
            % set flag
            MS_Event_Flag = 1;
        else
        end
    else
        % Detect IC points
        % detect IC(f2) points
        if angularVelocityFilteredWith3HzValue(i - 1) > angularVelocityFilteredWith3HzValue(i) ...
                && angularVelocityFilteredWith3HzValue(i) < angularVelocityFilteredWith3HzValue(i + 1)
            angularVelocityFilteredWith3HzValue_ICPositions = [...
                angularVelocityFilteredWith3HzValue_ICPositions i];
            angularVelocityFilteredWith3HzValue_ICValues = [...
                angularVelocityFilteredWith3HzValue_ICValues angularVelocityFilteredWith3HzValue(i)];
            
            % detect IC(unf) points
            for j = i - Df2 : 1 : i
                if angularVelocityRawValue(j - 1) > angularVelocityRawValue(j) ...
                        && angularVelocityRawValue(j) < angularVelocityRawValue(j + 1)
                    angularVelocityRawValue_ICPositions = [...
                        angularVelocityRawValue_ICPositions j];
                    angularVelocityRawValue_ICValues = [...
                        angularVelocityRawValue_ICValues angularVelocityRawValue(j)];
                end
            end
            
            % clear flag
            MS_Event_Flag = 0;
        end
    end
end

t_end = clock;
etime(t_end, t_start)

%   display data
figure();
t = 1 : 1 : length(angularVelocityFilteredWith3HzValue);
plot(t, angularVelocityFilteredWith3HzValue, 'b');

hold on
% t = 1 : 1 : length(angularVelocityRawValue);
% plot(t, angularVelocityRawValue, 'r');
plot(angularVelocityFilteredWith3HzValue_MSPositions, angularVelocityFilteredWith3HzValue_MSValues, '*y');
plot(angularVelocityFilteredWith3HzValue_ICPositions, angularVelocityFilteredWith3HzValue_ICValues, '*r');
plot(angularVelocityFilteredWith3HzValue_ECPositions, angularVelocityFilteredWith3HzValue_ECValues, '*g');
hold off

% figure();
% t = 1 : 1 : length(angularVelocityFilteredWith10HzValue);
% plot(t, angularVelocityFilteredWith10HzValue);
% 
% hold on
% plot(angularVelocityFilteredWith10HzValue_MSPositions, angularVelocityFilteredWith10HzValue_MSValues, '*y');
% plot(angularVelocityFilteredWith10HzValue_ICPositions, angularVelocityFilteredWith10HzValue_ICValues, '*r');
% plot(angularVelocityFilteredWith10HzValue_ECPositions, angularVelocityFilteredWith10HzValue_ECValues, '*g');
% hold off