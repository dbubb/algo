import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
        State[] sts1 = new State[5];
        State[] sts2 = new State[5];
        int stslen1 = 0;
        int stslen2 = 0;
        int cnt = 0;
        int val = 0;
        boolean valreaded = false;
        boolean start = true;
        while (true)
        {
            if (!valreaded)
                val = reader.read();
            if (val == -1)
            {
                if (cnt < n)
                    out.println(start ? "YES" : "NO");
                break;
            }
            char ch = (char)val;
            if (ch == '\n' || ch == '\r')
            {
                out.println(start ? "YES" : "NO");
                cnt++;
                if (cnt == n)
                    break;
                stslen1 = 0;
                stslen2 = 0;
                start = true;
                while ((char)val == '\n' || (char)val == '\r')
                    val = reader.read();
                valreaded = true;
                continue;
            }
            valreaded = false;
            if (start)
            {
                if (ch == 'o')
                    sts2[stslen2++] = State.O;
                else if (ch == 'p')
                    sts2[stslen2++] = State.P;
                else if (ch == 'i')
                    sts2[stslen2++] = State.I;
            }
            start = false;
            for (int i = 0; i < stslen1; i++)
            {
                State st = sts1[i];
                if (ch == 'o')
                {
                    if (st == State.PUT)
                        sts2[stslen2++] = State.PUTO;
                }
                else if (ch == 'u')
                {
                    if (st == State.O)
                        sts2[stslen2++] = State.OU;
                    else if (st == State.OUTP)
                        sts2[stslen2++] = State.OUTPU;
                    else if (st == State.P)
                        sts2[stslen2++] = State.PU;
                    else if (st == State.INP)
                        sts2[stslen2++] = State.INPU;
                }
                else if (ch == 't')
                {
                    if (st == State.OU)
                        sts2[stslen2++] = State.OUT;
                    else if (st == State.OUTPU)
                        sts2[stslen2++] = State.OUTPUT;
                    else if (st == State.PU)
                        sts2[stslen2++] = State.PUT;
                    else if (st == State.INPU)
                        sts2[stslen2++] = State.INPUT;
                }
                else if (ch == 'p')
                {
                    if (st == State.OUT)
                        sts2[stslen2++] = State.OUTP;
                    else if (st == State.IN)
                        sts2[stslen2++] = State.INP;
                }
                else if (ch == 'n')
                {
                    if (st == State.O)
                        sts2[stslen2++] = State.ON;
                    else if (st == State.PUTO)
                        sts2[stslen2++] = State.PUTON;
                    else if (st == State.I)
                        sts2[stslen2++] = State.IN;
                }
                else if (ch == 'e')
                {
                    if (st == State.ON)
                        sts2[stslen2++] = State.ONE;
                }
                if (stslen2 > 0 && sts2[stslen2-1].fin)
                    start = true;
                //out.println("start = " + start + "; " + Stream.of(sts2).limit(stslen2).map(s -> s.name()).collect(Collectors.joining(" ")));
            }
            if (!start && stslen2 == 0)
            {
                while (val != -1 && (char)val != '\n' && (char)val != '\r')
                    val = reader.read();
                valreaded = true;
                continue;
            }
            
            State[] tmp = sts1;
            sts1 = sts2;
            sts2 = tmp;
            stslen1 = stslen2;
            stslen2 = 0;
        }

        out.flush();
   }
   
   public static enum State
   {
       //START(false),
       O(false),
       OU(false),
       OUT(true),
       OUTP(false),
       OUTPU(false),
       OUTPUT(true),
       P(false),
       PU(false),
       PUT(false),
       PUTO(false),
       PUTON(true),
       I(false),
       IN(true),
       INP(false),
       INPU(false),
       INPUT(true),
       ON(false),
       ONE(true);
       
       public boolean fin;
       
       State(boolean fin)
       {
           this.fin = fin;
       }
   }
}
