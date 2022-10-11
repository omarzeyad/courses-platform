package io;

import java.util.Scanner;


public class InputSystem {

    public static Scanner consoleInput = new Scanner(System.in);

    public static String usernamePrompt() {
        System.out.print("Username ?> ");
        return consoleInput.nextLine();
    }

    public static String passwordPrompt() {
        System.out.print("Password ?> ");
        return consoleInput.nextLine();
    }

    public static int userLoginPrompt() {
        System.out.println();
        System.out.print("(0 to exit) ?> ");
        return Integer.parseInt(consoleInput.nextLine());
    }

    public static int userActionPrompt() {
        System.out.println();
        System.out.print("(0 to logout) ?> ");
        return Integer.parseInt(consoleInput.nextLine());
    }

    public static int accountTypePrompt() {
        System.out.println();
        System.out.println("User account type:");
        System.out.println("1. Instructor");
        System.out.println("2. Student");

        while(true) {
            System.out.println();
            System.out.print("(0 to go back) ?> ");
            int choice = Integer.parseInt(consoleInput.nextLine());
            switch(choice) {
            case 0:
            case 1:
            case 2:
                return choice;
            default:
                System.out.println("Unknown choice. Try again ❗");
            }
        }
    }

    public static boolean confirmActionPrompt() {
        System.out.println();
        System.out.println("Are you sure?");
        while(true) {
            System.out.print("(y/n) ?> ");
            String answer = consoleInput.nextLine().toLowerCase();
            switch(answer) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("Only y/n!. Try again ❗");
                System.out.println();
            }
        }
    }

    public static String courseNamePrompt() {
        System.out.println();
        System.out.print("Course Name: ");
        return consoleInput.nextLine();
    }

    public static String projectNamePrompt() {
        System.out.println();
        System.out.print("Project Name: ");
        return consoleInput.nextLine();
    }

    public static String chatMsgPrompt(String msg) {
        System.out.print(msg + ": ");
        return consoleInput.nextLine();
    }

    public static void waitForKeyPress() {
        System.out.print("Press enter to continue...");
        consoleInput.nextLine();
    }

    public static void clearConsole() {
        System.out.print("\033\143");
    }

}