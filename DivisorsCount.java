import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Определяем числа с максимальным кол-вом делеителей до 10^18
 * (закомментированный код), результат помещаем в массивы dcnt и ncnt
 * Нужно для задачи https://acm.timus.ru/problem.aspx?space=1&num=1748
 */
public class Main
{
    // Простые числа 100 штук первых
    /*public static int[] primes = new int[]{
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
    public static final Map<Integer, Long> MAP = new HashMap<>(1500);*/
    
    public static final int[] dcnt =
    { 1, 2, 3, 4, 6, 8, 9, 10, 12, 16, 18,
      20, 24, 30, 32, 36, 40, 48, 60, 64, 72,
      80, 84, 90, 96, 100, 108, 120, 128, 144, 160,
      168, 180, 192, 200, 216, 224, 240, 256, 288, 320,
      336, 360, 384, 400, 432, 448, 480, 504, 512, 576,
      600, 640, 672, 720, 768, 800, 864, 896, 960, 1008,
      1024, 1152, 1200, 1280, 1344, 1440, 1536, 1600, 1680, 1728,
      1792, 1920, 2016, 2048, 2304, 2400, 2688, 2880, 3072, 3360,
      3456, 3584, 3600, 3840, 4032, 4096, 4320, 4608, 4800, 5040,
      5376, 5760, 6144, 6720, 6912, 7168, 7200, 7680, 8064, 8192,
      8640, 9216, 10080, 10368, 10752, 11520, 12288, 12960, 13440,
      13824, 14336, 14400, 15360, 16128, 16384, 17280, 18432, 20160, 20736,
      21504, 23040, 24576, 25920, 26880, 27648, 28672, 28800, 30720, 32256,
      32768, 34560, 36864, 40320, 41472, 43008, 46080, 48384, 49152, 51840,
      53760, 55296, 57600, 61440, 62208, 64512, 65536, 69120, 73728, 80640,
      82944, 86016, 92160, 96768, 98304, 103680
    };
    public static final long[] ncnt =
    { 1L, 2L, 4L, 6L, 12L, 24L, 36L, 48L, 60L, 120L, 180L,
      240L, 360L, 720L, 840L, 1260L, 1680L, 2520L, 5040L, 7560L, 10080L,
      15120L, 20160L, 25200L, 27720L, 45360L, 50400L, 55440L, 83160L, 110880L, 166320L,
      221760L, 277200L, 332640L, 498960L, 554400L, 665280L, 720720L, 1081080L, 1441440L, 2162160L,
      2882880L, 3603600L, 4324320L, 6486480L, 7207200L, 8648640L, 10810800L, 14414400L, 17297280L, 21621600L,
      32432400L, 36756720L, 43243200L, 61261200L, 73513440L, 110270160L, 122522400L, 147026880L, 183783600L, 245044800L,
      294053760L, 367567200L, 551350800L, 698377680L, 735134400L, 1102701600L, 1396755360L, 2095133040L, 2205403200L, 2327925600L,
      2793510720L, 3491888400L, 4655851200L, 5587021440L, 6983776800L,
      10475665200L, 13967553600L, 20951330400L, 27935107200L, 41902660800L,
      48886437600L, 64250746560L, 73329656400L, 80313433200L, 97772875200L,
      128501493120L, 146659312800L, 160626866400L, 240940299600L, 293318625600L,
      321253732800L, 481880599200L, 642507465600L, 963761198400L, 1124388064800L,
      1606268664000L, 1686582097200L, 1927522396800L, 2248776129600L, 3212537328000L,
      3373164194400L, 4497552259200L, 6746328388800L, 8995104518400L, 9316358251200L,
      13492656777600L, 18632716502400L, 26985313555200L, 27949074753600L, 32607253879200L,
      46581791256000L, 48910880818800L, 55898149507200L, 65214507758400L, 93163582512000L,
      97821761637600L, 130429015516800L, 195643523275200L, 260858031033600L, 288807105787200L,
      391287046550400L, 577614211574400L, 782574093100800L, 866421317361600L, 1010824870255200L,
      1444035528936000L, 1516237305382800L, 1732842634723200L, 2021649740510400L, 2888071057872000L,
      3032474610765600L, 4043299481020800L, 6064949221531200L, 8086598962041600L, 10108248702552000L,
      12129898443062400L, 18194847664593600L, 20216497405104000L, 24259796886124800L, 30324746107656000L,
      36389695329187200L, 48519593772249600L, 60649492215312000L, 72779390658374400L, 74801040398884800L,
      106858629141264000L, 112201560598327200L, 149602080797769600L, 224403121196654400L, 299204161595539200L,
      374005201994424000L, 448806242393308800L, 673209363589963200L, 748010403988848000L, 897612484786617600L
    };

    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int t = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();

        /*int[] p = new int[MX];
        for (int i = 1; i <= MX; i++)
        {
            //System.out.print("M cnt = " + i);
            Arrays.fill(p, 0);
            p[0] = i;
            int cnt = 0;
            do
            {
                cnt++;
                process(p);
                //if (process(p))
                //    break;
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
        
        int[] del = new int[160];
        long[] num = new long[160];
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
                    System.out.println(cur + ": " + e.getValue() + " - " + e.getKey());
                    del[cur] = e.getKey();
                    num[cur] = e.getValue();
                    counter.incrementAndGet();
                }
            });
        int cur = counter.get();
        for (int i = 0; i < cur; i++)
            System.out.print(del[i] + ", ");
        System.out.println("");
        for (int i = 0; i < cur; i++)
            System.out.print(num[i] + "L, ");
        System.out.println("");*/
        
        for (int i = 0; i < t; i++)
        {
            long n = Long.parseLong(reader.readLine());
            
            int low = 0;
            int hgh = ncnt.length;
            while (low < hgh-1)
            {
                int mid = low  + ((hgh - low) / 2);
                if (ncnt[mid] > n)
                {
                    hgh = mid;
                }
                else
                {
                    low = mid;
                }
            }
            out.println(ncnt[low] + " " + dcnt[low]);
        }

        out.flush();
    }
    
    /*public static boolean process(int[] p)
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
                //return true;
                continue;
            //System.out.print(p[i] + " ");
        }
        //System.out.println("| del = " + d + "; num = " + n);
        if (num.compareTo(MAX_NUM) > 0)
            return true;
        long newVal = num.longValue();
        Long cur = MAP.get(d);
        if (cur == null || cur > newVal)
        {
            MAP.put(d, newVal);
        }
        return false;
    }*/
}
