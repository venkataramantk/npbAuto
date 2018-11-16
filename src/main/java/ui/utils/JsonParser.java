package ui.utils;

/*
 * Created by Richa Priya on 04/07/2018.
 */

import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import ui.UiBase;

public class JsonParser extends UiBase {

    private static Object ctx;

    private static Object getParsedJson(String json) {

        ctx = Configuration.defaultConfiguration().jsonProvider().parse(json);
        return ctx;

    }

    public static String getValueOfJsonPath(String json, String jsonpath) {
        String value = null;
        try {
            if (json != null && jsonpath != null) {
                getParsedJson(json);
                value = JsonPath.read(ctx, jsonpath).toString();
            } else {
                System.out.println("Json String or Json Path is null");
            }

        } catch (Exception e) {
            System.out.println("Json string or Json Path is null");
        }
        return value;
    }


    public static List<String> getListValueOfJsonPath(String json, String jsonpath) {
        List<String> value = null;
        try {

            if (json != null && jsonpath != null) {
                getParsedJson(json);
                value= JsonPath.read(ctx, jsonpath);
            } else {
                System.out.println("Json String or Json Path is null");
            }
        } catch (Exception e) {

            System.out.println("Json string or Json Path is null");
        }
        return value;
    }

}
