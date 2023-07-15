package touchfish.unit.util.script;

import touchfish.unit.util.JsonUtility;
import touchfish.unit.util.ReflectionUtility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;

public class ScriptGenerationUtility {

    /**
     * 获取创建符合格式的实体类的js方法
     */
    public static String objectCreator_JS(String beforeRowStr, Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder res = new StringBuilder(beforeRowStr + "Create" + clazz.getSimpleName() + "(");
        for (Field field : fields) {
            res.append(field.getName()).append(",");
        }
        res.deleteCharAt(res.length() - 1);
        res.append("){\n"+ beforeRowStr +"\treturn {");
        for (Field field : fields) {
            res.append(field.getName()).append(",");
        }
        res.deleteCharAt(res.length() - 1);
        res.append("};\n"+ beforeRowStr +"}");
        return res.toString();
    }

    /**
     * 获取创建符合格式的实体类的js方法
     */
    public static String objectCreator_JS(Class clazz){
        return objectCreator_JS("\t",clazz);
    }

    /**
     * 获取创建符合格式的实体类的ts类型
     */
    public static String objectCreator_TS(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder res = new StringBuilder("export class " + clazz.getSimpleName() + " {\n\tconstructor(");
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()))
                res.append(field.getName()).append(":").append(translateClassName_TS(field.getType())).append("=").append(translateClassValue(field.getType())).append(',');
        }
        res.deleteCharAt(res.length() - 1);
        res.append("){\n");
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()))
                res.append("\t\tthis.").append(field.getName()).append("=").append(field.getName()).append(";\n");
        }
        res.append("\t}\n");
        res.append("\tpublic static __custom_name:string=").append("\"").append(clazz.getSimpleName()).append("\";\n");
        res.append("\tpublic static __nonull_instance: " + clazz.getSimpleName() + " = new ").append(clazz.getSimpleName()).append("(");
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()))
                res.append(translateClassValue(field.getType())).append(',');
        }
        res.deleteCharAt(res.length()-1);
        res.append(");\n");
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.isStatic(field.getModifiers()))
                res.append("\tpublic ").append(field.getName()).append("!:").append(translateClassName_TS(field.getType())).append(";\n");
            else if (Modifier.isFinal(field.getModifiers())){

                String finalValue = translateClassValue(field.getType());
                String finalClass = translateClassName_TS(field.getType());

                if (finalValue.equals("{}")) {
                    try {
                        finalValue = JsonUtility.toJson(field.get(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        finalValue = field.get(null).toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (finalClass.equals("any")) {
                    if (clazz.getSimpleName().equals(field.getType().getSimpleName())) {
                        finalClass = clazz.getSimpleName();
                    }
                }

                res.append("\tpublic static ")
                        //字段名
                        .append(field.getName())
                        //类型
                        .append(":").append(finalClass)
                        //创建默认值
                        .append("=").append(finalValue).append(";\n");
            }
        }

        res.append("}\n");
        return res.toString();
    }

    public static void main(String[] args) {
        for (Class clazz : ReflectionUtility.getAllClassInPackage("touchfish.unit.math")) {
            System.out.println(objectCreator_TS(clazz));
        }
    }

    /**
     * 获取创建符合格式的实体类的ts类型
     * 及附属视图
     */
    public static String objectCreatorAndView_TS(Class clazz){
        String[] src = objectCreator_TS(clazz).split("\tpublic static __nonull_instance:");
        src[1] = "\tpublic static __nonull_instance:" + src[1];
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder res = new StringBuilder(src[0]);
        res.append("\tpublic static __router_permit:Array<number>=[99];\n");
        res.append("\tpublic static __insert_permit:Array<number>=[99];\n");
        res.append("\tpublic static __update_permit:Array<number>=[99];\n");
        res.append("\tpublic static __delete_permit:Array<number>=[99];\n");
        res.append("\tpublic static __show_mapper:{\n");
        for (Field field : fields) {
            res.append("\t\t").append(field.getName()).append(":EntityViewSettings,\n");
        }
        res.append("\t}={\n");
        for (Field field : fields) {
            String ann = (String) ReflectionUtility.getAnnotationParameterValue(field,"io.swagger.annotations.ApiModelProperty","value");
            if (ann != null) {
                res.append("\t\t").append(field.getName()).append(" : new EntityViewSettings(false,false,").append(ann.contains("状态")||ann.contains("类型")||ann.contains("性别")?"'enum_number'":"'default'").append(",").append("'").append(ann).append("',{'默认':0},[99],[99]").append("),\n");
            } else {
                res.append("\t\t").append(field.getName()).append(" : new EntityViewSettings(false,false,").append("'default'").append(",").append("'',{'默认':0},[99],[99]").append("),\n");
            }
        }
        res.append("\t};\n");
        res.append(src[1]);
        return res.toString();
    }

    /**
     * 将java类型转换成适用于TS代码的类型字符串
     */
    public static String translateClassName_TS(Class clazz) {
        if (clazz == Double.class){
            return "number";
        }
        else if (clazz == Integer.class){
            return "number";
        }
        else if (clazz == Float.class){
            return "number";
        }
        else if (clazz == Boolean.class){
            return "boolean";
        }
        else if (clazz == double.class){
            return "number";
        }
        else if (clazz == int.class){
            return "number";
        }
        else if (clazz == float.class){
            return "number";
        }
        else if (clazz == boolean.class){
            return "boolean";
        }
        else if (clazz == String.class){
            return "string";
        }
        else if (clazz == BigDecimal.class){
            return "number";
        }
        else if (clazz == Date.class){
            return "string";
        }
        System.err.println("unknown type " + clazz.getSimpleName());
        return "any";
    }

    /**
     * 根据输入的类型获取类型默认值的字符串
     */
    public static String translateClassValue(Class clazz) {
        if (clazz == Double.class){
            return "0.0";
        }
        else if (clazz == Integer.class){
            return "0";
        }
        else if (clazz == Float.class){
            return "0.0";
        }
        else if (clazz == Boolean.class){
            return "false";
        }
        else if (clazz == double.class){
            return "0.0";
        }
        else if (clazz == int.class){
            return "0";
        }
        else if (clazz == float.class){
            return "0.0";
        }
        else if (clazz == boolean.class){
            return "false";
        }
        else if (clazz == String.class){
            return "''";
        }
        else if (clazz == BigDecimal.class){
            return "0.0";
        }
        else if (clazz == Date.class){
            return "''";
        }
        return "{}";
    }
}
