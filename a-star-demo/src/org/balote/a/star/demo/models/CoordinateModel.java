package org.balote.a.star.demo.models;

import org.balote.a.star.demo.constants.NodeStates;

public class CoordinateModel {

	protected int state = NodeStates.PASSABLE;

	protected int x;
	protected int y;

	public CoordinateModel() {

	}

	public CoordinateModel(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isEquals(CoordinateModel cm) {

		if (this.x == cm.getX() && this.y == cm.getY()) {

			return true;
		}

		return false;
	}
}
