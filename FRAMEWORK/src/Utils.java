package etu1864.framework.util;
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
}
