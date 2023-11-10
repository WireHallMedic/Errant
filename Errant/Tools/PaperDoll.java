package Errant.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.GUI.*;

public class PaperDoll extends JPanel implements ActionListener
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
   private String[] colorDDStr = {"Red", "Green", "Blue", "Yellow", "Purple", "Orange", "Black", "White"};
   private String[] skinDDStr = {"Light", "Medium", "Dark"};
   
   private int[][] colorPair = {
      {new Color(255, 1, 1).getRGB(), new Color(186, 27, 27).getRGB()},       // red off-by-one to avoid collisions
      {new Color(37, 228, 91).getRGB(), new Color(31, 187, 76).getRGB()},     // green off-by-one to avoid collisions
      {new Color(154, 220, 253).getRGB(), new Color(60, 188, 252).getRGB()},  // blue
      {new Color(255, 241, 112).getRGB(), new Color(204, 193, 89).getRGB()},  // yellow
      {new Color(156, 14, 179).getRGB(), new Color(119, 13, 137).getRGB()},   // purple
      {new Color(255, 192, 32).getRGB(), new Color(255, 120, 0).getRGB()},    // orange
      {new Color(89, 89, 89).getRGB(), new Color(61, 61, 61).getRGB()},       // black
      {new Color(243, 243, 243).getRGB(), new Color(201, 201, 201).getRGB()}  // white
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
      
      timer = new javax.swing.Timer(333, this);
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
      headStrip = SystemTools.loadImageFromFile("Actors/Heads.png");
      armorStrip = SystemTools.loadImageFromFile("Actors/Armor.png");
      gearStrip = SystemTools.loadImageFromFile("Actors/Gear.png");
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
      
      // head
      BufferedImage temp = ImageTools.getFromSheet(headStrip, headIndex, 0, 24, 24);
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
      
      // main hand
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
      int[] newColors = colorPair[primaryDD.getSelectedIndex()];
      int lightGreen = new Color(36, 227, 90).getRGB();
      int darkGreen = new Color(30, 186, 74).getRGB();
      img = ImageTools.replaceColor(img, lightGreen, newColors[0]);
      img = ImageTools.replaceColor(img, darkGreen, newColors[1]);
      return img;
   }
   
   private BufferedImage colorSecondary(BufferedImage img)
   {
      int[] newColors = colorPair[secondaryDD.getSelectedIndex()];
      int lightRed = new Color(255, 0, 0).getRGB();
      int darkRed = new Color(186, 26, 26).getRGB();
      img = ImageTools.replaceColor(img, lightRed, newColors[0]);
      img = ImageTools.replaceColor(img, darkRed, newColors[1]);
      return img;
   }
   
   private BufferedImage colorSkin(BufferedImage img)
   {
      int[] colorPair = mediumSkin;
      switch(skinDD.getSelectedIndex())
      {
         // light
         case 0 : colorPair = lightSkin;
                  break;
         // medium
         case 1 : return img;
         // dark
         case 2 : colorPair = darkSkin;
                  break;
      }
      img = ImageTools.replaceColor(img, mediumSkin[0], colorPair[0]);
      img = ImageTools.replaceColor(img, mediumSkin[1], colorPair[1]);
      return img;
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