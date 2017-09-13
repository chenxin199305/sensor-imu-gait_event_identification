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
import javax.swing.UIManager;
import java.awt.Color;

public class SingleIMUDataDisplayPanel extends JPanel {
	
	public JTextField textFieldRecordLength;
	
	public JCheckBox chckbxAcceleration;
	public JCheckBox chckbxAngleVelocity;
	public JCheckBox chckbxEulerAngle;
	public JCheckBox chckbxAccFiltered;
	public JCheckBox chckbxAngvelFiltered;
	public JCheckBox chckbxEulangFiltered;
	public JCheckBox chckbxToeOffHeelHitDetect;
	public JCheckBox chckbxDebug;
	
	public ChartPanel chartPanel;
	public JFreeChart chartIMU;
	public ChartPanel chartPanelPhasePlain;
	public JFreeChart chartPhasePlain;
	
	public JButton btnStartRecord;
	public JButton btnSaveToFile;
	
	/**
	 * Create the panel.
	 */
	public SingleIMUDataDisplayPanel() {
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "IMU data display", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel lblContentSel = new JLabel("ContentSel:");
		lblContentSel.setBounds(10, 20, 100, 15);
		add(lblContentSel);
		
		chckbxAcceleration = new JCheckBox("Acceleration");
		chckbxAcceleration.setBounds(10, 41, 119, 23);
		add(chckbxAcceleration);
		
		chckbxAngleVelocity = new JCheckBox("Angle Velocity");
		chckbxAngleVelocity.setBounds(131, 41, 119, 23);
		add(chckbxAngleVelocity);
		
		chckbxEulerAngle = new JCheckBox("Euler Angle");
		chckbxEulerAngle.setBounds(252, 41, 119, 23);
		add(chckbxEulerAngle);
		
		chckbxAccFiltered = new JCheckBox("Acc Filtered");
		chckbxAccFiltered.setBounds(10, 66, 119, 23);
		add(chckbxAccFiltered);
		
		chckbxAngvelFiltered = new JCheckBox("AngVel Filtered");
		chckbxAngvelFiltered.setBounds(131, 66, 119, 23);
		add(chckbxAngvelFiltered);
		
		chckbxEulangFiltered = new JCheckBox("EulAng Filtered");
		chckbxEulangFiltered.setBounds(252, 66, 119, 23);
		add(chckbxEulangFiltered);
		
		// chart panel IMU
		chartPanel = new ChartPanel(null);
		chartPanel.setBounds(10, 124, 384, 255);
		chartIMU 	= ChartFactory.createTimeSeriesChart("IMU", "TIME", "VAL", null);
		chartPanel.setChart(chartIMU);
		add(chartPanel);
		
		JLabel lblRecordLens = new JLabel("RecordLen(s)");
		lblRecordLens.setBounds(10, 393, 90, 15);
		add(lblRecordLens);
		
		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setBounds(108, 390, 66, 21);
		add(textFieldRecordLength);
		textFieldRecordLength.setColumns(10);
		
		btnStartRecord = new JButton("StopRecord");
		btnStartRecord.setBounds(184, 389, 100, 23);
		add(btnStartRecord);
		
		btnSaveToFile = new JButton("SaveToFile");
		btnSaveToFile.setBounds(294, 389, 100, 23);
		add(btnSaveToFile);
		
		chckbxToeOffHeelHitDetect = new JCheckBox("Toe-Off Heel-Hit Detect");
		chckbxToeOffHeelHitDetect.setBounds(10, 91, 240, 23);
		add(chckbxToeOffHeelHitDetect);
		
		chckbxDebug = new JCheckBox("debug");
		chckbxDebug.setBounds(252, 91, 103, 23);
		add(chckbxDebug);
	}
}
