import java.lang.*;
public class BPTest
{
    public static void main(String[] args)
    {
        double W13 = 3.3848421193926117, W23 = -3.384842119392612, W14 = -3.3848421193926117, W24 = 3.384842119392612, W35 = 5.630857231102898, W45 = 5.630857231102898;
        double Theta3 = 3.512240001965859, Theta4 = 3.5122400019658593, Theta5 = 2.8174077252142835;
        double learningRate = 10;
        double No[][] = {{-10,-10,0},{-5,5,1},{5,-5,1},{10,10,0}};
        double net3 = 0, net4 = 0, net5 = 0;
        double H1 = 0, H2 = 0;
        double Y = 0;
        double delta3 = 0, delta4 = 0, delta5[] = {0,0,0,0};
        double DeltaW35[] = {0,0,0,0}, DeltaW45[] = {0,0,0,0}, DeltaW13[] = {0,0,0,0}, DeltaW23[] = {0,0,0,0}, DeltaW14[] = {0,0,0,0}, DeltaW24[] = {0,0,0,0};
        double DeltaTheta5[] = {0,0,0,0}, DeltaTheta4[] = {0,0,0,0}, DeltaTheta3[] = {0,0,0,0};    
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
            System.out.println(Y);
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
    }
}
