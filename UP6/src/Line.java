import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.*;
import java.io.IOException;
import java.io.Serializable;

public class Line implements  Shape, Serializable, Cloneable, Transferable  {
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
	
	static DataFlavor decDataFlavor = new DataFlavor(Line.class, "Line");

    // This is a list of the flavors we know how to work with
    private static DataFlavor[] supportedFlavors = {decDataFlavor, DataFlavor.stringFlavor};


    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors.clone();
    }

    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return (dataFlavor.equals(decDataFlavor) || dataFlavor.equals(DataFlavor.stringFlavor));
    }

    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
        if (dataFlavor.equals(decDataFlavor)) {
            return this;
        } else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return toString();
        } else
            throw new UnsupportedFlavorException(dataFlavor);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) { // This should never happen
            return this;
        }
    }


    @Override
    public String toString() {
        return radius + " " + centerX + " " + centerY ;
    }


    static Line getFromString(String line) {
        String[] arr = line.split(" ");
        return new Line(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                Integer.parseInt(arr[2]));
    }

    void translate(int x, int y) {
        centerX += x;
        centerY += y;
    }

}

