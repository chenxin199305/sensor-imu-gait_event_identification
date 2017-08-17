package CNNY.Xin.model;

import java.util.ArrayList;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import jssc.SerialPort;

public class SingleIMUStatusModel {

	public SerialPort serialPort;
	public ArrayList<Short> serialPortBufferData = new ArrayList<>();
	public IMUDataDecoder imuDataDecoder = new IMUDataDecoder();
	public IMUFrameDecoder imuFrameDecoder = new IMUFrameDecoder(imuDataDecoder);

	// char panel data
	public TimeSeries accRawXAxisTimeSeries = new TimeSeries("accRawX");
	public TimeSeries accRawYAxisTimeSeries = new TimeSeries("accRawY");
	public TimeSeries accRawZAxisTimeSeries = new TimeSeries("accRawZ");
	public TimeSeries accCalibratedXAxisTimeSeries = new TimeSeries("accCalX");
	public TimeSeries accCalibratedYAxisTimeSeries = new TimeSeries("accCalY");
	public TimeSeries accCalibratedZAxisTimeSeries = new TimeSeries("accCalZ");
	public TimeSeries accFilteredXAxisTimeSeries = new TimeSeries("accFilX");
	public TimeSeries accFilteredYAxisTimeSeries = new TimeSeries("accFilY");
	public TimeSeries accFilteredZAxisTimeSeries = new TimeSeries("accRawZ");
	public TimeSeries accLinearXAxisTimeSeries = new TimeSeries("accLinX");
	public TimeSeries accLinearYAxisTimeSeries = new TimeSeries("accLinY");
	public TimeSeries accLinearZAxisTimeSeries = new TimeSeries("accLinZ");
	public TimeSeries accGravityXAxisTimeSeries = new TimeSeries("accGraX");
	public TimeSeries accGravityYAxisTimeSeries = new TimeSeries("accGraY");
	public TimeSeries accGravityZAxisTimeSeries = new TimeSeries("accGraZ");

	public TimeSeries gyoRawXAxisTimeSeries = new TimeSeries("gyoRawX");
	public TimeSeries gyoRawYAxisTimeSeries = new TimeSeries("gyoRawY");
	public TimeSeries gyoRawZAxisTimeSeries = new TimeSeries("gyoRawZ");
	public TimeSeries gyoCalibratedXAxisTimeSeries = new TimeSeries("gyoCalX");
	public TimeSeries gyoCalibratedYAxisTimeSeries = new TimeSeries("gyoCalY");
	public TimeSeries gyoCalibratedZAxisTimeSeries = new TimeSeries("gyoCalZ");
	public TimeSeries gyoFilteredXAxisTimeSeries = new TimeSeries("gyoFilX");
	public TimeSeries gyoFilteredYAxisTimeSeries = new TimeSeries("gyoFilY");
	public TimeSeries gyoFilteredZAxisTimeSeries = new TimeSeries("gyoFilX");

	public TimeSeries magRawXAxisTimeSeries = new TimeSeries("magRawX");
	public TimeSeries magRawYAxisTimeSeries = new TimeSeries("magRawY");
	public TimeSeries magRawZAxisTimeSeries = new TimeSeries("magRawZ");
	public TimeSeries magCalibratedXAxisTimeSeries = new TimeSeries("magCalX");
	public TimeSeries magCalibratedYAxisTimeSeries = new TimeSeries("magCalY");
	public TimeSeries magCalibratedZAxisTimeSeries = new TimeSeries("magCalZ");
	public TimeSeries magFilteredXAxisTimeSeries = new TimeSeries("magFilX");
	public TimeSeries magFilteredYAxisTimeSeries = new TimeSeries("magFilY");
	public TimeSeries magFilteredZAxisTimeSeries = new TimeSeries("magFilZ");

	public TimeSeries eulerAnglesXAxisTimeSeries = new TimeSeries("eulerX");
	public TimeSeries eulerAnglesYAxisTimeSeries = new TimeSeries("eulerY");
	public TimeSeries eulerAnglesZAxisTimeSeries = new TimeSeries("eulerZ");
	public TimeSeries quaternion1AxisTimeSeries = new TimeSeries("quaternion1");
	public TimeSeries quaternion2AxisTimeSeries = new TimeSeries("quaternion2");
	public TimeSeries quaternion3AxisTimeSeries = new TimeSeries("quaternion3");
	public TimeSeries quaternion4AxisTimeSeries = new TimeSeries("quaternion4");
	
	public TimeSeries pressureAxisTimeSeries = new TimeSeries("pressure");
	public TimeSeries temperatureAxisTimeSeries = new TimeSeries("temperature");
	
	public SingleIMUStatusModel() {

		long timeForRecord = 1 * 1000;

		accRawXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accRawYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accRawZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accCalibratedXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accCalibratedYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accCalibratedZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accFilteredXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accFilteredYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accFilteredZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accLinearXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accLinearYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accLinearZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accGravityXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accGravityYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		accGravityZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		gyoRawXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoRawYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoRawZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoCalibratedXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoCalibratedYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoCalibratedZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoFilteredXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoFilteredYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		gyoFilteredZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		magRawXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magRawYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magRawZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magCalibratedXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magCalibratedYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magCalibratedZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magFilteredXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magFilteredYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		magFilteredZAxisTimeSeries.setMaximumItemAge(timeForRecord);

		eulerAnglesXAxisTimeSeries.setMaximumItemAge(timeForRecord);
		eulerAnglesYAxisTimeSeries.setMaximumItemAge(timeForRecord);
		eulerAnglesZAxisTimeSeries.setMaximumItemAge(timeForRecord);
		quaternion1AxisTimeSeries.setMaximumItemAge(timeForRecord);
		quaternion2AxisTimeSeries.setMaximumItemAge(timeForRecord);
		quaternion3AxisTimeSeries.setMaximumItemAge(timeForRecord);
		quaternion4AxisTimeSeries.setMaximumItemAge(timeForRecord);

		pressureAxisTimeSeries.setMaximumItemAge(timeForRecord);
		temperatureAxisTimeSeries.setMaximumItemAge(timeForRecord);
	}
	
	/**
	 *	Function Info:
	 *		update time series 
	 */
	public void updateTimeSeries() {
		
		final Millisecond millisecond = new Millisecond();

		accRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.AccRaw[0]);
		accRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.AccRaw[1]);
		accRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.AccRaw[2]);

		gyoRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.GyoRaw[0]);
		gyoRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.GyoRaw[1]);
		gyoRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.GyoRaw[2]);

		magRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.MagRaw[0]);
		magRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.MagRaw[1]);
		magRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.MagRaw[2]);

		eulerAnglesXAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.EulerAngles[0]);
		eulerAnglesYAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.EulerAngles[1]);
		eulerAnglesZAxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.EulerAngles[2]);

		quaternion1AxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.Quaternion[0]);
		quaternion2AxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.Quaternion[1]);
		quaternion3AxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.Quaternion[2]);
		quaternion4AxisTimeSeries.addOrUpdate(millisecond, imuDataDecoder.imuDataModel.Quaternion[3]);
	}
	
	/**
	 *	Function Info:
	 *		update time series from imu data model 
	 */
	public void updateTimeSeries(IMUDataModel imuDataModel) {
		
		final Millisecond millisecond = new Millisecond();

		accRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.AccRaw[0]);
		accRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.AccRaw[1]);
		accRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.AccRaw[2]);

		gyoRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.GyoRaw[0]);
		gyoRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.GyoRaw[1]);
		gyoRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.GyoRaw[2]);

		magRawXAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.MagRaw[0]);
		magRawYAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.MagRaw[1]);
		magRawZAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.MagRaw[2]);

		eulerAnglesXAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.EulerAngles[0]);
		eulerAnglesYAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.EulerAngles[1]);
		eulerAnglesZAxisTimeSeries.addOrUpdate(millisecond, imuDataModel.EulerAngles[2]);

		quaternion1AxisTimeSeries.addOrUpdate(millisecond, imuDataModel.Quaternion[0]);
		quaternion2AxisTimeSeries.addOrUpdate(millisecond, imuDataModel.Quaternion[1]);
		quaternion3AxisTimeSeries.addOrUpdate(millisecond, imuDataModel.Quaternion[2]);
		quaternion4AxisTimeSeries.addOrUpdate(millisecond, imuDataModel.Quaternion[3]);
	}
}
