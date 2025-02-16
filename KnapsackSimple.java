import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Задача о рюкзаке, простой вариант (без стоимостей)
 * https://algorithmica.org/tg/knapsack-gis-gcs
 * https://neerc.ifmo.ru/wiki/index.php?title=%D0%97%D0%B0%D0%B4%D0%B0%D1%87%D0%B0_%D0%BE_%D1%80%D1%8E%D0%BA%D0%B7%D0%B0%D0%BA%D0%B5
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int uw = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int n = Integer.parseInt(reader.readLine());
        int[] w = new int[n+1];
        boolean[][] dp = new boolean[n+1][uw+1];
        //Map<Integer, Integer> dp = new HashMap<>(10000);
        //dp.put(0, 0);
        dp[0][0] = true;

        for (int i = 1; i <= n; i++)
        {
            w[i] = Integer.parseInt(reader.readLine());
            for (int j = 0; j <= uw; j++)
            {
                dp[i][j] = dp[i - 1][j];
                if (w[i] <= j && dp[i - 1][j - w[i]])
                    dp[i][j] = true;
            }
            //System.out.println(dp);
        }
        /*for (int i = 0; i <= n; i++)
        {
            for (int j = 0; j <= uw; j++)
                System.out.print(dp[i][j] ? '1' : '0');
            System.out.println("");
        }*/
        
        if (dp[n][uw])
        {
            Set<Integer> e = new HashSet<>();
            int i = n;
            int j = uw;
            boolean pl = false;
            while (i > 0)
            {
                if (w[i] <= j && dp[i - 1][j - w[i]])
                {
                    if (dp[i - 1][j])
                    {
                        pl = true;
                        break;
                    }
                    e.add(i);
                    //System.out.println("yes " + i + " : " + (j - w[i]) + (dp[i - 1][j] ? " + and yes" : " + and no"));
                    j -= w[i];
                }
                /*else
                {
                    System.out.println("no " + i + " : " + (j - w[i]));
                }*/
                i--;
            }
            if (pl)
                out.println("-1");
            else
            {
                boolean printed = false;
                for (i = 1; i <= n; i++)
                {
                    if (!e.contains(i))
                    {
                        if (printed)
                            out.print(" ");
                        out.print(i);
                        printed = true;
                    }
                }
                out.println("");
            }
       }
        else
            out.println("0");
        
        out.flush();
    }
}
