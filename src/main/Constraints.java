package main;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Constraints extends GridBagConstraints {

	private static final long serialVersionUID = -3888761088562709387L;
	
	public Constraints(int gridx, int gridy, int gridwidth, int gridheight) {
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth; 
		this.gridheight = gridheight; 
	}

	public Constraints setWeight(double weightx, double weighty) {
		this.weightx = weightx;
		this.weighty = weighty;
		return this;
	}

	public Constraints setAnchor(int anchor) {
		this.anchor = anchor;
		return this;
	}

	public Constraints setFill(int fill) {
		this.fill = fill;
		return this;
	}

	public Constraints setInsets(int top, int left, int bottom, int right) {
		this.insets = new Insets(top, left, bottom, right);
		return this;
	}

}
