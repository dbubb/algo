package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1185
 */
public class ExpApp
{
    public static boolean[] prime;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        // String str = reader.readLine();
        
        int n = Integer.parseInt(parts[0]);
        int l = Integer.parseInt(parts[1]);
        
        int[] xy = new int[n];
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++)
        {
            parts = reader.readLine().split(" ");
            xy[i] = (Integer.parseInt(parts[0]) + 10000) * 100000 + (Integer.parseInt(parts[1]) + 10000);
        }
        
        Arrays.sort(xy);
        
        // left
        x[0] = xy[0] / 100000 - 10000;
        y[0] = xy[0] % 100000 - 10000;
        int cnt = 1;
        int idx = 1;
        for (int i = idx; i < n; i++)
        {
            int nx = xy[i] / 100000 - 10000;
            if (nx != x[0])
            {
                if (i > 1)
                {
                    x[cnt] = xy[i-1] / 100000 - 10000;
                    y[cnt] = xy[i-1] % 100000 - 10000;
                    cnt++;
                }
                idx = i;
                break;
            }
        }
        
        // top
        int lastx = xy[n-1] / 100000 - 10000;
        do
        {
            double maxk = -2.0;
            int maxki = idx;
            for (int i = idx; i < n; i++)
            {
                int nx = xy[i] / 100000 - 10000;
                int ny = xy[i] % 100000 - 10000;
                double k = (double)(ny - y[cnt-1]) / (double)(nx - x[cnt-1]);
                if ((i == idx) || k >= maxk)
                {
                    maxk = k;
                    maxki = i;
                }
            }
            x[cnt] = xy[maxki] / 100000 - 10000;
            y[cnt] = xy[maxki] % 100000 - 10000;
            cnt++;
            idx = maxki + 1;
        } while (x[cnt-1] < lastx);
        
        // right
        idx -= 2;
        for (int i = idx; i >= 0; i--)
        {
            int nx = xy[i] / 100000 - 10000;
            if (nx != x[cnt-1])
            {
                if (i < idx)
                {
                    x[cnt] = xy[i+1] / 100000 - 10000;
                    y[cnt] = xy[i+1] % 100000 - 10000;
                    cnt++;
                }
                idx = i;
                break;
            }
        }
        
        // bottom
        do
        {
            double maxk = -2.0;
            int maxki = idx;
            for (int i = idx; i >= 0; i--)
            {
                int nx = xy[i] / 100000 - 10000;
                int ny = xy[i] % 100000 - 10000;
                double k = (double)(y[cnt-1] - ny) / (double)(x[cnt-1] - nx);
                if ((i == idx) || k >= maxk)
                {
                    maxk = k;
                    maxki = i;
                }
            }
            if (maxki == 0)
                break;
            x[cnt] = xy[maxki] / 100000 - 10000;
            y[cnt] = xy[maxki] % 100000 - 10000;
            cnt++;
            idx = maxki - 1;
        } while (x[cnt-1] < lastx);
        
        // len
        double len = 0.0;
        for (int i = 0; i < cnt; i++)
        {
            int i2 = i + 1;
            if (i2 == cnt)
                i2 = 0;
            int dx = x[i] - x[i2];
            int dy = y[i] - y[i2];
            len += Math.sqrt(dx*dx+dy*dy);
        }
        len += (Math.PI * 2.0 * (double)l);
        
        out.println((int)(len + 0.5));

        out.flush();
    }
}
