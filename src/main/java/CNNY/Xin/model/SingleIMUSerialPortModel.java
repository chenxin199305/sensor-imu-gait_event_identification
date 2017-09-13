package CNNY.Xin.model;

import java.util.ArrayList;

import jssc.SerialPort;

public class SingleIMUSerialPortModel {

	public SerialPort serialPort;
	public ArrayList<Short> serialPortBufferData = new ArrayList<>();
	public IMUDataDecoder imuDataDecoder = new IMUDataDecoder();
	public IMUFrameDecoder imuFrameDecoder = new IMUFrameDecoder(imuDataDecoder);

}
