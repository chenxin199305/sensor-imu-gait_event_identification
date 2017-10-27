package CNNY.Xin.event;

import org.jfree.data.time.RegularTimePeriod;

public interface IMUPhasePlainEventListener {

	public void oneStepFinish(RegularTimePeriod receiveTime);
}
