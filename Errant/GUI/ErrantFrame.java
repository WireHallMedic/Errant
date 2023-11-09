package Errant.GUI;

import javax.swing.*;
import java.awt.*;

public class ErrantFrame extends JFrame
{
   public static final int PANEL_WIDTH = 1200;
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
   
   public static void main(String[] args)
   {
      ErrantFrame frame = new ErrantFrame();
      System.out.println(frame.getWidth() + ", " + frame.getHeight());
   }
}