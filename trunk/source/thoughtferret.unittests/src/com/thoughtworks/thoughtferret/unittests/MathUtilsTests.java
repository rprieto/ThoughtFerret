package com.thoughtworks.thoughtferret.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.ensure.Ensure;

public class MathUtilsTests {

	@Test
	public void projectShouldCalculateInterpolatedValue() {
		int result = MathUtils.project(1, 5, 10, 50, 2);
		assertEquals(20, result);
	}
	
	@Test
	public void projectShouldRejectOutOfRangeValues() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {
				MathUtils.project(1, 5, 10, 50, 7);
			}
		});
	}
	
	@Test
	public void projectShouldRejectReversedRanges() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {
				MathUtils.project(1, 5, 50, 10, 2);
			}
		});
	}
	
	@Test
	public void projectReverseShouldAcceptReversedRanges() {
		int result = MathUtils.projectReversed(1, 5, 50, 10, 2);
		assertEquals(40, result);
	}
	
	@Test
	public void projectReversedShouldRejectNormalRanges() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {
				MathUtils.projectReversed(1, 5, 10, 50, 2);
			}
		});
	}
	
}
