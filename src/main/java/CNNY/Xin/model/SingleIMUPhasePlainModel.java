package CNNY.Xin.model;

import org.jfree.data.xy.XYSeries;

public class SingleIMUPhasePlainModel {

	public XYSeries gyoXAxisXYSeries = new XYSeries("gyoX");
	public XYSeries gyoYAxisXYSeries = new XYSeries("gyoY");
	public XYSeries gyoZAxisXYSeries = new XYSeries("gyoZ");
	
	public XYSeries eulerAnglesXAxisXYSeries = new XYSeries("eulerX");
	public XYSeries eulerAnglesYAxisXYSeries = new XYSeries("eulerY");
	public XYSeries eulerAnglesZAxisXYSeries = new XYSeries("eulerZ");

	public Short[] LastGyoRaw 			= new Short[3];
	public Float[] LastEulerAngles 		= new Float[3];
	
	public SingleIMUPhasePlainModel() {

		int maximumCount = 200;

		gyoXAxisXYSeries.setMaximumItemCount(maximumCount);
		gyoYAxisXYSeries.setMaximumItemCount(maximumCount);
		gyoZAxisXYSeries.setMaximumItemCount(maximumCount);

		eulerAnglesXAxisXYSeries.setMaximumItemCount(maximumCount);
		eulerAnglesYAxisXYSeries.setMaximumItemCount(maximumCount);
		eulerAnglesZAxisXYSeries.setMaximumItemCount(maximumCount);

		LastGyoRaw[0] = 0;
		LastGyoRaw[1] = 0;
		LastGyoRaw[2] = 0;

		LastEulerAngles[0] = (float)0.0;
		LastEulerAngles[1] = (float)0.0;
		LastEulerAngles[2] = (float)0.0;
	}
}
