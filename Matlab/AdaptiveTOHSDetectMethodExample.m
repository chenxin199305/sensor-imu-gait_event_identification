%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 
%   函数说明：读取数据文件并训练 TOHS threshold
% 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. clear all data
clear;
clc;

windowLength = 5;

% 2. open file, get file id
gyroscope_fid = fopen('CHENXIN_IMU_6kmh_1.txt', 'r');
pressure_fid = fopen('CHENXIN_PRESSURE_6kmh_1.txt', 'r');

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

% 5. trainning TOHS threshold
timeInterval = 0.01;
oneStepBuffer = [];
stepPositions = [];
stepCount = 0;
windowLengthBuffer = [];
peakPositions = [];
peakGyroValues = [];
TOHSthreshold = 10;
adaptiveAlpha = 1.1;
adaptiveBeta = 0.9;
TOHSPositions = [];
TOHSGradients = [];

for times = 1 : 1 : 10
    windowLengthBuffer = [];
for i = 1 : 1 : length(gyroscopeFilteredValue)

    %   new input value
    newInputGyroscopeValue = gyroscopeFilteredValue(i);
    
    %   build one step buffer
    oneStepBuffer = [oneStepBuffer newInputGyroscopeValue];
    
    %   build window length buffer  
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
    
         %   check gradient
        findGradientResult = FindGradient(windowLengthBuffer);
        
        if findGradientResult > TOHSthreshold
            TOHSPositions = [TOHSPositions peakPositions(end)];
        end
    end
    
    %   check one step finish
    lastLastGyroscopeValue = gyroscopeFilteredValue(i - 2);
    lastGyroscopeValue = gyroscopeFilteredValue(i - 1);
    gyroscopeValue = gyroscopeFilteredValue(i);
    
    lastGradient = (lastGyroscopeValue - lastLastGyroscopeValue) / timeInterval;
    gradient = (gyroscopeValue - lastGyroscopeValue) / timeInterval;
    
    lastTan = atan2(lastGradient, lastGyroscopeValue);
    tan = atan2(gradient, gyroscopeValue);
    
    if lastTan <= -pi/2 && lastTan >= -pi && tan <= pi && tan >= pi/2
        oneStepBuffer = [];
        stepCount = stepCount + 1;
        stepPositions = [stepPositions i];
        
        %   change threshold
        if length(TOHSPositions) > 2
            TOHSthreshold = TOHSthreshold * adaptiveAlpha;
        elseif length(TOHSPositions) < 2
            TOHSthreshold = TOHSthreshold * adaptiveBeta;
        else
            TOHSthreshold = TOHSthreshold;
        end
        
        TOHSPositions = [];
    end
end 
end

% display data
t = 1 : 1 : length(gyroscopeRawValue);
figure();
plot(t, gyroscopeRawValue, t, gyroscopeFilteredValue);

t = 1 : 1 : length(toePressure);
figure();
plot(t, toePressure, t, heelPressure);
