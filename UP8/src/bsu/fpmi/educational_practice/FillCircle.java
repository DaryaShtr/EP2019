/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsu.fpmi.educational_practice;

/**
 *
 * @author Даша
 */
import java.awt.*;
 
public class FillCircle extends Canvas{
   
    protected int diametr;
    protected Color color;
    
    public FillCircle(int diametr, Color color)
    {
        this.diametr = diametr;
        this.color = color;
    }
    
    public FillCircle( )
    {
        diametr = 50;
        color = Color.getHSBColor(255,173,255);
    }
    
    public void setDiametr(int diametr)
    {
        this.diametr = diametr;
        repaint(); 
    }
    
    public void setColor(Color color)
    {
        this.color = color;
        repaint(); 
    }

    public int getDiametr( )
    {
        return diametr;
    }
    
    public Color getColor( )
    {
        return color;
    }

    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(diametr, diametr);
    }
    
    @Override
    public Dimension getMinimumSize() 
    {
        return getPreferredSize(); 
    }
    public Dimension getMaximumSize() 
    {
        return new Dimension(1500, 1500); 
    }
    @Override
    public void paint( Graphics g )
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillOval(0, 0, diametr, diametr);
    }
}
