
import java.awt.event.*;
import java.awt.*;
import java.io.File;


import javax.swing.*;

 @SuppressWarnings("serial")
public class UI extends JPanel implements ActionListener {

	JButton btnOpen, btnStart, btnDest;
	JLabel lblFile,lblUrl;
	JTextArea jTxtOutput;
	JTextField urlInput;
	String xlFileName = new String();
	String xlPath = new String();
	String dirName = new String();
	String dirPath = new String();

	public UI() {
		setLayout(new FlowLayout());

		btnOpen = new JButton("Select Excel File");
		lblUrl = new JLabel("Url");
		urlInput = new JTextField(20);	    
		btnStart = new JButton("Start");
		lblFile = new JLabel("Selected File :");
			
		btnOpen.addActionListener(this);
		btnStart.addActionListener(this);
		urlInput.addActionListener(this);

		add(btnOpen);
		add(lblUrl);
		add(urlInput);
		add(btnStart);
		add(lblFile);
		
	}

	public void actionPerformed(ActionEvent ae){
		String str = ae.getActionCommand();
		String url = urlInput.getText();			
			if (str.equals("Select Excel File")) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setCurrentDirectory(new File("."));
			int result = jFileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jFileChooser.getSelectedFile();
				xlFileName = (String)selectedFile.getName();
				xlPath = (String)selectedFile.getAbsolutePath();
				lblFile.setText("\n Selected file: " + xlPath);
			}
		} else if (str.equals("Start")) {
			try {
				new SeleniumCode(xlPath,url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Selenium UI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new UI());
		frame.setBounds(200, 200, 700, 400);
		frame.setVisible(true);
	} 
}
