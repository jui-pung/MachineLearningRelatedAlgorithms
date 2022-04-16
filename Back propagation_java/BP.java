import java.lang.*;
public class BP
{
    public static void main(String[] args)
    {
        double W13 = 1, W23 = -1, W14 = -1, W24 = 1, W35 = 1, W45 = 1;
        double Theta3 = 1, Theta4 = 1, Theta5 = 1;
        double learningRate = 10;
        double No[][] = {{-1,-1,0},{-1,1,1},{1,-1,1},{1,1,0}};
        double net3 = 0, net4 = 0, net5 = 0;
        double H1 = 0, H2 = 0;
        double Y = 0;
        double delta3 = 0, delta4 = 0, delta5[] = {0,0,0,0};
        double DeltaW35[] = {0,0,0,0}, DeltaW45[] = {0,0,0,0}, DeltaW13[] = {0,0,0,0}, DeltaW23[] = {0,0,0,0}, DeltaW14[] = {0,0,0,0}, DeltaW24[] = {0,0,0,0};
        double DeltaTheta5[] = {0,0,0,0}, DeltaTheta4[] = {0,0,0,0}, DeltaTheta3[] = {0,0,0,0};
        for (int n = 0; n < 50; n++)
        {     
            for (int i = 0; i < 4; i++)
            {
                net3 = 0; net4 = 0; net5 = 0;
                H1 = 0; H2 = 0;
                Y = 0;
                delta3 = 0; delta4 = 0;
                net3 = W13 * No[i][0] + W23 * No[i][1] - Theta3;
                H1 = 1/(1+(Math.exp(-net3)));
                net4 = W14 * No[i][0] + W24 * No[i][1] - Theta4;
                H2 = 1/(1+(Math.exp(-net4)));
                net5 = (W35 * H1 + W45 * H2) - Theta5;
                Y = 1/(1+(Math.exp(-net5)));
                delta5[i] = Y * (1 - Y) * (No[i][2] - Y);
                delta4 = H2 * (1 - H2) * W45 * delta5[i];
                delta3 = H1 * (1 - H1) * W35 * delta5[i];
                DeltaW35[i] = learningRate * delta5[i] * H1;
                DeltaW45[i] = learningRate * delta5[i] * H2;
                DeltaW13[i] = learningRate * delta3 * No[i][0];
                DeltaW23[i] = learningRate * delta3 * No[i][1];
                DeltaW14[i] = learningRate * delta4 * No[i][0];
                DeltaW24[i] = learningRate * delta4 * No[i][1];
                DeltaTheta5[i] = -1 * learningRate * delta5[i];
                DeltaTheta4[i] = -1 * learningRate * delta4;
                DeltaTheta3[i] = -1 * learningRate * delta3;
            }
        
            double sumDeltaW35 = 0; double sumDeltaW45 = 0; double sumDeltaW13 = 0; double sumDeltaW23 = 0; double sumDeltaW14 = 0; double sumDeltaW24 = 0;
            double sumDeltaTheta5 = 0; double sumDeltaTheta4 = 0; double sumDeltaTheta3 = 0;
            double sumTheta5 = 0;
            for (int j = 0; j < 4; j++)
            {
                sumTheta5 = sumTheta5 + delta5[j];
                sumDeltaW35 = sumDeltaW35 + DeltaW35[j];
                sumDeltaW45 = sumDeltaW45 + DeltaW45[j];
                sumDeltaW13 = sumDeltaW13 + DeltaW13[j];
                sumDeltaW23 = sumDeltaW23 + DeltaW23[j];
                sumDeltaW14 = sumDeltaW14 + DeltaW14[j];
                sumDeltaW24 = sumDeltaW24 + DeltaW24[j];
                sumDeltaTheta5 = sumDeltaTheta5 + DeltaTheta5[j];
                sumDeltaTheta4 = sumDeltaTheta4 + DeltaTheta4[j];
                sumDeltaTheta3 = sumDeltaTheta3 + DeltaTheta3[j];
            }
            System.out.println("目前誤差: "+sumTheta5);
            if (Math.abs(sumTheta5) < 0.002)
            {
                System.out.println("收斂後加權值與閥值==========");
                System.out.println("加權值與閥值更新次數: "+n);
                System.out.println("W35= "+W35);
                System.out.println("W45= "+W45);
                System.out.println("W13= "+W13);
                System.out.println("W23= "+W23);
                System.out.println("W14= "+W14);
                System.out.println("W24= "+W24);
                System.out.println("Theta5= "+Theta5);
                System.out.println("Theta4= "+Theta4);
                System.out.println("Theta3= "+Theta3);
                break;
            }
            W35 = W35 + sumDeltaW35;
            W45 = W45 + sumDeltaW45;
            W13 = W13 + sumDeltaW13;
            W23 = W23 + sumDeltaW23;
            W14 = W14 + sumDeltaW14;
            W24 = W24 + sumDeltaW24;
            Theta5 = Theta5 + sumDeltaTheta5;
            Theta4 = Theta4 + sumDeltaTheta4;
            Theta3 = Theta3 + sumDeltaTheta3;
            
            System.out.println("目前加權值與閥值==========");
            System.out.println("W35= "+W35);
            System.out.println("W45= "+W45);
            System.out.println("W13= "+W13);
            System.out.println("W23= "+W23);
            System.out.println("W14= "+W14);
            System.out.println("W24= "+W24);
            System.out.println("Theta5= "+Theta5);
            System.out.println("Theta4= "+Theta4);
            System.out.println("Theta3= "+Theta3);
        }
    }
}
