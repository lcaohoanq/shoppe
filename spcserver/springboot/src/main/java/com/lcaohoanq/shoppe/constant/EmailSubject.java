package com.lcaohoanq.shoppe.constant;

public class EmailSubject {

    public static String subjectGreeting(String name) {
        return """
            Shoppe Corporation- Welcome %s, thanks for joining us!
            """.formatted(name);
    }

    public static String subjectRequestUpdateRole(){
        return """
            Shoppe Corporation- New Request to update role
            """;
    }

    public static String subjectForgotPassword(String name) {
        return """
            Shoppe Corporation- Hi %s, we're here to help you get the password back!
            """.formatted(name);
    }

    public static String subjectRunningApp() {
        return """
            Shoppe Corporation- Your app is running, Happy Coding!
            """;
    }

    public static String subjectBlockEmail(String name){
        return """
            Shoppe Corporation- %s, your account has been blocked!
            """.formatted(name);
    }

}