function [ gradient ] = FindGradient( array )
%   FINDGRADIENT is used to change input data array into correlated gradient
%               array
%   Detailed explanation goes here

windowLength = length(array);
middlePosition = round(windowLength / 2);
halfWindowLength = middlePosition - 1;

foreGradient = (array(middlePosition) - array(middlePosition - halfWindowLength)) ...
                / halfWindowLength;
backGradient = (array(middlePosition) - array(middlePosition + halfWindowLength)) ...
                / halfWindowLength;
gradient = (foreGradient + backGradient) / 2;

end

