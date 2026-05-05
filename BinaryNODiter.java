package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=2003
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
        
        Map<Integer, Integer> m = new HashMap<>(n * 4 / 3);
        boolean inf = false;
        for (int i = 0; i < n; i++)
        {
            int k = Integer.parseInt(reader.readLine());
            if (k > 1)
            {
                int nv = m.getOrDefault(k, 0) + 1;
                if (nv >= 3)
                {
                    inf = true;
                    break;
                }
                m.put(k, nv);
            }
            else
            {
                m.put(1, 1);
            }
        }
        
        if (inf)
        {
            out.println("infinity");
        }
        else if (m.size() == 1)
        {
            out.println(m.keySet().iterator().next() == 1 ? "0" : "infinity");
        }
        /*else if (s2.size() == 1)
        {
            out.println(s2.keySet().iterator().next() == 1 ? "1" : "infinity");
        }*/
        else
        {
            Set<Integer> s = new HashSet<>(m.size() * 3 / 2);
            
            for (Entry<Integer, Integer> en : m.entrySet())
            {
                if (en.getKey() > 1)
                {
                    if (en.getValue() > 1)
                    {
                        if (s.contains(en.getKey()))
                        {
                            inf = true;
                            break;
                        }
                        else
                            s.add(en.getKey());
                    }
                    for (Entry<Integer, Integer> en2 : m.entrySet())
                    {
                        if (en2.getKey().equals(en.getKey()))
                            break;
                        int nk = binaryNODiter(en.getKey(), en2.getKey());
                        if (nk == 1)
                            s.add(1);
                        else
                        {
                            if (s.contains(nk) || en.getValue() > 1 || en2.getValue() > 1)
                            {
                                inf = true;
                                break;
                            }
                            s.add(nk);
                        }
                    }
                    if (inf)
                        break;
                }
                else
                {
                    s.add(1);
                }
            }
            if (inf)
            {
                out.println("infinity");
            }
            else if (s.size() == 1)
            {
                out.println(s.iterator().next() == 1 ? "1" : "infinity");
            }
            else
            {
                for (Integer it : s)
                {
                    for (Integer it2 : s)
                    {
                        if (it.equals(it2))
                            break;
                        int nk = binaryNODiter(it, it2);
                        if (nk != 1)
                        {
                            inf = true;
                            break;
                        }
                    }
                    if (inf)
                        break;
                }
                out.println(inf ? "infinity" : "2");
            }
        }
        
        out.flush();
    }
    
    public static int binaryNODiter(int n1, int n2)
    {
        if (n1 == 0)
            return n2;
        if (n2 == 0)
            return n1;
        if (n1 == 1 || n2 == 1)
            return 1;
        if (n1 == n2)
            return n1;
        int nod = 1;
        int tmp;
        while (n1 != 0 && n2 != 0)
        {
            if (((n1 & 1) | (n2 & 1)) == 0)
            {
                nod <<= 1;
                n1 >>= 1;
                n2 >>= 1;
                continue;
            }
            if (((n1 & 1) == 0) && ((n2 & 1) != 0))
            {
                n1 >>= 1;
                continue;
            }
            if (((n1 & 1) != 0) && ((n2 & 1) == 0))
            {
                n2 >>= 1;
                continue;
            }
            if (n1 > n2)
            {
                tmp = n1;
                n1 = n2;
                n2 = tmp;
            }
            tmp = n1;
            n1 = (n2 - n1) >> 1;
            n2 = tmp;
        }
        return (n1 == 0) ? nod * n2 : nod * n1;
    }
}
