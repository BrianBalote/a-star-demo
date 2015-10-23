package org.balote.a.star.demo.ui;

import java.util.ArrayList;

import org.balote.a.star.demo.R;
import org.balote.a.star.demo.algo.AStar;
import org.balote.a.star.demo.algo.AStarHeuristic;
import org.balote.a.star.demo.algo.AStarNode;
import org.balote.a.star.demo.algo.AreaMap;
import org.balote.a.star.demo.algo.DiagonalHeuristic;
import org.balote.a.star.demo.constants.MapSettings;
import org.balote.a.star.demo.constants.NodeStates;
import org.balote.a.star.demo.models.CoordinateModel;
import org.balote.a.star.demo.utils.PointsSorter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private GridView myGridView = null;
	private Button runAStarButton = null;
	private Button reset = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.i(TAG, "@ onCreate()");

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		MyGridAdapter myGridAdapter = new MyGridAdapter();

		myGridView = (GridView) findViewById(R.id.gw);
		myGridView.setAdapter(myGridAdapter);

		runAStarButton = (Button) findViewById(R.id.run_a_star);
		runAStarButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				UITilesManager utm = UITilesManager.getInstance();

				int sX = utm.getStartX();
				int sY = utm.getStartY();

				int eX = utm.getEndX();
				int eY = utm.getEndY();

				Log.d(TAG, "start: [" + sX + "," + sY + "]");
				Log.d(TAG, "end: [" + eX + "," + eY + "]");

				CoordinateModel startCm = new CoordinateModel(sX, sY);

				PointsSorter ps = new PointsSorter(startCm, utm
						.getCheckpoints());
				ArrayList<CoordinateModel> sortedCheckPoints = ps.sortPoints();
				sortedCheckPoints.add(new CoordinateModel(utm.getEndX(), utm
						.getEndY()));

				int[][] myMap = utm.generateMap();
				AStarHeuristic heuristic = new DiagonalHeuristic();

				ArrayList<Point> connectedPaths = new ArrayList<Point>();

				for (int i = 0; i < sortedCheckPoints.size(); i++) {

					if (i + 1 < sortedCheckPoints.size()) {

						sX = sortedCheckPoints.get(i).getX();
						sY = sortedCheckPoints.get(i).getY();

						eX = sortedCheckPoints.get(i + 1).getX();
						eY = sortedCheckPoints.get(i + 1).getY();

						AreaMap map = new AreaMap(MapSettings.WIDTH,
								MapSettings.HEIGHT, myMap, sY, sX, eY, eX);

						AStar aStar = new AStar(map, heuristic);

						ArrayList<Point> shortestPath = aStar.calcShortestPath(
								sY, sX, eY, eX);

						connectedPaths.addAll(shortestPath);
					}
				}

				utm.updateWithShortestPath(connectedPaths);
				MyGridAdapter myGridAdapter = new MyGridAdapter();
				myGridView.invalidateViews();
				myGridView.setAdapter(myGridAdapter);

			}
		});

		reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				UITilesManager.getInstance().reset();

				MyGridAdapter myGridAdapter = new MyGridAdapter();
				myGridView.invalidateViews();
				myGridView.setAdapter(myGridAdapter);
			}
		});
	}

	public void printMap(AreaMap map, ArrayList<Point> shortestPath) {
		AStarNode node;
		for (int y = 0; y < map.getMapHeight(); y++) {

			if (y == 0) {
				for (int i = 0; i <= map.getMapHeight(); i++)
					System.out.print("-");
				System.out.println();
			}
			System.out.print("|");

			for (int x = 0; x < map.getMapWidth(); x++) {
				node = map.getNode(x, y);

				if (node.isObstacle) {
					System.out.print("X");
				} else if (node.isStart) {
					System.out.print("s");
				} else if (node.isGoal) {
					System.out.print("g");
				} else if (shortestPath.contains(new Point(node.getX(), node
						.getY()))) {
					System.out.print("#");
				} else {
					System.out.print("0");
				}
				if (y == map.getMapHeight())
					System.out.print("_");
			}

			System.out.print("|");
			System.out.println();
		}
		for (int i = 0; i <= map.getMapHeight(); i++)
			System.out.print("-");
	}

	@Override
	public void onResume() {

		super.onResume();

		UITilesManager.getInstance().reset();
	}

	class MyGridAdapter extends BaseAdapter {

		public MyGridAdapter() {

			Log.d(TAG, "@ constructor");
		}

		@Override
		public int getCount() {
			return UITilesManager.getInstance().getUiTileList().size();
		}

		@Override
		public Object getItem(int position) {
			return UITilesManager.getInstance().getUiTileList().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			View gridViewItem = convertView;
			MyPlaceHolder holder = null;

			if (convertView == null) {

				LayoutInflater inflater = (LayoutInflater) MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				gridViewItem = inflater.inflate(R.layout.grid_view_item,
						parent, false);

				holder = new MyPlaceHolder();

				holder.gridItemContainer = gridViewItem
						.findViewById(R.id.grid_item_container);

				gridViewItem.setTag(holder);

			} else {

				holder = (MyPlaceHolder) gridViewItem.getTag();
			}

			final View tempView = holder.gridItemContainer;
			updateColor(tempView, UITilesManager.getInstance().getUiTileList()
					.get(position).getState());

			holder.gridItemContainer
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							UITilesManager.getInstance().updateNodeStateForUI(
									position);

							UITileModel uiTileModel = UITilesManager
									.getInstance().getUiTileList()
									.get(position);

							Log.w(TAG, "tapped tile [" + uiTileModel.getX()
									+ "," + uiTileModel.getY() + "]");

							updateColor(tempView, uiTileModel.getState());
						}
					});

			return gridViewItem;
		}

		class MyPlaceHolder {

			View gridItemContainer;
		}

		private void updateColor(View view, int state) {

			switch (state) {

			case NodeStates.PASSABLE:

				view.setBackgroundColor(MainActivity.this.getResources()
						.getColor(R.color.passable));
				break;

			case NodeStates.BLOCKED:

				view.setBackgroundColor(MainActivity.this.getResources()
						.getColor(R.color.blocked));
				break;

			case NodeStates.START:

				view.setBackgroundColor(MainActivity.this.getResources()
						.getColor(R.color.start));
				break;

			case NodeStates.END:

				view.setBackgroundColor(MainActivity.this.getResources()
						.getColor(R.color.end));
				break;

			case NodeStates.CHECKPOINT:

				view.setBackgroundColor(MainActivity.this.getResources()
						.getColor(R.color.checkpoint));
				break;

			case NodeStates.PATH:
				view.setBackgroundColor(MainActivity.this.getResources()
						.getColor(R.color.path));
				break;
			}
		}

	}

}
