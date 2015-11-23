package ntou.cs.R_tree;

import java.util.ArrayList;
import java.util.List;

import static com.github.davidmoten.rtree.Entry.entry;
import static com.github.davidmoten.rtree.geometry.Geometries.rectangle;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	List<Entry<String, Geometry>> list = new ArrayList<Entry<String, Geometry>>();
        for (long i = 0; i < 200; i++)
            list.add(entry(String.valueOf(i), (Geometry) r(Math.random() * 100, Math.random() * 100)));
        
    	RTree<String, Geometry> tree = RTree.minChildren(5).maxChildren(10).create();
    	tree = tree.add(list);
    	System.out.println(tree.asString());
    	tree.visualize(2000,2000)
        .save("target/mytree.png", "PNG");
    	
    	tree = RTree.minChildren(5).maxChildren(10).star().create();
    	tree = tree.add(list);
    	tree.visualize(2000,2000)
        .save("target/mytreeStar.png", "PNG");
    }
    
    private static Rectangle r(double n, double m) {
        return rectangle(n, m, n + 1, m + 1);
    }

    static Rectangle random() {
        return r(Math.random() * 1000, Math.random() * 1000);
    }
}
