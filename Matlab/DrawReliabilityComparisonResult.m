%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   函数说明：绘制变速速度时的检测结果曲线
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% speed = [2 2 3 4 5 6 6];
speed = [0.56 0.56 0.83 1.11 1.38 1.67 1.67];
PA_reliability = ...
    [100 100 100 100 100 100 100];
Greene_reliability = ...
    [100 100 100 100 100 100 100];
Lee_reliability = ...
    [90.9 90.9 98.8 99.5 100 100 100];
Gouwanda_reliability = ...
    [47.3 47.3 62.7 63.6 92.6 89.8 89.8];

points = [speed ; PA_reliability];
PA_values = spcrv(points, 3);
points = [speed ; Greene_reliability];
Greene_values = spcrv(points, 3);
points = [speed ; Lee_reliability];
Lee_values = spcrv(points, 3);
points = [speed ; Gouwanda_reliability];
Gouwanda_values = spcrv(points, 3);

figure();
hold on 
plot(PA_values(1,:), PA_values(2,:), 'r-', 'LineWidth', 3);
plot(Greene_values(1,:), Greene_values(2,:), 'c--', 'LineWidth', 3);
plot(Lee_values(1,:), Lee_values(2,:), 'g-.', 'LineWidth', 3);
plot(Gouwanda_values(1,:), Gouwanda_values(2,:), 'b:', 'LineWidth', 3);
set(gca, 'YLim', [40, 100.2]);
set(gca, 'XLim', [0.56, 1.67])
% set(gca, 'XTickLabel', {'0.56' '0.83' '1.11' '1.38' '1.67'});
set(gca, 'FontSize', 12);
legend('SGED', 'Greene', 'Lee', 'Gouwanda', 'Location', 'SouthEast');
xlabel('Walking speed [m/s]');
ylabel('Reliability [%]');
hold off