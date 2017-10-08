package CNNY.Xin.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import CNNY.Xin.action.SingleIMUSerialPortAction;
import CNNY.Xin.model.SingleIMUSerialPortModel;
import CNNY.Xin.view.SingleIMUSerialPortPanel;

public class FootPressureForceSensorSerialPortController {

	public SingleIMUSerialPortAction action;
	public SingleIMUSerialPortModel model;
	public SingleIMUSerialPortPanel panel;

	public FootPressureForceSensorSerialPortController(
			SingleIMUSerialPortModel singleIMUSerialPortModel,
			SingleIMUSerialPortPanel singleIMUSerialPortPanel) {

		this.model = singleIMUSerialPortModel;
		this.panel = singleIMUSerialPortPanel;
		this.action = new SingleIMUSerialPortAction(model, panel);

		initActionListener();
	}

	private void initActionListener() {

		// serial port MVA combobox pop up
		panel.comboBoxSerialPortSelection.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				action.serialPortComboBoxPopUp();
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		// serial port MVA connect button clicked
		panel.buttonConnectDisconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.connectButtonCliced();
			}
		});
	}
}
