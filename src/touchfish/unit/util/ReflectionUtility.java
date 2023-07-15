package touchfish.unit.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionUtility {

    public static String getPackageName(Class clazz){
        return clazz.getPackage().getName();
    }

    public static Object getAnnotationParameterValue(AnnotatedElement target, String annotationClassFullName, String paramName){
        try {
            Class annClass = getClassByName(annotationClassFullName);
            if (annClass != null) {
                return annClass.getMethod(paramName).invoke(target.getAnnotation(annClass));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getAnnotationParameterValue(AnnotatedElement target, Class annotationClazz, String paramName){
        try {
            return annotationClazz.getMethod(paramName).invoke(target.getAnnotation(annotationClazz));
        } catch (Exception e) {
            return null;
        }
    }

    public static Class getClassByName(String classFullName){
        try {
            return Class.forName(classFullName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static String getBuildRootPath(String localPath) {
        String packageName = getPackageName(ReflectionUtility.class);
        return localPath.substring(0,localPath.indexOf(packageName.substring(0,packageName.indexOf("."))));
    }

    /**
     * Field
     */
    public static List<Field> getAllFieldByAnnotationInClass(Class target, Class annotation){
        List<Field> res = new ArrayList<>();
        for (Field field : target.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                res.add(field);
            }
        }
        return res;
    }

    /**
     * Method
     */
    public static List<Method> getAllMethodByAnnotation(Class clazzAnnotation, Class methodAnnotation){
        return getAllMethodByAnnotationInClasses(getAllClassByAnnotation(clazzAnnotation),methodAnnotation);
    }
    public static List<Method> getAllMethodByAnnotationInClass(Class target, Class annotation){
        List<Method> res = new ArrayList<>();
        for (Method method : target.getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                res.add(method);
            }
        }
        return res;
    }
    public static List<Method> getAllMethodByAnnotationInClasses(List<Class> targets, Class annotation){
        List<Method> res = new ArrayList<>();
        for (Map.Entry<Class, List<Method>> classMethodEntry : getAllMethodTypedByClassByAnnotation(targets, annotation).entrySet()) {
            res.addAll(classMethodEntry.getValue());
        }
        return res;
    }
    public static List<Method> getAllMethodByAnnotationInPackage(String packageName, Class clazzAnnotation, Class methodAnnotation){
        return getAllMethodByAnnotationInClasses(getAllClassByAnnotationInPackage(packageName,clazzAnnotation),methodAnnotation);
    }
    public static Map<Class, List<Method>> getAllMethodTypedByClassByAnnotation(List<Class> targets, Class annotation){
        Map<Class, List<Method>> res = new HashMap<>();
        for (Class target : targets) {
            for (Method method : target.getMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    if (!res.containsKey(target))res.put(target, new ArrayList<>());
                    res.get(target).add(method);
                }
            }
        }
        return res;
    }
    public static Map<Class, List<Method>> getAllMethodTypedByClassByAnnotationInPackage(String packageName, Class clazzAnnotation, Class methodAnnotation){
        return getAllMethodTypedByClassByAnnotation(getAllClassByAnnotationInPackage(packageName, clazzAnnotation),methodAnnotation);
    }
    public static Map<Class, List<Method>> getAllMethodMapByAnnotationAnnotationInClass(Class target, Class annotation){
        Map<Class, List<Method>> res = new HashMap<>();
        for (Method method : target.getMethods()) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation mhAnn : annotations) {
                Class mhAnnClass = mhAnn.annotationType();
                if (mhAnnClass.isAnnotationPresent(annotation)) {
                    if (!res.containsKey(mhAnnClass)) {
                        res.put(mhAnnClass, new LinkedList<>());
                    }
                    res.get(mhAnnClass).add(method);
                }
            }
        }
        return res;
    }

    /**
     * Class
     */
    public static ArrayList<Class> getAllClass() {
        List<String> classNames = getAllClassName();
        ArrayList<Class> list = new ArrayList<>();
        for(String name : classNames){
            try {
                list.add(Class.forName(name));
            } catch (ClassNotFoundException e) {
                System.err.println("load class from name failed:" + name + e.getMessage());
            }
        }
        return list;
    }
    public static ArrayList<Class> getAllClassInPackage(String packagename) {
        List<String> classNames = getAllClassNameInPackage(packagename);
        ArrayList<Class> list = new ArrayList<>();
        for(String name : classNames){
            try {
                list.add(Class.forName(name));
            } catch (ClassNotFoundException e) {
                System.err.println("load class from name failed:" + name + e.getMessage());
            }
        }
        return list;
    }
    public static List<Class> getAllClassByAnnotation(Class annotation) {
        List<Class> list = new ArrayList<Class>();
        for(Class c : getAllClass()) {
            if(c.isAnnotationPresent(annotation))
                list.add(c);
        }
        return list;
    }
    public static List<Class> getAllClassByAnnotationInClasses(List<Class> classes, Class annotation) {
        List<Class> list = new ArrayList<>();
        for(Class c : classes) {
            if(c.isAnnotationPresent(annotation))
                list.add(c);
        }
        return list;
    }
    public static List<Class> getAllClassByAnnotationInPackage(String packageName, Class annotation) {
        List<Class> list = new ArrayList<>();
        for(Class c : getAllClassInPackage(packageName)) {
            if(c.isAnnotationPresent(annotation))
                list.add(c);
        }
        return list;
    }
    public static List<Class> getAllClassByInterfaceInClasses(List<Class> targets, Class interfaceType){
        List<Class> res = new ArrayList<>();
        for (Class<?> clazz : targets) {
            if (interfaceType.isAssignableFrom(clazz)) {
                res.add(clazz);
            }
        }
        return res;
    }

    /**
     * Class Name
     */
    public static List<String> getAllClassNameInPackage(String packageName){
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                String clazzPath = url.getPath();
                String buildPath = getBuildRootPath(clazzPath);
                fileNames = getAllClassNameByPath(clazzPath,buildPath);
            } else if (type.equals("jar")) {
                try{
                    JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    fileNames = getAllClassNameInJarPackage(jarFile,packagePath);
                }catch (java.io.IOException e){
                    throw new RuntimeException("open Package URL failed："+e.getMessage());
                }
            }else{
                throw new RuntimeException("file system not support! cannot load MsgProcessor！");
            }
        }
        return fileNames;
    }
    public static List<String> getAllClassName() {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packageName = getPackageName(ReflectionUtility.class);
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                String buildPath = getBuildRootPath(url.getPath());
                fileNames = getAllClassNameByPath(buildPath, buildPath);
            } else if (type.equals("jar")) {
                try{
                    JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    fileNames = getAllClassNameInJar(jarFile);
                }catch (java.io.IOException e){
                    throw new RuntimeException("open Package URL failed："+e.getMessage());
                }

            }else{
                throw new RuntimeException("file system not support! cannot load MsgProcessor！");
            }
        }
        return fileNames;
    }
    public static List<String> getAllClassNameByPath(String path, String rootPath) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(path);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getAllClassNameByPath(childFile.getPath(), rootPath));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(rootPath.length()-1, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }
        return myClassName;
    }
    public static List<String> getAllClassNameInJar(JarFile jarFile) {
        List<String> myClassName = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                //LOG.info("entrys jarfile:"+entryName);
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                    //LOG.debug("Find Class :"+entryName);
                }
            }
        } catch (Exception e) {

            throw new RuntimeException("发生异常:"+e.getMessage());
        }
        return myClassName;
    }
    public static List<String> getAllClassNameInJarPackage(JarFile jarFile, String packagePath) {
        List<String> myClassName = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                //LOG.info("entrys jarfile:"+entryName);
                if (entryName.endsWith(".class") && entryName.contains(packagePath)) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                    //LOG.debug("Find Class :"+entryName);
                }
            }
        } catch (Exception e) {

            throw new RuntimeException("发生异常:"+e.getMessage());
        }
        return myClassName;
    }
    public static Class[] getTClasses(Class ori) {
        Class[] res = new Class[0];
        Type genericSuperclass = ori.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                res = new Class[actualTypeArguments.length];
                for (int i = 0; i < res.length; i++) {
                    res[i] =  (Class) actualTypeArguments[i];
                }
            }
        }
        return res;
    }
    public static Class getTClass(Class ori) {
        return getTClasses(ori)[0];
    }
}

