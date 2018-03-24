%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：读取数据文件并 在线计算找到 TOHS
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. clear all data
clear;
clc;

windowLength = 13;
trainingTimes = 10;
timeInterval = 0.01;
TOHSInitialThreshold = 1;
learningRateAlpha = 1.2;
learningRateBeta = 0.8;

% 2. open file, get file id
gyroscope_fid = fopen('ZHANGYINPING_IMU_3kmh_1.txt', 'r');
pressure_fid = fopen('ZHANGYINPING_PRESSURE_3kmh_1.txt', 'r');

if gyroscope_fid >= 0
else
    return;
end

if pressure_fid >= 0
else
    return;
end

% 3. read file data
gyroscopeRawValue = [];

while ~feof(gyroscope_fid)
    
    line = fgetl(gyroscope_fid);
    
    if strcmp(line, 'gyoRawY')
        line = fgetl(gyroscope_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            gyroscopeRawValue = [gyroscopeRawValue lineNum];
            
            line = fgetl(gyroscope_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
end

heelPressure = [];
toePressure = [];

while ~feof(pressure_fid)
    
    line = fgetl(pressure_fid);
    
    if strcmp(line, 'heelPressure')
        line = fgetl(pressure_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            heelPressure = [heelPressure lineNum];
            
            %             disp(lineNum);
            
            line = fgetl(pressure_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
    if strcmp(line, 'toePressure')
        line = fgetl(pressure_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            toePressure = [toePressure lineNum];
            
            line = fgetl(pressure_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
end

% uniformed to 10 * 1000ms
t_interval_gyroscopeRawValue    = 10 * 1000 / length(gyroscopeRawValue);
t_interval_toePressure          = 10 * 1000 / length(toePressure);
t_interval_heelPressure         = 10 * 1000 / length(heelPressure);
% uniformer to (-1, 1)
max_value_gyroscopeRawValue = 0;
min_value_gyroscopeRawValue = 0;
for t = 1 : 1 : length(gyroscopeRawValue)
    if gyroscopeRawValue(t) > max_value_gyroscopeRawValue
        max_value_gyroscopeRawValue = gyroscopeRawValue(t);
    end
    if gyroscopeRawValue(t) < min_value_gyroscopeRawValue
        min_value_gyroscopeRawValue = gyroscopeRawValue(t);
    end
end

max_value_toePressure = 0;
min_value_toePressure = 0;
for t = 1 : 1 : length(toePressure)
    if toePressure(t) > max_value_toePressure
        max_value_toePressure = toePressure(t);
    end
    if toePressure(t) < min_value_toePressure
        min_value_toePressure = toePressure(t);
    end
end

max_value_heelPressure = 0;
min_value_heelPressure = 0;
for t = 1 : 1 : length(heelPressure)
    if heelPressure(t) > max_value_heelPressure
        max_value_heelPressure = heelPressure(t);
    end
    if heelPressure(t) < min_value_heelPressure
        min_value_heelPressure = heelPressure(t);
    end
end

gyroscopeUniformedTime = [];
gyroscopeUniformedValue = [];
toePressureUniformedTime = [];
toePressureUniformedValue = [];
heelPressureUniformedTime = [];
heelPressureUniformedValue = [];

for t = 1 : 1 : length(gyroscopeRawValue)
    % time
    gyroscopeUniformedTime = [gyroscopeUniformedTime t * t_interval_gyroscopeRawValue];

    % value
    if gyroscopeRawValue(t) >= 0
        gyroscopeUniformedValue = [gyroscopeUniformedValue (gyroscopeRawValue(t) / max_value_gyroscopeRawValue)];
    else
        gyroscopeUniformedValue = [gyroscopeUniformedValue (gyroscopeRawValue(t) / abs(min_value_gyroscopeRawValue))];
    end
end

for t = 1 : 1 : length(toePressure)
    % time
    toePressureUniformedTime = [toePressureUniformedTime t * t_interval_toePressure];

    % value
    if toePressure(t) >= 0
        toePressureUniformedValue = [toePressureUniformedValue (toePressure(t) / max_value_toePressure)];
    else
    end
end


for t = 1 : 1 : length(heelPressure)
    % time
    heelPressureUniformedTime = [heelPressureUniformedTime t * t_interval_heelPressure];

    % value
    if heelPressure(t) >= 0
        heelPressureUniformedValue = [heelPressureUniformedValue (heelPressure(t) / max_value_heelPressure)];
    else
    end
end

%   display data
figure();
plot(gyroscopeUniformedTime, gyroscopeUniformedValue, 'r');

hold on
plot(toePressureUniformedTime, toePressureUniformedValue, 'g');
plot(heelPressureUniformedTime, heelPressureUniformedValue, 'b');
hold off