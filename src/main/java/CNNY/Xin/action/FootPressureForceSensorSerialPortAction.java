package CNNY.Xin.action;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import CNNY.Xin.event.FootPressureForceSensorDataUpdateEventManager;
import CNNY.Xin.model.FootPressureForceSensorSerialPortModel;
import CNNY.Xin.view.FootPressureForceSensorSerialPortPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class FootPressureForceSensorSerialPortAction {

	private FootPressureForceSensorSerialPortModel model;
	private FootPressureForceSensorSerialPortPanel panel;

	public FootPressureForceSensorDataUpdateEventManager eventManager;

	/**
	 *	Func Info:
	 *		FootPressureForceSensorSerialPortAction Class Init 
	 */
	public FootPressureForceSensorSerialPortAction(
			FootPressureForceSensorSerialPortModel model,
			FootPressureForceSensorSerialPortPanel panel) {

		this.model = model;
		this.panel = panel;

		eventManager = new FootPressureForceSensorDataUpdateEventManager();
	}

	/**
	 *	Class Info:
	 *		IMU serial port event listener 
	 */
	public class FootPressureForceSensorSerialPortEventListener implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent serialPortEvent) {
			// Receive Data, decode and read data
			if (serialPortEvent.getEventType() == SerialPortEvent.RXCHAR) {
				synchronized (serialPortEvent) {
					ArrayList<Short> tempArray;
					if ((tempArray = readSerialPort(model.serialPort)) != null) {
						model.serialPortBufferData.addAll(tempArray);
//						System.out.println(model.serialPortBufferData.size());
						while(model.frameDecoder.PacketDecode(model.serialPortBufferData)) {
							refreshFootPressureForceSensorDataDisplay();
						}
					}
					else {
					}
				}
			}

			// send clear
			if (serialPortEvent.getEventType() == SerialPortEvent.TXEMPTY) {
				System.out.println("send successful!");
			}

			// serial port wrong
			if (serialPortEvent.getEventType() == SerialPortEvent.ERR) {
				System.err.println("IMUSerialPortEventListener.serialEvent()");
			}
		}
	}

	/**
	 *	Function Info
	 *		refresh the text content display of imu data 
	 */
	public void refreshFootPressureForceSensorDataDisplay() {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// refresh data display on text area
				panel.textAreaContentVal.setText(model.dataDecoder.dataModel.toString());

				// notify serial port data update
				eventManager.notifyListeners(model.dataDecoder.dataModel);				
			}
		});
		
	}

	/**
	 *	Function Info
	 *		Serial Port ComboBox Pop Up 
	 */
	public void serialPortComboBoxPopUp() {

		panel.comboBoxSerialPortSelection.removeAllItems();
		for (String object : SerialPortList.getPortNames()) {
			panel.comboBoxSerialPortSelection.addItem(object);
		}
	}

	/**
	 *	Function Info
	 *		Connect button clicked event handler 
	 */
	public void connectButtonCliced() {

		switch (panel.buttonConnectDisconnect.getText()) {
		case "Connect":
			// select port
			if (panel.comboBoxSerialPortSelection.getSelectedItem() != null && 
			panel.comboBoxSerialPortSelection.getSelectedItem().toString().length() > 1) {
				model.serialPort = 
						new SerialPort(panel.comboBoxSerialPortSelection.getSelectedItem().toString());
			}
			else {
				System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): serial port select error!");
			}

			// open port
			if (model.serialPort.isOpened() == false) {
				try {
					model.serialPort.openPort();
				} catch (SerialPortException e) {
					System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): open serial port error!");
					return;
				}
			}
			else {
				System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): serial port is already opened!");
				return;
			}

			// set port parameter
			try {
				model.serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (SerialPortException e2) {
				System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): set port parameter error!");
				try {
					model.serialPort.closePort();
				} catch (SerialPortException e) {
					e.printStackTrace();
				}
				return;
			}

			// add event listener
			try {
				model.serialPort.addEventListener(new FootPressureForceSensorSerialPortEventListener());
			} catch (SerialPortException e1) {
				System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): add event listener error!");
				return;
			}

			// change button text
			panel.buttonConnectDisconnect.setText("Disconnect");

			break;
		case "Disconnect":

			// remove event listener
			try {
				model.serialPort.removeEventListener();
			} catch (SerialPortException e) {
				System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): remove event listener error!");
				e.printStackTrace();
				return;
			}

			// close port
			if (model.serialPort.isOpened() == true) {
				try {
					model.serialPort.closePort();
				} catch (SerialPortException e) {
					System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): close serial port error!");
					return;
				}
			}
			else {
				System.err.println("FootPressureForceSensorStatusAction.connectButtonCliced(): serial port is already closed!");
				return;
			}

			// change button text
			panel.buttonConnectDisconnect.setText("Connect");

			break;
		default:
			break;
		}
	}

	/**
	 *	Function Info
	 *		read data from serialPort
	 */
	public ArrayList<Short> readSerialPort(SerialPort serialPort) {

		byte[] readData;

		if (serialPort == null || serialPort.isOpened() == false) {
			System.err.println("FootPressureForceSensorStatusAction.readSerialPort(): serial port is not ready.");
			return null;
		}

		// read all data in buffer
		try {

			readData = serialPort.readBytes();

			// print read data length
			//			if (readData != null) {
			//				System.out.println("readData.length = " + readData.length);	
			//			}

			// if data is not null
			if (readData != null) {

				ArrayList<Short> bufferData = new ArrayList<>();
				for (int i = 0; i < readData.length; i++) {
					bufferData.add((short) (readData[i] & 0xFF));
				}
				return bufferData;
			}
			else {
				return null;
			}
		} 
		catch (SerialPortException e) {
			e.printStackTrace();
		}

		return null;
	}
}
