package platform;

import user.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Collections;


public class Database {

    //------------------------------
    // STATIC INNER CLASSES TO MODEL
    // ENTITIES IN THE DATABASE
    //------------------------------

    private static class UserDB {

        private String filePath; // the path where storage text file exists on hard drive
        private TreeMap<String, String> buffer; // interfaces between actual storage and the program;
                                                // data takes the form username,password

        public UserDB(String path) {
            filePath = path;
            buffer = new TreeMap<>();
        }

        public void load() throws IOException {
            Scanner fileScanner = new Scanner( new File(filePath) );
            fileScanner.useDelimiter("[,\n]");
            while(fileScanner.hasNext()) {
                String username = fileScanner.next();
                String password = fileScanner.next();

                buffer.put(username, password);
            }
            fileScanner.close();
        }

        public void store() throws IOException {
            PrintWriter fileWriter = new PrintWriter( new File(filePath) );
            for(String username : buffer.keySet()) {
                String password = buffer.get(username);

                fileWriter.println(username + "," + password);
            }
            fileWriter.close();
        }

        public boolean contains(User user) {
            String username = user.getUsername();
            String password = user.getPassword();

            return buffer.containsKey(username) && buffer.get(username).equals(password);
        }

        public boolean contains(String username) {
            return buffer.containsKey(username);
        }

        public boolean add(String username, String password) {
            if(!contains(username)) {
                buffer.put(username, password);
                return true;
            }
            return false;
        }

        public boolean remove(String username) {
            if(contains(username)) {
                buffer.remove(username);
                return true;
            }
            return false;
        }

        public boolean isEmpty() {
            return buffer.isEmpty();
        }

        public void clear() {
            buffer.clear();
        }

        public void update(User user) {
            String username = user.getUsername();
            String password = user.getPassword();

            buffer.put(username, password);
        }

    }

    private static class MaterialDB {

        private String  filePath; // the path where storage text file exists on hard drive
        private TreeMap<String, ArrayList<String>> buffer; // interfaces between actual storage and the program
                                                           // data takes the form name,[values,...]

        public MaterialDB(String path) {
            filePath = path;
            buffer = new TreeMap<>();
        }

        public void load() throws IOException {
            Scanner fileScanner = new Scanner( new File(filePath) );
            while(fileScanner.hasNext()) {
                String[] line = fileScanner.nextLine().split(",");

                String name = line[0];
                ArrayList<String> values = new ArrayList<>();
                if(line.length > 1)
                    Collections.addAll(values, Arrays.copyOfRange(line, 1, line.length));

                // values would be the [values,...] or EMPTY
                buffer.put(name, values);
            }
            fileScanner.close();
        }

        public void store() throws IOException {
            PrintWriter fileWriter = new PrintWriter( new File(filePath) );
            for(String name : buffer.keySet()) {
                String values = String.join(",", buffer.get(name));

                fileWriter.println(name + "," + values);
            }
            fileWriter.close();
        }

        public String getNames() {
            return buffer.keySet().toString();
        }

        public ArrayList<String> getValuesAssociatedToName(String name) {
            return buffer.get(name);
        }

        public void printReport() {
            for(String name : buffer.keySet()) {
                System.out.println(name + " submitted: " + buffer.get(name));
            }
        }

        public ArrayList<String> getNamesAssociatedToValue(String value) {
            ArrayList<String> names = new ArrayList<>();
            for(String name : buffer.keySet()) {
                if( buffer.get(name).contains(value) )
                    names.add(name);
            }
            return names;
        }

        public boolean contains(String name) {
            return buffer.containsKey(name);
        }

        public boolean add(String name) {
            if(!contains(name)) {
                buffer.put(name, new ArrayList<String>());
                return true;
            }
            return false;
        }

        public boolean remove(String name) {
            if(contains(name)) {
                buffer.remove(name);
                return true;
            }
            return false;
        }

        public boolean associateToName(String name, String value) {
            if(contains(name)) {
                buffer.get(name).add(value);
                return true;
            }
            return false;
        }

        public boolean removeAssociationToName(String name, String value) {
            if(contains(name)) {
                buffer.get(name).remove(value);
                return true;
            }
            return false;
        }

        public void clear() {
            buffer.clear();
        }

    }

    private static class ChatDB {

        // the path where storage text file exists on hard drive
        private static final String filePath = "databases/Chat.txt";

        public static void chatHistory() {
            System.out.println();
            try {
                Scanner fileScanner = new Scanner( new File(filePath) );
                while(fileScanner.hasNext()) {
                    System.out.println(fileScanner.nextLine());
                }
                fileScanner.close();
            }
            catch(IOException e) {
                System.out.println("Database connection error ❗");
                e.printStackTrace();
                System.exit(1);
            }
        }

        public static void clear() {
            try {
                // open the file for writing instead of appending
            PrintWriter fileWriter = new PrintWriter( new File(filePath) );
            fileWriter.close();
            }
            catch(IOException e) {
                System.out.println("Database connection error ❗");
                e.printStackTrace();
                System.exit(1);
            }
        }

        public static void send(String identity, String msg) {
            try {
                PrintWriter fileWriter = new PrintWriter( new FileWriter(filePath, true) );
                if(!msg.trim().isEmpty()) {
                    fileWriter.println(identity + ": " + msg);
                }
                fileWriter.close();
            }
            catch(IOException e) {
                System.out.println("Database connection error ❗");
                e.printStackTrace();
                System.exit(1);
            }
        }

    }

    //------------------------
    // DATABASE FUNCTIONALITY
    //-----------------------

    // users
    private UserDB adminsAccounts;
    private UserDB instructorsAccounts;
    private UserDB studentsAccounts;

    // materials
    private MaterialDB courses;
    private MaterialDB projects;

    public Database() {
        // users
        adminsAccounts      = new UserDB("databases/AdminsAccounts.txt");
        instructorsAccounts = new UserDB("databases/InstructorsAccounts.txt");
        studentsAccounts    = new UserDB("databases/StudentsAccounts.txt");

        // materials
        courses  = new MaterialDB("databases/AvailableCourses.txt");
        projects = new MaterialDB("databases/SubmittedProjects.txt");
    }

    public void connect() {
        try {
            // users
            adminsAccounts.load();
            instructorsAccounts.load();
            studentsAccounts.load();

            // materials
            courses.load();
            projects.load();
        }
        catch(IOException e) {
            System.out.println("Database connection error ❗");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void disconnect() {
        try {
            // users
            adminsAccounts.store();
            instructorsAccounts.store();
            studentsAccounts.store();

            // materials
            courses.store();
            projects.store();
        }
        catch(IOException e) {
            System.out.println("Database connection error ❗");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean hasNoUsers() {
        return instructorsAccounts.isEmpty() && studentsAccounts.isEmpty();
    }

    //--------------------------------------------------------------
    // FUNCTIONALITY NEEDED BY OTHER CLASSES TO ACCESS THE DATABASE
    //--------------------------------------------------------------

    //---------------------
    // users functionality
    //---------------------

    public boolean userExists(User user) {
        if(user instanceof Admin) {
            return adminsAccounts.contains(user);
        }
        else if(user instanceof Instructor) {
            return instructorsAccounts.contains(user);
        }
        else if(user instanceof Student) {
            return studentsAccounts.contains(user);
        }

        return false;
    }

    public boolean addInstructor(String username, String password) {
        return instructorsAccounts.add(username, password);
    }

    public boolean removeInstructor(String username) {
        return instructorsAccounts.remove(username);
    }

    public boolean addStudent(String username, String password) {
        projects.add(username);
        return studentsAccounts.add(username, password);
    }

    public boolean removeStudent(String username) {
        return studentsAccounts.remove(username);
    }

    public void reset() {
        // users (except Admins, of course)
        instructorsAccounts.clear();
        studentsAccounts.clear();

        // materials
        courses.clear();
        projects.clear();

        // chat
        ChatDB.clear();
    }

    public void updateUser(User user) {
        if(user instanceof Admin) {
            adminsAccounts.update(user);
        }
        else if(user instanceof Instructor) {
            instructorsAccounts.update(user);
        }
        else if(user instanceof Student) {
            studentsAccounts.update(user);
        }
    }

    //-------------------------
    // materials functionality
    //-------------------------

    public void showAvailableCourses() {
        System.out.print("Available courses: ");
        System.out.println(courses.getNames());
    }

    public boolean registerStudentToCourse(String course, String username) {
        return courses.associateToName(course, username);
    }

    public boolean dropStudentFromCourse(String course, String username) {
        return courses.removeAssociationToName(course, username);
    }

    public boolean addNewCourse(String course) {
        return courses.add(course);
    }

    public boolean removeCourse(String course) {
        return courses.remove(course);
    }

    public ArrayList<String> registeredCourses(String username) {
        System.out.println();
        ArrayList<String> registeredCourses =
            courses.getNamesAssociatedToValue(username);
        System.out.println("Registered courses: " + registeredCourses);
        return registeredCourses;
    }

    public boolean submitNewProject(String username, String project) {
        return projects.associateToName(username, project);
    }

    public String getSubmittedProjects(String username) {
        ArrayList<String> projectsNames = projects.getValuesAssociatedToName(username);
        return (projectsNames == null) ?
            "None!" : projects.getValuesAssociatedToName(username).toString();
    }

    public void showProjectsReport() {
        projects.printReport();
    }

    public void displayChatHistory() {
        ChatDB.chatHistory();
    }

    public void sendMessage(String identity, String msg) {
        ChatDB.send(identity, msg);
    }

}