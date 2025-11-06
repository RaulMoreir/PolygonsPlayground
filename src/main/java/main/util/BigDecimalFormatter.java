package main.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalFormatter {

     public static BigDecimal getBigDecimal(double value) {
         return new BigDecimal(value).setScale(4, RoundingMode.HALF_EVEN);
     }

     public static BigDecimal getBigDecimal(float value) {
         return new BigDecimal(value).setScale(4, RoundingMode.HALF_EVEN);
     }

     public static String getBigDecimalAsString() {

         return "";
     }

}
