package Errant.GUI;

public interface GUIConstants
{
   public static final int SLOW_BLINK_SPEED = 1000;
   public static final int MEDIUM_BLINK_SPEED = 500;
   public static final int FAST_BLINK_SPEED = 250;
   public static final int VERY_FAST_BLINK_SPEED = 125;
   public static final int TICK_RESET_INDEX = 1000; // this should be the LCM of all blink and pulse speeds
   public static final int DEFAULT_VISUAL_EFFECT_FRAME_DURATION = 125;
   
   public static final int DEFAULT_ACTOR_SIZE = 24;
   public static final int DEFAULT_TERRAIN_SIZE = 24;
   public static final int DEFAULT_ITEM_SIZE = 16;
   
   public static final int SLOW_ANIMATION_SPEED = 0;
   public static final int MEDIUM_ANIMATION_SPEED = 1;
   public static final int FAST_ANIMATION_SPEED = 2;
   public static final int VERY_FAST_ANIMATION_SPEED = 3;
   
   public static final boolean FACING_LEFT = true;
   public static final boolean FACING_RIGHT = false;
   
   public static final int EXPIRE_ON_END = 0;
   public static final int LOOP_ON_END = 1;
   public static final int EXPIRE_TARGET_ON_END = 2;
}