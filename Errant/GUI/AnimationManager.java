package Errant.GUI;

import java.util.*;
import javax.swing.*;

public class AnimationManager implements Runnable, GUIConstants
{
   private static boolean runF;
   private static boolean terminateF;
   
   public static boolean slowBlink = false;
   public static boolean mediumBlink = false;
   public static boolean fastBlink = false;
   
   private static Vector<JPanel> panelList = new Vector<JPanel>();
   private int tickIndex;
   private long lastMilli;
   private Thread thread;
   
   public static void addPanel(JPanel p){synchronized(panelList){panelList.add(p);}}
   
   public static void removePanel(JPanel p){synchronized(panelList){panelList.remove(p);}}
   
   public AnimationManager()
   {
      start();
   }
   
   public void start()
   {
      terminateF = false;
      runF = true;
      thread = new Thread(this);
      thread.start();
   }

   public void run()
   {
      lastMilli = System.currentTimeMillis();
      long curMilli;
      int millisElapsed;
      while(!terminateF)
      {
         curMilli = System.currentTimeMillis();
         millisElapsed = (int)(curMilli - lastMilli);
         // loop around if less than a millisecond has elapsed
         if(millisElapsed == 0)
            continue;
         // blinks and pulses are tracked even outside of the run loop
         incrementTicks(millisElapsed);
         if(runF)
         {
            synchronized(panelList)
            {
               for(JPanel panel : panelList)
               {
                  if(panel.isVisible())
                     panel.repaint();
               }
            }
         }
         lastMilli = curMilli;
         thread.yield();
      }
   }
   
   private void incrementTicks(int millisElapsed)
   {
      for(int i = 0; i < millisElapsed; i++)
      {
         tickIndex++;
         if(tickIndex >= TICK_RESET_INDEX)
            tickIndex = 0;
         if(tickIndex % SLOW_BLINK_SPEED == 0)
            slowBlink = !slowBlink;
         if(tickIndex % MEDIUM_BLINK_SPEED == 0)
            mediumBlink = !mediumBlink;
         if(tickIndex % FAST_BLINK_SPEED == 0)
            fastBlink = !fastBlink;
      }
   }
   
   public static void terminate()
   {
      terminateF = true;
      runF = true;
   }
   
   public static void pause()
   {
      runF = false;
   }
   
   public static void resume()
   {
      runF = true;
   }
}