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
   
   private ActorImage actorImage;
   
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
      LIGHT_GREEN_GROUP,
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
   
   private javax.swing.Timer timer;
   
   
   public PaperDollTest()
   {
      super();
      PlayerSpriteFactory.setSize(192);
      actorImage = PlayerSpriteFactory.getImage();
      
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
      
      skinDD.setSelectedIndex(1);
      mainHDD.setSelectedIndex(2);
      primaryDD.setSelectedIndex(5);
      secondaryDD.setSelectedIndex(2);
      
      timer = new javax.swing.Timer(1000 / 60, this);
      timer.start();
      
      AnimationManager.start();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == timer)
      {
         displayPanel.repaint();
      }
      else if(ae.getSource() == flipB)
      {
         actorImage.setFacing(!actorImage.getFacing());
      }
      else
      {
         PlayerSpriteFactory.setHeadType(getHeadType());
         PlayerSpriteFactory.setBodyType(getBodyType());
         PlayerSpriteFactory.setMainHandType(getMainHandType());
         PlayerSpriteFactory.setOffHandType(getOffHandType());
         PlayerSpriteFactory.setHairColor(getHairColorSelection());
         PlayerSpriteFactory.setSkinColor(getSkinColorSelection());
         PlayerSpriteFactory.setPrimaryColor(getPrimaryColorSelection());
         PlayerSpriteFactory.setSecondaryColor(getSecondaryColorSelection());
         actorImage = PlayerSpriteFactory.getImage();
      }
   }
   
   private Color[] getHairColorSelection()
   {
      switch(hairDD.getSelectedIndex())
      {
         case 0 : return BROWN_GROUP;
         case 1 : return YELLOW_GROUP;
         case 2 : return BLACK_GROUP;
         case 3 : return ORANGE_GROUP;
      }
      return null;
   }
   
   private Color[] getSkinColorSelection()
   {
      switch(skinDD.getSelectedIndex())
      {
         case 0 : return LIGHT_FLESH_GROUP;
         case 1 : return MEDIUM_FLESH_GROUP;
         case 2 : return DARK_FLESH_GROUP;
         case 3 : return ROTTEN_FLESH_GROUP;
         case 4 : return DEMON_FLESH_GROUP;
      }
      return null;
   }
   
   private Color[] getPrimaryColorSelection()
   {
      return colorGroup[primaryDD.getSelectedIndex()];
   }
   
   private Color[] getSecondaryColorSelection()
   {
      return colorGroup[secondaryDD.getSelectedIndex()];
   }
   
   private PaperDollHead getHeadType()
   {
      return PaperDollHead.values()[headDD.getSelectedIndex()];
   }
   
	private PaperDollBody getBodyType()
   {
      return PaperDollBody.values()[armorDD.getSelectedIndex()];
   }
   
	private PaperDollMainHand getMainHandType()
   {
      return PaperDollMainHand.values()[mainHDD.getSelectedIndex()];
   }
   
	private PaperDollOffHand getOffHandType()
   {
      return PaperDollOffHand.values()[offHDD.getSelectedIndex()];
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
         
         g2d.drawImage(actorImage.getImage(), 10, 10, null);
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 300);
      PaperDollTest paperDoll = new PaperDollTest();
      frame.add(paperDoll);
      frame.setVisible(true);
   }
}