package CNNY.Xin.model;

import java.util.ArrayList;

import jssc.SerialPort;

public class SingleIMUSerialPortModel {

	public SerialPort serialPort;
	public ArrayList<Short> serialPortBufferData;
	public IMUDataDecoder imuDataDecoder;
	public IMUFrameDecoder imuFrameDecoder;

	public SingleIMUSerialPortModel() {

		this.imuDataDecoder = new IMUDataDecoder();
		this.imuFrameDecoder = new IMUFrameDecoder(imuDataDecoder);
		
		serialPortBufferData = new ArrayList<Short>();
	}
}
