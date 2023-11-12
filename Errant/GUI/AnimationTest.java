package Errant.GUI;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.Tools.*;

public class AnimationTest extends JPanel implements ActionListener
{
   private static final int TILE_SIZE = 72;
   private DisplayPanel displayPanel;
   private JPanel controlPanel;
   private JButton flipB;
   private JButton pauseB;
   private JButton resumeB;
   private JButton explosionB;
   
   private ErrantAnimationImage flame;
   private ErrantActorImage actor;
   private VisualEffect loopingEffect;
   private VisualEffect nonLoopingEffect;
   private Vector<BufferedImage> nonLoopingBase;
   private ErrantImage floorTile;

   public AnimationTest()
   {
      super();
      setLayout(new GridLayout(1, 2));
      displayPanel = new DisplayPanel();
      this.add(displayPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(8, 1));
      flipB = new JButton("Flip");
      flipB.addActionListener(this);
      controlPanel.add(flipB);
      pauseB = new JButton("Pause");
      pauseB.addActionListener(this);
      controlPanel.add(pauseB);
      resumeB = new JButton("Resume");
      resumeB.addActionListener(this);
      controlPanel.add(resumeB);
      explosionB = new JButton("Explosion");
      explosionB.addActionListener(this);
      controlPanel.add(explosionB);
      this.add(controlPanel);
      
      generateImages();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == flipB)
      {
         actor.setFacing(!actor.getFacing());
      }
      else if(ae.getSource() == pauseB)
      {
         AnimationManager.pause();
      }
      else if(ae.getSource() == resumeB)
      {
         AnimationManager.resume();
      }
      else if(ae.getSource() == explosionB)
      {
         nonLoopingEffect = new VisualEffect(nonLoopingBase, TILE_SIZE * 3 / 2);
      }
   }
   
   private void generateImages()
   {
      BufferedImage terrainSheet = SystemTools.loadImageFromFile("Terrain/oryx_terrain.png");
      BufferedImage actorSheet = SystemTools.loadImageFromFile("Actors/oryx_creatures.png");
      BufferedImage smallEffectSheet = SystemTools.loadImageFromFile("Effects/oryx_effects_24.png");
      BufferedImage largeEffectSheet = SystemTools.loadImageFromFile("Effects/oryx_effects_32.png");
      
      BufferedImage flame0 = ImageTools.getFromSheet(terrainSheet, 38, 0, 24, 24);
      BufferedImage flame1 = ImageTools.getFromSheet(terrainSheet, 39, 0, 24, 24);
      BufferedImage actor0 = ImageTools.getFromSheet(actorSheet, 0, 0, 24, 24);
      BufferedImage actor1 = ImageTools.getFromSheet(actorSheet, 0, 1, 24, 24);
   
      flame = new ErrantAnimationImage(flame0, flame1, TILE_SIZE);
      flame.setAnimationSpeed(GUIConstants.FAST_ANIMATION_SPEED);
      actor = new ErrantActorImage(actor0, actor1, TILE_SIZE);
      floorTile = new ErrantImage(ImageTools.getFromSheet(terrainSheet, 3, 0, 24, 24), TILE_SIZE);
      
      Vector<BufferedImage> loopingBase = new Vector<BufferedImage>();
      nonLoopingBase = new Vector<BufferedImage>();
      for(int i = 0; i < 4; i++)
      {
         loopingBase.add(ImageTools.getFromSheet(smallEffectSheet, 1 + i, 3, 24, 24));
         nonLoopingBase.add(ImageTools.getFromSheet(largeEffectSheet, 2 + i, 10, 32, 32));
      }
      
      loopingEffect = new VisualEffect(loopingBase, TILE_SIZE);
      loopingEffect.setActionOnEnd(GUIConstants.LOOP_ON_END);
      nonLoopingEffect = null;
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
         for(int x = 0; x * TILE_SIZE < getWidth(); x++)
         for(int y = 0; y * TILE_SIZE < getHeight(); y++)
            floorTile.paintFromCorner(g2d, x * TILE_SIZE, y * TILE_SIZE);
         flame.paintFromCorner(g2d, 10, 10);
         actor.paintFromCorner(g2d, 10, 85);
         loopingEffect.paintFromCorner(g2d, 10, 160);
         if(nonLoopingEffect != null)
         {
            if(nonLoopingEffect.isExpired())
               nonLoopingEffect = null;
            else
               nonLoopingEffect.paintFromCorner(g2d, 10, 235);
         }
         g2d.drawString("Cycles per second: " + AnimationManager.getCyclesPerSecond(), 10, getHeight() - 25);
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      frame.setLocation(50, 50);
      AnimationTest animationTest = new AnimationTest();
      frame.add(animationTest);
      frame.setVisible(true);
      
      AnimationManager.addPanel(animationTest);
      AnimationManager.start();
   }
}