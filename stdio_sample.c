#include <stdio.h>
//#include <vector>
//#include <stack>
//#include <algorithm>

//using namespace std;
#define bsize 20
#define bcnt 6000

int main() {
	//ios_base::sync_with_stdio(0);
	//cin.tie(0);
	//unsigned int mask1 = 1073741823; // 3FFFFFFF
	int n;
	char str[4];
	//cin >> n;
	scanf ("%d", &n);
	//unordered_map<int, stack<int>> m;
	//vector<stack<int>> m(1000, stack<int>());
	//const int bsize = 20;
	//const int bcnt = 5300;
	int val[bsize*bcnt];
	unsigned char sc[bcnt];
	short sb[bcnt];
	unsigned short fr[bcnt];
	unsigned int ftop = 0;
	int i;
	for (i = 0; i < bcnt; i++)
	{
	    fr[i] = i;
	    sb[i] = -1;
	}
	while (n--) {
	    //string cmd;
	    int s, v;
	    //cin >> cmd >> s;
	    scanf ("%s ", str);
	    scanf ("%d", &s);
        //unsigned char s1 = (unsigned char)(s % 256);
        //unsigned char s2 = (unsigned char)(s / 256);
	    //s--;
	    if (str[1] == 'U')
	    {
	        //cin >> v;
	        scanf (" %d", &v);
	        
	        if (sb[s] == -1 || sc[s] == bsize)
	        {
	            unsigned short newbl = fr[ftop++];
	            val[newbl*bsize] = sb[s];
	            sb[s] = newbl;
	            sc[s] = 1;
	        }
	        val[sb[s]*bsize + sc[s]] = v;
	        sc[s]++;
	        //int idx = last[s];
	        //while (idx < cur && (st[idx] != 0 || val[idx] != 0))
	        //    idx++;
	        
	        //val[idx] = (unsigned int)v + s2*mask1;
	        //st[idx] = s1;
	        //last[s] = idx+1;
	        //if (idx == cur)
	        //    cur++;
	        //m[s].push(v);
	        //cout << cmd << " " << s << " " << v << '\n';
	        /*if (m[s] != NULL)
	        {
                m[s].push(v);
	        }
	        else
	        {
	            stack<int> vals;
	            vals.push(v);
                m[s] = vals;
	        }*/
	    }
	    else
	    {
	        //cout << cmd << " " << s << '\n';
	        //cout << m[s].top() << '\n';
	        //printf("%d\n", m[s].top());
	        //m[s].pop();
	        //int idx = cur-1;
	        //while (st[idx] != s1 || (val[idx] / mask1) != s2)
	        //    idx--;
	        //printf("%d\n", val[idx] % mask1);
	        sc[s]--;
	        printf("%d\n", val[sb[s]*bsize + sc[s]]);
	        if (sc[s] == 1)
	        {
	            fr[--ftop] = sb[s];
	            sb[s] = val[sb[s]*bsize];
	            sc[s] = bsize;
	        }
	        //st[idx] = 0;
	        //val[idx] = 0;
	        //idx--;
	        
	        //while (idx >=0 && (st[idx] != s1 || (val[idx] / mask1) != s2))
	        //    idx--;
	        //last[s] = idx+1;
	    }
	}
    return 0;
}
