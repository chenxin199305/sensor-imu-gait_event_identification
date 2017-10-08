package CNNY.Xin.event;

import java.util.EventListener;

import CNNY.Xin.model.IMUDataModel;

public interface FootPressureForceSensorDataUpdateEventListener extends EventListener {
	public void imuDataUpdate(IMUDataModel imuDataModel);
}
