
/**
 *
 * {@code DependencyManager}
 *
 * This java class is used for identifying each connected component of the graph.
 * This class is also responsible for creationg the edge between the two nodesa and then traversing the graph
 * In this class we have used DFS for finding the stand alone modulea adn for checking whether the
 * graph contains the cycle or not
 *
 * This class includes following methods
 *  - void addEdge(String source, String destination)
 *  - Set<String> DFSCheck(String v, HashMap<String, Boolean> visited, Set<String> finalList)
 *  - Set<Set<String>> graphConnectedComponents()
 *  - private boolean isCyclicCheck(String i, HashMap<String, Boolean> visited, HashMap<String, Boolean> recursionStack)
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-02-27
 * @version 1.0.0
 * @since 1.0,0
 */

import javax.sound.midi.Soundbank;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;

public class StandaloneModule {
    HashMap<String ,ArrayList<String>> graph;
    Set<Set<String>> modules = new HashSet<>();

    StandaloneModule() {
        this.graph = new HashMap<>();
    }

    /**
     * {@code addClass} This method is responsible for creating the egde between the nodes
     *
     * @param source and destination takes source and destination as input for creating the edge between them
     *
     */
    public void addEdge(String source, String destination) {
        ArrayList<String> sourceList = this.graph.getOrDefault(source, new ArrayList<String>());
        ArrayList<String> destinationList = this.graph.getOrDefault(destination, new ArrayList<String>());
        sourceList.add(destination);
        destinationList.add(source);
        this.graph.put(source, sourceList);
        this.graph.put(destination, destinationList);
    }
    /**
     * This method search is root by depth first search.
     *
     * It checks weather the node is visited or not.
     */
    private Set<String> DFSCheck(String v, HashMap<String, Boolean> visited, Set<String> finalList) {
        visited.put(v, true);
        finalList.add(v);
        for (String x : graph.get(v)) {
            if (!visited.getOrDefault(x, false)) {
                DFSCheck(x, visited, finalList);
            }
        }
        return finalList;
    }

    /**
     * It checks and connects visitedNodes.
     *
     * If the node is visited by using HashMap we store it's key and value.
     */    


    public Set<Set<String>> graphConnectedComponents() {
        HashMap<String, Boolean> visitedNodes = new HashMap<>();
        Set<Set<String>> connectedComponentList = new HashSet<>();
        for (String key : graph.keySet()) {
            if (!visitedNodes.getOrDefault(key, false)) {
                connectedComponentList.add(DFSCheck(key, visitedNodes, new HashSet<>()));
            }
        }
        return connectedComponentList;
    }

    /**
     * This method checks weather the nodes of the graph is visited and forms cycle or not.
     * 
     * It also checks recursion stack and visitedNodes are null or not.
     * 
     */




    private boolean isCyclicCheck(String i, HashMap<String, Boolean> visited,
                                 HashMap<String, Boolean> recursionStack)
    {
        if (recursionStack.get(i) != null || visited.get(i) != null){
            if (recursionStack.get(i)){
                return true;
            }
            if(visited.get(i)){
                return false;
            }
        }

        visited.put(i, true);

        recursionStack.put(i, true);

        for (String x : graph.get(i)) {
            if (isCyclicCheck(x, visited, recursionStack))
            {
                return true;
            }
        }
        recursionStack.put(i, false);
        return false;
    }

/**
     * It checks wheather the graph is dependent on the the other classes or not.
     *
     * It returns true if the cyclic check is done and false if it is not cyclic
     */    

    public boolean Cyclic()
    {
        HashMap<String, Boolean> visited = new HashMap<>();
        HashMap<String, Boolean> recursionStack = new HashMap<>();

        for (String key : graph.keySet()){
            if (isCyclicCheck(key, visited, recursionStack))
                return true;
        }
        return false;
    }
}