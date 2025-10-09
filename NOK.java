package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1024
 */
public class ExpApp
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int[] p = new int[n+1];
        for (int i = 0; i < n; i++)
            p[i+1] = Integer.parseInt(parts[i]);
        
        int nk = 1;
        for (int i = 1; i <= n; i++)
        {
            int s = 1;
            int first = p[i];
            int cur = first;
            while (true)
            {
                int nxt = p[cur];
                if (nxt == first)
                    break;
                s++;
                cur = nxt;
            }
            nk = nok(nk, s);
        }
        
        out.println(nk);
        
        out.flush();
    }
    
    public static int nok(int n1, int n2)
    {
        return n1 / nod(n1, n2) * n2;
    }
    
    public static int nod(int n1, int n2)
    {
        while (n2 != 0)
        {
            int tmp = n1 % n2;
            n1 = n2;
            n2 = tmp;
        }
        return n1;
    }
}
