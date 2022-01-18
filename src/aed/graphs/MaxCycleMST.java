package aed.graphs;
//dif

// minimum spanning tree

import java.util.*;

public class MaxCycleMST {

    int i = 0;
    boolean buildMstCalled = false;

     public static class HelperClass {
        private boolean[] visited;
        //  private boolean[] inCurrentPath;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;

        PriorityQueue<UndirectedEdge> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        public Stack<UndirectedEdge> stack = new Stack<>();

        int starEndOfCycle = -1;

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
            visit(0, -1, null);
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

        private void visit(int v, int parent, UndirectedEdge fromEdge)
        {

            this.visited[v] = true;
            //stack.clear();
            if (fromEdge != null)
                stack.push(fromEdge);
            for(UndirectedEdge adj : graph.adj(v))
            {
                if(this.hasCycle) return;
                if (visitedEdgesMap.get(adj) == null || visitedEdgesMap.get(adj) == false) {
                  //  stack.push(adj);

                    //maxPQ.add(adj);
                    //stack.push(adj);

                   // stack.push(adj);

                    visitedEdgesMap.put(adj, true);
                }
                int v1 = adj.other(v);

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    starEndOfCycle = v1;
                    return;
                }


                if (!visited[v1])
                {
                    visit(v1, v, adj);
                    //stack.clear();
                }
              //  if(!stack.isEmpty())
                //    stack.pop();
            }

            if(!stack.isEmpty()) {
                UndirectedEdge edgePop = stack.pop();
                visitedEdgesMap.put(edgePop, false);
            }


        }
        public boolean hasCycle()
        {
            //   System.out.println(maxPQ.peek());
            return this.hasCycle;
        }



        public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph graphs) {
            System.out.println(stack.toString());
            maxPQ.add(stack.pop());

            UndirectedEdge start = stack.pop();
            maxPQ.add(start);

            int v = start.v1(); //initialize v
            //System.out.println(v);

            int size = stack.size();

            for (int i = 0; i < size; i++) {
                UndirectedEdge edge = stack.pop();
                // System.out.println("test" + " " +edge.toString());
                v = edge.other(v);
                //System.out.println(v);
                maxPQ.add(edge);
                   //System.out.println("edge added" + " " +edge.toString());
                if (v == starEndOfCycle) {
                    break;
                }
            }
         //   System.out.println(maxPQ.toString());
            return maxPQ.remove();
            /*for (int i = 0; i < size; i++) {
                UndirectedEdge edge = maxPQ.remove();
                copy.removeEdge(edge);
                int countnew = coutVerticesGraph(copy, edge.v1());
                if (originalVcount == countnew)
                    return edge; // can remove this edge

                copy.addEdge(edge);
            }*/
            //return null;
        }

    }


    private UndirectedWeightedGraph ogGraph;
    private UndirectedWeightedGraph mstGraph;



    // UndirectedWeightedGraph mstGraph;

    public MaxCycleMST(UndirectedWeightedGraph g) {
        ogGraph = g.shallowCopy();
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
               //     System.out.println(helperClass.stack.toString());
                   /* for (UndirectedEdge test : helperClass.stack) {

                        System.out.println();

                    }*/
                    if (helperClass.hasCycle())
                    {
                        //mstVcount = helperClass.coutVerticesGraph(mstGraph, edge.v1());

                        UndirectedEdge maxEdge = helperClass.determineMaxInCycle(mstGraph);
                        mstGraph.removeEdge(maxEdge);
                    }
                }
            }
        }
        return mstGraph;
    }
    public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        PriorityQueue<UndirectedEdge> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        UndirectedWeightedGraph copyG = g.shallowCopy();


        HelperClass helperClass = new HelperClass(copyG);

        helperClass.search();

        while (helperClass.hasCycle()) {
            UndirectedEdge maxEdge = helperClass.determineMaxInCycle(copyG);
            if (maxEdge != null) {
                copyG.removeEdge(maxEdge);
                maxPQ.add(maxEdge);
            }

            helperClass = new HelperClass(copyG);
            helperClass.search();
        }
        if (maxPQ.isEmpty())
            return null;
        else return maxPQ.remove();
    }



    public UndirectedWeightedGraph getMST() {
        //buildMST();
        if (buildMstCalled)
            return mstGraph;
        else return null;
    }

    public static void main(String[] args) {
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(6);

        test.addEdge(new UndirectedEdge(0, 1, 50));
        test.addEdge(new UndirectedEdge(1, 2, 4));
        test.addEdge(new UndirectedEdge(2, 3, 1));
        test.addEdge(new UndirectedEdge(3, 4, 2));
        test.addEdge(new UndirectedEdge(4, 5, 50));
        test.addEdge(new UndirectedEdge(4, 1, 3));

       /* test.addEdge(new UndirectedEdge(0, 1, 7));
        test.addEdge(new UndirectedEdge(1, 2, 20));
        test.addEdge(new UndirectedEdge(2, 3, 2));
        test.addEdge(new UndirectedEdge(3, 4, 5));
       // test.addEdge(new UndirectedEdge(2, 4, 3));
        test.addEdge(new UndirectedEdge(4, 5, 200));
        test.addEdge(new UndirectedEdge(2, 5, 250));*/

      /*  test.addEdge(new UndirectedEdge(0, 1, 2));
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
        test.addEdge(new UndirectedEdge(    2, 6, 3));*/

        //System.out.println(maxCycleMST.determineMaxInCycle(test));
       /*test.addEdge(new UndirectedEdge(0, 1, 3));
        test.addEdge(new UndirectedEdge(1 ,2, 4));
        test.addEdge(new UndirectedEdge(2, 3, 4));
        test.addEdge(new UndirectedEdge(0, 4, 30));
        test.addEdge(new UndirectedEdge(4,6,350));
       // test.addEdge(new UndirectedEdge(6, 5, 6));
        test.addEdge(new UndirectedEdge(5, 4, 300));
        test.addEdge(new UndirectedEdge (5, 6, 390));
        test.addEdge(new UndirectedEdge (5, 8, 900));
        test.addEdge(new UndirectedEdge (7, 8, 10));
        test.addEdge(new UndirectedEdge (8, 6, 2));*/

       /* test.addEdge(new UndirectedEdge(0 ,1, 3));
        test.addEdge(new UndirectedEdge(0 ,3, 6));
        test.addEdge(new UndirectedEdge(1 ,3, 7));
        test.addEdge(new UndirectedEdge(1 ,2, 8));
        test.addEdge(new UndirectedEdge(2 ,3, 4));
        test.addEdge(new UndirectedEdge(3 ,6, 5));
        test.addEdge(new UndirectedEdge(2 ,6, 3));
        test.addEdge(new UndirectedEdge(6 ,4, 1));
        test.addEdge(new UndirectedEdge(6 ,5, 9));
        test.addEdge(new UndirectedEdge(6 ,5, 9));
        test.addEdge(new UndirectedEdge(4 ,5, 8));
        test.addEdge(new UndirectedEdge(4 ,1, 8));
        test.addEdge(new UndirectedEdge(6 ,5, 9));
        test.addEdge(new UndirectedEdge(2,4, 2));*/


       /* MaxCycleMST maxCycleMST = new MaxCycleMST(test);

        HelperClass helperClass = new HelperClass(test);

        HelperClass cycleDetector = new HelperClass(test);
        cycleDetector.search();

        System.out.println(cycleDetector.hasCycle());

        int Vcount = helperClass.coutVerticesGraph(test, 1);


        System.out.println(cycleDetector.determineMaxInCycle(test, Vcount).toString());*/



        MaxCycleMST maxCycleMST = new MaxCycleMST(test);


        LazyPrim lazyPrim = new LazyPrim(test);

        System.out.println("Max edge: "  + maxCycleMST.determineMaxInCycle(test));

        lazyPrim.buildMST();

        System.out.println(lazyPrim.getMST().toString());

        System.out.println("------------------ mine");

        maxCycleMST.buildMST();

        System.out.println(maxCycleMST.getMST().toString());

    }

}