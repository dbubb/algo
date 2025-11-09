package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1156
 * Проверка графа на двудольность, разбивка на доли
 * https://brestprog.by/topics/bipartite/
 */
public class ExpApp
{
    public static int n, m, cnt;
    public static HashSet<Integer>[] e;
    public static int[] clr;
    public static int[][] ndc;
    public static boolean[] ndf;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        n = Integer.parseInt(parts[0]);
        m = Integer.parseInt(parts[1]);
        int n21 = n*2+1;
        e = new HashSet[n21];
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            if (e[n1] == null)
                e[n1] = new HashSet<>();
            e[n1].add(n2);
            if (e[n2] == null)
                e[n2] = new HashSet<>();
            e[n2].add(n1);
        }
        
        // Проверка графа на двудольность, разбивка на доли
        // https://brestprog.by/topics/bipartite/
        clr = new int[n21];
        cnt = 0;
        boolean imp = false;
        // граф может быть несвязный
        for (int i = 1; i < n21; i++)
        {
            if (clr[i] == 0)
            {
                cnt++;
                if (!dfs(i, cnt))
                {
                    imp = true;
                    break;
                }
            }
        }
        
        if (imp)
            out.println("IMPOSSIBLE");
        else
        {
            ndc = new int[cnt+1][2];
            ndf = new boolean[cnt+1];
            for (int i = 1; i < n21; i++)
            {
                if (clr[i] < 0)
                    ndc[-clr[i]][1]++;
                else
                    ndc[clr[i]][0]++;
            }
            
            if (fnd(1, 0, 0))
            {
                int rn = 0;
                for (int i = 1; i < n21; i++)
                {
                    if ((clr[i] < 0 && ndf[-clr[i]]) || (clr[i] > 0 && !ndf[clr[i]]))
                    {
                        out.print(i);
                        rn++;
                        if (rn == n)
                        {
                            out.println();
                            break;
                        }
                        else
                            out.print(" ");
                    }
                }
                
                rn = 0;
                for (int i = 1; i < n21; i++)
                {
                    if ((clr[i] < 0 && !ndf[-clr[i]]) || (clr[i] > 0 && ndf[clr[i]]))
                    {
                        out.print(i);
                        rn++;
                        if (rn == n)
                        {
                            out.println();
                            break;
                        }
                        else
                            out.print(" ");
                    }
                }
            }
            else
                out.println("IMPOSSIBLE");
        }
        
        out.flush();
    }
    
    public static boolean dfs(int num, int cl)
    {
        clr[num] = cl;
        if (e[num] != null)
        {
            for (int nxt : e[num])
            {
                if (clr[nxt] == 0)
                {
                    if (!dfs(nxt, -cl))
                        return false;
                }
                else if (clr[nxt] == cl)
                    return false;
            }
        }
        return true;
    }
    
    public static boolean fnd(int nd, int n1, int n2)
    {
        if (ndc[nd][0] == ndc[nd][1])
        {
            int nn1 = n1 + ndc[nd][0];
            int nn2 = n2 + ndc[nd][1];
            ndf[nd] = false;
            if (nn1 == n && nn2 == n)
                return true;
            else if (nn1 > n || nn2 > n)
                return false;
            else
                return fnd(nd+1, nn1, nn2);
        }
        else if (((ndc[nd][0] > ndc[nd][1]) && (n1 < n2)) || ((ndc[nd][0] < ndc[nd][1]) && (n1 > n2)))
        {
            int nn1 = n1 + ndc[nd][0];
            int nn2 = n2 + ndc[nd][1];
            ndf[nd] = false;
            if (nn1 == n && nn2 == n)
                return true;
            else if (nn1 > n || nn2 > n)
                return false;
            else
            {
                if (fnd(nd+1, nn1, nn2))
                    return true;
                nn1 = n1 + ndc[nd][1];
                nn2 = n2 + ndc[nd][0];
                ndf[nd] = true;
                if (nn1 == n && nn2 == n)
                    return true;
                else if (nn1 > n || nn2 > n)
                    return false;
                else
                    return fnd(nd+1, nn1, nn2);
            }
        }
        else
        {
            int nn1 = n1 + ndc[nd][1];
            int nn2 = n2 + ndc[nd][0];
            ndf[nd] = true;
            if (nn1 == n && nn2 == n)
                return true;
            else if (nn1 > n || nn2 > n)
                return false;
            else
            {
                if (fnd(nd+1, nn1, nn2))
                    return true;
                nn1 = n1 + ndc[nd][0];
                nn2 = n2 + ndc[nd][1];
                ndf[nd] = false;
                if (nn1 == n && nn2 == n)
                    return true;
                else if (nn1 > n || nn2 > n)
                    return false;
                else
                    return fnd(nd+1, nn1, nn2);
            }
        }
    }
}
