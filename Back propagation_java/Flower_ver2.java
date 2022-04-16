import java.lang.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class Flower_ver2
{
    public static void main(String[] args) throws IOException
    {
        //各層節點數
        int input_node = 4;
        int hidden1_node = 7;
        int hidden2_node = 7;
        int output_node = 3;
        //隨機產生輸入層到第一層隱藏層的權重
        double[][] W_xh = new double[input_node][hidden1_node];
        for (int i = 0; i < W_xh.length; i++)
        {
            for (int j = 0; j < W_xh[i].length; j++)
            {
                W_xh[i][j] = (double)(Math.random());
            }
        }
        //隨機產生第一層隱藏層到第二層隱藏層的權重
        double[][] W_hh = new double[hidden1_node][hidden2_node];
        for (int i = 0; i < W_hh.length; i++)
        {
            for (int j = 0; j < W_hh[i].length; j++)
            {
                W_hh[i][j] = (double)(Math.random());
            }
        }
        //隨機產生第二層隱藏層到輸出層的權重
        double[][] W_hy = new double[output_node][hidden2_node];
        for (int i = 0; i < W_hy.length; i++)
        {
            for (int j = 0; j < W_hy[i].length; j++)
            {
                W_hy[i][j] = (double)(Math.random());
            }
        }
        //兩層隱藏層的閥值
        double[] Theta_h1 = new double[hidden1_node];
        for (int i = 0; i < Theta_h1.length; i++)
        {
                Theta_h1[i] = (double)(Math.random());
        }
        double[] Theta_h2 = new double[hidden2_node];
        for (int i = 0; i < Theta_h2.length; i++)
        {
                Theta_h2[i] = (double)(Math.random());
        }
        //輸出層的閥值
        double[] Theta_y = new double[output_node];
        for (int i = 0; i < Theta_y.length; i++)
        {
                Theta_y[i] = (double)(Math.random());
        }

        //權重修正量初值
        double[][] D_W_xh = new double[input_node][hidden1_node];
        for (int i = 0; i < D_W_xh.length; i++)
        {
            for (int j = 0; j < D_W_xh[i].length; j++)
            {
                D_W_xh[i][j] = 0;
            }
        }
        double[][] D_W_hh = new double[hidden1_node][hidden2_node];
        for (int i = 0; i < D_W_hh.length; i++)
        {
            for (int j = 0; j < D_W_hh[i].length; j++)
            {
                D_W_hh[i][j] = 0;
            }
        }
        double[][] D_W_hy = new double[output_node][hidden2_node];
        for (int i = 0; i < D_W_hy.length; i++)
        {
            for (int j = 0; j < D_W_hy[i].length; j++)
            {
                D_W_hy[i][j] = 0;
            }
        }

        //閥值修正量初值
        double[] D_Theta_h1 = new double[hidden1_node];
        for (int i = 0; i < D_Theta_h1.length; i++)
        {
                D_Theta_h1[i] = 0;
        }
        double[] D_Theta_h2 = new double[hidden2_node];
        for (int i = 0; i < D_Theta_h2.length; i++)
        {
                D_Theta_h2[i] = 0;
        }
        double[] D_Theta_y = new double[output_node];
        for (int i = 0; i < D_Theta_y.length; i++)
        {
                D_Theta_y[i] = 0;
        }
        
        double learningRate = 5;
        
        //讀檔training
        int each_item = 7; //前四筆為輸入 後三筆為輸出
        FileReader fr = new FileReader("iris datasets_train.txt"); 
        BufferedReader br = new BufferedReader(fr);
        String line,tempstring;
        String[] tempArray= new String[each_item];
        ArrayList myList = new ArrayList();
        int l=0;
        while((line = br.readLine())!=null)
        {
             tempstring = line; 
             tempArray = tempstring.split("\\s");
             for(l=0;l< tempArray.length;l++)
             {          
                 myList.add(tempArray[l]);
             }
        }
        int k = myList.size()/each_item;
        int count=0;
        double[][] No = new double[k][each_item]; 
        for (int x=0; x<myList.size()/each_item; x++)
        {
            for (int y=0; y<each_item; y++)
            {
                No[x][y]=Double.parseDouble((String) myList.get(count));
                count++;
            }
        }

        double[] H1 = new double[hidden1_node];
        double[] H2 = new double[hidden2_node];
        double[] delta_H1 = new double[hidden1_node];
        double[] delta_H2 = new double[hidden2_node];
        for (int i = 0; i < hidden1_node; i++)
        {
                H1[i] = 0;
                delta_H1[i] = 0;
        }
        for (int i = 0; i < hidden2_node; i++)
        {
                H2[i] = 0;
                delta_H2[i] = 0;
        }
        double[] Y = new double[output_node];
        double[] delta_Y = new double[output_node];
        for (int i = 0; i < output_node; i++)
        {
                Y[i] = 0;
                delta_Y[i] = 0;
        }
        
        for (int n = 0; n < 300; n++)
        {
            //權重修正量加總初值
            double[][] sum_D_W_xh = new double[input_node][hidden1_node];
            for (int i = 0; i < sum_D_W_xh.length; i++)
            {
                for (int j = 0; j < sum_D_W_xh[i].length; j++)
                {
                    sum_D_W_xh[i][j] = 0;
                }
            }
            double[][] sum_D_W_hh = new double[hidden1_node][hidden2_node];
            for (int i = 0; i < sum_D_W_hh.length; i++)
            {
                for (int j = 0; j < sum_D_W_hh[i].length; j++)
                {
                    sum_D_W_hh[i][j] = 0;
                }
            }
            double[][] sum_D_W_hy = new double[output_node][hidden2_node];
            for (int i = 0; i < sum_D_W_hy.length; i++)
            {
                for (int j = 0; j < sum_D_W_hy[i].length; j++)
                {
                    sum_D_W_hy[i][j] = 0;
                }
            }
            //閥值修正量加總初值
            double[] sum_D_Theta_h1 = new double[hidden1_node];
            for (int i = 0; i < sum_D_Theta_h1.length; i++)
            {
                    sum_D_Theta_h1[i] = 0;
            }
            double[] sum_D_Theta_h2 = new double[hidden2_node];
            for (int i = 0; i < sum_D_Theta_h2.length; i++)
            {
                    sum_D_Theta_h2[i] = 0;
            }
            double[] sum_D_Theta_y = new double[output_node];
            for (int i = 0; i < sum_D_Theta_y.length; i++)
            {
                    sum_D_Theta_y[i] = 0;
            }
            double correct = 0;
            
            for (int i = 0; i < 90; i++)
            {
                //計算第一層隱藏層輸出值
                for (int j = 0; j < hidden1_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < input_node; h++)
                    {
                        sum = sum + No[i][h] * W_xh[h][j];
                    }
                    H1[j] = 1.0 / (1 + Math.exp(-(sum - Theta_h1[j])));
                }
                //計算第二層隱藏層輸出值
                for (int j = 0; j < hidden2_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < hidden1_node; h++)
                    {
                        sum = sum + H1[h] * W_hh[h][j];
                    }
                    H2[j] = 1.0 / (1 + Math.exp(-(sum - Theta_h2[j])));
                    //System.out.println("Y["+ j + "]: " + Y[j]);
                }
                //計算輸出層輸出值
                int N = -1;
                for (int j = 0; j < output_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < hidden2_node; h++)
                    {
                        sum = sum + H2[h] * W_hy[j][h];
                    }
                    Y[j] = 1.0 / (1 + Math.exp(-(sum - Theta_y[j])));
                    if (Y[0] > Y[1] && Y[0] > Y[2])
                    {
                        N = 0;
                    }
                    if (Y[1] > Y[0] && Y[1] > Y[2])
                    {
                        N = 1;
                    }
                    if (Y[2] > Y[0] && Y[2] > Y[1])
                    {
                        N = 2; 
                    }
                    //System.out.println("Y["+ j + "]: " + Y[j]);
                }
                if (N == 0 && (No[i][each_item-3] == 1) || N == 1 && (No[i][each_item-2] == 1) || N == 2 && (No[i][each_item-1] == 1))
                {
                    correct = correct + 1;
                }
                
                //計算輸出層差距量
                for (int j = 0; j < output_node; j++)
                {
                    delta_Y[j] = Y[j] * (1.0 - Y[j]) * (No[i][j+4] - Y[j]);
                    //System.out.println("delta_Y["+ j + "]: " + (Y[j]));
                }
                //計算隱藏層差距量
                for (int j = 0; j < hidden2_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < output_node; h++)
                    {
                        sum = sum + W_hy[h][j] * delta_Y[h];
                    }
                    delta_H2[j] = H2[j] * (1.0 - H2[j]) * sum;
                    //System.out.println("delta_H["+ j + "]: " + delta_H[j]);
                }
                for (int j = 0; j < hidden1_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < hidden2_node; h++)
                    {
                        sum = sum + W_hh[h][j] * delta_H2[h];
                    }
                    delta_H1[j] = H1[j] * (1.0 - H1[j]) * sum;
                    //System.out.println("delta_H["+ j + "]: " + delta_H[j]);
                }
                //隱藏層到輸出層的權重與閥值修正量
                for (int j = 0; j < output_node; j++)
                {
                    for (int h = 0; h < hidden2_node; h++)
                    {
                        D_W_hy[j][h] = learningRate * delta_Y[j] * H2[h];
                    }
                }
                //第一層隱藏層到第二層隱藏層的權重與閥值修正量
                for (int j = 0; j < hidden2_node; j++)
                {
                    for (int h = 0; h < hidden1_node; h++)
                    {
                        D_W_hh[j][h] = learningRate * delta_H2[j] * H1[h];
                    }
                }
                for (int j = 0; j < output_node; j++)
                {
                    D_Theta_y[j] = -1 * learningRate * delta_Y[j];
                }
                //輸入層到隱藏層的權重與閥值修正量
                for (int j = 0; j < input_node; j++)
                {
                    for (int h = 0; h < hidden1_node; h++)
                    {
                        D_W_xh[j][h] = learningRate * delta_H1[h] * No[i][j];
                    }
                }
                for (int j = 0; j < hidden1_node; j++)
                {
                    D_Theta_h1[j] = -1 * learningRate * delta_H1[j];
                }
                for (int j = 0; j < hidden2_node; j++)
                {
                    D_Theta_h2[j] = -1 * learningRate * delta_H2[j];
                }
                for (int j = 0; j < output_node; j++)
                { 
                    for (int h = 0; h < hidden2_node; h++)
                    {
                        sum_D_W_hy[j][h] = sum_D_W_hy[j][h] + D_W_hy[j][h];
                    }
                }
                for (int j = 0; j < output_node; j++)
                {
                    sum_D_Theta_y[j] = sum_D_Theta_y[j] + D_Theta_y[j];
                    //System.out.println(D_Theta_y[j]);
                }
                for (int j = 0; j < input_node; j++)
                {
                    for (int h = 0; h < hidden1_node; h++)
                    {
                        sum_D_W_xh[j][h] = sum_D_W_xh[j][h] + D_W_xh[j][h];
                        //System.out.println("D_W_xh["+ j + "]["+ h + "]: " + D_W_xh[j][h]);
                    }
                }
                for (int j = 0; j < hidden1_node; j++)
                {
                    for (int h = 0; h < hidden2_node; h++)
                    {
                        sum_D_W_hh[j][h] = sum_D_W_hh[j][h] + D_W_hh[j][h];
                        //System.out.println("D_W_xh["+ j + "]["+ h + "]: " + D_W_xh[j][h]);
                    }
                }
                for (int j = 0; j < hidden2_node; j++)
                {
                    sum_D_Theta_h2[j] = sum_D_Theta_h2[j] + D_Theta_h2[j];
                    //System.out.println(D_Theta_h[j]);
                }
                for (int j = 0; j < hidden1_node; j++)
                {
                    sum_D_Theta_h1[j] = sum_D_Theta_h1[j] + D_Theta_h1[j];
                    //System.out.println(D_Theta_h[j]);
                }
            }
            DecimalFormat df = new DecimalFormat("##0.00");
            System.out.println("accuracy: " + df.format(correct/90.0));
            if (correct > 85)
                break;
            
            FileWriter fw = new FileWriter("gg2.txt");   
            //計算新的權重與閥值
            System.out.println("======新的權重與閥值");
            fw.write("W_hy\r\n");
            for (int j = 0; j < output_node; j++)
            {
                for (int h = 0; h < hidden2_node; h++)
                {
                    W_hy[j][h] = W_hy[j][h] + sum_D_W_hy[j][h]/90;
                    fw.write(Double.toString(W_hy[j][h]) + ","); 
                    System.out.println("W_hy[" + j + "][" + h + "]: " + W_hy[j][h]);
                }
                fw.write("\r\n");
            }
            fw.write("\r\n");
            fw.write("W_hh\r\n");
            for (int j = 0; j < hidden2_node; j++)
            {
                for (int h = 0; h < hidden1_node; h++)
                {
                    W_hh[j][h] = W_hh[j][h] + sum_D_W_hh[j][h]/90;
                    fw.write(Double.toString(W_hh[j][h]) + ","); 
                    System.out.println("W_hh[" + j + "][" + h + "]: " + W_hh[j][h]);
                }
                fw.write("\r\n");
            }
            fw.write("\r\n");
            fw.write("Theta_y\r\n");
            for (int j = 0; j < output_node; j++)
            {
                Theta_y[j] = Theta_y[j] + sum_D_Theta_y[j]/90;
                fw.write(Double.toString(Theta_y[j]) + ",");
                System.out.println("Theta_y[" + j + "]: " + Theta_y[j]);
            }
            fw.write("\r\n");
            fw.write("\r\n");
            fw.write("W_xh\r\n");
            for (int j = 0; j < input_node; j++)
            {
                for (int h = 0; h < hidden1_node; h++)
                {
                    W_xh[j][h] = W_xh[j][h] + sum_D_W_xh[j][h]/90;
                    fw.write(Double.toString(W_xh[j][h]) + ",");
                    System.out.println("W_xh[" + j + "][" + h + "]: " + W_xh[j][h]);
                }
                fw.write("\r\n");
            }
            fw.write("\r\n");
            fw.write("Theta_h1\r\n");
            for (int j = 0; j < hidden1_node; j++)
            {
                Theta_h1[j] = Theta_h1[j] + sum_D_Theta_h1[j]/90;
                fw.write(Double.toString(Theta_h1[j]) + ",");    
                System.out.println("Theta_h1[" + j + "]: " + Theta_h1[j]);
            }
            fw.write("\r\n");
            fw.write("Theta_h2\r\n");
            for (int j = 0; j < hidden2_node; j++)
            {
                Theta_h2[j] = Theta_h2[j] + sum_D_Theta_h2[j]/90;
                fw.write(Double.toString(Theta_h2[j]) + ",");    
                System.out.println("Theta_h2[" + j + "]: " + Theta_h2[j]);
            }
            fw.close(); 
        }
    }
}
