
package olona;
import etu1864.annotation.Urls;
import etu1864.framework.ModelView;

public class Emp {
    private String name;
    
    public Emp() {
    }

    @Urls(name = "/ddd")
    public ModelView bidon(){
        ModelView v = new ModelView();
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
