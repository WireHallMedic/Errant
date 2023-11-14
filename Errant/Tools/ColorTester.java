package Errant.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.GUI.*;
import java.awt.datatransfer.*;
import java.io.*;

public class ColorTester extends JPanel implements ActionListener, GUIConstants
{
   public static final int SPRITE_SIZE = 96;
   private DisplayPanel displayPanel;
   private JPanel controlPanel;
   private JComboBox<String> spriteDD;
   private JComboBox<String> color1DD;
   private JComboBox<String> color2DD;
   private JButton copyB;
   
   private BufferedImage[] baseStrip1;
   private BufferedImage[] baseStrip2;
   private BufferedImage[] imageStrip1;
   private BufferedImage[] imageStrip2;
   private boolean imageToggle;
   private BufferedImage strip;
   
   private String[] colorDDStr = {"Red", "Green", "Blue", "Light Blue", "Yellow", "Orange", "Purple", "Black", "White", "Grey", 
                                  "Brown", "Dark Flesh", "Medium Flesh", "Light Flesh", "Rotten Flesh", "Demon Flesh"};
   private String[] spriteDDStr = {"Animals", "Cultists", "Demons", "Undead"};
   
   private javax.swing.Timer timer;
   
   
   public ColorTester()
   {
      super();
      setLayout(new GridLayout(1, 2));
      displayPanel = new DisplayPanel();
      this.add(displayPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(12, 1));
      controlPanel.add(new JLabel("Sprites"));
      spriteDD = new JComboBox<String>(spriteDDStr);
      spriteDD.addActionListener(this);
      controlPanel.add(spriteDD);
      controlPanel.add(new JLabel(""));
      controlPanel.add(new JLabel("Old Color"));
      color1DD = new JComboBox<String>(colorDDStr);
      color1DD.addActionListener(this);
      controlPanel.add(color1DD);
      controlPanel.add(new JLabel(""));
      controlPanel.add(new JLabel("New Color"));
      color2DD = new JComboBox<String>(colorDDStr);
      color2DD.addActionListener(this);
      controlPanel.add(color2DD);
      controlPanel.add(new JLabel(""));
      copyB = new JButton("Copy to Clipboard");
      copyB.addActionListener(this);
      controlPanel.add(copyB);
      
      this.add(controlPanel);
      
      loadStrips();
      generateImages();
      
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
      else if(ae.getSource() == copyB)
      {
         copyToClipboard(colorFullImage());
      }
      else if(ae.getSource() == spriteDD)
      {
         loadStrips();
         generateImages();
      }
      else
         generateImages();
   }
   
   private void loadStrips()
   {
      String str = (String)spriteDD.getSelectedItem();
      strip = null;
      if(str.equals("Animals"))
         strip = SystemTools.loadImageFromFile("Actors/animals.png");
      if(str.equals("Cultists"))
         strip = SystemTools.loadImageFromFile("Actors/cultists.png");
      if(str.equals("Demons"))
         strip = SystemTools.loadImageFromFile("Actors/demons.png");
      if(str.equals("Undead"))
         strip = SystemTools.loadImageFromFile("Actors/undead.png");
      int tiles = strip.getWidth() / 24;
      baseStrip1 = new BufferedImage[tiles];
      baseStrip2 = new BufferedImage[tiles];
      for(int i = 0; i < tiles; i++)
      {
         BufferedImage img1 = ImageTools.getFromSheet(strip, i, 0, 24, 24);
         BufferedImage img2 = ImageTools.getFromSheet(strip, i, 1, 24, 24);
         baseStrip1[i] = ImageTools.scale(img1, SPRITE_SIZE, SPRITE_SIZE);
         baseStrip2[i] = ImageTools.scale(img2, SPRITE_SIZE, SPRITE_SIZE);
      }
   }
   
   private void generateImages()
   {
      int length = baseStrip1.length;
      imageStrip1 = new BufferedImage[length];
      imageStrip2 = new BufferedImage[length];
      Color[] oldGroup = getColorGroup((String)color1DD.getSelectedItem());
      Color[] newGroup = getColorGroup((String)color2DD.getSelectedItem());
      for(int i = 0; i < length; i++)
      {
         imageStrip1[i] = ImageTools.replaceColor(baseStrip1[i], oldGroup, newGroup);
         imageStrip2[i] = ImageTools.replaceColor(baseStrip2[i], oldGroup, newGroup);
      }
   }
   
   private BufferedImage colorFullImage()
   {
      Color[] oldGroup = getColorGroup((String)color1DD.getSelectedItem());
      Color[] newGroup = getColorGroup((String)color2DD.getSelectedItem());
      return ImageTools.replaceColor(strip, oldGroup, newGroup);
   }
   
   private Color[] getColorGroup(String str)
   {
      if(str.equals("Red"))
         return RED_GROUP;
      if(str.equals("Green")) 
         return GREEN_GROUP;
      if(str.equals("Blue")) 
         return BLUE_GROUP;
      if(str.equals("Light Blue")) 
         return LIGHT_BLUE_GROUP;
      if(str.equals("Yellow")) 
         return YELLOW_GROUP;
      if(str.equals("Orange")) 
         return ORANGE_GROUP;
      if(str.equals("Purple")) 
         return PURPLE_GROUP;
      if(str.equals("Black")) 
         return BLACK_GROUP;
      if(str.equals("White")) 
         return WHITE_GROUP;
      if(str.equals("Grey")) 
         return GREY_GROUP;
      if(str.equals("Brown")) 
         return BROWN_GROUP;
      if(str.equals("Dark Flesh")) 
         return DARK_FLESH_GROUP;
      if(str.equals("Medium Flesh")) 
         return MEDIUM_FLESH_GROUP;
      if(str.equals("Light Flesh")) 
         return LIGHT_FLESH_GROUP;
      if(str.equals("Rotten Flesh")) 
         return ROTTEN_FLESH_GROUP;
      if(str.equals("Demon Flesh"))
         return DEMON_FLESH_GROUP;
      return null;
   }
   
   private void copyToClipboard(BufferedImage bi)
   {
      if(bi == null)
         return;
         
      TransferableImage ti = new TransferableImage( bi );
      Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
      c.setContents( ti, null );
   }
   
   // private class for transfering to clipboard
   private class TransferableImage implements Transferable 
   {
      Image i;
      public TransferableImage( Image i )
      {
         this.i = i;
      }
      
      public Object getTransferData( DataFlavor flavor )
        throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
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
         for(int i = 0; i < imageStrip1.length; i++)
         {
            int xLoc = i % 4;
            int yLoc = i / 4;
            if(imageToggle)
               g2d.drawImage(imageStrip1[i], 10 + (xLoc * (SPRITE_SIZE + 5)), yLoc * (SPRITE_SIZE + 5), null);
            else
               g2d.drawImage(imageStrip2[i], 10 + (xLoc * (SPRITE_SIZE + 5)), yLoc * (SPRITE_SIZE + 5), null);
         }
      }
   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(860, 450);
      ColorTester colorTester = new ColorTester();
      frame.add(colorTester);
      frame.setVisible(true);
   }
}