package com.thoughtworks.thoughtferret;

import com.thoughtworks.thoughtferret.view.moodgraph.ensure.Ensure;

import android.graphics.Point;
import android.graphics.Rect;

public class MathUtils {

	public static int clamp(int value, int min, int max) {
		if (value < min) {
			value = min;
		}
		if (value > max) {
			value = max;
		}
		return value;
	}

	public static Point getMiddle(Point a, Point b) {
		Point mid = new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
		return mid;
	}
	
	public static Rect getRect(Point origin, int width, int height) {
		return new Rect(origin.x, origin.y, origin.x + width, origin.y + height);
	}
	
	public static int getRandom(int min, int max) {
		double rnd = min + (max - min) * Math.random();
		return (int) rnd;
	}
	
	public static int project(int sourceMin, int sourceMax, int destMin, int destMax, int value) {
		return (int) project(sourceMin, sourceMax, destMin, destMax, (double) value);
	}
	
	public static double project(int sourceMin, int sourceMax, int destMin, int destMax, double value) {
		Ensure.that(value).isBetween(sourceMin, sourceMax);
		if (sourceMin == sourceMax) {
			return destMax;
		}
		double result = destMin + (destMax-destMin) * (value-sourceMin) / (sourceMax-sourceMin);
		Ensure.that(result).isBetween(destMin, destMax);
		return result;
	}
	
	public static int projectReversed(int sourceMin, int sourceMax, int destMin, int destMax, int value) {
		return (int) projectReversed(sourceMin, sourceMax, destMin, destMax, (double) value);
	}
	
	public static double projectReversed(int sourceMin, int sourceMax, int destMin, int destMax, double value) {
		Ensure.that(value).isBetween(sourceMin, sourceMax);
		if (sourceMin == sourceMax) {
			return destMax;
		}
		double result = destMin - (destMin-destMax) * (value-sourceMin) / (sourceMax-sourceMin);
		Ensure.that(result).isBetween(destMax, destMin);
		return result;
	}
	
}
