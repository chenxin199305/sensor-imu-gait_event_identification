package CNNY.Xin.model;

import java.util.ArrayList;

public class IMUFrameDecoder {

	private Integer MAX_PACKET_LEN = 128;
    private Integer DataPacketLen;
	private DecoderStatus status = DecoderStatus.kStatus_Idle;
	private Short command;
    private Integer DataCounter = 0;
    private ArrayList<Short> DataPacketBuffer = new ArrayList<>();
    private Integer CRCReceived;
    private Integer CRCCalculated;
    private IMUDataDecoder imuDataDecoder;
    
    /**
     *	Info:
     *		Class Initiate 
     */
    public IMUFrameDecoder(IMUDataDecoder imuDataDecoder) {
    	this.imuDataDecoder = imuDataDecoder;
    }
    
	/**
	 *	Info:
	 *		Decoder's status enumeration 
	 */
	private enum DecoderStatus {
		kStatus_Idle,
		kStatus_Cmd,
		kStatus_LenLow,
		kStatus_LenHigh,
		kStatus_CRCLow,
		kStatus_CRCHigh,
		kStatus_Data, 
	}

	/**
	 *	Function Info:
	 *		Frame decode main part
	 *
	 * 	@return	
	 * 		if frame exist, return imu data model
	 * 		else, return null
	 */
	public IMUDataModel PacketDecode(short c)
	{
		//Console.WriteLine(c.ToString("X"));
		switch (status)
		{
		case kStatus_Idle:
			if (c == 0x5A) {
				status = DecoderStatus.kStatus_Cmd;
			}
			break;
		case kStatus_Cmd:
			command = c;
			if(command == 0xA5) {
				status = DecoderStatus.kStatus_LenLow;
			}
			else {
				status = DecoderStatus.kStatus_Idle;
			}
			break;
		case kStatus_LenLow:
			DataPacketLen = (int) c;
			status = DecoderStatus.kStatus_LenHigh;
			break;
		case kStatus_LenHigh:
			DataPacketLen += (c << 8) & 0xFFFF;

			if (DataPacketLen > MAX_PACKET_LEN) {
				status = DecoderStatus.kStatus_Idle;
			}
			else {
				status = DecoderStatus.kStatus_CRCLow;
			}
			break;
		case kStatus_CRCLow:
			CRCReceived = (int) c;
			status = DecoderStatus.kStatus_CRCHigh;
			break;
		case kStatus_CRCHigh:
			CRCReceived += (c << 8) & 0xFFFF;
			DataCounter = 0;
			status = DecoderStatus.kStatus_Data;
			break;
		case kStatus_Data:
			if (DataPacketBuffer.size() != 256) {
				for (int i = 0; i < 256; i++) {
					DataPacketBuffer.add((short) 0);
				}
			}
			DataPacketBuffer.set(DataCounter++, c);

			if (DataCounter >= DataPacketLen) {
				ArrayList<Short> header = new ArrayList<Short>();
				header.add((short) 0x5A);
				header.add((short) 0xA5);
				header.add((short) ((DataPacketLen >> 0) & 0xFF));
				header.add((short) ((DataPacketLen >> 8) & 0xFF));
				CRCCalculated = crc16(0, header, 0, header.size(), 0x1021);
				CRCCalculated = crc16(CRCCalculated, DataPacketBuffer, 0, DataPacketLen, 0x1021);

				// CRC match, Kboot suffucally received a packet
				if (CRCCalculated == CRCReceived) {
					if (imuDataDecoder != null) {
						return imuDataDecoder.decode(DataPacketBuffer, DataPacketLen);
					}
				}
				status = DecoderStatus.kStatus_Idle;
			}
			break;
		}
		
		return null;
	}
	
	/**
	 *	Function Info: 
	 *		CRC check
	 */
    private int crc16(
    		int crc,
    		ArrayList<Short> data, 
    		int start, 
    		int length,
    		int poly)
    {
        while (length-- > 0) {
            short bt = data.get(start++);
            for (int i = 0; i < 8; i++) {
            	Boolean b1 = (crc & 0x8000) != 0;
            	Boolean b2 = (bt & 0x80) != 0;
                if (b1 != b2) {
                	crc = ((crc << 1) ^ poly);
                }
                else {
                	crc <<= 1;
                }
                bt <<= 1;
            }
        }
        return crc;
    }
}
