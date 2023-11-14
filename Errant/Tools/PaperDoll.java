package Errant.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.GUI.*;

public class PaperDoll extends JPanel implements ActionListener, GUIConstants
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
   private String[] colorDDStr = {"Red", "Green", "Blue", "Light Blue", "Yellow", "Purple", "Orange", "Brown", "Grey", "Black", "White"};
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
   
   
   public PaperDoll()
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
      BufferedImage l0 = ImageTools.getFromSheet(armorStrip, armorIndex, 0, 24, 24);
      BufferedImage l1 = ImageTools.getFromSheet(armorStrip, armorIndex, 1, 24, 24);
      l0 = ImageTools.scale(l0, 192, 192);
      l1 = ImageTools.scale(l1, 192, 192);
      l0 = colorPrimary(l0);
      l0 = colorSecondary(l0);
      l1 = colorPrimary(l1);
      l1 = colorSecondary(l1);
      
      BufferedImage temp;
      // main hand (to be behind head)
      if(weaponIndex > -1)
      {
         temp = ImageTools.getFromSheet(gearStrip, weaponIndex, 0, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         l0 = ImageTools.overlay(l0, temp);
         temp = ImageTools.getFromSheet(gearStrip, weaponIndex, 1, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         l1 = ImageTools.overlay(l1, temp);
      }
      
      // head
      temp = ImageTools.getFromSheet(headStrip, headIndex, 0, 24, 24);
      temp = ImageTools.scale(temp, 192, 192);
      temp = colorHair(temp);
      temp = colorPrimary(temp);
      temp = colorSecondary(temp);
      l0 = ImageTools.overlay(l0, temp);
      temp = ImageTools.getFromSheet(headStrip, headIndex, 1, 24, 24);
      temp = ImageTools.scale(temp, 192, 192);
      temp = colorHair(temp);
      temp = colorPrimary(temp);
      temp = colorSecondary(temp);
      l1 = ImageTools.overlay(l1, temp);
      
      // off hand
      if(shieldIndex > -1)
      {
         temp = ImageTools.getFromSheet(gearStrip, shieldIndex, 2, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         l0 = ImageTools.overlay(l0, temp);
         temp = ImageTools.getFromSheet(gearStrip, shieldIndex, 3, 24, 24);
         temp = ImageTools.scale(temp, 192, 192);
         temp = colorPrimary(temp);
         temp = colorSecondary(temp);
         l1 = ImageTools.overlay(l1, temp);
      }
      
      l0 = colorSkin(l0);
      l1 = colorSkin(l1);
      
      // corner dots
      temp = ImageTools.getFromSheet(gearStrip, 11, 3, 24, 24);
      temp = ImageTools.scale(temp, 192, 192);
      l0 = ImageTools.overlay(l0, temp);
      l1 = ImageTools.overlay(l1, temp);
      
      left_0 = l0;
      left_1 = l1;
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
      int lightBrown = new Color(184, 150, 0).getRGB();
      int darkBrown = new Color(136, 112, 0).getRGB();
      int newLight = lightBrown;
      int newDark = darkBrown;
      
      switch(hairDD.getSelectedIndex())
      {
         // brown
         case 0 : return img;
         // blonde
         case 1 : newLight = new Color(234, 255, 0).getRGB();
                  newDark = new Color(232, 190, 0).getRGB();
                  break;
         // black
         case 2 : newLight = new Color(105, 105, 105).getRGB();
                  newDark = new Color(61, 61, 61).getRGB();
                  break;
         // red
         case 3 : newLight = new Color(255, 104, 74).getRGB();
                  newDark = new Color(227, 38, 0).getRGB();
                  break;
      }
      img = ImageTools.replaceColor(img, lightBrown, newLight);
      img = ImageTools.replaceColor(img, darkBrown, newDark);
      return img;
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
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 250);
      PaperDoll paperDoll = new PaperDoll();
      frame.add(paperDoll);
      frame.setVisible(true);
   }
}