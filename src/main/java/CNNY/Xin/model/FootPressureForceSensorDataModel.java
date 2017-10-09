package CNNY.Xin.model;

/**
 *	Class Info:
 *		This class is the model to describe IMU data 
 */
public class FootPressureForceSensorDataModel {

	public Integer sensor11Value;
	public Integer sensor12Value;
	public Integer sensor13Value;
	public Integer sensor14Value;

	public Integer sensor22Value;
	public Integer sensor23Value;
	public Integer sensor24Value;

	public Integer sensor32Value;
	public Integer sensor33Value;

	public Integer sensor42Value;
	public Integer sensor43Value;

	public Integer sensor54Value;

	public Integer sensor61Value;
	public Integer sensor62Value;
	public Integer sensor63Value;
	public Integer sensor64Value;

	private String StringData;

	public String toString() {

		StringData = "sensor11Value = " + sensor11Value + "\t" + "sensor12Value = " + sensor12Value + "\n"
				+ "sensor13Value = " + sensor13Value + "\t" + "sensor14Value = " + sensor14Value + "\n"
				+ "sensor22Value = " + sensor22Value + "\t" + "sensor23Value = " + sensor23Value + "\n"
				+ "sensor24Value = " + sensor24Value + "\n"
				+ "sensor32Value = " + sensor32Value + "\t" + "sensor33Value = " + sensor33Value + "\n"
				+ "sensor42Value = " + sensor42Value + "\t" + "sensor43Value = " + sensor43Value + "\n"
				+ "sensor54Value = " + sensor54Value + "\t" + "\n"
				+ "sensor61Value = " + sensor61Value + "\t" + "sensor62Value = " + sensor62Value + "\n"
				+ "sensor63Value = " + sensor63Value + "\t" + "sensor64Value = " + sensor64Value + "\n";

		return StringData;
	}
}
