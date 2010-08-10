package com.thoughtworks.thoughtferret.view.moodgraph.ensure;

public class DoubleToEnsure {

	private final Double value;

	public DoubleToEnsure(Double value) {
		this.value = value;
	}

	public void isBetween(double min, double max) {
		if (value < min || value > max) {
			throw new EnsureException(String.format("Double value [%s] out of boundaries. It should be between [%s,%s]", 
					value, min, max));
		}
	}

}
