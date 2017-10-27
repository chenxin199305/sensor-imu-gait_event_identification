package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import CNNY.Xin.action.IMUPhasePlainAction;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.model.IMUPhasePlainModel;
import CNNY.Xin.view.IMUPhasePlainPanel;

public class IMUPhasePlainController {

	public IMUPhasePlainAction action;
	public IMUPhasePlainModel model;
	public IMUPhasePlainPanel panel;

	public IMUPhasePlainController(
			IMUPhasePlainModel iMUPhasePlainModel,
			IMUPhasePlainPanel singleIMUPhasePlainPanel) {

		this.model = iMUPhasePlainModel;
		this.panel = singleIMUPhasePlainPanel;
		this.action = new IMUPhasePlainAction(model, panel);

		initActionListener();
	}

	private void initActionListener() {

		panel.checkBoxAngleVelocity.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.checkBoxAngleVelocityStateChange();
			}
		});

		panel.checkBoxEulerAngle.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.checkBoxEulerAngleStateChange();
			}
		});

		// StartToRecord Button Clicked
		panel.buttonStartStopRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.startToRecordButtonCliced();
			}
		});

		// SaveToFile Button Clicked
		panel.buttonSaveToFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.saveToFileButtonCliced();
			}
		});
	}

	/**
	 *	Func Info:
	 *		imu data update event
	 */
	public void imuDataUpdate(IMUDataModel imuDataModel) {
		action.imuDataUpdate(imuDataModel);
	}
	
	public void setRecordingFlag() {
		action.setRecordingFlag();
	}

	public void clearRecordingFlag() {
		action.clearRecordingFlag();
	}
}
