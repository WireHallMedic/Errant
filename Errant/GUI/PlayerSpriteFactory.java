package Errant.GUI;

import Errant.Tools.*;
import java.awt.*;
import java.awt.image.*;

public class PlayerSpriteFactory implements GUIConstants
{
   private static final int HEAD = 0;
   private static final int BODY = 1;
   private static final int MAIN_HAND = 2;
   private static final int OFF_HAND = 3;
	private static final Color[] BASE_PRIMARY_COLOR = ORANGE_GROUP;
	private static final Color[] BASE_SECONDARY_COLOR = LIGHT_BLUE_GROUP;
	private static final Color[] BASE_HAIR_COLOR = BROWN_GROUP;
	private static final Color[] BASE_MAIN_HAND_COLOR = GREY_GROUP;
	private static final Color[] BASE_OFF_HAND_COLOR = GREY_GROUP;
	private static final Color[] BASE_SKIN_COLOR = MEDIUM_FLESH_GROUP;
   private static final BufferedImage HEAD_SPRITE_SHEET = SystemTools.loadImageFromFile("Actors/paper_doll_heads.png");
   private static final BufferedImage BODY_SPRITE_SHEET = SystemTools.loadImageFromFile("Actors/paper_doll_bodies.png");
   private static final BufferedImage GEAR_SPRITE_SHEET = SystemTools.loadImageFromFile("Actors/paper_doll_gear.png");
   
   private static int size = 24;
	private static PaperDollHead headType = PaperDollHead.SHORT_HAIR;
	private static PaperDollBody bodyType = PaperDollBody.CLOTHES;
	private static PaperDollMainHand mainHandType = PaperDollMainHand.NOTHING;
	private static PaperDollOffHand offHandType = PaperDollOffHand.NOTHING;
	private static Color[] primaryColor = ORANGE_GROUP;
	private static Color[] secondaryColor = LIGHT_BLUE_GROUP;
	private static Color[] hairColor = BROWN_GROUP;
	private static Color[] skinColor = MEDIUM_FLESH_GROUP;
	private static Color[] mainHandColor = GREY_GROUP;
	private static Color[] offHandColor = GREY_GROUP;
   private static BufferedImage[] baseStrip0 = null;
   private static BufferedImage[] baseStrip1 = null;
   private static BufferedImage[] strip0 = null;
   private static BufferedImage[] strip1 = null;


   public static int getSize(){return size;}
	public static PaperDollHead getHeadType(){return headType;}
	public static PaperDollBody getBodyType(){return bodyType;}
	public static PaperDollMainHand getMainHandType(){return mainHandType;}
	public static PaperDollOffHand getOffHandType(){return offHandType;}
	public static Color[] getPrimaryColor(){return primaryColor;}
	public static Color[] getSecondaryColor(){return secondaryColor;}
	public static Color[] getHairColor(){return hairColor;}
	public static Color[] getSkinColor(){return skinColor;}
	public static Color[] getMainHandColor(){return mainHandColor;}
	public static Color[] getOffHandColor(){return offHandColor;}


   public static void setSize(int s){size = s;}
	public static void setHeadType(PaperDollHead h){headType = h; loadBases();}
	public static void setBodyType(PaperDollBody b){bodyType = b; loadBases();}
	public static void setMainHandType(PaperDollMainHand m){mainHandType = m; loadBases();}
	public static void setOffHandType(PaperDollOffHand o){offHandType = o; loadBases();}
	public static void setPrimaryColor(Color[] p){primaryColor = p; generate();}
	public static void setSecondaryColor(Color[] s){secondaryColor = s; generate();}
	public static void setHairColor(Color[] h){hairColor = h; generate();}
	public static void setSkinColor(Color[] s){skinColor = s; generate();}
	public static void setMainHandColor(Color[] m){mainHandColor = m; generate();}
	public static void setOffHandColor(Color[] o){offHandColor = o; generate();}


   private static void loadBases()
   {
      baseStrip0 = new BufferedImage[4];
      baseStrip1 = new BufferedImage[4];
      
      baseStrip0[HEAD] = ImageTools.getFromSheet(HEAD_SPRITE_SHEET, headType.ordinal(), 0, HEAD_TILE_SIZE, HEAD_TILE_SIZE);
      baseStrip1[HEAD] = ImageTools.getFromSheet(HEAD_SPRITE_SHEET, headType.ordinal(), 1, HEAD_TILE_SIZE, HEAD_TILE_SIZE);
      
      baseStrip0[BODY] = ImageTools.getFromSheet(BODY_SPRITE_SHEET, bodyType.ordinal(), 0, BODY_TILE_SIZE, BODY_TILE_SIZE);
      baseStrip1[BODY] = ImageTools.getFromSheet(BODY_SPRITE_SHEET, bodyType.ordinal(), 1, BODY_TILE_SIZE, BODY_TILE_SIZE);
      
      if(mainHandType != PaperDollMainHand.NOTHING)
      {
         baseStrip0[MAIN_HAND] = ImageTools.getFromSheet(GEAR_SPRITE_SHEET, mainHandType.ordinal() - 1, 0, GEAR_TILE_SIZE, GEAR_TILE_SIZE);
         baseStrip1[MAIN_HAND] = ImageTools.getFromSheet(GEAR_SPRITE_SHEET, mainHandType.ordinal() - 1, 1, GEAR_TILE_SIZE, GEAR_TILE_SIZE);
      }
      else
      {
         baseStrip0[MAIN_HAND] = null;
         baseStrip1[MAIN_HAND] = null;
      }
      
      if(offHandType != PaperDollOffHand.NOTHING)
      {
         baseStrip0[OFF_HAND] = ImageTools.getFromSheet(GEAR_SPRITE_SHEET, offHandType.ordinal() - 1, 2, GEAR_TILE_SIZE, GEAR_TILE_SIZE);
         baseStrip1[OFF_HAND] = ImageTools.getFromSheet(GEAR_SPRITE_SHEET, offHandType.ordinal() - 1, 3, GEAR_TILE_SIZE, GEAR_TILE_SIZE);
      }
      else
      {
         baseStrip0[OFF_HAND] = null;
         baseStrip1[OFF_HAND] = null;
      }
   }
   
   private static void generate()
   {
      if(baseStrip0 == null)
         loadBases();
         
      strip0 = new BufferedImage[4];
      strip1 = new BufferedImage[4];
      
      // head
      strip0[HEAD] = ImageTools.replaceColor(baseStrip0[HEAD], BASE_SKIN_COLOR, INTERMEDIATE_GROUP);
      strip1[HEAD] = ImageTools.replaceColor(baseStrip1[HEAD], BASE_SKIN_COLOR, INTERMEDIATE_GROUP);
      strip0[HEAD] = ImageTools.replaceColor(strip0[HEAD], BASE_HAIR_COLOR, hairColor);
      strip1[HEAD] = ImageTools.replaceColor(strip1[HEAD], BASE_HAIR_COLOR, hairColor);
      strip0[HEAD] = ImageTools.replaceColor(strip0[HEAD], INTERMEDIATE_GROUP, skinColor);
      strip1[HEAD] = ImageTools.replaceColor(strip1[HEAD], INTERMEDIATE_GROUP, skinColor);
      strip0[HEAD] = ImageTools.replaceColor(strip0[HEAD], BASE_SECONDARY_COLOR, secondaryColor);
      strip1[HEAD] = ImageTools.replaceColor(strip1[HEAD], BASE_SECONDARY_COLOR, secondaryColor);
      
      // body
      strip0[BODY] = ImageTools.replaceColor(baseStrip0[BODY], BASE_SECONDARY_COLOR, INTERMEDIATE_GROUP);
      strip1[BODY] = ImageTools.replaceColor(baseStrip1[BODY], BASE_SECONDARY_COLOR, INTERMEDIATE_GROUP);
      strip0[BODY] = ImageTools.replaceColor(strip0[BODY], BASE_PRIMARY_COLOR, primaryColor);
      strip1[BODY] = ImageTools.replaceColor(strip1[BODY], BASE_PRIMARY_COLOR, primaryColor);
      strip0[BODY] = ImageTools.replaceColor(strip0[BODY], INTERMEDIATE_GROUP, secondaryColor);
      strip1[BODY] = ImageTools.replaceColor(strip1[BODY], INTERMEDIATE_GROUP, secondaryColor);
      
      // main hand
      if(baseStrip0[MAIN_HAND] != null)
      {
         strip0[MAIN_HAND] = ImageTools.replaceColor(baseStrip0[MAIN_HAND], BASE_SECONDARY_COLOR, INTERMEDIATE_GROUP);
         strip1[MAIN_HAND] = ImageTools.replaceColor(baseStrip1[MAIN_HAND], BASE_SECONDARY_COLOR, INTERMEDIATE_GROUP);
         strip0[MAIN_HAND] = ImageTools.replaceColor(strip0[MAIN_HAND], BASE_MAIN_HAND_COLOR, mainHandColor);
         strip1[MAIN_HAND] = ImageTools.replaceColor(strip1[MAIN_HAND], BASE_MAIN_HAND_COLOR, mainHandColor);
         strip0[MAIN_HAND] = ImageTools.replaceColor(strip0[MAIN_HAND], INTERMEDIATE_GROUP, secondaryColor);
         strip1[MAIN_HAND] = ImageTools.replaceColor(strip1[MAIN_HAND], INTERMEDIATE_GROUP, secondaryColor);
      }
      else
      {
         strip0[MAIN_HAND] = null;
         strip1[MAIN_HAND] = null;
      }
      
      // off hand
      if(baseStrip0[OFF_HAND] != null)
      {
         strip0[OFF_HAND] = ImageTools.replaceColor(baseStrip0[OFF_HAND], BASE_SECONDARY_COLOR, INTERMEDIATE_GROUP);
         strip1[OFF_HAND] = ImageTools.replaceColor(baseStrip1[OFF_HAND], BASE_SECONDARY_COLOR, INTERMEDIATE_GROUP);
         strip0[OFF_HAND] = ImageTools.replaceColor(strip0[OFF_HAND], BASE_OFF_HAND_COLOR, mainHandColor);
         strip1[OFF_HAND] = ImageTools.replaceColor(strip1[OFF_HAND], BASE_OFF_HAND_COLOR, mainHandColor);
         strip0[OFF_HAND] = ImageTools.replaceColor(strip0[OFF_HAND], INTERMEDIATE_GROUP, secondaryColor);
         strip1[OFF_HAND] = ImageTools.replaceColor(strip1[OFF_HAND], INTERMEDIATE_GROUP, secondaryColor);
      }
      else
      {
         strip0[OFF_HAND] = null;
         strip1[OFF_HAND] = null;
      }
   }
   
   public static ActorImage getImage()
   {
      if(strip0 == null)
         generate();
         
      BufferedImage left0 = strip0[BODY];
      BufferedImage left1 = strip1[BODY];
      if(strip0[MAIN_HAND] != null)
      {
         left0 = ImageTools.overlay(left0, strip0[MAIN_HAND]);
         left1 = ImageTools.overlay(left1, strip1[MAIN_HAND]);
      }
      left0 = ImageTools.overlay(left0, strip0[HEAD]);
      left1 = ImageTools.overlay(left1, strip1[HEAD]);
      if(strip0[OFF_HAND] != null)
      {
         left0 = ImageTools.overlay(left0, strip0[OFF_HAND]);
         left1 = ImageTools.overlay(left1, strip1[OFF_HAND]);
      }
      ActorImage actorImage = new ActorImage(left0, left1);
      actorImage.setSize(size);
      return actorImage;
   }
}