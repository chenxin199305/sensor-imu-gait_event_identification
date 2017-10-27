package CNNY.Xin.action;

import javax.swing.JButton;

import org.jfree.data.time.RegularTimePeriod;

import CNNY.Xin.event.IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventManager;
import CNNY.Xin.model.IMUAdaptiveTOHSDetectAlgorithmModel;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.IMUTOHSDataModel;
import CNNY.Xin.view.detectAlgorithmView.IMUAdaptiveTOHSDetectAlgorithmPanel;

public class IMUAdaptiveTOHSDetectAlgorithmAction {

	private IMUAdaptiveTOHSDetectAlgorithmModel model;
	private IMUAdaptiveTOHSDetectAlgorithmPanel panel;

	private Boolean runningFlag = false;

	public IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventManager eventManager;

	public IMUAdaptiveTOHSDetectAlgorithmAction(
			IMUAdaptiveTOHSDetectAlgorithmModel model,
			IMUAdaptiveTOHSDetectAlgorithmPanel panel) {

		this.model = model;
		this.panel = panel;

		eventManager = new IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventManager();
	}

	public void startStopButtonCliced(JButton startStopButton) {

		switch (startStopButton.getText()) {
		case "start":
			model.windowLength = Integer.valueOf(panel.textFieldWindowLength.getText());
			model.initialThreshold = Float.valueOf(panel.textFieldInitialThreshold.getText());
			model.alpha = Float.valueOf(panel.textFieldAlpha.getText());
			model.beta = Float.valueOf(panel.textFieldBeta.getText());
			model.threshold = model.initialThreshold;
			runningFlag = true;
			startStopButton.setText("stop");
			break;
		case "stop":
			model.windowLength = 0;
			model.initialThreshold = 0.0f;
			model.alpha = 0.0f;
			model.beta = 0.0f;
			runningFlag = false;
			startStopButton.setText("start");
			break;
		default:
			break;
		}
	}

	public void imuDataUpdate(IMUDataModel imuDataModel) {

		if (runningFlag == true) {
			
			Float newGyroAngleVelocityValue = (float) (imuDataModel.GyoRaw[1] / 10.0);

			// 1. find peak
			model.eulerAngleOneStepBuffer.add(newGyroAngleVelocityValue);
			if (model.eulerAngleWindowBuffer.size() >= model.windowLength) {
				model.eulerAngleWindowBuffer.remove(0);
				model.eulerAngleWindowBuffer.add(newGyroAngleVelocityValue);
			}
			else {
				model.eulerAngleWindowBuffer.add(newGyroAngleVelocityValue);
			}

			Integer windowBufferSize = model.eulerAngleWindowBuffer.size();
			Integer windowMiddlePosition = model.eulerAngleWindowBuffer.size() / 2;
			Float windowMiddleValue = model.eulerAngleWindowBuffer.get(windowMiddlePosition);

			model.newTOHSPointFindFlag = true;
			for (int i = 0; i < windowBufferSize; i++) {
				if (model.eulerAngleWindowBuffer.get(i) > windowMiddleValue) {
					model.newTOHSPointFindFlag = false;
					break;
				}
			}

			// 2. gradient threshold
			if (model.newTOHSPointFindFlag == true) {
				Float foreGradient = (windowMiddleValue - model.eulerAngleWindowBuffer.get(0)) 
						/ (windowMiddlePosition - 0);
				Float backGradient = (windowMiddleValue - model.eulerAngleOneStepBuffer.get(windowBufferSize - 1)) 
						/ (windowBufferSize - windowMiddlePosition - 1);
				Float gradient = (foreGradient + backGradient) / 2.0f;

				if (gradient > model.threshold) {
					model.newTOHSPointFindFlag = true;
					model.toeOffHeelStrikeBufferAverageValue = (
							model.toeOffHeelStrikeBufferAverageValue * model.toeOffHeelStrikeCount + newGyroAngleVelocityValue) / (model.toeOffHeelStrikeCount + 1);
					model.toeOffHeelStrikeBuffer.add(newGyroAngleVelocityValue);
					model.toeOffHeelStrikeCount++;
				}
				else {
					model.newTOHSPointFindFlag = false;
				}	
			}

			// 3. separate toe-off and heel-strike point
			if (model.newTOHSPointFindFlag == true) {
				if (newGyroAngleVelocityValue > model.toeOffHeelStrikeBufferAverageValue) {
					eventManager.notifyListeners(
							new IMUTOHSDataModel(IMUTOHSDataModel.TOHSTYPE_TO, 
									imuDataModel.receiveTime, 
									newGyroAngleVelocityValue));
					model.newTOHSPointFindFlag = true;
				}
				else if (newGyroAngleVelocityValue < model.toeOffHeelStrikeBufferAverageValue){
					eventManager.notifyListeners(
							new IMUTOHSDataModel(IMUTOHSDataModel.TOHSTYPE_HS, 
									imuDataModel.receiveTime, 
									newGyroAngleVelocityValue));
					model.newTOHSPointFindFlag = true;
				}
				else {
					model.newTOHSPointFindFlag = false;
				}	
			}

			// update text filed
			panel.textArea.setText(model.toString());
		}
		else {
		}
	}

	/**
	 *	Func Info:
	 *		phase plain event listen 
	 * @param receiveTime 
	 */
	public void oneStepFinish(RegularTimePeriod receiveTime) {
		
		// 1. update one step time length
		model.stepLengthInMs = receiveTime.getMiddleMillisecond() - model.lastStepTimeInMs;
		model.lastStepTimeInMs = receiveTime.getMiddleMillisecond();

		// 2. update threshold
		if (model.toeOffHeelStrikeCount > 2) {
			model.threshold = (float) (model.threshold * model.alpha);
		}
		else if (model.toeOffHeelStrikeCount < 2) {
			model.threshold = (float) (model.threshold * model.beta);
		}
		else {
			model.threshold = model.threshold;
		}

		// 3. clear last step 's toe-off and heel-strike points
		model.toeOffHeelStrikeBuffer.clear();
		model.toeOffHeelStrikeCount = 0;
	}
}
