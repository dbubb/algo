import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/*
* Суффиксный массив
* https://neerc.ifmo.ru/wiki/index.php?title=%D0%A1%D1%83%D1%84%D1%84%D0%B8%D0%BA%D1%81%D0%BD%D1%8B%D0%B9_%D0%BC%D0%B0%D1%81%D1%81%D0%B8%D0%B2
*/
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String str = reader.readLine();
        int sl = str.length();
        int[] sa = getSuffixArray(str);
        
        int res = sa[0] + 1;
        for(int i = 1; i < sl; i++)
        {
            res += (sa[i] + 1);
            res -= lcp(str, sa[i-1], sa[i]);
        }

        out.println(res);

        out.flush();
    }
    
    public static int[] getSuffixArray(String str)
    {
        int sl = str.length();
        int[] res = IntStream.range(0, sl).boxed().sorted((Integer n1, Integer n2) ->
        {
            int i1 = n1, i2 = n2;
            while (true)
            {
                if (i1 >= sl)
                    return -1;
                if (i2 >= sl)
                    return 1;
                char ch1 = str.charAt(i1);
                char ch2 = str.charAt(i2);
                if (ch1 != ch2)
                    return ch1 - ch2;
                i1++;
                i2++;
            }
        }).mapToInt(i -> i).toArray();
        return res;
    }
    
    public static int lcp(String str, int i1, int i2)
    {
        int res = 0;
        int sl = str.length();
        while (i1 < sl && i2 < sl && str.charAt(i1) == str.charAt(i2))
        {
            res++;
            i1++;
            i2++;
        }
        return res;
    }
}
