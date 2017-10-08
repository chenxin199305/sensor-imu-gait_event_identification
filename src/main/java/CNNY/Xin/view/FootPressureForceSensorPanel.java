package CNNY.Xin.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

public class FootPressureForceSensorPanel extends JPanel {

	// content
	public FootPressureForceSensorSerialPortPanel serialPortPanel;
	
	// data display panel
	public FootPressureForceSensorDataDisplayPanel dataDisplayPanel;

	/**
	 * Create the panel.
	 */
	public FootPressureForceSensorPanel() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Foot Pressure Force Sensor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		// serial port panel
		serialPortPanel = new FootPressureForceSensorSerialPortPanel();
		serialPortPanel.setBounds(10, 17, 429, 176);
		add(serialPortPanel);
		
		// data display panel
		dataDisplayPanel = new FootPressureForceSensorDataDisplayPanel();
		dataDisplayPanel.setBounds(10, 203, 429, 422);
		add(dataDisplayPanel);
	}
}
