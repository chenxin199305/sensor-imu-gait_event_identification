package CNNY.Xin.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class FootPressureForceSensorSerialPortPanel extends JPanel {

	public JComboBox<String> comboBoxSerialPortSelection;
	public JButton buttonConnectDisconnect;
	public JTextArea textAreaContentVal;
	
	public FootPressureForceSensorSerialPortPanel() {

		setBorder(new TitledBorder(null, "serial port", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JLabel labelSerialPortSel = new JLabel("SerialPortSel:");
		labelSerialPortSel.setBounds(10, 24, 100, 15);
		add(labelSerialPortSel);
		
		comboBoxSerialPortSelection = new JComboBox<String>();
		comboBoxSerialPortSelection.setBounds(120, 21, 90, 21);
		add(comboBoxSerialPortSelection);
		
		buttonConnectDisconnect = new JButton("Connect");
		buttonConnectDisconnect.setBounds(236, 20, 93, 23);
		add(buttonConnectDisconnect);
		
		textAreaContentVal = new JTextArea();
		textAreaContentVal.setEditable(false);
		textAreaContentVal.setBounds(10, 49, 408, 117);
		add(textAreaContentVal);
	}

}
