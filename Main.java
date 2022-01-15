

/*
Name: Rahul Gopikannan
 */


import java.util.*;
import java.io.*;
import java.lang.String;



// This program is used to read in graph and routes file and compare if the routes are valid from graphs.
public class Main {

    public static void main(String[] args) throws IOException {

        // prompting for graph file
        System.out.println("Enter graph file");
        Scanner scan = new Scanner(System.in);
        String graphInput = scan.next();
        File graph = new File(graphInput);
        Scanner scGraph = new Scanner(graph);


        // Entering routes file
        System.out.println("Enter route file name");
        String routeInput = scan.next();
        File route = new File(routeInput);
        Scanner scRoute = new Scanner(route);


        // this string will hold each line in the routes file
        String routesLine;

        // creating an arraylist to save graph vertex
        ArrayList<Object> vertex = new ArrayList<>();


        // this will be the graph object where we will save the graph data
        Graph graph2 = new Graph();

        // this string is for each line in the graphs file
        String graphsLine;

        // if the graph file is opening properly
        if (graph.canRead()) {
            // run a while loop for each line then pass it into a parser function
            while (scGraph.hasNext()) {
                graphsLine = scGraph.nextLine();
                graphsParser(scGraph, graphsLine, vertex, graph2);
            }


            // this list will be used to store the final driver and total weight and use it to sort
            List<sorter> listSort = new ArrayList<>();


            // if the routes file can read, each line is parsed into a loop to parse function
            if (route.canRead()) {
                // routes file scanner
                while (scRoute.hasNext()) {
                    routesLine = scRoute.nextLine();
                    routesParser(scRoute, routesLine, graph2, listSort);
                }


                // collections.sort will sort the list based off totalweight and use names if there is a tie
                // tostring method from sort class is used
                Collections.sort(listSort);
                for (int i = 0; i < listSort.size(); i++) {
                    System.out.println(listSort.get(i).toString());

                }
            }
        }

        // closing both the input files
        scGraph.close();
        scRoute.close();
    }


    // this function is used to parse the graph file and add it into graph objects
    public static void graphsParser(Scanner scGraph, String graphsLine, ArrayList<Object> vertex, Graph graph) {

        // need to get the space of the first whitespace
        int firstSpace;

        // firstLocation stores the starting vertex
        String firstLocation;
        // remaining vertex gets the remaining edges
        String remainingVertex;


        // parsing here using indexOf "space" characters and substring
        firstSpace = graphsLine.indexOf(" ");
        firstLocation = graphsLine.substring(0, firstSpace);

        // if the graph object doesn't contain the firstLocation string then we add
        if (!(graph.hasNode(firstLocation))) {
            graph.addNode(firstLocation);
        }

        remainingVertex = graphsLine.substring(firstSpace + 1);


        // multiple int variables are created to assist in the parsing process
        int newWeightIndex;
        int whiteSpaceCount = 0;
        int temp = -2;
        int index = 0;
        int weight;
        int bugFixer = 0;

        // for loop to run through each letter of remainingVertex
        for (int i = 0; i < remainingVertex.length(); i++) {
            // two strings - locVertex will hold the edge name and weightNumber holds its weight
            String locVertex = " ";
            String weightNumber = " ";

            // checking for first comma to split edge and weight
            if (remainingVertex.charAt(i) == ',') {

                // then find its index
                index = remainingVertex.indexOf(",", temp);

                // find the index of the whitespace after the first pair
                newWeightIndex = remainingVertex.indexOf(" ", index);

                // newWeightIndex == -1 for the last string because there's no whitespace and it results in -1
                if (newWeightIndex == -1) {

                    // weight number stores the weight
                    weightNumber = remainingVertex.substring(index + 1);

                    // weight is used to convert it to int
                    weight = Integer.parseInt(weightNumber);

                    // find the whitespace before the last string
                    bugFixer = remainingVertex.indexOf(" ", temp);

                    // parse it into locVertex
                    locVertex = remainingVertex.substring(bugFixer + 1, index);

                    // if the node isn't present we add it
                    if (!(graph.hasNode(locVertex))) {
                        graph.addNode(locVertex);
                    }

                    // Starting, edge, and weight is added
                    graph.addEdge(firstLocation, locVertex, weight);

                    temp = index + 1;

                }
                // this else statement activates for every statement besides last string
                else {
                    // likewise weight gets parsed for each term
                    weightNumber = remainingVertex.substring(index + 1, newWeightIndex);
                    weight = Integer.parseInt(weightNumber);


                    bugFixer = remainingVertex.indexOf(" ", temp);

                    // locVertex is parsed for the string edges
                    if (bugFixer < index) {
                        locVertex = remainingVertex.substring(bugFixer + 1, index);
                    } else {
                        locVertex = remainingVertex.substring(temp + 2, index);
                    }


                    // if not present in the node object we add
                    if (!(graph.hasNode(locVertex))) {
                        // ADDING edges to Node for each line
                        graph.addNode(locVertex);
                    }


                    // Starting, edge, and weight is added
                    graph.addEdge(firstLocation, locVertex, weight);

                    temp = index + 1;


                }


            }
        }

    }


    // this function parses sample routes function
    public static void routesParser(Scanner scRoute, String routesLine, Graph graph, List<sorter> listSorter) {

        // used to find the index of driver
        int nameSpace;
        int routeSpace;

        // totalWeight of each path
        int totalWeight = 0;

        // parsed driver and remaining route
        String parsedName;
        String parsedRoute;


        // parsing here using indexOf "space" characters and substring
        nameSpace = routesLine.indexOf(" ");
        parsedName = routesLine.substring(0, nameSpace);


        // remaining route
        parsedRoute = routesLine.substring(nameSpace + 1);


        // respective int variables for parsing
        int temp = 0;
        int index = 0;
        int weight;
        int index1 = 0;

        // for loop to run through each element
        for (int i = 0; i < parsedRoute.length(); i++) {
            // will be used to get two routes - starting route, ending route
            String route = " ";
            String route2 = " ";

            // checking for space
            if (parsedRoute.charAt(i) == ' ') {
                index = parsedRoute.indexOf(" ", temp);

                // first route gets parsed here
                route = parsedRoute.substring(temp, index);

                // finding index of second space
                index1 = parsedRoute.indexOf(" ", index + 1);

                // index1 == -1 for last route
                if (index1 == -1) {
                    // second route gets parsed
                    route2 = parsedRoute.substring(index + 1);

                    // check if the graph has specific edge using hasEdge method
                    if (graph.hasEdge(route, route2)) {

                        // if true, return its weight and add it total weight
                        totalWeight += graph.returnWeight(route, route2);

                    } else {

                        // else it means the edge doesn't exist meaning its invalid
                        totalWeight += graph.returnWeight(route, route2);
                        // totalWeight = 0
                        totalWeight = 0;
                        // we add the driver and weight to the list and return
                        listSorter.add(new sorter(parsedName, totalWeight));
                        return;

                    }
                }
                // this else statement runs if the route is not last string
                else {
                    // route2 gets parsed
                    route2 = parsedRoute.substring(index + 1, index1);

                    temp = index + 1;

                    // checks for edge again
                    if (graph.hasEdge(route, route2)) {

                        // if yes, add it to totalWeight
                        totalWeight += graph.returnWeight(route, route2);


                    } else {
                        // else invalid repeat the process
                        totalWeight += graph.returnWeight(route, route2);
                        totalWeight = 0;
                        listSorter.add(new sorter(parsedName, totalWeight));
                        return;

                    }
                }
            }

        }


        // overloaded constructor adding driver + total weight into sort class
        sorter test = new sorter(parsedName, totalWeight);


        // array driver/totalweight to arraylist (valid drivers)
        listSorter.add(new sorter(parsedName, totalWeight));

    }
}

    // sort class implemented to help with sorting
    class sorter implements Comparable<sorter> {

        // names and total weight attributes
        private String driver;
        private int totalWeight;

        // overloaded constructor
        public sorter(String dri, int totWei) {
            this.driver = dri;
            this.totalWeight = totWei;
        }

        // accessor
        public int getTotalWeight() {return totalWeight; }

        public String getDriver() {return driver; }

        // toString displays the driver and weight in proper format
        @Override
        public String toString() {
            if (this.totalWeight == 0) {
                return this.getDriver() + " " + this.getTotalWeight() + " invalid";
            }
            else {
                return this.getDriver() + " " + this.getTotalWeight() + " valid";
            }
        }

        // compareTo compares the totalWeight, and checks for alphabets in case weights are same
        @Override
        public int compareTo(sorter A) {
            if (this.totalWeight != A.getTotalWeight()) {
                return this.getTotalWeight() - A.getTotalWeight();
            }
            return this.driver.compareTo(A.getDriver());
        }


        // used for string comparison
        public int compareToString(sorter B) {
           if (this.getDriver().equals(B.getDriver()))
               return 0;
           else if (this.getDriver().compareTo(B.getDriver()) > 0)
               return 1;
           else
               return -1;
        }
    }

