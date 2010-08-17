package com.thoughtworks.thoughtferret.model.ensure;

public class DoubleToEnsure {

	private final double value;

	public DoubleToEnsure(double value) {
		this.value = value;
	}

	public void isBetween(double min, double max) {
		if (value < min || value > max) {
			throw new EnsureException(String.format("Double value [%s] out of boundaries. It should be between [%s,%s]", 
					value, min, max));
		}
	}

	public void isSmallerThan(int other) {
		if (value >= other) {
			throw new EnsureException(String.format("Double value [%s] should be smaller than [%s]", 
					value, other));
		}
	}

}
