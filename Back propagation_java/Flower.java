import java.lang.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class Flower
{
    public static void main(String[] args) throws IOException
    {
        int input_node = 4;
        int hidden_node = 6;
        int output_node = 3;
        //輸入到隱藏的權重
        double[][] W_xh = new double[input_node][hidden_node];
        for (int i = 0; i < W_xh.length; i++)
        {
            for (int j = 0; j < W_xh[i].length; j++)
            {
                W_xh[i][j] = (double)(Math.random());
            }
        }
        //double[][] W_xh = {{0.5,0.5,0.5,0.5,0.5},{0.5,0.5,0.5,0.5,0.5},{0.5,0.5,0.5,0.5,0.5},{0.5,0.5,0.5,0.5,0.5}};
        //隱藏到輸出的權重
        double[][] W_hy = new double[output_node][hidden_node];
        for (int i = 0; i < W_hy.length; i++)
        {
            for (int j = 0; j < W_hy[i].length; j++)
            {
                W_hy[i][j] = (double)(Math.random());
            }
        }
        //double[][] W_hy = {{0.5,0.5,0.5,0.5,0.5},{0.5,0.5,0.5,0.5,0.5},{0.5,0.5,0.5,0.5,0.5}};
        //隱藏層的閥值
        double[] Theta_h = new double[hidden_node];
        for (int i = 0; i < Theta_h.length; i++)
        {
                Theta_h[i] = (double)(Math.random());
        }
        //double[] Theta_h = {0.1,0.1,0.1,0.1,0.1};
        //輸出層的閥值
        double[] Theta_y = new double[output_node];
        for (int i = 0; i < Theta_y.length; i++)
        {
                Theta_y[i] = (double)(Math.random());
        }
        //double[] Theta_y = {0.1,0.1,0.1};
        //權重修正量
        double[][] D_W_xh = new double[input_node][hidden_node];
        for (int i = 0; i < D_W_xh.length; i++)
        {
            for (int j = 0; j < D_W_xh[i].length; j++)
            {
                D_W_xh[i][j] = 0;
            }
        }
        //double[][] D_W_xh = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
        double[][] D_W_hy = new double[output_node][hidden_node];
        for (int i = 0; i < D_W_hy.length; i++)
        {
            for (int j = 0; j < D_W_hy[i].length; j++)
            {
                D_W_hy[i][j] = 0;
            }
        }
        //double[][] D_W_hy = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
        //閥值修正量
        double[] D_Theta_h = new double[hidden_node];
        for (int i = 0; i < D_Theta_h.length; i++)
        {
                D_Theta_h[i] = 0;
        }
        //double[] D_Theta_h = {0,0,0,0,0};
        double[] D_Theta_y = new double[output_node];
        for (int i = 0; i < D_Theta_y.length; i++)
        {
                D_Theta_y[i] = 0;
        }
        //double[] D_Theta_y = {0,0,0};
        
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

        double[] H = new double[hidden_node];
        double[] delta_H = new double[hidden_node];
        for (int i = 0; i < hidden_node; i++)
        {
                H[i] = 0;
                delta_H[i] = 0;
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
            double[][] sum_D_W_xh = new double[input_node][hidden_node];
            for (int i = 0; i < sum_D_W_xh.length; i++)
            {
                for (int j = 0; j < sum_D_W_xh[i].length; j++)
                {
                    sum_D_W_xh[i][j] = 0;
                }
            }
            double[][] sum_D_W_hy = new double[output_node][hidden_node];
            for (int i = 0; i < sum_D_W_hy.length; i++)
            {
                for (int j = 0; j < sum_D_W_hy[i].length; j++)
                {
                    sum_D_W_hy[i][j] = 0;
                }
            }
            double[] sum_D_Theta_h = new double[hidden_node];
            for (int i = 0; i < sum_D_Theta_h.length; i++)
            {
                    sum_D_Theta_h[i] = 0;
            }
            double[] sum_D_Theta_y = new double[output_node];
            for (int i = 0; i < sum_D_Theta_y.length; i++)
            {
                    sum_D_Theta_y[i] = 0;
            }
            double correct = 0;
            
            for (int i = 0; i < 90; i++)
            {
                //計算隱藏層輸出值
                for (int j = 0; j < hidden_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < input_node; h++)
                    {
                        sum = sum + No[i][h] * W_xh[h][j];
                        //System.out.println("H[j]"+W_xh[h][j]);
                    }
                    H[j] = 1.0 / (1 + Math.exp(-(sum - Theta_h[j])));
                    //System.out.println("H[j]"+H[j]);
                }
                //計算輸出層輸出值
                int N = -1;
                for (int j = 0; j < output_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < hidden_node; h++)
                    {
                        sum = sum + H[h] * W_hy[j][h];
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
                for (int j = 0; j < hidden_node; j++)
                {
                    double sum = 0;
                    for (int h = 0; h < output_node; h++)
                    {
                        sum = sum + W_hy[h][j] * delta_Y[h];
                    }
                    delta_H[j] = H[j] * (1.0 - H[j]) * sum;
                    //System.out.println("delta_H["+ j + "]: " + delta_H[j]);
                }
                //隱藏層到輸出層的權重與閥值修正量
                for (int j = 0; j < output_node; j++)
                {
                    for (int h = 0; h < hidden_node; h++)
                    {
                        D_W_hy[j][h] = learningRate * delta_Y[j] * H[h];
                        //System.out.println("D_W_hy["+ j + "]["+ h + "]: " + D_W_hy[j][h]);
                    }
                }
                for (int j = 0; j < output_node; j++)
                {
                    D_Theta_y[j] = -1 * learningRate * delta_Y[j];
                    //System.out.println(D_Theta_y[j]);
                }
                //輸入層到隱藏層的權重與閥值修正量
                for (int j = 0; j < input_node; j++)
                {
                    for (int h = 0; h < hidden_node; h++)
                    {
                        D_W_xh[j][h] = learningRate * delta_H[h] * No[i][j];
                        //System.out.println("D_W_xh["+ j + "]["+ h + "]: " + D_W_xh[j][h]);
                    }
                }
                for (int j = 0; j < hidden_node; j++)
                {
                    D_Theta_h[j] = -1 * learningRate * delta_H[j];
                    //System.out.println(D_Theta_h[j]);
                }
                
                for (int j = 0; j < output_node; j++)
                {
                    
                    for (int h = 0; h < hidden_node; h++)
                    {
                        sum_D_W_hy[j][h] = sum_D_W_hy[j][h] + D_W_hy[j][h];
                        //System.out.println("sum_W_hy[" + j + "][" + h + "]: " + sum_D_W_hy[j][h]);
                    }
                }
                
                for (int j = 0; j < output_node; j++)
                {
                    sum_D_Theta_y[j] = sum_D_Theta_y[j] + D_Theta_y[j];
                    //System.out.println(D_Theta_y[j]);
                }
                for (int j = 0; j < input_node; j++)
                {
                    for (int h = 0; h < hidden_node; h++)
                    {
                        sum_D_W_xh[j][h] = sum_D_W_xh[j][h] + D_W_xh[j][h];
                        //System.out.println("D_W_xh["+ j + "]["+ h + "]: " + D_W_xh[j][h]);
                    }
                }
                for (int j = 0; j < hidden_node; j++)
                {
                    sum_D_Theta_h[j] = sum_D_Theta_h[j] + D_Theta_h[j];
                    //System.out.println(D_Theta_h[j]);
                }
            }
            DecimalFormat df = new DecimalFormat("##0.00");
            System.out.println("accuracy: " + df.format(correct/90.0));
            if (correct > 85)
                break;
            
            FileWriter fw = new FileWriter("gg.txt");   
            //計算新的權重與閥值
            System.out.println("======新的權重與閥值");
            fw.write("W_hy\r\n");
            for (int j = 0; j < output_node; j++)
            {
                for (int h = 0; h < hidden_node; h++)
                {
                    W_hy[j][h] = W_hy[j][h] + sum_D_W_hy[j][h]/90;
                    fw.write(Double.toString(W_hy[j][h]) + ","); 
                    System.out.println("W_hy[" + j + "][" + h + "]: " + W_hy[j][h]);
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
                for (int h = 0; h < hidden_node; h++)
                {
                    W_xh[j][h] = W_xh[j][h] + sum_D_W_xh[j][h]/90;
                    fw.write(Double.toString(W_xh[j][h]) + ",");
                    System.out.println("W_xh[" + j + "][" + h + "]: " + W_xh[j][h]);
                }
                fw.write("\r\n");
            }
            fw.write("\r\n");
            fw.write("Theta_h\r\n");
            for (int j = 0; j < hidden_node; j++)
            {
                Theta_h[j] = Theta_h[j] + sum_D_Theta_h[j]/90;
                fw.write(Double.toString(Theta_h[j]) + ",");    
                System.out.println("Theta_h[" + j + "]: " + Theta_h[j]);
            }
            fw.close(); 
        }
    }
}
