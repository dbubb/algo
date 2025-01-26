import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Суффиксный автомат
 * http://e-maxx.ru/algo/suffix_automata
 * Наидлиннейшая общая подстрока двух строк (LCS) через суффиксный автомат (в том же файле)
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();

        out.println(lcs(reader.readLine(), reader.readLine()));

        out.flush();
    }
    
  /** Наидлиннейшая общая подстрока двух строк */  
  public static String lcs(String str1, String str2)
    {
        // формируем суффиксный автомат для первой строки
        SuffixAutomaton sa = new SuffixAutomaton(str1);
        
        int v = 0;
        int len = 0;
        int maxlen = 0;
        int pos = 0;

        // идем по второй строке, для каждого префикса ищем наидлиннейший суффикс в sa
        for (int i = 0; i < str2.length(); i++)
        {
            int ch = str2.charAt(i) - 'A';
            while (v != 0 && sa.gr[v][ch] == 0)
            {
                v = sa.ref[v];
                len = sa.len[v];
            }
            if (sa.gr[v][ch] != 0)
            {
                v = sa.gr[v][ch];
                len++;
            }
            if (len > maxlen)
            {
                maxlen = len;
                pos = i;
            }
        }
        return str2.substring(pos-maxlen+1,pos+1);
    }
    
  /** Суффиксный автомат */  
  public static class SuffixAutomaton
    {
        public int[] len;
        public int[] ref;
        public int[][] gr;
        
        public SuffixAutomaton(String str)
        {
            int l = str.length();
            
            len = new int[l * 2];
            ref = new int[l * 2];
            gr  = new int[l * 2][26];
            
            int sz = 1;
            int prev = 0;
            int cur;
            len[0] = 0;
            ref[0] = -1;
            //debugPrint(this, 2);
            
            for (int i = 0; i < l; i++)
            {
                int ch = str.charAt(i) - 'A';
                cur = sz++;
                //System.out.println("step " + i + "; ch=" + ch + "; cur=" + cur);
                len[cur] = len[prev] + 1;
                int p;
                for (p = prev; p != -1 && gr[p][ch] == 0; p = ref[p])
                    gr[p][ch] = cur;
                if (p == -1)
                    ref[cur] = 0;
                else
                {
                    int q = gr[p][ch];
                    if (len[p] + 1 == len[q])
                        ref[cur] = q;
                    else
                    {
                        int clone = sz++;
                        len[clone] = len[p] + 1;
                        for (int j = 0; j < 26; j++)
                            gr[clone][j] = gr[q][j];
                        ref[clone] = ref[q];
                        for ( ; p != -1 && gr[p][ch] == q; p = ref[p])
                            gr[p][ch] = clone;
                        ref[q] = clone;
                        ref[cur] = clone;
                    }
                }
                prev = cur;
                //debugPrint(this, 7);
            }
        }
    }
    
    /*public static void debugPrint(SuffixAutomaton sa, int len)
    {
        for (int i = 0; i < len; i++)
            System.out.println(sa.len[i] + " " + sa.ref[i] + " " + sa.gr[i][0]
                    + "-" + sa.gr[i][1]+ "-" + sa.gr[i][2]+ "-" + sa.gr[i][3]);

    }*/
}
