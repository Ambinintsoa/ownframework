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
    for (Map.Entry<String,Mapping> entry : mappingUrls.entrySet()) {
        out.println(entry.getKey()+"  "+entry.getValue().getMethod());
    }
    try {
        String key = Utils.getInfo(req.getServletPath());
        out.print(key);
        if(mappingUrls.containsKey(key)){
            Mapping map = mappingUrls.get(key);
            Class<?> classe = Class.forName(map.getClassName());
            Object created = classe.newInstance();
            out.println(Utils.method(created, map.getMethod()));
            Object model = created.getClass().getMethod(map.getMethod() ).invoke(created);
            out.println( map.getMethod());
            
            if(model instanceof ModelView){
                Enumeration<String> ressources = req.getParameterNames();
                while (ressources.hasMoreElements()) {
                    String ressource = ressources.nextElement();
                    String[] values = req.getParameterValues(ressource);
                    Method[] methods = created.getClass().getDeclaredMethods();
                        for (int i = 0; i < methods.length; i++) {
                            if(methods[i].getName().compareTo(Utils.getSetter(ressource))==0 && methods[i].getParameterTypes().length == 1){
                                created.getClass().getMethod(Utils.getSetter(ressource),methods[i].getParameterTypes()[0]).invoke(created,Utils.transform(values[0], methods[i].getParameterTypes()[0]));
                                out.println(created.getClass().getMethod(Utils.getGetter(ressource)).invoke(created));
                            }
                        }
                }
                if( ((ModelView) model).getData() instanceof HashMap  ){
                    HashMap<String,Object>data =   ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getData();
                    if(data !=null )  {
                        for (Map.Entry<String,Object> entry : data.entrySet()) {
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                    }     
                } 

                
                // if( ((ModelView) model).getView() instanceof String  ){
                  
                // RequestDispatcher dis = req.getRequestDispatcher( String.format("/%s", ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView()));
                //  dis.forward(req,res);
                // }
            }
            
        }
        //  res.sendRedirect(String.format("%s/error.jsp", req.getContextPath()));
        
    } catch (Exception e) {
        // TODO: handle 
        out.println(e);
    }
}

}

