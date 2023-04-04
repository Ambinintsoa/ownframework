
package olona;
import java.util.HashMap;
import java.util.List;

import etu1864.annotation.Urls;
import etu1864.framework.ModelView;
import java.util.ArrayList;
public class Emp {
    private String name;
    
    public Emp() {
    }
    public Emp(String name) {
        this.name = name;
    }

    @Urls(name = "/ddd")
    public ModelView bidon(){
        ModelView v = new ModelView();
        v.setData(new HashMap<String,Object>());
        ArrayList<Emp> a = new ArrayList<Emp>();
        a.add(new Emp("jean"));
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
}
