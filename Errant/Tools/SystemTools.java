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
         fileName = "/Errant/Resources/" + fileName;
         if(RUNNING_FROM_JAR)
            fileName = "." + fileName;
         URL url = SystemTools.class.getResource(fileName);
         if(url == null)
            throw new Exception("Could not find resource: " + fileName);
         return url;
      } 
      catch(Exception e) 
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to load resource " + fileName);
   }
   
   public static BufferedImage loadImageFromFile(String fileName)
   {
      fileName = "Images/" + fileName;
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
   
   public static URL getSoundURL(String fileName)
   {
      fileName = "Sounds/" + fileName;
      try
      {
         return loadResource(fileName);
      }
      catch(Exception e)
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to load sound " + fileName);
   }
}