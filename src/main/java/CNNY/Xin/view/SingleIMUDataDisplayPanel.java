package CNNY.Xin.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;

public class SingleIMUDataDisplayPanel extends JPanel {
	
	public JTextField textFieldRecordLength;
	
	public JCheckBox checkBoxAcceleration;
	public JCheckBox checkBoxAngleVelocity;
	public JCheckBox checkBoxEulerAngle;
	public JCheckBox checkBoxAccelerationFiltered;
	public JCheckBox checkBoxAngleVelocityFiltered;
	public JCheckBox checkBoxEulerAngleFiltered;
	public JCheckBox checkBoxToeOffHeelHitDetect;
	public JCheckBox checkBoxDebug;
	
	public ChartPanel chartPanel;
	public JFreeChart chart;
	public TimeSeriesCollection chartDataSet;
	
	public JButton buttonStartStopRecord;
	public JButton buttonSaveToFile;
	
	/**
	 * Create the panel.
	 */
	public SingleIMUDataDisplayPanel() {
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "data display", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel lblContentSel = new JLabel("ContentSel:");
		lblContentSel.setBounds(10, 20, 100, 15);
		add(lblContentSel);
		
		checkBoxAcceleration = new JCheckBox("Acceleration");
		checkBoxAcceleration.setBounds(10, 41, 119, 23);
		add(checkBoxAcceleration);
		
		checkBoxAngleVelocity = new JCheckBox("Angle Velocity");
		checkBoxAngleVelocity.setBounds(131, 41, 119, 23);
		add(checkBoxAngleVelocity);
		
		checkBoxEulerAngle = new JCheckBox("Euler Angle");
		checkBoxEulerAngle.setBounds(252, 41, 119, 23);
		add(checkBoxEulerAngle);
		
		checkBoxAccelerationFiltered = new JCheckBox("Acc Filtered");
		checkBoxAccelerationFiltered.setBounds(10, 66, 119, 23);
		add(checkBoxAccelerationFiltered);
		
		checkBoxAngleVelocityFiltered = new JCheckBox("AngVel Filtered");
		checkBoxAngleVelocityFiltered.setBounds(131, 66, 119, 23);
		add(checkBoxAngleVelocityFiltered);
		
		checkBoxEulerAngleFiltered = new JCheckBox("EulAng Filtered");
		checkBoxEulerAngleFiltered.setBounds(252, 66, 119, 23);
		add(checkBoxEulerAngleFiltered);
		
		checkBoxToeOffHeelHitDetect = new JCheckBox("Toe-Off Heel-Hit Detect");
		checkBoxToeOffHeelHitDetect.setBounds(10, 91, 240, 23);
		add(checkBoxToeOffHeelHitDetect);
		
		checkBoxDebug = new JCheckBox("debug");
		checkBoxDebug.setBounds(252, 91, 103, 23);
		add(checkBoxDebug);
		
		// chart panel IMU
		chartPanel = new ChartPanel(null);
		chartPanel.setBounds(10, 124, 384, 255);
		chartDataSet = new TimeSeriesCollection();
		chart 	= ChartFactory.createTimeSeriesChart("IMU", "TIME", "VAL", chartDataSet);
		chartPanel.setChart(chart);
		add(chartPanel);
		
		JLabel lblRecordLens = new JLabel("RecordLen(s)");
		lblRecordLens.setBounds(10, 393, 90, 15);
		add(lblRecordLens);
		
		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setBounds(108, 390, 66, 21);
		add(textFieldRecordLength);
		textFieldRecordLength.setColumns(10);
		
		buttonStartStopRecord = new JButton("StopRecord");
		buttonStartStopRecord.setBounds(184, 389, 100, 23);
		add(buttonStartStopRecord);
		
		buttonSaveToFile = new JButton("SaveToFile");
		buttonSaveToFile.setBounds(294, 389, 100, 23);
		add(buttonSaveToFile);
		
		panelConfig();
	}

	private void panelConfig() {

		checkBoxDebug.setEnabled(false);
	}
}
