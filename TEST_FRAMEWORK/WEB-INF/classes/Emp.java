
package olona;
import java.util.HashMap;
import java.util.List;

import etu1864.annotation.*;
import etu1864.framework.FileUpload;
import etu1864.framework.ModelView;
import java.util.ArrayList;
import etu1864.annotation.Scope;
@Scope(singleton = true)
public class Emp {
    private String name;
    // private int age ;
    private FileUpload fi;
    public Emp() {
    }
    public Emp(String name) {
        this.name = name;
    }

    @Urls(name = "/ddd.fn")
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
    // public int getAge() {
    //     return age;
    // }
    // public void setAge(int age) {
    //     this.age = age;
    // }
    public FileUpload getFi() {
        return fi;
    }
    public void setFi(FileUpload file) {
        this.fi = file;
    }
    
}
