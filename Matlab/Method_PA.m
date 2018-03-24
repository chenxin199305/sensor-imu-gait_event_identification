%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   ???????¡Â???????????????? ???????????? TOHS
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. clear all data
clear;
clc;

windowLength = 13;
trainingTimes = 3;
timeInterval = 0.01;
TOHSInitialThreshold = 1;
learningRateAlpha = 1.2;
learningRateBeta = 0.8;

% 2. open file, get file id
training_fid = fopen('normal_IMU_6kmh_1.txt', 'r');
gyroscope_fid = fopen('normal_IMU_6kmh_1.txt', 'r');
% pressure_fid = fopen('normal_Pressure_3ms_3.txt', 'r');

if training_fid >= 0
else
    return;
end

if gyroscope_fid >= 0
else
    return;
end

% if pressure_fid >= 0
% else
%     return;
% end

% 3. read file data
trainingRawValue = [];
trainingFilteredValue = [];

while ~feof(training_fid)
    
    line = fgetl(training_fid);
    
    if strcmp(line, 'gyoRawY')
        line = fgetl(training_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            trainingRawValue = [trainingRawValue lineNum];
            
            line = fgetl(training_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
    
end

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
end

% 4. training process, get TOHS training threshold
trainingFilteredValue = [];

stepPositions = [];
stepValues = [];

TOPositions = [];
TOGradients = [];
TOValues = [];
HSPositions = [];
HSGradients = [];
HSValues = [];

for i = 1 : 1 : trainingTimes
    if i == 1
        [trainingFilteredValue, ...
            stepPositions, stepValues, ...
            windowLength, TOHSthreshold, ...
            TOPositions, TOValues, TOGradients, ...
            HSPositions, HSValues, HSGradients] = ...
            AdaptiveTOHSDetectingMethod(...
            trainingRawValue, timeInterval, ...
            windowLength, TOHSInitialThreshold, learningRateAlpha, learningRateBeta);
    elseif i < trainingTimes
        [trainingFilteredValue, ...
            stepPositions, stepValues, ...
            windowLength, TOHSthreshold, ...
            TOPositions, TOValues, TOGradients, ...
            HSPositions, HSValues, HSGradients] = ...
            AdaptiveTOHSDetectingMethod(...
            trainingRawValue, timeInterval, ...
            windowLength, TOHSthreshold, learningRateAlpha, learningRateBeta);
    else
        tic;
        [trainingFilteredValue, ...
            stepPositions, stepValues, ...
            windowLength, TOHSthreshold, ...
            TOPositions, TOValues, TOGradients, ...
            HSPositions, HSValues, HSGradients] = ...
            AdaptiveTOHSDetectingMethod(...
            gyroscopeRawValue, timeInterval, ...
            windowLength, TOHSthreshold, learningRateAlpha, learningRateBeta);
        toc;
    end
end

%   display data
t = 1 : 1 : length(trainingFilteredValue);
figure();
plot(t, trainingFilteredValue);

hold on
t = 1 : 1 : length(gyroscopeRawValue);
plot(t, gyroscopeRawValue, 'r');
plot(TOPositions, TOValues, '*r');
plot(HSPositions, HSValues, '*g');
plot(stepPositions, stepValues, '*y');
hold off

% t_gradients = 1 : 1 : length(gradients);
%
% figure();
% plot(t_gradients, gradients);