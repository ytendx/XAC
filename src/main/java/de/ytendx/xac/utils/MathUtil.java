package de.ytendx.xac.utils;

public class MathUtil {

    public static final double EXPANDER = Math.pow(2, 24);

    public static double getAmount(double d){
        String s = d + "";
        if(s.contains("-")){
            s.replaceAll("-", "");
        }
        return Double.parseDouble(s);
    }

    public static long getGcd(final long current, final long previous) {
        return (previous <= 16384L) ? current : getGcd(previous, current % previous);
    }

    public static double getGcd(final double a, final double b) {
        if (a < b) {
            return getGcd(b, a);
        }

        if (Math.abs(b) < 0.001) {
            return a;
        } else {
            return getGcd(b, a - Math.floor(a / b) * b);
        }
    }
}
