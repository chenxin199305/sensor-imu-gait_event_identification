package CNNY.Xin;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import CNNY.Xin.action.SingleIMUAction;
import CNNY.Xin.controller.SingleIMUController;
import CNNY.Xin.model.SingleIMUModel;
import CNNY.Xin.view.SingleIMUDataDisplayPanel;
import CNNY.Xin.view.SingleIMUPanel;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class MainWindow {

	private JFrame frame;

	// IMU1
	private SingleIMUModel singleIMUStatusModel1;
	private SingleIMUPanel singleIMUMainPanel1;
	private SingleIMUController singleIMUStatusController1;

	// IMU2
	private SingleIMUModel singleIMUStatusModel2;
	private SingleIMUPanel singleIMUMainPanel2;
	private SingleIMUController singleIMUStatusController2;
	
	// IMU3
	private SingleIMUModel singleIMUStatusModel3;
	private SingleIMUPanel singleIMUMainPanel3;
	private SingleIMUController singleIMUStatusController3;
	
	// IMU4
	private SingleIMUModel singleIMUStatusModel4;
	private SingleIMUPanel singleIMUMainPanel4;
	private SingleIMUController singleIMUStatusController4;

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
		frame.setBounds(100, 100, 878, 697);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// IMU1
		singleIMUStatusModel1 = new SingleIMUModel();
		singleIMUMainPanel1 = new SingleIMUPanel();
//		singleIMUStatusPanel1.setBounds(0, 0, 425, 589);
		singleIMUMainPanel1.setBounds(0, 0, 426, 589);
		this.frame.getContentPane().add(singleIMUMainPanel1);
		singleIMUStatusController1 = new SingleIMUController(singleIMUStatusModel1, singleIMUStatusPanel1);

		// IMU2
		singleIMUStatusModel2 = new SingleIMUModel();
		singleIMUStatusPanel2 = new SingleIMUDataDisplayPanel();
		singleIMUStatusPanel2.setBounds(436, 0, 426, 589);
		this.frame.getContentPane().add(singleIMUStatusPanel2);
		singleIMUStatusController2 = new SingleIMUController(singleIMUStatusModel2, singleIMUStatusPanel2);
		
		// IMU3
		singleIMUStatusModel3 = new SingleIMUModel();
		singleIMUStatusPanel3 = new SingleIMUDataDisplayPanel();
		singleIMUStatusPanel3.setBounds(872, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUStatusPanel3);
		singleIMUStatusController3 = new SingleIMUController(singleIMUStatusModel3, singleIMUStatusPanel3);
		
		// IMU4
		singleIMUStatusModel4 = new SingleIMUModel();
		singleIMUStatusPanel4 = new SingleIMUDataDisplayPanel();
		singleIMUStatusPanel4.setBounds(1308, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUStatusPanel4);
		singleIMUStatusController4 = new SingleIMUController(singleIMUStatusModel4, singleIMUStatusPanel4);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "total control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 585, 426, 69);
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
					singleIMUStatusController1.getSingleIMUStatusAction().setToRecordState();
					singleIMUStatusController2.getSingleIMUStatusAction().setToRecordState();
					singleIMUStatusController3.getSingleIMUStatusAction().setToRecordState();
					singleIMUStatusController4.getSingleIMUStatusAction().setToRecordState();
					btnStartrecord.setText("StopRecord");
					break;
				case "StopRecord":
					singleIMUStatusController1.getSingleIMUStatusAction().setToStopRecordState();
					singleIMUStatusController2.getSingleIMUStatusAction().setToStopRecordState();
					singleIMUStatusController3.getSingleIMUStatusAction().setToStopRecordState();
					singleIMUStatusController4.getSingleIMUStatusAction().setToStopRecordState();
					btnStartrecord.setText("StartRecord");
					break;
				default:
					break;
				}
			}
		});
		
	}
}
