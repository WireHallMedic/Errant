package Errant.GUI;

import java.util.*;
import Errant.Map.*;


// wrapper class for BufferedImage, so that when we need a bunch we can use the same interface
public class MovementScript implements GUIConstants, MilliListener
{
   private Vector<MovementStep> stepList;
   private int stepIndex;
   private MapPosition target;
   private int actionOnEnd;
   private boolean centerOnEnd;
   private ErrantImage image;
   
   public int getActionOnEnd(){return actionOnEnd;}
   public boolean getCenterOnEnd(){return centerOnEnd;}
   
   public void setActionOnEnd(int aoe){actionOnEnd = aoe;}
   public void setCenterOnEnd(boolean coe){centerOnEnd = coe;}
   
   public MovementScript(ErrantImage img, MapPosition t)
   {
      target = t;
      image = img;
      stepList = new Vector<MovementStep>();
      stepIndex = 0;
      actionOnEnd = EXPIRE_ON_END;
      centerOnEnd = true;
   }
   
   public void millisElapsed(int me)
   {
      int remainingMillis = me;
      while(me > 0 && stepIndex < stepList.size())
      {
         stepList.elementAt(stepIndex).millisElapsed(me, target);
         if(stepList.elementAt(stepIndex).getRemainingDuration() < 1)
         {
            me = 0 - stepList.elementAt(stepIndex).getRemainingDuration();
            stepIndex++;
            if(isExpired())
            {
               if(actionOnEnd == LOOP_ON_END)
               {
                  stepIndex = 0;
               }
               else if(actionOnEnd == EXPIRE_IMAGE_ON_END)
               {
                  if(image instanceof VisualEffect)
                  {
                     VisualEffect ve = (VisualEffect)image;
                     ve.expire();
                  }
               }
               if(centerOnEnd)
               {
                  target.setOffset(0.0, 0.0);
               }
            }
         }
         else
            me = 0;
      }
   }
   
   public void append(MovementScript that)
   {
      for(MovementStep step : that.stepList)
      {
         addStep(step);
      }
   }
   
   public boolean isExpired()
   {
      return stepIndex >= stepList.size();
   }
   
   public void addStep(int duration, double xSpeed, double ySpeed)
   {
      stepList.add(new MovementStep(duration, xSpeed, ySpeed));
   }
   
   public void addStep(MovementStep step)
   {
      addStep(step.getRemainingDuration(), step.getXSpeed(), step.getYSpeed());
   }
   
   public void register(){register(LOCKING);}
   public void register(int lockType)
   {
      AnimationManager.addListener(this, lockType);
   }
}