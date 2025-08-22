package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1047
 */
public class ExpApp
{
    public static boolean[] prime;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        // String str = reader.readLine();
        
        double[] a = new double[n+2];
        a[0] = Double.parseDouble(reader.readLine());
        a[n+1] = Double.parseDouble(reader.readLine());
        for (int i = 1; i <= n; i++)
            a[i] = Double.parseDouble(reader.readLine());
        
        if (n == 1)
        {
            double r = (a[0] + a[2]) / 2.0 - a[1];
            out.format("%.2f", r);
        }
        else
        {
            // Решаем СЛАУ методом Гаусса
            // https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D1%82%D0%BE%D0%B4_%D0%93%D0%B0%D1%83%D1%81%D1%81%D0%B0
            // Инициализируем матрицу, храним только три элемента в самой матрице (диагональ и по два с краев) + столбец свободных членов
            double[][] g = new double[n+1][4];
            g[1][0] = 0.0;
            g[1][1] = 1.0;
            g[1][2] = -0.5;
            g[1][3] = -a[1] + (a[0] / 2.0);
            for (int i = 2; i < n; i++)
            {
                g[i][0] = -0.5;
                g[i][1] = 1.0;
                g[i][2] = -0.5;
                g[i][3] = -a[i];
            }
            g[n][0] = -0.5;
            g[n][1] = 1.0;
            g[n][2] = 0.0;
            g[n][3] = -a[n] + (a[n+1] / 2.0);
            
            // Прямой ход
            for (int i = 2; i <= n; i++)
            {
                double k = g[i][0];
                g[i][0] = 0.0;
                g[i][1] -= (g[i-1][2] * k);
                g[i][3] -= (g[i-1][3] * k);
                k = g[i][1];
                g[i][1] = 1.0;
                g[i][2] /= k;
                g[i][3] /= k;
            }
            
            // Обратный ход
            for (int i = n-1; i >= 1; i--)
            {
                double k = g[i][2];
                g[i][2] = 0.0;
                g[i][3] -= (g[i+1][3] * k);
            }
            out.format("%.2f", g[1][3]);
        }
        //out.println(0);

        out.flush();
    }
}
