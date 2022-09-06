package input;

import java.util.ArrayList;

public class Target {

	//list for all target properties
	private ArrayList<Integer> Zvalues = new ArrayList<Integer>();
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Integer> stoichValues = new ArrayList<Integer>();
	private ArrayList<Double> massValues = new ArrayList<Double>();
	private ArrayList<Double> densities = new ArrayList<Double>();
	
	private int state;//solid=0, gas=1
	private int compoundCorr;//always 1 for now
	
	public Target(int Z, String name, int stoich, double mass, double density, int state, int corr) {
		
		Zvalues.add(Z);
		names.add(name);
		stoichValues.add(stoich);
		massValues.add(mass);
		densities.add(density);
		this.state = state;
		compoundCorr = corr;
		
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
	public double getDensity(int index) {
		return densities.get(index);
	}
	public int getState() {
		return state;
	}
	public int getCompCorr() {
		return compoundCorr;
	}
	
}
