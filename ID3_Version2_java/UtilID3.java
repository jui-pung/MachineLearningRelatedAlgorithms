import java.util.*;
import java.util.Comparator;
import java.lang.String;
public class UtilID3 {
    TreeNode root;
    private boolean[] flag;
    //訓練集
    private Object[] trainArrays;
    //節點索引
    private int nodeIndex;
    public static void main(String[] args)
    {
        //初始化訓練集
        Object[] arrays = new Object[]{
                new String[]{"youth", "high", "no", "fair", "no"},
                new String[]{"youth", "high", "no", "excellent", "no"},
                new String[]{"middle_aged", "high", "no", "fair", "yes"},
                new String[]{"senior", "medium", "no", "fair", "yes"},
                new String[]{"senior", "low", "yes", "fair", "yes"},
                new String[]{"senior", "low", "yes", "excellent", "no"},
                new String[]{"middle_aged", "low", "yes", "excellent", "yes"},
                new String[]{"youth", "medium", "no", "fair", "no"},
                new String[]{"youth", "low", "yes", "fair", "yes"},
                new String[]{"senior", "medium", "yes", "fair", "yes"},
                new String[]{"youth", "medium", "yes", "excellent", "yes"},
                new String[]{"middle_aged", "medium", "no", "excellent", "yes"},
                new String[]{"middle_aged", "high", "yes", "fair", "yes"},
                new String[]{"senior", "medium", "no", "excellent", "no"}};
        UtilID3 ID3Tree = new UtilID3();
        ID3Tree.create(arrays, 4);
    }
    
    //創建
    public void create(Object[] arrays, int index)
    {
        this.trainArrays = arrays;
        initial(arrays, index);
        createDTree(arrays);
        System.out.println("==========DecisionTree==========");
        printDTree(root);
    }
    
    //初始化
    public void initial(Object[] dataArray, int index)
    {
        this.nodeIndex = index;
        
        //flag陣列數據初始化
        this.flag = new boolean[((String[])dataArray[0]).length];
        for (int i = 0; i<this.flag.length; i++)
        {
            if (i == index)
            {
                this.flag[i] = true;
            }
            else
            {
                this.flag[i] = false;
            }
        }
    }
    
    //建立決策樹
    public void createDTree(Object[] arrays)
    {
        Object[] ob = getMaxGain(arrays);
        if (this.root == null)
        {
            this.root = new TreeNode();
            root.parent = null;
            root.parentAttribute = null;
            root.attributes = getAttributes(((Integer)ob[1]).intValue());
            root.nodeName = getNodeName(((Integer)ob[1]).intValue());
            root.childNodes = new TreeNode[root.attributes.length];
            insert(arrays, root);
        }
    }
    
    //插入決策樹
    public void insert(Object[] arrays, TreeNode parentNode)
    {
        String[] attributes = parentNode.attributes;
        for (int i = 0; i < attributes.length; i++)
        {
            Object[] Arrays = pickUpAndCreateArray(arrays, attributes[i],getNodeIndex(parentNode.nodeName));
            Object[] info = getMaxGain(Arrays);
            double gain = ((Double)info[0]).doubleValue();
            if (gain != 0)
            {
                int index = ((Integer)info[1]).intValue();
                TreeNode currentNode = new TreeNode();
                currentNode.parent = parentNode;
                currentNode.parentAttribute = attributes[i];
                currentNode.attributes = getAttributes(index);
                currentNode.nodeName = getNodeName(index);
                currentNode.childNodes = new TreeNode[currentNode.attributes.length];
                parentNode.childNodes[i] = currentNode;
                insert(Arrays, currentNode);
            }
            else
            {
                TreeNode leafNode = new TreeNode();
                leafNode.parent = parentNode;
                leafNode.parentAttribute = attributes[i];
                leafNode.attributes = new String[0];
                leafNode.nodeName = getLeafNodeName(Arrays);
                leafNode.childNodes = new TreeNode[0];
                parentNode.childNodes[i] = leafNode;
            }
        }
    }
    

    
    //剪取數組
    public Object[] pickUpAndCreateArray(Object[] arrays, String attribute, int index)
    {
        List<String[]> list = new ArrayList<String[]>();
        for (int i = 0; i < arrays.length; i++)
        {
            String[] strs = (String[])arrays[i];
            if (strs[index].equals(attribute))
            {
                list.add(strs);
            }
        }
        return list.toArray();
    }
    
    //取得節點名
    public String getNodeName(int index)
    {
        String[] strs = new String[]{"age", "income", "student", "credit_rating" , "buys_computer"};
        for (int i = 0; i < strs.length; i++)
        {
            if (i == index)
            {
                System.out.println("節點--> "+strs[i]);
                return strs[i];
            }
        }
        return null;
    }
    
    //class分類結果都相同,取得葉子節點名稱
    public String getLeafNodeName(Object[] arrays)
    {
        if (arrays != null && arrays.length > 0)
        {
            String[] strs = (String[])arrays[0];
            return strs[nodeIndex];
        }
        return null;
    }
    
    //取得節點索引
    public int getNodeIndex(String name)
    {
        String[] strs = new String[]{"age", "income", "student", "credit_rating" , "buys_computer"};
        for (int i = 0; i < strs.length; i++)
        {
            if (name.equals(strs[i]))
            {
                return i;
            }
        }
        return -1;
    }
    
    //用TreeSet取得Attributes組
    public String[] getAttributes(int index)
    {
        @SuppressWarnings("unchecked")
        TreeSet<String> set = new TreeSet<String>(new Comparisons());
        //傳進來的表的每筆資料
        for (int i = 0; i<this.trainArrays.length; i++)
        {
            String[] strs = (String[])this.trainArrays[i];
            set.add(strs[index]);
        }
        String[] result = new String[set.size()];
        return set.toArray(result);    
    }
    
    //找出最大的Gain
    public Object[] getMaxGain(Object[] arrays)
    {
        Object[] result = new Object[2];
        double gain = 0;
        int index = -1; //代表哪個Attribute, ex.0代表"age",1代表"income",2代表"student".....
        for (int i = 0; i<this.flag.length; i++)
        {
            if (!this.flag[i])
            {
                double value = gain(arrays, i);
                if (gain < value)
                {
                    gain = value;
                    index = i;
                }
            }
        }
        result[0] = gain;
        if ((Double)result[0] != 0.0)
        {
            System.out.println("Highest Information Gain= "+result[0]);
        }
        result[1] = index;
        //System.out.println("result[1]"+result[1]);
        if (index != -1)
        {
            this.flag[index] = true;
        }
        return result;
    }
    
    //計算Gain
    public double gain(Object[] arrays, int index)
    {
        String[] playBalls = getAttributes(this.nodeIndex);
        //counts用來計算同類裡面同屬性的個數
        int[] counts = new int[playBalls.length];  //playBalls.length是最後分類結果有幾類
        for (int i = 0; i<counts.length; i++)
        {
            counts[i] = 0;
        }
        //計算同類裡面同屬性的個數
        for (int i = 0; i<arrays.length; i++)
        {
            String[] strs = (String[])arrays[i];
            for (int j = 0; j<playBalls.length; j++)
            {    
                if (strs[this.nodeIndex].equals(playBalls[j]))
                {
                    counts[j]++;
                }
            }
        }
        //計算目前決策樹的整體亂度Info(D)
        double entropyS = 0;
        for (int i = 0;i <counts.length; i++)
        {
            entropyS = entropyS + Entropy.getEntropy(counts[i], arrays.length);
        }
        
        //計算目前分支下特徵的整體亂度ex.Info下標age(D)
        String[] attributes = getAttributes(index);
        double total = 0;
        for (int i = 0; i<attributes.length; i++)
        {
            total = total + entropy(arrays, index, attributes[i], arrays.length);
        }
        //回傳Gain值
        //System.out.println("=========="+(entropyS - total));
        return entropyS - total;
        
    }
    
    //計算分支下各個特徵的Entropy
    public double entropy(Object[] arrays, int index, String attribute, int totals)
    {
        String[] playBalls = getAttributes(this.nodeIndex);
        int[] counts = new int[playBalls.length];
        for (int i = 0; i < counts.length; i++)
        {
            counts[i] = 0;
        }
        
        for (int i = 0; i < arrays.length; i++)
        {
            String[] strs = (String[])arrays[i];
            if (strs[index].equals(attribute))
            {
                for (int k = 0; k<playBalls.length; k++)
                {
                    if (strs[this.nodeIndex].equals(playBalls[k]))
                    {
                        counts[k]++;
                    }
                }
            }
        }
        
        int total = 0;  //分支內不同類別各數(ex.在age="youth"裡,Class:buys_computer="yes"的total=2)
        double entropy = 0;  //不同分支的entropy
        for (int i = 0; i < counts.length; i++)
        {
            total = total +counts[i];
        }
        
        for (int i = 0; i < counts.length; i++)
        {
            entropy = entropy + Entropy.getEntropy(counts[i], total);
        }
        return Entropy.getProbability(total, totals)*entropy;
    }
    
    //輸出
    public void printDTree(TreeNode node)
    {
        //System.out.println("======");
        if(node.nodeName.equals("yes") || node.nodeName.equals("no"))
        {
            System.out.print("Final Result: ");
        }
        System.out.println(node.nodeName);
        if(node.nodeName.equals("yes") || node.nodeName.equals("no"))
        {
            System.out.println();
        }
        TreeNode[] childs = node.childNodes;
        for (int i = 0; i < childs.length; i++)
        {
            if (childs[i] != null)
            {
                System.out.println("If "+node.nodeName+"="+childs[i].parentAttribute);
                printDTree(childs[i]);
            }
            
        }
        
    }
}