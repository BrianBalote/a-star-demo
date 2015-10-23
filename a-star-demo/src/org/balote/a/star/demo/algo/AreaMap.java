package org.balote.a.star.demo.algo;

import java.util.ArrayList;

import android.graphics.Point;
import android.util.Log;

/**
 * The AreaMap holds information about the With, Height, Start position, Goal
 * position and Obstacles on the map. A place on the map is referred to by it's
 * (x,y) coordinates, where (0,0) is the upper left corner, and x is horizontal
 * and y is vertical.
 */
public class AreaMap {

	private static final String TAG = "AreaMap";

	private int mapWidth;
	private int mapHeight;
	private ArrayList<ArrayList<AStarNode>> map;
	private int startLocationX = 0;
	private int startLocationY = 0;
	private int goalLocationX = 0;
	private int goalLocationY = 0;
	private int[][] obstacleMap;

	public AreaMap(int mapWidth, int mapHeight, int[][] obstacleMap,
			int startX, int startY, int goalX, int goalY) {

		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.obstacleMap = obstacleMap;

		this.startLocationX = startX;
		this.startLocationY = startY;

		this.goalLocationX = goalX;
		this.goalLocationY = goalY;

		createMap();
	}

	private void createMap() {

		map = new ArrayList<ArrayList<AStarNode>>();

		Log.i(TAG, "@ createMap() mapWidth: " + mapWidth);
		Log.i(TAG, "@ createMap() mapHeight: " + mapHeight);

		for (int i = 0; i < mapHeight; i++) {

			ArrayList<AStarNode> list = new ArrayList<AStarNode>();

			for (int j = 0; j < mapWidth; j++) {

				AStarNode node = new AStarNode(j, i, this);

				try {
					if (obstacleMap[i][j] == 1) {
						node.setObstical(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(node);
			}

			Log.w(TAG, "@ createMap() list added at index: " + i);

			map.add(list);
		}

		Log.w(TAG, "@ createMap() map size: " + map.size());
		Log.w(TAG, "@ createMap() child map size: " + map.get(0).size());
	}

	public ArrayList<ArrayList<AStarNode>> getNodes() {
		return map;
	}

	public void setObstacle(int x, int y, boolean isObstical) {
		map.get(x).get(y).setObstical(isObstical);
	}

	public AStarNode getNode(int x, int y) {
		return map.get(y).get(x);
	}

	public int getStartLocationX() {
		return startLocationX;
	}

	public int getStartLocationY() {
		return startLocationY;
	}

	public AStarNode getStartNode() {
		return map.get(startLocationY).get(startLocationX);
	}

	public int getGoalLocationX() {
		return goalLocationX;
	}

	public int getGoalLocationY() {
		return goalLocationY;
	}

	public Point getGoalPoint() {
		return new Point(goalLocationY, goalLocationX);
	}

	public AStarNode getGoalNode() {
		return map.get(goalLocationY).get(goalLocationX);
	}

	public float getDistanceBetween(AStarNode node1, AStarNode node2) {

		if (node1.getX() == node2.getX() || node1.getY() == node2.getY()) {
			return 1;
		} else {
			return (float) 1.9;
		}
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void clear() {
		startLocationX = 0;
		startLocationY = 0;
		goalLocationX = 0;
		goalLocationY = 0;
		createMap();
	}
}
