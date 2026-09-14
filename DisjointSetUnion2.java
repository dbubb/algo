package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1416
 */
public class ExpApp
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        
        Edge[] ed = new Edge[m];
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            ed[i] = new Edge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }
        
        // Ищем минимальный остов графа
        // Алгоритм Крускала
        // https://algorithmica.org/ru/mst
        Arrays.sort(ed, Comparator.comparingInt(e -> e.w));
        DisjointSetUnion dsu = new DisjointSetUnion(n);
        int cost = 0, mcnt = 0;
        for (Edge e : ed)
        {
            e.inc = dsu.union(e.n1, e.n2);
            if (e.inc)
                cost += e.w;
            else
                mcnt++;
        }
        
        out.println("Cost: " + cost);
        if (mcnt == 0)
            out.println("Cost: -1");
        else
        {
            int scost = -1;
            for (Edge ee : ed)
            {
                if (ee.inc)
                {
                    DisjointSetUnion cdsu = new DisjointSetUnion(n);
                    int ccost = 0;
                    for (Edge e : ed)
                    {
                        if (e == ee)
                            continue;
                        if (cdsu.union(e.n1, e.n2))
                            ccost += e.w;
                    }
                    if ((scost < 0 || ccost < scost) && cdsu.isAllJoined())
                        scost = ccost;
                    if (scost == cost)
                        break;
                }
            }
            out.println("Cost: " + scost);
        }
        
        out.flush();
    }
    
    public static class Edge
    {
        int n1;
        int n2;
        int w;
        boolean inc;
        
        public Edge(int n1, int n2, int w)
        {
            this.n1 = n1;
            this.n2 = n2;
            this.w = w;
        }
    }
    
    public static class DisjointSetUnion
    {
        int[] p;
        int[] w;
        
        public DisjointSetUnion(int n)
        {
            p = new int[n+1];
            w = new int[n+1];
        }
        
        public int top(int n)
        {
            return (p[n] == 0) ? n : (p[n] = top(p[n]));
        }
        
        public boolean union(int n1, int n2)
        {
            int tn1 = top(n1);
            int tn2 = top(n2);
            if (tn1 == tn2)
                return false;
            if (w[tn1] <= w[tn2])
            {
                w[tn2] += (w[tn1] + 1);
                p[tn1] = tn2;
            }
            else
            {
                w[tn1] += (w[tn2] + 1);
                p[tn2] = tn1;
            }
            return true;
        }
        
        public boolean isAllJoined()
        {
            int n1 = top(1);
            for (int i = 2; i < p.length; i++)
                if (top(i) != n1)
                    return false;
            return true;
        }
    }
}
