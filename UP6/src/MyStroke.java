import java.awt.*;
import java.awt.geom.*;

public class MyStroke implements Stroke {

    public MyStroke() {}

    public Shape createStrokedShape(Shape shape) {
    	GeneralPath strokedShape  = new GeneralPath(); 
		float[] coords = new float[2];
		float[] prevCoord = new float[2];
		for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i.next()){
			int type = i.currentSegment(coords);
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				strokedShape.moveTo(coords[0], coords[1]);
				break;

			case PathIterator.SEG_LINETO:
				markPoint(strokedShape, prevCoord[0], prevCoord[1], coords[0], coords[1]);		
				break;

			}
			prevCoord[0] = coords[0];
			prevCoord[1] = coords[1];
		}
		return strokedShape;
    }

    void markPoint(GeneralPath path, float x1, float y1, float x2, float y2) {
		path.lineTo(x1 + y2 - y1, y1 - x2 + x1);
		path.lineTo(x2, y2);
    }
}

