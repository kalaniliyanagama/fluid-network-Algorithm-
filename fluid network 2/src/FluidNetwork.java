import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.LinkedList;

public class FluidNetwork {
    static  int V ; // Number of vertices in graph

    /* Returns true if there is a path from source 's' to
      sink 't' in residual graph. Also fills parent[] to
      store the path */
    static boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as
        // not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        // Create a queue,  enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue
                = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false
                        && rGraph[u][v] > 0) {
                    // If we find a connection to the sink
                    // node, then there is no point in BFS
                    // anymore We just have to set its parent
                    // and can return true
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // We didn't reach sink in BFS starting from source,
        // so return false
        return false;
    }

    // Returns tne maximum flow from s to t in the given
    // graph
    static int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while there is path from source
        // to sink
        while (bfs(rGraph, s, t, parent)) {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow
                        = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
    }
    public static void main(String[] args) throws java.lang.Exception{

        int numberOfNodes = 0;
        int source;
        int sink;
        int maxFlow;
        In in = new In(args[0]);
        int i,j ,k;

        int[] a = in.readAllInts();
        numberOfNodes=a[0] ;
        V=a[0];
        int size  = a.length;
        int repeat = ((a.length-1)/3);
        int s= 0,v = 0,c = 0;
        System.out.println("Nodes " +numberOfNodes);
        int[][] graph=new int[numberOfNodes][numberOfNodes];
//        for (int[] innerArray : graph)
//        {
//
//            for (int val : innerArray)
//
//
//            {
////                graph[Arrays.asList(graph).indexOf(innerArray)][Arrays.asList(innerArray).indexOf(val)] = 0;
//            }
//        }
////        System.out.println(graph);

        for (i = 1; i < size; i = i + 3) {
            s = a[i];
            v =a[i+1];
            c = a[i +2];
            System.out.println("Edge from node " + s + " to node " + v + " with capacity " + c);
            graph[s][v]=c;


        }
        for (int[] innerArray : graph)
        {
            System.out.println(Arrays.toString(innerArray));


        }

        FluidNetwork m = new FluidNetwork();

        System.out.println("The maximum possible is "
                + m.fordFulkerson(graph, 0, numberOfNodes-1));
    }


}
