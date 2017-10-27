function [ average ] = FindAverage( array )
%   FINDAVERAGE Summary of this function goes here
%   Detailed explanation goes here

    total = 0;

    for i = 1 : 1 : length(array)
        total = total + array(i);
    end

    average = total / length(array);
    
end

