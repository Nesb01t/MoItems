package co.nesb01t.moitems.utils;

public class Random {
    public static boolean Chance(double n) {
        double random = Math.random();
        return random * 100 > n;
    }
}
