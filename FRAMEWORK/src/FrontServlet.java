package etu1864.framework.servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import etu1864.scanner.Scan;
import etu1864.framework.FileUpload;
import etu1864.framework.Mapping;
import etu1864.framework.ModelView;
import etu1864.framework.util.Utils;
import etu1864.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.stream.*;
import org.ietf.jgss.Oid;
import java.io.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import etu1864.framework.*;
@MultipartConfig
public class FrontServlet extends  HttpServlet {
    private HashMap<String,Mapping> mappingUrls;
    private HashMap<String,Object> singleton;
    private String url ;
    public void init() throws ServletException {
        try {
            mappingUrls = new HashMap<>();
            singleton = new HashMap<>();
            url = this.getInitParameter("package_to_scan").toString();
            Scan.initUrls(url,mappingUrls,singleton);
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
    OutputStream outs = null;
    InputStream fileContent = null;
    try {
        // out.println(singleton.get("Emp"));
        HttpSession session_request = req.getSession();
        String key = Utils.getInfo(req.getServletPath());
        // out.println(key);
        if(mappingUrls.containsKey(key)){
            Collection<Part> val = null;
            Mapping map = mappingUrls.get(key);
            Class<?> classe = Class.forName(map.getClassName());
            Object created = null;
            
            if(reinitialize(classe.getSimpleName())==false){
                 created = classe.newInstance();
            }else{
                created = singleton.get(classe.getSimpleName());
            }
            // out.print(created);

            // complete session attribut of model

            Object model  = null;
            String[] values = null;
            Method met = null;
            Enumeration<String> ressources = req.getParameterNames();
            if(isMutipart(req)){
                values = new String[1];
            }
                while (ressources.hasMoreElements()) {
                    String ressource = ressources.nextElement();
                    Method[] methods = created.getClass().getDeclaredMethods();
                        //initialize all atrributes 
                    Field[] fields = created.getClass().getDeclaredFields();
                        for (int i = 0; i < fields.length; i++){
                            if(fields[i].getType().getSimpleName().compareToIgnoreCase("FileUpload")==0 && isMutipart(req) ){
                                Part file = req.getPart(fields[i].getName());
                                String savePath = this.getInitParameter("upload_directory").toString() + getFileName(file);
    
                                // Save the file to the specified location


                                    outs = new FileOutputStream(new File(savePath));
                                    fileContent = file.getInputStream();
                                    
                                    int read;
                                    byte[] buffer = new byte[1024];
                                    while ((read = fileContent.read(buffer)) != -1) {
                                        outs.write(buffer, 0, read);
                                    }
                                    outs.flush();
                                    outs.close();
   
                                //instantiantion de la valeur de l'attribut de type fileupload
                                FileUpload fil= new FileUpload();
                                fil.setFilename(getFileName(file));
                                fil.setFile(file.getInputStream().readAllBytes());
                                created.getClass().getMethod(Utils.getSetter(fields[i].getName()),FileUpload.class).invoke(created,fil);
                                break;
                            }
                        }
                        for (int i = 0; i < methods.length; i++){
                            if(methods[i].getName().compareTo(Utils.getSetter(ressource))==0 && methods[i].getParameterTypes().length == 1){
                                if(methods[i].getParameterTypes()[0].getSimpleName().compareToIgnoreCase("FileUpload")!=0 ){
                                    values = req.getParameterValues(ressource);
                                    created.getClass().getMethod(Utils.getSetter(ressource),methods[i].getParameterTypes()[0]).invoke(created,Utils.transform(values[0], methods[i].getParameterTypes()[0]));
                                }
                             }
                        }
                }

            //initialize argument
            Object[] obj = null;
            boolean identification = true;
            for (int i = 0; i < created.getClass().getDeclaredMethods().length; i++) {
                if (map.getMethod().compareToIgnoreCase(created.getClass().getDeclaredMethods()[i].getName())== 0  )
                {
                    met = created.getClass().getDeclaredMethods()[i];
                    
                 if(met.getDeclaredAnnotation(Auth.class) !=null){
                        if(met.getDeclaredAnnotation(Auth.class).profile().compareToIgnoreCase("ano")!=0){
                            if(session_request.getAttribute("profile") == null || met.getDeclaredAnnotation(Auth.class).profile().compareToIgnoreCase( session_request.getAttribute("profile").toString())!=0){
                                identification = false;
                            }
                        }
                        if( session_request.getAttribute("isConnected") == null ||  session_request.getAttribute("isConnected") == "false"){
                            identification = false;
                    }
                    }

                    if(identification == true){
                        Annotation annote = met.getDeclaredAnnotation(Session.class);
                        Field sessions = created.getClass().getDeclaredField("session");
                        Enumeration<String> attributes = session_request.getAttributeNames();
                        if(annote!=null &&  sessions !=null && session_request.getAttributeNames() !=null){
                            HashMap<String,Object> sess = new HashMap<String,Object>();
                            while (attributes.hasMoreElements()) {
                                String attribut = attributes.nextElement();
                                    sess.put(attribut,session_request.getAttribute(attribut));
                            }
                            
                            created.getClass().getDeclaredMethod(Utils.getSetter("session"), HashMap.class).invoke(created,sess);
                        }
                        if(met.getDeclaredAnnotation(Arguments.class)!=null){
                            String[] args =met.getDeclaredAnnotation(Arguments.class).arguments();
                            obj = new Object[args.length];
                                        for (int index = 0; index < args.length; index++) {
                                            if(req.getParameter(args[index])!=null){
                                                obj[index] = Utils.transform(req.getParameter(args[index]), met.getParameters()[index].getType());
                                            }
                                        }
                                        met.setAccessible(true);
                                        model = met.invoke(created, obj);
        
                                        
        
                         }else{
                            model= created.getClass().getMethod(map.getMethod()).invoke(created);
                        }
                       
                    
                        if(model instanceof ModelView){

                            if( ((ModelView) model).getData() instanceof HashMap  ){
                                    HashMap<String,Object>data =   ((ModelView) model).getData();
                                    if(data !=null )  {
                                        if(met.getDeclaredAnnotation(Json.class) !=null){
                                            res.setContentType("application/json;charset=UTF-8");
                                            out = res.getWriter();
                                            Gson j = new Gson();
                                            String JSON = j.toJson(data);
                                            out.print(JSON);
                                        }else{
                                            for (Map.Entry<String,Object> entry : data.entrySet()) {
                                                req.setAttribute(entry.getKey(),entry.getValue());
                                            }
                                        }
                                    } 

                            } 
                            if( ((ModelView) model).getSession() instanceof HashMap  ){
                                HashMap<String,Object>session =   ((ModelView) model).getSession();
                                if(session !=null )  {
                                    for (Map.Entry<String,Object> entry : session.entrySet()) {
                                        session_request.setAttribute(entry.getKey(),entry.getValue());
                                    }
                                } 
                            }
                            
                            if( ((ModelView) model).getView() instanceof String && met.getDeclaredAnnotation(Json.class) ==null){
                                RequestDispatcher dis = null; 
                                dis = req.getRequestDispatcher( String.format("/%s", ((ModelView) model).getView()));
                                dis.forward(req,res);
                            }
                        }

                }
            }

        }
        if(met.getDeclaredAnnotation(Json.class) ==null ){
            res.sendRedirect(String.format("%s/error.jsp", req.getContextPath()));
           }
        }

        
    } catch (Exception e) {
        // TODO: handle 
        out.println(e);
        
    }
    finally {
        if (outs != null) {
            outs.close();
        }
        if (fileContent != null) {
            fileContent.close();
        }
    }
}

    private boolean isMutipart(HttpServletRequest req ){
        String content = req.getContentType();
        if(content !=null &&  content.startsWith("multipart/form-data")){
            return true;
        }else{
            return false;
        }
    }
public String getFileName(Part part) {
    String contentDisposition = part.getHeader("content-disposition");
    String[] tokens = contentDisposition.split(";");
    for (String token : tokens) {
        if (token.trim().startsWith("filename")) {
            return token.substring(token.indexOf("=") + 2, token.length() - 1);
        }
    }
    return "";
}
private void reset(Object obj )throws Exception{
    Method[] methods = obj.getClass().getDeclaredMethods();
    Field[] fields = obj.getClass().getDeclaredFields();
    for (int i = 0; i < methods.length; i++) {
        for (int j = 0; j < fields.length; j++) {
            if(methods[i].getName().compareToIgnoreCase(Utils.getSetter(fields[j].getName()))==0){
                methods[i].invoke(obj,Utils.transformNUll(methods[i].getParameterTypes()[0]));
            }
        }
        
    }
}
private boolean reinitialize(String name)throws Exception{
    Object obj = singleton.get(name);
    if(obj!=null){
        try {
            reset(obj);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
        
        return true;
    }
    return false;
}
}
