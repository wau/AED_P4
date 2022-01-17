package aed.graphs;



// minimum spanning tree

import java.util.*;

public class MaxCycleMST {

    int i = 0;
    boolean buildMstCalled = false;

    int calculatePathWeight(UndirectedWeightedGraph g) {
        int result = 0;
        HashMap<UndirectedEdge, Boolean>  hashMap = new HashMap<UndirectedEdge, Boolean>();
        for (int i = 0; i < g.vCount(); i++) {
            for ( UndirectedEdge edge : g.adj(i)) {
                // System.out.println(hashMap.toString());
                if (hashMap.get(edge) == null || hashMap.get(edge) == false) {
                    hashMap.put(edge, true);
                    result += edge.weight();
                }
            }
        }
        return result;
    }

     public class HelperClass {
        private boolean[] visited;
        //  private boolean[] inCurrentPath;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;

        PriorityQueue<UndirectedEdge> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        private UndirectedEdge heaviestEdge;
        int countModified;
        private boolean[] visitedModified;

        HashMap<UndirectedEdge, Boolean> visitedEdgesMap = new HashMap<>();


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
            visit(0, -1);
            /*for (int i = 0; i < vertices; i++) {
                //start a new search for each vertex that has not been visited yet
                if (!this.visited[i]) visit(i, i-1);
                if (this.hasCycle) return;
            }*/
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

                if (visitedEdgesMap.get(adj) == null || visitedEdgesMap.get(adj) == false) {
                    maxPQ.add(adj);
                    visitedEdgesMap.put(adj, true);
                }



                int v1 = adj.other(v);


                // System.out.println("Visiting: " + adj.toString());

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    //return;
                    /*if (heaviestEdge == null || adj.compareTo(heaviestEdge) > 0) {
                        heaviestEdge = adj;
                        System.out.println("Heaviest: " + " " + adj.toString());
                    }*/
                }

                if (!visited[v1])
                {
               //     maxPQ.add(adj);
                    visit(v1, v);
                }
            }
        }
        public boolean hasCycle()
        {
            //   System.out.println(maxPQ.peek());
            return this.hasCycle;
        }



        public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph graphs, int originalVcount) {
          //  int originalVcount = graphs.vCount();
            UndirectedWeightedGraph copy = graphs.shallowCopy();


            int size = maxPQ.size();

         //   System.out.println(size);
           // System.out.println(Arrays.stream(maxPQ.toArray()).toList());

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

    public MaxCycleMST(UndirectedWeightedGraph g) {
        ogGraph = g;
        mstGraph = new UndirectedWeightedGraph(ogGraph.vCount());
    }


    public UndirectedWeightedGraph buildMST() {
        buildMstCalled = true;
        int nVertices = ogGraph.vCount();

        HashMap<UndirectedEdge, Boolean>  hashMap = new HashMap<UndirectedEdge, Boolean>();


        int mstVcount = 0;
        for (int i = 0; i < nVertices; i++) {

            for ( UndirectedEdge edge : ogGraph.adj(i)) {
               // System.out.println(hashMap.toString());
                if (hashMap.get(edge) == null || hashMap.get(edge) == false) {
                    hashMap.put(edge, true);
                    mstGraph.addEdge(edge);

                 //   System.out.println(mstGraph.toString());

                    HelperClass helperClass = new HelperClass(mstGraph);


                    helperClass.search();
                    if (helperClass.hasCycle())
                    {
                        mstVcount = helperClass.coutVerticesGraph(mstGraph, edge.v1());

                        UndirectedEdge maxEdge = helperClass.determineMaxInCycle(mstGraph, mstVcount);
                        //if (maxEdge != null)
                        mstGraph.removeEdge(maxEdge);

                      //  hashMap.put(maxEdge, false);
                    }
                   // System.out.println("step: " + i++ + mstGraph.toString());

                }
            }
        }
      //  System.out.println();
        return mstGraph;
    }
    public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {

        HelperClass helperClass = new HelperClass(g);


        int mstVcount = 0;
        helperClass.search();

        for ( UndirectedEdge edge : g.adj(0))
            mstVcount = helperClass.coutVerticesGraph(g, edge.v1());

      //  System.out.println(mstVcount);
        return helperClass.determineMaxInCycle(g, mstVcount);
    }



    public UndirectedWeightedGraph getMST() {
        //buildMST();
        if (buildMstCalled)
            return mstGraph;
        else return null;
    }

    public void test() {
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(7);

        test.addEdge(new UndirectedEdge(0, 2, 3));
        test.addEdge(new UndirectedEdge(0, 3, 2));
        test.addEdge(new UndirectedEdge(0, 1, 2));

        HelperClass helper = new HelperClass(test);

        helper.search();

        System.out.println(helper.hasCycle());
    }


    public static void main(String[] args) {
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(7);
       /* test.addEdge(new UndirectedEdge(0, 3, 1));
        test.addEdge(new UndirectedEdge(2, 3, 1));
        test.addEdge(new UndirectedEdge(2, 1, 2));
        test.addEdge(new UndirectedEdge(2, 4, 1));
        test.addEdge(new UndirectedEdge(3, 4, 3));
        test.addEdge(new UndirectedEdge(4, 5, 1));*/
        /*
        A-0/B-1/C-2/D-3/E-4/F-5/G-6
         */

        test.addEdge(new UndirectedEdge(0, 1, 2));
        test.addEdge(new UndirectedEdge(0, 2, 3));
        test.addEdge(new UndirectedEdge(2, 3, 1));
        test.addEdge(new UndirectedEdge(0, 3, 1));
        test.addEdge(new UndirectedEdge(1, 2, 2)); //primeiro losango

        test.addEdge(new UndirectedEdge(    1, 4, 4));
        test.addEdge(new UndirectedEdge(    2, 4, 2));//triang de cima

        test.addEdge(new UndirectedEdge(    4, 5, 2));
        test.addEdge(new UndirectedEdge(    2, 5, 3));

        test.addEdge(new UndirectedEdge(    6, 5, 1));
        test.addEdge(new UndirectedEdge(    2, 6, 1));
        test.addEdge(new UndirectedEdge(    2, 6, 3));

       /* test.addEdge(new UndirectedEdge(    0, 1, 4));
        test.addEdge(new UndirectedEdge(    1, 2, 3));
        test.addEdge(new UndirectedEdge(    0, 2, 5));*/

        MaxCycleMST maxCycleMST = new MaxCycleMST(test);

        //System.out.println(maxCycleMST.determineMaxInCycle(test));






        /*test.addEdge(new UndirectedEdge(0, 2, 3));
        test.addEdge(new UndirectedEdge(0, 3, 2));
        test.addEdge(new UndirectedEdge(0, 1, 2));

        /*test.addEdge(new UndirectedEdge(0,4,3));
        test.addEdge(new UndirectedEdge(4,7,9));
        test.addEdge(new UndirectedEdge(7,3,5));
        test.addEdge(new UndirectedEdge(3,5,5));
        test.addEdge(new UndirectedEdge(5,1,8));
        test.addEdge(new UndirectedEdge(5,1,2));
        test.addEdge(new UndirectedEdge(1,2,6));
        test.addEdge(new UndirectedEdge(2,6,9));*/

        /*MaxCycleMST maxCycleMST = new MaxCycleMST(test);

        HelperClass helperClass = new HelperClass(test);

        HelperClass cycleDetector = new HelperClass(test);
        cycleDetector.search();

        System.out.println(cycleDetector.hasCycle());

        int Vcount = helperClass.coutVerticesGraph(test, 1);


        System.out.println(cycleDetector.determineMaxInCycle(test, Vcount).toString());*/


      //  MaxCycleMST maxCycleMST = new MaxCycleMST(test);
        LazyPrim lazyPrim = new LazyPrim(test);

        lazyPrim.buildMST();

        System.out.println(lazyPrim.getMST().toString());

        System.out.println(maxCycleMST.calculatePathWeight(lazyPrim.getMST()));
        System.out.println("------------------ mine");

        maxCycleMST.buildMST();
        System.out.println("final");

        System.out.println(maxCycleMST.getMST().toString());
        System.out.println(maxCycleMST.calculatePathWeight(maxCycleMST.getMST()));

    }

}