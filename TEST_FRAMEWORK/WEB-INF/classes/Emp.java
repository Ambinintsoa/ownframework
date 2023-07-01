
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
    @Session
    @Urls(name = "/eee.fn")
    @Auth(profile = "admin")
    // @Json
    public ModelView usesession(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
       Emp  a = new Emp((String)this.getSession().get("name"));
        v.addItem("dg",a);
        v.setInvalidateSession(true);
        ArrayList list = new ArrayList<>();
        list.add("name");
        v.setRemoveSession(list);
        v.addItem("file", this.getFi());
        v.setView("emp.jsp");
        return v;
    }
    @Session
    @Urls(name = "/eeef.fn")
    @Auth(profile = "admin")
    @Json
    public String usesessions(){
        return "AAA";
    }
    @Urls(name = "/save.fn")
    public ModelView save(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        v.addItem("employe", this);
        v.setView("new.jsp");
        return v;
    }
    @Urls(name = "/saved.fn" )
    @Arguments(arguments = {"id","name"})
    public ModelView saved(Integer id,String name){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        ArrayList<Emp> a = new ArrayList<Emp>();
        a.add(new Emp(name));
        v.addItem("dg",a);
        v.setView("emp.jsp");
        return v;
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
    @Urls(name = "/log_in.fn" )
    @Arguments(arguments = {"name","password"})
    public ModelView log_in(String name,String password){
        ModelView mv = new ModelView();
        mv.setSession(new HashMap<String,Object>());
        mv.addSession("isConnected", true);
        mv.addSession("profile", "admin");
        mv.addSession("name", name);
        mv.setView("emp.jsp");
        return mv;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    
}
