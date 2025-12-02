package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1989
 */
public class ExpApp
{
    public static char[] ch;
    public static int n;
    public static int k = 31;
    public static int p = 1000000007;
    public static int[] dg;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        //int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        ch = reader.readLine().toCharArray();
        n = ch.length;
        int m = Integer.parseInt(reader.readLine());
        
        // https://wiki.algocode.ru/index.php?title=%D0%A5%D0%B5%D1%88%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5
        dg = new int[n+1];
        FenwickTree t1 = new FenwickTree(n + 1);
        FenwickTree t2 = new FenwickTree(n + 1);
        //int[] h1 = new int[n];
        //int[] h2 = new int[n];
        dg[0] = 1;
        for (int i = 0; i < n; i++)
        {
            //h1[i] = ((i > 0 ? h1[i-1] : 0) + dg[i] * (ch[i]     - 'a' + 1)) % p;
            //h2[i] = ((i > 0 ? h2[i-1] : 0) + dg[i] * (ch[n-1-i] - 'a' + 1)) % p;
            t1.add(i + 1, getHash(i, false));
            t2.add(i + 1, getHash(i, true));
            dg[i+1] = (int)(((long)dg[i] * k) % p);
        }
        
        for (int i = 0; i < m; i++)
        {
            String[] parts = reader.readLine().split(" ");
            if ("change".equals(parts[0]))
            {
                int indx = Integer.parseInt(parts[1]) - 1;
                int oldh1 = getHash(indx, false);
                int oldh2 = getHash(n - 1 - indx, true);
                ch[indx] = parts[2].charAt(0);
                int newh1 = getHash(indx, false);
                int newh2 = getHash(n - 1 - indx, true);
                t1.add(indx + 1, newh1 - oldh1);
                t2.add(n - indx, newh2 - oldh2);
            }
            else
            {
                int indx1 = Integer.parseInt(parts[1]);
                int indx2 = Integer.parseInt(parts[2]);
                /*boolean f = true;
                while (indx2 > indx1)
                {
                    if (ch[indx1] != ch[indx2])
                    {
                        f = false;
                        break;
                    }
                    indx1++;
                    indx2--;
                }*/
                long s1 = t1.sum(indx1, indx2);
                long s2 = t2.sum(n - indx2 + 1, n - indx1 + 1);
                if (indx2 > n - indx1 + 1)
                {
                    int ddg = indx2 - n + indx1 - 1;
                    s2 = (s2 * dg[ddg]) % p;
                }
                else if (indx2 < n - indx1 + 1)
                {
                    int ddg = n - indx1 + 1 - indx2;
                    s1 = (s1 * dg[ddg]) % p;
                }
                
                out.println(s1 == s2 ? "Yes" : "No");
            }
        }
        
        out.flush();
    }
    
    public static int getHash(int i, boolean revers)
    {
        char c = revers ? ch[n-1-i] : ch[i];
        return (int)(((long)dg[i] * (c - 'a' + 1)) % p);
    }
    
    // https://www.topcoder.com/thrive/articles/Binary%20Indexed%20Trees
    // Не использовать индекс == 0 !!!
    public static class FenwickTree
    {
        private int[] tree;
        
        public FenwickTree(int size)
        {
            tree = new int[size];
        }
        
        public int sum(int index)
        {
            int sum = 0;
            while (index > 0)
            {
                sum = (sum + tree[index]) % p;
                index -= (index & -index);
            }
            return sum;
        }
        
        public int sum(int indexFrom, int indexTo)
        {
            int res = sum(indexTo) - sum(indexFrom - 1);
            if (res < 0)
                res += p;
            return res;
        }
        
        public void add(int index, int val)
        {
            while (index < tree.length)
            {
                tree[index] = (tree[index] + val) % p;
                if (tree[index] < 0)
                    tree[index] += p;
                index += (index & -index);
            }
        }
    }
}
