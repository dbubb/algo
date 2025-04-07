import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Определяем первые числа с максимальным кол-вом делеителей до 10^18
 * Нужно для задачи https://acm.timus.ru/problem.aspx?space=1&num=1748
 * (но сама задача здесь не решается)
 */
public class Main
{
    // Простые числа 100 штук первых
    public static int[] primes = new int[]{
        2,3,5,7,11,13,17,19,23,29,
        31,37,41,43,47,53,59,61,67,71,
        73,79,83,89,97,101,103,107,109,113,
        127,131,137,139,149,151,157,163,167,173,
        179,181,191,193,197,199,211,223,227,229,
        233,239,241,251,257,263,269,271,277,281,
        283,293,307,311,313,317,331,337,347,349,
        353,359,367,373,379,383,389,397,401,409,
        419,421,431,433,439,443,449,457,461,463,
        467,479,487,491,499,503,509,521,523,541
    };
    public static final int MX = 60;
    public static final BigInteger MAX_NUM = BigInteger.valueOf(1_000_000_000_000_000_000L);
    public static final Map<Integer, Long> MAP = new HashMap<>(1500);
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();

        int[] p = new int[MX];
        for (int i = 1; i <= MX; i++)
        {
            //System.out.print("M cnt = " + i);
            Arrays.fill(p, 0);
            p[0] = i;
            int cnt = 0;
            do
            {
                cnt++;
                if (process(p))
                    break;
                if (p[0] == 1)
                    break;
                int cur = MX-1;
                int sum = 0;
                while (p[cur] < 2)
                {
                    sum += p[cur];
                    cur--;
                }
                p[cur]--;
                sum++;
                int curMax = p[cur];
                for (int j = cur+1; j < MX; j++)
                {
                    if (sum < curMax)
                    {
                        p[j] = sum;
                        sum = 0;
                    }
                    else
                    {
                        p[j] = curMax;
                        sum -= curMax;
                    }
                }
            }
            while (true);
            //System.out.println("; cnt = " + cnt + "; total = " + MAP.size());
        }
        
        int[] del = new int[140];
        long[] num = new long[140];
        final AtomicInteger counter = new AtomicInteger(0);
        MAP.entrySet().stream()
            .sorted(
                (e1, e2) -> (e1.getValue().equals(e2.getValue()))
                    ? e1.getKey().compareTo(e2.getKey())
                    : e1.getValue().compareTo(e2.getValue()))
            .forEach(e -> {
                int cur = counter.get();
                if (cur == 0 || del[cur-1] < e.getKey())
                {
                    del[cur] = e.getKey();
                    num[cur] = e.getValue();
                    //System.out.println(cur + ": " + num[cur] + " - " + del[cur]);
                    counter.incrementAndGet();
                }
            });
        int cur = counter.get();
        for (int i = 0; i < cur; i++)
            System.out.print(del[i] + ", ");
        System.out.println("");
        for (int i = 0; i < cur; i++)
            System.out.print(num[i] + ", ");
        System.out.println("");
        out.println("Yes");

        out.flush();
    }
    
    public static boolean process(int[] p)
    {
        int d = 1;
        BigInteger num = BigInteger.ONE;
        for (int i = 0; i < MX; i++)
        {
            if (p[i] == 0)
                break;
            d *= (p[i] + 1);
            num = num.multiply(BigInteger.valueOf(primes[i]).pow(p[i]));
            if (num.compareTo(MAX_NUM) > 0)
                return true;
            //System.out.print(p[i] + " ");
        }
        //System.out.println("| del = " + d + "; num = " + n);
        long newVal = num.longValue();
        Long cur = MAP.get(d);
        if (cur == null || cur > newVal)
        {
            MAP.put(d, newVal);
        }
        return false;
    }
}
