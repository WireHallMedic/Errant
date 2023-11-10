package Errant.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.Tools.*;

public class AnimationTest extends JPanel implements ActionListener
{
   private DisplayPanel displayPanel;
   private JPanel controlPanel;
   private JButton flipB;
   
   private ErrantAnimationImage flame;
   private ErrantActorImage actor;
   private javax.swing.Timer timer;

   public AnimationTest()
   {
      super();
      setLayout(new GridLayout(1, 2));
      displayPanel = new DisplayPanel();
      this.add(displayPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(5, 1));
      flipB = new JButton("Flip");
      flipB.addActionListener(this);
      controlPanel.add(flipB);
      this.add(controlPanel);
      
      generateImages();
      
      timer = new javax.swing.Timer(16, this);
  //    timer.start();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == timer)
      {
         displayPanel.repaint();
      }
      else if(ae.getSource() == flipB)
      {
         actor.setFacing(!actor.getFacing());
      }
   }
   
   private void generateImages()
   {
      BufferedImage terrainSheet = SystemTools.loadImageFromFile("Terrain/oryx_terrain.png");
      BufferedImage actorSheet = SystemTools.loadImageFromFile("Actors/oryx_creatures.png");
      BufferedImage flame0 = ImageTools.getFromSheet(terrainSheet, 38, 0, 24, 24);
      BufferedImage flame1 = ImageTools.getFromSheet(terrainSheet, 39, 0, 24, 24);
      BufferedImage actor0 = ImageTools.getFromSheet(actorSheet, 0, 0, 24, 24);
      BufferedImage actor1 = ImageTools.getFromSheet(actorSheet, 0, 1, 24, 24);
   
      flame = new ErrantAnimationImage(flame0, flame1, 96);
      flame.setAnimationSpeed(GUIConstants.FAST_ANIMATION_SPEED);
      actor = new ErrantActorImage(actor0, actor1, 96);
   }
      
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
         flame.paintFromCorner(g2d, 10, 10);
         actor.paintFromCorner(g2d, 10, 116);
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 500);
      AnimationTest animationTest = new AnimationTest();
      frame.add(animationTest);
      frame.setVisible(true);
      
      AnimationManager am = new AnimationManager();
      am.addPanel(animationTest);
   }
}