import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public class MainPanel extends JPanel{
    
    JButton singleRunBtn, batchRunBtn, jdkChooserBtn, singleChooserBtn, batchChooserBtn;
    JTextField jdkField, singleField, batchField, inputsField, argsField;
	String singleStudentName, singleDirectory, batchDirectory;
	String[] batchStudents;
	BatchTester batchTest = new BatchTester();
	ConfigurationItems configItems = new ConfigurationItems();
    
    public MainPanel() {
        
        super();
        setLayout(null);
        //setBackground(Color.WHITE);
		
		singleRunBtn = new JButton("Single Run");
		singleRunBtn.setEnabled(false);
        batchRunBtn = new JButton("Batch Run");
		batchRunBtn.setEnabled(false);
        
        // JLabel title over field?
        jdkField = new JTextField();
        jdkField.setEditable(false);
        jdkField.setBackground(Color.white);
        
        
        jdkChooserBtn = new JButton("Select JDK...");
        jdkChooserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFile = fileChooser.getSelectedFiles();
                    for(File file : selectedFile){
                        
                        // Make it so all filenames in textfield
                        jdkField.setText(file.getName());
                    }
                    
                }
            }
        });
        
        singleField = new JTextField();
        singleField.setEditable(false);
        singleField.setBackground(Color.white);
        
        singleChooserBtn = new JButton("Select Single...");
        singleChooserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser("C:\\java\\src\\program-test-242-1\\src\\src-output");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
					singleDirectory = selectedFile.getAbsolutePath();
                    singleField.setText(selectedFile.getName());
                    singleStudentName = selectedFile.getName();
				
					File configFile = new File("configSingle.txt");
					Scanner in = null;
					try {
						in = new Scanner(configFile);
					} catch (FileNotFoundException ex) {
						Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
					}
					String line  = in.nextLine();
					Scanner inLine = new Scanner(line); 
					inLine.next();
					line = in.nextLine();
					inLine = new Scanner(line);
					String previousStudentName = inLine.next();
					
					Path path = Paths.get("configSingle.txt");
					Charset charset = StandardCharsets.UTF_8;

					String content = null;
					try {
						content = new String(Files.readAllBytes(path), charset);
					} catch (IOException ex) {
						Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
					}
					content = content.replaceAll(previousStudentName, singleStudentName);
					try {
						Files.write(path, content.getBytes(charset));
					} catch (IOException ex) {
						Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
					}
					singleRunBtn.setEnabled(true);
				}
            }
        });
        
        batchField = new JTextField();
        batchField.setEditable(false);
        batchField.setBackground(Color.white);
        
        batchChooserBtn = new JButton("Select Batch...");
        batchChooserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
				File studentFoldersDir= null;
                JFileChooser fileChooser = new JFileChooser("C:\\java\\src\\program-test-242-1\\src");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    studentFoldersDir = fileChooser.getSelectedFile();
                    batchField.setText(studentFoldersDir.getName());
					batchDirectory = studentFoldersDir.getAbsolutePath();
					batchTest.sourcePath = batchDirectory;
					
					batchStudents = studentFoldersDir.list();
					int n = 0;
					File configFile = new File("configBatch.txt");
					FileWriter writer = null;
					try {
						writer = new FileWriter(configFile, false);
						writer.write("");
						writer.close();
					} catch (IOException ex) {
						Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
					}
					String text = "";
					for(String student : batchStudents) {
						String studentNumber = "";
						if(n == 0) {
							studentNumber = "0000000";
							text = n + " " + student + " " + studentNumber;
						}
						else {
							studentNumber = String.valueOf(n*1111111);
							text = text + "\n" + n + " " + student + " " + studentNumber;
						}
						n++;
					}
					FileWriter writer2 = null;
					try {
						writer2 = new FileWriter("configBatch.txt");
						writer2.write(text);
						writer2.close();
					} catch (IOException ex) {
						Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
					}
					batchRunBtn.setEnabled(true);
				}
            }
        });
        
        JLabel inputsLbl = new JLabel("Test Inputs (Separate by Comma)");
        inputsField = new JTextField();
        inputsField.setBackground(Color.white);
		
		JLabel argsLbl = new JLabel("Argument Inputs");
		argsField = new JTextField();
		argsField.setText(null);
        JLabel argsFormatLbl = new JLabel("Arg Format: ProgramName arg arg arg, ProgramName arg arg, etc.");
        
        singleRunBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
				String argText = argsField.getText();
				configItems.setArgs(argText);
				configItems.setTestInputs(inputsField.getText());
				SingleTester singleTest = new SingleTester();
				singleTest.main(null);
				OutputFrame singleFrame = new OutputFrame(false);
				try {
					singleFrame.singlePanel.displayStudentOutputs(singleDirectory, singleStudentName);
				} catch (BadLocationException ex) {
					Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
				}
            }
        });
        
        batchRunBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
				String argText = argsField.getText();
				configItems.setArgs(argText);
				configItems.setTestInputs(inputsField.getText());
				batchTest.main(null);
                OutputFrame batchFrame = new OutputFrame(true);
				try {
					batchFrame.batchPanel.displayStudentOutputs(batchDirectory, batchStudents);
				} catch (BadLocationException ex) {
					Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
				}
            }
        });
        
        singleRunBtn.setBounds(150,500,100,30);
        batchRunBtn.setBounds(450,500,100,30);
        jdkChooserBtn.setBounds(355, 150, 125, 30);
        jdkField.setBounds(200, 150, 150, 30);
        singleChooserBtn.setBounds(355, 200, 125, 30);
        singleField.setBounds(200, 200, 150, 30);
        batchChooserBtn.setBounds(355, 250, 125, 30);
        batchField.setBounds(200, 250, 150, 30);
        inputsField.setBounds(200, 350, 280, 30);
        inputsLbl.setBounds(200, 320, 200, 30);
		argsLbl.setBounds(200,400,280,30);
		argsField.setBounds(200, 430, 280, 30);
        argsFormatLbl.setBounds(150,460,400,30);
        
        add(singleRunBtn);
        add(batchRunBtn);
        add(jdkField);
        add(jdkChooserBtn);
        add(singleField);
        add(singleChooserBtn);
        add(batchField);
        add(batchChooserBtn);
        add(inputsLbl);
        add(inputsField);
		add(argsLbl);
		add(argsField);
		add(argsFormatLbl);
    }
}
