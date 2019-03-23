package application.model.p1_utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class RoundDecimal {
	
	public static double roundHalfDown(double d) {
	    return new BigDecimal(d).setScale(0, RoundingMode.HALF_DOWN)
                .doubleValue();
	}
	
	public static double twoDecimalNumber (double d) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
    	DecimalFormat df = (DecimalFormat)nf;

    	return Double.parseDouble(df.format(d));
	}

}
