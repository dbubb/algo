package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1141
 */
public class ExpApp
{
    // Простые числа до корня из 32000
    public static int[] primes = new int[]{
        2,3,5,7,11,13,17,19,23,29,
        31,37,41,43,47,53,59,61,67,71,
        73,79,83,89,97,101,103,107,109,113,
        127,131,137,139,149,151,157,163,167,173};
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int k = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        for (int i = 0; i < k; i++)
        {
            String[] parts = reader.readLine().split(" ");
            int e = Integer.parseInt(parts[0]);
            int n = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            
            int p = 0, q = 0;
            for (int j = 0; j < primes.length; j++)
            {
                if (n % primes[j] == 0)
                {
                    p = primes[j];
                    q = n / p;
                    break;
                }
            }
            int pq = (p-1)*(q-1);
            int d = 1;
            while ((e*d) % pq != 1)
                d++;
            out.println(powmod(c, d, n));
        }
        
        out.flush();
    }
    
    public static int powmod(int x, int y, int m)
    {
        int res = 1;
        while (y > 0)
        {
            if ((y & 1) == 0)
            {
                x = (x * x) % m;
                y = y >> 1;
            }
            else
            {
                res = (res * x) % m;
                y--;
            }
        }
        return res;
    }
}
