package org.balote.a.star.demo.algo;

import java.util.ArrayList;

import android.graphics.Point;
import android.util.Log;

public class AStarNode implements Comparable<AStarNode> {

	private static final String TAG = "AStarNode";

	/* Nodes that this is connected to */
	AreaMap map = null;
	boolean visited;
	float distanceFromStart;
	float heuristicDistanceFromGoal;
	AStarNode previousNode;
	int x;
	int y;
	public boolean isObstacle;
	public boolean isStart;
	public boolean isGoal;
	public boolean isPath;

	AStarNode(int x, int y, AreaMap map) {

		Log.i(TAG, "@ AStarNode constructor() [x,y]: " + x + "," + y);

		this.x = x;
		this.y = y;
		this.map = map;
		this.visited = false;
		this.distanceFromStart = Integer.MAX_VALUE;
		this.isObstacle = false;
		this.isStart = false;
		this.isGoal = false;
	}

	AStarNode(int x, int y, AreaMap map, boolean visited,
			int distanceFromStart, boolean isObstical, boolean isStart,
			boolean isGoal) {

		Log.i(TAG, "@ AStarNode constructor() [x,y]: " + x + "," + y);

		this.x = x;
		this.y = y;
		this.map = map;
		this.visited = visited;
		this.distanceFromStart = distanceFromStart;
		this.isObstacle = isObstical;
		this.isStart = isStart;
		this.isGoal = isGoal;
	}

	public ArrayList<AStarNode> getNeighborList() {

		ArrayList<AStarNode> neighborList = new ArrayList<AStarNode>();

		Log.i(TAG, "@ getNeighborList() [x,y]: " + x + "," + y);
		Log.i(TAG, "@ getNeighborList() map width: " + map.getMapWidth());
		Log.i(TAG, "@ getNeighborList() map height: " + map.getMapHeight());
		
		if (!(y==0)) {
			neighborList.add(map.getNode(x, (y-1)));
		}
		if (!(y==0) && !(x==(map.getMapWidth()-1))) {
			neighborList.add(map.getNode(x+1,y-1));
		}
		if (!(x==(map.getMapWidth()-1))) {
			neighborList.add(map.getNode(x+1,y));
		}
		if (!(x==(map.getMapWidth()-1)) && !(y==(map.getMapHeight()-1))) {
			neighborList.add(map.getNode(x+1,y+1));
		}
		if (!(y==(map.getMapHeight()-1))) {
			neighborList.add(map.getNode(x,y+1));
		}
		if (!(x==0) && !(y==(map.getMapHeight()-1))) {
			neighborList.add(map.getNode(x-1,y+1));
		}
		if (!(x==0)) {
			neighborList.add(map.getNode(x-1,y));
		}
		if (!(x==0) && !(y==0)) {
			neighborList.add(map.getNode(x-1,y-1));
		}
		return neighborList;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public float getDistanceFromStart() {
		return distanceFromStart;
	}

	public void setDistanceFromStart(float f) {
		this.distanceFromStart = f;
	}

	public AStarNode getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(AStarNode previousNode) {
		this.previousNode = previousNode;
	}

	public float getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
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

	public Point getPoint() {
		return new Point(x, y);
	}

	public boolean isObstical() {
		return isObstacle;
	}

	public void setObstical(boolean isObstical) {
		this.isObstacle = isObstical;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public boolean isPath() {
		return isPath;
	}

	public void setPath(boolean isPath) {
		this.isPath = isPath;
	}

	public boolean equals(AStarNode node) {
		return (node.x == x) && (node.y == y);
	}

	public int compareTo(AStarNode otherNode) {
		float thisTotalDistanceFromGoal = heuristicDistanceFromGoal
				+ distanceFromStart;
		float otherTotalDistanceFromGoal = otherNode
				.getHeuristicDistanceFromGoal()
				+ otherNode.getDistanceFromStart();

		if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal) {
			return -1;
		} else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal) {
			return 1;
		} else {
			return 0;
		}
	}
}