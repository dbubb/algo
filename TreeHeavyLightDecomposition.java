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
     * Heavy-Light-Decomposition
     * https://codeforces.com/blog/entry/12239?locale=ru
     * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1320
     */
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        // 
        // https://ru.algorithmica.org/cs/trees/heavy-light/
        // https://codeforces.com/blog/entry/12239?locale=ru
        
        Node[] nodes = new Node[n+1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node();
        for (int i = 1; i < n; i++)
        {
            String[] parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            nodes[n1].edges.add(n2);
            nodes[n2].edges.add(n1);
        }
        
        nodes[1].parent = -1;
        nodes[1].lvl = 0;
        
        dfs(nodes, 1);
        
        Param prm = new Param(n);
        hld(nodes, 1, prm);
        
        SegmentsTree st = new SegmentsTree(n+1);
        
        int m = Integer.parseInt(reader.readLine());
        for (int i = 0; i < m; i++)
        {
            String[] parts = reader.readLine().split(" ");
            if ("I".equals(parts[0]))
            {
                int num = Integer.parseInt(parts[1]);
                int dr = Integer.parseInt(parts[2]);
                st.update(nodes[num].num, dr);
            }
            else
            {
                Node n1 = nodes[Integer.parseInt(parts[1])];
                Node n2 = nodes[Integer.parseInt(parts[2])];
                int res = 0;
                while (n1.chain != n2.chain)
                {
                    Node ns1 = nodes[prm.chainStart[n1.chain]];
                    Node ns2 = nodes[prm.chainStart[n2.chain]];
                    if (ns1.lvl < ns2.lvl)
                    {
                        res = Math.max(res, st.get(ns2.num, n2.num));
                        n2 = nodes[ns2.parent];
                    }
                    else
                    {
                        res = Math.max(res, st.get(ns1.num, n1.num));
                        n1 = nodes[ns1.parent];
                    }
                }
                if (n1.lvl < n2.lvl)
                {
                    res = Math.max(res, st.get(n1.num, n2.num));
                }
                else
                {
                    res = Math.max(res, st.get(n2.num, n1.num));
                }
                out.println(res);
            }
        }

        out.flush();
    }
    
    public static void dfs(Node[] nodes, int n)
    {
        Node nd = nodes[n];
        nd.subCnt = 1;
        for (int next : nd.edges)
        {
            Node nnd = nodes[next];
            if (nnd.lvl == -1)
            {
                nnd.parent = n;
                nnd.lvl = nd.lvl + 1;
                dfs(nodes, next);
                nd.subCnt += nnd.subCnt;
                if (nd.heavy < 0 || nnd.subCnt > nodes[nd.heavy].subCnt)
                    nd.heavy = next;
            }
        }
    }
    
    public static void hld(Node[] nodes, int n, Param prm)
    {
        Node nd = nodes[n];
        nd.chain = prm.chain;
        nd.num = prm.num;
        prm.num++;
        
        if (prm.chainSize[prm.chain] == 0)
            prm.chainStart[prm.chain] = n;
        prm.chainSize[prm.chain]++;
        
        if (nd.heavy > 0)
            hld(nodes, nd.heavy, prm);
        
        for (int next : nd.edges)
        {
            if (next == nd.parent || next == nd.heavy)
                continue;
            prm.chain++;
            hld(nodes, next, prm);
        }
    }
    
    public static class Node
    {
        int parent;
        int lvl = -1;
        int subCnt = -1;
        int heavy = -1;
        int chain = -1;
        int num = -1;
        List<Integer> edges = new ArrayList<>();
    }
    
    public static class Param
    {
        int chain;
        int num;
        int[] chainStart;
        int[] chainSize;
        
        public Param(int sz)
        {
            chain = 0;
            num = 0;
            chainStart = new int[sz];
            chainSize = new int[sz];
        }
    }
    
    public static class SegmentsTree
    {
        public int[] arr;
        public int n;
        
        public SegmentsTree(int sz)
        {
            n = closestDeg2(sz);
            arr = new int[2 * n];
        }
        
        public int getAll()
        {
            return (arr[1] == 0) ? 1 : arr[1];
        }
        
        public int get(int l, int r)
        {
            return get(1, 0, n-1, l, r);
        }
        public int get(int v, int tl, int tr, int l, int r)
        {
            if (l > r)
                return 0;
            if (l == tl && r == tr)
                return arr[v];
            int tm = (tl + tr) / 2;
            return calc(get(v*2, tl, tm, l, Math.min(r,tm)), get(v*2+1, tm+1, tr, Math.max(l,tm+1), r));
        }
        
        public void update(int pos, int val)
        {
            update(1, 0, n-1, pos, val);
        }
        
        public boolean update(int v, int tl, int tr, int pos, int val)
        {
            if (tl == tr)
            {
                boolean res = arr[v] != val;
                arr[v] += val;
                return res;
            }
            else
            {
                int tm = (tl + tr) / 2;
                boolean updated = (pos <= tm) ?
                    update(v*2, tl, tm, pos, val) :
                    update(v*2+1, tm+1, tr, pos, val);
                if (updated)
                {
                    int nv = calc(arr[v*2], arr[v*2+1]);
                    boolean res = arr[v] != nv;
                    arr[v] = nv;
                    return res;
                }
                return false;
            }
        }
        
        public int calc(int n1, int n2)
        {
            return Math.max(n1, n2);
        }
        
        public static int closestDeg2(int n)
        {
            int res = 1;
            while (res < n)
                res *= 2;
            return res;
        }
    }
}
