package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import CNNY.Xin.action.SingleIMUAction;
import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;
import CNNY.Xin.view.SingleIMUPanel;

public class SingleIMUController {

	private SingleIMUModel singleIMUStatusModel;
	private SingleIMUPanel singleIMUMainPanel;
	private SingleIMUAction singleIMUStatusAction;

	/**
	 *	Function Info:
	 *		Initialization 
	 */
	public SingleIMUController(
			SingleIMUModel singleIMUStatusModel,
			SingleIMUPanel singleIMUPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUMainPanel = singleIMUPanel;

		this.singleIMUStatusAction = 
				new SingleIMUAction(this.singleIMUStatusModel, this.singleIMUStatusPanel);

		initActionListener();
	}

	/**
	 *	Function Info:
	 *		Initiate Action Listeners 
	 */
	private void initActionListener() {

		// SerailPort ComboBox Pop Up
		singleIMUStatusPanel.comboBoxSerialPortSelection.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				singleIMUStatusAction.serialPortComboBoxPopUp();
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		// Check Box Selection
		singleIMUStatusPanel.chckbxAcceleration.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.accelerationCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxAngleVelocity.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.angleVelocityCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxEulerAngle.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.eulerAngleCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxAccFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.accFilteredCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxAngvelFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.angVelFilteredCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxEulangFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.eulerAngleFilteredCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxToeOffHeelHitDetect.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.toeOffHeelHitDetectCheckBoxStateChange();
			}
		});

		singleIMUStatusPanel.chckbxDebug.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				singleIMUStatusAction.debugCheckBoxStateChange();
			}
		});

		// Connect Button Clicked
		singleIMUStatusPanel.btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				singleIMUStatusAction.connectButtonCliced();
			}
		});

		// StartToRecord Button Clicked
		singleIMUStatusPanel.btnStartRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				singleIMUStatusAction.startToRecordButtonCliced();
			}
		});

		// SaveToFile Button Clicked
		singleIMUStatusPanel.btnSaveToFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				singleIMUStatusAction.saveToFileButtonCliced();
			}
		});

	}

	public SingleIMUModel getSingleIMUStatusModel() {
		return singleIMUStatusModel;
	}

	public SingleIMUDataDisplayPanel getSingleIMUStatusPanel() {
		return singleIMUStatusPanel;
	}

	public SingleIMUAction getSingleIMUStatusAction() {
		return singleIMUStatusAction;
	}
}
