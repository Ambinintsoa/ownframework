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
        HttpSession session_request = req.getSession();
        String key = Utils.getInfo(req.getServletPath());
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

            // complete session attribut of model

            Object model  = null;
            String[] values = null;
            Method met = null;
            Enumeration<String> ressources = req.getParameterNames();

            //complete file if it exists
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

            //instantiate all attributes of the class which has value in form
            while (ressources.hasMoreElements()) {
                String ressource = ressources.nextElement();
                Method[] methods = created.getClass().getDeclaredMethods();
                    //initialize all atrributes 

                    for (int i = 0; i < methods.length; i++){
                        if(methods[i].getName().compareTo(Utils.getSetter(ressource))==0 && methods[i].getParameterTypes().length == 1){
                            if(methods[i].getParameterTypes()[0].getSimpleName().compareToIgnoreCase("FileUpload")!=0 ){
                                values = req.getParameterValues(ressource);
                                out.print(ressource+values[0]);

                                created.getClass().getMethod(Utils.getSetter(ressource),methods[i].getParameterTypes()[0]).invoke(created,Utils.transform(values[0], methods[i].getParameterTypes()[0]));
                                out.print( created.getClass().getMethod(Utils.getGetter(ressource),(Class[]) null).invoke(created,(Object[])null));
                               
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
                //verify if the user has access in the method
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
                        //verify if method is annoted session for using session in model
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

                        //complete argument with values in form
                        if(met.getDeclaredAnnotation(Arguments.class)!=null){
                            String[] args =met.getDeclaredAnnotation(Arguments.class).arguments();
                            obj = new Object[args.length];
                                        for (int index = 0; index < args.length; index++) {
                                            if(req.getParameter(args[index])!=null){
                                                obj[index] = Utils.transform(req.getParameter(args[index]), met.getParameters()[index].getType());
                                            }
                                        }
                                        met.setAccessible(true);
                                        //method with parameters
                                        model = met.invoke(created, obj);
        
                                        
        
                         }else{
                            //method without parameter
                            model= created.getClass().getMethod(map.getMethod()).invoke(created);
                        }
                       
                        //take return value of the function in model
                        if(model instanceof ModelView){
                            if((boolean)((ModelView) model).isInvalidateSession() == true){
                                if(((ModelView) model).getRemoveSession()== null){
                                    session_request.invalidate();
                                }else{
                                    for (String sessionString : ((ModelView) model).getRemoveSession()) {
                                   
                                        session_request.removeAttribute(sessionString);
                                       
                                    }
                                }

                            }


                            //take values in data
                            if( ((ModelView) model).getData() instanceof HashMap  ){
                                    HashMap<String,Object>data =   ((ModelView) model).getData();
                                    if(data !=null )  {
                                        if((boolean)((ModelView) model).isJson() ==true){
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


                            //complete http session with return value of the function
                            if( ((ModelView) model).getSession() instanceof HashMap  ){
                                HashMap<String,Object>session =   ((ModelView) model).getSession();
                                if(session !=null )  {
                                    for (Map.Entry<String,Object> entry : session.entrySet()) {
                                        session_request.setAttribute(entry.getKey(),entry.getValue());
                                    }
                                } 
                            }
                            
                            //dispatch with a specific view
                            if( ((ModelView) model).getView() instanceof String && (boolean)((ModelView) model).isJson()  ==false){
                                RequestDispatcher dis = null; 
                                dis = req.getRequestDispatcher( String.format("/%s", ((ModelView) model).getView()));
                                dis.forward(req,res);
                            }
                            //function doesn't return modelview
                        }else{
                            
                            if(met.getDeclaredAnnotation(Json.class) !=null){
                                res.setContentType("application/json;charset=UTF-8");
                                out = res.getWriter();
                                Gson j = new Gson();
                                String JSON = j.toJson(model);
                                out.print(JSON);
                            }
                        }

                }
            }

        }
        // if(met.getDeclaredAnnotation(Json.class) ==null ){
        //     res.sendRedirect(String.format("%s/error.jsp", req.getContextPath()));
        //    }
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

//verify if the form is Type multipart
    private boolean isMutipart(HttpServletRequest req ){
        String content = req.getContentType();
        if(content !=null &&  content.startsWith("multipart/form-data")){
            return true;
        }else{
            return false;
        }
    }

    //getFilename of the file
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

//reset null all attribute of the class
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


//verify if it's a class singleton
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
