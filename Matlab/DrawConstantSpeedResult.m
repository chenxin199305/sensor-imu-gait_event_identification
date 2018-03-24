%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：绘制恒定速度时的检测结果曲线
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   1. clear all data
clear;
clc;

%   2. input subjects' result data
%       sequence: CHENXIN ZHAOXIAOMING HUANGCHAO ZHANGYINPING
subjects_2kmh_result_means =        [100 100 99.5 99.8];
subjects_2kmh_result_variances =    [0 0 0.00644 0];

subjects_3kmh_result_means =        [100 100 100 100];
subjects_3kmh_result_variances =    [0 0 0 0];

subjects_4kmh_result_means =        [100 100 100 100];
subjects_4kmh_result_variances =    [0 0 0 0];

subjects_5kmh_result_means =        [100 100 100 100];
subjects_5kmh_result_variances =    [0 0 0 0];

subjects_6kmh_result_means =        [100 100 100 100];
subjects_6kmh_result_variances =    [0 0 0 0];

result_means = [
    subjects_2kmh_result_means;
    subjects_3kmh_result_means;
    subjects_4kmh_result_means;
    subjects_5kmh_result_means;
    subjects_6kmh_result_means];
result_variances = [
    subjects_2kmh_result_variances;
    subjects_3kmh_result_variances;
    subjects_4kmh_result_variances;
    subjects_5kmh_result_variances;
    subjects_6kmh_result_variances];

%   draw bar figure
resultBar = bar(result_means, 1);
set(resultBar(1),'facecolor','r')
set(resultBar(2),'facecolor','y')
set(resultBar(3),'facecolor','g')
set(resultBar(4),'facecolor','b')
set(gca, 'YLim', [99, 100.1]);
set(gca, 'XTickLabel', {'0.56' '0.83' '1.11' '1.38' '1.67'});
set(gca, 'FontSize', 12);
xlabel('Walking Speed [m/s]');
ylabel('Reliability [%]');
legend('Subject 1','Subject 2','Subject 3','Subject 4', 'Location', 'SouthEast');
% legend('Subject 1','Subject 2','Subject 3','Subject 4', 'Location', 'EastOutside');

%   draw errbar figure
hold on
numgroups   = size(result_means, 1); 
numbars     = size(result_means, 2); 
groupwidth  = min(0.8, numbars/(numbars + 1.5));
for i = 1 : 1 : numbars
      % Based on barweb.m by Bolu Ajiboye from MATLAB File Exchange
      x = (1 : numgroups) - groupwidth/2 + (2*i-1) * groupwidth / (2*numbars);
      errorbar(x, result_means(:,i), result_variances(:,i), 'k', 'linestyle', 'none', 'lineWidth', 1);
end
hold off

%   draw curve
hold on
subjects_2kmh_result_means_mean = mean(subjects_2kmh_result_means(1:end));
subjects_3kmh_result_means_mean = mean(subjects_3kmh_result_means(1:end));
subjects_4kmh_result_means_mean = mean(subjects_4kmh_result_means(1:end));
subjects_5kmh_result_means_mean = mean(subjects_5kmh_result_means(1:end));
subjects_6kmh_result_means_mean = mean(subjects_6kmh_result_means(1:end));

x = 0 : 1 : 6
y = [...
    subjects_2kmh_result_means_mean ...
    subjects_2kmh_result_means_mean ...
    subjects_3kmh_result_means_mean ...
    subjects_4kmh_result_means_mean ...
    subjects_5kmh_result_means_mean ...
    subjects_6kmh_result_means_mean ...
    subjects_6kmh_result_means_mean];
points = [x ; y];
values = spcrv(points, 3);
plot(values(1,:), values(2,:), 'm--');
hold off