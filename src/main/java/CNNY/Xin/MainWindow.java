package CNNY.Xin;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import CNNY.Xin.action.SingleIMUStatusAction;
import CNNY.Xin.controller.SingleIMUStatusController;
import CNNY.Xin.model.SingleIMUStatusModel;
import CNNY.Xin.view.SingleIMUStatusPanel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

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
		singleIMUStatusController2 = new SingleIMUStatusController(singleIMUStatusModel2, singleIMUStatusPanel2);
		
		// IMU3
		singleIMUStatusModel3 = new SingleIMUStatusModel();
		singleIMUStatusPanel3 = new SingleIMUStatusPanel();
		singleIMUStatusPanel3.setBounds(872, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUStatusPanel3);
		singleIMUStatusController3 = new SingleIMUStatusController(singleIMUStatusModel3, singleIMUStatusPanel3);
		
		// IMU4
		singleIMUStatusModel4 = new SingleIMUStatusModel();
		singleIMUStatusPanel4 = new SingleIMUStatusPanel();
		singleIMUStatusPanel4.setBounds(1308, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUStatusPanel4);
		singleIMUStatusController4 = new SingleIMUStatusController(singleIMUStatusModel4, singleIMUStatusPanel4);
		
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
