import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/** Умножение и возведение в степень матриц 2*2
 *  взято отсюда: https://www.nayuki.io/page/fast-fibonacci-algorithms
 */
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //String[] parts = reader.readLine().split(" ");
        long n = Long.parseLong(reader.readLine());
        long k = Long.parseLong(reader.readLine());
        long m = Long.parseLong(reader.readLine());
        
        BigInteger k1 = BigInteger.valueOf(k-1);
        BigInteger mm = BigInteger.valueOf(m);
        if (n == 2)
        {
            out.println(BigInteger.valueOf(k).multiply(k1).remainder(mm).toString());
        }
        else
        {
            BigInteger[] mat0 = { BigInteger.valueOf(k).multiply(k1), k1,
                                  k1,                                 BigInteger.ONE };
            BigInteger[] mat1 = { k1,             k1,
                                  BigInteger.ONE, BigInteger.ZERO };
            BigInteger[] matn = (n > 3) ? matrixPow(mat1, n-2, mm) : mat1;
            BigInteger[] res = matrixMultiply(matn, mat0, mm);
            out.println(res[0].toString());
        }

        out.flush();
    }
    
	/** возведение в степень за логарифмическое время */
  private static BigInteger[] matrixPow(BigInteger[] matrix, long n, BigInteger mm)
	{
		BigInteger[] result = { BigInteger.ONE,  BigInteger.ZERO,
		                        BigInteger.ZERO, BigInteger.ONE };
		while (n != 0)
		{
			if (n % 2 != 0)
				result = matrixMultiply(result, matrix, mm);
			n /= 2;
			matrix = matrixMultiply(matrix, matrix, mm);
		}
		return result;
	}
	
	/** умножение матриц, результат по модулю mm */
	private static BigInteger[] matrixMultiply(BigInteger[] x, BigInteger[] y, BigInteger mm)
	{
		return new BigInteger[]
        		{
        			x[0].multiply(y[0]).add(x[1].multiply(y[2])).remainder(mm), x[0].multiply(y[1]).add(x[1].multiply(y[3])).remainder(mm),
        			x[2].multiply(y[0]).add(x[3].multiply(y[2])).remainder(mm), x[2].multiply(y[1]).add(x[3].multiply(y[3])).remainder(mm)
        		};
	}}
