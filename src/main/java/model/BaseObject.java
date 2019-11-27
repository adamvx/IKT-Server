package model;

import com.google.gson.Gson;

/**
 * Every mdoel object extends from this class. Has useful method for serialization to json.
 */
public class BaseObject {

    public String toJson() {
        return new Gson().toJson(this);
    }

}
