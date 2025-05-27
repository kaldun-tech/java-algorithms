/******************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {

    public static void main(String[] args) {
        // Set up the drawing canvas
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        
        // Draw initial instructions
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.5, 0.9, "KdTree Visualizer");
        StdDraw.text(0.5, 0.85, "Click anywhere to add points");
        StdDraw.text(0.5, 0.8, "Red lines are vertical splits, blue lines are horizontal splits");
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5); // Draw the unit square boundary
        StdDraw.show();
        
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        KdTree kdtree = new KdTree();
        
        // Main interaction loop
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("Adding point: %8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                
                if (rect.contains(p)) {
                    kdtree.insert(p);
                    
                    // Redraw everything
                    StdDraw.clear();
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(0.5, 0.95, "KdTree Visualizer");
                    StdDraw.text(0.5, 0.9, "Points: " + (kdtree.isEmpty() ? 0 : kdtree.size()));
                    
                    // Draw the tree
                    kdtree.draw();
                    
                    // Draw the unit square boundary
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setPenRadius(0.001);
                    StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
                    
                    StdDraw.show();
                    
                    // Wait a bit to avoid multiple insertions from a single click
                    StdDraw.pause(200);
                }
            }
            
            StdDraw.pause(20);
        }
    }
}
