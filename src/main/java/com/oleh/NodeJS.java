package com.oleh;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


public class NodeJS {

    public static void main(String[] args) throws IOException, InterruptedException {
//        meth();
        testStrategy();
    }
    
    public static void testStrategy() throws IOException, InterruptedException {
        String left = "5b475002e4b21e6c50b66685";
        String right = "5b474f66e4b21e6c50b6667f";
        String strategy = "deep-diff-strategy";
        String renderer = "deep-diff-html-renderer";
        strategy(left, right, strategy, renderer);
    }
    
    public static String strategy(String left, String right, String strategy, String renderer) throws IOException, InterruptedException {
        String f = "node index.js --baseline=%s --candidate=%s --strategy=%s --renderer=%s";
        String ff = String.format(f, left, right, strategy, renderer);

        ProcessBuilder pb = new ProcessBuilder(ff.split(" "));

        pb.directory(new File("C:\\js\\QATA\\js-test-scripts\\qata-client"));
//        pb.inheritIO();
        pb.redirectErrorStream(true);

        Process p = pb.start();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        p.waitFor();
        String lines = reader.lines().collect(Collectors.joining("\n"));
        reader.close();
        System.out.println("This is absolutely new");
        System.out.println(lines);
        return lines;
    }
    
    static void meth() throws IOException, InterruptedException {
        ProcessBuilder   ps=new ProcessBuilder("java.exe","-version");

//From the DOC:  Initially, this property is false, meaning that the 
//standard output and error output of a subprocess are sent to two 
//separate streams
        ps.redirectErrorStream(true);
        Process pr = ps.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line ;
        String all = "";
        while ((line = in.readLine()) != null) {
            all +=line;
        }
        pr.waitFor();
        System.out.println(all);
        in.close();
    }

}