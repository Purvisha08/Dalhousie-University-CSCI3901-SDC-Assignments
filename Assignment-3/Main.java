import javax.sound.midi.Soundbank;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String addClass = "1";
        String addClassWithDependencies = "2";
        String buildOrder = "3";
        String standaloneModules = "4";
        String highUseClasses = "5";
        String hasDependencyCycle = "6";
        String quitCommand = "7";
        String print = "8";

        String userCommand = "";
        Scanner userInput = new Scanner(System.in);
        DependencyManager dependencyManager = new DependencyManager();

        System.out.println("\nCommands available");
        System.out.println();
        System.out.println("Press "+addClass + " to add class");
        System.out.println("Press "+addClassWithDependencies + " to add class with dependencies");
        System.out.println("Press "+buildOrder + " to build order");
        System.out.println("Press "+standaloneModules + " to identify standalone module");
        System.out.println("Press "+highUseClasses + " to identify high use of classes");
        System.out.println("Press "+hasDependencyCycle + " to check if dependency cycle exists or not");
        System.out.println("Press "+quitCommand+" to Quit");
        System.out.println("Press "+print+ " to Print");
        System.out.println();

        do {
            userCommand = userInput.nextLine();
            if (userCommand.equalsIgnoreCase(addClass)) {
                System.out.println("Enter The class name");
                String className = userInput.next();
                dependencyManager.addClass(className);
                System.out.println("Classes Added Successfully");
            } else if (userCommand.equalsIgnoreCase(addClassWithDependencies)) {
                Set<String> setOFClasses = new HashSet<>();
                System.out.println("Enter The class name");
                String className = userInput.nextLine();
                System.out.println("On how many clases does class "+className+ " depends On");
                int numberOfClass = userInput.nextInt();
                System.out.println("Enter the name of classes");
                for (int i=0; i<numberOfClass; i++){
                    String dependentClassName = userInput.next();
                    setOFClasses.add(dependentClassName);
                }
                dependencyManager.addClass(className, setOFClasses);
                System.out.println("Classes Added Successfully");

            } else if (userCommand.equalsIgnoreCase(buildOrder)) {
                System.out.println(dependencyManager.buildOrder());
            }else if (userCommand.equalsIgnoreCase(standaloneModules)) {
                System.out.println(dependencyManager.standaloneModules());
            }else if (userCommand.equalsIgnoreCase(highUseClasses)) {
                System.out.println(dependencyManager.highUseClasses(3));
            }else if (userCommand.equalsIgnoreCase(hasDependencyCycle)) {
                System.out.println(dependencyManager.hasDependencyCycle());
            }
            else if (userCommand.equalsIgnoreCase(print)) {

            }else if (userCommand.equalsIgnoreCase(quitCommand)) {
                System.out.println(userCommand);
            } else {
                System.out.println("Bad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase("quit"));
    }
}
