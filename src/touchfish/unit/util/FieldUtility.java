package touchfish.unit.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

public class FieldUtility {

    /**
     * 将Map转换为指定类型对象，
     * 目标类型必须有默认构造函数
     * @param map 字段键值对
     * @param clazz 目标对象类型
     * @return 返回转换后的实例化对象
     */
    public static <T> T toObject(Map<String, Object> map, Class<T> clazz) throws Exception {
        T obj = clazz.newInstance();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            Field field = clazz.getDeclaredField(key);
            field.setAccessible(true);
            field.set(obj, value);
        }

        return obj;
    }

    /**
     * 等于克隆，
     * 处理报错，不用写try-catch了
     */
    public static <T> T clone(T obj) {
        try {
            return (T) obj.getClass().getDeclaredMethod("clone").invoke(obj);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制字段浅层数据到目标对象相同名称的字段中，输入对象类型任意
     */
    public static void copy(Object source, Object target) throws Exception {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName()) && !Modifier.isStatic(sourceField.getModifiers())
                        && sourceField.getType().equals(targetField.getType())) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    targetField.set(target, sourceField.get(source));
                }
            }
        }
    }

    /**
     * 复制字段深层（包括引用类型）数据到目标对象相同名称的字段中，输入对象类型任意
     */
    public static void copyDeep(Object source, Object target) throws Exception {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName()) && !Modifier.isStatic(sourceField.getModifiers())
                        && sourceField.getType().equals(targetField.getType())) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);

                    if (isPrimitiveOrString(sourceField.getType())) {
                        targetField.set(target, sourceField.get(source));
                    } else {
                        Object sourceObject = sourceField.get(source);
                        Object targetObject = targetField.get(target);
                        if (sourceObject == null) {
                            targetField.set(target, null);
                        } else {
                            targetField.set(target, sourceObject.getClass().newInstance());
                            copyDeep(sourceObject, targetObject);
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断类型是否为基础类型或字符串
     */
    private static boolean isPrimitiveOrString(Class<?> type) {
        return type.isPrimitive() || type == String.class;
    }

    /**
     * 判断类型是否存在默认构造函数
     */
    public static boolean hasDefaultConstructor(Class clazz) {
        try {
            clazz.getConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }


    /**
     * 等于新建，
     * 处理报错，不用写try-catch了
     */
    public static <T> T newInstance(Class<T> clazz){
        if (hasDefaultConstructor(clazz)) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 创建一个填充引用类型默认值的对象
     */
    public static <T> T createFilledInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = newInstance(clazz);
        if (instance != null) {
            return (T) fillNewInstance(instance);
        }
        return null;
    }

    /**
     * 对象中的引用类型字段填充默认值
     */
    public static <T> T fillNewInstance(T target){
        for (Field field : target.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            fillDefaultField(target, field, false);
        }
        return target;
    }

    /**
     * 引用类型字段填充默认值
     */
    public static void fillDefaultField(Object target, Field field, boolean createByConstructor){
        Class fieldType = field.getType();
        try {
            if (fieldType == Double.class){
                field.set(target, new Double(0.0));
            }
            else if (fieldType == Integer.class){
                field.set(target, new Integer(0));
            }
            else if (fieldType == String.class){
                field.set(target, "");
            }
            else if (fieldType == Float.class){
                field.set(target, new Float(0.0f));
            }
            else if (fieldType == Date.class){
                field.set(target, new Date());
            }
            else if (fieldType == BigDecimal.class){
                field.set(target, new BigDecimal(0.0));
            }
            else if (createByConstructor) {
                field.set(target, newInstance(fieldType));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查字符串是否安全
     */
    public static boolean sensitive(String ... checkStrs){
        for (String sensitiveText : new String[]{
                "傻" + "逼" + "妈" + "爸" + "爹" + "娘" + "操" + "色情" + "18禁" + "台湾" + "台独" + "西藏" + "藏族"
        }) {
            for (String s : checkStrs) {
                if (s == null) continue;
                if (s.contains(sensitiveText)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断数组中的对象是否为指定类型
     */
    public static boolean arrayInstanceof(Object array,Class itemClass){
        if (array instanceof Collection){
            return ReflectionUtility.getTClass(array.getClass()).equals(itemClass);
        }else {
            return false;
        }
    }

    /**
     * 根据名称获取对象的字段
     */
    public static Object getField(Object target, String name){
        Field f = null;
        try {
            f = target.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
        f.setAccessible(Boolean.TRUE);
        try {
            return f.get(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
