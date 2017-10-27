package CNNY.Xin.model;

import org.jfree.data.time.RegularTimePeriod;

public class IMUTOHSDataModel {

	public static Integer TOHSTYPE_TO = 1;
	public static Integer TOHSTYPE_HS = 2;
	
	// initial set value
	public Integer type;
	public RegularTimePeriod time;
	public Float value;
	
	private String descritpion;
	
	public IMUTOHSDataModel(
			Integer type, RegularTimePeriod time, Float value) {
	
		this.type = type;
		this.time = time;
		this.value = value;
	}

	public String toString() {
		
		descritpion = "time = " + time + "\t" + "value = " + value + "\n";
		
		return descritpion;
	}
}
