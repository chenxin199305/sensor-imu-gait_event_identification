package CNNY.Xin.model;

import java.util.ArrayList;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;

/**
 *	Class Info:
 *		This class is the model to describe IMU data 
 */
public class IMUDataModel {

	/*
	 	Reference:	
		public byte ID;
        public UInt32 UID;
        public UInt32 Key32;
        public Int16[] AccRaw;
        public Int16[] AccCalibrated;
        public Int16[] AccFiltered;
        public Int16[] AccLinear;
        public Int16[] AccGravity;

        public Int16[] GyoRaw;
        public Int16[] GyoCalibrated;
        public Int16[] GyoFiltered;

        public Int16[] MagRaw;
        public Int16[] MagCalibrated;
        public Int16[] MagFiltered;

        public float[] EulerAngles;
        public float[] Quaternion;
        public float Pressure;
        public float Temperature;
        public byte[] AvailableItem;
        public string[] CsvHeader;
        public string[] CsvData;
        private string StringData;
	*/
	public Short ID;
	public Long	UID;
	public Long Key32;
	public Short[] AccRaw 			= new Short[3];
	public Short[] AccCalibrated 	= new Short[3];
	public Short[] AccFiltered 		= new Short[3];
	public Short[] AccLinear 		= new Short[3];
	public Short[] AccGravity 		= new Short[3];

	public Short[] GyoRaw 			= new Short[3];
	public Short[] GyoCalibrated 	= new Short[3];
	public Short[] GyoFiltered 		= new Short[3];

	public Short[] MagRaw 			= new Short[3];
	public Short[] MagCalibrated 	= new Short[3];
	public Short[] MagFiltered 		= new Short[3];
	
	public Float[] EulerAngles 		= new Float[3];
	public Float[] Quaternion 		= new Float[4];
	public Float Pressure;
	public Float Temperature;
	
	public ArrayList<Short> AvailableItem;
    public ArrayList<String> CsvHeader;
    public ArrayList<String> CsvData;
    
    public RegularTimePeriod receiveTime;
    
    public String descritpion;
    
    public String toString() {
    	
    	return descritpion;
    }
}
