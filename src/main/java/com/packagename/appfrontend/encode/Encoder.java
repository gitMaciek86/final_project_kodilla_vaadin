package com.packagename.appfrontend.encode;

public class Encoder {

    public static String encode(String value) {
        String result = value.replaceAll("\\s", "%20");
        result = value.replaceAll(".", "");
        result = value.replaceAll("\"", "");
        System.out.println(result);
        return result;
    }
}


