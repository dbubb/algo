package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1176
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
        int a = Integer.parseInt(parts[1]);
        
        boolean[][] m = new boolean[n+1][n+1];
        int zc = 0;
        for (int i = 1; i <= n; i++)
        {
            char[] row = reader.readLine().toCharArray();
            int idx = 1;
            for (char ch : row)
                if (ch == '0' || ch == '1')
                {
                    if (idx != i && ch == '0')
                        zc++;
                    m[i][idx++] = (ch == '1');
                }
        }
        
        // Ищем Эйлеров цикл в графе
        List<Integer> res = new ArrayList<>(zc+1);
        euler(a, m, n, res);
        
        for (int i = res.size() - 1; i > 0; i--)
            out.println(res.get(i) + " " + res.get(i-1));
        
        out.flush();
    }

    // Ищем Эйлеров цикл в графе заданном матрицей переходов
    // https://ru.algorithmica.org/cs/graph-traversals/euler-cycle/
    public static void euler(int num, boolean[][] m, int n, List<Integer> res)
    {
        for (int i = 1; i <= n; i++)
        {
            if (i == num || m[num][i])
                continue;
            m[num][i] = true;
            euler(i, m, n, res);
        }
        res.add(num);
    }
}
