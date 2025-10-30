package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1218
 * Ищем компоненты сильной связаности в графе
 * https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%A2%D0%B0%D1%80%D1%8C%D1%8F%D0%BD%D0%B0
 */
public class ExpApp
{
    public static int n;
    public static boolean[][] m;
    public static int index;
    public static int sccnt;
    public static int[] vi;
    public static int[] vli;
    public static int[] sc;
    public static boolean[] vs;
    public static List<Integer> s;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        //Set<Integer>[] e = new HashSet[n];
        m = new boolean[n][n];
        int[] cnt = new int[n];
        String[] nms = new String[n];
        int[][] p = new int[3][n];
        for (int i = 0; i < n; i++)
        {
            //e[i] = new HashSet<>();
            String[] parts = reader.readLine().split(" ");
            nms[i] = parts[0];
            p[0][i] = Integer.parseInt(parts[1]);
            p[1][i] = Integer.parseInt(parts[2]);
            p[2][i] = Integer.parseInt(parts[3]);
            for (int j = 0; j < i; j++)
            {
                int sc = 0;
                if (p[0][i] > p[0][j])
                    sc++;
                if (p[1][i] > p[1][j])
                    sc++;
                if (p[2][i] > p[2][j])
                    sc++;
                if (sc >= 2)
                {
                    //e[j].add(i);
                    m[i][j] = true;
                    cnt[j]++;
                }
                else
                {
                    //e[i].add(j);
                    m[j][i] = true;
                    cnt[i]++;
                }
            }
        }
        
        boolean fnd = false;
        for (int i = 0; i < n; i++)
        {
            //if (e[i].isEmpty())
            if (cnt[i] == 0)
            {
                out.println(nms[i]);
                fnd = true;
                break;
            }
        }
        
        if (!fnd)
        {
            // Ищем компоненты сильной связаности в графе
            // https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%A2%D0%B0%D1%80%D1%8C%D1%8F%D0%BD%D0%B0
            index = 1;
            sccnt = 0;
            vi = new int[n];
            vli = new int[n];
            sc = new int[n];
            vs = new boolean[n];
            s = new ArrayList<>(n);
            for (int i = 0; i < n; i++)
            {
                if (vi[i] == 0)
                    strongConnect(i);
            }
            
            for (int i = 0; i < n; i++)
            {
                boolean fndsc = true;
                for (int j = 0; j < n; j++)
                {
                    if (m[j][i] && sc[i] != sc[j])
                    {
                        fndsc = false;
                        break;
                    }
                }
                if (fndsc)
                {
                    for (int j = 0; j < n; j++)
                    {
                        if (sc[j] == sc[i])
                            out.println(nms[j]);
                    }
                    break;
                }
            }
        }
        
        out.flush();
    }
    
    public static void strongConnect(int i)
    {
        vi[i] = index;
        vli[i] = index;
        index++;
        s.add(i);
        vs[i] = true;
        for (int j = 0; j < n; j++)
        {
            if (m[j][i])
            {
                if (vi[j] == 0)
                {
                    strongConnect(j);
                    vli[i] = Math.min(vli[i], vli[j]);
                }
                else if (vs[j])
                {
                    vli[i] = Math.min(vli[i], vli[j]);
                }
            }
        }
        
        if (vli[i] == vi[i])
        {
            sccnt++;
            int j;
            do
            {
                j = s.remove(s.size() - 1);
                vs[j] = false;
                sc[j] = sccnt;
            } while (j != i);
        }
    }
}
