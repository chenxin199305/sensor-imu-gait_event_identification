package CNNY.Xin.event;

import CNNY.Xin.model.IMUTOHSDataModel;

public interface IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener {

	public void newTOHSPointFound(IMUTOHSDataModel imutohsDataModel);
}
