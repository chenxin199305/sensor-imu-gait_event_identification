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
	public JCheckBox chckbxEulerAngle;
	public JCheckBox chckbxAccFiltered;
	public JCheckBox chckbxAngvelFiltered;
	public JCheckBox chckbxEulangFiltered;
	public JCheckBox chckbxToeOffHeelHitDetect;
	public JCheckBox chckbxDebug;
	
	public JTextArea textAreaContentVal;
	private JPanel panelContentValForChart;
	public ChartPanel chartPanelContentVal;
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
		chckbxAcceleration.setBounds(21, 82, 119, 23);
		add(chckbxAcceleration);
		
		chckbxAngleVelocity = new JCheckBox("Angle Velocity");
		chckbxAngleVelocity.setBounds(142, 82, 119, 23);
		add(chckbxAngleVelocity);
		
		chckbxEulerAngle = new JCheckBox("Euler Angle");
		chckbxEulerAngle.setBounds(263, 82, 119, 23);
		add(chckbxEulerAngle);
		
		chckbxAccFiltered = new JCheckBox("Acc Filtered");
		chckbxAccFiltered.setBounds(21, 107, 119, 23);
		add(chckbxAccFiltered);
		
		chckbxAngvelFiltered = new JCheckBox("AngVel Filtered");
		chckbxAngvelFiltered.setBounds(142, 107, 119, 23);
		add(chckbxAngvelFiltered);
		
		chckbxEulangFiltered = new JCheckBox("EulAng Filtered");
		chckbxEulangFiltered.setBounds(263, 107, 119, 23);
		add(chckbxEulangFiltered);
		
		textAreaContentVal = new JTextArea();
		textAreaContentVal.setEditable(false);
		textAreaContentVal.setBounds(21, 161, 384, 117);
		add(textAreaContentVal);
		
		panelContentValForChart = new JPanel();
		panelContentValForChart.setBounds(21, 288, 384, 255);
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
		lblRecordLens.setBounds(21, 557, 90, 15);
		add(lblRecordLens);
		
		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setBounds(119, 554, 66, 21);
		add(textFieldRecordLength);
		textFieldRecordLength.setColumns(10);
		
		btnStartRecord = new JButton("StopRecord");
		btnStartRecord.setBounds(195, 553, 100, 23);
		add(btnStartRecord);
		
		btnSaveToFile = new JButton("SaveToFile");
		btnSaveToFile.setBounds(305, 553, 100, 23);
		add(btnSaveToFile);
		
		chckbxToeOffHeelHitDetect = new JCheckBox("Toe-Off Heel-Hit Detect");
		chckbxToeOffHeelHitDetect.setBounds(21, 132, 103, 23);
		add(chckbxToeOffHeelHitDetect);
		
		chckbxDebug = new JCheckBox("debug");
		chckbxDebug.setBounds(263, 132, 103, 23);
		add(chckbxDebug);

	}
}
