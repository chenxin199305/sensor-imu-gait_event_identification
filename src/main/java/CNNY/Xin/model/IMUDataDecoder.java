package CNNY.Xin.model;

import java.util.ArrayList;
import java.util.Date;

import org.jfree.data.time.Millisecond;

public class IMUDataDecoder {

	public IMUDataModel imuDataModel = new IMUDataModel();

	public IMUDataModel decode(ArrayList<Short> buf, Integer len)
	{
		ArrayList<Short> AvailableItem = new ArrayList<Short>();
		ArrayList<String> csv_headers = new ArrayList<String>();
		ArrayList<String> csv_data = new ArrayList<String>();
		String string_data = "";

		csv_headers.add("Time");
		csv_data.add(new Date().toGMTString());

		int offset = 0;
		while (offset < len)
		{
			Short cmd = buf.get(offset);
			switch (cmd)
			{
			case IMUItemID.kItemID:
				imuDataModel.ID = buf.get(offset + 1);
				offset += 2;
				AvailableItem.add(cmd);
				csv_headers.add("ID");
				csv_data.add(imuDataModel.ID.toString());
				string_data = "ID:" + imuDataModel.ID.toString() + "\r\n";
				break;

			case IMUItemID.kItemUID:
				imuDataModel.UID = (long)(buf.get(offset + 1) + (buf.get(offset + 2) << 8) + (buf.get(offset + 3) << 16) + (buf.get(offset + 4) << 24));
				offset += 5;
				AvailableItem.add(cmd);
				csv_headers.add("UID");
				csv_data.add(imuDataModel.UID.toString());
				string_data += "UID:" + "0x" + Long.toHexString(imuDataModel.UID) + "\r\n";
				break;

			case IMUItemID.kItemAccRaw:
				imuDataModel.AccRaw = new Short[3];
				imuDataModel.AccRaw[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.AccRaw[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.AccRaw[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("AccRaw, AccRawY, AccRawZ");
				csv_data.add(imuDataModel.AccRaw[0].toString() + ',' + imuDataModel.AccRaw[1].toString() + ',' + imuDataModel.AccRaw[2].toString());
				string_data += "加速度:" + imuDataModel.AccRaw[0].toString() + " " + imuDataModel.AccRaw[1].toString() + " " + imuDataModel.AccRaw[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemAccCalibrated:
				imuDataModel.AccCalibrated = new Short[3];
				imuDataModel.AccCalibrated[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.AccCalibrated[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.AccCalibrated[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("AccCalibratedX, AccCalibratedY, AccCalibratedZ");
				csv_data.add(imuDataModel.AccCalibrated[0].toString() + ',' + imuDataModel.AccCalibrated[1].toString() + ',' + imuDataModel.AccCalibrated[2].toString());
				string_data += "AccCalibrated:" + imuDataModel.AccCalibrated[0].toString() + " " + imuDataModel.AccCalibrated[1].toString() + " " + imuDataModel.AccCalibrated[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemAccFiltered:
				imuDataModel.AccFiltered = new Short[3];
				imuDataModel.AccFiltered[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.AccFiltered[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.AccFiltered[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("AccFilteredX, AccFilteredY, AccFilteredZ");
				csv_data.add(imuDataModel.AccFiltered[0].toString() + ',' + imuDataModel.AccFiltered[1].toString() + ',' + imuDataModel.AccFiltered[2].toString());
				string_data += "AccFiltered:" + imuDataModel.AccFiltered[0].toString() + " " + imuDataModel.AccFiltered[1].toString() + " " + imuDataModel.AccFiltered[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemAccLinear:
				imuDataModel.AccLinear = new Short[3];
				imuDataModel.AccLinear[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.AccLinear[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.AccLinear[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("AccLinearX, AccLinearY, AccLinearZ");
				csv_data.add(imuDataModel.AccLinear[0].toString() + ',' + imuDataModel.AccLinear[1].toString() + ',' + imuDataModel.AccLinear[2].toString());
				string_data += "AccLinear:" + imuDataModel.AccLinear[0].toString() + " " + imuDataModel.AccLinear[1].toString() + " " + imuDataModel.AccLinear[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemAccGravity:
				imuDataModel.AccGravity = new Short[3];
				imuDataModel.AccGravity[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.AccGravity[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.AccGravity[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("AccGravityX, AccGravityY, AccGravityZ");
				csv_data.add(imuDataModel.AccLinear[0].toString() + ',' + imuDataModel.AccLinear[1].toString() + ',' + imuDataModel.AccLinear[2].toString());
				string_data += "AccGravity:" + imuDataModel.AccGravity[0].toString() + " " + imuDataModel.AccGravity[1].toString() + " " + imuDataModel.AccGravity[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemGyoRaw:
				imuDataModel.GyoRaw = new Short[3];
				imuDataModel.GyoRaw[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.GyoRaw[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.GyoRaw[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("GyoRawX, GyoRawY, GyoRawZ");
				csv_data.add(imuDataModel.GyoRaw[0].toString() + ',' + imuDataModel.GyoRaw[1].toString() + ',' + imuDataModel.GyoRaw[2].toString());
				string_data += "角速度:" + imuDataModel.GyoRaw[0].toString() + " " + imuDataModel.GyoRaw[1].toString() + " " + imuDataModel.GyoRaw[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemGyoCalibrated:
				imuDataModel.GyoCalibrated = new Short[3];
				imuDataModel.GyoCalibrated[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.GyoCalibrated[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.GyoCalibrated[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("GyoCalibratedX, GyoCalibratedY, GyoCalibratedZ");
				csv_data.add(imuDataModel.GyoCalibrated[0].toString() + ',' + imuDataModel.GyoCalibrated[1].toString() + ',' + imuDataModel.GyoCalibrated[2].toString());
				string_data += "GyoCalibrated:" + imuDataModel.GyoCalibrated[0].toString() + " " + imuDataModel.GyoCalibrated[1].toString() + " " + imuDataModel.GyoCalibrated[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemGyoFiltered:
				imuDataModel.GyoFiltered = new Short[3];
				imuDataModel.GyoFiltered[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.GyoFiltered[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.GyoFiltered[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("GyoFilteredX, GyoFilteredY, GyoFilteredZ");
				csv_data.add(imuDataModel.GyoFiltered[0].toString() + ',' + imuDataModel.GyoFiltered[1].toString() + ',' + imuDataModel.GyoFiltered[2].toString());
				string_data += "GyoFiltered:" + imuDataModel.GyoFiltered[0].toString() + " " + imuDataModel.GyoFiltered[1].toString() + " " + imuDataModel.GyoFiltered[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemMagRaw:
				imuDataModel.MagRaw = new Short[3];
				imuDataModel.MagRaw[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.MagRaw[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.MagRaw[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("MagRawX, MagRawY, MagRawZ");
				csv_data.add(imuDataModel.MagRaw[0].toString() + ',' + imuDataModel.MagRaw[1].toString() + ',' + imuDataModel.MagRaw[2].toString());
				string_data += "地磁场:" + imuDataModel.MagRaw[0].toString() + " " + imuDataModel.MagRaw[1].toString() + " " + imuDataModel.MagRaw[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemMagCalibrated:
				imuDataModel.MagCalibrated = new Short[3];
				imuDataModel.MagCalibrated[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.MagCalibrated[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.MagCalibrated[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("MagCalibratedX, MagCalibratedY, MagCalibratedZ");
				csv_data.add(imuDataModel.MagCalibrated[0].toString() + ',' + imuDataModel.MagCalibrated[1].toString() + ',' + imuDataModel.MagCalibrated[2].toString());
				string_data += "MagCalibrated:" + imuDataModel.MagCalibrated[0].toString() + " " + imuDataModel.MagCalibrated[1].toString() + " " + imuDataModel.MagCalibrated[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemMagFiltered:
				imuDataModel.MagFiltered = new Short[3];
				imuDataModel.MagFiltered[0] = (short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8));
				imuDataModel.MagFiltered[1] = (short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8));
				imuDataModel.MagFiltered[2] = (short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8));
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("MagFilteredX, MagFilteredY, MagFilteredZ");
				csv_data.add(imuDataModel.MagFiltered[0].toString() + ',' + imuDataModel.MagFiltered[1].toString() + ',' + imuDataModel.MagFiltered[2].toString());
				string_data += "MagFiltered:" + imuDataModel.MagFiltered[0].toString() + " " + imuDataModel.MagFiltered[1].toString() + " " + imuDataModel.MagFiltered[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemRotationEuler:
				imuDataModel.EulerAngles = new Float[3];
				imuDataModel.EulerAngles[0] = (float)(short)(buf.get(offset + 1) + (buf.get(offset + 2) << 8)) / 100;
				imuDataModel.EulerAngles[1] = (float)(short)(buf.get(offset + 3) + (buf.get(offset + 4) << 8)) / 100;
				imuDataModel.EulerAngles[2] = (float)(short)(buf.get(offset + 5) + (buf.get(offset + 6) << 8)) / 10;
				offset += 7;
				AvailableItem.add(cmd);
				csv_headers.add("Pitch, Roll, Yaw");
				csv_data.add(imuDataModel.EulerAngles[0].toString() + ',' + imuDataModel.EulerAngles[1].toString() + ',' + imuDataModel.EulerAngles[2].toString());
				string_data += "Angles(PRY):" + imuDataModel.EulerAngles[0].toString() + " " + imuDataModel.EulerAngles[1].toString() + " " + imuDataModel.EulerAngles[2].toString() + "\r\n";
				break;
			case IMUItemID.kItemRotationEuler2:
				imuDataModel.EulerAngles = new Float[3];
				imuDataModel.EulerAngles[0] = (float)(buf.get(offset + 1 + 0 * 4) + (buf.get(offset + 2 + 0 * 4) << 8) + (buf.get(offset + 3 + 0 * 4) << 16) + (buf.get(offset + 4 + 0 * 4) << 24));
				imuDataModel.EulerAngles[1] = (float)(buf.get(offset + 1 + 1 * 4) + (buf.get(offset + 2 + 1 * 4) << 8) + (buf.get(offset + 3 + 1 * 4) << 16) + (buf.get(offset + 4 + 1 * 4) << 24));
				imuDataModel.EulerAngles[2] = (float)(buf.get(offset + 1 + 2 * 4) + (buf.get(offset + 2 + 2 * 4) << 8) + (buf.get(offset + 3 + 2 * 4) << 16) + (buf.get(offset + 4 + 2 * 4) << 24));
				offset += 13;
				AvailableItem.add(cmd);
				csv_headers.add("Pitch, Roll, Yaw");
				csv_data.add(imuDataModel.EulerAngles[0].toString() + ',' + imuDataModel.EulerAngles[1].toString() + ',' + imuDataModel.EulerAngles[2].toString());
				string_data += "Angles2(PRY):" + imuDataModel.EulerAngles[0].toString() + " " + imuDataModel.EulerAngles[1].toString() + " " + imuDataModel.EulerAngles[2].toString() + "\r\n";
				break;

			case IMUItemID.kItemRotationQuat:
				imuDataModel.Quaternion = new Float[4];
				imuDataModel.Quaternion[0] = (float)(buf.get(offset + 1 + 0 * 4) + (buf.get(offset + 2 + 0 * 4) << 8) + (buf.get(offset + 3 + 0 * 4) << 16) + (buf.get(offset + 4 + 0 * 4) << 24));
				imuDataModel.Quaternion[1] = (float)(buf.get(offset + 1 + 1 * 4) + (buf.get(offset + 2 + 1 * 4) << 8) + (buf.get(offset + 3 + 1 * 4) << 16) + (buf.get(offset + 4 + 1 * 4) << 24));
				imuDataModel.Quaternion[2] = (float)(buf.get(offset + 1 + 2 * 4) + (buf.get(offset + 2 + 2 * 4) << 8) + (buf.get(offset + 3 + 2 * 4) << 16) + (buf.get(offset + 4 + 2 * 4) << 24));
				imuDataModel.Quaternion[3] = (float)(buf.get(offset + 1 + 3 * 4) + (buf.get(offset + 2 + 3 * 4) << 8) + (buf.get(offset + 3 + 3 * 4) << 16) + (buf.get(offset + 4 + 3 * 4) << 24));
				offset += 17;
				AvailableItem.add(cmd);
				csv_headers.add("Q(w), Q(x), Q(y), Q(z)");
				csv_data.add(imuDataModel.Quaternion[0].toString() + ',' + imuDataModel.Quaternion[1].toString() + ',' + imuDataModel.Quaternion[2].toString() + ',' + imuDataModel.Quaternion[3].toString());
				string_data += "Q(WXYZ):" + imuDataModel.Quaternion[0].toString() + " " + imuDataModel.Quaternion[1].toString() + " " + imuDataModel.Quaternion[2].toString() + " " + imuDataModel.Quaternion[3].toString() + "\r\n";
				break;

			case IMUItemID.kItemTemperature:
				imuDataModel.Temperature = (float)(buf.get(offset + 1 + 0 * 4) + (buf.get(offset + 2 + 0 * 4) << 8) + (buf.get(offset + 3 + 0 * 4) << 16) + (buf.get(offset + 4 + 0 * 4) << 24));
				offset += 5;
				AvailableItem.add(cmd);
				csv_headers.add("Temperature");
				csv_data.add(imuDataModel.Temperature.toString());
				string_data += "Temperature:" + imuDataModel.Temperature.toString() + "\r\n";
				break;

			case IMUItemID.kItemPressure:
				imuDataModel.Pressure = (float)(buf.get(offset + 1 + 0 * 4) + (buf.get(offset + 2 + 0 * 4) << 8) + (buf.get(offset + 3 + 0 * 4) << 16) + (buf.get(offset + 4 + 0 * 4) << 24));
				offset += 5;
				AvailableItem.add(cmd);
				csv_headers.add("Pressure");
				csv_data.add(imuDataModel.Pressure.toString());
				string_data += "Pressure:" + imuDataModel.Pressure.toString() + "\r\n";
				break;
			case IMUItemID.kItemKey32:
				imuDataModel.Key32 = (long)(buf.get(offset + 1 + 0 * 4) + (buf.get(offset + 2 + 0 * 4) << 8) + (buf.get(offset + 3 + 0 * 4) << 16) + (buf.get(offset + 4 + 0 * 4) << 24));
				offset += 5;
				AvailableItem.add(cmd);
				csv_headers.add("Key32");
				csv_data.add(imuDataModel.Key32.toString());
				string_data += "Key32:" + "0x" + Long.toHexString(imuDataModel.Key32) + "\r\n";
				break;
			default:
				// error has been occured. may be a unspported Items
				//   if (Enum.IsDefined(typeof(IMUData.DataID), (IMUData.DataID)cmd))
				offset++;
				break;
			}
		}

		imuDataModel.descritpion = string_data;
		imuDataModel.AvailableItem = AvailableItem;
		imuDataModel.CsvHeader = csv_headers;
		imuDataModel.CsvData = csv_data;
	
		imuDataModel.receiveTime = new Millisecond();

		return imuDataModel;
	}
}
