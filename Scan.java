package classes;
import java.util.*;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.stream.*;

import annotation.Urls;
import etu864.framework.Mapping;
import annotations.*;
public class Scan {

    public  Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
          .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
          .filter(line -> line.endsWith(".class"))
          .map(line -> getClass(line, packageName))
          .collect(Collectors.toSet());
    }
    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
              + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
    public Object[] getUrlMatching(Class classe,Class annotation){
    Object[] data = new Object[2];
    String annotation;
    Mapping returns  = new Mapping();
    for(Method f : classe.getDeclaredMethods()){ //take method's annotation
            if(f.getAnnotation(annotation)!=null){
                data[0] = f.getAnnotation(annotation).name();
               returns.setClassName(classe.getName());
               returns.setMethod(f.getName());
               data[1]=returns;
               return data;
            }
        }
        return null;
    }
    public HashMap<String,Mapping> initUrls(String packageName){
        Set<Class> classes = this.findAllClassesUsingClassLoader(packageName);
        HashMap <String,Mapping> datas = new HashMap<>();
        for (Class class1 : classes) {
            if(this.getUrlMatching(class1, Urls.class)!=null){

            }
        }


    }
}