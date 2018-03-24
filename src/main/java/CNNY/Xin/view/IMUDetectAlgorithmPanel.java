package CNNY.Xin.view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

public class IMUDetectAlgorithmPanel extends JPanel {

	public JTabbedPane tabbedPane;
	
	public IMUDetectAlgorithmPanel() {
		setBorder(new TitledBorder(null, "detect algorithm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 15, 386, 351);
		add(tabbedPane);
	}
}
