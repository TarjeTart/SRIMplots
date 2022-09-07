import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	public static String srModLocation = "D:" + File.separator + "SRIM-2013" + File.separator + "SR Module";
	
	public static void main(String[] args) {
		
		//file name
		String fileName = "Neon in Niobium";
		//create beam
		//argon: 18, 39.948
		//neon: 10, 20.1797
		IonBeam beam = new IonBeam(10, 20.1797);//Z, mass
		//create target
		Target target = new Target(41, "Niobium", 1, 92.906, 8.57 , 0, 1);//int Z, String name, int stoich, double mass, double density, int state(0=sol,1=gas), int corr
		//method to create the SRIM input file
		createSRIN(fileName, beam, target, 10, 45);//filename, beam, target, Emin, Emax
		//run SRIM
		try {
			//creates process
			ProcessBuilder pb = new ProcessBuilder(srModLocation + File.separator + "SRModule.exe");
			pb.directory(new File(srModLocation));//sets directory. THIS IS SUPER IMPORTANT TO MAKE SURE ITS CORRECT
			Process process = pb.start();//runs srim
			process.waitFor();//waits for srim to finish
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.out.println("Error running SRModule");
		}
		//retrieve data
		ArrayList<SrimDataPoint> data = getData(fileName);
		//plot range data
		RangeChart rangeChart = new RangeChart(fileName, data, fileName);
		rangeChart.pack();
		UIUtils.centerFrameOnScreen(rangeChart);
		rangeChart.setVisible(true);
		try {
			ChartUtils.saveChartAsPNG(new File(srModLocation + File.separator + fileName + ".png"), 
					rangeChart.getJFreeChart(), 
					1080, 720);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving chart as png");
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
					+ beam.getZ(0) + "   " + beam.getMass(0) + "\r\n"
					+ "---Target Data: (Solid=0,Gas=1), Density(g/cm3), Compound Corr.\r\n"
					+ target.getState() + "    " + target.getDensity(0) + "    " + target.getStoich(0) + "\r\n"
					+ "---Number of Target Elements\r\n1\r\n---Target Elements: (Z), Target name, Stoich, Target Mass(u)\r\n"
					+ target.getZ(0) + "   \"" + target.getName(0) + "\"               " + target.getStoich(0) + "             " + target.getMass(0) + "\r\n"
					+ "---Output Stopping Units (1-8)\r\n5\r\n---Ion Energy : E-Min(keV), E-Max(keV)\r\n"
					+ Emin + "    " + Emax);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Writing File");
		}
	}
	
	public static ArrayList<SrimDataPoint> getData(String fileName){
		
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
				line.trim();
				Scanner tmp = new Scanner(line);
				Double energy = tmp.nextDouble(); 
				tmp.next();
				tmp.nextDouble();
				tmp.nextDouble();
				Double range = tmp.nextDouble(); tmp.next();
				Double straggling = tmp.nextDouble();
				dataPoints.add(new SrimDataPoint(energy, range, straggling));
				line = fileScanner.nextLine();
				tmp.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error loading data file");
		}
		
		return dataPoints;
		
	}

}