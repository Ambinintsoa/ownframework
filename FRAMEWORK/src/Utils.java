package etu1864.framework.util;
import java.util.ArrayList;
public class Utils {
    public static String getInfo(String url){
        return  url.split("\\?")[0];
    }
}
