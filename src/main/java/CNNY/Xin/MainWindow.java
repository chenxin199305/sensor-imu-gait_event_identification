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
	private SingleIMUModel singleIMUModel1;
	private SingleIMUPanel singleIMUPanel1;
	private SingleIMUController singleIMUController1;

	// IMU2
	private SingleIMUModel singleIMUModel2;
	private SingleIMUPanel singleIMUPanel2;
	private SingleIMUController singleIMUController2;
	
	// IMU3
	private SingleIMUModel singleIMUModel3;
	private SingleIMUPanel singleIMUPanel3;
	private SingleIMUController singleIMUController3;
	
	// IMU4
	private SingleIMUModel singleIMUModel4;
	private SingleIMUPanel singleIMUPanel4;
	private SingleIMUController singleIMUController4;

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
		frame.setBounds(100, 100, 878, 744);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// IMU1
		singleIMUModel1 = new SingleIMUModel();
		singleIMUPanel1 = new SingleIMUPanel();
		singleIMUPanel1.setBounds(10, 6, 872, 634);
		this.frame.getContentPane().add(singleIMUPanel1);
		singleIMUController1 = new SingleIMUController(singleIMUModel1, singleIMUPanel1);

		// IMU2
		singleIMUModel2 = new SingleIMUModel();
		singleIMUPanel2 = new SingleIMUPanel();
		singleIMUPanel2.setBounds(436, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUPanel2);
		singleIMUController2 = new SingleIMUController(singleIMUModel2, singleIMUPanel2);
		
		// IMU3
		singleIMUModel3 = new SingleIMUModel();
		singleIMUPanel3 = new SingleIMUPanel();
		singleIMUPanel3.setBounds(872, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUPanel3);
		singleIMUController3 = new SingleIMUController(singleIMUModel3, singleIMUPanel3);
		
		// IMU4
		singleIMUModel4 = new SingleIMUModel();
		singleIMUPanel4 = new SingleIMUPanel();
		singleIMUPanel4.setBounds(1308, 0, 426, 589);
//		this.frame.getContentPane().add(singleIMUPanel4);
		singleIMUController4 = new SingleIMUController(singleIMUModel4, singleIMUPanel4);
		
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
//					singleIMUController1.singleIMUAction.setToRecordState();
//					singleIMUController2.singleIMUAction.setToRecordState();
//					singleIMUController3.singleIMUAction.setToRecordState();
//					singleIMUController4.singleIMUAction.setToRecordState();
					btnStartrecord.setText("StopRecord");
					break;
				case "StopRecord":
//					singleIMUController1.singleIMUAction.setToStopRecordState();
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
