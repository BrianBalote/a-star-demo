package org.balote.a.star.demo.utils;

import java.util.ArrayList;

import org.balote.a.star.demo.models.CoordinateModel;

import android.util.Log;

public class PointsSorter {

	private static final String TAG = "PointsSorter";

	private CoordinateModel start;
	private ArrayList<CoordinateModel> points = null;

	public PointsSorter(CoordinateModel start, ArrayList<CoordinateModel> points) {

		this.start = start;
		this.points = points;
	}

	public ArrayList<CoordinateModel> sortPoints() {

		ArrayList<CoordinateModel> sortedPoints = new ArrayList<CoordinateModel>();

		ArrayList<CoordinateModel> checkPoints = points;

		sortedPoints.add(this.start);

		int i = 0;

		while (checkPoints.size() > 1) {

			CoordinateModel cm = findNearestPointFromListOfPoints(
					sortedPoints.get(i), checkPoints);

			checkPoints.remove(cm);

			sortedPoints.add(cm);

			i++;
		}

		sortedPoints.add(checkPoints.get(0));

		testSortedPoints(sortedPoints);

		return sortedPoints;
	}

	public void testSortedPoints(ArrayList<CoordinateModel> sortedPoints) {

		for (CoordinateModel cm : sortedPoints) {
			Log.i(TAG, "@ testSortedPoints() [" + cm.getX() + "," + cm.getY()
					+ "]");
		}
	}

	public CoordinateModel findNearestPointFromListOfPoints(
			CoordinateModel referencePoint,
			ArrayList<CoordinateModel> listOfPoints) {

		CoordinateModel nearest = null;

		double nearestD = 0.0;

		for (CoordinateModel cm : listOfPoints) {

			double computedD = computeDistance(referencePoint, cm);

			if (computedD < nearestD || nearestD <= 0.0) {

				nearestD = computedD;

				nearest = cm;
			}
		}

		return nearest;
	}

	public double computeDistance(CoordinateModel cm1, CoordinateModel cm2) {

		return computeDistance(cm1.getX(), cm2.getX(), cm1.getY(), cm2.getY());
	}

	public double computeDistance(int x1, int x2, int y1, int y2) {

		int xd = x2 - x1;
		int yd = y2 - y1;

		double d = Math.sqrt(xd * xd + yd * yd);

		return d;
	}
}
