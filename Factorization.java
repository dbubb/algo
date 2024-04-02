import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      int n = in.nextInt();

      PrintWriter out = new PrintWriter(System.out);
      BigInteger m = BigInteger.ONE;
      int k = 1;
      int pr = 1;
      final int MPR = 100000;
      boolean prime[] = getPrimes(MPR);
      while (n > 1)
      {
          do
          {
             pr++;
             if (pr > MPR)
                break;
          } while (!prime[pr]);
          if (pr > MPR)
          {
              m = m.multiply(BigInteger.valueOf(n).pow(2));
              k *= n;
              break;
          }
          int d = 0;
          while (n % pr == 0)
          {
              n /= pr;
              d++;
          }
          //out.println("pr = " + pr + "; d = " + d);
          if (d > 0)
          {
              if (d % 2 == 0)
              {
                  m = m.multiply(BigInteger.valueOf(pr).pow(d/2));
              }
              else
              {
                  m = m.multiply(BigInteger.valueOf(pr).pow((d+3)/2));
                  k *= pr;
              }
          }
      }
      
      
      out.println(m.toString());
      out.println(k);

      out.flush();
   }
   
   public static boolean[] getPrimes(int n)
   {
       boolean prime[] = new boolean[n + 1];
       Arrays.fill(prime, true);
       for (int p = 2; p * p <= n; p++)
       {
           if (prime[p])
           {
               for (int i = p * 2; i <= n; i += p)
                   prime[i] = false;
           }
       }
       return prime;
   }   
}
