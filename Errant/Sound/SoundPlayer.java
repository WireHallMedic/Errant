package Errant.Sound;

import javax.sound.sampled.*;
import java.net.URL;
import Errant.Tools.*;
import java.util.*;


public class SoundPlayer
{
   public static synchronized void playSound(URL url)
   {
      try
      {
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         Clip clip = AudioSystem.getClip();
         clip.open(audioInputStream);
         clip.start();
      }
      catch(Exception ex)
      {
         ex.printStackTrace();
      }
   }
   
   // testing method
   public static void main(String[] args)
   {
      System.out.println(SystemTools.RUNNING_FROM_JAR);
      URL coinURL = SystemTools.getSoundURL("Test_Coin.wav");
      URL hitURL = SystemTools.getSoundURL("Test_Hit.wav");
      playSound(hitURL);
      playSound(coinURL);
      
      String str = "";
      Scanner scanner = new Scanner(System.in);  // Create a Scanner object
      
      while(!str.equals("q"))
      {
         str = scanner.nextLine().toLowerCase();
         playSound(coinURL);
      }
   }
}