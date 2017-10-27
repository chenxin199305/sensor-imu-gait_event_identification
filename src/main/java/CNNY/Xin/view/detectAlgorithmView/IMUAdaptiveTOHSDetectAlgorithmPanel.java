package CNNY.Xin.view.detectAlgorithmView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IMUAdaptiveTOHSDetectAlgorithmPanel extends JPanel {

	public JTextField textFieldWindowLength;
	public JTextField textFieldInitialThreshold;
	public JTextField textFieldAlpha;
	public JTextField textFieldBeta;
	
	public JButton buttonStartStop;
	
	public JTextArea textArea;
	
	/**
	 * Create the panel.
	 */
	public IMUAdaptiveTOHSDetectAlgorithmPanel() {
		setLayout(null);
		
		JLabel lblInitialThreshold = new JLabel("thhold:");
		lblInitialThreshold.setBounds(146, 13, 50, 15);
		add(lblInitialThreshold);
		
		textFieldInitialThreshold = new JTextField();
		textFieldInitialThreshold.setText("0");
		textFieldInitialThreshold.setBounds(206, 10, 66, 21);
		add(textFieldInitialThreshold);
		textFieldInitialThreshold.setColumns(10);
		
		textFieldAlpha = new JTextField();
		textFieldAlpha.setText("0");
		textFieldAlpha.setBounds(70, 41, 66, 21);
		add(textFieldAlpha);
		textFieldAlpha.setColumns(10);
		
		textFieldBeta = new JTextField();
		textFieldBeta.setText("0");
		textFieldBeta.setBounds(206, 41, 66, 21);
		add(textFieldBeta);
		textFieldBeta.setColumns(10);
		
		buttonStartStop = new JButton("start");
		buttonStartStop.setBounds(298, 10, 70, 52);
		add(buttonStartStop);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 72, 358, 56);
		add(textArea);
		
		JLabel lblAlpha = new JLabel("alpha:");
		lblAlpha.setBounds(10, 46, 50, 15);
		add(lblAlpha);
		
		JLabel lblBeta = new JLabel("beta:");
		lblBeta.setBounds(146, 44, 50, 15);
		add(lblBeta);
		
		JLabel lblWindowLength = new JLabel("winLen:");
		lblWindowLength.setBounds(10, 13, 50, 15);
		add(lblWindowLength);
		
		textFieldWindowLength = new JTextField();
		textFieldWindowLength.setText("0");
		textFieldWindowLength.setBounds(70, 10, 66, 21);
		add(textFieldWindowLength);
		textFieldWindowLength.setColumns(10);

	}
}
