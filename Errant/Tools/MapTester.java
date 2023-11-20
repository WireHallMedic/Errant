package Errant.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.GUI.*;
import Errant.Map.*;
import java.awt.datatransfer.*;
import java.io.*;

public class MapTester extends JPanel implements ActionListener, GUIConstants
{
   public static final int SPRITE_SIZE = 48;
   public static final int FRAME_WIDTH = SPRITE_SIZE * 15;
   public static final int FRAME_HEIGHT = SPRITE_SIZE * 15;
   private DisplayPanel displayPanel;
   private JPanel controlPanel;
   private JComboBox<TerrainStyleIndex> styleDD;
   private JButton toggleB;
   
   private MapImage map;
   
   
   public MapTester()
   {
      super();
      setLayout(null);
      displayPanel = new DisplayPanel();
      displayPanel.setBackground(Color.BLACK);
      displayPanel.setSize(FRAME_WIDTH * 3 / 4, FRAME_HEIGHT);
      displayPanel.setLocation(0, 0);
      this.add(displayPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(12, 1));
      controlPanel.setSize(FRAME_WIDTH / 4, FRAME_HEIGHT);
      controlPanel.setLocation(FRAME_WIDTH * 3 / 4, 0);
      styleDD = new JComboBox<TerrainStyleIndex>(TerrainStyleIndex.values());
      controlPanel.add(styleDD);
      styleDD.addActionListener(this);
      toggleB = new JButton("Toggle Door");
      controlPanel.add(toggleB);
      toggleB.addActionListener(this);
      this.add(controlPanel);
      
      generateImage();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == toggleB)
         map.toggle(2, 4);
      else if(ae.getSource() == styleDD)
         generateImage();
      displayPanel.repaint();
   }
      
   private void generateImage()
   {
      map = MapImageBuilder.generateMapImage(ZoneMap.getTestMap(), (TerrainStyleIndex)styleDD.getSelectedItem(), SPRITE_SIZE);
   }
        
   // private class for display 
   private class DisplayPanel extends JPanel
   {
      public DisplayPanel()
      {
         super();
      }
      
      @Override
      public void paint(Graphics g)
      {
         super.paint(g);
         Graphics2D g2d = (Graphics2D)g;
         map.paintBG(g2d);
         map.paintFG(g2d);
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(FRAME_WIDTH + 20, FRAME_HEIGHT);
      MapTester mapTester = new MapTester();
      frame.add(mapTester);
      frame.setVisible(true);
   }
}