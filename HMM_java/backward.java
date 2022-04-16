import java.lang.*;
public class backward
{
    static String[] observations = {"Heads","Tails","Tails","Heads"};
    static int n = 3;  //states
    static double[][] a = {{0.4,0.3,0.3},{0.6,0.3,0.1},{0.2,0.5,0.3}}; //Table A
    static double[][] b = {{0.7,0.3},{0.3,0.7},{0.5,0.5}};  //Table B
    static double[] pi = {0.4,0.3,0.3};  //pi
    static int length = 4;
    static double[][] bwk = new double[100][100];
    public static void main(String[] args)
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
                System.out.println("bwk["+i+"]["+j+"]="+bwk[i][j]);
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
        System.out.println(prob);
    }
}
