package ntou.cs.R_tree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;

import static com.github.davidmoten.rtree.Entry.entry;
import static com.github.davidmoten.rtree.geometry.Geometries.point;
import static com.github.davidmoten.rtree.geometry.Geometries.rectangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Rtree<T, S extends Geometry> {
	private RTree<T, S> tree;

	public Rtree(int minChildren, int maxChildren, boolean isStar) {
		if (isStar)
			tree = RTree.minChildren(minChildren).maxChildren(maxChildren).star().create();
		else
			tree = RTree.minChildren(minChildren).maxChildren(maxChildren).create();
	}

	@SuppressWarnings("unchecked")
	public void put(double x, double y, Object value) {
		tree = tree.add((Entry<? extends T, ? extends S>) entry(value, point(x, y)));
	}

	@SuppressWarnings("unchecked")
	public void put(double x1, double y1, double x2, double y2, Object value) {
		tree = tree.add((Entry<? extends T, ? extends S>) entry(value, rectangle(x1, y1, x2, y2)));
	}

	public void put(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line = br.readLine();

			while (line != null) {
				System.out.println(line);
				String[] intValues = line.split(" ");
				if (intValues.length == 2) {
					put(Double.parseDouble(intValues[0]), Double.parseDouble(intValues[1]), null);
				} else {
					put(Double.parseDouble(intValues[0]), Double.parseDouble(intValues[1]),
							Double.parseDouble(intValues[2]), Double.parseDouble(intValues[3]), null);
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(double x, double y, Object value) {
		tree = tree.delete((Entry<? extends T, ? extends S>) entry(value, point(x, y)));
	}

	@SuppressWarnings("unchecked")
	public void delete(double x1, double y1, double x2, double y2, Object value) {
		tree = tree.delete((Entry<? extends T, ? extends S>) entry(value, rectangle(x1, y1, x2, y2)));
	}

	public List<Entry<T, S>> find(double x, double y) {
		return tree.search(point(x, y)).toList().toBlocking().single();
	}

	public List<Entry<T, S>> find(double x, double y, double maxDistance) {
		return tree.search(point(x, y), maxDistance).toList().toBlocking().single();
	}

	public List<Entry<T, S>> find(double x1, double y1, double x2, double y2) {
		return tree.search(rectangle(x1, y1, x2, y2)).toList().toBlocking().single();
	}

	public List<Entry<T, S>> find(double x1, double y1, double x2, double y2, double maxDistance) {
		return tree.search(rectangle(x1, y1, x2, y2), maxDistance).toList().toBlocking().single();
	}

	public void visualize(int x, int y, String filename) {
		tree.visualize(x, y).save(filename, "PNG");
	}

	@Override
	public String toString() {
		return tree.asString();
	}
}
