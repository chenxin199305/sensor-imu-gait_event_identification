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
training_fid = fopen('normal_IMU_1kmh_3.txt', 'r');
gyroscope_fid = fopen('normal_IMU_1kmh_3.txt', 'r');
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

% heelPressure = [];
% toePressure = [];
% 
% while ~feof(pressure_fid)
%     
%     line = fgetl(pressure_fid);
%     
%     if strcmp(line, 'heelPressure')
%         line = fgetl(pressure_fid);
%         [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
%         while startIndex ~= endIndex
%             % remove head info
%             line(startIndex:endIndex) = [];
%             lineNum = str2num(line);
%             heelPressure = [heelPressure lineNum];
%             
%             %             disp(lineNum);
%             
%             line = fgetl(pressure_fid);
%             [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
%         end
%     end
%     if strcmp(line, 'toePressure')
%         line = fgetl(pressure_fid);
%         [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
%         while startIndex ~= endIndex
%             % remove head info
%             line(startIndex:endIndex) = [];
%             lineNum = str2num(line);
%             toePressure = [toePressure lineNum];
%             
%             line = fgetl(pressure_fid);
%             [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
%         end
%     end
% end

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
        [trainingFilteredValue, ...
            stepPositions, stepValues, ...
            windowLength, TOHSthreshold, ...
            TOPositions, TOValues, TOGradients, ...
            HSPositions, HSValues, HSGradients] = ...
            AdaptiveTOHSDetectingMethod(...
            gyroscopeRawValue, timeInterval, ...
            windowLength, TOHSthreshold, learningRateAlpha, learningRateBeta);
    end
end

%   5. test process

% [gyroscopeFilteredValue, ...
%     stepPositions, stepValues, ...
%     TOHSthreshold, ...
%     TOPositions, TOValues, TOGradients, ...
%     HSPositions, HSValues, HSGradients] = ...
%     AdaptiveTOHSDetectingMethod(...
%     gyroscopeRawValue, timeInterval, 1, ...
%     windowLength, TOHSthreshold, learningRateAlpha, learningRateBeta);

%   display data
t = 1 : 1 : length(trainingFilteredValue);
figure();
plot(t, trainingFilteredValue);

hold on
plot(TOPositions, TOValues, '*r');
plot(HSPositions, HSValues, '*g');
plot(stepPositions, stepValues, '*y');
hold off

% t_gradients = 1 : 1 : length(gradients);
%
% figure();
% plot(t_gradients, gradients);