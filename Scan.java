package scanner;
import java.util.*;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.stream.*;
import annotation.Urls;
import etu1864.framework.Mapping;
public class Scan {

    public  static Set<Class> findAllClassesUsingClassLoader(String packageName){ //find all classes in a package

    try {
        InputStream stream = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(packageName);
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
          return reader.lines()
          .filter(line -> line.endsWith(".class"))
          .map(line -> Scan.getClass(line, packageName.replace("/", ".")))
          .collect(Collectors.toSet());
    } catch (Exception e) {
        // TODO: handle exception
        // System.out.println(e);
    }
return null;
        
    }
    private static Class getClass(String className, String packageName){ //instantiate a class
        try {
            return Class.forName(packageName + "."
              + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
            // System.out.println(e);
            return null;
        }
    }
    public static void getUrlMatching(Class classe,HashMap<String,Mapping> datas){ //find url and method 
    Mapping returns  = new Mapping();
    for(Method f : classe.getDeclaredMethods()){ //take method's annotation
            if(f.getAnnotation(Urls.class)!=null){
                returns.setClassName(classe.getName());
               returns.setMethod(f.getName());
               datas.put(f.getAnnotation(Urls.class).name(),returns);
            }
        }
    }
    public static ArrayList<File>  initUrls(String packageName,HashMap<String,Mapping>data)throws Exception{

        ArrayList<File> pack = new ArrayList<>();
        Scan.getDirectory(packageName,pack);
        for (int i = 0; i < pack.size(); i++) {
            if(pack.get(i).getAbsolutePath().split(packageName).length==1){
                throw  new Exception("don't find the classes");
            }
                Set<Class> classes = Scan.findAllClassesUsingClassLoader(pack.get(i).getAbsolutePath().split(packageName)[1]);
                if(classes!=null){
                    //System.out.println(classes.size());
                    for (Class class1 : classes) {
                        // System.out.println(class1);
                        Scan.getUrlMatching(class1, data);
                    }
                }

        }
        return pack;

    }
    public static  void  getDirectory(String path,ArrayList<File> pack){ //get all directories in a specific repository
        
        File file = new File(path);
        File[] directory = file.listFiles();
        for (File dir : directory) {
            if(dir.isDirectory()){
                Scan.getDirectory(dir.getAbsolutePath(), pack);
                pack.add(dir);
            }
        }
        
    }
}