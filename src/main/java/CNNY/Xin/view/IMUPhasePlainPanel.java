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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

public class IMUPhasePlainPanel extends JPanel {

	public JCheckBox checkBoxAcceleration;
	public JCheckBox checkBoxAngleVelocity;
	public JCheckBox checkBoxEulerAngle;
	
	public JTextField textFieldRecordLength;
	public JButton buttonStartStopRecord;
	public JButton buttonSaveToFile;
	
	public ChartPanel chartPanel;
	public JFreeChart chart;
	public XYSeriesCollection chartDataSet;
	public XYLineAndShapeRenderer lineAndShapeRenderer;

	/**
	 * Create the panel.
	 */
	public IMUPhasePlainPanel() {
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
		checkBoxEulerAngle.setEnabled(false);
		checkBoxEulerAngle.setBounds(252, 41, 119, 23);
		add(checkBoxEulerAngle);

		// chart panel Phase Plain
		chartPanel = new ChartPanel((JFreeChart) null);
		chartPanel.setBounds(10, 124, 384, 255);
		chartDataSet = new XYSeriesCollection();
		chart = ChartFactory.createXYLineChart("Phase Plain", "X(deg/s)", "Y(deg/s^2)", chartDataSet);
		chartPanel.setChart(chart);
		add(chartPanel);

		lineAndShapeRenderer = new XYLineAndShapeRenderer();
		chart.getXYPlot().setRenderer(lineAndShapeRenderer);
		
		JLabel labelRecordLength = new JLabel("RecordLen(c)");
		labelRecordLength.setBounds(10, 393, 90, 15);
		add(labelRecordLength);

		textFieldRecordLength = new JTextField();
		textFieldRecordLength.setColumns(10);
		textFieldRecordLength.setBounds(108, 390, 66, 21);
		add(textFieldRecordLength);
		textFieldRecordLength.setText("150");

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
	}

}
