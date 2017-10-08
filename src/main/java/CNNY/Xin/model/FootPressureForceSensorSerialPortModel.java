package CNNY.Xin.model;

import java.util.ArrayList;

import jssc.SerialPort;

public class FootPressureForceSensorSerialPortModel {

	public SerialPort serialPort;
	public ArrayList<Short> serialPortBufferData;
	public FootPressureForceSensorDataDecoder dataDecoder;
	public FootPressureForceSensorFrameDecoder frameDecoder;

	public FootPressureForceSensorSerialPortModel() {

		this.dataDecoder = new FootPressureForceSensorDataDecoder();
		this.frameDecoder = new FootPressureForceSensorFrameDecoder(dataDecoder);
		
		serialPortBufferData = new ArrayList<Short>();
	}
}
