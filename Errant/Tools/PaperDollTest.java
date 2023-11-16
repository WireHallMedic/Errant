package Errant.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.GUI.*;

public class PaperDollTest extends JPanel implements ActionListener, GUIConstants
{
   private DisplayPanel displayPanel;
   private JPanel controlPanel;
   private JComboBox<String> headDD;
   private JComboBox<String> armorDD;
   private JComboBox<String> mainHDD;
   private JComboBox<String> offHDD;
   private JComboBox<String> hairDD;
   private JComboBox<String> primaryDD;
   private JComboBox<String> secondaryDD;
   private JComboBox<String> skinDD;
   private JButton flipB;
   
   private BufferedImage left_0 = null;
   private BufferedImage left_1 = null;
   private BufferedImage right_0 = null;
   private BufferedImage right_1 = null;
   private BufferedImage headStrip = null;
   private BufferedImage armorStrip = null;
   private BufferedImage gearStrip = null;
   
   private boolean facingLeft = true;
   private boolean imageToggle = false;
   
   private String[] headDDStr = {"Short Hair", "Long Hair", "Light Helmet", "Heavy Helmet"};
   private String[] armorDDStr = {"Clothes", "Robes", "Armor"};
   private String[] mainHDDStr = {"Nothing", "Knife", "Sword", "2H Sword", "Mace", "Hammer", "2H Hammer", "Axe", "2hAxe", "Bow", "Spear", "Wand", "Staff"};
   private String[] offHDDStr = {"Nothing", "Small Shield", "Large Shield", "Knife", "Sword", "Mace", "Hammer", "Axe", "Orb"};
   private String[] hairDDStr = {"Brown Hair", "Blonde Hair", "Black Hair", "Red Hair"};
   private String[] colorDDStr = {"Red", "Green", "Light Green", "Blue", "Light Blue", "Yellow", "Purple", "Orange", "Brown", "Grey", "Black", "White"};
   private String[] skinDDStr = {"Light", "Medium", "Dark", "Undead", "Demon"};
   
   private Color[][] colorGroup = {
      RED_GROUP,
      GREEN_GROUP,
      BLUE_GROUP,
      LIGHT_BLUE_GROUP,
      YELLOW_GROUP,
      PURPLE_GROUP,
      ORANGE_GROUP,
      BROWN_GROUP,
      GREY_GROUP,
      BLACK_GROUP,
      WHITE_GROUP
   };
   
   private int[] lightSkin = {new Color(255, 236, 217).getRGB(), new Color(255, 209, 166).getRGB()};
   private int[] mediumSkin = {new Color(255, 209, 166).getRGB(), new Color(252, 152, 56).getRGB()};
   private int[] darkSkin = {new Color(252, 152, 56).getRGB(), new Color(184, 110, 40).getRGB()};
   
   private javax.swing.Timer timer;
   
   
   public PaperDollTest()
   {
      super();
      setLayout(new GridLayout(1, 2));
      displayPanel = new DisplayPanel();
      this.add(displayPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(9, 2));
      controlPanel.add(new JLabel("Head"));
      headDD = new JComboBox<String>(headDDStr);
      headDD.addActionListener(this);
      controlPanel.add(headDD);
      controlPanel.add(new JLabel("Hair"));
      hairDD = new JComboBox<String>(hairDDStr);
      hairDD.addActionListener(this);
      controlPanel.add(hairDD);
      controlPanel.add(new JLabel("Skin"));
      skinDD = new JComboBox<String>(skinDDStr);
      skinDD.addActionListener(this);
      controlPanel.add(skinDD);
      controlPanel.add(new JLabel("Torso"));
      armorDD = new JComboBox<String>(armorDDStr);
      armorDD.addActionListener(this);
      controlPanel.add(armorDD);
      controlPanel.add(new JLabel("Main Hand"));
      mainHDD = new JComboBox<String>(mainHDDStr);
      mainHDD.addActionListener(this);
      controlPanel.add(mainHDD);
      controlPanel.add(new JLabel("Off Hand"));
      offHDD = new JComboBox<String>(offHDDStr);
      offHDD.addActionListener(this);
      controlPanel.add(offHDD);
      controlPanel.add(new JLabel("Primary Color"));
      primaryDD = new JComboBox<String>(colorDDStr);
      primaryDD.addActionListener(this);
      controlPanel.add(primaryDD);
      controlPanel.add(new JLabel("Secondary Color"));
      secondaryDD = new JComboBox<String>(colorDDStr);
      secondaryDD.addActionListener(this);
      controlPanel.add(secondaryDD);
      controlPanel.add(new JLabel(""));
      flipB = new JButton("Flip");
      flipB.addActionListener(this);
      controlPanel.add(flipB);
      this.add(controlPanel);
      
      loadStrips();
      generateImage();
      
      skinDD.setSelectedIndex(1);
      mainHDD.setSelectedIndex(2);
      primaryDD.setSelectedIndex(5);
      secondaryDD.setSelectedIndex(2);
      
      timer = new javax.swing.Timer(500, this);
      timer.start();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == timer)
      {
         imageToggle = !imageToggle;
         displayPanel.repaint();
      }
      else if(ae.getSource() == flipB)
      {
         facingLeft = !facingLeft;
      }
      else
         generateImage();
   }
   
   private void loadStrips()
   {
      headStrip = SystemTools.loadImageFromFile("Actors/paper_doll_heads.png");
      armorStrip = SystemTools.loadImageFromFile("Actors/paper_doll_bodies.png");
      gearStrip = SystemTools.loadImageFromFile("Actors/paper_doll_gear.png");
   }
   
   private void generateImage()
   {
      int headIndex = headDD.getSelectedIndex();
      int armorIndex = armorDD.getSelectedIndex();
      int weaponIndex = mainHDD.getSelectedIndex() - 1;
      int shieldIndex = offHDD.getSelectedIndex() - 1;
      // body
      BufferedImage body0 = ImageTools.getFromSheet(armorStrip, armorIndex, 0, 24, 24);
      BufferedImage body1 = ImageTools.getFromSheet(armorStrip, armorIndex, 1, 24, 24);
      body0 = ImageTools.scale(body0, 192, 192);
      body1 = ImageTools.scale(body1, 192, 192);
      body0 = colorPrimary(body0);
      body0 = colorSecondary(body0);
      body1 = colorPrimary(body1);
      body1 = colorSecondary(body1);
      
      BufferedImage temp;
      // main hand (to be behind head)
      if(weaponIndex > -1)
      {
         temp = ImageTools.getFromSheet(gearStrip, weaponIndex, 0, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         body0 = ImageTools.overlay(body0, temp);
         temp = ImageTools.getFromSheet(gearStrip, weaponIndex, 1, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         body1 = ImageTools.overlay(body1, temp);
      }
      
      // head
      BufferedImage head0 = ImageTools.getFromSheet(headStrip, headIndex, 0, 24, 24);
      head0 = ImageTools.scale(head0, 192, 192);
      head0 = colorPrimary(head0);
      head0 = colorSecondary(head0);
      head0 = colorHair(head0);
      body0 = ImageTools.overlay(body0, head0);
      BufferedImage head1 = ImageTools.getFromSheet(headStrip, headIndex, 1, 24, 24);
      head1 = ImageTools.scale(head1, 192, 192);
      head1 = colorPrimary(head1);
      head1 = colorSecondary(head1);
      head1 = colorHair(head1);
      body1 = ImageTools.overlay(body1, head1);
      
      // off hand
      if(shieldIndex > -1)
      {
         temp = ImageTools.getFromSheet(gearStrip, shieldIndex, 2, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         body0 = ImageTools.overlay(body0, temp);
         temp = ImageTools.getFromSheet(gearStrip, shieldIndex, 3, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         body1 = ImageTools.overlay(body1, temp);
      }
      
      body0 = colorSkin(body0);
      body1 = colorSkin(body1);
      
      // corner dots
      temp = ImageTools.getFromSheet(gearStrip, 11, 3, 24, 24);
      temp = ImageTools.scale(temp, 192, 192);
      body0 = ImageTools.overlay(body0, temp);
      body1 = ImageTools.overlay(body1, temp);
      
      left_0 = body0;
      left_1 = body1;
      right_0 = ImageTools.mirrorHorizontal(left_0);
      right_1 = ImageTools.mirrorHorizontal(left_1);
   }
   
   private BufferedImage colorPrimary(BufferedImage img)
   {
      Color[] newColors = colorGroup[primaryDD.getSelectedIndex()];
      Color[] oldColors = ORANGE_GROUP;
      return ImageTools.replaceColor(img, oldColors, newColors);
   }
   
   private BufferedImage colorSecondary(BufferedImage img)
   {
      Color[] newColors = colorGroup[secondaryDD.getSelectedIndex()];
      Color[] oldColors = LIGHT_BLUE_GROUP;
      return ImageTools.replaceColor(img, oldColors, newColors);
   }
   
   private BufferedImage colorSkin(BufferedImage img)
   {
      Color[] colorGroup = MEDIUM_FLESH_GROUP;
      switch(skinDD.getSelectedIndex())
      {
         // light
         case 0 : colorGroup = LIGHT_FLESH_GROUP;
                  break;
         // medium
         case 1 : return img;
         // dark
         case 2 : colorGroup = DARK_FLESH_GROUP;
                  break;
         // undead
         case 3 : colorGroup = ROTTEN_FLESH_GROUP;
                  break;
         // demon
         case 4 : colorGroup = DEMON_FLESH_GROUP;
                  break;
      }
      return ImageTools.replaceColor(img, MEDIUM_FLESH_GROUP, colorGroup);
   }
   
   private BufferedImage colorHair(BufferedImage img)
   {
      Color[] colorGroup = BROWN_GROUP;
      
      switch(hairDD.getSelectedIndex())
      {
         // brown
         case 0 : return img;
         // blonde
         case 1 : colorGroup = YELLOW_GROUP;
                  break;
         // black
         case 2 : colorGroup = BLACK_GROUP;
                  break;
         // red
         case 3 : colorGroup = ORANGE_GROUP;
                  break;
      }
      return ImageTools.replaceColor(img, BROWN_GROUP, colorGroup);
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
         if(right_1 == null)
            return;
         if(imageToggle)
         {
            if(facingLeft)
               g2d.drawImage(left_0, 10, 10, null);
            else
               g2d.drawImage(right_0, 10, 10, null);
         }
         else
         {
            if(facingLeft)
               g2d.drawImage(left_1, 10, 10, null);
            else
               g2d.drawImage(right_1, 10, 10, null);
         }
         
         PlayerSpriteFactory psf = new PlayerSpriteFactory();
         ActorImage ai = psf.getImage();
         ai.setSize(192);
         g2d.drawImage(ai.getImage(), 10, 202, null);
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 500);
      PaperDollTest paperDoll = new PaperDollTest();
      frame.add(paperDoll);
      frame.setVisible(true);
   }
}