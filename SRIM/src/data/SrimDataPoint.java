package data;

public class SrimDataPoint {

	private double ionEnergy, range, straggling;

	public SrimDataPoint(double ionEnergy, double range, double straggling) {
		this.ionEnergy = ionEnergy;
		this.range = range;
		this.straggling = straggling;
	}

	public double getIonEnergy() {
		return ionEnergy;
	}

	public void setIonEnergy(double ionEnergy) {
		this.ionEnergy = ionEnergy;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public double getStraggling() {
		return straggling;
	}

	public void setStraggling(double straggling) {
		this.straggling = straggling;
	}
	
}
