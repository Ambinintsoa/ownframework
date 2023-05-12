package etu1864.framework.servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import java.io.File;
import java.io.IOException;
import java.util.*;
import etu1864.scanner.Scan;
import etu1864.framework.Mapping;
import etu1864.framework.ModelView;
import etu1864.framework.util.Utils;
import etu1864.annotation.*;
import java.lang.reflect.*;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.stream.*;
import org.ietf.jgss.Oid;;
public class FrontServlet extends  HttpServlet {
    private HashMap<String,Mapping> mappingUrls;
    private String url ;
    public void init() throws ServletException {
        try {
            mappingUrls = new HashMap<>();
            url = this.getInitParameter("package_to_scan").toString();
            Scan.initUrls(url,mappingUrls);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        this.excecutable(req, res);
    }
public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        this.excecutable(req, res);
    }
    
private  void excecutable(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    PrintWriter out = res.getWriter();
    try {
        String key = Utils.getInfo(req.getServletPath());
        out.print(key);
        if(mappingUrls.containsKey(key)){
            Mapping map = mappingUrls.get(key);
            Class<?> classe = Class.forName(map.getClassName());
            Object created = classe.newInstance();
            HashMap<String,String> param = Utils.getParam(req.getQueryString()); 
            Object argument = null;
            Object model  = null;
            Enumeration<String> ressources = req.getParameterNames();
            while (ressources.hasMoreElements()) {
                String ressource = ressources.nextElement();
                String[] values = req.getParameterValues(ressource);
                Method[] methods = created.getClass().getDeclaredMethods();
                    //initialize all atrributes
                    for (int i = 0; i < methods.length; i++) {
                        if(methods[i].getName().compareTo(Utils.getSetter(ressource))==0 && methods[i].getParameterTypes().length == 1){
                            created.getClass().getMethod(Utils.getSetter(ressource),methods[i].getParameterTypes()[0]).invoke(created,Utils.transform(values[0], methods[i].getParameterTypes()[0]));
                            out.println(created.getClass().getMethod(Utils.getGetter(ressource)).invoke(created));
                        }
                    }
            }
            //initialize arggument
            for (int i = 0; i < created.getClass().getDeclaredMethods().length; i++) {
                if (map.getMethod().compareToIgnoreCase(created.getClass().getDeclaredMethods()[i].getName())== 0)
                {
                     out.print(created.getClass().getDeclaredMethods()[i].getDeclaredAnnotation(Urls.class).arguments());
                     String[] args = Utils.get_arguments(created.getClass().getDeclaredMethods()[i].getDeclaredAnnotation(Urls.class).arguments());
                   
                    for (int j = 0; j < created.getClass().getDeclaredMethods()[i].getParameters().length; j++) {
                                for (int index = 0; index < args.length; index++) {
                                    if(req.getParameter(args[index])!=null){
                                        out.println( map.getMethod());
                                        argument = Utils.transform(req.getParameter(args[index]), created.getClass().getDeclaredMethods()[i].getParameters()[j].getType());
                                        out.println(req.getParameter(args[index]));
                                        out.print(argument);
                                       model= created.getClass().getMethod(map.getMethod(),created.getClass().getDeclaredMethods()[i].getParameters()[j].getType()).invoke(created,argument);
                                    }

                                }
                    }
                }
            }
            if(argument == null){
                model= created.getClass().getMethod(map.getMethod()).invoke(created);
            }
            if(model instanceof ModelView){

                if( ((ModelView) model).getData() instanceof HashMap  ){
                    if(argument !=null){
                        // out.println(created.getClass().getMethod(map.getMethod(),argument.getClass()).invoke(created,argument));
                        HashMap<String,Object>data =   ((ModelView) (created.getClass().getMethod(map.getMethod(),argument.getClass()).invoke(created,argument))).getData();
                        if(data !=null )  {
                            for (Map.Entry<String,Object> entry : data.entrySet()) {
                                req.setAttribute(entry.getKey(),entry.getValue());
                            }
                        } 
                    }else{
                    HashMap<String,Object>data =   ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getData();
                    if(data !=null )  {
                        for (Map.Entry<String,Object> entry : data.entrySet()) {
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                    } 
                    }
    
                } 

                
                if( ((ModelView) model).getView() instanceof String  ){
                    RequestDispatcher dis = null; 
                    if(argument!=null){
                         dis = req.getRequestDispatcher( String.format("/%s", ((ModelView) (created.getClass().getMethod(map.getMethod(),argument.getClass()).invoke(created,argument))).getView()));
                    }else{
                        dis = req.getRequestDispatcher( String.format("/%s", ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView()));
                    }
                  
                 dis.forward(req,res);
                }
            }
            
        }
         res.sendRedirect(String.format("%s/error.jsp", req.getContextPath()));
        
    } catch (Exception e) {
        // TODO: handle 
        out.println(e);
    }
}

}

