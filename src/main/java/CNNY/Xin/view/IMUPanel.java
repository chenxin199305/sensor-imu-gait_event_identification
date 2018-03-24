package CNNY.Xin.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class IMUPanel extends JPanel {

	// content
	public IMUSerialPortPanel serialPortPanel;
	
	// data display panel
	public IMUDataDisplayPanel dataDisplayPanel;

	// phase plain panel
	public IMUPhasePlainPanel phasePlainPanel;
	
	// detect algorithm panel
	public IMUDetectAlgorithmPanel detectAlgorithmPanel;
	
	/**
	 * Create the panel.
	 */
	public IMUPanel() {
		setBorder(new TitledBorder(null, "IMU", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		// serial port panel
		serialPortPanel = new IMUSerialPortPanel();
		serialPortPanel.setBounds(10, 17, 429, 176);
		add(serialPortPanel);
		
		// data display panel
		dataDisplayPanel = new IMUDataDisplayPanel();
		dataDisplayPanel.setBounds(10, 203, 429, 422);
		add(dataDisplayPanel);
		
		// phase plain panel
		phasePlainPanel = new IMUPhasePlainPanel();
		phasePlainPanel.setBounds(444, 203, 408, 1);
		add(phasePlainPanel);
		
		// detect algorithm panel
		detectAlgorithmPanel = new IMUDetectAlgorithmPanel();
		detectAlgorithmPanel.tabbedPane.setBounds(10, 15, 386, 583);
		detectAlgorithmPanel.setBounds(449, 17, 403, 608);
		add(detectAlgorithmPanel);
	}
}
