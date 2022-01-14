package aed.graphs;


// minimum spanning tree

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class MaxCycleMST {

     static public class HelperClass {
        private boolean[] visited;
      //  private boolean[] inCurrentPath;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;

        PriorityQueue<UndirectedEdge> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        private UndirectedEdge heaviestEdge;
        int countModified;
        private boolean[] visitedModified;


        public HelperClass(UndirectedWeightedGraph g) {
            this.graph = g;
            this.visited = new boolean[g.vCount()];
            this.heaviestEdge = null;
            this.countModified = 0;
        //    this.inCurrentPath = new boolean[g.vCount()];
        }

        public void search() {
            int vertices = this.graph.vCount();
            this.hasCycle = false;
            //initialize array to false
            for (int i = 0; i < vertices; i++) {
                this.visited[i] = false;
              //  this.inCurrentPath[i] = false;
            }
            for (int i = 0; i < vertices; i++) {
                //start a new search for each vertex that has not been visited yet
                if (!this.visited[i]) visit(i, i-1);
                if (this.hasCycle) return;
            }
        }

        private int coutVerticesGraph(UndirectedWeightedGraph graph, int v) {
            countModified = 0;
            visitedModified = new boolean[graph.vCount()];

            for (int i = 0; i < graph.vCount(); i++) {
                visitedModified[i] = false;
            }

            modifiedVisit(graph, v);
            return countModified;
        }

        private void modifiedVisit(UndirectedWeightedGraph graph, int v) {
            visitedModified[v] = true;
            countModified++;

            for(UndirectedEdge adj : graph.adj(v)) {
                int v1 = adj.other(v);
                if (!visitedModified[v1])
                    modifiedVisit(graph, v1);
            }

        }

        private void visit(int v, int parent)
        {
            //this.inCurrentPath[v] = true;
            this.visited[v] = true;
            for(UndirectedEdge adj : graph.adj(v))
            {
                if(this.hasCycle) return;
                int v1 = adj.other(v);


               // System.out.println("Visiting: " + adj.toString());

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    return;
                    /*if (heaviestEdge == null || adj.compareTo(heaviestEdge) > 0) {
                        heaviestEdge = adj;
                        System.out.println("Heaviest: " + " " + adj.toString());
                    }*/
                }

                if (!visited[v1])
                {
                    maxPQ.add(adj);
                    visit(v1, v);
                }
            }
        }
        public boolean hasCycle()
        {
            System.out.println(maxPQ.peek());
            return this.hasCycle;
        }



        UndirectedEdge determineMaxInCycle() {
            int originalVcount = graph.vCount();
            UndirectedWeightedGraph copy = graph.shallowCopy();


            int size = maxPQ.size();

            for (int i = 0; i < size; i++) {
                UndirectedEdge edge = maxPQ.remove();
                copy.removeEdge(edge);
                int countnew = coutVerticesGraph(copy, edge.v1());
                if (originalVcount == countnew)
                    return edge; // can remove this edge

                copy.addEdge(edge);

            }
            return null;
        }

    }


    private UndirectedWeightedGraph ogGraph;
    private UndirectedWeightedGraph mstGraph;



   // UndirectedWeightedGraph mstGraph;

    MaxCycleMST(UndirectedWeightedGraph g) {
        ogGraph = g;
        mstGraph = new UndirectedWeightedGraph(ogGraph.vCount());
    }


    UndirectedWeightedGraph buildMST() {
        int nVertices = ogGraph.vCount();


        for (int i = 0; i < nVertices; i++) {

            for ( UndirectedEdge edge : ogGraph.adj(i)) {

            }


        }

        return null;
    }
    /*UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        max
    }*/



   /* UndirectedWeightedGraph getMST() {

    }*/


    public static void main(String[] args) {
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(7);
        //vcount numero maximo de vertices basicamente
        test.addEdge(new UndirectedEdge(0, 1, 3));
        test.addEdge(new UndirectedEdge(0, 4, 10));

        test.addEdge(new UndirectedEdge(1, 2, 4));
        test.addEdge(new UndirectedEdge(2, 3, 4));

        //test.addEdge(new UndirectedEdge(4, 3, 5));
        test.addEdge(new UndirectedEdge(4, 5, 4));
        test.addEdge(new UndirectedEdge(4, 6, 5));
        test.addEdge(new UndirectedEdge(5, 6, 6));

    //    test.addEdge(new UndirectedEdge(5, 6, 6));



        HelperClass cycleDetector = new HelperClass(test);
        cycleDetector.search();

        System.out.println(cycleDetector.hasCycle());

        System.out.println(cycleDetector.determineMaxInCycle());


        System.out.println(test.toString());


        //  System.out.println(test.eCount());

       /* for (int i = 0; i < test.vCount() ; i++)
        {

            System.out.print(i + ": ");
            for(UndirectedEdge e : test.adj(i))
            {
                System.out.print(e.toString());
            }
            System.out.println();
        }

        for(UndirectedEdge e : test.adj(0))
        {
            System.out.print(e.toString());
        }*/


    }

}
