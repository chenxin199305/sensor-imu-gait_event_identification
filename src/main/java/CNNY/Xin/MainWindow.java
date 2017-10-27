package CNNY.Xin;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import CNNY.Xin.controller.FootPressureForceSensorController;
import CNNY.Xin.controller.IMUController;
import CNNY.Xin.model.FootPressureForceSensorModel;
import CNNY.Xin.model.IMUModel;
import CNNY.Xin.view.FootPressureForceSensorPanel;
import CNNY.Xin.view.IMUPanel;

public class MainWindow {

	private JFrame frame;
	
	// IMU1
	public IMUModel IMUmodel1;
	public IMUPanel IMUpanel1;
	public IMUController IMUcontroller1;

	// IMU2
	public IMUModel model2;
	public IMUPanel panel2;
	public IMUController controller2;
	
	// IMU3
	public IMUModel model3;
	public IMUPanel panel3;
	public IMUController controller3;
	
	// IMU4
	public IMUModel model4;
	public IMUPanel panel4;
	public IMUController controller4;
	
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
		IMUmodel1 = new IMUModel();
		IMUpanel1 = new IMUPanel();
		IMUpanel1.setBounds(10, 6, 872, 634);
		this.frame.getContentPane().add(IMUpanel1);
		IMUcontroller1 = new IMUController(IMUmodel1, IMUpanel1);
		
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
					FPFSController1.setDataDisplayRecordingFlag();
					btnStartrecord.setText("StopRecord");
					break;
				case "StopRecord":
					IMUcontroller1.clearDataDisplayRecordingFlag();
					IMUcontroller1.clearPhasePlainRecordingFlag();
					FPFSController1.clearDataDisplayRecordingFlag();
					btnStartrecord.setText("StartRecord");
					break;
				default:
					break;
				}
			}
		});
	}
}
