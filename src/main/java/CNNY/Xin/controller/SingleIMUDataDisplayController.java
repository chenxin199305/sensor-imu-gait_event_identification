package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import CNNY.Xin.action.SingleIMUDataDisplayAction;
import CNNY.Xin.model.SingleIMUDataDisplayModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;

public class SingleIMUDataDisplayController {

	public SingleIMUDataDisplayAction action;
	public SingleIMUDataDisplayModel model;
	public SingleIMUDataDisplayPanel panel;

	public SingleIMUDataDisplayController(
			SingleIMUDataDisplayModel singleIMUDataDisplayModel,
			SingleIMUDataDisplayPanel singleIMUDataDisplayPanel) {

		this.model = singleIMUDataDisplayModel;
		this.panel = singleIMUDataDisplayPanel;
		this.action = new SingleIMUDataDisplayAction(model, panel);

		initActionListener();
	}

	private void initActionListener() {

		// check box selection
		panel.chckbxAcceleration.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.accelerationCheckBoxStateChange();
			}
		});

		panel.chckbxAngleVelocity.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.angleVelocityCheckBoxStateChange();
			}
		});

		panel.chckbxEulerAngle.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.eulerAngleCheckBoxStateChange();
			}
		});

		panel.chckbxAccFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.accFilteredCheckBoxStateChange();
			}
		});

		panel.chckbxAngvelFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.angVelFilteredCheckBoxStateChange();
			}
		});

		panel.chckbxEulangFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.eulerAngleFilteredCheckBoxStateChange();
			}
		});

		panel.chckbxToeOffHeelHitDetect.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.toeOffHeelHitDetectCheckBoxStateChange();
			}
		});

		panel.chckbxDebug.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				action.debugCheckBoxStateChange();
			}
		});

		// StartToRecord Button Clicked
		panel.btnStartRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.startToRecordButtonCliced();
			}
		});

		// SaveToFile Button Clicked
		panel.btnSaveToFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.saveToFileButtonCliced();
			}
		});
	}
}
