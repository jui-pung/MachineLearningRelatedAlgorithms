
public class perceptron
{
   public static void main(String[] args)
   {
       int[][] w = {{0,0,0}};  //權重
       int[][] x = {{0,0,1},{0,1,1},{1,0,1},{1,1,1}};  //特徵
       int k = 0;//目前分類位置(例如上面宣告x陣列分為兩類,k=0,1為第一類, k=2,3為第二類)
       int check = 0;//計算符合正確規則後,需不需要再遞迴
       getDecisionFunction(w,x,k,check);
   }
   public static int[][] getDecisionFunction(int[][] w, int[][] x, int k, int check)
   {
       int[] d = new int[x.length];;
       int count = 0;
       for (int i = 0; i < w.length; i++)
       {
           for (int j = 0; j < w[0].length; j++)
           {
               count = w[i][j]*x[k][j];
               d[i] = d[i] + count;
           }
           System.out.println("權重X特徵= " + d[i]);
             
       }
       //判斷權重是否符合規則
       for (int i = 0; i < w.length; i++)
       {
           if (k == 0 || k == 1)
           {
               //System.out.println("k= "+k);
               if (d[i] <= 0)
               {
                    if (check > 0)
                        check = check - 1;
                    System.out.print("新的權重: ");    
                    for (int j = 0; j < w[0].length; j++)
                    {
                        w[i][j] = w[i][j] + x[k][j];
                        System.out.print(w[i][j]+" ");
                    }
                    System.out.println();
                    getDecisionFunction(w,x,(k+1)%4,check);
               }
               else
               {
                   check = check + 1;
                   System.out.print("新的權重: ");
                   for (int j = 0; j < w[0].length; j++)
                   {
                       w[i][j] = w[i][j];
                       System.out.print(w[i][j]+" ");
                   }
                   System.out.println();
                   if (check == 3)
                   {
                       System.out.print("=======Final Weight: ");
                       for (int j = 0; j < w[0].length; j++)
                       {
                           System.out.print(w[i][j]+" ");
                       }
                       break;
                   }
                   getDecisionFunction(w,x,(k+1)%4,check);
               }    
           }
           if (k == 2 || k == 3)
           {
               //System.out.println("k= "+k);
               if (d[i] >= 0)
               {
                    if (check > 0)
                        check = check - 1;
                    System.out.print("新的權重: ");
                    for (int j = 0; j < w[0].length; j++)
                    {
                        w[i][j] = w[i][j] - x[k][j];
                        System.out.print(w[i][j]+" ");
                    }
                    System.out.println();
                    getDecisionFunction(w,x,(k+1)%4,check);
               }
               else
               {
                   check = check + 1;
                   System.out.print("新的權重: ");
                   for (int j = 0; j < w[0].length; j++)
                   {
                       w[i][j] = w[i][j];
                       System.out.print(w[i][j]+" ");
                   }
                   System.out.println();
                   if (check == 3)
                   {
                       System.out.print("=======Final Weight: ");
                       for (int j = 0; j < w[0].length; j++)
                       {
                           System.out.print(w[i][j]+" ");
                       }
                       break;
                   }
                   getDecisionFunction(w,x,(k+1)%4,check);
               }
           }
           
       }
       return w;
   }
}
