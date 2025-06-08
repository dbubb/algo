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
        //int[][] dvd = new int[n+1][181];
        ArrayList<Integer>[] dvd = new ArrayList[n+1];
        for (int i = 1; i <= n; i++)
        {
            //dvd[i][0] = 1;
            //dvd[i][1] = 1;
            dvd[i] = new ArrayList<Integer>();
            dvd[i].add(1);
        }
        for (int i = 2; i <= n; i++)
        {
            int idx = i;
            while (idx <= n)
            {
                //dvd[idx][0]++;
                //dvd[idx][dvd[idx][0]] = i;
                dvd[idx].add(i);
                idx += i;
            }
        }
        //for (int i = 1; i <= n; i++)
        //    out.println(i + ": " + dvd[i][0]);
        // precalc
        
        int q = Integer.parseInt(reader.readLine());
        //long[] v = new long[n+1];
        SegmentsTree st = new SegmentsTree(n+1);
        for (int i = 0; i < q; i++)
        {
            parts = reader.readLine().split(" ");
            if ("1".equals(parts[0]))
            {
                int idx = Integer.parseInt(parts[1]);
                long res = a[idx];
                //for (int j = 1; j <= dvd[idx][0]; j++)
                for (int j = 0; j < dvd[idx].size(); j++)
                    //res += v[dvd[idx][j]];
                    res += st.get(dvd[idx].get(j));
                out.println(res);
            }
            else
            {
                int l = Integer.parseInt(parts[1]);
                int r = Integer.parseInt(parts[2]);
                int d = Integer.parseInt(parts[3]);
                st.update(l, r, d);
                //for (int j = l; j <= r; j++)
                //    v[j] += d;
                /*for (int j = r; j >= l; j--)
                {
                    int idx = j;
                    while (idx <= n)
                    {
                        a[idx] += d;
                        idx += j;
                    }
                }*/
            }
        }

        out.flush();
        
        // см. https://codeforces.com/blog/entry/12528
    }
    
    /**
     * Дерево отрезков с обновлением интервала и получением значения в конкретном элементе
     * https://cp-algorithms.com/data_structures/segment_tree.html
     * (раздел "Range updates (Lazy Propagation)")
     */
    public static class SegmentsTree
    {
        public long[] arr;
        public int n;
        
        public SegmentsTree(int sz)
        {
            n = closestDeg2(sz);
            arr = new long[2 * n];
        }
        
        public void update(int l, int r, int val)
        {
            update(1, 0, n-1, l, r, val);
        }
        
        public void update(int v, int tl, int tr, int l, int r, int val)
        {
            if (l > r)
                return;
            if (l == tl && r == tr)
                arr[v] += val;
            else
            {
                int tm = (tl + tr) / 2;
                update(v*2, tl, tm, l, Math.min(r,tm), val);
                update(v*2+1, tm+1, tr, Math.max(l,tm+1), r, val);
            }
        }
        
        public long get(int pos)
        {
            return get(1, 0, n-1, pos);
        }
        
        public long get(int v, int tl, int tr, int pos)
        {
            if (tl == tr)
                return arr[v];
            int tm = (tl + tr) / 2;
            if (pos <= tm)
                return arr[v] + get(v*2, tl, tm, pos);
            else
                return arr[v] + get(v*2+1, tm+1, tr, pos);
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
