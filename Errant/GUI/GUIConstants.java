package Errant.GUI;

import java.awt.*;

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
   public static final int EXPIRE_IMAGE_ON_END = 2;
   
   // movement script types
   public static final int LOCKING = 0;
   public static final int NONLOCKING = 1;
   public static final int SEMILOCKING = 2;
   
   // color groups
   public static final Color[] RED_GROUP = {new Color(142, 32, 32), new Color(185, 26, 26), new Color(255, 0, 0)};
   public static final Color[] GREEN_GROUP = {new Color(17, 104, 42), new Color(22, 133, 53), new Color(30, 185, 74)};
   public static final Color[] LIGHT_GREEN_GROUP = {new Color(22, 133, 53), new Color(30, 186, 74), new Color(23, 227, 90)};
   public static final Color[] BLUE_GROUP = {new Color(11, 94, 135), new Color(14, 123, 178), new Color(60, 188, 252)};
   public static final Color[] LIGHT_BLUE_GROUP = {new Color(61, 139, 179), new Color(129, 198, 226), new Color(187, 231, 253)};
   public static final Color[] YELLOW_GROUP = {new Color(168, 148, 32), new Color(232, 190, 0), new Color(228, 230, 16)};
   public static final Color[] ORANGE_GROUP = {new Color(204, 96, 25), new Color(255, 120, 0), new Color(255, 192, 32)};
   public static final Color[] PURPLE_GROUP = {new Color(82, 30, 103), new Color(127, 59, 164), new Color(199, 104, 252)};
   public static final Color[] BLACK_GROUP = {new Color(61, 61, 61), new Color(89, 89, 89), new Color(130, 130, 130)};
   public static final Color[] WHITE_GROUP = {new Color(145, 145, 145), new Color(201, 201, 201), new Color(243, 243, 243)};
   public static final Color[] GREY_GROUP = {new Color(105, 105, 105), new Color(144, 144, 144), new Color(202, 202, 202)};
   public static final Color[] LIGHT_GREY_GROUP = {new Color(144, 144, 144), new Color(202, 202, 202), new Color(242, 242, 242)};
   public static final Color[] BROWN_GROUP = {new Color(93, 75, 0), new Color(136, 112, 0), new Color(184, 150, 0)};
   public static final Color[] DARK_FLESH_GROUP = {new Color(120, 72, 26), new Color(184, 110, 40), new Color(252, 152, 56)};
   public static final Color[] MEDIUM_FLESH_GROUP = {new Color(184, 110, 40), new Color(252, 152, 56), new Color(255, 209, 166)};
   public static final Color[] LIGHT_FLESH_GROUP = {new Color(252, 152, 56), new Color(255, 209, 166), new Color(255, 236, 217)};
   public static final Color[] ROTTEN_FLESH_GROUP = {new Color(106, 128, 20), new Color(155, 186, 30), new Color(187, 225, 36)};
   public static final Color[] DEMON_FLESH_GROUP = {new Color(160, 58, 62), new Color(201, 69, 73), new Color(243, 82, 84)};
   public static final Color[] INTERMEDIATE_GROUP = {new Color(1, 1, 1), new Color(2, 2, 2), new Color(3, 3, 3)};
   
   // tile sizes
   public static final int HEAD_TILE_SIZE = 24;
   public static final int BODY_TILE_SIZE = 24;
   public static final int GEAR_TILE_SIZE = 24;
   
   public enum PaperDollHead
   {
      SHORT_HAIR, LONG_HAIR, LIGHT_HELMET, HEAVY_HELMET;
   }
   
   public enum PaperDollBody
   {
      CLOTHES, ROBES, ARMOR;
   }
   
   public enum PaperDollMainHand
   {
      NOTHING, KNIFE, SWORD, HEAVY_SWORD, MACE, HAMMER, HEAVY_HAMMER, AXE, HEAVY_AXE, BOW, SPEAR, WAND, STAFF;
   }
   
   public enum PaperDollOffHand
   {
      NOTHING, SMALL_SHIELD, LARGE_SHIELD, KNIFE, SWORD, MACE, HAMMER, AXE, ORB;
   }
}