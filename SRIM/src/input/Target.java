package input;

import java.util.ArrayList;

public class Target {

	//list for all target properties
	private ArrayList<Integer> Zvalues = new ArrayList<Integer>();
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Integer> stoichValues = new ArrayList<Integer>();
	private ArrayList<Double> massValues = new ArrayList<Double>();
	
	private double density;
	private int state;//solid=0, gas=1
	private int compoundCorr;//always 1 for now
	
	private int elemCount = 0;
	
	public Target(double density, int state, int corr) {
		this.density = density;
		this.state = state;
		compoundCorr = corr;
	}
	
	public void addElement(int Z, String name, int stoich, double mass) {
		Zvalues.add(Z);
		names.add(name);
		stoichValues.add(stoich);
		massValues.add(mass);
		elemCount++;
	}
	
	public int getElementCount() {
		return elemCount;
	}
	
	public int getZ(int index) {
		return Zvalues.get(index);
	}
	public String getName(int index) {
		return names.get(index);
	}
	public int getStoich(int index) {
		return stoichValues.get(index);
	}
	public double getMass(int index) {
		return massValues.get(index);
	}
	public double getDensity() {
		return density;
	}
	public int getState() {
		return state;
	}
	public int getCompCorr() {
		return compoundCorr;
	}
	
}
