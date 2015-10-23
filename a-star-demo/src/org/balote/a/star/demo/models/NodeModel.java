package org.balote.a.star.demo.models;

import java.util.ArrayList;

public class NodeModel extends CoordinateModel implements Comparable<NodeModel> {

	public static final int H_V_MOVE_COST = 10;
	public static final int D_MOVE_COST = 14;

	private boolean wasVisited = false;

	private float distanceFromStart;
	private float heuristicDistanceFromGoal;

	private NodeModel parent = null;

	private NodeModel northNode = null;
	private NodeModel southNode = null;
	private NodeModel eastNode = null;
	private NodeModel westNode = null;

	private NodeModel northWestNode = null;
	private NodeModel northEastNode = null;
	private NodeModel southWestNode = null;
	private NodeModel southEastNode = null;

	private ArrayList<NodeModel> neighborNodes = null;

	public NodeModel(int x, int y, int state) {

		this.x = x;
		this.y = y;
		this.state = state;
		
		neighborNodes = new ArrayList<NodeModel>(8);
	}

	public NodeModel getParent() {
		return parent;
	}

	public void setParent(NodeModel parent) {
		this.parent = parent;
	}

	public NodeModel getNorthNode() {
		return northNode;
	}

	public void setNorthNode(NodeModel northNode) {

		if (this.neighborNodes.contains(this.northNode)) {

			this.neighborNodes.remove(this.northNode);
		}
		this.neighborNodes.add(northNode);
		this.northNode = northNode;
	}

	public NodeModel getSouthNode() {
		return southNode;
	}

	public void setSouthNode(NodeModel southNode) {

		if (this.neighborNodes.contains(this.southNode)) {

			this.neighborNodes.remove(this.southNode);
		}
		this.neighborNodes.add(southNode);
		this.southNode = southNode;
	}

	public NodeModel getEastNode() {
		return eastNode;
	}

	public void setEastNode(NodeModel eastNode) {

		if (this.neighborNodes.contains(this.eastNode)) {

			this.neighborNodes.remove(this.eastNode);
		}
		this.neighborNodes.add(eastNode);
		this.eastNode = eastNode;
	}

	public NodeModel getWestNode() {
		return westNode;
	}

	public void setWestNode(NodeModel westNode) {

		if (this.neighborNodes.contains(this.westNode)) {

			this.neighborNodes.remove(this.westNode);
		}
		this.neighborNodes.add(westNode);
		this.westNode = westNode;
	}

	public NodeModel getNorthWestNode() {
		return northWestNode;
	}

	public void setNorthWestNode(NodeModel northWestNode) {

		if (this.neighborNodes.contains(this.northWestNode)) {

			this.neighborNodes.remove(this.northWestNode);
		}
		this.neighborNodes.add(northWestNode);
		this.northWestNode = northWestNode;
	}

	public NodeModel getNorthEastNode() {
		return northEastNode;
	}

	public void setNorthEastNode(NodeModel northEastNode) {

		if (this.neighborNodes.contains(this.northEastNode)) {

			this.neighborNodes.remove(this.northEastNode);
		}
		this.neighborNodes.add(northEastNode);
		this.northEastNode = northEastNode;
	}

	public NodeModel getSouthWestNode() {
		return southWestNode;
	}

	public void setSouthWestNode(NodeModel southWestNode) {

		if (this.neighborNodes.contains(this.southWestNode)) {

			this.neighborNodes.remove(this.southWestNode);
		}
		this.neighborNodes.add(southWestNode);
		this.southWestNode = southWestNode;
	}

	public NodeModel getSouthEastNode() {
		return southEastNode;
	}

	public void setSouthEastNode(NodeModel southEastNode) {

		if (this.neighborNodes.contains(this.southEastNode)) {

			this.neighborNodes.remove(this.southEastNode);
		}
		this.neighborNodes.add(southEastNode);
		this.southEastNode = southEastNode;
	}

	public static int gethVMoveCost() {
		return H_V_MOVE_COST;
	}

	public static int getdMoveCost() {
		return D_MOVE_COST;
	}

	public ArrayList<NodeModel> getNeighborNodes() {
		return neighborNodes;
	}

	public void setNeighborNodes(ArrayList<NodeModel> neighborNodes) {
		this.neighborNodes = neighborNodes;
	}

	public boolean isWasVisited() {
		return wasVisited;
	}

	public void setWasVisited(boolean wasVisited) {
		this.wasVisited = wasVisited;
	}

	public float getDistanceFromStart() {
		return distanceFromStart;
	}

	public void setDistanceFromStart(float distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}

	public float getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}

	@Override
	public int compareTo(NodeModel otherNode) {

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
