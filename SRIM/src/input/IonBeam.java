package input;

public class IonBeam {
	
	//list for all beam properties
	private int Z;
	private double mass;

	public IonBeam(int Z, double mass) {
		this.Z = Z;
		this.mass = mass;
	}
	
	public int getZ() {
		return Z;
	}
	
	public double getMass() {
		return mass;
	}
	
}
