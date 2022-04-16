package ai.tree.data;
import java.util.HashMap;

//java-Cloneable介面
public class Data implements Cloneable
{
    //把資料的 (key, value) 放入HashMap
    private HashMap<String,String> feature = new HashMap<String, String>();
    private String result;

    public Data(HashMap<String,String> feature,String result)
    {
        this.feature = feature;
        this.result = result;
    }

    public HashMap<String, String> getFeature() 
    {
        return feature;
    }

    public String getResult() 
    {
        return result;
    }

    private void setFeature(HashMap<String, String> feature) 
    {
        this.feature = feature;
    }

    @Override
    //目的:需要一個新的物件來儲存當前物件
    //覆蓋clone()方法
    public Data clone()
    {
        Data object=null;
        try {
            object = (Data) super.clone();//得到需要複製的物件
            object.setFeature((HashMap<String, String>) this.feature.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return object;
    }
}