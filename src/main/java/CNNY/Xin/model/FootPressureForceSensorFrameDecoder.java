package CNNY.Xin.model;

public class FootPressureForceSensorFrameDecoder {

    private FootPressureForceSensorDataDecoder dataDecoder;
    
    /**
     *	Info:
     *		Class Initiate 
     */
    public FootPressureForceSensorFrameDecoder(FootPressureForceSensorDataDecoder dataDecoder) {
    	this.dataDecoder = dataDecoder;
    }

	public boolean PacketDecode(Short short1) {

		return false;
	}
    
}
