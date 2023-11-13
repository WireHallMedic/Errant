package Errant.GUI;

public interface SpriteConstants
{
   public static final String PAPER_DOLL_BODY_FILE = "Actors/paper_doll_bodies.png";
   public static final String PAPER_DOLL_HEAD_FILE = "Actors/paper_doll_heads.png";
   public static final String PAPER_DOLL_GEAR_FILE = "Actors/paper_doll_gear.png";
   public static final String SMALL_EFFECT_FILE = "Effects/oryx_effects_24.png";
   public static final String LARGE_EFFECT_FILE = "Effects/oryx_effects_32.png";
   public static final String CREATURE_FILE = "Actors/oryx_creatures.png";
   public static final String TERRAIN_FILE = "Terrain/oryx_terrain.png";
   public static final String ITEM_FILE = "Items/oryx_items.png";
   public static final String GOLD_FILE = "Items/coins.png";
   
   public enum GoldSprite
   {
      SINGLE_COIN       (0, 0),
      SMALL_PILE        (1, 0),
      MEDIUM_PILE       (2, 0),
      LARGE_PILE        (3, 0),
      VERY_LARGE_PILE   (4, 0);
      
      public int[] sheetLocation;
      
      private GoldSprite(int x, int y)
      {
         sheetLocation = new int[2];
         sheetLocation[0] = x;
         sheetLocation[1] = y;
      }
   }
   
   public enum PaperDollBodySprite
   {
      CLOTHES  (0, 0),
      ROBES    (1, 0),
      ARMOR    (2, 0);
      
      public int[][] sheetLocation;
      
      private PaperDollBodySprite(int x, int y)
      {
         sheetLocation = new int[2][2];
         sheetLocation[0][0] = x;
         sheetLocation[0][1] = y;
         sheetLocation[1][0] = x;
         sheetLocation[1][1] = y + 1;
      }
   }
}