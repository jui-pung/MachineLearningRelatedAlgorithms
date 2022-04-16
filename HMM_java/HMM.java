import java.lang.*;
public class HMM
{
    static String[] observations = {"Heads","Tails","Tails","Heads"};
    static int n = 3;  //states
    static int m = 2;
    static double[][] a = {{0.4,0.3,0.3},{0.6,0.3,0.1},{0.2,0.5,0.3}}; //Table A
    static double[][] b = {{0.7,0.3},{0.3,0.7},{0.5,0.5}};  //Table B
    static double[] pi = {0.4,0.3,0.3};  //pi
    static int length = 4;
    static double[][] fwd = new double[100][100];
    static double[][] bwk = new double[100][100];
    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++)
        {
            double p;
            viterbi(observations,n,a,b,pi);
            p = forward(observations,n,a,b,pi);
            System.out.println("=====此序列機率=====");
            System.out.println("P(O|λ)= "+ p);
            backward(observations,n,a,b,pi);
            forward_backward(observations,n,m,a,b,pi);
            if (p > 0.9)
            {
                System.out.println("P(O|λ)= "+ p);
                System.out.println("training frequency= "+i);
                break;
            }
        }
    }
    static void viterbi(String[] observations,int n,double[][] a,double[][] b,double[] pi)
    {
        int[] seq = new int[length];
        for (int i = 0; i < length; i++)
        {
            seq[i] = 0;
        }
        double[][] prob = new double[length][n];
        int[][] prevs = new int[length][n];
        for (int i = 0; i < n; i++)
        {
            if (observations[0].equals("Heads"))
            {
                prob[0][i] = pi[i] * b[i][0];
            }
            else
            {
                prob[0][i] = pi[i] * b[i][1];
            }
        }
        for (int i = 1; i < length; i++)
        {
            for (int j = 0; j < n; j++)
            {
                double pmax = 0;
                double p;
                int dmax = 0;
                for (int k = 0; k < n; k++)
                {
                    p = prob[i-1][k] * a[k][j];
                    //System.out.println(p +"="+ prob[i-1][k]+ "*" +a[k][j]);
                    if (p > pmax)
                    {
                        pmax = p;
                        dmax = k;
                    }
                    //System.out.println("pmax="+pmax);
                }
                if (observations[i].equals("Heads"))
                {
                    prob[i][j] = b[j][0] * pmax;
                    //System.out.println("prob["+i+"]["+j+"] = "+b[j][0]+"*"+pmax);
                }
                else
                {
                    prob[i][j] = b[j][1] * pmax;
                    //System.out.println("prob["+i+"]["+j+"] = "+b[j][1]+"*"+pmax);
                }
                prevs[i-1][j] = dmax;
                //System.out.println("prob["+i+"]["+j+"]"+prob[i][j]);
                //System.out.println("prevs["+(i-1)+"]["+j+"]"+prevs[i-1][j]);
            }
        }
        double pmax = 0;
        int dmax = 0;
        //預測最後一個observation的state
        for (int i = 0; i < n; i++)
        {
            if (prob[length-1][i] > pmax)
            {
                pmax = prob[length-1][i];
                dmax = i;
            }
        }
        seq[length-1] = dmax;
        //System.out.println("seq["+(length-1)+"] = "+seq[length-1]);
        for (int i = length-2; i >= 0; i--)
        {
            seq[i] = prevs[i][seq[i+1]];
            //System.out.println("seq["+i+"] = "+(prevs[i][seq[i+1]]));
        }
        System.out.println("=====預測的隱藏狀態序列=====");
        for (int i = 0; i < length; i++)
        {
            if (seq[i] == 0)
                System.out.print("A");
            if (seq[i] == 1)
                System.out.print("B");
            if (seq[i] == 2)
                System.out.print("C");
            if (i != (length-1))
                System.out.print("->");    
        }
        System.out.println(); 
        for (int i = 0; i < length; i++)
        {
            prob[i] = null;
            prevs[i] = null;
        }
        prob = null;
        prevs = null;
    }
    static double forward(String[] observations,int n,double[][] a,double[][] b,double[] pi)
    {
        double value = 0.0;
        double sum = 0.0;
        double prob;
        for (int i = 0; i < n; i++)
        {
            if (observations[0].equals("Heads"))
            {
                fwd[0][i] = pi[i] * b[i][0];
                //System.out.println("fwd[0]["+i+"]="+fwd[0][i]);
            }
            else
            {
                fwd[0][i] = pi[i] * b[i][1];
                //System.out.println("fwd[0]["+i+"]="+fwd[0][i]);
            }
        }
        for (int i = 1; i < length; i++)
        {
            for (int j = 0; j < n; j++)
            {
                value = 0.0;
                for (int k = 0; k < n; k++)
                {
                    if (observations[i].equals("Heads"))
                    {
                        value = value + fwd[i-1][k] * a[k][j] * b[j][0];
                    }
                    else
                    {
                        value = value + fwd[i-1][k] * a[k][j] * b[j][1];
                    }
                    fwd[i][j] = value;
                }
                //System.out.println("fwd["+i+"]["+j+"]="+fwd[i][j]);
            }
        }
        for (int i = 0; i < n; i++)
        {
            sum = sum + fwd[length-1][i];
        }
        prob = sum;  //所有機率值加總結果(fwd[3][0]+fwd[3][1]+fwd[3][2])
        //System.out.println(prob); 
        return prob;
    }
    static double backward(String[] observations,int n,double[][] a,double[][] b,double[] pi)
    {
        double value = 0.0;
        double sum = 0.0;
        double prob;
        for (int i = 0; i < n; i++)
        {
            bwk[length-1][i] = 1;
        }
        for (int i = length-2; i >= 0; i--)
        {
            for (int j = 0; j < n; j++)
            {
                value = 0.0;
                for (int k = 0; k < n; k++)
                {
                    if (observations[i+1].equals("Heads"))
                    {
                        value = value + bwk[i+1][k] * a[j][k] *b[k][0];
                    }
                    else
                    {
                        value = value + bwk[i+1][k] * a[j][k] *b[k][1];
                    }
                    bwk[i][j] = value;
                }
                //System.out.println("bwk["+i+"]["+j+"]="+bwk[i][j]);
            }
        }
        for (int i = 0; i < n; i++)
        {
            if (observations[0].equals("Heads"))
            {
                sum = sum + pi[i] * b[i][0] * bwk[0][i];
            }
            else
            {
                sum = sum + pi[i] * b[i][1] * bwk[0][i];
            }
        }
        prob = sum;
        //System.out.println(prob);
        return prob;
    }
    static void forward_backward(String[] observations,int n,int m,double[][] a,double[][] b,double[] pi)
    {
        double[][] gamma = new double[100][100];
        double[][][] zi = new double[100][100][100];
        double p_obs = forward(observations,n,a,b,pi);
        double b_obs = backward(observations,n,a,b,pi);;
        double val;
        double sum = 0.0;
        double sum1 = 0.0;
        System.out.println("=====New pi=====");
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < n; j++)
            {
                gamma[i][j] = (fwd[i][j] *bwk[i][j]) / p_obs;
                if (i == 0)
                {
                    pi[j] = gamma[i][j];
                    System.out.println("pi["+j+"]= "+pi[j]);
                }
                if (i == length-1)
                    continue;
                for (int k = 0; k < n; k++)
                {
                    if (observations[i+1].equals("Heads"))
                        zi[i][j][k] = fwd[i][j] * a[j][k] * b[k][0] * bwk[i+1][k] / p_obs;
                    else
                        zi[i][j][k] = fwd[i][j] * a[j][k] * b[k][1] * bwk[i+1][k] / p_obs;
                }
            }
        }
        System.out.println("=====New Table A=====");
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                sum = 0.0;
                sum1 = 0.0;
                for (int k = 0; k < length-1; k++)
                {
                    sum = sum + zi[k][i][j];
                }
                val = sum;
                for (int k = 0; k < length-1; k++)
                {
                    sum1 = sum1 + gamma[k][i];
                }
                val = val / sum1;
                a[i][j] = val;
                System.out.println("a["+i+"]["+j+"]= "+a[i][j]);
            }
        }
        sum = 0.0;
        int t;
        System.out.println("=====New Table B=====");
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                val = 0.0;
                for (int k = 0; k < length; k++)
                {
                    if (observations[k].equals("Heads"))
                        t = 0;
                    else
                        t = 1;
                    if (t == j)
                        val = val + gamma[k][i];
                }
                sum = 0.0;
                for (int k = 0; k < length; k++)
                {
                    sum = sum + gamma[k][i];
                }
                val = val / sum;
                b[i][j] = val;
                System.out.println("b["+i+"]["+j+"]= "+b[i][j]);
            }
        }
    }
}
