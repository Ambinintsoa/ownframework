package etu1864.scanner;
import java.util.*;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.stream.*;
import etu1864.annotation.Urls;
import etu1864.framework.Mapping;
import java.io.*;
import java.net.URL;
public class Scan {

    public static void initUrls(String packageName,HashMap<String,Mapping> datas) throws Exception {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            List<Class<?>> classes = new LinkedList<>();
                    try {
                        Enumeration<URL> resources = classLoader.getResources(packageName);
                        List<File> dirs = new LinkedList<>();
                        while (resources.hasMoreElements()) {
                            URL resource = resources.nextElement();
                            dirs.add(new File(resource.getFile()));
                        }
                        
                        for (File directory : dirs) {
                            classes.addAll(findClasses(directory, packageName,datas));
                        }   
                    } catch (Exception e) {
                        // TODO: handle exception
                        throw e;
                    }
    }

    private static List<Class<?>> findClasses(File directory, String packageName,HashMap<String,Mapping> datas) throws ClassNotFoundException {
        List<Class<?>> classes = new LinkedList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName(),datas));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                
                classes.add(Class.forName(className));

            }
        }
        Scan.verif(classes,datas);
        return classes;
    }
    private static void verif(List<Class<?>> list,HashMap<String,Mapping> datas){
        for(int i = 0 ; i< list.size();i++){
                Scan.getUrlMatching(list.get(i),datas);
        }

    }
    
    public static void getUrlMatching(Class classe,HashMap<String,Mapping> datas){ //find url and method 
    
    for(Method f : classe.getDeclaredMethods()){ //take method's annotation
            if(f.getAnnotation(Urls.class)!=null){
                Mapping returns  = new Mapping();
                returns.setClassName(classe.getName());
               returns.setMethod(f.getName());
               datas.put(f.getAnnotation(Urls.class).name(),returns);
            }
        }
    }
    
}