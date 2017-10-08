package CNNY.Xin.action;

import java.util.ArrayList;

import CNNY.Xin.event.IMUDataUpdateEventManager;
import CNNY.Xin.model.SingleIMUSerialPortModel;
import CNNY.Xin.view.SingleIMUSerialPortPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class FootPressureForceSensorSerialPortAction {

	private SingleIMUSerialPortModel model;
	private SingleIMUSerialPortPanel panel;
	
	public IMUDataUpdateEventManager imuDataUpdateEventManager;
	
	/**
	 *	Func Info:
	 *		SingleIMUSerialPortAction Class Init 
	 */
	public FootPressureForceSensorSerialPortAction(
			SingleIMUSerialPortModel singleIMUSerialPortModel,
			SingleIMUSerialPortPanel singleIMUSerialPortPanel) {

		this.model = singleIMUSerialPortModel;
		this.panel = singleIMUSerialPortPanel;
		
		imuDataUpdateEventManager = new IMUDataUpdateEventManager();
	}
	
	/**
	 *	Class Info:
	 *		IMU serial port event listener 
	 */
	public class IMUSerialPortEventListener implements SerialPortEventListener {
		
		public void serialEvent(SerialPortEvent serialPortEvent) {

			// Receive Data, decode and read data
			if (serialPortEvent.getEventType() == SerialPortEvent.RXCHAR) {

				synchronized (serialPortEvent) {
					//					if (!checkReadFlag()) {
					//						readData();
					//					}
					ArrayList<Short> tempArray;
					if ((tempArray = readSerialPort(model.serialPort)) != null) {
						model.serialPortBufferData.addAll(tempArray);
						for (int i = 0; i < model.serialPortBufferData.size(); i++) {

							// if decode success, imu data model is ready
							if(model.imuFrameDecoder.PacketDecode(
									model.serialPortBufferData.get(i))) {

								// System.out.println("packet decode success.");

								// broadcast to controller 
								// to refresh data
								refreshIMUDataDisplay();

								// clear serial port buffer
								model.serialPortBufferData.clear();
							}
						}
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

			// there are still other events can be added...		
		}

	}
	
	/**
	 *	Function Info
	 *		refresh the text content display of imu data 
	 */
	public void refreshIMUDataDisplay() {

		// refresh data display on text area
		panel.textAreaContentVal.setText(model.imuDataDecoder.imuDataModel.StringData);

		// notify serial port data update
		imuDataUpdateEventManager.notifyListeners(model.imuDataDecoder.imuDataModel);
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
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): serial port select error!");
			}

			// open port
			if (model.serialPort.isOpened() == false) {
				try {
					model.serialPort.openPort();
				} catch (SerialPortException e) {
					System.err.println("SingleIMUStatusAction.connectButtonCliced(): open serial port error!");
					return;
				}
			}
			else {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): serial port is already opened!");
				return;
			}

			// set port parameter
			try {
				model.serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (SerialPortException e2) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): set port parameter error!");
				try {
					model.serialPort.closePort();
				} catch (SerialPortException e) {
					e.printStackTrace();
				}
				return;
			}

			// add event listener
			try {
				model.serialPort.addEventListener(new IMUSerialPortEventListener());
			} catch (SerialPortException e1) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): add event listener error!");
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
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): remove event listener error!");
				e.printStackTrace();
				return;
			}

			// close port
			if (model.serialPort.isOpened() == true) {
				try {
					model.serialPort.closePort();
				} catch (SerialPortException e) {
					System.err.println("SingleIMUStatusAction.connectButtonCliced(): close serial port error!");
					return;
				}
			}
			else {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): serial port is already closed!");
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
			System.err.println("SingleIMUStatusAction.readSerialPort(): serial port is not ready.");
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
