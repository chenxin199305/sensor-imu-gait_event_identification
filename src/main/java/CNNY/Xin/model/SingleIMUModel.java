package CNNY.Xin.model;

public class SingleIMUModel {

	// serial port
	public SingleIMUSerialPortModel serialPortModel;

	// data display
	public SingleIMUDataDisplayModel dataDisplayModel;
	
	// phase plain
	public SingleIMUPhasePlainModel phasePlainModel;
	
	public SingleIMUModel() {

		// serial port
		serialPortModel = new SingleIMUSerialPortModel();
		
		// data display
		dataDisplayModel = new SingleIMUDataDisplayModel();
		
		// phase plain
		phasePlainModel = new SingleIMUPhasePlainModel();
	}

}
