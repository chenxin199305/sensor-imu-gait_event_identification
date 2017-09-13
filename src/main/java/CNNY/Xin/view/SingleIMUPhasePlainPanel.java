package CNNY.Xin.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SingleIMUPhasePlainPanel extends JPanel {

	private JTextField textFieldRecordLength;

	public ChartPanel chartPanel;
	public JFreeChart chart;

	/**
	 * Create the panel.
	 */
	public SingleIMUPhasePlainPanel() {
		setBorder(new TitledBorder(null, "PhasePlain", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JLabel label = new JLabel("ContentSel:");
		label.setBounds(10, 20, 100, 15);
		add(label);

		JCheckBox checkBoxAcceleration = new JCheckBox("Acceleration");
		checkBoxAcceleration.setBounds(10, 41, 119, 23);
		add(checkBoxAcceleration);

		JCheckBox checkBoxAngleVelocity = new JCheckBox("Angle Velocity");
		checkBoxAngleVelocity.setBounds(131, 41, 119, 23);
		add(checkBoxAngleVelocity);

		JCheckBox checkBoxEulerAngle = new JCheckBox("Euler Angle");
		checkBoxEulerAngle.setBounds(252, 41, 119, 23);
		add(checkBoxEulerAngle);

		JCheckBox checkBoxEulerAngleFiltered = new JCheckBox("EulAng Filtered");
		checkBoxEulerAngleFiltered.setBounds(252, 66, 119, 23);
		add(checkBoxEulerAngleFiltered);

		JCheckBox checkBoxAngleVelocityFiltered = new JCheckBox("AngVel Filtered");
		checkBoxAngleVelocityFiltered.setBounds(131, 66, 119, 23);
		add(checkBoxAngleVelocityFiltered);

		JCheckBox checkBoxToeOffHeelHitDetect = new JCheckBox("Toe-Off Heel-Hit Detect");
		checkBoxToeOffHeelHitDetect.setBounds(10, 91, 240, 23);
		add(checkBoxToeOffHeelHitDetect);

		JCheckBox checkBoxAccelerationFiltered = new JCheckBox("Acc Filtered");
		checkBoxAccelerationFiltered.setBounds(10, 66, 119, 23);
		add(checkBoxAccelerationFiltered);

		JCheckBox checkBoxDebug = new JCheckBox("debug");
		checkBoxDebug.setBounds(252, 91, 103, 23);
		add(checkBoxDebug);

		// chart panel Phase Plain
		chartPanel = new ChartPanel((JFreeChart) null);
		chartPanel.setBounds(10, 124, 384, 255);
		chart = ChartFactory.createXYLineChart("Phase Plain", "X(deg)", "Y(deg/s)", null);
		chartPanel.setChart(chart);
		add(chartPanel);
		
		JLabel labelRecordLength = new JLabel("RecordLen(s)");
		labelRecordLength.setBounds(10, 393, 90, 15);
		add(labelRecordLength);

		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setColumns(10);
		textFieldRecordLength.setBounds(108, 390, 66, 21);
		add(textFieldRecordLength);

		JButton buttonStartStopRecord = new JButton("StopRecord");
		buttonStartStopRecord.setBounds(184, 389, 100, 23);
		add(buttonStartStopRecord);

		JButton buttonSaveToFile = new JButton("SaveToFile");
		buttonSaveToFile.setBounds(294, 389, 100, 23);
		add(buttonSaveToFile);

	}

}
