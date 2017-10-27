package CNNY.Xin.model;

import org.jfree.data.time.TimeSeries;

public class FootPressureForceSensorDataDisplayModel {

	public Long recordTimeLengthInMS;
	
	// char panel data
	public TimeSeries sensor61TimeSeries = new TimeSeries("sensor_61");
	public TimeSeries sensor62TimeSeries = new TimeSeries("sensor_62");
	public TimeSeries sensor63TimeSeries = new TimeSeries("sensor_63");
	public TimeSeries sensor64TimeSeries = new TimeSeries("sensor_64");

	public TimeSeries sensor32TimeSeries = new TimeSeries("sensor_32");
	public TimeSeries sensor33TimeSeries = new TimeSeries("sensor_33");
	
	public TimeSeries toePressureTimeSeries = new TimeSeries("toePressure");
	public TimeSeries heelPressureTimeSeries = new TimeSeries("heelPressure");

	public FootPressureForceSensorDataDisplayModel() {

		setRecordTimeLengthInS(5);
	}

	public void setRecordTimeLengthInS(Integer recordTimeLengthInS) {
		
		Long recordTimeLengthInMS = (long) (recordTimeLengthInS * 1000);
		this.recordTimeLengthInMS = recordTimeLengthInMS;
		
		sensor61TimeSeries.setMaximumItemAge(recordTimeLengthInMS);
		sensor62TimeSeries.setMaximumItemAge(recordTimeLengthInMS);
		sensor63TimeSeries.setMaximumItemAge(recordTimeLengthInMS);
		sensor64TimeSeries.setMaximumItemAge(recordTimeLengthInMS);
		
		sensor32TimeSeries.setMaximumItemAge(recordTimeLengthInMS);
		sensor33TimeSeries.setMaximumItemAge(recordTimeLengthInMS);

		toePressureTimeSeries.setMaximumItemAge(recordTimeLengthInMS);
		heelPressureTimeSeries.setMaximumItemAge(recordTimeLengthInMS);
	}
}
