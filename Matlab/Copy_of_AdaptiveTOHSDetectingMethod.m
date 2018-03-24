function [ ...
    trainingFilteredValue, ...
    stepPositions, ...
    stepValues, ...
    windowLength, ...
    TOHSFinalThreshold, ...
    globalTOPositions, ...
    globalTOValues, ...
    globalTOGradients, ...
    globalHSPositions, ...
    globalHSValues, ...
    globalHSGradients] = ...
    AdaptiveTOHSDetectingMethod( ...
    rawValueArray, ...
    timeInterval, ...
    windowInitialLength, ...
    TOHSInitialThreshold, ...
    learningRateAlpha, ...
    learningRateBeta)

%   ADAPTIVETOHSDETECTINGMETHOD Summary of this function goes here
%   Detailed explanation goes here
windowLength = windowInitialLength;
windowLengthBuffer = [];
windowLengthFilteredBuffer = [];
trainingFilteredValue = [];

stepPositions = [];
stepValues = [];
stepCount = 0;
stepLength = 0;
stepLengths = [];
stepLengthsLength = 6;
stepAverageLength = 0;
stepMinimumValue = 0;
stepMaximumValue = 0;

peakPositions = [];
peakValues = [];
peakGradients = [];

TOHSthreshold = TOHSInitialThreshold;
TOHSFinalThreshold = 0;
adaptiveAlpha = learningRateAlpha;
adaptiveBeta = learningRateBeta;

TOHSPositions = [];
TOHSValues = [];
TOHSGradients = [];

TOPositions = [];
TOGradients = [];
TOValues = [];
HSPositions = [];
HSGradients = [];
HSValues = [];
TOHSaverageValue = 0;

globalTOPositions = [];
globalTOValues = [];
globalTOGradients = [];
globalHSPositions = [];
globalHSValues = [];
globalHSGradients = [];

for i = 1 : 1 : length(rawValueArray)
    
    %   build window length raw value buffer
    newInputTrainingRawValue = rawValueArray(i);
    
    if length(windowLengthBuffer) < windowLength
        windowLengthBuffer = [windowLengthBuffer newInputTrainingRawValue];
    elseif length(windowLengthBuffer) == windowLength
        windowLengthBuffer = windowLengthBuffer(2 : end);
        windowLengthBuffer = [windowLengthBuffer newInputTrainingRawValue];
    else
        windowLengthBuffer = windowLengthBuffer(end - (windowLength - 2) : end);
        windowLengthBuffer = [windowLengthBuffer newInputTrainingRawValue];
    end
    
    %   build window length filtered value buffer
    newInputTrainingFilteredValue = FindAverage(windowLengthBuffer);
%     newInputTrainingFilteredValue = newInputTrainingRawValue;
    trainingFilteredValue = [trainingFilteredValue newInputTrainingFilteredValue];
    
    %   update step information
    stepLength = stepLength + 1;
    
    if stepMaximumValue < newInputTrainingFilteredValue
        stepMaximumValue = newInputTrainingFilteredValue;
    end
    
    if newInputTrainingFilteredValue < stepMinimumValue
        stepMinimumValue = newInputTrainingFilteredValue;
    end
    
    if length(windowLengthFilteredBuffer) < windowLength
        windowLengthFilteredBuffer = [windowLengthFilteredBuffer newInputTrainingFilteredValue];
        continue;
    elseif length(windowLengthFilteredBuffer) == windowLength
        windowLengthFilteredBuffer = windowLengthFilteredBuffer(2 : end);
        windowLengthFilteredBuffer = [windowLengthFilteredBuffer newInputTrainingFilteredValue];
    else
        windowLengthFilteredBuffer = windowLengthFilteredBuffer(end - (windowLength - 2) : end);
        windowLengthFilteredBuffer = [windowLengthFilteredBuffer newInputTrainingFilteredValue];
    end
    
    %   check peak
    findPeakResult = FindPeak(windowLengthFilteredBuffer);
    
    if findPeakResult == 1
        peakPositions   = [peakPositions    i - (round(windowLength / 2) - 1)];
        peakValues      = [peakValues       FindMiddlePositionValue(windowLengthFilteredBuffer)];
        
        %   check gradient
        findGradientResult = FindGradient(windowLengthFilteredBuffer);
        peakGradients = [peakGradients findGradientResult];
        
        if findGradientResult > TOHSthreshold
            TOHSPositions   = [TOHSPositions    peakPositions(end)];
            TOHSValues      = [TOHSValues       peakValues(end)];
            TOHSGradients   = [TOHSGradients    peakGradients(end)];
        end
    end
    
    %   check one step finish
    lastLastTrainingFilteredValue = windowLengthFilteredBuffer(end - 2);
    lastTrainingFilteredValue = windowLengthFilteredBuffer(end - 1);
    trainingFiltered = windowLengthFilteredBuffer(end);
    
    lastTrainingFilteredGradient = ...
        (lastTrainingFilteredValue - lastLastTrainingFilteredValue) / timeInterval;
    trainingFilteredGradient = ...
        (trainingFiltered - lastTrainingFilteredValue) / timeInterval;
    
    lastTan = atan2(lastTrainingFilteredGradient, lastTrainingFilteredValue);
    tan = atan2(trainingFilteredGradient, trainingFiltered);
    
    if lastTan <= -pi/2 && lastTan >= -pi ...
            && tan <= pi && tan >= pi/2 ...
            && trainingFiltered <= stepMinimumValue * 0.3
        
        %   classify toe-off and heel-strike
        TOHSaverageValue = 0;
        
        for j = 1 : 1 : length(TOHSValues)
            TOHSaverageValue = TOHSaverageValue + TOHSValues(j);
        end
        
        TOHSaverageValue = TOHSaverageValue / length(TOHSValues);
        
        if length(TOHSValues) <= 1
            for j = 1 : 1 : length(TOHSValues)
                TOPositions = [TOPositions  TOHSPositions(j)];
                TOValues    = [TOValues     TOHSValues(j)];
                TOGradients = [TOGradients  TOHSGradients(j)];
            end
        elseif length(TOHSValues) == 2
            HSPositions = [HSPositions  TOHSPositions(1)];
            HSValues    = [HSValues     TOHSValues(1)];
            HSGradients = [HSGradients  TOHSGradients(1)];
            
            TOPositions = [TOPositions  TOHSPositions(2)];
            TOValues    = [TOValues     TOHSValues(2)];
            TOGradients = [TOGradients  TOHSGradients(2)];
        elseif length(TOHSValues) > 2
            HSPositions = [HSPositions  TOHSPositions(1)];
            HSValues    = [HSValues     TOHSValues(1)];
            HSGradients = [HSGradients  TOHSGradients(1)];
            
            TOPositions = [TOPositions  TOHSPositions(end)];
            TOValues    = [TOValues     TOHSValues(end)];
            TOGradients = [TOGradients  TOHSGradients(end)];
            
            for j = 2 : 1 : length(TOHSValues) - 1
                if TOHSValues(j) > TOHSaverageValue
                    TOPositions = [TOPositions  TOHSPositions(j)];
                    TOValues    = [TOValues     TOHSValues(j)];
                    TOGradients = [TOGradients  TOHSGradients(j)];
                elseif TOHSValues(j) < TOHSaverageValue
                    HSPositions = [HSPositions  TOHSPositions(j)];
                    HSValues    = [HSValues     TOHSValues(j)];
                    HSGradients = [HSGradients  TOHSGradients(j)];
                else
                end
            end
        end
        
        %   adaptively change threshold
        if length(TOHSPositions) > 2
            TOHSthreshold = TOHSthreshold * adaptiveAlpha;
        elseif length(TOHSPositions) < 2
            TOHSthreshold = TOHSthreshold * adaptiveBeta;
        else
            TOHSthreshold = TOHSthreshold;
        end
        
        %   adaptively change windowlength
        stepLengths = [stepLengths stepLength];
        if length(stepLengths) > stepLengthsLength
            stepLengths = stepLengths(end - stepLengthsLength + 1:end);
        end
        stepLength = 0;
        
        stepAverageLength = 0;
        for j = 1 : 1 : length(stepLengths)
            stepAverageLength = stepAverageLength + stepLengths(j);
        end
        stepAverageLength = stepAverageLength / length(stepLengths);
        
        windowLength = floor(stepAverageLength * 0.1) + 1 - mod(floor(stepAverageLength * 0.1), 2);
        if windowLength < 3
            windowLength = 3;
        end
        
        %   record necessary information
        stepCount       = stepCount + 1;
        stepPositions   = [stepPositions i];
        stepValues      = [stepValues newInputTrainingFilteredValue];
        stepMinimumValue    = 0;
        stepMaximumValue    = 0;
        
        globalTOPositions   = [globalTOPositions    TOPositions];
        globalTOValues      = [globalTOValues       TOValues];
        globalTOGradients   = [globalTOGradients    TOGradients];
        
        globalHSPositions   = [globalHSPositions    HSPositions];
        globalHSValues      = [globalHSValues       HSValues];
        globalHSGradients   = [globalHSGradients    HSGradients];
        
        %   clear reference variance
        
        peakPositions   = [];
        peakValues      = [];
        peakGradients   = [];
        
        TOHSPositions   = [];
        TOHSValues      = [];
        TOHSGradients   = [];
        
        TOPositions     = [];
        TOValues        = [];
        TOGradients     = [];
        
        HSPositions     = [];
        HSValues        = [];
        HSGradients     = [];
    end
    
end

TOHSFinalThreshold = TOHSthreshold;

end

