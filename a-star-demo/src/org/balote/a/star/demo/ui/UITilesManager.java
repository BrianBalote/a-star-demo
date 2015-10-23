package org.balote.a.star.demo.ui;

import java.util.ArrayList;

import org.balote.a.star.demo.constants.MapSettings;
import org.balote.a.star.demo.constants.NodeStates;
import org.balote.a.star.demo.models.CoordinateModel;

import android.graphics.Point;
import android.util.Log;

public class UITilesManager {

	private static final String TAG = "UITilesManager";

	private static UITilesManager instance = null;

	private int startX;
	private int startY;

	private int endX;
	private int endY;

	private ArrayList<UITileModel> uiTileList = null;

	private boolean hasStartPoint = false;
	private boolean hasEndPoint = false;

	private UITilesManager() {

		uiTileList = new ArrayList<UITileModel>(MapSettings.GRID_ITEMS_COUNT);
		reset();
	}

	public static UITilesManager getInstance() {

		if (instance == null) {

			instance = new UITilesManager();
		}

		return instance;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public ArrayList<UITileModel> getUiTileList() {
		return uiTileList;
	}

	public void setUiTileList(ArrayList<UITileModel> uiTileList) {
		this.uiTileList = uiTileList;
	}

	public boolean getHasStartPoint() {
		return hasStartPoint;
	}

	public void setHasStartPoint(boolean hasStartPoint) {
		this.hasStartPoint = hasStartPoint;
	}

	public boolean getHasEndPoint() {
		return hasEndPoint;
	}

	public void setHasEndPoint(boolean hasEndPoint) {
		this.hasEndPoint = hasEndPoint;
	}

	public void reset() {

		uiTileList.clear();

		for (int i = 0; i < MapSettings.GRID_ITEMS_COUNT; i++) {

			UITileModel cm = new UITileModel();

			int x = i / MapSettings.WIDTH;
			int y = i % MapSettings.WIDTH;

			cm.setX(x);
			cm.setY(y);
			cm.setState(NodeStates.PASSABLE);
			uiTileList.add(cm);
		}

		this.hasEndPoint = false;
		this.hasStartPoint = false;
	}

	public void updateWithShortestPath(ArrayList<Point> shortestPath) {

		// uiTileList.clear();

		for (int i = 0; i < MapSettings.GRID_ITEMS_COUNT; i++) {

			UITileModel cm = new UITileModel();

			if (uiTileList.get(i).getState() == NodeStates.PASSABLE) {

				int x = i / MapSettings.WIDTH;
				int y = i % MapSettings.WIDTH;

				cm.setX(x);
				cm.setY(y);

				Log.w(TAG, "x,y: " + x + "," + y);

				if (shortestPath.contains(new Point(y, x))) {

					cm.setState(NodeStates.PATH);

				}

				uiTileList.remove(i);
				uiTileList.add(i, cm);
			}
		}
	}

	public void updateNodeStateForUI(int position) {

		Log.i(TAG, "@ updateNodeStateForUI() position: " + position);

		UITileModel tm = uiTileList.get(position);

		Log.i(TAG, "@ updateNodeStateForUI() [" + tm.getX() + "," + tm.getY()
				+ "]");

		switch (tm.getState()) {

		case NodeStates.PASSABLE:
			tm.setState(NodeStates.BLOCKED);
			break;

		case NodeStates.BLOCKED:
			if (!hasStartPoint) {
				tm.setState(NodeStates.START);

				startX = tm.getX();
				startY = tm.getY();

				Log.w(TAG, "start x and y [" + startX + "," + startY + "]");

				hasStartPoint = true;
			} else if (!hasEndPoint) {
				tm.setState(NodeStates.END);

				endX = tm.getX();
				endY = tm.getY();

				Log.d(TAG, "end x and y [" + endX + "," + endY + "]");

				hasEndPoint = true;
			} else {
				tm.setState(NodeStates.CHECKPOINT);
			}
			break;

		case NodeStates.START:
			if (!hasEndPoint) {
				tm.setState(NodeStates.END);

				endX = tm.getX();
				endY = tm.getY();

				Log.d(TAG, "end x and y [" + endX + "," + endY + "]");

				hasEndPoint = true;
			} else {

				tm.setState(NodeStates.CHECKPOINT);
			}
			hasStartPoint = false;
			break;

		case NodeStates.END:
			tm.setState(NodeStates.CHECKPOINT);
			hasEndPoint = false;
			break;

		case NodeStates.CHECKPOINT:
			tm.setState(NodeStates.PASSABLE);
			break;
		}
	}

	public ArrayList<CoordinateModel> getCheckpoints() {

		return getCheckpoints(this.uiTileList);

	}

	public ArrayList<CoordinateModel> getCheckpoints(ArrayList<UITileModel> list) {

		ArrayList<CoordinateModel> checkpoints = new ArrayList<CoordinateModel>();

		for (UITileModel um : list) {

			if (um.getState() == NodeStates.CHECKPOINT) {

				checkpoints.add(um);
			}
		}

		testCheckPoints(checkpoints);
		
		return checkpoints;
	}
	
	public void testCheckPoints(ArrayList<CoordinateModel> checkPoints) {

		for (CoordinateModel cm : checkPoints) {
			Log.d(TAG, "@ testCheckPoints(): [" + cm.getX() + "," + cm.getY() + "]");
		}
	}

	public int[][] generateMap() {

		int[][] map = new int[MapSettings.HEIGHT][MapSettings.WIDTH];

		for (int i = 0; i < MapSettings.HEIGHT; i++) {

			for (int j = 0; j < MapSettings.WIDTH; j++) {

				int position = i * MapSettings.WIDTH + j;

				map[i][j] = this.uiTileList.get(position).getState();

				// Log.d(TAG, "@ generateMap() position: " + position);
				// Log.d(TAG, "@ generateMap() [" + i + "," + j + "]");
			}
		}

		testGeneratedMap(map);

		return map;
	}

	private void testGeneratedMap(int map[][]) {

		for (int i = 0; i < MapSettings.HEIGHT; i++) {

			StringBuffer sb = new StringBuffer();

			for (int j = 0; j < MapSettings.WIDTH; j++) {

				sb.append(map[i][j]);
			}

			Log.d(TAG, "@ generateMap(): " + sb.toString());
		}
	}
}
