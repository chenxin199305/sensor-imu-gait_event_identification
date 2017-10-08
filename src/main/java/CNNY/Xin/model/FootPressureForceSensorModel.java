package CNNY.Xin.model;

public class FootPressureForceSensorModel {

	// serial port
	public FootPressureForceSensorSerialPortModel serialPortModel;

	// data display
	public FootPressureForceSensorDataDisplayModel dataDisplayModel;
	
	public FootPressureForceSensorModel() {

		// serial port
		serialPortModel = new FootPressureForceSensorSerialPortModel();
		
		// data display
		dataDisplayModel = new FootPressureForceSensorDataDisplayModel();
		
	}

}
