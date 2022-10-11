package io;


public class ActionMenus {

    public static void adminActions() {
        System.out.println("1. Add user");
        System.out.println("2. Remove user");
        System.out.println("3. Reset database");
        System.out.println("4. Change password");
    }

    public static void instructorActions() {
        System.out.println("1. Add course");
        System.out.println("2. Remove Course");
        System.out.println("3. Show submitted projects");
        System.out.println("4. Change password");
        System.out.println("5. Chat");
    }

    public static void studentActions() {
        System.out.println("1. Register to course");
        System.out.println("2. Drop course");
        System.out.println("3. Submit project");
        System.out.println("4. Show submitted projects");
        System.out.println("5. Change Password");
        System.out.println("6. Chat");
    }

    public static void unknownAction() {
        System.out.println("Unknown Action ❗");
    }
    
    public static void actionCompleted() {
    
    System.out.println();
        System.out.println("Done ✅");
    }

    public static void actionFailed(String why) {
        System.out.println();
        System.out.println(why + " ❗");
    }

}