package com.thoughtworks.thoughtferret;

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
		if (value < sourceMin || value > sourceMax) {
			throw new IllegalArgumentException(String.format("Invalid source value: %d is not in [%d;%d]", value, sourceMin, sourceMax));
		}
		if (sourceMin == sourceMax) {
			return destMax;
		}
		return destMin + (destMax-destMin) * (value-sourceMin) / (sourceMax-sourceMin);
	}
	
}
