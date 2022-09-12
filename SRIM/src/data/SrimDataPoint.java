package data;

public class SrimDataPoint {

	private double ionEnergy, range, straggling;
	
	private units energyUnit, rangeUnit,straggleUnit;

	public SrimDataPoint(double ionEnergy, String energyUnit, double range, String rangeUnit, double straggling, String straggleUnit) {
		this.ionEnergy = ionEnergy;
		this.setEnergyUnit(units.getUnit(energyUnit));
		this.range = range;
		this.setRangeUnit(units.getUnit(rangeUnit));
		this.straggling = straggling;
		this.setStraggleUnit(units.getUnit(straggleUnit));
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

	public units getRangeUnit() {
		return rangeUnit;
	}

	public void setRangeUnit(units rangeUnit) {
		this.rangeUnit = rangeUnit;
	}

	public units getStraggleUnit() {
		return straggleUnit;
	}

	public void setStraggleUnit(units straggleUnit) {
		this.straggleUnit = straggleUnit;
	}

	public units getEnergyUnit() {
		return energyUnit;
	}

	public void setEnergyUnit(units energyUnit) {
		this.energyUnit = energyUnit;
	}
	
}
