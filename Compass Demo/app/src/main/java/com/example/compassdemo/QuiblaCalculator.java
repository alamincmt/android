package com.example.compassdemo;

import java.io.PrintStream;

public class QuiblaCalculator {
    public static double MECCA_LATITUDE = 21.41666d;
    public static double MECCA_LONGITUDE = 39.81666d;

    public static double doCalculate(double d, double d2) {
        double d3 = d2 - MECCA_LONGITUDE;
        double dACot = MyMath.dACot(((MyMath.dSin(d) * MyMath.dCos(d3)) - (MyMath.dCos(d) * MyMath.dTan(MECCA_LATITUDE))) / MyMath.dSin(d3));
        if (d2 > MECCA_LONGITUDE) {
            dACot += 180.0d;
        }
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("My Angle = ");
        sb.append(dACot);
        printStream.println(sb.toString());
        return dACot;
    }
}
