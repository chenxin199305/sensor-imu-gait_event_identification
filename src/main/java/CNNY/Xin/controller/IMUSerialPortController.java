package CNNY.Xin.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import CNNY.Xin.action.IMUSerialPortAction;
import CNNY.Xin.model.IMUSerialPortModel;
import CNNY.Xin.view.IMUSerialPortPanel;

public class IMUSerialPortController {

	public IMUSerialPortAction action;
	public IMUSerialPortModel model;
	public IMUSerialPortPanel panel;

	public IMUSerialPortController(
			IMUSerialPortModel iMUSerialPortModel,
			IMUSerialPortPanel singleIMUSerialPortPanel) {

		this.model = iMUSerialPortModel;
		this.panel = singleIMUSerialPortPanel;
		this.action = new IMUSerialPortAction(model, panel);

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
