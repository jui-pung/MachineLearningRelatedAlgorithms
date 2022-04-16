import java.lang.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class FlowerTest_ver2
{
    public static void main(String[] args) throws IOException
    {
        int input_node = 4;
        int hidden1_node = 7;
        int hidden2_node = 7;
        int output_node = 3;
        //輸入到隱藏的權重
        double[][] W_hy = 
        {{0.36290002581140923,1.0994949663579325,0.4786642561441251,0.32742636569582334,-4.348947048170665,0.0747612427127136,0.3847486470991828},
        {0.4583928717724801,-0.4757620417112261,-0.6856921224871964,-0.3261714744810962,2.012052845775347,-0.3674815237334009,0.017371227045121392},
        {0.2518144563343144,0.07401281703724359,-0.4272718888460496,-0.24309892339835829,2.4751903099125014,-0.29216971415683257,0.01063416555112579}};
        //隱藏到輸出的權重
        double[][] W_hh = 
        {{0.31483441673758994,0.024348642794624748,0.6081294860347657,0.25197700736887546,0.4974592488466013,0.8669968982799455,0.9676625723440049},
        {-0.09299266182175384,0.20278684352112955,-0.13356556933342972,0.5348924127856343,-0.7050110474721013,-0.1690469895914752,0.45275339361372596},
        {0.6139987602174668,0.05560990463894111,0.818900629666553,0.23321667916972147,-0.027810134962718603,0.6919107747561605,0.6836814362246818},
        {0.44739501367676504,0.745787724686126,0.016249670133384422,-0.007670892858788236,-0.0871234015069697,0.3298728480292657,0.12326666050670093},
        {2.248338951044638,-0.65035317439708,-0.9461991846524624,-0.5962038147732579,4.183092084175348,-0.4468717694952907,-0.8974841314951789},
        {0.8386063109504087,0.5540375286119672,1.0051110904303797,0.32728126009065134,0.003985557909632154,0.9943626727949049,0.6145559742760258},
        {0.5292572639542442,0.6918102487272195,0.14690983894328152,0.6419947897542145,0.13786260371861095,0.20843404525617773,0.2533175770702693}};

        double[][] W_xh = 
        {{-0.3196484446759931,0.6760530708173286,0.45974280345902635,0.168685474679477,-0.31693845340528065,0.814707448650393,0.7337844119033817},
        {-0.42523866714700176,0.7821231038779705,0.7561445099022238,0.9622679916964686,-1.081670692345501,0.722138108176643,0.9909271197249588},
        {1.1239325394895805,0.37653418712192027,0.33653496617560835,0.8266186625691656,1.7773541908064436,0.6805246422994783,0.4535142777096032},
        {0.9705385941667974,0.05186245513259484,0.3374648689782795,0.9778814684064382,1.4971761523855325,0.06563752744046761,0.4412030343647572}};
        //隱藏層的閥值
        double[] Theta_h1 = {1.2117265322723498,0.6695604730946924,0.5713282916513376,0.2912144215541953,1.148562434645,0.04084783861968644,0.756959996497037};
        double[] Theta_h2 = {0.5169105684958419,0.5165497423681661,0.5003739554368193,0.09726429437379415,1.8819228022565455,0.51322192598591,0.6071272992396421};
        //輸出層的閥值
        double[] Theta_y = {0.36067269821503506,0.9014178869056932,1.8319081947117852};

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

        double[] H1 = new double[hidden1_node];
        for (int i = 0; i < hidden1_node; i++)
        {
                H1[i] = 0;
        }
        double[] H2 = new double[hidden2_node];
        for (int i = 0; i < hidden2_node; i++)
        {
                H2[i] = 0;
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
