package CNNY.Xin.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class SingleIMUStatusPanel extends JPanel {
	
	public JTextField textFieldRecordLength;
	public JComboBox<String> comboBoxSerialPortSelection;
	public JButton btnConnect;
	public JCheckBox chckbxAcceleration;
	public JCheckBox chckbxAngleVelocity;
	public JCheckBox chckbxMagnetic;
	public JCheckBox chckbxEulerAngle;
	public JCheckBox chckbxAirPressure;
	public JTextArea textAreaContentVal;
	private JPanel panelContentValForChart;
	private ChartPanel chartPanelContentVal;
	public JFreeChart chartContentVal;
	public JButton btnStartRecord;
	public JButton btnSaveToFile;
	/**
	 * Create the panel.
	 */
	public SingleIMUStatusPanel() {
		
		setBorder(new TitledBorder(null, "IMU", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JLabel lblSerialPortSelect = new JLabel("SerialPortSel:");
		lblSerialPortSelect.setBounds(21, 32, 100, 15);
		add(lblSerialPortSelect);
		
		comboBoxSerialPortSelection = new JComboBox<String>();
		comboBoxSerialPortSelection.setBounds(131, 29, 90, 21);
		add(comboBoxSerialPortSelection);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(247, 28, 93, 23);
		add(btnConnect);
		
		JLabel lblContentSel = new JLabel("ContentSel:");
		lblContentSel.setBounds(21, 61, 100, 15);
		add(lblContentSel);
		
		chckbxAcceleration = new JCheckBox("Acceleration");
		chckbxAcceleration.setSelected(true);
		chckbxAcceleration.setBounds(21, 82, 119, 23);
		add(chckbxAcceleration);
		
		chckbxAngleVelocity = new JCheckBox("Angle Velocity");
		chckbxAngleVelocity.setSelected(true);
		chckbxAngleVelocity.setBounds(21, 107, 119, 23);
		add(chckbxAngleVelocity);
		
		chckbxMagnetic = new JCheckBox("Magnetic");
		chckbxMagnetic.setBounds(142, 82, 103, 23);
		add(chckbxMagnetic);
		
		chckbxEulerAngle = new JCheckBox("Euler Angle");
		chckbxEulerAngle.setSelected(true);
		chckbxEulerAngle.setBounds(142, 107, 103, 23);
		add(chckbxEulerAngle);
		
		chckbxAirPressure = new JCheckBox("Air Pressure");
		chckbxAirPressure.setBounds(247, 82, 103, 23);
		add(chckbxAirPressure);
		
		JLabel lblContentval = new JLabel("ContentVal:");
		lblContentval.setBounds(21, 136, 100, 15);
		add(lblContentval);
		
		textAreaContentVal = new JTextArea();
		textAreaContentVal.setEditable(false);
		textAreaContentVal.setBounds(21, 161, 384, 117);
		add(textAreaContentVal);
		
		panelContentValForChart = new JPanel();
		panelContentValForChart.setBounds(21, 288, 384, 197);
		add(panelContentValForChart);
		panelContentValForChart.setLayout(null);
		
		// chart panel
		chartPanelContentVal = new ChartPanel(null);
		chartPanelContentVal.setBounds(
				0, 
				0, 
				panelContentValForChart.getWidth(), 
				panelContentValForChart.getHeight());
		panelContentValForChart.add(chartPanelContentVal);
		
		chartContentVal 	= ChartFactory.createXYLineChart("IMU", "TIME", "VAL", null);
		chartPanelContentVal.setChart(chartContentVal);
		
		JLabel lblRecordLens = new JLabel("RecordLen(s)");
		lblRecordLens.setBounds(21, 495, 90, 15);
		add(lblRecordLens);
		
		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setBounds(119, 492, 66, 21);
		add(textFieldRecordLength);
		textFieldRecordLength.setColumns(10);
		
		btnStartRecord = new JButton("StopRecord");
		btnStartRecord.setBounds(195, 491, 100, 23);
		add(btnStartRecord);
		
		btnSaveToFile = new JButton("SaveToFile");
		btnSaveToFile.setBounds(305, 491, 100, 23);
		add(btnSaveToFile);

	}
}
