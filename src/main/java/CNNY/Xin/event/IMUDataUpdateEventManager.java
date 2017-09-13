package CNNY.Xin.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import CNNY.Xin.model.IMUDataModel;

public class IMUDataUpdateEventManager {

	private Collection<IMUDataUpdateEventListener> listeners;

	/**
	 *	Func Info:
	 *		add event listener
	 * 
	 *	@param listener
	 *   		DoorListener
	 */
	public void addListener(IMUDataUpdateEventListener listener) {
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
	public void removeListener(IMUDataUpdateEventListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	/**
	 * 通知所有的DoorListener
	 */
	public void notifyListeners(IMUDataModel imuDataModel) {
		Iterator<IMUDataUpdateEventListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			IMUDataUpdateEventListener listener = (IMUDataUpdateEventListener) iter.next();
			listener.imuDataUpdate(imuDataModel);;
		}
	}
}
