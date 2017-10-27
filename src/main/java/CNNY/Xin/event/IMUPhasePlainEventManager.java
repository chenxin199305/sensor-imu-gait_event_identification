package CNNY.Xin.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.jfree.data.time.RegularTimePeriod;

import CNNY.Xin.controller.IMUController;
import CNNY.Xin.model.IMUDataModel;

public class IMUPhasePlainEventManager {

	private Collection<IMUPhasePlainEventListener> listeners;

	/**
	 *	Func Info:
	 *		add event listener
	 * 
	 *	@param imuController
	 *   		DoorListener
	 */
	public void addListener(IMUPhasePlainEventListener listener) {
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
	public void removeListener(IMUPhasePlainEventListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	/**
	 * 通知所有的 Listener OneStepFinish
	 * @param receiveTime 
	 */
	public void notifyListenersOneStepFinish(RegularTimePeriod receiveTime) {
		Iterator<IMUPhasePlainEventListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			IMUPhasePlainEventListener listener = (IMUPhasePlainEventListener) iter.next();
			listener.oneStepFinish(receiveTime);
		}
	}
}
