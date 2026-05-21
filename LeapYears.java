package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1201
 */
public class ExpApp
{
    public static String[] dw = { "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
    public static int[] dm = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int d = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        
        boolean leap = (y % 4 == 0) && ((y % 100 != 0) || (y % 400 == 0));
        int dc = dm[m-1];
        if (m == 2 && leap)
            dc++;
        
        int dy = y - 1600;
        
        int days = 365 * dy + ((dy+3) / 4) - ((dy+99) / 100) + ((dy+399) / 400);
        for (int i = 0; i < m - 1; i++)
            days += dm[i];
        if (m > 2 && leap)
            days++;
        //days += d;
        int fdw = (5 + days) % 7; // TODO
        
        int[][] cal = new int[7][6];
        int row = fdw, col = 0;
        for (int i = 1; i <= dc; i++)
        {
            cal[row][col] = i;
            row++;
            if (row >= 7)
            {
                row = 0;
                col++;
            }
        }
        
        for (int i = 0; i < 7; i++)
        {
            StringBuilder sb = new StringBuilder(dw[i]);
            for (int j = 0; j < 6; j++)
            {
                sb.append(' ');
                int curd = cal[i][j];
                boolean cur = (curd == d);
                sb.append(cur ? '[' : ' ');
                if (curd == 0)
                    sb.append("  ");
                else if (curd < 10)
                    sb.append(' ').append(curd);
                else
                    sb.append(curd);
                sb.append(cur ? ']' : ' ');
            }
            
            out.println(sb.toString().trim());
        }
        
        //out.println(fdw);
        
        out.flush();
    }
}
