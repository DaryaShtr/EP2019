import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.util.*;
import javax.swing.*;


public class MyWindow extends Applet implements Runnable{
	   
		private volatile boolean fSuspend = false;
	  	public synchronized boolean getFSuspend() {
	  		return fSuspend;
	  	}
	  	public synchronized void setFSuspend( boolean value ) { 
	  		fSuspend = value;
	  	}
	  	public void suspend() { setFSuspend( true ); } 
	  	public void resume() { setFSuspend( false ); }
	  	public boolean isSuspended() { return getFSuspend(); }
	  	
	  	int xWindowSize = 1300;
	  	int yWindowSize = 600;
	  	Thread t = null;
	  	int signSize = 400;
	  	Symbol symbol = new Symbol(signSize);
	  
	  	public void start() { 
	    	resume();
	    } 

	    public void stop() { 
	    	suspend();
	    } 
	
	    public void destroy() {
	        if ( t != null ) {
	            t.interrupt();
	            t = null;
	        }
	    }

		public void init() { 
	   	    this.setSize(xWindowSize, yWindowSize);
	        setBackground(new Color( 255, 255, 255));
	        if ( t == null ) {
	        	setFSuspend( false );
	            t = new Thread( this );
	        }
	        t.start();
	    } 
	    
		public void paint(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			
			Paint shadowPaint = new Color(0, 0, 0, 50);
	        AffineTransform shadowTransform = AffineTransform.getShearInstance( -1, 0);
	        shadowTransform.scale(1, -0.4);
	        g.setPaint(shadowPaint);
	        g.translate(100, 500);
	        g.fill(shadowTransform.createTransformedShape(new Rectangle(0, 0, signSize, signSize)));
	        g.translate(-100, -500);
	        g.translate(700, 500);
	        g.fill(shadowTransform.createTransformedShape(new Rectangle(0, 0, signSize, signSize)));
	        g.translate(-700, -500);
			
			BufferedImage image1 = new BufferedImage(signSize, signSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gr1 = image1.createGraphics();
			symbol.paint(gr1);
			gr1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawImage(image1, 100, 100, this);
			g.setFont(new Font("TimesRoman", Font.BOLD, 30));
			g.setColor(Color.green);
			g.drawString("Symbol without filters", 140, 550);
			
			BufferedImage image2 = new BufferedImage(signSize, signSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gr2 = image2.createGraphics();
			symbol.paint(gr2);
			gr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			BufferedImageOp filters = new RescaleOp(-1.0f, 255f, null);
			g.drawImage(filters.filter(image2, null), 700, 100, this);
			g.setColor(Color.green);
			g.drawString("Symbol with filters", 770, 550);

	    }
		
		public void run() { 
			suspend();
			repaint();
	  }

}

