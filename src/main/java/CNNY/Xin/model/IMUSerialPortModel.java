package CNNY.Xin.model;

import java.util.ArrayList;

import jssc.SerialPort;

public class IMUSerialPortModel {

	public SerialPort serialPort;
	public ArrayList<Short> serialPortBufferData;
	public IMUDataDecoder imuDataDecoder;
	public IMUFrameDecoder imuFrameDecoder;

	public IMUSerialPortModel() {

		this.imuDataDecoder = new IMUDataDecoder();
		this.imuFrameDecoder = new IMUFrameDecoder(imuDataDecoder);
		
		serialPortBufferData = new ArrayList<Short>();
	}
}
