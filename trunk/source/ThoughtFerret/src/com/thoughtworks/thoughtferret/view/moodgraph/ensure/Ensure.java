package com.thoughtworks.thoughtferret.view.moodgraph.ensure;

public class Ensure {

	public static DoubleToEnsure that(Double value) {
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
