package CNNY.Xin.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import CNNY.Xin.model.IMUTOHSDataModel;

public class IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventManager {

	private Collection<IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener> listeners;

	/**
	 *	Func Info:
	 *		add event listener
	 * 
	 *	@param listener
	 *   		DoorListener
	 */
	public void addListener(IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener listener) {
		if (listeners == null) {
			listeners = new HashSet<>();
		}
		listeners.add(listener);
	}

	/**
	 * 移除事件
	 * 
	 * @param listener
	 *            DoorListener
	 */
	public void removeListener(IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	/**
	 * 通知所有的DoorListener
	 */
	public void notifyListeners(IMUTOHSDataModel imutohsDataModel) {
		Iterator<IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener listener = (IMUAdaptiveTOHSDetectAlgorithmDataUpdateEventListener) iter.next();
			listener.newTOHSPointFound(imutohsDataModel);;
		}
	}
}
