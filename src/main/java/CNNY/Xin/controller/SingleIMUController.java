package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import CNNY.Xin.action.SingleIMUAction;
import CNNY.Xin.action.SingleIMUDataDisplayAction;
import CNNY.Xin.action.SingleIMUPhasePlainAction;
import CNNY.Xin.action.SingleIMUSerialPortAction;
import CNNY.Xin.model.SingleIMUDataDisplayModel;
import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.model.SingleIMUSerialPortModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;
import CNNY.Xin.view.SingleIMUPanel;
import CNNY.Xin.view.SingleIMUPhasePlainPanel;
import CNNY.Xin.view.SingleIMUSerialPortPanel;

public class SingleIMUController {

	public SingleIMUModel singleIMUModel;
	public SingleIMUPanel singleIMUPanel;
	public SingleIMUAction singleIMUAction;
	
	// serial port MVA
	private SingleIMUSerialPortAction serialPortAction;
	private SingleIMUSerialPortModel serialPortModel;
	private SingleIMUSerialPortPanel serialPortPanel;
	
	// data display MVA
	private SingleIMUDataDisplayAction dataDisplayAction;
	private SingleIMUDataDisplayPanel dataDisplayPanel;
	private SingleIMUDataDisplayModel dataDisplayModel;
	
	// phase plain MVA
	private SingleIMUPhasePlainAction phasePlainAction;
	private SingleIMUPhasePlainModel phasePlainModel;
	private SingleIMUPhasePlainPanel phasePlainPanel;

	/**
	 *	Function Info:
	 *		Initialization 
	 */
	public SingleIMUController(
			SingleIMUModel singleIMUModel,
			SingleIMUPanel singleIMUPanel) {

		this.singleIMUModel = singleIMUModel;
		this.singleIMUPanel = singleIMUPanel;
		this.singleIMUAction = new SingleIMUAction(singleIMUModel, singleIMUPanel);

		// serial port MVA
		this.serialPortModel = singleIMUModel.serialPortModel;
		this.serialPortPanel = singleIMUPanel.serialPortPanel;
		this.serialPortAction = new SingleIMUSerialPortAction(serialPortModel, serialPortPanel);
		
		// data display MVA
		this.dataDisplayModel = singleIMUModel.dataDisplayModel;
		this.dataDisplayPanel = singleIMUPanel.dataDisplayPanel;
		this.dataDisplayAction = new SingleIMUDataDisplayAction(dataDisplayModel, dataDisplayPanel);
		
		// phase plain MVA
		this.phasePlainModel = singleIMUModel.phasePlainModel;
		this.phasePlainPanel = singleIMUPanel.phasePlainPanel;
		this.phasePlainAction = new SingleIMUPhasePlainAction(phasePlainModel, phasePlainPanel);
		
		initActionListener();
	}

	/**
	 *	Function Info:
	 *		Initiate Action Listeners 
	 */
	private void initActionListener() {

		// serial port MVA combobox pop up
		serialPortPanel.comboBoxSerialPortSelection.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				serialPortAction.serialPortComboBoxPopUp();
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		
		// serial port MVA connect button clicked
		serialPortPanel.buttonConnectDisconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				serialPortAction.connectButtonCliced();
			}
		});

		// data display MVA check box selection
		dataDisplayPanel.chckbxAcceleration.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.accelerationCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxAngleVelocity.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.angleVelocityCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxEulerAngle.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.eulerAngleCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxAccFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.accFilteredCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxAngvelFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.angVelFilteredCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxEulangFiltered.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.eulerAngleFilteredCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxToeOffHeelHitDetect.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.toeOffHeelHitDetectCheckBoxStateChange();
			}
		});

		dataDisplayPanel.chckbxDebug.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				dataDisplayAction.debugCheckBoxStateChange();
			}
		});

		// StartToRecord Button Clicked
		dataDisplayPanel.btnStartRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				dataDisplayAction.startToRecordButtonCliced();
			}
		});

		// SaveToFile Button Clicked
		dataDisplayPanel.btnSaveToFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				dataDisplayAction.saveToFileButtonCliced();
			}
		});

	}

}
