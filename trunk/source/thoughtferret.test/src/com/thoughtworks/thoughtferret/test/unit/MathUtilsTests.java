package com.thoughtworks.thoughtferret.test.unit;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.ensure.Ensure;

public class MathUtilsTests extends TestCase {

	public void testProjectShouldCalculateInterpolatedValue() {
		int result = MathUtils.project(1, 5, 10, 50, 2);
		assertEquals(20, result);
	}
	
	public void testProjectShouldRejectOutOfRangeValues() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {
				MathUtils.project(1, 5, 10, 50, 7);
			}
		});
	}
	
	public void testProjectShouldRejectReversedRanges() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {
				MathUtils.project(1, 5, 50, 10, 2);
			}
		});
	}
	
	public void testProjectReverseShouldAcceptReversedRanges() {
		int result = MathUtils.projectReversed(1, 5, 50, 10, 2);
		assertEquals(40, result);
	}
	
	public void testProjectReversedShouldRejectNormalRanges() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {
				MathUtils.projectReversed(1, 5, 10, 50, 2);
			}
		});
	}
	
}
