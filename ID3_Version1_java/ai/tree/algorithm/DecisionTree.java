package ai.tree.algorithm;
import ai.tree.data.Data;
import ai.tree.algorithm.TreeNode;

import java.math.BigDecimal;
import java.util.*;

public class DecisionTree 
{
    public TreeNode createTree(List<Data> dataList) 
    {
        //創建目前節點
        TreeNode<String, String, String> nowTreeNode = new TreeNode<String, String, String>();
        //目前節點的各個分之節點
        HashMap<String, TreeNode> featureDecisionMap = new HashMap<String, TreeNode>();

        //計算目前的分類結果各數, HashSet中的元素是唯一的
        Set<String> resultSet = new HashSet<String>();
        //Enhanced for loop
        for (Data data : dataList) 
        {
            resultSet.add(data.getResult());
        }

        //如果分類結果各數只有一類，返回目前節點
        if (resultSet.size() == 1) 
        {
            String resultClassify = resultSet.iterator().next();
            nowTreeNode.setResultNode(resultClassify);
            return nowTreeNode;
        }

        //如果数据集中特征为空，则选择整个集合中出现次数最多的分类，作为分类结果
        if (dataList.get(0).getFeature().size() == 0) {
            Map<String, Integer> countMap = new HashMap<String, Integer>();
            for (Data data :
                    dataList) {
                Integer num = countMap.get(data.getResult());
                if (num == null) {
                    countMap.put(data.getResult(), 1);
                } else {
                    countMap.put(data.getResult(), num + 1);
                }
            }

            String tmpResult = "";
            Integer tmpNum = 0;
            for (String res :
                    countMap.keySet()) {
                if (countMap.get(res) > tmpNum) {
                    tmpNum = countMap.get(res);
                    tmpResult = res;
                }
            }

            nowTreeNode.setResultNode(tmpResult);

            return nowTreeNode;
        }

        //寻找当前最优分类
        String bestLabel = chooseBestFeatureToSplit(dataList);

        //提取最优特征的所有可能值
        Set<String> bestLabelInfoSet = new HashSet<String>();
        for (Data data :
                dataList) {
            bestLabelInfoSet.add(data.getFeature().get(bestLabel));
        }

        //使用最优特征的各个特征值进行分类
        for (String labelInfo :
                bestLabelInfoSet) {
            for (Data data :
                    dataList) {
            }
            List<Data> branchDataList = splitDataList(dataList, bestLabel, labelInfo);

            //最优特征下该特征值的节点
            TreeNode branchTreeNode = createTree(branchDataList);
            featureDecisionMap.put(labelInfo, branchTreeNode);
        }

        nowTreeNode.setDecisionNode(bestLabel, featureDecisionMap);

        return nowTreeNode;
    }

    /**
     * 计算传入数据集中的最优分类特征
     *
     * @param dataList
     * @return int 最优分类特征的描述
     * @author ChenLuyang
     * @date 2019/2/21 14:12
     */
    public String chooseBestFeatureToSplit(List<Data> dataList) {
        //目前数据集中的特征集合
        Set<String> futureSet = dataList.get(0).getFeature().keySet();

        //未分类时的熵
        BigDecimal baseEntropy = calcShannonEnt(dataList);

        //熵差
        BigDecimal bestInfoGain = new BigDecimal("0");
        //最优特征
        String bestFeature = "";

        //按照各特征分类
        for (String future :
                futureSet) {
            //该特征分类后的熵
            BigDecimal futureEntropy = new BigDecimal("0");

            //该特征的所有特征值去重集合
            Set<String> futureInfoSet = new HashSet<String>();
            for (Data data :
                    dataList) {
                futureInfoSet.add(data.getFeature().get(future));
            }

            //按照该特征的特征值一一分类
            for (String futureInfo :
                    futureInfoSet) {
                List<Data> splitResultDataList = splitDataList(dataList, future, futureInfo);

                //分类后样本数占总样本数的比例
                BigDecimal tmpProb = new BigDecimal(splitResultDataList.size() + "").divide(new BigDecimal(dataList.size() + ""), 5, BigDecimal.ROUND_HALF_DOWN);

                //所占比例乘以分类后的样本熵，然后再进行熵的累加
                futureEntropy = futureEntropy.add(tmpProb.multiply(calcShannonEnt(splitResultDataList)));
            }

            BigDecimal subEntropy = baseEntropy.subtract(futureEntropy);

            if (subEntropy.compareTo(bestInfoGain) >= 0) {
                bestInfoGain = subEntropy;
                bestFeature = future;
            }
        }

        return bestFeature;
    }

    /**
     * 计算传入样本集的熵值
     *
     * @param dataList 样本集
     * @return java.math.BigDecimal 熵
     * @author ChenLuyang
     * @date 2019/2/22 9:41
     */
    public BigDecimal calcShannonEnt(List<Data> dataList) {
        //样本总数
        BigDecimal sumEntries = new BigDecimal(dataList.size() + "");
        //香农熵
        BigDecimal shannonEnt = new BigDecimal("0");
        //统计各个分类结果的样本数量
        HashMap<String, Integer> resultCountMap = new HashMap<String, Integer>();
        for (Data data : dataList) 
        {
            Integer dataResultCount = resultCountMap.get(data.getResult());
            if (dataResultCount == null) 
            {
                resultCountMap.put(data.getResult(), 1);
            } 
            else 
            {
                resultCountMap.put(data.getResult(), dataResultCount + 1);
            }
        }

        for (String resultCountKey : resultCountMap.keySet()) 
        {
            BigDecimal resultCountValue = new BigDecimal(resultCountMap.get(resultCountKey).toString());

            BigDecimal prob = resultCountValue.divide(sumEntries, 5, BigDecimal.ROUND_HALF_DOWN);
            shannonEnt = shannonEnt.subtract(prob.multiply(new BigDecimal(Math.log(prob.doubleValue()) / Math.log(2) + "")));
        }

        return shannonEnt;
    }

    /**
     * 根据某个特征的特征值，进行样本数据的划分，将划分后的样本数据集返回
     *
     * @param dataList 待划分的样本数据集
     * @param future   筛选的特征依据
     * @param info     筛选的特征值依据
     * @return java.util.List<ai.tree.data.Data> 按照指定特征值分类后的数据集
     * @author ChenLuyang
     * @date 2019/2/21 18:26
     */
    public List<Data> splitDataList(List<Data> dataList, String future, String info) {
        List<Data> resultDataList = new ArrayList<Data>();
        for (Data data :
                dataList) {
            if (data.getFeature().get(future).equals(info)) {
                Data newData = (Data) data.clone();
                newData.getFeature().remove(future);
                resultDataList.add(newData);
            }
        }

        return resultDataList;
    }


    
}