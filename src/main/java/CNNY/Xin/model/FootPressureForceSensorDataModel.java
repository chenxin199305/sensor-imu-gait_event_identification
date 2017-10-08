package CNNY.Xin.model;

import java.util.ArrayList;

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

    public String StringData;
    
    public String toString() {
    	
    	return StringData;
    }
}
