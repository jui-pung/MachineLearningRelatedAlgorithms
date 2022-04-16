package ai.tree.algorithm;

import ai.tree.data.Data;
import ai.tree.algorithm.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Start 
{
    /**
        {"age", "income", "student", "credit_rating" , "buys_computer"},
        1{"youth", "high", "no", "fair", "no"},
        2{"youth", "high", "no", "excellent", "no"},
        3{"middle_aged", "high", "no", "fair", "yes"},
        4{"senior", "medium", "no", "fair", "yes"},
        5{"senior", "low", "yes", "fair", "yes"},
        6{"senior", "low", "yes", "excellent", "no"},
        7{"middle_aged", "low", "yes", "excellent", "yes"},
        8{"youth", "medium", "no", "fair", "no"},
        9{"youth", "low", "yes", "fair", "yes"},
        10{"senior", "medium", "yes", "fair", "yes"},
        11{"youth", "medium", "yes", "excellent", "yes"},
        12{"middle_aged", "medium", "no", "excellent", "yes"},
        13{"middle_aged", "high", "yes", "fair", "yes"},
        14{"senior", "medium", "no", "excellent", "no"},
     */
    public static List<Data> createDataList()
	{

        String[] labels = new String[]{"age", "income", "student", "credit_rating"};

        List<Data> dataList = new ArrayList<Data>();

        HashMap<String,String> feature1 = new HashMap<String, String>();
        feature1.put(labels[0],"youth");
        feature1.put(labels[1],"high");
        feature1.put(labels[2],"no");
	feature1.put(labels[3],"fair");
        dataList.add(new Data(feature1,"no"));

        HashMap<String,String> feature2 = new HashMap<String, String>();
        feature2.put(labels[0],"youth");
        feature2.put(labels[1],"high");
        feature2.put(labels[2],"no");
	feature2.put(labels[3],"excellent");
        dataList.add(new Data(feature2,"no"));

        HashMap<String,String> feature3 = new HashMap<String, String>();
        feature3.put(labels[0],"middle_aged");
        feature3.put(labels[1],"high");
        feature3.put(labels[2],"no");
	feature3.put(labels[3],"fair");
        dataList.add(new Data(feature3,"yes"));

        HashMap<String,String> feature4 = new HashMap<String, String>();
        feature4.put(labels[0],"senior");
        feature4.put(labels[1],"medium");
        feature4.put(labels[2],"no");
	feature4.put(labels[3],"fair");
        dataList.add(new Data(feature4,"yes"));

        HashMap<String,String> feature5 = new HashMap<String, String>();
        feature5.put(labels[0],"senior");
        feature5.put(labels[1],"low");
        feature5.put(labels[2],"yes");
	feature5.put(labels[3],"fair");
        dataList.add(new Data(feature5,"yes"));

	HashMap<String,String> feature6 = new HashMap<String, String>();
        feature6.put(labels[0],"senior");
        feature6.put(labels[1],"low");
        feature6.put(labels[2],"yes");
	feature6.put(labels[3],"excellent");
        dataList.add(new Data(feature6,"no"));
		
	HashMap<String,String> feature7 = new HashMap<String, String>();
        feature7.put(labels[0],"middle_aged");
        feature7.put(labels[1],"low");
        feature7.put(labels[2],"yes");
	feature7.put(labels[3],"excellent");
        dataList.add(new Data(feature7,"yes"));
		
	HashMap<String,String> feature8 = new HashMap<String, String>();
        feature8.put(labels[0],"youth");
        feature8.put(labels[1],"medium");
        feature8.put(labels[2],"no");
	feature8.put(labels[3],"fair");
        dataList.add(new Data(feature8,"no"));
		
	HashMap<String,String> feature9 = new HashMap<String, String>();
        feature9.put(labels[0],"youth");
        feature9.put(labels[1],"low");
        feature9.put(labels[2],"yes");
	feature9.put(labels[3],"fair");
        dataList.add(new Data(feature9,"yes"));
		
	HashMap<String,String> feature10 = new HashMap<String, String>();
        feature10.put(labels[0],"senior");
        feature10.put(labels[1],"low");
        feature10.put(labels[2],"yes");
	feature10.put(labels[3],"fair");
        dataList.add(new Data(feature10,"yes"));
		
	HashMap<String,String> feature11 = new HashMap<String, String>();
        feature11.put(labels[0],"youth");
        feature11.put(labels[1],"low");
        feature11.put(labels[2],"yes");
	feature11.put(labels[3],"excellent");
        dataList.add(new Data(feature11,"yes"));
		
	HashMap<String,String> feature12 = new HashMap<String, String>();
        feature12.put(labels[0],"middle_aged");
        feature12.put(labels[1],"low");
        feature12.put(labels[2],"no");
	feature12.put(labels[3],"excellent");
        dataList.add(new Data(feature12,"yes"));
		
	HashMap<String,String> feature13 = new HashMap<String, String>();
        feature13.put(labels[0],"middle_aged");
        feature13.put(labels[1],"yes");
        feature13.put(labels[2],"yes");
	feature13.put(labels[3],"fair");
        dataList.add(new Data(feature13,"yes"));
		
	HashMap<String,String> feature14 = new HashMap<String, String>();
        feature14.put(labels[0],"senior");
        feature14.put(labels[1],"low");
        feature14.put(labels[2],"no");
	feature14.put(labels[3],"excellent");
        dataList.add(new Data(feature14,"no"));

        return dataList;
    }

    public static void main(String[] args) {
        DecisionTree decisionTree = new DecisionTree();

        //使用测试样本生成决策树
        TreeNode tree = decisionTree.createTree(createDataList());

        //展示决策树
        System.out.println(tree.showTree());
    }
}