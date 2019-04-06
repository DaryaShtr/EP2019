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
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;


public class Main extends Applet{
	
	public void paint(Graphics g)
	{
		FillCircle fl = new FillCircle(200, Color.green);
		fl.paint(g);
	}


}

