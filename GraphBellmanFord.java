import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

/** Алгоритм Беллмана-Форда */
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String[] parts = reader.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int[] d = new int[n+1];
        for (int i = 0; i <= n; i++)
            d[i] = -1; // Ищем максимум, а не минимум, поэтому инициализируем -1 а не бесконечностью
        int[] ef = new int[m];
        int[] et = new int[m];
        int[] ep = new int[m];
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            ef[i] = Integer.parseInt(parts[0]);
            et[i] = Integer.parseInt(parts[1]);
            ep[i] = Integer.parseInt(parts[2]);
        }
        parts = reader.readLine().split(" ");
        int from = Integer.parseInt(parts[0]);
        int to = Integer.parseInt(parts[1]);
        
        d[from] = 0;
        while (true) // в нашем случае циклов точно нет, можно не ограничиваться колиеством вершин
        {
            int cnt = 0;
            for (int i = 0; i < m; i++)
            {
                if (d[ef[i]] != -1)
                {
                    int p = d[ef[i]] + ep[i];
                    if (d[et[i]] < p) // ищем максимум а не минимум
                    {
                        d[et[i]] = p;
                        cnt++;
                    }
                }
            }
            if (cnt == 0)
                break;
        }
        if (d[to] == -1)
            out.println("No solution");
        else
            out.println(d[to]);

        out.flush();
   }
}
