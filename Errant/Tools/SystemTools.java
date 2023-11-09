package Errant.Tools;

import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.util.regex.*;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.*;

public class SystemTools
{
   public static final boolean RUNNING_FROM_JAR = isRunningFromJar();

   // check if this is running from a .jar or not
   private static boolean isRunningFromJar()
   {
      String str = SystemTools.class.getResource(SystemTools.class.getSimpleName().toString() + ".class").toString();
      Pattern jarPattern = Pattern.compile("^jar:");
      Matcher matcher = jarPattern.matcher(str);
      return matcher.find();
   }
   
   public static URL loadResource(String fileName)
   {
      try
      {
         if(RUNNING_FROM_JAR)
            fileName = "." + fileName;
         return SystemTools.class.getResource(fileName);
      } 
      catch(Exception e) 
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to load resource " + fileName);
   }
   
   public static BufferedImage loadImageFromFile(String fileName)
   {
      fileName += "Resources/Images/";
      try
      {
         return ImageIO.read(loadResource(fileName));
      }
      catch(Exception e)
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to create image " + fileName);
   }
   
   public static Clip loadSoundFromFile(String fileName)
   {
      fileName += "Resources/Sounds/";
      try
      {
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(loadResource(fileName));
         Clip clip = AudioSystem.getClip();
      }
      catch(Exception e)
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to load sound " + fileName);
   }
}