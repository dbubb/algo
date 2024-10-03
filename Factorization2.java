import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Факторизация чисел (до 10000)
 * https://acm.timus.ru/problem.aspx?space=1&num=1049
 */
public class Main
{
    public static int[] primes = new int[]
        {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};
        
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();

        Map<Integer,Integer> m = new HashMap<>(40);
        for (int i = 0; i < 10; i++)
        {
            int a = Integer.parseInt(reader.readLine());
            for (int p : primes)
            {
                while (a % p == 0)
                {
                    a /= p;
                    m.put(p, m.getOrDefault(p, 0) + 1);
                }
                if (a == 1)
                    break;
            }
            if (a > 1)
                m.put(a, m.getOrDefault(a, 0) + 1);
        }
        long res = 1;
        for (int d : m.values())
            res *= (d + 1);

        out.println(res % 10);

        out.flush();
    }
}
