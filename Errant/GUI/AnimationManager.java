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
   public static double slowPulse = 0.0;
   public static double mediumPulse = 0.0;
   public static double fastPulse = 0.0;
   private static int tileSize = 32;
   
   private static Vector<JPanel> panelList = new Vector<JPanel>();
   private static Vector<MilliListener> lockingList = new Vector<MilliListener>();
   private static Vector<MilliListener> nonlockingList = new Vector<MilliListener>();
   private static Vector<MilliListener> semilockingList = new Vector<MilliListener>();
   private static int cyclesLastSecond = 0;
   private static int cyclesThisSecond = 0;
   private static int secondIndex = 0;
   private int tickIndex;
   private int pulseIndex;
   private long lastMilli;
   private Thread thread;
   
   public static void addPanel(JPanel p){synchronized(panelList){panelList.add(p);}}
   public static void removePanel(JPanel p){synchronized(panelList){panelList.remove(p);}}
   public static void setTileSize(int ts){tileSize = ts;}
   
   public static int getCyclesPerSecond(){return cyclesLastSecond;}
   public static int getTileSize(){return tileSize;}
   
   
   public static void start()
   {
      AnimationManager am = new AnimationManager();
      terminateF = false;
      runF = true;
      am.thread = new Thread(am);
      am.thread.start();
   }
   
   public static void addListener(MilliListener m){addListener(m, LOCKING);}
   public static void addListener(MilliListener ml, int lockType)
   {
      switch(lockType)
      {
         case LOCKING   :     synchronized(lockingList){lockingList.add(ml);} break;
         case NONLOCKING :    synchronized(nonlockingList){nonlockingList.add(ml);} break;
         case SEMILOCKING :   synchronized(semilockingList){semilockingList.add(ml);} break;
      }
   }
   
   public static boolean isAnimationLocked()
   {
      return lockingList.size() > 0;
   }
   
   public static boolean isAnimationSemilocked()
   {
      return (semilockingList.size() > 0) || isAnimationLocked();
   }
   
   public static String lockingStatus()
   {
      return "L:" + lockingList.size() + ", " +
             "SL:" + semilockingList.size() + ", " +
             "NL:" + nonlockingList.size();
   }
   
   
   public static void removeListener(MilliListener ml)
   {
      synchronized(lockingList){lockingList.remove(ml);}
      synchronized(nonlockingList){nonlockingList.remove(ml);}
      synchronized(semilockingList){semilockingList.remove(ml);}
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
            synchronized(lockingList)
            {
               for(MilliListener listener : lockingList)
               {
                  listener.millisElapsed(millisElapsed);
               }
            }
            synchronized(nonlockingList)
            {
               for(MilliListener listener : nonlockingList)
               {
                  listener.millisElapsed(millisElapsed);
               }
            }
            synchronized(semilockingList)
            {
               for(MilliListener listener : semilockingList)
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
            cleanUpListenerLists();
         }
         lastMilli = curMilli;
         thread.yield();
      }
   }
   
   private void incrementTicks(int millisElapsed)
   {
      // track second
      secondIndex += millisElapsed;
      if(secondIndex >= 1000)
      {
         cyclesLastSecond = cyclesThisSecond;
         cyclesThisSecond = 0;
         secondIndex -= 1000;
      }
      // handle blinks
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
      // handle pulses
      pulseIndex += millisElapsed;
      if(pulseIndex >= TICK_RESET_INDEX)
         pulseIndex -= TICK_RESET_INDEX;
      if(slowBlink)
         slowPulse = (double)(pulseIndex % SLOW_BLINK_SPEED) / SLOW_BLINK_SPEED;
      else
         slowPulse = 1.0 - (double)(pulseIndex % SLOW_BLINK_SPEED) / SLOW_BLINK_SPEED;
      if(mediumBlink)
         mediumPulse = (double)(pulseIndex % MEDIUM_BLINK_SPEED) / MEDIUM_BLINK_SPEED;
      else
         mediumPulse = 1.0 - (double)(pulseIndex % MEDIUM_BLINK_SPEED) / MEDIUM_BLINK_SPEED;
      if(fastBlink)
         fastPulse = (double)(pulseIndex % FAST_BLINK_SPEED) / FAST_BLINK_SPEED;
      else
         fastPulse = 1.0 - (double)(pulseIndex % FAST_BLINK_SPEED) / FAST_BLINK_SPEED;
   }
   
   private static void cleanUpListenerLists()
   {
      cleanUpListenerList(lockingList);
      cleanUpListenerList(nonlockingList);
      cleanUpListenerList(semilockingList);
   }
   
   private static void cleanUpListenerList(Vector<MilliListener> list)
   {
      synchronized(list)
      {
         for(int i = 0; i < list.size(); i++)
         {
            if(list.elementAt(i).isExpired())
            {
               list.removeElementAt(i);
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