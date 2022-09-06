package input;

import java.util.ArrayList;

public class IonBeam {
	
	//list for all beam properties
	private ArrayList<Integer> Zvalues = new ArrayList<Integer>();
	private ArrayList<Double> massValues = new ArrayList<Double>();

	public IonBeam(int Z, double mass) {
		Zvalues.add(Z);
		massValues.add(mass);
	}
	
	public int getZ(int index) {
		return Zvalues.get(0);
	}
	
	public double getMass(int index) {
		return massValues.get(0);
	}
	
}
