%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 
%   函数说明：读取数据文件并找到 TOHS
% 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. clear all data
clear;
clc;

windowLength = 7;
TOHSthreshold = 6.0521;

% 2. open file, get file id
gyroscope_fid = fopen('normal_IMU_5ms_1.txt', 'r');
pressure_fid = fopen('normal_Pressure_3ms_3.txt', 'r');

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
gyroscopeFilteredValue = [];

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
    if strcmp(line, 'gyoFilteredY')
        line = fgetl(gyroscope_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            gyroscopeFilteredValue = [gyroscopeFilteredValue lineNum];
            
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

% 4. filter raw data
windowLengthBuffer = [];
gyroscopeFilteredValue = [];

for i = 1 : 1 : length(gyroscopeRawValue)
    
    %   build window length buffer  
    newInputGyroscopeValue = gyroscopeRawValue(i);
    
    if length(windowLengthBuffer) < windowLength
        windowLengthBuffer = [windowLengthBuffer newInputGyroscopeValue];
    else
        windowLengthBuffer = windowLengthBuffer(2 : end);
        windowLengthBuffer = [windowLengthBuffer newInputGyroscopeValue];
    end
    
    gyroscopeFilteredValue = [gyroscopeFilteredValue FindAverage(windowLengthBuffer)];
    
end

% 5. find peaks
windowLengthBuffer = [];
peakPositions = [];
peakGyroValues = [];

for i = 1 : 1 : length(gyroscopeFilteredValue)

    %   build window length buffer  
    newInputGyroscopeValue = gyroscopeFilteredValue(i);
    
    if length(windowLengthBuffer) < windowLength
        windowLengthBuffer = [windowLengthBuffer newInputGyroscopeValue];
        continue;
    else
        windowLengthBuffer = windowLengthBuffer(2 : end);
        windowLengthBuffer = [windowLengthBuffer newInputGyroscopeValue];
    end
    
    %   check peak
    findPeakResult = FindPeak(windowLengthBuffer);
    
    if findPeakResult == 1
        peakPositions = [peakPositions i - (round(windowLength / 2) - 1)];
        peakGyroValues = [peakGyroValues newInputGyroscopeValue];
    end
    
end

% 6. calculate peaks gradient
windowLengthBuffer = [];
peakGradients = [];

for i = 1 : 1 : length(peakPositions)

    windowLengthBuffer = [];
    gradientCannotCalculate = 0;
    
    for j = 1 : 1 : windowLength
        tempPosition = peakPositions(i) - round(windowLength / 2) + j;
        if tempPosition < 1 || tempPosition > length(gyroscopeFilteredValue)
            gradientCannotCalculate = 1;
            break;    
        end
        windowLengthBuffer = [windowLengthBuffer ...
            gyroscopeFilteredValue(peakPositions(i) - round(windowLength / 2) + j)];
    end
    
    if gradientCannotCalculate == 1
        findGradientResult = 0;
    else
        findGradientResult = FindGradient(windowLengthBuffer);
    end
    
    peakGradients = [peakGradients findGradientResult];
    
end

% 7. toe-off and heel-strike threshold
TOHSPositions = [];
TOHSGradients = [];
TOHSGyroValues = [];

for i = 1 : 1 : length(peakGradients)

    if peakGradients(i) > TOHSthreshold
        TOHSPositions = [TOHSPositions peakPositions(i)];
        TOHSGradients = [TOHSGradients peakGradients(i)];
        TOHSGyroValues = [TOHSGyroValues gyroscopeFilteredValue(peakPositions(i))];
    end
    
end

% 8. classify toe-off and heel-strike
TOPositions = [];
TOGradients = [];
TOGyroValues = [];
HSPositions = [];
HSGradients = [];
HSGyroValues = [];
TOHSaverageGyroValue = 0;

for i = 1 : 1 : length(TOHSGyroValues)
    
    TOHSaverageGyroValue = TOHSaverageGyroValue + TOHSGyroValues(i);
    
end

TOHSaverageGyroValue = TOHSaverageGyroValue / length(TOHSGyroValues);

for i = 1 : 1 : length(TOHSPositions)
    
    if TOHSGyroValues(i) > TOHSaverageGyroValue
        TOPositions = [TOPositions TOHSPositions(i)];
        TOGradients = [TOGradients TOHSGradients(i)];
        TOGyroValues = [TOGyroValues TOHSGyroValues(i)];
    elseif TOHSGyroValues(i) < TOHSaverageGyroValue
        HSPositions = [HSPositions TOHSPositions(i)];
        HSGradients = [HSGradients TOHSGradients(i)];
        HSGyroValues = [HSGyroValues TOHSGyroValues(i)];
    else
    end
    
end

% display data
t = 1 : 1 : length(gyroscopeRawValue);
figure();
plot(t, gyroscopeRawValue, t, gyroscopeFilteredValue);

hold on
plot(TOPositions, TOGyroValues, '*r');
plot(HSPositions, HSGyroValues, '*g');
hold off

t = 1 : 1 : length(toePressure);
figure();
plot(t, toePressure, t, heelPressure);

% t_gradients = 1 : 1 : length(gradients);
% 
% figure();
% plot(t_gradients, gradients);