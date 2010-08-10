package com.thoughtworks.thoughtferret.model.ensure;

public class Ensure {

	public static DoubleToEnsure that(double value) {
		return new DoubleToEnsure(value);
	}

	public static void thatBreaksContract(Runnable codeThatBreaksContract) {
		try {
			codeThatBreaksContract.run();
			throw new IllegalStateException("Should have broken the contract");
		} catch (EnsureException e) {
		}
	}

}
