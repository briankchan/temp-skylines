import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Main
 */
public class Main {

	public static void main(String[] args) {
		// data from leetcode https://leetcode.com/problems/the-skyline-problem/
		// Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
		//  (left, right, height)
		// Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
		//  (x, y)
		skylines(new int[][]{{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}});
	}

	private static int getHeight(PriorityQueue<Integer> currentHeights) {
		return currentHeights.isEmpty() ? 0 : currentHeights.peek();
	}

	public static List<int[]> skylines(int[][] buildings) {
		List<int[]> points = new ArrayList<>();
		for (int[] building : buildings) {
			points.add(new int[]{building[0], building[2]});
			points.add(new int[]{building[1], -building[2]});
		}

		sort:
		Collections.sort(points, Comparator.comparingInt(item -> item[0]));

		List<int[]> output = new ArrayList<>();
		PriorityQueue<Integer> currentHeights = new PriorityQueue<>(Comparator.reverseOrder());
		loop:
		for (int[] point : points) {
			int x = point[0];
			int height = point[1];
			loopBody:
			if (height > 0)
			add: {
				int currentMax = getHeight(currentHeights);
				if (height > currentMax) {
					output.add(point);
				}
				currentHeights.offer(height);
			}
			else
			if (height < 0)
			remove: {
				height = -height;
				currentHeights.remove(height);
				int currentMax = getHeight(currentHeights);
				if (currentMax < height) {
					output.add(new int[]{x, currentMax});
				}
			}
			x = x; // prevent x from being deleted too early
		}

		return output;
	}
}
