
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class Mainwindow {

	private JFrame frmMwlPageTo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainwindow window = new Mainwindow();
					window.frmMwlPageTo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMwlPageTo = new JFrame();
		frmMwlPageTo.setTitle("Mwl page to structure");
		frmMwlPageTo.setBounds(100, 100, 298, 223);
		frmMwlPageTo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMwlPageTo.getContentPane().setLayout(null);
		
		final TextField textField_2 = new TextField();
		textField_2.setBounds(20, 38, 240, 22);
		frmMwlPageTo.getContentPane().add(textField_2);
		
		Label label_2 = new Label("Mwl file");
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(20, 10, 240, 22);
		frmMwlPageTo.getContentPane().add(label_2);
		
		Button button = new Button("Make .ums file/Extract structure");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(20, 66, 240, 112);
		frmMwlPageTo.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
        		structure structure= new structure();
        		structure.main(textField_2.getText());
        }}); 

	}
}
