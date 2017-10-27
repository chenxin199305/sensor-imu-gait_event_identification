%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：绘制检测结果曲线
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. clear all data
clear;
clc;

gyroWindowLength = 9;
forceWindowLength = 5;

% 2. open file, get file id
gyroscope_fid = fopen('normal_IMU_2kmh_1.txt', 'r');
pressure_fid = fopen('normal_Pressure_2kmh_1.txt', 'r');

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
gyroscopePhaseSpaceAngularVelocityValue = [];
gyroscopePhaseSpaceAngularAccelerationValue = [];

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
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....')
        end
    end
end

heelRawPressure = [];
toeRawPressure = [];
heelFilteredPressure = [];
toeFilteredPressure = [];

while ~feof(pressure_fid)
    
    line = fgetl(pressure_fid);
    
    if strcmp(line, 'heelPressure')
        line = fgetl(pressure_fid);
        [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        while startIndex ~= endIndex
            % remove head info
            line(startIndex:endIndex) = [];
            lineNum = str2num(line);
            heelRawPressure = [heelRawPressure lineNum];
            
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
            toeRawPressure = [toeRawPressure lineNum];
            
            line = fgetl(pressure_fid);
            [startIndex, endIndex] = regexp(line, '... ... .. ..:..:.. ... ....');
        end
    end
end

%   filtering
windowLengthBuffer = [];
windowLengthFilteredBuffer = [];

for i = 1 : 1 : length(gyroscopeRawValue)
    newInputRawValue = gyroscopeRawValue(i);
    if length(windowLengthBuffer) < gyroWindowLength
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    elseif length(windowLengthBuffer) == gyroWindowLength
        windowLengthBuffer = windowLengthBuffer(2 : end);
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    else
        windowLengthBuffer = windowLengthBuffer(end - (gyroWindowLength - 2) : end);
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    end
    
    newInputFilteredValue = FindAverage(windowLengthBuffer);
    gyroscopeFilteredValue = [gyroscopeFilteredValue newInputFilteredValue];
    
    %   check one step finish
    if length(gyroscopeFilteredValue) >= 2
        lastValue = gyroscopeFilteredValue(end - 1);
        value = gyroscopeFilteredValue(end);
        gradient = (value - lastValue) * 60;
        
        gyroscopePhaseSpaceAngularVelocityValue = [gyroscopePhaseSpaceAngularVelocityValue value];
        gyroscopePhaseSpaceAngularAccelerationValue = [gyroscopePhaseSpaceAngularAccelerationValue gradient];
    end
end

windowLengthBuffer = [];
windowLengthFilteredBuffer = [];

for i = 1 : 1 : length(toeRawPressure)
    newInputRawValue = toeRawPressure(i);
    if length(windowLengthBuffer) < forceWindowLength
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    elseif length(windowLengthBuffer) == forceWindowLength
        windowLengthBuffer = windowLengthBuffer(2 : end);
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    else
        windowLengthBuffer = windowLengthBuffer(end - (forceWindowLength - 2) : end);
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    end
    
    newInputFilteredValue = FindAverage(windowLengthBuffer);
    toeFilteredPressure = [toeFilteredPressure newInputFilteredValue];
end

windowLengthBuffer = [];
windowLengthFilteredBuffer = [];

for i = 1 : 1 : length(heelRawPressure)
    newInputRawValue = heelRawPressure(i);
    if length(windowLengthBuffer) < forceWindowLength
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    elseif length(windowLengthBuffer) == forceWindowLength
        windowLengthBuffer = windowLengthBuffer(2 : end);
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    else
        windowLengthBuffer = windowLengthBuffer(end - (forceWindowLength - 2) : end);
        windowLengthBuffer = [windowLengthBuffer newInputRawValue];
    end
    
    newInputFilteredValue = FindAverage(windowLengthBuffer);
    heelFilteredPressure = [heelFilteredPressure newInputFilteredValue];
end

%   4. draw filtered data curve
t = 1 : 1 : length(gyroscopeRawValue);
for i = 1 : 1 : length(t)
    t(i) = t(i);
end
figure();
% plot(t, gyroscopeRawValue);

hold on
t = 1 : 1 : length(gyroscopeFilteredValue);
for i = 1 : 1 : length(t)
    t(i) = t(i) / 60;
end
plot(t, gyroscopeFilteredValue);
xlabel('Time [s]')
ylabel('Angular Velocity [deg/s]')
hold off

%   5. draw phase space data curve
figure();
plot(gyroscopePhaseSpaceAngularVelocityValue, gyroscopePhaseSpaceAngularAccelerationValue);
xlabel('Angular Velocity [deg/s]');
ylabel('Angular Acceleration [deg/s^2]');

%   6. draw pressure data curve
for i = 1 : 1 : length(toeFilteredPressure)
    if toeFilteredPressure(i) > 3
        toeFilteredPressure(i) = 3.5;
    end
    
    if heelFilteredPressure(i) > 3
        heelFilteredPressure(i) = 3.5;
    end
end

t = 1 : 1 : length(toeRawPressure);
for i = 1 : 1 : length(t)
    t(i) = t(i);
end
figure();
% plot(t, toeRawPressure, t, heelRawPressure);

hold on
t = 1 : 1 : length(toeFilteredPressure);
for i = 1 : 1 : length(t)
    toeFilteredPressure(i) = toeFilteredPressure(i) * 2.80;
    heelFilteredPressure(i) = heelFilteredPressure(i) * 2.80;
    t(i) = t(i) / 50;
end
plot(t, toeFilteredPressure, t, heelFilteredPressure);
xlabel('Time [s]')
ylabel('Mass [kg]')
hold off
