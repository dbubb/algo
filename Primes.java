import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      int fr = in.nextInt();
      int to = in.nextInt();
      
      PrintWriter out = new PrintWriter(System.out);
      if (fr == 1)
      {
          out.println(1);
      }
      else if (fr == to)
      {
          out.println(fr);
      }
      else
      {
          boolean prime[] = getPrimes(1000000);
          
          double minTriv = Double.MAX_VALUE;
          int n = to;
          for (int i = to; i >= fr; i--)
          {
              if (prime[i])
              {
                  n = i;
                  break;
              }
              double triv = getTriv(i);
              if (triv < minTriv)
              {
                  minTriv = triv;
                  n = i;
              }
              //out.print(i);
              //out.print(" - ");
              //out.println(triv);
          }

          out.println(n);
      }

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
   
   public static double getTriv(int val)
   {
       int d = 2;
       int n = val;
       double res = 1.0;
       while (n > 1)
       {
           long p = d;
           while (n % d == 0)
           {
               n /= d;
               p *= d;
           }
           res *= (double)(p-1)/(d-1);
           d++;
       }
       return (res - val)/val;
   }
}
