package ru.dbubb;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Задача - https://acm.timus.ru/problem.aspx?space=1&num=1326
 */
public class ExpApp
{
    public static int n;
    public static int m;
    public static int[] kpr;
    public static int[] grpr;
    public static int[] grmsk;
    public static int trsz;
    public static int trmsk;
    public static int[] trn;
    public static int[] trm;
    public static int msum;
    public static int sz;
    public static int[] dp;
    public static int[] np;
    public static int[] npb;
    public static int npsz;
    public static int npbsz;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        kpr = new int[n+1];
        for (int i = 1; i <= n; i++)
            kpr[i] = Integer.parseInt(reader.readLine());
        
        m = Integer.parseInt(reader.readLine());
        grpr = new int[m];
        grmsk = new int[m];
        for (int i = 0; i < m; i++)
        {
            String[] parts = reader.readLine().split(" ");
            grpr[i] = Integer.parseInt(parts[0]);
            int grsz = Integer.parseInt(parts[1]);
            for (int j = 0; j < grsz; j++)
            {
                int kn = Integer.parseInt(parts[j+2]);
                grmsk[i] |= (1 << (kn-1));
            }
        }
        
        String[] parts = reader.readLine().split(" ");
        trsz = Integer.parseInt(parts[0]);
        trmsk = 0;
        trn = new int[trsz];
        trm = new int[trsz];
        msum = 0;
        for (int j = 0; j < trsz; j++)
        {
            trn[j] = Integer.parseInt(parts[j+1]);
            msum += kpr[trn[j]];
            trm[j] = (1 << (trn[j]-1));
            trmsk |= trm[j];
        }
        
        sz = (1<<n);
        dp = new int[sz];
        np = new int[sz];
        npb = new int[sz];
        npsz = 0;
        npbsz = 0;
        for (int i = 0; i < m; i++)
        {
            if (dp[grmsk[i]] == 0 || dp[grmsk[i]] > grpr[i])
            {
                dp[grmsk[i]] = grpr[i];
                npb[npbsz++] = grmsk[i];
                checkPrice(grmsk[i]);
            }
        }
        
        while (npbsz > 0)
        {
            for (int i = 0; i < npbsz; i++)
            {
                int curmsk = npb[i];
                for (int j = 0; j < m; j++)
                {
                    int nmsk = (grmsk[j] | curmsk);
                    if (nmsk == curmsk)
                        continue;
                    int nprgr = dp[curmsk] + grpr[j];
                    if (dp[nmsk] == 0 || dp[nmsk] > nprgr)
                    {
                        dp[nmsk] = nprgr;
                        np[npsz++] = nmsk;
                        checkPrice(nmsk);
                    }
                }
            }
            int[] tmp = npb;
            npb = np;
            npbsz = npsz;
            np = tmp;
            npsz = 0;
        }
        
        out.println(msum);
        
        out.flush();
    }
    
    public static void checkPrice(int msk)
    {
        int pr = getPrice(msk);
        if (pr < msum)
            msum = pr;
    }
    
    public static int getPrice(int msk)
    {
        return getPrice(msk, dp[msk]);
    }
    
    public static int getPrice(int msk, int pr)
    {
        for (int j = 0; j < trsz; j++)
            if ((msk & trm[j]) == 0)
                pr += kpr[trn[j]];
        return pr;
    }
}
