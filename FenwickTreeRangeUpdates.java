package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class ExpApp
{
    /**
     * https://acm.timus.ru/problem.aspx?space=1&num=2062
     */
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int[] a = new int[n+1];
        for (int i = 0; i < n; i++)
            a[i+1] = Integer.parseInt(parts[i]);
        
        // precalc dividers
        int[][] dvd = new int[n+1][];
        int[] dc = new int[n+1];
        for (int i = 2; i <= n; i++)
        {
            int idx = i;
            while (idx <= n)
            {
                dc[idx]++;
                idx += i;
            }
        }
        for (int i = 1; i <= n; i++)
        {
            dvd[i] = new int[dc[i]+1];
            dvd[i][dc[i]] = 1;
            dc[i]--;
        }
        for (int i = 2; i <= n; i++)
        {
            int idx = i;
            while (idx <= n)
            {
                dvd[idx][dc[idx]] = i;
                dc[idx]--;
                idx += i;
            }
        }
        
        int q = Integer.parseInt(reader.readLine());
        FenwickTree ft = new FenwickTree(n+1);
        for (int i = 0; i < q; i++)
        {
            parts = reader.readLine().split(" ");
            if ("1".equals(parts[0]))
            {
                int idx = Integer.parseInt(parts[1]);
                long res = a[idx];
                for (int j = 0; j < dvd[idx].length; j++)
                    res += ft.get(dvd[idx][j]);
                out.println(res);
            }
            else
            {
                int l = Integer.parseInt(parts[1]);
                int r = Integer.parseInt(parts[2]);
                int d = Integer.parseInt(parts[3]);
                ft.updateRange(l, r, d);
            }
        }

        out.flush();
    }
    
    // Дерево Фенвика с обновлением интервала и получением значения в конкретном элементе
    // https://www.geeksforgeeks.org/binary-indexed-tree-range-updates-point-queries/
    // Не использовать индекс == 0 !!!
    public static class FenwickTree
    {
        private long[] tree;
        
        public FenwickTree(int size)
        {
            tree = new long[size];
        }
        
        public long get(int index)
        {
            long sum = 0;
            while (index > 0)
            {
                sum += tree[index];
                index -= (index & -index);
            }
            return sum;
        }
        
        public void update(int index, int val)
        {
            while (index < tree.length)
            {
                tree[index] += val;
                index += (index & -index);
            }
        }
        
        public void updateRange(int l, int r, int val)
        {
            update(l, val);
            update(r + 1, -val);
        }
    }
}
