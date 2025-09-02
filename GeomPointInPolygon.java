package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1215
 */
public class ExpApp
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        // String str = reader.readLine();
        
        int xs = Integer.parseInt(parts[0]);
        int ys = Integer.parseInt(parts[1]);
        int n = Integer.parseInt(parts[2]);
        
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++)
        {
            parts = reader.readLine().split(" ");
            x[i] = Integer.parseInt(parts[0]);
            y[i] = Integer.parseInt(parts[1]);
        }
        
        // Проверяем принадлежность многоугольнику
        // https://ru.wikibooks.org/wiki/%D0%A0%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8_%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC%D0%BE%D0%B2/%D0%97%D0%B0%D0%B4%D0%B0%D1%87%D0%B0_%D0%BE_%D0%BF%D1%80%D0%B8%D0%BD%D0%B0%D0%B4%D0%BB%D0%B5%D0%B6%D0%BD%D0%BE%D1%81%D1%82%D0%B8_%D1%82%D0%BE%D1%87%D0%BA%D0%B8_%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA%D1%83
        // https://wiki.algocode.ru/index.php?title=%D0%9F%D1%80%D0%B8%D0%BD%D0%B0%D0%B4%D0%BB%D0%B5%D0%B6%D0%BD%D0%BE%D1%81%D1%82%D1%8C_%D1%82%D0%BE%D1%87%D0%BA%D0%B8_%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA%D1%83
        int j = n-1;
        int cross = 0;
        for (int i = 0; i < n; i++)
        {
            if (y[i] !=  y[j])
            {
                if ((((y[i] <= ys) && (ys < y[j])) || ((y[j] <= ys) && (ys < y[i]))) &&
                        (xs > (double)(x[j] - x[i]) * (ys - y[i]) / (y[j] - y[i]) + x[i]))
                    cross++;
            }
            j = i;
        }
        
        if (cross % 2 != 0)
            out.format("%.3f", 0.0);
        else
        {
            // ищем минимальное расстояние
            j = n-1;
            double rmin = 0.0;
            for (int i = 0; i < n; i++)
            {
                double ri = Math.sqrt((double)(xs - x[i])*(xs - x[i]) + (double)(ys - y[i])*(ys - y[i]));
                double rj = Math.sqrt((double)(xs - x[j])*(xs - x[j]) + (double)(ys - y[j])*(ys - y[j]));
                double r = Math.min(ri, rj);
                
                // уравнение прямой
                double a = (double)(y[j] - y[i]);
                double b = (double)(x[i] - x[j]);
                double c = (double)x[j]*y[i]-(double)y[j]*x[i];
                double ab2 = a*a + b*b;
                if (ab2 > 0)
                {
                    // Координаты ближайшей точки на прямой
                    double xm = (b*(b*xs - a*ys) - a*c) / ab2;
                    double ym = (a*(-b*xs + a*ys) - b*c) / ab2;
                    
                    boolean byx = (x[i] == x[j]) || (x[i] < x[j] && x[i] <= xm && xm <= x[j]) || (x[i] > x[j] && x[i] >= xm && xm >= x[j]);
                    boolean byy = (y[i] == y[j]) || (y[i] < y[j] && y[i] <= ym && ym <= y[j]) || (y[i] > y[j] && y[i] >= ym && ym >= y[j]);
                    if (byx && byy)
                    {
                        // Расстояние от точки до прямой
                        double rm = Math.abs(a*xs+b*ys+c) / Math.sqrt(ab2);
                        r = Math.min(r, rm);
                    }
                }
                if (i == 0 || r < rmin)
                    rmin = r;
                j = i;
            }
            out.format("%.3f", rmin * 2);
            //out.println(BigDecimal.valueOf(rmin).setScale(3, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(2)).toString());
        }
        //out.println(0);

        out.flush();
    }
}
