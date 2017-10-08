package CNNY.Xin.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import CNNY.Xin.model.FootPressureForceSensorDataModel;

public class FootPressureForceSensorDataUpdateEventManager {

	private Collection<FootPressureForceSensorDataUpdateEventListener> listeners;

	/**
	 *	Func Info:
	 *		add event listener
	 * 
	 *	@param listener
	 *   		DoorListener
	 */
	public void addListener(FootPressureForceSensorDataUpdateEventListener listener) {
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
	public void removeListener(FootPressureForceSensorDataUpdateEventListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	/**
	 * 通知所有的DoorListener
	 */
	public void notifyListeners(FootPressureForceSensorDataModel model) {
		Iterator<FootPressureForceSensorDataUpdateEventListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			FootPressureForceSensorDataUpdateEventListener listener = (FootPressureForceSensorDataUpdateEventListener) iter.next();
			listener.footPressureForceSensorDataUpdate(model);
		}
	}
}
