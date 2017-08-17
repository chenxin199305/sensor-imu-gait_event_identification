package CNNY.Xin;

import java.awt.EventQueue;

import javax.swing.JFrame;

import CNNY.Xin.action.SingleIMUStatusAction;
import CNNY.Xin.controller.SingleIMUStatusController;
import CNNY.Xin.model.SingleIMUStatusModel;
import CNNY.Xin.view.SingleIMUStatusPanel;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frame;

	// IMU1
	private SingleIMUStatusModel singleIMUStatusModel1;
	private SingleIMUStatusPanel singleIMUStatusPanel1;
	private SingleIMUStatusController singleIMUStatusController1;

	// IMU2
	private SingleIMUStatusModel singleIMUStatusModel2;
	private SingleIMUStatusPanel singleIMUStatusPanel2;
	private SingleIMUStatusController singleIMUStatusController2;
	
	// IMU3
	private SingleIMUStatusModel singleIMUStatusModel3;
	private SingleIMUStatusPanel singleIMUStatusPanel3;
	private SingleIMUStatusController singleIMUStatusController3;
	
	// IMU4
	private SingleIMUStatusModel singleIMUStatusModel4;
	private SingleIMUStatusPanel singleIMUStatusPanel4;
	private SingleIMUStatusController singleIMUStatusController4;

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
		frame.setBounds(100, 100, 880, 780);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// IMU1
		singleIMUStatusModel1 = new SingleIMUStatusModel();
		singleIMUStatusPanel1 = new SingleIMUStatusPanel();
//		singleIMUStatusPanel1.setBounds(0, 0, 425, 589);
		singleIMUStatusPanel1.setBounds(0, 0, 426, 589);
		this.frame.getContentPane().add(singleIMUStatusPanel1);
		singleIMUStatusController1 = new SingleIMUStatusController(singleIMUStatusModel1, singleIMUStatusPanel1);

		// IMU2
		singleIMUStatusModel2 = new SingleIMUStatusModel();
		singleIMUStatusPanel2 = new SingleIMUStatusPanel();
		singleIMUStatusPanel2.setBounds(436, 0, 426, 589);
		this.frame.getContentPane().add(singleIMUStatusPanel2);
		singleIMUStatusController1 = new SingleIMUStatusController(singleIMUStatusModel2, singleIMUStatusPanel2);
		
		// IMU3
		singleIMUStatusModel3 = new SingleIMUStatusModel();
		singleIMUStatusPanel3 = new SingleIMUStatusPanel();
		singleIMUStatusPanel3.setBounds(0, 599, 426, 132);
		this.frame.getContentPane().add(singleIMUStatusPanel3);
		singleIMUStatusController1 = new SingleIMUStatusController(singleIMUStatusModel3, singleIMUStatusPanel3);
		
		// IMU4
		singleIMUStatusModel4 = new SingleIMUStatusModel();
		singleIMUStatusPanel4 = new SingleIMUStatusPanel();
		singleIMUStatusPanel4.setBounds(436, 599, 426, 132);
		this.frame.getContentPane().add(singleIMUStatusPanel4);
		singleIMUStatusController1 = new SingleIMUStatusController(singleIMUStatusModel4, singleIMUStatusPanel4);
		
	}
}
