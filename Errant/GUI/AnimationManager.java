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
   private static Vector<MilliListener> listenerList = new Vector<MilliListener>();
   private static int cyclesLastSecond = 0;
   private static int cyclesThisSecond = 0;
   private static int secondIndex = 0;
   private int tickIndex;
   private long lastMilli;
   private Thread thread;
   
   public static void addPanel(JPanel p){synchronized(panelList){panelList.add(p);}}
   public static void removePanel(JPanel p){synchronized(panelList){panelList.remove(p);}}
   public static void addListener(MilliListener ml){synchronized(listenerList){listenerList.add(ml);}}
   public static void removeListener(MilliListener ml){synchronized(listenerList){listenerList.remove(ml);}}
   
   public static int getCyclesPerSecond(){return cyclesLastSecond;}
   
   
   public static void start()
   {
      AnimationManager am = new AnimationManager();
      terminateF = false;
      runF = true;
      am.thread = new Thread(am);
      am.thread.start();
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
         cyclesThisSecond++;
         // blinks and pulses are tracked even outside of the run loop
         incrementTicks(millisElapsed);
         if(runF)
         {
            synchronized(listenerList)
            {
               for(MilliListener listener : listenerList)
               {
                  listener.millisElapsed(millisElapsed);
               }
            }
            synchronized(panelList)
            {
               for(JPanel panel : panelList)
               {
                  if(panel.isVisible())
                     panel.repaint();
               }
            }
            cleanUpListenerList();
         }
         lastMilli = curMilli;
         thread.yield();
      }
   }
   
   private void incrementTicks(int millisElapsed)
   {
      secondIndex += millisElapsed;
      if(secondIndex >= 1000)
      {
         cyclesLastSecond = cyclesThisSecond;
         cyclesThisSecond = 0;
         secondIndex -= 1000;
      }
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
   
   public static void cleanUpListenerList()
   {
      synchronized(listenerList)
      {
         for(int i = 0; i < listenerList.size(); i++)
         {
            if(listenerList.elementAt(i).isExpired())
            {
               listenerList.removeElementAt(i);
               i--;
            }
         }
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