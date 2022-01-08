package MEDMEX.Utils;



import java.awt.Color;

public class ColorUtil {
	
	public static int getRainbow(float seconds,float saturation, float brightness) {
		float hue = ((System.currentTimeMillis()) % (int)(4000) *1000) / (float)(4000 * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
		
	}
	public static int getRainbow(float seconds,float saturation, float brightness, long index) {
		float hue = ((System.currentTimeMillis() + index) % (int)(seconds*1000) *1000) / (float)(seconds*1000 * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
		
	}
	public static int astolfoColorsDraw(int yOffset, int yTotal) {
        return astolfoColorsDraw(yOffset, yTotal, 2900F);
     }
	
	 public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
         float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
         while (hue > speed) {
            hue -= speed;
         }
         hue /= speed;
         if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
         }
         hue += 0.5F;
         return Color.HSBtoRGB(hue, 0.5f, 1F);
      }

}
