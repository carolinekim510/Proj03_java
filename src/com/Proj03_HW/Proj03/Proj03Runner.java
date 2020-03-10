package com.Proj03_HW.Proj03;

public class Proj03Runner {

    public Proj03Runner() {
        System.out.println("I certify that this program is my own work");
        System.out.println("and is not the work of other. I agree not");
        System.out.println("to share my solution with others.");
        System.out.println("Caroline Kim\n");
    }

    public void run(String website) {
        try {
            HtmlHandler handler = new HtmlHandler(website);
            handler.startBrowser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
