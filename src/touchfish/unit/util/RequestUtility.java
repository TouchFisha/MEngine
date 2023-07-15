//package touchfish.unit.util;
//
//import org.ssssssss.magicapi.servlet.javaee.MagicJavaEEHttpServletRequest;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class RequestUtility {
//
//    public static HttpServletRequest TranslateMagicRequest(MagicJavaEEHttpServletRequest magic) {
//        return (HttpServletRequest)FieldUtility.getFieldValue(magic,"request");
//    }
//    public static Map<String, String> GetMagicParams(MagicJavaEEHttpServletRequest magic){
//        return GetParams(TranslateMagicRequest(magic));
//    }
//    public static Map<String, String> GetParams(HttpServletRequest request){
//        Enumeration<String> stringEnumeration = request.getParameterNames();
//        Map<String,String> res = new HashMap<>();
//        while (stringEnumeration.hasMoreElements())
//        {
//            String name = stringEnumeration.nextElement();
//            String value = request.getParameterMap().get(name)[0];
//            res.put(name, value);
//        }
//        return res;
//    }
//}
