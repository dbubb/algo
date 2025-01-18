import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Алгоритм Куна нахождения наибольшего паросочетания в двудольном графе
 * http://e-maxx.ru/algo/kuhn_matching
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();

        int m = Integer.parseInt(parts[0]);
        int n = Integer.parseInt(parts[1]);
        int k = Integer.parseInt(parts[2]);
        
        List<Integer>[] em = new ArrayList[m];
        List<Integer>[] en = new ArrayList[n];

        for (int i = 0; i < k; i++)
        {
            parts = reader.readLine().split(" ");
            int edmi = Integer.parseInt(parts[0]) - 1;
            int edni = Integer.parseInt(parts[1]) - 1;
            if (em[edmi] == null)
                em[edmi] = new ArrayList<>();
            em[edmi].add(edni);
            if (en[edni] == null)
                en[edni] = new ArrayList<>();
            en[edni].add(edmi);
        }

        int[] mt;
        if (n < m)
        {
            mt = new int[m];
            Arrays.fill(mt, -1);
            boolean[] used = new boolean[n];
            for (int i = 0; i < n; i++)
            {
                Arrays.fill(used, false);
                kuhn(i, used, en, mt);
            }
        }
        else
        {
            mt = new int[n];
            Arrays.fill(mt, -1);
            boolean[] used = new boolean[m];
            for (int i = 0; i < m; i++)
            {
                Arrays.fill(used, false);
                kuhn(i, used, em, mt);
            }
        }
        int res = 0;
        for (int i = 0; i < mt.length; i++)
            if (mt[i] != -1)
                res++;

        out.println(m + n - res);

        out.flush();
    }
    
    public static boolean kuhn(int node, boolean[] used, List<Integer>[] edges, int[] mt)
    {
        if (used[node])
            return false;
        used[node] = true;
        for (int i = 0; i < edges[node].size(); i++)
        {
            int to = edges[node].get(i);
            if (mt[to] == -1 || kuhn(mt[to], used, edges, mt))
            {
                mt[to] = node;
                return true;
            }
        }
        return false;
    }
}
