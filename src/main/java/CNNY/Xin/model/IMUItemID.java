package CNNY.Xin.model;

public class IMUItemID {

	public static final short kItemKey32 = 0x80;   /* key status           size: 4 */
	public static final short kItemID = 0x90;   /* user programed ID    size: 1 */
	public static final short kItemUID = 0x91;   /* Unique ID            size: 4 */
	public static final short kItemIPAdress = 0x92;   /* ip address           size: 4 */
	public static final short kItemAccRaw = 0xA0;   /* raw acc              size: 3x2 */
	public static final short kItemAccCalibrated = 0xA1;
	public static final short kItemAccFiltered = 0xA2;
	public static final short kItemAccLinear = 0xA5;
	public static final short kItemAccGravity = 0xA6;
	public static final short kItemGyoRaw = 0xB0;   /* raw gyro             size: 3x2 */
	public static final short kItemGyoCalibrated = 0xB1;
	public static final short kItemGyoFiltered = 0xB2;
	public static final short kItemMagRaw = 0xC0;   /* raw mag              size: 3x2 */
	public static final short kItemMagCalibrated = 0xC1;
	public static final short kItemMagFiltered = 0xC2;
	public static final short kItemRotationEuler = 0xD0;   /* Euler angle          size:3x2 */
	public static final short kItemRotationEuler2 = 0xD9;   /* Euler angle2          size:3x4 */
	public static final short kItemRotationQuat = 0xD1;   /* att q;               size:4x4 */
	public static final short kItemTemperature = 0xE0;
	public static final short kItemPressure = 0xF0;   /* pressure             size:1x4 */
	public static final short kItemEnd = 0x00;   
}
