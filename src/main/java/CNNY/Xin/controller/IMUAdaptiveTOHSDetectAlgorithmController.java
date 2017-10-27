package CNNY.Xin.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.jfree.data.time.RegularTimePeriod;

import CNNY.Xin.action.IMUAdaptiveTOHSDetectAlgorithmAction;
import CNNY.Xin.event.IMUDataUpdateEventListener;
import CNNY.Xin.model.IMUAdaptiveTOHSDetectAlgorithmModel;
import CNNY.Xin.model.IMUDataModel;
import CNNY.Xin.view.detectAlgorithmView.IMUAdaptiveTOHSDetectAlgorithmPanel;

public class IMUAdaptiveTOHSDetectAlgorithmController implements IMUDataUpdateEventListener {

	public IMUAdaptiveTOHSDetectAlgorithmAction action;
	public IMUAdaptiveTOHSDetectAlgorithmModel model;
	public IMUAdaptiveTOHSDetectAlgorithmPanel panel;

	public IMUAdaptiveTOHSDetectAlgorithmController(
			IMUAdaptiveTOHSDetectAlgorithmModel model,
			IMUAdaptiveTOHSDetectAlgorithmPanel panel) {

		this.model = model;
		this.panel = panel;
		this.action = new IMUAdaptiveTOHSDetectAlgorithmAction(model, panel);

		initActionListener();
	}

	private void initActionListener() {
		
		// start or stop button clicked
		panel.buttonStartStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.startStopButtonCliced((JButton)e.getSource());
			}
		});
	}

	@Override
	public void imuDataUpdate(IMUDataModel imuDataModel) {
		action.imuDataUpdate(imuDataModel);
	}	
	
	public void oneStepFinish(RegularTimePeriod receiveTime) {
		action.oneStepFinish(receiveTime);
	}
}
