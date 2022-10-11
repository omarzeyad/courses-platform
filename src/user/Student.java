package user;

import io.*;


public class Student extends User {

    public Student(String name, String pass) {
        username = name;
        password = pass;
    }

    public String whoAmI() {
        return "[Student] " + username;
    }

    public void displayActionsMenu() {
        LoginMenus.greetUser(whoAmI());
        ActionMenus.studentActions();
    }

    public void takeAction() {
        int actionChoice = InputSystem.userActionPrompt();
        switch (actionChoice) {
        case 1 -> registerToCourse();
        case 2 -> dropCourse();
        case 3 -> submitProject();
        case 4 -> showSubmittedProject();
        case 5 -> changePassword();
        case 6 -> chat();
        case 0 -> logout();
        default -> ActionMenus.unknownAction();
        }
    }
    

    // 1.
    public void registerToCourse() {
        db.showAvailableCourses();
        String course = InputSystem.courseNamePrompt();
        if(course.trim().isEmpty()) {
            ActionMenus.actionFailed("Choose a course to register");
            return;
        }

        if( db.registerStudentToCourse(course, username) )
            ActionMenus.actionCompleted();
        else
            ActionMenus.actionFailed(course + " does NOT exist");
    }

    // 2.
    public void dropCourse() {
        if( !db.registeredCourses(username).isEmpty() ) {
            String course = InputSystem.courseNamePrompt();
            if(course.trim().isEmpty()) {
                ActionMenus.actionFailed("Choose a course to drop");
                return;
            }

            if( db.dropStudentFromCourse(course, username) )
                ActionMenus.actionCompleted();
            else
                ActionMenus.actionFailed(username + " is NOT registered in " + course);
        }
        else {
            ActionMenus.actionFailed("Register a course first");
        }
    }

    // 3.
    public void submitProject() {
        String project = InputSystem.projectNamePrompt();
        if(project.trim().isEmpty()) {
            ActionMenus.actionFailed("Enter a project name to be submitted");
            return;
        }

        if( db.submitNewProject(username, project) )
            ActionMenus.actionCompleted();
        else
            ActionMenus.actionFailed(project + " already submitted");
    }

    // 4.
    public void showSubmittedProject() {
        System.out.print("You submitted: ");
        System.out.println(db.getSubmittedProjects(username));
    }

    // 6.
    public void chat() {
        db.displayChatHistory();
        String msg = InputSystem.chatMsgPrompt(whoAmI());
        db.sendMessage(whoAmI(), msg);
    }

}