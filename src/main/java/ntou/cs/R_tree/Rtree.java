package ntou.cs.R_tree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;

import static com.github.davidmoten.rtree.Entry.entry;
import static com.github.davidmoten.rtree.geometry.Geometries.point;
import static com.github.davidmoten.rtree.geometry.Geometries.rectangle;

import java.util.List;

public class Rtree {
	private RTree<Object, Point> rtreePoint;
	private RTree<Object, Rectangle> rtreeRetangle;
	private final boolean isPointRtree;
	
	public Rtree(int minChildren, int maxChildren, boolean isStar, boolean isPoint){
		isPointRtree = isPoint;
		if(isPoint){
			rtreePoint = RTree.minChildren(minChildren).maxChildren(maxChildren).create();			
		}
		else{
			rtreeRetangle = RTree.minChildren(minChildren).maxChildren(maxChildren).create();			
		}
	}
	
	public void put(double x, double y, Object value){
		rtreePoint = rtreePoint.add(entry(value, point(x, y)));
	}
	
	public void put(double x1, double y1, double x2, double y2, Object value){
		rtreeRetangle=rtreeRetangle.add(entry(value, rectangle(x1, y1, x2, y2)));
	}
	
	public void delete(double x, double y, Object value){
		rtreePoint=rtreePoint.delete(entry(value, point(x, y)));
	}
	
	public void delete(double x1, double y1, double x2, double y2, Object value){
		rtreeRetangle=rtreeRetangle.delete(entry(value, rectangle(x1, y1, x2, y2)));		
	}
	
	public List<Entry<Object, Point>> find(double x, double y){
		return rtreePoint.search(point(x, y)).toList().toBlocking().single();		
	}
	
	public List<Entry<Object, Point>> find(double x, double y, double maxDistance){
		return rtreePoint.search(point(x, y), maxDistance).toList().toBlocking().single();			
	}

	public List<Entry<Object, Rectangle>> find(double x1, double y1, double x2, double y2){
		return rtreeRetangle.search(rectangle(x1, y1, x2, y2)).toList().toBlocking().single();			
	}

	public List<Entry<Object, Rectangle>> find(double x1, double y1, double x2, double y2, double maxDistance){
		return rtreeRetangle.search(rectangle(x1, y1, x2, y2), maxDistance).toList().toBlocking().single();			
	}
}
