package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Draws a String in a frame, which can fade out.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author Patrick Silvestre
 */

public class FadeableString extends JComponent
{
   //----------------Public Interface-------------------------

   /**
    * Creates a new FadeableString instance.
    * @param string The string to be output.
    * @param color The color of the string.
    * @param fontSize The font size of the string.
    */
   public FadeableString(String string, Color color, int fontSize)
   {
      this.string = string;
      this.color = color;
      this.fontSize = fontSize;
      fadeCount = 0;
   }

   /**
    * Initiates the fade-in animation.
    */
   public void fadeIn()
   {
      if (fadeCount < RGB_ALPHA_MAX)
         fadeCount++;
   }

   /**
    * Paints the fadeable string onto the graphics context.
    * @param g The graphics context.
    */
   public void paint(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;

      Font font = new Font ("Sans-Serif", Font.PLAIN, fontSize);
      g2.setFont(font);
      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D bounds = font.getStringBounds(string, context);

      double ascent = -bounds.getY();
      double descent = bounds.getHeight() - ascent;
      double extent = bounds.getWidth();

      g2.setColor(new Color(
              color.getRed(),
              color.getGreen(),
              color.getBlue(),
              fadeCount
              ));
      g2.drawString(
              string,
              (int) (ExplosionTester.WIDTH - extent) / 2,
              (int) (((ExplosionTester.HEIGHT - (ascent + descent)) / 2) + ascent)
              );

   }

   //----------------Private Methods/Fields----------------------
   private int fadeCount;
   private int fontSize;
   private Color color;
   private String string;

   private static final int RGB_ALPHA_MAX = 255;
}