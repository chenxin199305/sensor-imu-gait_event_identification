package CNNY.Xin.model;

import java.util.ArrayList;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import Jama.Matrix;

public class FootPressureForceSensorDataDisplayModel {

	// char panel data
	public TimeSeries sensor61TimeSeries = new TimeSeries("sensor_61");
	public TimeSeries sensor62TimeSeries = new TimeSeries("sensor_61");
	public TimeSeries sensor63TimeSeries = new TimeSeries("sensor_61");
	public TimeSeries sensor64TimeSeries = new TimeSeries("sensor_61");

	public TimeSeries sensor32TimeSeries = new TimeSeries("sensor_32");
	public TimeSeries sensor33TimeSeries = new TimeSeries("sensor_33");
	
	public TimeSeries toePressureTimeSeries = new TimeSeries("toePressure");
	public TimeSeries heelPressureTimeSeries = new TimeSeries("heelPressure");

	public FootPressureForceSensorDataDisplayModel() {

		long timeForRecord = 6 * 1000;

		sensor61TimeSeries.setMaximumItemAge(timeForRecord);
		sensor62TimeSeries.setMaximumItemAge(timeForRecord);
		sensor63TimeSeries.setMaximumItemAge(timeForRecord);
		sensor64TimeSeries.setMaximumItemAge(timeForRecord);
		
		sensor32TimeSeries.setMaximumItemAge(timeForRecord);
		sensor33TimeSeries.setMaximumItemAge(timeForRecord);

		toePressureTimeSeries.setMaximumItemAge(timeForRecord);
		heelPressureTimeSeries.setMaximumItemAge(timeForRecord);

	}
	
	/**
	 *	Function Info:
	 *		update time series 
	 */
	public void updateTimeSeries(IMUDataDecoder imuDataDecoder) {

		
	}
}
