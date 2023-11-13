package Errant.GUI;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.Tools.*;
import Errant.Map.*;

public class AnimationTest extends JPanel implements ActionListener, KeyListener
{
   private static final int TILE_SIZE = 72;
   private DisplayPanel displayPanel;
   private JPanel controlPanel;
   private JButton flipB;
   private JButton pauseB;
   private JButton resumeB;
   private JButton explosionB;
   private JButton leftB;
   private JButton rightB;
   private JButton moveB;
   private JButton boopB;
   private JButton unboopB;
   private JButton fullBoopB;
   private JButton sparkB;
   private int cornerX;
   private int cornerY;
   
   private AnimationImage flame;
   private ActorImage actor;
   private MapPosition actorPosition;
   private MapPosition bonePosition;
   private MapPosition sparkPosition;
   private VisualEffect loopingEffect;
   private VisualEffect nonLoopingEffect;
   private VisualEffect spark;
   private Vector<BufferedImage> nonLoopingBase;
   private ErrantImage floorTile;

   public AnimationTest()
   {
      super();
      setLayout(new GridLayout(1, 2));
      displayPanel = new DisplayPanel();
      this.add(displayPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(10, 1));
      flipB = new JButton("Flip");
      flipB.addActionListener(this);
      flipB.setFocusable(false);
      controlPanel.add(flipB);
      pauseB = new JButton("Pause");
      pauseB.addActionListener(this);
      pauseB.setFocusable(false);
      controlPanel.add(pauseB);
      resumeB = new JButton("Resume");
      resumeB.addActionListener(this);
      resumeB.setFocusable(false);
      controlPanel.add(resumeB);
      explosionB = new JButton("Explosion");
      explosionB.addActionListener(this);
      explosionB.setFocusable(false);
      controlPanel.add(explosionB);
      moveB = new JButton("Move Bone");
      moveB.addActionListener(this);
      moveB.setFocusable(false);
      controlPanel.add(moveB);
      boopB = new JButton("Boop");
      boopB.addActionListener(this);
      boopB.setFocusable(false);
      controlPanel.add(boopB);
      unboopB = new JButton("Unboop");
      unboopB.addActionListener(this);
      unboopB.setFocusable(false);
      controlPanel.add(unboopB);
      fullBoopB = new JButton("Full Boop");
      fullBoopB.addActionListener(this);
      fullBoopB.setFocusable(false);
      controlPanel.add(fullBoopB);
      sparkB = new JButton("Spark");
      sparkB.addActionListener(this);
      sparkB.setFocusable(false);
      controlPanel.add(sparkB);
      controlPanel.add(new JLabel("Arrows for camera, numpad for actor."));
      this.add(controlPanel);
      
      cornerX = 0;
      cornerY = 0;
      AnimationManager.setTileSize(TILE_SIZE);
      bonePosition = new MapPosition(2, 2);
      actorPosition = new MapPosition(2, 1);
      
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
      else if(ae.getSource() == moveB)
      {
         MovementScript script = new MovementScript(loopingEffect, bonePosition);
         script.addStep(100, 4.0, 0.0);
         script.addStep(100, 4.0, 4.0);
         script.addStep(100, 0.0, 4.0);
         script.addStep(100, -4.0, 4.0);
         script.addStep(100, -4.0, 0.0);
         script.addStep(100, -4.0, -4.0);
         script.addStep(100, 0.0, -4.0);
         script.addStep(100, 4.0, -4.0);
         script.register();
      }
      else if(ae.getSource() == boopB)
      {
         MovementScript script = MovementScriptFactory.getPreAttackScript(actor, actorPosition, Direction.EAST);
         actor.setFacing(GUIConstants.FACING_RIGHT);
         script.register();
      }
      else if(ae.getSource() == unboopB)
      {
         MovementScript script = MovementScriptFactory.getPostAttackScript(actor, actorPosition, Direction.EAST);
         script.register();
      }
      else if(ae.getSource() == fullBoopB)
      {
         MovementScript script = MovementScriptFactory.getPreAttackScript(actor, actorPosition, Direction.WEST);
         script.append(MovementScriptFactory.getPostAttackScript(actor, actorPosition, Direction.WEST));
         actor.setFacing(GUIConstants.FACING_LEFT);
         script.register();
      }
      else if(ae.getSource() == sparkB)
      {
         VisualEffect spark = getProjectile();
         sparkPosition = new MapPosition(2, 0);
         MovementScript script = MovementScriptFactory.getProjectileScript(spark, sparkPosition, actorPosition.getXLoc(), actorPosition.getYLoc(), 10.0);
         script.register();
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
   
      flame = new AnimationImage(flame0, flame1, TILE_SIZE);
      flame.setAnimationSpeed(GUIConstants.FAST_ANIMATION_SPEED);
      actor = new ActorImage(actor0, actor1, TILE_SIZE);
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
      spark = null;
   }
   
   private VisualEffect getProjectile()
   {
      BufferedImage smallEffectSheet = SystemTools.loadImageFromFile("Effects/oryx_effects_24.png");
      
      Vector<BufferedImage> base = new Vector<BufferedImage>();
      base.add(ImageTools.getFromSheet(smallEffectSheet, 0, 7, 24, 24));
      base.add(ImageTools.getFromSheet(smallEffectSheet, 1, 7, 24, 24));
      
      spark = new VisualEffect(base, TILE_SIZE);
      spark.setActionOnEnd(GUIConstants.LOOP_ON_END);
      return spark;
   }
   
   public void keyTyped(KeyEvent ke){}
   public void keyReleased(KeyEvent ke){}
   public void keyPressed(KeyEvent ke)
   {
      Direction dir = null;
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_NUMPAD1 : dir = Direction.SOUTHWEST; break;
         case KeyEvent.VK_NUMPAD2 : dir = Direction.SOUTH; break;
         case KeyEvent.VK_NUMPAD3 : dir = Direction.SOUTHEAST; break;
         case KeyEvent.VK_NUMPAD4 : dir = Direction.WEST; break;
         case KeyEvent.VK_NUMPAD6 : dir = Direction.EAST; break;
         case KeyEvent.VK_NUMPAD7 : dir = Direction.NORTHWEST; break;
         case KeyEvent.VK_NUMPAD8 : dir = Direction.NORTH; break;
         case KeyEvent.VK_NUMPAD9 : dir = Direction.NORTHEAST; break;
         case KeyEvent.VK_UP :      cornerY--; break;
         case KeyEvent.VK_DOWN :    cornerY++; break;
         case KeyEvent.VK_LEFT :    cornerX--; break;
         case KeyEvent.VK_RIGHT :   cornerX++; break;
      }
      if(dir != null)
      {
         MovementScript script = MovementScriptFactory.getStepScript(actor, actorPosition, dir);
         actor.setFacing(dir);
         script.register();
      }
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
         flame.paintAtTile(g2d, 2 - cornerX, 0 - cornerY);
         actor.paint(g2d, actorPosition, cornerX, cornerY);
         loopingEffect.paint(g2d, bonePosition, cornerX, cornerY);
         if(nonLoopingEffect != null)
         {
            if(nonLoopingEffect.isExpired())
               nonLoopingEffect = null;
            else
               nonLoopingEffect.paintAtTile(g2d, 2 - cornerX, cornerY + 3);
         }
         if(spark != null)
         {
            if(spark.isExpired())
               spark = null;
            else
               spark.paint(g2d, sparkPosition, cornerX, cornerY);
         }
         int colorVal = (int)((128 * AnimationManager.slowPulse) + 127);
         g2d.setColor(new Color(colorVal, colorVal, colorVal));
         g2d.fillRect(200, getHeight() - 25, 25, 25);
         
         colorVal = (int)((128 * AnimationManager.mediumPulse) + 127);
         g2d.setColor(new Color(colorVal, colorVal, colorVal));
         g2d.fillRect(250, getHeight() - 25, 25, 25);
         
         colorVal = (int)((128 * AnimationManager.fastPulse) + 127);
         g2d.setColor(new Color(colorVal, colorVal, colorVal));
         g2d.fillRect(300, getHeight() - 25, 25, 25);
         
         g2d.setColor(Color.WHITE);
         g2d.drawString("Cycles per second: " + AnimationManager.getCyclesPerSecond(), 10, getHeight() - 10);
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
      
      frame.addKeyListener(animationTest);
      
      AnimationManager.addPanel(animationTest);
      AnimationManager.start();
   }
}