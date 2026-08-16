package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1111
 */
public class ExpApp
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        ArrayList<Square> sq = new ArrayList<>(n);
        for (int i = 1; i <= n; i++)
        {
            String[] parts = reader.readLine().split(" ");
            sq.add(new Square(i, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
        }
        
        String[] parts = reader.readLine().split(" ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        
        for (Square s : sq)
            s.calc(x, y);
        
        Collections.sort(sq);
        
        boolean first = true;
        for (Square s : sq)
        {
            if (first)
                first = false;
            else
                out.print(' ');
            out.print(s.num);
        }
        
        out.println("");
        
        out.flush();
    }
    
    public static class Square implements Comparable<Square>
    {
        public static double EPS = 0.0000000001;
        public static double PI4 = Math.PI / 4.0;
        
        public int num;
        public int x1;
        public int x2;
        public int y1;
        public int y2;
        public double len;
        
        public Square(int num, int x1, int y1, int x2, int y2)
        {
            this.num = num;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        public double calc(int x, int y)
        {
            int dx = Math.abs(x1 - x2);
            int dy = Math.abs(y1 - y2);
            double xd = x;
            double yd = y;
            double x1d = x1;
            double y1d = y1;
            double x2d = x2;
            double y2d = y2;
            if (dx != dy)
            {
                double a;
                if (dx == 0 || dy == 0)
                {
                    a = PI4;
                }
                else
                {
                    a = Math.atan2(dy, dx);
                    if ((x1 - x2)*(y1 - y2) < 0)
                        a = a - PI4;
                    else
                        a = PI4 - a;
                }
                double nx = xd * Math.cos(a) - yd * Math.sin(a);
                double ny = xd * Math.sin(a) + yd * Math.cos(a);
                xd = nx;
                yd = ny;
                nx = x1d * Math.cos(a) - y1d * Math.sin(a);
                ny = x1d * Math.sin(a) + y1d * Math.cos(a);
                x1d = nx;
                y1d = ny;
                nx = x2d * Math.cos(a) - y2d * Math.sin(a);
                ny = x2d * Math.sin(a) + y2d * Math.cos(a);
                x2d = nx;
                y2d = ny;
            }
            if (x2d < x1d)
            {
                double tmp = x1d;
                x1d = x2d;
                x2d = tmp;
            }
            if (y2d < y1d)
            {
                double tmp = y1d;
                y1d = y2d;
                y2d = tmp;
            }
            
            if (xd < x1d)
            {
                if (yd < y1d)
                    len = diag(x1d - xd, y1d - yd);
                else if (yd <= y2d)
                    len = x1d - xd;
                else
                    len = diag(x1d - xd, yd - y2d);
            }
            else if (xd <= x2d)
            {
                if (yd < y1d)
                    len = y1d - yd;
                else if (yd <= y2d)
                    len = 0.0;
                else
                    len = yd - y2d;
            }
            else
            {
                if (yd < y1d)
                    len = diag(xd - x2d, y1d - yd);
                else if (y <= y2d)
                    len = xd - x2d;
                else
                    len = diag(xd - x2d, yd - y2d);
            }
            return len;
        }
        
        public double diag(double dx, double dy)
        {
            return Math.sqrt((dx * dx) + (dy * dy));
        }

        @Override
        public int compareTo(Square s)
        {
            if (Math.abs(len - s.len) < EPS)
                return num < s.num ? -1 : 1;
            return len < s.len ? -1 : 1;
        }
    }
}
