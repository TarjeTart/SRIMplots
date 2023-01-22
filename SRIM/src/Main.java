import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.ui.UIUtils;

import Chart.*;
import data.*;
import input.*;

public class Main {
	
	/*stopping units
	 * 1 = eV/ang, 2 = KeV/micron, 3 = MeV/mm
	 * 4 = KeV/ug/cm2, 5 = MeV/mg/cm2, 6 = KeV/mg/cm2
	 * 7 = eV/1E15 atoms/cm2, 8 = LSS reduce units
	 */
	
	/*
	 * checklist for new runs
	 * - Change filename
	 * - Change beam ion
	 * - Change material
	 * - Change energy range
	 */
	
	//location of the SRIM SR Module directory on the machine
	public static String srModLocation;
	
	public static String fileName;
	
	public static JFrame logFrame,firstFrame,fileNameFrame,beamFrame,materialFrame,elementFrame,finalFrame;
	
	public static IonBeam beam;
	
	public static Target target;
	
	public static void main(String[] args) {
		
		logFrame = new JFrame();
		logFrame.setSize(400,50);
		//set to true if working on code
		logFrame.setVisible(false);
		logFrame.setTitle("Version 3.0.0");
		
		//create initial jframes
		firstFrame = new JFrame();
		fileNameFrame = new JFrame();
		//text field to hold path location
		JTextField path = new JTextField("No Path Selected");
		path.setBounds(130,200,200,40);
		firstFrame.add(path);
		path.setLocation(250,100);
		//button that will open file explorer
		JButton fileExplore = new JButton("Select SRIM Folder");
		fileExplore.setBounds(130,200,200,40);
		//what to do when button is pressed
		fileExplore.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				makeExplorer(fileExplore, path);
			}  
		});
		firstFrame.add(fileExplore);
		fileExplore.setLocation(250, 300);
		//next button
		JButton approvePath = new JButton("Approve Path");
		approvePath.setBounds(130,200,200,40);
		//creates file name frame
		approvePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField fileName = new JTextField("Enter name for file (Eg: \'beam\' in \'material\')");
				fileName.setBounds(130,300,300,40);
				JButton approveName = new JButton("Accept");
				approveName.setBounds(130,100,100,40);
				approveName.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.fileName = fileName.getText();
						makeBeamFrame();
					}
				});
				fileNameFrame.add(fileName);
				fileName.setLocation(175, 100);
				fileNameFrame.add(approveName);
				approveName.setLocation(250, 300);
				fileNameFrame.setSize(700,800);
				fileNameFrame.setLayout(null);
				firstFrame.dispose();;
				fileNameFrame.setVisible(true);
				fileNameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		firstFrame.add(approvePath);
		approvePath.setLocation(250,600);
		//finalizes initial frame
		firstFrame.setSize(700,800);
		firstFrame.setLayout(null);
		firstFrame.setVisible(true);
		firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//argon: 18, 39.948
		//neon: 10, 19.992
		//Nb 8.57 , 0, 1
		//density, int state(0=sol,1=gas), int corr
		//nb 41, "Niobium", 1, 92.906
		//Z, name, stoich, mass
		
	}
	
	//creates beam frame
	public static void makeBeamFrame() {
		//initialize components
		JTextField zValue = new JTextField("Ion Z value");
		zValue.setBounds(130,100,100,40);
		zValue.setLocation(50, 100);
		JTextField massValue = new JTextField("Ion Mass");
		massValue.setBounds(130,100,100,40);
		massValue.setLocation(200, 100);
		JButton approveBeam = new JButton("Set Beam");
		approveBeam.setBounds(130,100,100,40);
		approveBeam.setLocation(100, 300);
		//add action to approveBeam button
		approveBeam.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				beam = new IonBeam(new Scanner(zValue.getText()).nextInt(),new Scanner(massValue.getText()).nextDouble());
				makeTargetFrame();
			}  
		});
		//create beamFrame and remove fileNameFrame
		beamFrame = new JFrame();
		beamFrame.add(zValue);
		beamFrame.add(massValue);
		beamFrame.add(approveBeam);
		beamFrame.setSize(400,500);
		beamFrame.setLayout(null);
		fileNameFrame.dispose();
		beamFrame.setVisible(true);
		beamFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//creates material frame
	public static void makeTargetFrame() {
		JTextField density = new JTextField("Material Density");
		density.setBounds(130,100,100,40);
		density.setLocation(100,100);
		//0=solid 1=gas
		String[] stateVal = {"Solid","Gas"};
		JComboBox<String> state = new JComboBox<String>(stateVal);
		state.setBounds(130,100,100,40);
		state.setLocation(250, 100);
		JTextField corr = new JTextField("Compound Correction");
		corr.setBounds(130,200,200, 40);
		corr.setLocation(400, 100);
		JButton approveMaterial = new JButton("Approve");
		approveMaterial.setBounds(130,100,100,40);
		approveMaterial.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				target = new Target(
						new Scanner(density.getText()).nextDouble(),
						state.getSelectedIndex(),
						new Scanner(corr.getText()).nextInt());
				makeElementFrame();
			}  
		});
		approveMaterial.setLocation(300,300);
		materialFrame = new JFrame();
		materialFrame.add(density);
		materialFrame.add(state);
		materialFrame.add(corr);
		materialFrame.add(approveMaterial);
		materialFrame.setSize(700,800);
		materialFrame.setLayout(null);
		beamFrame.dispose();;
		materialFrame.setVisible(true);
		materialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//creates element frame
	public static void makeElementFrame() {
		JTextField z = new JTextField("Element Z");
		z.setBounds(130,100,100,40);
		z.setLocation(100,100);
		JTextField name = new JTextField("Element Name");
		name.setBounds(130,200,200,40);
		name.setLocation(250,100);
		JTextField stoich = new JTextField("Element Stoich");
		stoich.setBounds(130,100,100,40);
		stoich.setLocation(500, 100);
		JTextField mass = new JTextField("Element Mass");
		mass.setBounds(130,100,100,40);
		mass.setLocation(650,100);
		JButton addElement = new JButton("Add Another Element");
		addElement.setBounds(130,200,200,40);
		addElement.setLocation(300,300);
		JButton approveElements = new JButton("Finalize Material");
		approveElements.setBounds(130,200,200,40);
		approveElements.setLocation(300, 600);
		addElement.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				target.addElement(new Scanner(z.getText()).nextInt(),
						name.getText(), 
						new Scanner(stoich.getText()).nextInt(), 
						new Scanner(mass.getText()).nextDouble());
				z.setText("Element Z");
				name.setText("Element Name");
				stoich.setText("Element Stoich");
				mass.setText("Element Mass");
			}  
		});
		approveElements.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				if(!z.getText().equals("Element Z")) {
					target.addElement(new Scanner(z.getText()).nextInt(),
						name.getText(), 
						new Scanner(stoich.getText()).nextInt(), 
						new Scanner(mass.getText()).nextDouble());
				}
				makeFinalFrame();
			}  
		});
		elementFrame = new JFrame();
		elementFrame.add(z);
		elementFrame.add(name);
		elementFrame.add(stoich);
		elementFrame.add(mass);
		elementFrame.add(addElement);
		elementFrame.add(approveElements);
		elementFrame.setSize(800,900);
		elementFrame.setLayout(null);
		materialFrame.dispose();
		elementFrame.setVisible(true);
		elementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//creates final frame
	public static void makeFinalFrame() {
		JTextField energyI = new JTextField("Lower Bound Energy (KeV)");
		energyI.setBounds(130,250,250,40);
		energyI.setLocation(50,100);
		JTextField energyF = new JTextField("Upper Bound Energy (KeV)");
		energyF.setBounds(130,250,250,40);
		energyF.setLocation(350,100);
		String[] energyU = {"Output Energy","KeV","MeV"};
		String[] lengthU = {"Output Length","A","um","mm","cm"};
		JComboBox<String> energyUnit = new JComboBox<String>(energyU);
		energyUnit.setBounds(130,200,200,40);
		energyUnit.setLocation(100, 300);
		JComboBox<String> lengthUnit = new JComboBox<String>(lengthU);
		lengthUnit.setBounds(130,200,200,40);
		lengthUnit.setLocation(350, 300);
		JButton createPlot = new JButton("Create Plot");
		createPlot.setBounds(130,100,100,40);
		createPlot.setLocation(250, 600);
		logFrame.setTitle("Before createPlot");
		///////////////////////////////////////////////////////////////////////////
		createPlot.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				logFrame.setTitle("Before SRIN");
				createSRIN(fileName, beam, target, 
						new Scanner(energyI.getText()).nextInt(), 
						new Scanner(energyF.getText()).nextInt());
				logFrame.setTitle("Before Process");
				try {
					//creates process
					ProcessBuilder pb = new ProcessBuilder(srModLocation + File.separator + "SRModule.exe");
					pb.directory(new File(srModLocation));//sets directory. THIS IS SUPER IMPORTANT TO MAKE SURE ITS CORRECT
					Process process = pb.start();//runs srim
					process.waitFor();//waits for srim to finish
				} catch (IOException | InterruptedException ex) {
					ex.printStackTrace();
					logFrame.setTitle("error running sr mod");
				}
				logFrame.setTitle("after process");
				//retrieve data
				ArrayList<SrimDataPoint> data = getData(fileName);
				//form: filename, data, filename, length unit, energy unit
				RangeChart rangeChart = new RangeChart(fileName, data, fileName, 
						lengthUnit.getItemAt(lengthUnit.getSelectedIndex()), 
						energyUnit.getItemAt(energyUnit.getSelectedIndex()));
				//makes chart fit window size
				rangeChart.pack();
				//creates chart panel in middle of screen
				UIUtils.centerFrameOnScreen(rangeChart);
				//shows user final chart
				rangeChart.setVisible(true);
				//saves chart as a png
				try {
					ChartUtils.saveChartAsPNG(new File(srModLocation + File.separator + fileName + ".png"), 
							rangeChart.getJFreeChart(), 
							1080, 720);
				} catch (IOException ex) {
					ex.printStackTrace();
					logFrame.setTitle("error saving chart as png");
				}
				finalFrame.dispose();
			}  
		});
		logFrame.setTitle("outside createPlot");
		finalFrame = new JFrame();
		finalFrame.setTitle("Final Frame");
		finalFrame.add(energyI);
		finalFrame.add(energyF);
		finalFrame.add(energyUnit);
		finalFrame.add(lengthUnit);
		finalFrame.add(createPlot);
		finalFrame.setSize(700,800);
		finalFrame.setLayout(null);
		elementFrame.dispose();
		finalFrame.setVisible(true);
		finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//creates file explorer and sets path
	public static void makeExplorer(JButton fileExplore, JTextField path) {
		JFileChooser explorer = new JFileChooser();
		explorer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int i = explorer.showOpenDialog(fileExplore);
		if(i == JFileChooser.APPROVE_OPTION) {
			File f=explorer.getSelectedFile();    
			Main.srModLocation = f.getPath() + File.separator + "SR Module";  
			path.setText(f.getAbsolutePath());
		}
	}
	
	public static void createSRIN(String fileName, IonBeam beam, Target target, int Emin, int Emax) {
		Writer fileWriter = null;
		try {
			fileWriter = new FileWriter(srModLocation + File.separator + "SR.IN", false);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could Not Load File at " + srModLocation);
		}
		try {
			fileWriter.write("---Stopping/Range Input Data (Number-format: Period = Decimal Point)\r\n"
					+ "---Output File Name\r\n"
					+ "\"" + fileName + "\"\r\n"
					+ "---Ion(Z), Ion Mass(u)\r\n"
					+ beam.getZ() + "   " + beam.getMass() + "\r\n"
					+ "---Target Data: (Solid=0,Gas=1), Density(g/cm3), Compound Corr.\r\n"
					+ target.getState() + "    " + target.getDensity() + "    " + target.getCompCorr() + "\r\n"
					+ "---Number of Target Elements\r\n" + target.getElementCount() + "\r\n---Target Elements: (Z), Target name, Stoich, Target Mass(u)\r\n");
			for(int i = 0; i < target.getElementCount(); i++) {
				fileWriter.write( target.getZ(i) + "   \"" + target.getName(i) + "\"               " + target.getStoich(i) + "             " + target.getMass(i) + "\r\n");
			}
			fileWriter.write("---Output Stopping Units (1-8)\r\n5\r\n---Ion Energy : E-Min(keV), E-Max(keV)\r\n"
					+ Emin + "    " + Emax);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Writing File");
		}
	}
	
	public static ArrayList<SrimDataPoint> getData(String fileName){
		
		//data points to be plotted
		ArrayList<SrimDataPoint> dataPoints = new ArrayList<SrimDataPoint>();
		
		try {
			Scanner fileScanner = new Scanner(new File(srModLocation + File.separator + fileName));
			String line = fileScanner.nextLine();
			//go until data lines
			while(!line.contains("Energy") || !line.contains("Nuclear") || !line.contains("Straggling")) {
				line = fileScanner.nextLine();
			}
			//gets to line with data
			fileScanner.nextLine();line = fileScanner.nextLine();
			//while are data lines
			while(!line.contains("--------")) {
				Scanner tmp = new Scanner(line);
				//get energy
				Double energy = tmp.nextDouble();
				String eUnit = tmp.next();
				//stopping range values elec and nuclear
				tmp.nextDouble();
				tmp.nextDouble();
				//get range
				Double range = tmp.nextDouble(); 
				String rUnit = tmp.next();
				//get straggle
				Double straggling = tmp.nextDouble();
				String sUnit = tmp.next();
				//adds data point
				dataPoints.add(new SrimDataPoint(energy, eUnit, range, rUnit, straggling, sUnit));
				tmp.close();
				line = fileScanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error loading data file");
		}
		
		return dataPoints;
		
	}

}