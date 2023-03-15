/**
 *
 * {@code DependencyManager}
 *
 * This class will collect the inforrmation on how different files depends on one
 * another and will then it will report on the status of the file.
 * This class includes following methods
 * - Constructor that accepts no arguments
 * - boolean addClass( String className ) throws IllegalArgumentException
 * - boolean addClass( String className, Set<String> dependencies ) throws IllegalArgumentException
 * - List<String> buildOrder () throws IllegalStateException
 * - Set<Set<String>> standaloneModules()
 * - List<String> highUseClasses( int listMax ) throws IllegalArgumentException
 * - boolean hasDependencyCycle (
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-02-27
 * @version 1.0.0
 * @since 1.0,0
 */

import javax.swing.plaf.PanelUI;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.lang.reflect.Modifier;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class DependencyManager {
    public Map<String, List<String>> classNameWithDependencies; //stores the class name as key and dependent classes as values
    public Map<String, Integer> keySize; // Stores the class name as key and number of dependent classes as value
    public List<String> nodes; // Used to store the classes
    public List<String> dependentClassWithClassName; // used to store the classes and dependent classes together
    public List<String> dependentClass; // user to store only dependednt classes
    public Graph graphManager; // Used to create a fraph
    public List<String> orderList; // User to store the order elemnts
    public StandaloneModule standaloneModule;// userd to create the object of standalone module
    public List<String> classNameList; // used tp store the class name

    // Constructor helps us to create the object
    public DependencyManager() {
        classNameWithDependencies = new HashMap<>();
        keySize = new HashMap<>();
        nodes = new ArrayList<String>();
        graphManager = new Graph();
        orderList = new ArrayList<>();
        standaloneModule = new StandaloneModule();
        classNameList = new ArrayList<>();
    }


    /**
     * {@code addClass} This method add the classes to the list
     *
     * @throws IllegalArgumentException
     *         if className cannot be represent a class or if the set of dependencies is unusable
     *
     * @param className takes className as input
     *
     * @return true if the className was stores in the list. false if the className is null or empty or blank
     *
     */
    public boolean addClass(String className) throws IllegalArgumentException{
        if (className == null){
            System.out.println("Class name cannot be null");
            return false;
        }

        if (className.isEmpty() || className.isBlank()){
            System.out.println("Class name cannot be empty");
            return false;
        }
        classNameList.add(className);
        System.out.println("Class added Successfully");
        return true;
    }



    /**
     * {@code addClass} This method add the classes with its dependences
     *
     * @throws IllegalArgumentException
     *         if className cannot be represent a class or if the set of dependencies is unusable
     *
     * @param className takes className and dependent classes as input
     *
     * @return true if the className and depeendent classname was stores in the list. False if the className is null or empty or blank
     *
     */
    public boolean addClass(String className, Set<String> dependencies ) throws IllegalArgumentException{

        // Returns false if classname is null
        if(className == null){
            System.out.println("Class name cannot be null");
            return false;
        }

        if (className.isEmpty() || className.isBlank()){
            System.out.println("Class name cannot be empty");
            return false;
        }

        if (dependencies.size() == 0){
            System.out.println("Set of dependent class cannot be empty");
            return false;
        }

        // if class name is present in the classNameList the it will allow to perform the further operation
        if(classNameList.contains(className)) {
            keySize.put(className, dependencies.size());
            dependentClassWithClassName = new ArrayList<>();
            dependentClass = new ArrayList<>();

            dependentClassWithClassName.addAll(dependencies);
            dependentClassWithClassName.add(className);

            dependentClass.addAll(dependencies);

            classNameWithDependencies.put(className, dependentClass);
            nodes.addAll(dependentClassWithClassName);

            graphManager.nodes(nodes);

            for (int i = 0; i < dependentClass.size(); i++) {
                graphManager.addEdge(className, dependentClass.get(i));
                standaloneModule.addEdge(className, dependentClass.get(i));
            }

            // Return true if the further operation are perfpormed on the classname
            return true;
        } else {

            // returns false if class name is not present in the list
            System.out.println("Class is not present in the list. Add the class first");
            return false;
        }
    }



    /**
     * {@code buildOrder} This methpd is used to get all the class name in an order for which we could compile the objects and, in
     * that compilation, when we reach a class in the sequence then all of the classes that it depends
     * upon have already been compiled
     *
     * @throws IllegalArgumentException
     *         If there is a cycle of dependencies
     *
     * @return Return a list of classes
     *
     */
    public List<String> buildOrder() throws IllegalStateException{
        graphManager.topoSort(); // Topological Sort is use to get the sequence of classes
        Collections.reverse(graphManager.order); // Revers the sequence of classes
        return graphManager.order; // retrun the List of classes
    }



    /**
     * {@code standaloneModules} This method is used to identify group of class files that can be compiled into standalone executable
     * programs.
     *
     * @return Return A set of class files can be compiled into a standalone executable program
     *
     */
    Set<Set<String>> standaloneModules(){
        return standaloneModule.graphConnectedComponents();
    }


    /**
     * {@code highUseClasses} This Class Report the top “listMax” classes, in decreasing order of dependencies
     *
     * @param listMax takes listMax int as an input
     *
     * @return Return list od classes
     *
     */
    public List<String> highUseClasses( int listMax ) throws IllegalArgumentException {
        if (listMax < nodes.size()){
            List<String> sortedClasses = new ArrayList<>(sortByValue(keySize).keySet());
            List<String> returnedMap = new ArrayList<>();
            Collections.reverse(sortedClasses);
            for (int i=0; i<listMax; i++){
                returnedMap.add(sortedClasses.get(i));
            }
            return returnedMap;
        } else{
            System.out.println("Your have entered wrong listMax value.");
            return null;
        }
    }

    /**
     * {@code hasDependencyCycle} This method is used to identify the cyclic dependency in the graph
     *
     *
     * @return It will Return true if there exists one or more dependency cycles and return
     * false if there are no dependency cycles
     *
     */
    public boolean hasDependencyCycle (){
        return standaloneModule.Cyclic();
    }


    /**
     * {@code sortByValue} This method is used to sort the map elemensts
     *
     *
     * @param UnsortedMap takes the unsorted map elements as input
     *
     * @return It will retrun the sorted map elements
     *
     */
    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap){
        List<Map.Entry<String, Integer>> elements = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(elements, Comparator.comparing(Map.Entry::getValue));
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : elements) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
