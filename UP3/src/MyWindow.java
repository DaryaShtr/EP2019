import java.applet.*;
import java.awt.*;

public class MyWindow extends Applet implements Runnable{
	  	
	  	int xWindowSize = 900;
	  	int yWindowSize = 900;
	  	Thread t = null;

		public void init() { 
	   	    this.setSize(xWindowSize, yWindowSize);
	        setBackground(new Color(255, 218, 185));
	    } 
	    
		public void paint(Graphics g){
			Graphics2D gr = (Graphics2D)g;
			gr.setColor(new Color (250, 128, 114));
			MyStroke stroke = new MyStroke();
			gr.setStroke(new BasicStroke(7.0f));
			gr.draw(stroke.createStrokedShape(new Line(xWindowSize/2, yWindowSize/2, xWindowSize/3)));
	    }
		
		public void run() { 
			repaint();
	  }

}

