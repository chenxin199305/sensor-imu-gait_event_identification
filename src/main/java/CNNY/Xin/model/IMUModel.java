package CNNY.Xin.model;

public class IMUModel {

	// serial port
	public IMUSerialPortModel serialPortModel;

	// data display
	public IMUDataDisplayModel dataDisplayModel;
	
	// phase plain
	public IMUPhasePlainModel phasePlainModel;
	
	// detect algorithm
	public IMUDetectAlgorithmModel detectAlgorithmModel;
	
	public IMUModel() {

		// serial port
		serialPortModel = new IMUSerialPortModel();
		
		// data display
		dataDisplayModel = new IMUDataDisplayModel();
		
		// phase plain
		phasePlainModel = new IMUPhasePlainModel();
		
		// detect algorithm
		detectAlgorithmModel = new IMUDetectAlgorithmModel();
	}

}
