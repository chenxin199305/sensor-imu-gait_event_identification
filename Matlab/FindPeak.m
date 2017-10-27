function [ isPeak ] = FindPeak( array )
%   FINDPEAK is used to find peak points in an array of data
% 
%   Detailed explanation goes here
%   return
%       0: not peak
%       1: is peak
% 
%   Use a window and gradient to find the peaks
datasLength = length(array);
halfLength = round(datasLength / 2);
middleValue = array(halfLength);

for i = 1 : 1 : datasLength
    if array(i) <= middleValue
    else
        isPeak = 0;
        return
    end
end

isPeak = 1;

end

