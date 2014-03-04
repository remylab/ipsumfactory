package tools;

import play.mvc.Http;

public class Chelper {

    public static boolean getBool(String key) {
        Object val = Http.Context.current().args.get(key);
        if (val != null) {
            return (Boolean) Http.Context.current().args.get(key);
        }
        return false;
    }

    public static void setBool(String key, Boolean value) {
        Http.Context.current().args.put(key, value);
    }
}
