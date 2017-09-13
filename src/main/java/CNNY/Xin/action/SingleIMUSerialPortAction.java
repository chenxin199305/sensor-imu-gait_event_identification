package CNNY.Xin.action;

import java.util.ArrayList;

import CNNY.Xin.model.SingleIMUPhasePlainModel;
import CNNY.Xin.model.SingleIMUSerialPortModel;
import CNNY.Xin.view.SingleIMUSerialPortPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SingleIMUSerialPortAction {

	private SingleIMUSerialPortModel singleIMUSerialPortModel;
	private SingleIMUSerialPortPanel singleIMUSerialPortPanel;
	
	/**
	 *	Func Info:
	 *		SingleIMUSerialPortAction Class Init 
	 */
	public SingleIMUSerialPortAction(
			SingleIMUSerialPortModel singleIMUSerialPortModel,
			SingleIMUSerialPortPanel singleIMUSerialPortPanel) {
	
		this.singleIMUSerialPortModel = singleIMUSerialPortModel;
		this.singleIMUSerialPortPanel = singleIMUSerialPortPanel;
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
					if ((tempArray = readSerialPort(singleIMUSerialPortModel.serialPort)) != null) {
						singleIMUSerialPortModel.serialPortBufferData.addAll(tempArray);
						for (int i = 0; i < singleIMUSerialPortModel.serialPortBufferData.size(); i++) {

							// if decode success, imu data model is ready
							if(singleIMUSerialPortModel.imuFrameDecoder.PacketDecode(
									singleIMUSerialPortModel.serialPortBufferData.get(i))) {

								//							System.out.println("packet decode success.");

								// broadcast to controller 
								// to refresh data
								refreshIMUDataDisplay();

								// clear serial port buffer
								singleIMUSerialPortModel.serialPortBufferData.clear();
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

			// 4 还有其他标志位可选用...根据具体情况可以进行添加...		
		}

	}
	
	/**
	 *	Function Info
	 *		refresh the text content display of imu data 
	 */
	public void refreshIMUDataDisplay() {

		// refresh data display on text area
		singleIMUSerialPortPanel.textAreaContentVal.setText(singleIMUSerialPortModel.imuDataDecoder.imuDataModel.StringData);

		// refresh data display on chart
		// ...
	}

	/**
	 *	Function Info
	 *		Serial Port ComboBox Pop Up 
	 */
	public void serialPortComboBoxPopUp() {

		singleIMUSerialPortPanel.comboBoxSerialPortSelection.removeAllItems();
		for (String object : SerialPortList.getPortNames()) {
			singleIMUSerialPortPanel.comboBoxSerialPortSelection.addItem(object);
		}
	}

	/**
	 *	Function Info
	 *		Connect button clicked event handler 
	 */
	public void connectButtonCliced() {

		switch (singleIMUSerialPortPanel.buttonConnectDisconnect.getText()) {
		case "Connect":
			// select port
			if (singleIMUSerialPortPanel.comboBoxSerialPortSelection.getSelectedItem() != null && 
					singleIMUSerialPortPanel.comboBoxSerialPortSelection.getSelectedItem().toString().length() > 1) {
				singleIMUSerialPortModel.serialPort = 
						new SerialPort(singleIMUSerialPortPanel.comboBoxSerialPortSelection.getSelectedItem().toString());
			}
			else {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): serial port select error!");
			}

			// open port
			if (singleIMUSerialPortModel.serialPort.isOpened() == false) {
				try {
					singleIMUSerialPortModel.serialPort.openPort();
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
				singleIMUSerialPortModel.serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (SerialPortException e2) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): set port parameter error!");
				try {
					singleIMUSerialPortModel.serialPort.closePort();
				} catch (SerialPortException e) {
					e.printStackTrace();
				}
				return;
			}

			// add event listener
			try {
				singleIMUSerialPortModel.serialPort.addEventListener(new IMUSerialPortEventListener());
			} catch (SerialPortException e1) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): add event listener error!");
				return;
			}

			// change button text
			singleIMUSerialPortPanel.buttonConnectDisconnect.setText("Disconnect");

			break;
		case "Disconnect":

			// remove event listener
			try {
				singleIMUSerialPortModel.serialPort.removeEventListener();
			} catch (SerialPortException e) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): remove event listener error!");
				e.printStackTrace();
				return;
			}

			// close port
			if (singleIMUSerialPortModel.serialPort.isOpened() == true) {
				try {
					singleIMUSerialPortModel.serialPort.closePort();
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
			singleIMUSerialPortPanel.buttonConnectDisconnect.setText("Connect");

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
