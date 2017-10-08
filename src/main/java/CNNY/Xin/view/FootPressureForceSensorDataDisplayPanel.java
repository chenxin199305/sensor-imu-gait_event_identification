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

public class FootPressureForceSensorDataDisplayPanel extends JPanel {
	
	public JTextField textFieldRecordLength;
	
	public JCheckBox checkBoxSensor61;
	public JCheckBox checkBoxSensor62;
	public JCheckBox checkBoxSensor63;
	public JCheckBox checkBoxSensor64;
	public JCheckBox checkBoxSensor32;
	public JCheckBox checkBoxSensor33;
	public JCheckBox checkBoxToePressure;
	public JCheckBox checkBoxHeelPressure;
	
	public ChartPanel chartPanel;
	public JFreeChart chart;
	public TimeSeriesCollection chartDataSet;
	
	public JButton buttonStartStopRecord;
	public JButton buttonSaveToFile;
	
	/**
	 * Create the panel.
	 */
	public FootPressureForceSensorDataDisplayPanel() {
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "data display", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel lblContentSel = new JLabel("ContentSel:");
		lblContentSel.setBounds(10, 20, 100, 15);
		add(lblContentSel);
		
		checkBoxSensor61 = new JCheckBox("sensor61");
		checkBoxSensor61.setBounds(10, 41, 119, 23);
		add(checkBoxSensor61);
		
		checkBoxSensor62 = new JCheckBox("sensor62");
		checkBoxSensor62.setBounds(131, 41, 119, 23);
		add(checkBoxSensor62);
		
		checkBoxSensor63 = new JCheckBox("sensor63");
		checkBoxSensor63.setBounds(252, 41, 119, 23);
		add(checkBoxSensor63);
		
		checkBoxSensor64 = new JCheckBox("sensor64");
		checkBoxSensor64.setBounds(10, 66, 119, 23);
		add(checkBoxSensor64);
		
		checkBoxSensor32 = new JCheckBox("sensor32");
		checkBoxSensor32.setBounds(131, 66, 119, 23);
		add(checkBoxSensor32);
		
		checkBoxSensor33 = new JCheckBox("sensor33");
		checkBoxSensor33.setBounds(252, 66, 119, 23);
		add(checkBoxSensor33);
		
		checkBoxToePressure = new JCheckBox("toePressure");
		checkBoxToePressure.setBounds(10, 91, 119, 23);
		add(checkBoxToePressure);
		
		checkBoxHeelPressure = new JCheckBox("heelPressure");
		checkBoxHeelPressure.setBounds(131, 91, 119, 23);
		add(checkBoxHeelPressure);
		
		// chart panel IMU
		chartPanel = new ChartPanel(null);
		chartPanel.setBounds(10, 124, 384, 255);
		chartDataSet = new TimeSeriesCollection();
		chart 	= ChartFactory.createTimeSeriesChart("Foot Pressure", "TIME", "VAL", chartDataSet);
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
	}
}
