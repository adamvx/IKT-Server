package model;

import com.google.gson.Gson;

public class BaseObject {

    public String toJson() {
        return new Gson().toJson(this);
    }

}
