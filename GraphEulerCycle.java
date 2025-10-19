package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1137
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
        
        Set<Integer>[] ot = new HashSet[1001];
        int sum = 0;
        int strt = 0;
        
        for (int i = 0; i < n; i++)
        {
            String[] parts = reader.readLine().split(" ");
            int m = Integer.parseInt(parts[0]);
            sum += m;
            int pr = Integer.parseInt(parts[1]);
            if (i == 0)
                strt = pr;
            for (int j = 1; j <= m; j++)
            {
                int nx = Integer.parseInt(parts[j+1]);
                if (ot[pr] == null)
                    ot[pr] = new HashSet<>();
                ot[pr].add(nx);
                pr = nx;
            }
        }
        
        out.print(sum);
        out.print(' ');
        
        List<Integer> res = new ArrayList<>(sum+1);
        euler(strt, ot, res);
        for (int i = res.size() - 1; i >= 0; i--)
        {
            out.print(res.get(i));
            out.print(' ');
        }
        
        out.println();
        
        out.flush();
    }

    // Ищем Эйлеров цикл в графе
    // https://ru.algorithmica.org/cs/graph-traversals/euler-cycle/
    public static void euler(int num, Set<Integer>[] ot, List<Integer> res)
    {
        while (!ot[num].isEmpty())
        {
            int nx = ot[num].iterator().next();
            ot[num].remove(nx);
            euler(nx, ot, res);
        }
        res.add(num);
    }
}
