package de.ytendx.xac.utils;

public class MathUtil {

    public static double getAmount(double d){
        String s = d + "";
        if(s.contains("-")){
            s.replaceAll("-", "");
        }
        return Double.parseDouble(s);
    }

}
