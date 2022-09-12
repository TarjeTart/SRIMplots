package data;

public enum units {
	
	//energies
	KeV,
	MeV,
	//length
	A,
	um,
	mm,
	cm;
	
	public static units getUnit(String unit) {
		switch(unit) {
			case "keV":
				return KeV;
			case "MeV":
				return MeV;
			case "A":
				return A;
			case "um":
				return um;
			case "mm":
				return mm;
			case "cm":
				return cm;
		}
		return null;
			
	}
	
	public static String toString(units unit) {
		switch(unit) {
			case KeV:
				return "keV";
			case MeV:
				return "MeV";
			case A:
				return "A";
			case um:
				return "um";
			case mm:
				return "mm";
			case cm:
				return "cm";
		}
		return null;
			
	}
	
	public static double convertLength(double value, units start, units end) {
		switch(start) {
			case A:
				switch(end) {
					case A:
						return value;
					case um:
						return value*1e-4;
					case mm:
						return value*1e-7;
					case cm:
						return value*1e-9;
				}
			case um:
				switch(end) {
				case A:
					return value*1e+4;
				case um:
					return value;
				case mm:
					return value*.001;
				case cm:
					return value*1e-4;
				}
			case mm:
				switch(end) {
				case A:
					return value*1e+7;
				case um:
					return value*1000;
				case mm:
					return value;
				case cm:
					return value*.1;
				}
			case cm:
				switch(end) {
				case A:
					return value*1e+8;
				case um:
					return value*10000;
				case mm:
					return value*10;
				case cm:
					return value;
				}
		}
		return -1;
	}
	
	public static double toKeV(double value, units unit) {
		switch(unit) {
			case KeV:
				return value;
			case MeV:
				return value*1000;
		}
		return -1;
	}

}