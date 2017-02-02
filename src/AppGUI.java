import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AppGUI extends JFrame{
	private static int WIDTH = 600;
	private static int HEIGTH = 420;
	
	JPanel appPanel = new JPanel();
	JLabel parsetypeInfo;
	JLabel logo;
	Icon accenture;
	JButton selectText;
	JButton parseOperation;
	JTextArea textLog;
	JTextArea parseType;
	JTextArea parseLog;
	
	public AppGUI(String header){
		super(header);
		setLayout(new BorderLayout());
		setBounds(0, 0, WIDTH, HEIGTH);
		
		textLog = new JTextArea();
		textLog.setEditable(false);
		JScrollPane ScrolltextLog = new JScrollPane(textLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ScrolltextLog.setBounds(10, 10, 250, 300);
		
		parseType = new JTextArea();
		parseType.setBounds(100, 320, 20, 20);
		parsetypeInfo = new JLabel("Parsing Item:");
		parsetypeInfo.setBounds(10, 320, 100, 20);
		selectText = new JButton("Select Text");
		selectText.setBounds(150, 320, 100, 25);
		
		parseOperation = new JButton(">>");
		parseOperation.setBounds(275, 150, 50, 25);
		parseLog = new JTextArea();
		parseLog.setEditable(false);
		JScrollPane ScrollparseLog = new JScrollPane(parseLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ScrollparseLog.setBounds(335, 10, 250, 300);
		
		
		appPanel.setLayout(null);
		appPanel.add(ScrolltextLog);
		appPanel.add(ScrollparseLog);
		appPanel.add(parsetypeInfo);
		appPanel.add(parseType);
		appPanel.add(selectText);
		appPanel.add(parseOperation);
		
		Listener list = new Listener();
		selectText.addActionListener(list);
		parseOperation.addActionListener(list);
		
		add(appPanel);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	private class Listener implements ActionListener{
		public void actionPerformed(ActionEvent click){
			if(click.getActionCommand() == "Select Text"){
				JFileChooser chooser = new JFileChooser("./");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(new FileCH_Filter());
				int returnVal = chooser.showOpenDialog(appPanel);
				File directory = null;
				if (returnVal == JFileChooser.APPROVE_OPTION){
					directory = chooser.getSelectedFile();
				}
				BufferedReader reader = null;
				try{
					String ext = FileCH_Filter.getExtension(directory);
					reader = new BufferedReader(new FileReader(directory));
					try{
						String text;
						String allText = "";
						while((text = reader.readLine()) != null){
							allText = allText + text + "\n";
						}
						if(allText.isEmpty()){
							JOptionPane.showMessageDialog(appPanel, "No Char", "Error!", JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							textLog.setText(allText);
						}
					}
					catch(IOException except){
						except.printStackTrace();
					}
				}
				catch(FileNotFoundException except){
					except.printStackTrace();
				}
			}
			if(click.getActionCommand() == ">>"){
				String parseText = textLog.getText();
				if(parseText.length() == 0){
					JOptionPane.showMessageDialog(appPanel, "No Text Here", "Error!", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					String parse = parseType.getText();
					if(parse.length() == 0){
						JOptionPane.showMessageDialog(appPanel, "No Token", "Error!", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						StringTokenizer token = new StringTokenizer(parseText, parse);
						while(token.hasMoreElements()){
							parseLog.append(token.nextToken());
							parseLog.append("\n");
						}
					}
				}
			}
		}
	}
	public static void main(String args[]){
		new AppGUI("String Parsing v1.0");
	}
}
