package MEDMEX.Utils;

import java.awt.Color;

public class Timer {
	
		public long timeleft = 0;
		public long lastMS = System.currentTimeMillis();
		
		public void reset() {
			lastMS = System.currentTimeMillis();
		}
		
		public boolean hasTimeElapsed(long time, boolean reset) {
			timeleft = time - (System.currentTimeMillis()-lastMS);
			if(System.currentTimeMillis()-lastMS > time) {
				if(reset)
					reset();
				
				return true;
			}
			
			return false;
		}
		
	
	
}
