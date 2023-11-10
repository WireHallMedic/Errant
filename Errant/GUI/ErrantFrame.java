package Errant.GUI;

import javax.swing.*;
import java.awt.*;
import Errant.Tools.*;


public class ErrantFrame extends JFrame
{
   public static final int PANEL_WIDTH = 1600;
   public static final int PANEL_HEIGHT = 900;
   
   public ErrantFrame()
   {
      super();
      setSize(PANEL_WIDTH, PANEL_HEIGHT);
      setTitle("Errant");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
      
      //resize to proper dimensions, as we don't know border sizes until drawn
      Container pane = getContentPane();
      int fullWidth = PANEL_WIDTH + (PANEL_WIDTH - pane.getWidth());
      int fullHeight = PANEL_HEIGHT + (PANEL_HEIGHT - pane.getHeight());
      setSize(fullWidth, fullHeight);
      this.repaint();
   }
}