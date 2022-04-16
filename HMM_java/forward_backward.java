import java.lang.*;
public class forward_backward
{
    static String[] observations = {"Heads","Tails","Tails","Heads"};
    static int n = 3;  //states
    static int m = 2;  
    static double[][] a = {{0.4,0.3,0.3},{0.6,0.3,0.1},{0.2,0.5,0.3}}; //Table A
    static double[][] b = {{0.7,0.3},{0.3,0.7},{0.5,0.5}};  //Table B
    static double[] pi = {0.4,0.3,0.3};  //pi
    static double[][] bwk = {{0.12,0.10,0.14},{0.24,0.23,0.29},{0.52,0.56,0.44},{1,1,1}};
    static double[][] fwd = {{0.28,0.09,0.15},{0.05,0.13,0.06},{0.03,0.06,0.02},{0.04,0.01,0.01}};
    static int length = 4;
    public static void main(String[] args)
    {
        double[][] gamma = new double[100][100];
        double[][][] zi = new double[100][100][100];
        double p_obs = 0.073;
        double b_obs = 0.073;
        double val;
        double sum = 0.0;
        double sum1 = 0.0;
        
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
