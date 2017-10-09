package CNNY.Xin;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import CNNY.Xin.controller.FootPressureForceSensorController;
import CNNY.Xin.controller.SingleIMUController;
import CNNY.Xin.model.FootPressureForceSensorModel;
import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.view.FootPressureForceSensorPanel;
import CNNY.Xin.view.SingleIMUPanel;

public class MainWindow {

	private JFrame frame;
	
	// IMU1
	public SingleIMUModel IMUmodel1;
	public SingleIMUPanel IMUpanel1;
	public SingleIMUController IMUcontroller1;

	// IMU2
	public SingleIMUModel model2;
	public SingleIMUPanel panel2;
	public SingleIMUController controller2;
	
	// IMU3
	public SingleIMUModel model3;
	public SingleIMUPanel panel3;
	public SingleIMUController controller3;
	
	// IMU4
	public SingleIMUModel model4;
	public SingleIMUPanel panel4;
	public SingleIMUController controller4;
	
	// Foot Pressure Force Sensor
	public FootPressureForceSensorModel FPFSModel1;
	public FootPressureForceSensorPanel FPFSpanel1;
	public FootPressureForceSensorController FPFSController1;

	// total control panel
	JButton btnStartrecord;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1372, 744);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// IMU1
		IMUmodel1 = new SingleIMUModel();
		IMUpanel1 = new SingleIMUPanel();
		IMUpanel1.setBounds(10, 6, 872, 634);
		this.frame.getContentPane().add(IMUpanel1);
		IMUcontroller1 = new SingleIMUController(IMUmodel1, IMUpanel1);

		// IMU2
		model2 = new SingleIMUModel();
		panel2 = new SingleIMUPanel();
		panel2.setBounds(436, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUPanel2);
		controller2 = new SingleIMUController(model2, panel2);
		
		// IMU3
		model3 = new SingleIMUModel();
		panel3 = new SingleIMUPanel();
		panel3.setBounds(872, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUPanel3);
		controller3 = new SingleIMUController(model3, panel3);
		
		// IMU4
		model4 = new SingleIMUModel();
		panel4 = new SingleIMUPanel();
		panel4.setBounds(1308, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUPanel4);
		controller4 = new SingleIMUController(model4, panel4);
		
		// Foot Pressure Force Sensor
		FPFSModel1 = new FootPressureForceSensorModel();
		FPFSpanel1 = new FootPressureForceSensorPanel();
		FPFSpanel1.setBounds(892, 6, 456, 634);
		this.frame.getContentPane().add(FPFSpanel1);
		FPFSController1 = new FootPressureForceSensorController(FPFSModel1, FPFSpanel1);
		
		// Total control panel
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "total control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 646, 426, 69);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		btnStartrecord = new JButton("StartRecord");
		btnStartrecord.setBounds(20, 26, 105, 23);
		panel.add(btnStartrecord);
		btnStartrecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				switch (btnStartrecord.getText()) {
				case "StartRecord":
					IMUcontroller1.setDataDisplayRecordingFlag();
					IMUcontroller1.setPhasePlainRecordingFlag();
//					singleIMUController2.singleIMUAction.setToRecordState();
//					singleIMUController3.singleIMUAction.setToRecordState();
//					singleIMUController4.singleIMUAction.setToRecordState();
					btnStartrecord.setText("StopRecord");
					break;
				case "StopRecord":
					IMUcontroller1.clearDataDisplayRecordingFlag();
					IMUcontroller1.clearPhasePlainRecordingFlag();
//					singleIMUController2.singleIMUAction.setToStopRecordState();
//					singleIMUController3.singleIMUAction.setToStopRecordState();
//					singleIMUController4.singleIMUAction.setToStopRecordState();
					btnStartrecord.setText("StartRecord");
					break;
				default:
					break;
				}
			}
		});
	}
}
