package user;

import io.*;


public class Instructor extends User {

    public Instructor(String name, String pass) {
        username = name;
        password = pass;
    }

    public String whoAmI() {
        return "[Instructor] " + username;
    }

    public void displayActionsMenu() {
        LoginMenus.greetUser(whoAmI());
        ActionMenus.instructorActions();
    }

    public void takeAction() {
        int actionChoice = InputSystem.userActionPrompt();
        switch (actionChoice) {
        case 1 -> addCourse();
        case 2 -> removeCourse();
        case 3 -> showSubmittedProjects();
        case 4 -> changePassword();
        case 5 -> chat();
        case 0 -> logout();
        default -> ActionMenus.unknownAction();
        }
    }
    

    // 1.
    public void addCourse() {
        String course = InputSystem.courseNamePrompt();
        if( db.addNewCourse(course) ) {
            ActionMenus.actionCompleted();
        }
        else {
            ActionMenus.actionFailed(course + " already exists");
        }
    }

    // 2.
    public void removeCourse() {
        db.showAvailableCourses();

        String course = InputSystem.courseNamePrompt();
        if( db.removeCourse(course) )
            ActionMenus.actionCompleted();
        else
            ActionMenus.actionFailed(course + " does NOT exist");
    }

    // 3.
    public void showSubmittedProjects() {
        db.showProjectsReport();
    }

    // 5.
    public void chat() {
        db.displayChatHistory();
        String msg = InputSystem.chatMsgPrompt(whoAmI());
        db.sendMessage(whoAmI(), msg);
    }

}