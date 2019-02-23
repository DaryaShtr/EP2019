import java.awt.*;
import java.awt.geom.*;

public class Symbol implements Shape{

	@Override
	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return contains(arg0.getX(), arg0.getY());
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int) (0), (int) (0), (int) (size), (int) (size));
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(0, 0, size, size);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return getBounds().intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return getBounds().intersects(arg0, arg1, arg2, arg3);
	}
	
    @Override
    public boolean contains(double x, double y) {
        if (x > 0 || y > 0) return true;
        else return false;
    }

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	int size;
	Color symbolColor;
	Color backgroundColor;
	
	public Symbol(int size) {
		symbolColor = backgroundColor = Color.yellow;
		this.size = size;
	}
	
	public void paint(Graphics2D g) {
		g.setPaint(new GradientPaint(400, 250, new Color(90, 90, 90), 0, 250, new Color(220, 220, 220)));
		g.fillRect(0, 0, size, size);    
		g.setColor(symbolColor);
		g.setStroke(new BasicStroke(70.0f));                   
		g.setFont(new Font("TimesRoman", Font.BOLD, size));
		g.drawString("?", 80, 340);
		g.drawRect(0, 0, size, size);
	}
	
}
