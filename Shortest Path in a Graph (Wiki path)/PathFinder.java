import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.Deque;
import java.util.Iterator;
import java.util.Random;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

/**
* Class that find the shortest path and path length between two wikipedia articles.
* If requested, this path goes through an intermediate article. 
*/
public class PathFinder {
    // wikiGraph stores articles and the links between them.
    private UnweightedGraph wikiGraph;
    // maps allows translation between vertex numbers and article names
    private Map<Integer,String> vertexToName;
    private Map<String,Integer> nameToVertex;
    // stores the number of vertices
    private int numberOfVertices;

    
    /**
     * Constructs a PathFinder that represents the graph with nodes (vertices) specified as in
     * nodeFile and edges specified as in edgeFile.
     * @param nodeFile name of the file with the node names
     * @param edgeFile name of the file with the edge names
     */
    public PathFinder(String nodeFile, String edgeFile){
        // initializes instance variables
        wikiGraph = new MysteryUnweightedGraphImplementation(true);
        vertexToName = new HashMap<Integer,String>();
        nameToVertex = new HashMap<String,Integer>();
        numberOfVertices = 0;
        
        File articles = new File(nodeFile);
        // Scans the file if the file path is correctly formatted.
        // If not, returns an error message.
        Scanner scanner1 = null;
        try {
            scanner1 = new Scanner(articles);
        } catch (FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }
        
        // creates vertex for each article
        while (scanner1.hasNextLine()) {
            String s = scanner1.nextLine();
            // skips comments and empty lines on top 
            if (!s.contains("#") && !s.isEmpty()){
                try {
                    // decodes the URL name
                    String readableString = java.net.URLDecoder.decode(s, "UTF-8");
                    // updates graph and maps with new vertex
                    int vertexNum = wikiGraph.addVertex();
                    vertexToName.put(Integer.valueOf(vertexNum), readableString);
                    nameToVertex.put(readableString, Integer.valueOf(vertexNum));
                    // number of vertices increase for each new vertex
                    numberOfVertices++;
                } catch (UnsupportedEncodingException e){
                    System.err.println(e);
                    System.exit(1);
                }
            }
        }
        
        File links = new File(edgeFile);
        // Scans the file if the file path is correctly formatted.
        // If not, returns an error message.
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(links);
        } catch (FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }
        
        // creates edges between articles
        while(scanner2.hasNextLine()){
            String s = scanner2.nextLine();
            // skips comments and empty lines
            if (!s.contains("#") && !s.isEmpty()){
                try {
                    // splits articles into an array
                    String[] articleNames= s.split("	");
                    // decodes the URL name
                    String readableString1 = java.net.URLDecoder.decode(articleNames[0], "UTF-8");
                    String readableString2 = java.net.URLDecoder.decode(articleNames[1], "UTF-8");
                    // updates graph and maps with new vertex
                    int vertex1 = nameToVertex.get(readableString1);
                    int vertex2 = nameToVertex.get(readableString2);
                    wikiGraph.addEdge(vertex1, vertex2);
                } catch (UnsupportedEncodingException e){
                    System.err.println(e);
                    System.exit(1);
                }
                
            }
        }
    }
    

    /**
     * Returns the length of the shortest path from node1 to node2. If no path exists,
     * returns -1. If the two nodes are the same, the path length is 0.
     * @param node1 name of the starting article node
     * @param node2 name of the ending article node
     * @return length of shortest path
     */
    public int getShortestPathLength(String node1, String node2){
        List<String> pathList = this.getShortestPath(node1, node2);
        // path length equals number of articles minus 1
        return (pathList.size() - 1);
    }
        
    
    
    /**
     * Returns a shortest path from node1 to node2, represented as list that has node1 at
     * position 0, node2 in the final position, and the names of each node on the path
     * (in order) in between. If the two nodes are the same, then the "path" is just a
     * single node. If no path exists, returns an empty list.
     * @param node1 name of the starting article node
     * @param node2 name of the ending article node
     * @return list of the names of nodes on the shortest path
     */
    public List<String> getShortestPath(String node1, String node2){
        // Queue is for breadth-first traversal algorithm
        Queue<int[]> vertexQueue = new ArrayDeque<int[]>();
        // creates a stack for vertices visited in traversal
        Deque<int[]> visitedVertices = new ArrayDeque<int[]>();
        // list that stores all visited vertex number
        List<Integer> visitedVerticesInt = new ArrayList<Integer>();
        // boolean that indicates whether path exists
        boolean pathExists = false;
        // list stores article names along the path 
        List<String> pathList = new ArrayList<String>();
        
        int initialVertex = nameToVertex.get(node1);
        int finalVertex = nameToVertex.get(node2);
        
        // if the starting vertex and final vertex are the same, skips traversal algorithm
        if (initialVertex == finalVertex){
            pathExists = true;
        }
        
        int initialPathLength = 0;
        // stores the following information in an array:
        // [current vertex, path length, previous vertex]
        int[] initialVertexArray = new int[3];
        initialVertexArray[0] = initialVertex;
        initialVertexArray[1] = initialPathLength;
        initialVertexArray[2] = initialVertex;
        
        // adds initial vertex to traversal queue, visited array
        vertexQueue.add(initialVertexArray);
        visitedVertices.push(initialVertexArray);
        
        // while we haven't found the path, use breadth-first traversal to find final article
        while (!pathExists && !vertexQueue.isEmpty()){
            // gets vertex array from queue
            int[] currentArray = vertexQueue.poll();
            // gets current vertex number and path length from the array
            int frontVertexNumber = currentArray[0]; 
            int currentPathLength = currentArray[1];
            
            // gets neighbors of current vertex
            Iterable<Integer> neighbors = wikiGraph.getNeighbors(frontVertexNumber);
          
            // for each unvisited neighbor, compare characteristics to current vertex
            for (int neighbor : neighbors){
                if ((!pathExists) && (!visitedVerticesInt.contains(neighbor))){
                    // creates an array for the neighbor's characteristics
                    int[] neighborArray = new int[3];
                    neighborArray[0] = neighbor;
                    neighborArray[1] = currentPathLength + 1;
                    neighborArray[2] = frontVertexNumber;
                    // adds to visited vertex stack, traversal queue, and vertex number list
                    visitedVertices.push(neighborArray); 
                    vertexQueue.add(neighborArray);
                    visitedVerticesInt.add(neighbor);
                    
                    // if the neighbor is the final article, set pathExists to true, exiting the loop
                    if (neighbor == finalVertex){
                        pathExists = true;
                    }
                }
            }
        }
        
        // if we have a path, place articles along the path in a list
        if (pathExists){
            // gets characteristics of final vertex (on top of the stack)
            int[] finalVertexArray = visitedVertices.pop();
            int vertexNumber = finalVertexArray[0];
            int lengthToVertex = finalVertexArray[1];
            int previousVertexNumber = finalVertexArray[2];
            String vertexString = vertexToName.get(vertexNumber);
            // adds final vertex to the path list
            pathList.add(vertexString);
            
            // for each vertex in the stack
            while(!visitedVertices.isEmpty()){
                // get characteristics
                int[] currentArray = visitedVertices.pop();
                int currentVertexNumber = currentArray[0];
                int currentLengthToVertex = currentArray[1];
                
                // if the current vertex number matches the previous number for the last and
                // the length stored by the vertex is one less than the last,
                // then update the characteristics to be compared and adds it to the list
                if ((currentVertexNumber == previousVertexNumber) && (currentLengthToVertex == (lengthToVertex - 1))){
                    vertexNumber = currentVertexNumber;
                    lengthToVertex--;
                    previousVertexNumber = currentArray[2];
                    vertexString = vertexToName.get(vertexNumber);
                    pathList.add(0, vertexString);
                }
            }  
        }
        // returns the path list of articles
        return pathList;  
    }


    /**
     * Returns a shortest path from node1 to node2 that includes the node intermediateNode.
     * This may not be the absolute shortest path between node1 and node2, but should be 
     * a shortest path given the constraint that intermediateNodeAppears in the path. If all
     * three nodes are the same, the "path" is just a single node.  If no path exists, returns
     * an empty list.
     * @param node1 name of the starting article node
     * @param node2 name of the ending article node
     * @return list that has node1 at position 0, node2 in the final position, and the names of each node 
     *      on the path (in order) in between. 
     */
    public List<String> getShortestPath(String node1, String intermediateNode, String node2){
        // gets the paths from node1 to intermediateNode and from intermediateNode to node2
        List<String> path1 = this.getShortestPath(node1, intermediateNode);
        List<String> path2 = this.getShortestPath(intermediateNode, node2);
        
        // if both paths exist, combine the two
        if (!path1.isEmpty() && !path2.isEmpty()){
            // remove the intermediate node from path 1 to avoid double counts
            path1.remove(intermediateNode);
            // adds each article from path 2 to path 1
            for (String node : path2){
                path1.add(node);
            }
        // returns path
            return path1;
        // if either path doesn't exist, return an empty list
        } else {
            List<String> emptyPath = new ArrayList<String>();
            return emptyPath;
        }
        
    }
    
    /**
    * Displays a given path from a starting node to a final node
    */
    public void displayPath(List<String> pathList, String vertex1, String vertex2){
        // path length equals number of articles minus 1
        int length = pathList.size() - 1;
        String firstVertex = vertex1;
        String lastVertex = vertex2;
        
        System.out.println("Path from " + firstVertex + " to " + lastVertex + ", length = " + length);

        // if the path isn't empty, print the path, connecting each node with arrows
        if (length >= 0){
            for (int i = 0; i < (length); i++){
                String articleName = pathList.get(i);
                System.out.print(articleName + " --> ");
            }
            String lastArticle = pathList.get(length);
            System.out.println(lastArticle);
        // if the path is empty, indicate so
        } else{
            System.out.println("There is no path between these entries under given parameters.");
        }    
    }
    
    /**
    * Main method: given two correctly formatted files, finds the shortest path between two (or three) 
    * randomly generated articles.
    */
    public static void main(String[] args){
        // Usage statements for incorrect argument formatting
        if (args.length < 1 || args.length > 3){
            System.err.println("Arguments formatted incorrectly. You should include: articlesFile vertexFile (useIntermediateNode)");
        }
        if (args.length == 3 && !args[2].equals("useIntermediateNode")){
            System.err.println("Third argument formatted incorrectly. You should format it as: useIntermediateNode");
        }
        
        // Creates a PathFinder object from given article and link files
        String vertexFile = args[0];
        String edgeFile = args[1];
        PathFinder path = new PathFinder(vertexFile, edgeFile);
        
        // picks random vertices within the number of vertices of the graph
        Random rng = new Random();
        int vertex1 = rng.nextInt(path.numberOfVertices);
        int vertex2 = rng.nextInt(path.numberOfVertices);
        // converts them to strings
        String vertex1String = path.vertexToName.get(vertex1);
        String vertex2String = path.vertexToName.get(vertex2);
        
        // if intermediate node is not specified, get the shortest path between the two
        // random nodes
        if (args.length == 2){
            List<String> ourPath = path.getShortestPath(vertex1String, vertex2String);
            path.displayPath(ourPath, vertex1String, vertex2String); 
        }
        
        // if intermediate node is specified, get the shortest path between the two
        // random nodes, but with the intermediate node in between
        if (args.length == 3 && args[2].equals("useIntermediateNode")){
            // creates a random intermediate node and converts it to a string
            int intermediateNode = rng.nextInt(path.numberOfVertices);
            String intermediateString = path.vertexToName.get(intermediateNode);
            
            List<String> ourPath = path.getShortestPath(vertex1String, intermediateString, vertex2String);
            System.out.println("If we are passing through " + intermediateString + ":");
            path.displayPath(ourPath, vertex1String, vertex2String);
            
        }
    }
}
    