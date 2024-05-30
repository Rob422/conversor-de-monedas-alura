package modulos;

import com.google.gson.*;

public class JsonParsing {

    public Monedas getMonedas(String json){
        return new Gson().fromJson(json, Monedas.class);
    }

}
