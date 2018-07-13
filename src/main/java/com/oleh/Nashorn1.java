package com.oleh;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Nashorn1 {
    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js');\n" +
                "\n" +
                "var odds = _.filter([1, 2, 3, 4, 5, 6], function (num) {\n" +
                "    return num % 2 == 1;\n" +
                "});\n" +
                "\n" +
                "print(odds);");


        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(((ScriptObjectMirror)result).entrySet());
        System.out.println(result.getClass());
    }
}
