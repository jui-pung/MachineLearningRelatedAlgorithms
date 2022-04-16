import java.lang.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class FlowerTest
{
    public static void main(String[] args) throws IOException
    {
        int input_node = 4;
        int hidden_node = 6;
        int output_node = 3;
        //輸入到隱藏的權重
        double[][] W_hy = 
        {{-1.997783378046438,-1.1105110683933734,0.6284003353205694,0.6106058165788343,-0.4166860772295936,1.8546098072747654},
        {0.5420786675010454,0.21744262857951116,-0.07943433587377616,0.037742394758336205,0.4790938199672145,-0.6440889237163502},
        {1.0983011539696206,0.6563768472574626,-0.5801816596571078,-0.21773523037575493,0.8379528292293447,-0.7132038582154773}};
        //隱藏到輸出的權重
        double[][] W_xh = 
        {{-0.4712952723629617,0.013011166207035726,0.5401277689744848,0.6332973879781801,-0.19101177490361182,0.30534856021309675},
        {-1.1276735152286195,-0.7537288764512231,0.3763979231165397,0.18176519227469634,-0.5198795188455132,1.203942490846085},
        {1.839044454823261,0.5937651122059298,1.0295262265530107,1.0039800042173384,0.6331989595570452,-2.429362859442649},
        {0.9715709931458231,0.8399167314021176,0.6998102553065173,0.9860790975872072,0.6773853942764494,-0.6980235617927453}};
        //隱藏層的閥值
        double[] Theta_h = {0.879687088583938,0.7189724745628193,0.40889505054567143,0.6943781247134175,0.4088206557959636,0.3342669478028224};
        //輸出層的閥值
        double[] Theta_y = {0.258159287902944,1.2710507403823583,1.6872251223434505};

        //讀檔testing
        int each_item = 7;
        FileReader fr = new FileReader("iris datasets_test.txt"); 
        BufferedReader br = new BufferedReader(fr);
        String line,tempstring;
        String[] tempArray= new String[7];
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
        int k = myList.size()/7;
        int count=0;
        double[][] No = new double[k][7]; 
        for (int x=0; x<myList.size()/7; x++)
        {
            for (int y=0; y<7; y++)
            {
                No[x][y]=Double.parseDouble((String) myList.get(count));
                count++;
            }
        }

        double[] H = new double[hidden_node];
        for (int i = 0; i < hidden_node; i++)
        {
                H[i] = 0;
        }

        double[] Y = new double[output_node];
        for (int i = 0; i < output_node; i++)
        {
                Y[i] = 0;
        }
        int correct = 0;  //判斷正確統計

        for (int i = 0; i < No.length; i++)
        {
            System.out.println("第" + (i+1) + "筆測試資料:");
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
            int N = -1; //判斷花種類
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
            //System.out.println(N);
            if (N == 0)
            {
                if (No[i][each_item-3] == 1)
                    System.out.print("正確花種類為: setosa     ");
                if (No[i][each_item-2] == 1)
                    System.out.print("正確花種類為: versicolor ");  
                if (No[i][each_item-1] == 1)
                    System.out.print("正確花種類為: virginica  ");    
                System.out.println("===>> 預測花種類為: setosa");
            }
            if (N == 1)
            {
                if (No[i][each_item-3] == 1)
                    System.out.print("正確花種類為: setosa     ");
                if (No[i][each_item-2] == 1)
                    System.out.print("正確花種類為: versicolor ");  
                if (No[i][each_item-1] == 1)
                    System.out.print("正確花種類為: virginica  "); 
                System.out.println("===>> 預測花種類為: versicolor");
            }
            if (N == 2)
            {
                if (No[i][each_item-3] == 1)
                    System.out.print("正確花種類為: setosa     ");
                if (No[i][each_item-2] == 1)
                    System.out.print("正確花種類為: versicolor ");  
                if (No[i][each_item-1] == 1)
                    System.out.print("正確花種類為: virginica  "); 
                System.out.println("===>> 預測花種類為: virginica");
            }
            if (N == 0 && (No[i][each_item-3] == 1) || N == 1 && (No[i][each_item-2] == 1) || N == 2 && (No[i][each_item-1] == 1))
            {
                correct = correct + 1;
            }
            System.out.println();
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        System.out.println("Total accuracy: " + df.format(correct/60.0));
    }
}
