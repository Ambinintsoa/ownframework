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
            
            // for (Map.Entry<String,Mapping> entry : mappingUrls.entrySet()) {
            //     out.println(entry.getKey()+"       "+entry.getValue().getClassName());
            //     }
                out.println(req.getRequestURI());
                out.println( Utils.getInfo(req.getRequestURI(),req.getServletPath() )[0]);
                String key = "/"+ Utils.getInfo(req.getRequestURI(),req.getServletPath() )[0];
                if(mappingUrls.containsKey(key)){
                    Mapping map = mappingUrls.get(key);
                    Class<?> classe = Class.forName(map.getClassName());
                    Object created = classe.newInstance();
                    out.print(((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView());
                    RequestDispatcher dis = req.getRequestDispatcher("/"+((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView());
                    dis.forward(req,res);
                }
            
        } catch (Exception e) {
            // TODO: handle 
            out.println(e);
        }
        
    }
public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            
            // for (Map.Entry<String,Mapping> entry : mappingUrls.entrySet()) {
            //     out.println(entry.getKey()+"       "+entry.getValue().getClassName());
            //     }
                out.println(req.getRequestURI());
                out.println( Utils.getInfo(req.getRequestURI(),req.getServletPath() )[0]);
                String key = "/"+ Utils.getInfo(req.getRequestURI(),req.getServletPath() )[0];
                if(mappingUrls.containsKey(key)){
                    Mapping map = mappingUrls.get(key);
                    Class<?> classe = Class.forName(map.getClassName());
                    Object created = classe.newInstance();
                    out.print(((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView());
                    RequestDispatcher dis = req.getRequestDispatcher("/"+((ModelView) (created.getClass().getMethod(map.getMethod() ).invoke(created))).getView());
                     dis.forward(req,res);
                }
            
        } catch (Exception e) {
            // TODO: handle 
            out.println(e);
        }


    }

}

