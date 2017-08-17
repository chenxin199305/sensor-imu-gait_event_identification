package CNNY.Xin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.jfree.data.time.TimeSeriesCollection;

import CNNY.Xin.model.SingleIMUStatusModel;
import CNNY.Xin.view.SingleIMUStatusPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *	Class Info:
 *		IMU Status Action 
 */
public class SingleIMUStatusAction {

	private SingleIMUStatusModel singleIMUStatusModel;
	private SingleIMUStatusPanel singleIMUStatusPanel;
	private TimeSeriesCollection imuChartTimeSeriesCollection = new TimeSeriesCollection();
	private Boolean recordingFlag = true;

	public SingleIMUStatusAction(
			SingleIMUStatusModel singleIMUStatusModel,
			SingleIMUStatusPanel singleIMUStatusPanel) {

		this.singleIMUStatusModel = singleIMUStatusModel;
		this.singleIMUStatusPanel = singleIMUStatusPanel;
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
					singleIMUStatusModel.serialPortBufferData.addAll(
							readSerialPort(singleIMUStatusModel.serialPort));
					for (int i = 0; i < singleIMUStatusModel.serialPortBufferData.size(); i++) {

						// if decode success, imu data model is ready
						if(singleIMUStatusModel.imuFrameDecoder.PacketDecode(
								singleIMUStatusModel.serialPortBufferData.get(i)) != null) {
							
							System.out.println("packet decode success.");
							// refresh data exhibit
							refreshIMUDataDisplay();
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
	 *		Serial Port ComboBox Pop Up 
	 */
	public void serialPortComboBoxPopUp() {

		singleIMUStatusPanel.comboBoxSerialPortSelection.removeAllItems();
		for (String object : SerialPortList.getPortNames()) {
			singleIMUStatusPanel.comboBoxSerialPortSelection.addItem(object);
		}
	}

	/**
	 *	Function Info
	 *		Connect button clicked event handler 
	 */
	public void connectButtonCliced() {

		switch (singleIMUStatusPanel.btnConnect.getText()) {
		case "Connect":
			// select port
			if (singleIMUStatusPanel.comboBoxSerialPortSelection.getSelectedItem() != null && 
			singleIMUStatusPanel.comboBoxSerialPortSelection.getSelectedItem().toString().length() > 1) {
				singleIMUStatusModel.serialPort = 
						new SerialPort(singleIMUStatusPanel.comboBoxSerialPortSelection.getSelectedItem().toString());
			}
			else {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): serial port select error!");
			}

			// open port
			if (singleIMUStatusModel.serialPort.isOpened() == false) {
				try {
					singleIMUStatusModel.serialPort.openPort();
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
				singleIMUStatusModel.serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (SerialPortException e2) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): set port parameter error!");
				try {
					singleIMUStatusModel.serialPort.closePort();
				} catch (SerialPortException e) {
					e.printStackTrace();
				}
				return;
			}

			// add event listener
			try {
				singleIMUStatusModel.serialPort.addEventListener(new IMUSerialPortEventListener());
			} catch (SerialPortException e1) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): add event listener error!");
				return;
			}

			// change button text
			singleIMUStatusPanel.btnConnect.setText("Disconnect");

			break;
		case "Disconnect":

			// remove event listener
			try {
				singleIMUStatusModel.serialPort.removeEventListener();
			} catch (SerialPortException e) {
				System.err.println("SingleIMUStatusAction.connectButtonCliced(): remove event listener error!");
				e.printStackTrace();
				return;
			}

			// close port
			if (singleIMUStatusModel.serialPort.isOpened() == true) {
				try {
					singleIMUStatusModel.serialPort.closePort();
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
			singleIMUStatusPanel.btnConnect.setText("Connect");

			break;
		default:
			break;
		}
	}

	/**
	 *	Function Info
	 *		StartToRecord button clicked event handler 
	 */
	public void startToRecordButtonCliced() {

		switch (singleIMUStatusPanel.btnStartRecord.getText()) {
		case "StartRecord":
			recordingFlag = true;
			singleIMUStatusPanel.btnStartRecord.setText("StopRecord");
			break;
		case "StopRecord":
			recordingFlag = false;
			singleIMUStatusPanel.btnStartRecord.setText("StartRecord");
			break;
		default:
			break;
		}
	}

	/**
	 *	Function Info
	 *		SaveToFile button clicked event handler 
	 */
	public void saveToFileButtonCliced() {

		JFileChooser jFileChooser = new JFileChooser();
		int chooseResult = jFileChooser.showSaveDialog(null);
		if (chooseResult == JFileChooser.APPROVE_OPTION) {
		}
		else {
			return;
		}

		File file = jFileChooser.getSelectedFile();
		try {
			boolean createFileResult = file.createNewFile();
			if (false == createFileResult) {
				file.delete();
				file.createNewFile();
			}
			else {
			}

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			//			bufferedWriter.write("leftLegThighAngleTimeSeries:\n");
			//			for (int i = 0; i < leftLegThighAngleTimeSeries.getItemCount(); i++) {
			//				bufferedWriter.write(leftLegThighAngleTimeSeries.getValue(i).toString() + "\n");
			//			}
			//			bufferedWriter.write("\n");

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *	Function Info
	 *		acceleration check box state change
	 */
	public void accelerationCheckBoxStateChange() {
		if (singleIMUStatusPanel.chckbxAcceleration.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.accRawZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.accRawZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		angle velocity check box state change
	 */
	public void angleVelocityCheckBoxStateChange() {
		if (singleIMUStatusPanel.chckbxAngleVelocity.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.gyoRawZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoRawXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoRawYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.gyoRawZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		euler angle check box state change
	 */
	public void eulerAngleCheckBoxStateChange() {
		if (singleIMUStatusPanel.chckbxEulerAngle.isSelected()) {
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesXAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesYAxisTimeSeries);
			imuChartTimeSeriesCollection.addSeries(singleIMUStatusModel.eulerAnglesZAxisTimeSeries);
		}
		else {
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesXAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesYAxisTimeSeries);
			imuChartTimeSeriesCollection.removeSeries(singleIMUStatusModel.eulerAnglesZAxisTimeSeries);
		}
	}

	/**
	 *	Function Info
	 *		refresh the display of imu data 
	 */
	public void refreshIMUDataDisplay() {

		// refresh data display on text area
		singleIMUStatusPanel.textAreaContentVal.setText(singleIMUStatusModel.imuDataDecoder.imuDataModel.StringData);

		// refresh data display on chart
		singleIMUStatusModel.updateTimeSeries();
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
			if (readData != null) {
				System.out.println("readData.length = " + readData.length);	
			}

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
