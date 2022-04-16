
public class perceptron
{
   public static void main(String[] args)
   {
       int[][] w = {{0,0,0},{0,0,0},{0,0,0}};  //權重
       int[][] x = {{0,0,1},{1,1,1},{-1,1,1}};  //特徵
       int k = 0;//目前分類位置(x陣列的位置)
       int a = 0;//計算符合正確規則後,需不需要再遞迴
       getDecisionFunction(w,x,k,a);
   }
   public static int[][] getDecisionFunction(int[][] w, int[][] x, int k, int a)
   {
       int[] d = new int[x.length];;
       int count = 0;
       boolean doRecycled = true;
       for (int i = 0; i < w.length; i++)
       {
           for (int j = 0; j < w[0].length; j++)
           {
               count = w[i][j]*x[k][j];
               d[i] = d[i] + count;
           }
           //System.out.println("d" + i + " = " + d[i]);
             
       }
       //判斷權重是否符合規則di[x(k)]<=dl[x(k)]
       for (int i = 0; i < w.length; i++)
       {
           if (i != k)
           {
               //System.out.println("k= "+k);
               if (d[k] <= d[i])
               {
                    doRecycled = false;
                    break;
               }
           }
       }
       if (doRecycled == true)
       {
           System.out.println("======correctly classified======");
           for (int i = 0; i < w.length; i++)
           {
               System.out.print("W"+(i+1)+"= ");
               for (int j = 0; j < w[0].length; j++)
               {
                   w[i][j] = w[i][j];
                   System.out.print(w[i][j]+" ");
               }
               System.out.println();
           }
           a++;
           //System.out.println("a= "+a);
           if (a != 3)
                getDecisionFunction(w,x,(k+1)%3,a);
           if (a == 3)
           {
               System.out.println("======Finall Weight Vectors======");
               for (int i = 0; i < w.length; i++)
               {
                   System.out.print("W"+(i+1)+"= ");
                   for (int j = 0; j < w[0].length; j++)
                   {
                       System.out.print(w[i][j]+" ");
                   }
                   System.out.println();
                }
           }
       }
       else 
       {
           //System.out.println("incorrect");
           System.out.println("======Weight Vectors Increasedd or Decreased======");
           for (int i = 0; i < w.length; i++)
           {
               System.out.print("W"+(i+1)+"= ");
               for (int j = 0; j < w[0].length; j++)
               {
                   if (i == k)
                   {
                        w[i][j] = w[i][j] + x[k][j];
                        System.out.print(w[i][j]+" ");
                   }
                   else
                   {
                        w[i][j] = w[i][j] - x[k][j];
                        System.out.print(w[i][j]+" ");
                   }
               }
               System.out.println();
           }
           getDecisionFunction(w,x,(k+1)%3,a);
       }
       return w;
   }
}
