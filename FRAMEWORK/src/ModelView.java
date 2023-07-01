package etu1864.framework;

import java.util.*;

public class ModelView {
    private String view;
    private HashMap<String,Object> data;
    private HashMap<String,Object> session;
    private boolean invalidateSession;
    private ArrayList<String> removeSession;

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    public void addItem(String key ,Object value){
        this.getData().put(key, value);
    }
    public void addSession(String key ,Object value){
        this.getSession().put(key, value);
    }

    public boolean isInvalidateSession() {
        return invalidateSession;
    }



    public ArrayList<String> getRemoveSession() {
        return removeSession;
    }

    public void setRemoveSession(ArrayList<String> removeSession) {
        this.removeSession = removeSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        this.invalidateSession = invalidateSession;
    }


}
