

/**
 *
 * {@code Graph
 *
 *
 *This java class is used to create the nodes and edges for the DFS algorithm
 *Following are the methods of the java file. In this filke we also perform topological sort
 * This topological sort is used to create a build order meanthod.
 *
 * nodes(List<String> vertices)
 * addEdge(String src, String dest)
 * void sortNodes(String node, Stack stackOfNode)
 * void topoSort()
 *   
 * @author Purvisha Patel (B00912611)
 * Created on 2022-02-27
 * @version 1.0.0
 * @since 1.0,0
 */


import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Graph{
    private HashMap<String, ArrayList<String>> edges;
    private List<String> nodes;
    private List<String> nodeVisited;
    private ArrayList<String> edgeList;
    public List<String> order = new ArrayList<>();

// It stores the visitedNodes into an ArrayList using HashMap

    public Graph() {
        edges = new HashMap<>();
        nodeVisited = new ArrayList<String>();
    }

// Vertices are stored in Nodes

    public void nodes(List<String> vertices){
        nodes = vertices;
    }

// This method adds Edge of nodes from source to destinaton.

    public void addEdge(String src, String dest){
        if(!edges.containsKey(src)){
            edgeList = new ArrayList<String>();
        } else {
            edgeList = edges.get(src);
        }
        edgeList.add(dest);
        edges.put(src,edgeList);
    }


     

    public void topoSort(){
        Stack<String> stackOfNodes = new Stack<String>();
        for (String x : nodes){
            if(!nodeVisited.contains(x)){
                sortNodes(x, stackOfNodes);
            }
        }
        while(!stackOfNodes.empty()){
            order.add(stackOfNodes.pop());
        }
    }

    public void sortNodes(String node, Stack stackOfNode){
        nodeVisited.add(node);
        if(edges.get(node)!=null){
            Iterator<String> edgeIterator = edges.get(node).iterator();
            String neighborNode;
            while(edgeIterator.hasNext()){
                neighborNode = edgeIterator.next();
                if(!nodeVisited.contains(neighborNode)){
                    sortNodes(neighborNode,stackOfNode);
                }
            }
        }
        stackOfNode.push(node);
    }
}