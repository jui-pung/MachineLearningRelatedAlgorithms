package ai.tree.algorithm;
import java.math.BigDecimal;
import java.util.*;
//泛型類別,將變數型態設為通用類型
//L:每一個特徵描述資訊的型態, F:特徵的型態, R:最終分類結果的型態
public class TreeNode<L, F, R>
{
        //節點最好特徵的描述資訊
        private L label;
        //根據不同特徵做出的決策
        //<F(特徵值),TreeNode(該特徵做出的決策節點)>
        private Map<F, TreeNode> featureDecisionMap;
        //是否為最終分類節點
        private boolean isFinal;
        //最終分類結果
        private R resultClassify;

        //設定葉子節點
        public void setResultNode(R resultClassify) 
        {
            this.isFinal = true;
            this.resultClassify = resultClassify;
        }

        //設定分支節點
        public void setDecisionNode(L label, Map<F, TreeNode> featureDecisionMap) {
            this.isFinal = false;
            this.label = label;
            this.featureDecisionMap = featureDecisionMap;
        }

        //展示目前節點樹結構
        public String showTree() {
            HashMap<String, String> treeMap = new HashMap<String, String>();
            if (isFinal) 
            {
                String key = "result";
                R value = resultClassify;
                treeMap.put(key, value.toString());
            } 
            else 
            {
                String key = label.toString();
                HashMap<F, String> showFutureMap = new HashMap<F, String>();
                for (F f : featureDecisionMap.keySet()) 
                {
                    showFutureMap.put(f, featureDecisionMap.get(f).showTree());
                }
                String value = showFutureMap.toString();
                treeMap.put(key, value);
            }
            return treeMap.toString();
        }

        public L getLabel() 
        {
            return label;
        }

        public Map<F, TreeNode> getFeatureDecisionMap() 
        {
            return featureDecisionMap;
        }

        public R getResultClassify() 
        {
            return resultClassify;
        }

        public boolean getFinal() 
        {
            return isFinal;
        }
}