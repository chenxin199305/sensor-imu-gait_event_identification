package CNNY.Xin.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

public class SingleIMUPhasePlainPanel extends JPanel {

	public JCheckBox checkBoxAcceleration;
	public JCheckBox checkBoxAngleVelocity;
	public JCheckBox checkBoxEulerAngle;
	public JCheckBox checkBoxAccelerationFiltered;
	public JCheckBox checkBoxAngleVelocityFiltered;
	public JCheckBox checkBoxEulerAngleFiltered;
	
	public JTextField textFieldRecordLength;
	public JButton buttonStartStopRecord;
	public JButton buttonSaveToFile;
	
	public ChartPanel chartPanel;
	public JFreeChart chart;
	public XYSeriesCollection chartDataSet;

	/**
	 * Create the panel.
	 */
	public SingleIMUPhasePlainPanel() {
		setBorder(new TitledBorder(null, "PhasePlain", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JLabel label = new JLabel("ContentSel:");
		label.setBounds(10, 20, 100, 15);
		add(label);

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

		// chart panel Phase Plain
		chartPanel = new ChartPanel((JFreeChart) null);
		chartPanel.setBounds(10, 124, 384, 255);
		chartDataSet = new XYSeriesCollection();
		chart = ChartFactory.createXYLineChart("Phase Plain", "X(deg)", "Y(deg/s)", chartDataSet);
		chartPanel.setChart(chart);
		add(chartPanel);
		
		JLabel labelRecordLength = new JLabel("RecordLen(s)");
		labelRecordLength.setBounds(10, 393, 90, 15);
		add(labelRecordLength);

		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setColumns(10);
		textFieldRecordLength.setBounds(108, 390, 66, 21);
		add(textFieldRecordLength);

		buttonStartStopRecord = new JButton("StopRecord");
		buttonStartStopRecord.setBounds(184, 389, 100, 23);
		add(buttonStartStopRecord);

		buttonSaveToFile = new JButton("SaveToFile");
		buttonSaveToFile.setBounds(294, 389, 100, 23);
		add(buttonSaveToFile);

		panelConfig();
	}

	private void panelConfig() {

		checkBoxAcceleration.setEnabled(false);
		checkBoxAccelerationFiltered.setEnabled(false);
		checkBoxAngleVelocityFiltered.setEnabled(false);
		checkBoxEulerAngleFiltered.setEnabled(false);
	}

}
