package CNNY.Xin.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class SingleIMUPanel extends JPanel {

	// content
	public SingleIMUSerialPortPanel serialPortPanel;
	
	// data display panel
	public SingleIMUDataDisplayPanel dataDisplayPanel;

	// Phase Plain Panel
	public SingleIMUPhasePlainPanel phasePlainPanel;
	
	/**
	 * Create the panel.
	 */
	public SingleIMUPanel() {
		setBorder(new TitledBorder(null, "IMU", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		// serial port panel
		serialPortPanel = new SingleIMUSerialPortPanel();
		serialPortPanel.setBounds(10, 17, 429, 176);
		add(serialPortPanel);
		
		// data display panel
		dataDisplayPanel = new SingleIMUDataDisplayPanel();
		dataDisplayPanel.setBounds(10, 203, 429, 422);
		add(dataDisplayPanel);
		
		// phase plain panel
		phasePlainPanel = new SingleIMUPhasePlainPanel();
		phasePlainPanel.setBounds(444, 203, 408, 422);
		add(phasePlainPanel);
	}
}
