import java.awt.*;
import java.awt.geom.*;

public class Line implements Shape {
    double centerX, centerY;
    double radius, startAngle;                

    public Line(double centerX, double centerY, double radius)
    {
		this.centerX = centerX; 	
		this.centerY = centerY;
		this.radius = radius;	
		this.startAngle = 0;
		if (radius < 0)
		    throw new IllegalArgumentException("Astroida radius must be >= 0");
	}

    public Rectangle getBounds() {
	return new Rectangle((int)(centerX-radius),
			     (int)(centerY-radius),
			     (int)(radius*2), (int)(radius*2));
    }

    public Rectangle2D getBounds2D() {
	return new Rectangle2D.Double(centerX-radius, centerY-radius,
				      radius*2, radius*2);
    }

    public boolean contains(double x, double y) { return false; }
    public boolean contains(Point2D p) { return false; }
    public boolean contains(Rectangle2D r) { return false; }
    public boolean contains(double x, double y, double w, double h) {
	return false;
    }

    public boolean intersects(double x, double y, double w, double h) {
	Shape approx = new Ellipse2D.Double(centerX-radius,
					    centerY-radius,
					    radius*2, radius*2);
	return approx.intersects(x, y, w, h);
    }

    public boolean intersects(Rectangle2D r) {
	return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    
    public PathIterator getPathIterator(AffineTransform at) {
    	return new LineIterator();
    }
    
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
    	return new LineIterator();
    }

	class LineIterator implements PathIterator {
		
		double angle = startAngle;
		boolean done = false;         
	
		public LineIterator() {}

		public int getWindingRule() { return WIND_NON_ZERO; }
	
		public boolean isDone() { return done; }
	
		public int currentSegment(float[] coords) {
		    coords[0] = (float)(centerX + radius*Math.pow(Math.cos(angle), 3));
		    coords[1] = (float)(centerY + radius*Math.pow(Math.sin(angle), 3));
		    if (angle > 2*Math.PI) done = true;
		    if (angle == startAngle) return SEG_MOVETO;
		    return SEG_LINETO;
		}
		
		public int currentSegment(double[] coords) {
		    coords[0] = centerX + radius*Math.pow(Math.cos(angle), 3);
		    coords[1] = centerY + radius*Math.pow(Math.sin(angle), 3);
		    if (angle > 2*Math.PI) done = true;
		    if (angle == startAngle) return SEG_MOVETO;
		    else return SEG_LINETO;
		}
	
		public void next() {
		    if (done) return;
			angle += Math.PI/36; 
		}
    }
}

