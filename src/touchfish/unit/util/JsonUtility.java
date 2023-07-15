package touchfish.unit.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.lang.reflect.Type;

public class JsonUtility {

    /**
     * 将Json转换成指定类型对象
     */
    public static <T> T toObject(String json, Type type){
        return JSONUtil.parseObj(json).toBean(type);
    }

    /**
     * 将对象转换成Json
     */
    public static String toJson(Object obj){
        return JSONUtil.toJsonStr(obj);
    }

    /**
     * 合并两个对象到一个Json中，不可以出现相同字段
     */
    public static JSONObject merge(Object A, Object B) {
        String a = JSONUtil.toJsonStr(A);
        String b = JSONUtil.toJsonStr(B);
        return JSONUtil.parseObj(a.substring(0,a.length()-1)+","+b.substring(1));
    }

    /**
     * 将addition对象作为字段添加到source对象中，
     * 以 addition.getClass().getSimpleName().toLowerCase() 为新增字段名
     */
    public static JSONObject addInto(Object source, Object addition) {
        if (addition == null)return JSONUtil.parseObj(source);
        String a = JSONUtil.toJsonStr(source);
        String b = JSONUtil.toJsonStr(addition);
        return JSONUtil.parseObj(a.substring(0,a.length()-1)+","+addition.getClass().getSimpleName().toLowerCase()+":" +b+"}");
    }

    /**
     * 将addition对象作为字段添加到source对象中，
     * 以 fieldName 为新增字段名
     */
    public static JSONObject addInto(Object source, Object addition, String fieldName) {
        if (addition == null)return JSONUtil.parseObj(source);
        String a = JSONUtil.toJsonStr(source);
        String b = JSONUtil.toJsonStr(addition);
        return JSONUtil.parseObj(a.substring(0,a.length()-1)+",\""+fieldName+"\":" +b+"}");
    }

    /**
     * 将字符串作为字段添加到source对象中，
     * 以 fieldName 为新增字段名
     */
    public static JSONObject addInto(Object source, String str, String fieldName) {
        String a = JSONUtil.toJsonStr(source);
        return JSONUtil.parseObj(a.substring(0,a.length()-1)+",\""+fieldName+"\":\"" +str+"\"}");
    }

    static class UserInfo {
        public Integer aId = 3;
        public String name = "SB";
        public String content = "SBBB";
    }

    static class User {
        public Integer bid = 0;
        public Integer aId = 3;
    }

    public static void main(String[] args) {
        System.out.println(addInto(new User(),new UserInfo()));
        System.out.println(addInto(new User(),new UserInfo(),"rename_user"));
    }

}
