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
import scanner.Scan;
import etu1864.framework.Mapping;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
public class FrontServlet extends  HttpServlet {
    HashMap<String,Mapping> mappingUrls;
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
        for (Map.Entry<String,Mapping> entry : mappingUrls.entrySet()) {
        out.println(entry.getKey()+"       "+entry.getValue().getClassName());
        }
        
    }
public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        
        PrintWriter out = res.getWriter();
        for (Map.Entry<String,Mapping> entry : mappingUrls.entrySet()) {
        out.println(entry.getKey()+"       "+entry.getValue().getClassName());
        }
    }

}

