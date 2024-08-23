import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Быстрый расчет чисел Фибоначчи
 * https://www.nayuki.io/page/fast-fibonacci-algorithms
 */
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int n1 = Integer.parseInt(parts[0]);
        int fn1 = Integer.parseInt(parts[1]);
        int n2 = Integer.parseInt(parts[2]);
        int fn2 = Integer.parseInt(parts[3]);
        int n = Integer.parseInt(parts[4]);
        
        if (n == n1)
        {
            out.println(fn1);
        }
        else if (n == n2)
        {
            out.println(fn2);
        }
        else
        {
            if (n2 < n1)
            {
                int t = n1;
                n1 = n2;
                n2 = t;
                t = fn1;
                fn1 = fn2;
                fn2 = t;
            }
            n2 -= n1;
            n -= n1;
            
            BigInteger fbn2 = fastFibonacciDoubling(n2);
            BigInteger fbn21 = fastFibonacciDoubling(n2-1);
            BigInteger f0 = BigInteger.valueOf(fn1);
            BigInteger f1 = BigInteger.valueOf(fn2).subtract(fbn21.multiply(f0)).divide(fbn2);
            if (n == 1)
            {
                out.println(f1.toString());
            }
            else if (n > 1)
            {
                BigInteger fbn = fastFibonacciDoubling(n);
                BigInteger fbn1 = fastFibonacciDoubling(n-1);
                BigInteger fn = fbn.multiply(f1).add(fbn1.multiply(f0));
                out.println(fn.toString());
            }
            else
            {
                n = -n;
                BigInteger fbn = fastFibonacciDoubling(n);
                BigInteger fbn1 = fastFibonacciDoubling(n+1);
                BigInteger fn = (n % 2 == 0) ? fbn1.multiply(f0).subtract(fbn.multiply(f1)) : fbn.multiply(f1).subtract(fbn1.multiply(f0));
                out.println(fn.toString());
            }
        }

        out.flush();
    }
    
  /**
	 * Fast doubling method. Faster than the matrix method.
	 * F(2n) = F(n) * (2*F(n+1) - F(n)).
	 * F(2n+1) = F(n+1)^2 + F(n)^2.
	 * This implementation is the non-recursive version. See the web page and
	 * the other programming language implementations for the recursive version.
	 */
	private static BigInteger fastFibonacciDoubling(int n)
	{
		BigInteger a = BigInteger.ZERO;
		BigInteger b = BigInteger.ONE;
		int m = 0;
		for (int bit = Integer.highestOneBit(n); bit != 0; bit >>>= 1)
		{
			// Double it
			BigInteger d = a.multiply(b.shiftLeft(1).subtract(a));
			BigInteger e = a.multiply(a).add(b.multiply(b));
			a = d;
			b = e;
			m *= 2;

			// Advance by one conditionally
			if ((n & bit) != 0) {
				BigInteger c = a.add(b);
				a = b;
				b = c;
				m++;
			}
		}
		return a;
	}
}
