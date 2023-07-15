package touchfish.unit.util.script;

import java.lang.reflect.Method;
import java.util.List;
import static touchfish.unit.util.ReflectionUtility.*;
import static touchfish.unit.util.script.ScriptGenerationUtility.*;
public class WebSpringBootScriptGenerationUtility {

    /**
     * 获取所有POST接口
     */
    public static List<Method> getPostMethods(String controllerPackage){
        return getAllMethodByAnnotationInPackage(
                controllerPackage,
                getClassByName("org.springframework.web.bind.annotation.RestController"),
                getClassByName("org.springframework.web.bind.annotation.PostMapping"));
    }

    /**
     * 生成全部接口调用ts代码
     */
    public static String interfaceMap_TS(String controllerPackage) {
        List<Method> methods = getPostMethods(controllerPackage);
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("export type post = \n");
        for (Method method : methods) {

            Class parent = method.getDeclaringClass();

            String postStr =
                    ((String[]) getAnnotationParameterValue(
                            method,
                            "org.springframework.web.bind.annotation.PostMapping",
                            "value"))[0];

            String requestStr =
                    ((String[]) getAnnotationParameterValue(
                            parent,
                            "org.springframework.web.bind.annotation.RequestMapping",
                            "value"))[0];

            jsonStr.append("\"" + requestStr + postStr + "\"\n|");
        }
        jsonStr.deleteCharAt(jsonStr.length()-1);
        jsonStr.deleteCharAt(jsonStr.length()-1);
        jsonStr.append(";\n");
        return jsonStr.toString();
    }

    /**
     * 生成全部接口调用js代码
     */
    public static String interfaceMap_JS(String controllerPackage) {
        List<Method> methods = getPostMethods(controllerPackage);
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("{\n");
        for (Method method : methods) {
            Class parent = method.getDeclaringClass();

            String postStr =
                    ((String[]) getAnnotationParameterValue(
                            method,
                            "org.springframework.web.bind.annotation.PostMapping",
                            "value"))[0];

            String requestStr =
                    ((String[]) getAnnotationParameterValue(
                            parent,
                            "org.springframework.web.bind.annotation.RequestMapping",
                            "value"))[0];

            jsonStr.append("\t\t" + requestStr + postStr.replace("/","_") + ":\"" + requestStr + postStr + "\",\n");
        }
        jsonStr.append("}\n");
        return jsonStr.toString();
    }

    /**
     * 获取全部创建符合格式的实体类的js类型
     */
    public static String entityCreators_JS(String entityPackage) {
        List<Class> entitys = getAllClassByAnnotationInPackage(entityPackage, getClassByName("lombok.Data"));
        StringBuilder jsonStr = new StringBuilder();
        for (Class entity : entitys) {
            jsonStr.append(objectCreator_JS(entity));
            jsonStr.append(",\n");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 2);
        return jsonStr.toString();
    }

    /**
     * 获取全部创建符合格式的实体类的ts类型
     */
    public static String entityCreators_TS(String entityPackage) {
        List<Class> entitys = getAllClassByAnnotationInPackage(entityPackage, getClassByName("lombok.Data"));
        StringBuilder jsonStr = new StringBuilder();
        for (Class entity : entitys) {
            jsonStr.append(objectCreatorAndView_TS(entity));
        }
        jsonStr.append("export const entityTypes = [");
        for (Class entity : entitys) {
            jsonStr.append(entity.getSimpleName()).append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length()-1);
        jsonStr.append("];");
        return jsonStr.toString();
    }

    /**
     * 获取ts全部后台链接代码
     */
    public static String ts(String controllerPackage,String entityPackage){

        StringBuilder file = new StringBuilder("");

        file.append("export type EntityFieldType = \"default\" | \"file\" | \"link\" | \"link_name_enum\" | \"enum_number\" | \"picture\";\n");

        file.append("export class EntityViewSettings {\n");
        file.append("\tread_permit:Array<number>;\n");
        file.append("\tread:boolean;\n");
        file.append("\twrite_permit:Array<number>;\n");
        file.append("\twrite:boolean;\n");
        file.append("\ttype:EntityFieldType;\n");
        file.append("\tannotation:string;\n");
        file.append("\tenum_number:any;\n");
        file.append("\tconstructor(read:boolean,write:boolean,type:EntityFieldType,annotation:string,enum_number?:any,read_permit:Array<number>=[],write_permit:Array<number>=[]) {\n");
        file.append("\t\tthis.read_permit = read_permit;\n");
        file.append("\t\tthis.read = read;\n");
        file.append("\t\tthis.write = write;\n");
        file.append("\t\tthis.write_permit = write_permit;\n");
        file.append("\t\tthis.type = type;\n");
        file.append("\t\tthis.annotation = annotation;\n");
        file.append("\t\tthis.enum_number = enum_number;\n");
        file.append("\t}\n");
        file.append("}\n");

        file.append(interfaceMap_TS(controllerPackage));
        file.append(entityCreators_TS(entityPackage));
        return file.toString();

    }

    /**
     * 获取js全部后台链接代码
     */
    public static String js(String controllerPackage,String entityPackage){
        StringBuilder file = new StringBuilder("{\n");
        file.append("\tpost:");
        file.append(interfaceMap_JS(controllerPackage));
        file.append(",\n");
        file.append(entityCreators_JS(entityPackage));
        file.append("}");
        return file.toString();
    }


}