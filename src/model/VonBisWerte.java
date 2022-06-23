package model;

public class VonBisWerte {

	private double Von;
	private double Bis;

	public VonBisWerte(double v, double b) {
		Von = v;
		Bis = b;
	}

	public String toString(String prefix) {
		return String.format("%s von %.2f bis %.2f", prefix, Von, Bis);
	}

}
