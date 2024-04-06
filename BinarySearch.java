import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String[] parts = reader.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        Set<Edge>[] e = new HashSet[n+1];
        Set<Integer> lens = new HashSet<>();
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            int len = Integer.parseInt(parts[2]);
            if (e[n1] == null)
                e[n1] = new HashSet<>();
            e[n1].add(new Edge(n2, len));
            if (e[n2] == null)
                e[n2] = new HashSet<>();
            e[n2].add(new Edge(n1, len));
            lens.add(len);
        }
        
        List<Integer> ls = lens.stream().sorted().collect(Collectors.toList());
        //out.println(ls.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        int low = 0;
        int hgh = ls.size() - 1;
        while (low <= hgh)
        {
            int mid = low  + ((hgh - low) / 2);
            boolean visited[] = new boolean[n+1];
            visited[1] = true;
            int cnt = 1;
            Queue<Integer> q = new ArrayDeque<>(n);
            q.add(1);
            int maxlen = ls.get(mid);
            while (!q.isEmpty())
            {
                int cur = q.remove();
                if (e[cur] != null)
                {
                    for (Edge ed : e[cur])
                    {
                        if (visited[ed.n] || ed.len > maxlen)
                            continue;
                        visited[ed.n] = true;
                        cnt++;
                        q.add(ed.n);
                    }
                }
            }
            //out.println(mid + " - " + maxlen + " - " + cnt);
            if (cnt == n)
            {
                hgh = mid - 1;
            }
            else
            {
                low = mid + 1;
            }
        }
        
        int res = ls.get(low);
        out.println(res);
        
        List<String> rl = new ArrayList<String>();
        for (int i = 1; i <= n; i++)
        {
            if (e[i] == null)
                continue;
            for (Edge ed : e[i])
            {
                if (ed.n > i && ed.len <= res)
                    rl.add(i + " " + ed.n);
            }
        }
        out.println(rl.size());
        for (String line : rl)
            out.println(line);
        
        out.flush();
   }
   
   public static class Edge
   {
       int n;
       int len;
       
       public Edge(int n, int len)
       {
           this.n = n;
           this.len = len;
       }
   }
}
