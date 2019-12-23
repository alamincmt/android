package com.example.compassdemo;

public class MyMath {
    public static double DTR = 0.017453292519943295d;
    public static double RTD = 57.29577951308232d;

    public static double power(double d, int i) {
        double d2 = 1.0d;
        if (i != 0) {
            if (i > 0) {
                while (i > 0) {
                    d2 *= d;
                    i--;
                }
            } else if (i < 0) {
                while (i < 0) {
                    d2 /= d;
                    i++;
                }
            }
        }
        return d2;
    }

    public static int[] whatIsTheTimeNow() {
        return null;
    }

    public static double fixAngle(double d) {
        double floor = d - (Math.floor(d / 360.0d) * 360.0d);
        return floor < 0.0d ? floor + 360.0d : floor;
    }

    public static double fixHours(double d) {
        double floor = d - (Math.floor(d / 24.0d) * 24.0d);
        return floor < 0.0d ? floor + 24.0d : floor;
    }

    public static double dSin(double d) {
        return Math.sin(d * DTR);
    }

    public static double dCos(double d) {
        return Math.cos(d * DTR);
    }

    public static double dTan(double d) {
        return Math.tan(d * DTR);
    }

    public static double dASin(double d) {
        return dATan(d / (Math.sqrt(1.0d - power(d, 2)) + 1.0d)) * 2.0d;
    }

    public static double dACos(double d) {
        return 90.0d - dASin(d);
    }

    public static double dATan(double d) {
        double d2;
        if (Math.abs(d) < 1.0d) {
            int i = -1;
            d2 = d;
            for (int i2 = 3; i2 < 300; i2 += 2) {
                double d3 = (double) i;
                double power = power(d, i2);
                Double.isNaN(d3);
                double d4 = d3 * power;
                double d5 = (double) i2;
                Double.isNaN(d5);
                d2 += d4 / d5;
                i *= -1;
            }
        } else {
            double d6 = 0.0d;
            int i3 = -1;
            for (int i4 = 1; i4 < 300; i4 += 2) {
                double d7 = (double) (i3 * 1);
                double power2 = power(d, i4);
                double d8 = (double) i4;
                Double.isNaN(d8);
                double d9 = power2 * d8;
                Double.isNaN(d7);
                d6 += d7 / d9;
                i3 *= -1;
            }
            d2 = d >= 1.0d ? d6 + 1.5707963267948966d : d6 - 1.5707963267948966d;
        }
        return d2 * RTD;
    }

    public static double dATan2(double d, double d2) {
        if (d2 > 0.0d) {
            return dATan(d / d2);
        }
        if (d2 == 0.0d) {
            if (d > 0.0d) {
                return 90.0d;
            }
            return d < 0.0d ? -90.0d : 0.0d;
        } else if (d2 >= 0.0d) {
            return 0.0d;
        } else {
            if (d > 0.0d) {
                return dATan(d / d2) + 180.0d;
            }
            if (d < 0.0d) {
                return dATan(d / d2) - 180.0d;
            }
            return 0.0d;
        }
    }

    public static double dACot(double d) {
        return 90.0d - dATan(d);
    }

    public static double frac(double d) {
        double d2 = (double) ((int) d);
        Double.isNaN(d2);
        return d - d2;
    }

    public static double normalize(double d) {
        double d2 = (double) ((int) d);
        Double.isNaN(d2);
        double d3 = d - d2;
        return d3 < 0.0d ? d3 + 1.0d : d3;
    }

    public static double power(int i, int i2) {
        double d = (double) i;
        Double.isNaN(d);
        return power(d * 1.0d, i2);
    }

    public static int mod(int i, int i2) {
        int abs = Math.abs(i);
        int abs2 = Math.abs(i2);
        while (abs > abs2 - 1) {
            abs -= abs2;
        }
        return abs;
    }

    public static int round(double d) {
        int i = (int) d;
        double d2 = (double) i;
        Double.isNaN(d2);
        return d - d2 >= 0.5d ? i + 1 : i;
    }

    public static double roundTime(double d) {
        double d2 = (double) ((int) d);
        Double.isNaN(d2);
        double round = (double) round((d - d2) * 60.0d);
        Double.isNaN(round);
        double d3 = round / 60.0d;
        Double.isNaN(d2);
        return d2 + d3;
    }
}
