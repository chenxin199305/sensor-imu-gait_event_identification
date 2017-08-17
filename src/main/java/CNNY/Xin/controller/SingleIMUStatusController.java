package CNNY.Xin.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import CNNY.Xin.action.SingleIMUStatusAction;
import CNNY.Xin.model.SingleIMUStatusModel;
import CNNY.Xin.view.SingleIMUStatusPanel;

public class SingleIMUStatusController {

	private SingleIMUStatusModel singleIMUStatusModel;
	private SingleIMUStatusPanel singleIMUStatusPanel;
	private SingleIMUStatusAction singleIMUStatusAction;
	
	/**
	 *	Function Info:
	 *		Initialization 
	 */
	public SingleIMUStatusController(
			SingleIMUStatusModel singleIMUStatusModel,
			SingleIMUStatusPanel singleIMUStatusPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUStatusPanel = singleIMUStatusPanel;

		this.singleIMUStatusAction = 
				new SingleIMUStatusAction(this.singleIMUStatusModel, this.singleIMUStatusPanel);
	
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
}
