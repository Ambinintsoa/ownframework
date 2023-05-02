package etu1864.framework.util;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
public class Utils {
    public static String getInfo(String url){
        return  url.split("\\?")[0];
    }
    public static String getSetter(String name){
        return "set"+name.substring(0, 1).toUpperCase()+name.substring(1);
    }
    public static String getGetter(String name){
        return "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
    }
    public static Object transform(String value,Class classe){

        if(classe.getSimpleName().compareToIgnoreCase("int")==0 || classe.getSimpleName().compareToIgnoreCase("Integer") ==0){
            return Integer.parseInt(value);
        }
        if(classe.getSimpleName().compareToIgnoreCase("float")==0 || classe.getSimpleName().compareToIgnoreCase("Float") ==0){
            return Float.parseFloat(value);
        }

        if(classe.getSimpleName().compareToIgnoreCase("double")==0 || classe.getSimpleName().compareToIgnoreCase("Double") ==0){
            return Double.parseDouble(value);
        }
        if(classe.getSimpleName().compareToIgnoreCase("Date")==0 ){
            return Date.valueOf(value);
        }
        return value;
    }
    // public ArrayList<Class<?>> getAllArguments(Object obj ,String method){
    //     ArrayList<Class<?>> classes = new ArrayList<>();
    //     Method[] meth = obj.getClass().getDeclaredMethods();
    //     for (int i = 0; i < meth.length; i++) {
    //         if(meth[i].getName().compareTo(method)== 0){
               
    //         }
    //     }
    //     return classes;
    // }
    public static String method(Object obj , String methods){
        Method[] meth = obj.getClass().getDeclaredMethods();
        for (Method met : meth) {
            if (met.getName().compareTo(methods)== 0){
                return met.toGenericString();
            }

        }
        return "not found";
    }
}
