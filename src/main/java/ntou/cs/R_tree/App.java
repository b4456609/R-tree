package ntou.cs.R_tree;

import java.io.IOException;
import java.util.List;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Rectangle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        
    	Rtree<Object, Rectangle> tree = new Rtree<Object, Rectangle>(5, 10, true);
    	try {
			tree.put("1000-rec.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(tree);
    	tree.visualize(2000,2000,"target/mytree.png");
	
    	List<Entry<Object, Rectangle>> entries = tree.find(50,50,100);
    	System.out.println(entries.size());
    	for(Entry<Object, Rectangle> item : entries){
        	System.out.println(item);
    	}
    	
    	tree.delete(71.84164, 37.1636, 72.84164, 38.1636, null);
    	
    	entries = tree.find(50,50,100);
    	System.out.println(entries.size());
    	for(Entry<Object, Rectangle> item : entries){
        	System.out.println(item);
    	}

    }
}
