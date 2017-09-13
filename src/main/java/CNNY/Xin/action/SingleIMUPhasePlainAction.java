package CNNY.Xin.action;

import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.view.SingleIMUPhasePlainPanel;

public class SingleIMUPhasePlainAction {

	private SingleIMUPhasePlainModel model;
	private SingleIMUPhasePlainPanel panel;
	
	public SingleIMUPhasePlainAction(
			SingleIMUPhasePlainModel singleIMUPhasePlainModel,
			SingleIMUPhasePlainPanel singleIMUPhasePlainPanel) {
	
		this.model = singleIMUPhasePlainModel;
		this.panel = singleIMUPhasePlainPanel;
	}
	
	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {

		model.gyoXAxisXYSeries.addOrUpdate(imuDataModel.GyoRaw[0], (Number)(imuDataModel.GyoRaw[0]- model.LastGyoRaw[0]));
		model.gyoYAxisXYSeries.addOrUpdate(imuDataModel.GyoRaw[1], (Number)(imuDataModel.GyoRaw[1]- model.LastGyoRaw[1]));
		model.gyoZAxisXYSeries.addOrUpdate(imuDataModel.GyoRaw[2], (Number)(imuDataModel.GyoRaw[2]- model.LastGyoRaw[2]));
		
		model.eulerAnglesXAxisXYSeries.addOrUpdate(imuDataModel.EulerAngles[0], (Number)(imuDataModel.EulerAngles[0] - model.LastEulerAngles[0]));
		model.eulerAnglesYAxisXYSeries.addOrUpdate(imuDataModel.EulerAngles[1], (Number)(imuDataModel.EulerAngles[1] - model.LastEulerAngles[1]));
		model.eulerAnglesZAxisXYSeries.addOrUpdate(imuDataModel.EulerAngles[2], (Number)(imuDataModel.EulerAngles[2] - model.LastEulerAngles[2]));

		model.LastGyoRaw[0] = imuDataModel.GyoRaw[0];
		model.LastGyoRaw[1] = imuDataModel.GyoRaw[1];
		model.LastGyoRaw[2] = imuDataModel.GyoRaw[2];

		model.LastEulerAngles[0] = imuDataModel.EulerAngles[0];
		model.LastEulerAngles[1] = imuDataModel.EulerAngles[1];
		model.LastEulerAngles[2] = imuDataModel.EulerAngles[2];
		
	}

	public void checkBoxAngleVelocityStateChange() {
		if (panel.checkBoxAngleVelocity.isSelected()) {
//			panel.chartDataSet.addSeries(model.gyoXAxisXYSeries);
			panel.chartDataSet.addSeries(model.gyoYAxisXYSeries);
//			panel.chartDataSet.addSeries(model.gyoZAxisXYSeries);
		}
		else {
//			panel.chartDataSet.removeSeries(model.gyoXAxisXYSeries);
			panel.chartDataSet.removeSeries(model.gyoYAxisXYSeries);
//			panel.chartDataSet.removeSeries(model.gyoZAxisXYSeries);
		}
	}

	public void checkBoxEulerAngleStateChange() {
		if (panel.checkBoxEulerAngle.isSelected()) {
			panel.chartDataSet.addSeries(model.eulerAnglesXAxisXYSeries);
			panel.chartDataSet.addSeries(model.eulerAnglesYAxisXYSeries);
			panel.chartDataSet.addSeries(model.eulerAnglesZAxisXYSeries);
		}
		else {
			panel.chartDataSet.removeSeries(model.eulerAnglesXAxisXYSeries);
			panel.chartDataSet.removeSeries(model.eulerAnglesYAxisXYSeries);
			panel.chartDataSet.removeSeries(model.eulerAnglesZAxisXYSeries);
		}
	}
	
}
