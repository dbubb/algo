import java.io.*;
import java.lang.*;
import java.util.*;

public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String[] parts = reader.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int k = Integer.parseInt(parts[1]);
        int m = Integer.parseInt(parts[2]);
        Set<Integer>[] e = new HashSet[n];
        for (int i = 0; i < k; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]) - 1;
            int n2 = Integer.parseInt(parts[1]) - 1;
            if (e[n1] == null)
                e[n1] = new HashSet<>();
            e[n1].add(n2);
            if (e[n2] == null)
                e[n2] = new HashSet<>();
            e[n2].add(n1);
        }
        
        int num = 0;
        int res[] = new int[n];
        for (int i = 0; i < n; i++)
        {
            if (res[i] != 0)
                continue;
            num++;
            res[i] = num;
            Queue<Integer> q = new ArrayDeque<>(n);
            q.add(i);
            while (!q.isEmpty())
            {
                int cur = q.remove();
                if (e[cur] != null)
                {
                    for (int next : e[cur])
                    {
                        if (res[next] == 0)
                        {
                            res[next] = num;
                            q.add(next);
                        }
                    }
                }
            }
        }

        out.println(num - 1);
        
        out.flush();
    }
}
