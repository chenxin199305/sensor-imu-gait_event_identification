package CNNY.Xin.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import CNNY.Xin.action.FootPressureForceSensorSerialPortAction;
import CNNY.Xin.model.FootPressureForceSensorSerialPortModel;
import CNNY.Xin.view.FootPressureForceSensorSerialPortPanel;

public class FootPressureForceSensorSerialPortController {

	public FootPressureForceSensorSerialPortAction action;
	public FootPressureForceSensorSerialPortModel model;
	public FootPressureForceSensorSerialPortPanel panel;

	public FootPressureForceSensorSerialPortController(
			FootPressureForceSensorSerialPortModel model,
			FootPressureForceSensorSerialPortPanel panel) {

		this.model = model;
		this.panel = panel;
		this.action = new FootPressureForceSensorSerialPortAction(model, panel);

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
