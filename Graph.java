


/*
Name: Rahul Gopikannan
 */




import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


// graph class
public class Graph {

    // two hashmaps and int are created to hold the adjancency lists and nodes
    private Map<String, Node> nodes = new HashMap<>();
    private Map<Node, List<Edge>> adjacencyList = new HashMap<>();
    private int indexes = -1;


    // private node class for each label
    private class Node {
        private String label;

        public Node() {

        }

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }


    // private edge class using node objects
    private class Edge {
        private Node start;
        private Node dest;
        private int weight;

        // overloaded constructor to hold start, edge, and weight
        public Edge(Node from, Node to, int wt) {
            this.start = from;
            this.dest = to;
            this.weight = wt;
        }

        // accessor
        public int getWeight() {return weight; }

        @Override
        public String toString() {
            return start + " " + dest + " weight " + weight;
        }
    }


    // addNode function using string input / insertVertex
    public void addNode(String input) {
        // create a node object
        var node = new Node(input);


        // if a given key is not null, then add it to node
        nodes.putIfAbsent(input, node);
        // update adjacency list using same method
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    // then addEdge with all the parameters / insertEdge
    public void addEdge(String start, String end, int weight) {
        // create node object for start
        var from = nodes.get(start);

        // create node object for edge
        var to = nodes.get(end);

        // add it to adjancency list
        adjacencyList.get(from).add(new Edge(from, to, weight));
    }


    // checks if the key is present in the node
    public boolean hasNode(String name) {

        return nodes.containsKey(name);
    }


    // checks if a given route is an edge of another route
    public boolean hasEdge(String from, String to) {

        // if node is not present return null
        if (!hasNode(from) || !hasNode(to))
            return false;

            // else run a for loop
        else {
            // check if they are present in the adjancency list and return true
            for (int i = 0; i < adjacencyList.get(nodes.get(from)).size(); i++) {
                if (adjacencyList.get(nodes.get(from)).get(i).dest.label.equals(to))
                    return true;
            }
        }


        return false;
    }

    // remove node method / deleteVertex
    public void removeNode(String input) {
        // create a node object using input string
        var node = nodes.get(input);
        // if node is null return
        if (node == null) {
            return;
        }

        // else loop through adjacency list and find the node
        for (var temp : adjacencyList.keySet()) {
            // using remove method, delete the node
            adjacencyList.get(temp).remove(node);
        }

        // delete the node from adjacency list as well
        adjacencyList.remove(node);
        nodes.remove(node);
    }


    // returnWeight gives the weight of a specific route
    public int returnWeight(String from, String to) {


        int temp = 0;
        if (!hasNode(from) || !hasNode(to))
            return temp;

            // similar to hasEdge, we check through adjacency list and return thr weight
        else {
            for (int i = 0; i < adjacencyList.get(nodes.get(from)).size(); i++) {
                if (adjacencyList.get(nodes.get(from)).get(i).dest.label.equals(to))
                    temp = adjacencyList.get(nodes.get(from)).get(i).weight;
            }
        }

        return temp;

    }


    // deleting the edges / deleteEdge
    public void removeEdge(String start, String edge) {
        // make node objects
        var from = nodes.get(start);
        var to = nodes.get(edge);

        // if null return
        if (from == null || to == null) return;

        // else remove the edges from adjacency list
        adjacencyList.get(from).remove(to);
    }



    // print the adjacency list
    public void print() {
        for (var node : nodes.values()) {
            System.out.println(node + ": " + adjacencyList.get(node));
        }
    }




}
