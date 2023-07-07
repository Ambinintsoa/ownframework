
package olona;
import java.util.HashMap;
import java.util.List;

import etu1864.annotation.*;
import etu1864.framework.FileUpload;
import etu1864.framework.ModelView;
import java.util.ArrayList;
import etu1864.annotation.*;
@Scope(singleton = true)

public class Emp {
    private String name;
    private HashMap<String,Object> session;
    private Integer age ;
    public HashMap<String, Object> getSession() {
        return session;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    private FileUpload fi;
    public Emp() {
    }
    public Emp(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileUpload getFi() {
        return fi;
    }
    public void setFi(FileUpload file) {
        this.fi = file;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Urls(name = "/ddd.fn")
    @Auth(profile = "admin")
    public ModelView bidon(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        ArrayList<Emp> a = new ArrayList<Emp>();
        a.add(new Emp(this.getName()));
        v.addItem("dg",a);
        v.addItem("file", this.getFi());
        v.setView("emp.jsp");
        return v;
    }
    //delete one attribute of the session
    @Session
    @Urls(name = "/delete.fn")
    @Auth(profile = "admin")
    public ModelView deletesession(){
        ModelView v = new ModelView();
        v.setInvalidateSession(true);
        ArrayList list = new ArrayList<>();
        list.add("name");
        v.setRemoveSession(list);
        v.setView("index.jsp");
        return v;
    }

    //invalidate the session
    @Urls(name = "/invalidate.fn")
    @Auth(profile = "admin")
    public ModelView invalidatesession(){
        ModelView v = new ModelView();
        v.setInvalidateSession(true);
        v.setView("index.jsp");
        return v;
    }


    
    //upload file
    @Urls(name = "/file.fn")
    public ModelView file(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        v.addItem("file", this.getFi());
        v.setView("file.jsp");
        return v;
    }


    //json by annotation & annotation session
    @Json
    @Session
    @Urls(name = "/random.fn")
    public Emp random_employe_annotation(){
        return new Emp(session.get("name").toString());
    }

    //json by modelView
    @Urls(name = "/random_model.fn")
    public ModelView random_employe_ModelView(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        v.addItem("emp",new Emp("random by modelview"));
        v.setJson(true);
        return v;
    }
    //save the name of employe
    @Urls(name = "/save.fn")
    @Arguments(arguments = {"nom"})
    public ModelView save(String nom){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        this.setName(nom);
        v.addItem("new_employe", this);
        v.setView("emp.jsp");
        return v;
    }

    //save the name of employe
    @Urls(name = "/save_without.fn")
    public ModelView save_attribut(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        v.addItem("new_employe", new Emp(this.getName()));
        v.setView("emp.jsp");
        return v;
    }
//list of employe
    @Urls(name = "/liste.fn" )
    public ModelView list(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        ArrayList<Emp> a = new ArrayList<Emp>();
        a.add(new Emp("Jeanne"));
        a.add(new Emp("Philippe"));
        a.add(new Emp("STeff"));
        v.addItem("employe",a);
        v.setView("emp.jsp");
        return v;
    }


    //connect user
    @Urls(name = "/log_in.fn" )
    @Arguments(arguments = {"name","password"})
    public ModelView log_in(String name,String password){
        ModelView mv = new ModelView();
        mv.setSession(new HashMap<String,Object>());
        mv.addSession("isConnected", true);
        mv.addSession("profile", "admin");
        mv.addSession("name", name);
        mv.setView("Welcome.jsp");
        return mv;
    }

    
}
