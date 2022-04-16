import java.lang.*;
public class forward
{
    static String[] observations = {"Heads","Tails","Tails","Heads"};
    static int n = 3;  //states
    static double[][] a = {{0.32,0.36,0.32},{0.56,0.34,0.09},{0.14,0.56,0.28}}; //Table A
    static double[][] b = {{0.72,0.27},{0.23,0.76},{0.54,0.45}};  //Table B
    static double[] pi = {0.51,0.13,0.32};  //pi
    static int length = 4;
    static double[][] fwd = new double[100][100];
    public static void main(String[] args)
    {
        double value = 0.0;
        double sum = 0.0;
        double prob;
        for (int i = 0; i < n; i++)
        {
            if (observations[0].equals("Heads"))
            {
                fwd[0][i] = pi[i] * b[i][0];
                System.out.println("fwd[0]["+i+"]="+fwd[0][i]);
            }
            else
            {
                fwd[0][i] = pi[i] * b[i][1];
                System.out.println("fwd[0]["+i+"]="+fwd[0][i]);
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
                System.out.println("fwd["+i+"]["+j+"]="+fwd[i][j]);
            }
        }
        for (int i = 0; i < n; i++)
        {
            sum = sum + fwd[length-1][i];
        }
        prob = sum;  //所有機率值加總結果(fwd[3][0]+fwd[3][1]+fwd[3][2])
        System.out.println(prob);  
    }
}
