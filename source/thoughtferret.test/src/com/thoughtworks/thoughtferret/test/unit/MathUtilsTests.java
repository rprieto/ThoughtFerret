package com.thoughtworks.thoughtferret.test.unit;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.ensure.Ensure;

public class MathUtilsTests extends TestCase {

	public void testShouldNotClampValueInRange() {
		int notClamped = MathUtils.clamp(7, 5, 8);
		assertEquals(7, notClamped);
	}
	
	public void testShouldClampValueBelowMinimum() {
		int clamped = MathUtils.clamp(2, 5, 8);
		assertEquals(5, clamped);
	}

	public void testShouldClampValueAboveMaximum() {
		int clamped = MathUtils.clamp(10, 5, 8);
		assertEquals(8, clamped);
	}
	
	public void testShouldNotWrapValueBelowMaximum() {
		int notWrapped = MathUtils.wrap(3, 5);
		assertEquals(3, notWrapped);
	}
	
	public void testShouldWrapValueAboveMaximum() {
		int wrapped = MathUtils.wrap(7, 5);
		assertEquals(2, wrapped);
	}
	
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
