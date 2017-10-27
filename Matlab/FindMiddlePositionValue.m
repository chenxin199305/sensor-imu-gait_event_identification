function [ middleValue ] = FindMiddlePositionValue( array )
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
