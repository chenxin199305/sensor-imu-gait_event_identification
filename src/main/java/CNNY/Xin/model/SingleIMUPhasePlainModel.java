package CNNY.Xin.model;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;

public class SingleIMUPhasePlainModel {

	public ArrayList<Double> gyoXAxisArray = new ArrayList();
	public ArrayList<Double> gyoYAxisArray = new ArrayList();
	public ArrayList<Double> gyoZAxisArray = new ArrayList();
	
	public ArrayList<Double> eulerAngleXAxisArray = new ArrayList();
	public ArrayList<Double> eulerAngleYAxisArray = new ArrayList();
	public ArrayList<Double> eulerAngleZAxisArray = new ArrayList();
	
	public XYSeries gyoXAxisXYSeries = new XYSeries("gyoX");
	public XYSeries gyoYAxisXYSeries = new XYSeries("gyoY");
	public XYSeries gyoZAxisXYSeries = new XYSeries("gyoZ");
	
	public XYSeries eulerAnglesXAxisXYSeries = new XYSeries("eulerX");
	public XYSeries eulerAnglesYAxisXYSeries = new XYSeries("eulerY");
	public XYSeries eulerAnglesZAxisXYSeries = new XYSeries("eulerZ");

	public int seriesMaximumCount = 200;
	
	public double[] LastGyoRaw 			= new double[3];
	public double[] LastEulerAngles 	= new double[3];
	
	public SingleIMUPhasePlainModel() {

		gyoXAxisXYSeries.setMaximumItemCount(seriesMaximumCount);
		gyoYAxisXYSeries.setMaximumItemCount(seriesMaximumCount);
		gyoZAxisXYSeries.setMaximumItemCount(seriesMaximumCount);

		eulerAnglesXAxisXYSeries.setMaximumItemCount(seriesMaximumCount);
		eulerAnglesYAxisXYSeries.setMaximumItemCount(seriesMaximumCount);
		eulerAnglesZAxisXYSeries.setMaximumItemCount(seriesMaximumCount);

		LastGyoRaw[0] = 0;
		LastGyoRaw[1] = 0;
		LastGyoRaw[2] = 0;

		LastEulerAngles[0] = (float)0.0;
		LastEulerAngles[1] = (float)0.0;
		LastEulerAngles[2] = (float)0.0;
	}
}
