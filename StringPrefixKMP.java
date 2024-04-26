import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

// Префикс-функция. Алгоритм Кнута-Морриса-Пратта
// https://brestprog.by/topics/prefixfunction/
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
      
        final int LC = 2; // количество символов для перевода строки
        char[] buf = new char[n * 3 + LC];
        int cnt = reader.read(buf, 0, n * 2 + LC);
        System.arraycopy(buf, n + LC, buf, n * 2 + LC, n);
        //out.println(buf);

        int[] pr = prefix(buf);
        
        int res = -1;
        for (int i = 0; i < n * 3 + LC; i++)
            if (pr[i] == n)
            {
                res = i - n * 2 - LC + 1;
                //out.println(i + " " + shift + " " + res);
                break;
            }
        out.println(res);

        out.flush();
    }

    /** Префикс-функция */
    public static int[] prefix(char[] str)
    {
        int[] pr = new int[str.length];
        for (int i = 1; i < str.length; i++)
        {
            int j = pr[i-1];
            while (j > 0 && str[i] != str[j])
                j = pr[j-1];
            pr[i] = (str[i] == str[j]) ? j+1 : j;
        }
        return pr;
    }
}
