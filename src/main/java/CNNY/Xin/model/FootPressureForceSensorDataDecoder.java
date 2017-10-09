package CNNY.Xin.model;

import java.util.ArrayList;

public class FootPressureForceSensorDataDecoder {

	public FootPressureForceSensorDataModel dataModel = new FootPressureForceSensorDataModel();
	
	public void decode(ArrayList<Short> buf) {
		dataModel.sensor11Value = (buf.get(0) << 8) + buf.get(1);
		dataModel.sensor12Value = (buf.get(2) << 8) + buf.get(3);
		dataModel.sensor13Value = (buf.get(4) << 8) + buf.get(5);
		dataModel.sensor14Value = (buf.get(6) << 8) + buf.get(7);
		
		dataModel.sensor22Value = (buf.get(8) << 8) + buf.get(9);
		dataModel.sensor23Value = (buf.get(10) << 8) + buf.get(11);
		dataModel.sensor24Value = (buf.get(12) << 8) + buf.get(13);

		dataModel.sensor32Value = (buf.get(14) << 8) + buf.get(15);
		dataModel.sensor33Value = (buf.get(16) << 8) + buf.get(17);

		dataModel.sensor42Value = (buf.get(18) << 8) + buf.get(19);
		dataModel.sensor43Value = (buf.get(20) << 8) + buf.get(21);

		dataModel.sensor54Value = (buf.get(22) << 8) + buf.get(23);

		dataModel.sensor61Value = (buf.get(24) << 8) + buf.get(25);
		dataModel.sensor62Value = (buf.get(26) << 8) + buf.get(27);
		dataModel.sensor63Value = (buf.get(28) << 8) + buf.get(29);
		dataModel.sensor64Value = (buf.get(30) << 8) + buf.get(31);
	}
	
	public FootPressureForceSensorDataModel decode(ArrayList<Short> buf, Integer len) {

		return dataModel;
	}
}
