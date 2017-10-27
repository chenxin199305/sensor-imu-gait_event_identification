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
subjects_1kmh_result_means = [0.9826 0.9916 0.9275 0.9099];
subjects_1kmh_result_variances = [0.001265 0.00007506 0.0000644 0.0002271];

subjects_2kmh_result_means = [1 1 0.9954 1];
subjects_2kmh_result_variances = [0 0 0.0000644 0];

subjects_3kmh_result_means = [1 1 1 1];
subjects_3kmh_result_variances = [0 0 0 0];

subjects_4kmh_result_means = [1 1 1 1];
subjects_4kmh_result_variances = [0 0 0 0];

subjects_5kmh_result_means = [1 1 1 1];
subjects_5kmh_result_variances = [0 0 0 0];

subjects_6kmh_result_means = [1 1 1 1];
subjects_6kmh_result_variances = [0 0 0 0];

result_means = [
    subjects_1kmh_result_means;
    subjects_2kmh_result_means;
    subjects_3kmh_result_means;
    subjects_4kmh_result_means;
    subjects_5kmh_result_means;
    subjects_6kmh_result_means];
result_variances = [
    subjects_1kmh_result_variances;
    subjects_2kmh_result_variances;
    subjects_3kmh_result_variances;
    subjects_4kmh_result_variances;
    subjects_5kmh_result_variances;
    subjects_6kmh_result_variances];

%   draw bar figure
resultBar = bar(result_means, 1);
resultBar(1).FaceColor = 'red';
resultBar(2).FaceColor = 'yellow';
resultBar(3).FaceColor = 'green';
resultBar(4).FaceColor = 'blue';
set(gca, 'YLim', [0.8, 1.01]);
set(gca, 'XTickLabel', {'1', '2', '3', '4', '5', '6'});
set(gca, 'FontSize', 12);
xlabel('Walking Speed [KM/H]');
ylabel('Reliability');
legend('Subject 1','Subject 2','Subject 3','Subject 4', 'Location', 'EastOutside');

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
subjects_1kmh_result_means_mean = mean(subjects_1kmh_result_means(1:end));
subjects_2kmh_result_means_mean = mean(subjects_2kmh_result_means(1:end));
subjects_3kmh_result_means_mean = mean(subjects_3kmh_result_means(1:end));
subjects_4kmh_result_means_mean = mean(subjects_4kmh_result_means(1:end));
subjects_5kmh_result_means_mean = mean(subjects_5kmh_result_means(1:end));
subjects_6kmh_result_means_mean = mean(subjects_6kmh_result_means(1:end));

x = 0 : 1 : 7
y = [subjects_1kmh_result_means_mean subjects_1kmh_result_means_mean ...
    subjects_2kmh_result_means_mean ...
    subjects_3kmh_result_means_mean ...
    subjects_4kmh_result_means_mean ...
    subjects_5kmh_result_means_mean ...
    subjects_6kmh_result_means_mean subjects_6kmh_result_means_mean];
points = [x ; y];
values = spcrv(points, 3);
plot(values(1,:), values(2,:), 'm--');
hold off