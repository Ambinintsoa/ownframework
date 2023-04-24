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
        PrintWriter out = res.getWriter();
        try {
            String key = Utils.getInfo(req.getServletPath());
            
            if(mappingUrls.containsKey(key)){
                Mapping map = mappingUrls.get(key);
                Class<?> classe = Class.forName(map.getClassName());
                Object created = classe.newInstance();
                Object model = created.getClass().getMethod(map.getMethod() ).invoke(created);
                if(model instanceof ModelView){
                    Enumeration<String> ressources = req.getParameterNames();
                    while (ressources.hasMoreElements()) {
                        String ressource = ressources.nextElement();
                        String[] values = req.getParameterValues(ressource);
                        out.println(Utils.getSetter(ressource));
                        created.getClass().getMethod(Utils.getSetter(ressource),String.class).invoke(created,values[0]);
                        out.println(created.getClass().getMethod(Utils.getGetter(ressource)).invoke(created));
                    }
                    if( ((ModelView) model).getData() instanceof HashMap  ){
                        HashMap<String,Object>data =   ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getData();
                        if(data !=null )  {
                            for (Map.Entry<String,Object> entry : data.entrySet()) {
                                req.setAttribute(entry.getKey(),entry.getValue());
                            }
                        } 
                        
                    } 
                    
                    if( ((ModelView) model).getView() instanceof String  ){
                       
                    // RequestDispatcher dis = req.getRequestDispatcher( String.format("/%s", ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView()));
                    //  dis.forward(req,res);
                    }
                }
                
            }
            // res.sendRedirect(String.format("%s/error.jsp", req.getContextPath()));
            
        } catch (Exception e) {
            // TODO: handle 
            out.println(e);
        }
        
    }
public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            String key = Utils.getInfo(req.getServletPath() );
            if(mappingUrls.containsKey(key)){
                Mapping map = mappingUrls.get(key);
                Class<?> classe = Class.forName(map.getClassName());
                Object created = classe.newInstance();
                Object model = created.getClass().getMethod(map.getMethod() ).invoke(created);
                if(model instanceof ModelView){
                    Enumeration<String> ressources = req.getParameterNames();
                    while (ressources.hasMoreElements()) {
                        String ressource = ressources.nextElement();
                        String[] values = req.getParameterValues(ressource);
                        out.println(Utils.getSetter(ressource));
                        created.getClass().getMethod(Utils.getSetter(ressource),String.class).invoke(created,values[0]);
                        out.println(created.getClass().getMethod(Utils.getGetter(ressource)).invoke(created));
                    }
                    if( ((ModelView) model).getData() instanceof HashMap  ){
                        HashMap<String,Object>data =   ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getData();
                        if(data !=null )  {
                            for (Map.Entry<String,Object> entry : data.entrySet()) {
                                req.setAttribute(entry.getKey(),entry.getValue());
                            }
                        } 
                        
                    } 
                    if( ((ModelView) model).getView() instanceof String  ){
                    RequestDispatcher dis = req.getRequestDispatcher(String.format("/%s", ((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView()));
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

