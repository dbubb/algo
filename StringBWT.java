import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/*
* Преобразование Барроуза — Уилера (Burrows-Wheeler transform, BWT)
* https://neerc.ifmo.ru/wiki/index.php?title=%D0%9F%D1%80%D0%B5%D0%BE%D0%B1%D1%80%D0%B0%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%91%D0%B0%D1%80%D1%80%D0%BE%D1%83%D0%B7%D0%B0-%D0%A3%D0%B8%D0%BB%D0%B5%D1%80%D0%B0
*/
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
        String str = reader.readLine();
        
        int[] cnt = new int[53];
        int sl = str.length();
        int[] idx = new int[sl];
        for (int i = 0; i < sl; i++)
        {
            int indx = getIndex(str.charAt(i));
            idx[i] = cnt[indx];
            cnt[indx]++;
        }
        
        int sum = 0;
        for (int i = 0; i < 53; i++)
        {
            sum += cnt[i];
            cnt[i] = sum - cnt[i];
        }
        
        char[] res = new char[sl];
        int li = n-1;
        char ch = str.charAt(li);
        int indx = getIndex(ch);
        res[sl-1] = ch;
        for (int i = sl-2; i >= 0; i--)
        {
            li = cnt[indx] + idx[li];
            ch = str.charAt(li);
            indx = getIndex(ch);
            res[i] = ch;
        }
        out.println(res);

        out.flush();
    }
    
    public static int getIndex(char ch)
    {
        return (ch <= 'Z') ? (ch - 'A') : ((ch == '_') ? 26 : (ch - 'a' + 27));
    }
}
