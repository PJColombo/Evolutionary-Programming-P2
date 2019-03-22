package application.model.p1_utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundDecimal {
	
	public static double roundHalfDown(double d) {
	    return new BigDecimal(d).setScale(0, RoundingMode.HALF_DOWN)
                .doubleValue();
	}

}
