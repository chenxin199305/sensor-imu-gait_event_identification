package CNNY.Xin.event;

import java.util.EventListener;

import CNNY.Xin.model.FootPressureForceSensorDataModel;

public interface FootPressureForceSensorDataUpdateEventListener extends EventListener {
	public void footPressureForceSensorDataUpdate(FootPressureForceSensorDataModel imuDataModel);
}
