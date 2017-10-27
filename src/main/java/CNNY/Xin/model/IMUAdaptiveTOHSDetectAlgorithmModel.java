package CNNY.Xin.model;

import java.util.ArrayList;

public class IMUAdaptiveTOHSDetectAlgorithmModel {

	// initial set value
	public Float initialThreshold = 0.0f;
	public Integer windowLength = 0;
	public Float alpha = 0.0f;
	public Float beta = 0.0f;
	
	public ArrayList<Float> eulerAngleOneStepBuffer = new ArrayList<>();
	public ArrayList<Float> eulerAngleWindowBuffer = new ArrayList<>();
	public ArrayList<Float> toeOffHeelStrikeBuffer = new ArrayList<>();
	public Integer toeOffHeelStrikeCount = 0;
	public Float toeOffHeelStrikeBufferAverageValue = 0.0f;
	
	public Float threshold = 0.0f;
	public Long stepLengthInMs = 0l;
	public Long lastStepTimeInMs = 0l;
	
	public Boolean newTOHSPointFindFlag = false;
	
	private String descritpion = "";

	public String toString() {
		
		descritpion = "thhold = " + threshold + "\t" + "stepLen = " + stepLengthInMs + "\n"
				+ "newTOHS = " + newTOHSPointFindFlag + "\t" + "TOHScount = " + toeOffHeelStrikeCount + "\n";
		
		return descritpion;
	}
}
