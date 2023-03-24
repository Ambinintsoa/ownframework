package etu1864.framework.util;
import java.util.ArrayList;
public class Utils {
    public static String[] getInfo(String url,String context){
        String urlsplit =  url.split("\\?")[0];
         urlsplit = url.split(context+"/")[1];
        return urlsplit.split("/");
    }
}
