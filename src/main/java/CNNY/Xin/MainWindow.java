package CNNY.Xin;

import java.awt.EventQueue;

import javax.swing.JFrame;

import CNNY.Xin.action.SingleIMUStatusAction;
import CNNY.Xin.controller.SingleIMUStatusController;
import CNNY.Xin.model.SingleIMUStatusModel;
import CNNY.Xin.view.SingleIMUStatusPanel;

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
		frame.setBounds(100, 100, 876, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// IMU1
		singleIMUStatusModel1 = new SingleIMUStatusModel();
		singleIMUStatusPanel1 = new SingleIMUStatusPanel();
		singleIMUStatusPanel1.setBounds(0, 0, 425, 528);
		this.frame.getContentPane().add(singleIMUStatusPanel1);
		singleIMUStatusController1 = new SingleIMUStatusController(singleIMUStatusModel1, singleIMUStatusPanel1);
	
		// IMU2
		singleIMUStatusModel2 = new SingleIMUStatusModel();
		singleIMUStatusPanel2 = new SingleIMUStatusPanel();
		singleIMUStatusPanel2.setBounds(435, 0, 425, 528);
		this.frame.getContentPane().add(singleIMUStatusPanel2);
		singleIMUStatusController1 = new SingleIMUStatusController(singleIMUStatusModel2, singleIMUStatusPanel2);
	}

}
