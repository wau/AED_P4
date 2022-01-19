package aed.graphs;
//dif

// minimum spanning tree

import java.awt.*;
import java.util.*;

public class MaxCycleMST {

    int i = 0;
    boolean buildMstCalled;


    public static class HelperClass {
        private boolean[] visited;
        //  private boolean[] inCurrentPath;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;

//        PriorityQueue<UndirectedEdge> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        public Stack<UndirectedEdge> stack;

        int starEndOfCycle;

        int countModified;
        private boolean[] visitedModified;


        public HelperClass(UndirectedWeightedGraph g) {
            this.graph = g;
            this.visited = new boolean[g.vCount()];
            this.countModified = 0;
            starEndOfCycle = -1;
            stack = new Stack<>();
        }

        public void search() {
            int vertices = this.graph.vCount();
            this.hasCycle = false;

            for (int i = 0; i < vertices; i++) {
                //if (!stack.isEmpty())
                //  stack.clear();
                if (!this.visited[i]) visit(i, -1, null);
                if (this.hasCycle) return;
            }
        }

        public void betterSearch() {
            int vertices = this.graph.vCount();
            this.hasCycle = false;

            for (int i = 0; i < vertices; i++) {
                //if (!stack.isEmpty())
                //  stack.clear();
                if (this.hasCycle) return;
                if (!this.visited[i]) betterVisit(i, -1);

            }
        }


        private void betterVisit(int v, int parent) {
            this.visited[v] = true;

            for (UndirectedEdge adj : graph.adj(v)) {
                if (this.hasCycle) return;
                int v1 = adj.other(v);

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    return;
                }
                if (!visited[v1]) {
                    betterVisit(v1, v);
                }
            }

        }

        private void visit(int v, int parent, UndirectedEdge fromEdge) {
            this.visited[v] = true;
            if (fromEdge != null)
                stack.push(fromEdge);
            for (UndirectedEdge adj : graph.adj(v)) {
                if (this.hasCycle) return;
                int v1 = adj.other(v);

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    starEndOfCycle = v1;
                    stack.push(adj);
                    return;
                }
                if (!visited[v1]) {
                    visit(v1, v, adj);
                }
            }
            if (!stack.isEmpty() && !this.hasCycle) {
                //UndirectedEdge edgePop = stack.pop();
                stack.pop();
            }
        }

        public boolean hasCycle() {
            return this.hasCycle;
        }

        public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph graphs) {
            UndirectedEdge max = stack.pop();

            while (!stack.isEmpty()) {
                //   for (int i = 0; i < size; i++) {
                UndirectedEdge edge = stack.pop();

                if (edge.compareTo(max) > 0)
                    max = edge;

                if (edge.v1() == starEndOfCycle || edge.v2() == starEndOfCycle) {
                    break;
                }
            }
            return max;
        }
    }




    public static class BetterHelperClass {

        private boolean[] visited;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;


        public BetterHelperClass(UndirectedWeightedGraph g) {
            this.graph = g;
            this.visited = new boolean[g.vCount()];
        }

        public void betterSearch () {
            int vertices = this.graph.vCount();
            this.hasCycle = false;

            for (int i = 0; i < vertices; i++) {

                if (this.hasCycle) return;
                if (!this.visited[i]) betterVisit(i, -1);

            }
        }


        private void betterVisit ( int v, int parent){
            this.visited[v] = true;

            for (UndirectedEdge adj : graph.adj(v)) {
                if (this.hasCycle) return;
                int v1 = adj.other(v);

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    return;
                }
                if (!visited[v1]) {
                    betterVisit(v1, v);
                }
            }
        }
        public boolean hasCycle () {
            return this.hasCycle;
        }

    }

    private UndirectedWeightedGraph ogGraph;
    private UndirectedWeightedGraph mstGraph;
    private PriorityQueue<UndirectedEdge> minPQ = new PriorityQueue<>();


    public MaxCycleMST(UndirectedWeightedGraph g) {
        ogGraph = g;//.shallowCopy();
        mstGraph = new UndirectedWeightedGraph(ogGraph.vCount());

        for (UndirectedEdge e : ogGraph.allEdges()) {
            minPQ.add(e);
        }
        buildMstCalled = false;
    }




    /*public UndirectedWeightedGraph buildMST() {
        buildMstCalled = true;

        for(UndirectedEdge edge : ogGraph.allEdges()) {
            mstGraph.addEdge(edge);

            HelperClass helperClass = new HelperClass(mstGraph);
            helperClass.search();

            if (helperClass.hasCycle()) {
                UndirectedEdge maxEdge = helperClass.determineMaxInCycle(mstGraph);
                mstGraph.removeEdge(maxEdge);
            }

        }
        return mstGraph;
    }*/

    public UndirectedWeightedGraph buildMST() {
        buildMstCalled = true;

       // int size = minPQ.size();
        boo vertices[] = new int[ogGraph.vCount()];
        while (!minPQ.isEmpty()) {
            UndirectedEdge edge = minPQ.remove();
            mstGraph.addEdge(edge);


            if (mstGraph.eCount() >= mstGraph) {
                BetterHelperClass betterHelperClass = new BetterHelperClass(mstGraph);
                betterHelperClass.betterSearch();

                if (betterHelperClass.hasCycle())
                    mstGraph.removeEdge(edge);
            }


            if (mstGraph.eCount()+1 == mstGraph.vCount())
                break;
        }

        return mstGraph;
    }


    public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        /*PriorityQueue<UndirectedEdge> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
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
        else return maxPQ.remove();*/
        HelperClass helperClass = new HelperClass(g);
        helperClass.search();
        if (helperClass.hasCycle()) {
            return helperClass.determineMaxInCycle(mstGraph);
        }
        else return null;
    }



    public UndirectedWeightedGraph getMST() {
        //buildMST();
        if (buildMstCalled)
            return mstGraph;
        else return null;
    }

    static public UndirectedWeightedGraph generateRandomGraph(int vCount) {
        UndirectedWeightedGraph graph = new UndirectedWeightedGraph(vCount);
     //   HashMap<UndirectedEdge, Boolean>  hashMap = new HashMap<UndirectedEdge, Boolean>();

        Random r = new Random();
        for (int i = 0; i < vCount; i++) {
            int edges = 1+r.nextInt(4);

            for (int j = 0; j < edges; j++) {
             //   int i2 = r.nextInt(vCount);
             //   if (hashMap.get(edge) == null || hashMap.get(edge) == false)
                graph.addEdge(new UndirectedEdge(i, r.nextInt(vCount), r.nextFloat()));
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        UndirectedWeightedGraph test = generateRandomGraph(1000);//new UndirectedWeightedGraph(9);
        //UndirectedWeightedGraph test = new UndirectedWeightedGraph(9);


       /* test.addEdge(new UndirectedEdge(0, 1, 50));
        test.addEdge(new UndirectedEdge(1, 2, 4));
       // test.addEdge(new UndirectedEdge(2, 3, 1));
        test.addEdge(new UndirectedEdge(3, 4, 2));
        test.addEdge(new UndirectedEdge(4, 5, 50));
        //test.addEdge(new UndirectedEdge(4, 1, 3));

        test.addEdge(new UndirectedEdge(3, 6 , 700));
        test.addEdge(new UndirectedEdge(2, 3 , 1000));

        test.addEdge(new UndirectedEdge(3, 7, 20));
        test.addEdge(new UndirectedEdge(7, 8, 710));
        test.addEdge(new UndirectedEdge(6, 7, 250));*/

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

        /*test.addEdge(new UndirectedEdge(0 ,1, 3));
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
        //UndirectedEdge maxEdge1 = maxCycleMST.determineMaxInCycle(test);
        //System.out.println("Max edge 1: "  +maxEdge1);

       // test.removeEdge(maxCycleMST.determineMaxInCycle(test));

        //System.out.println("Max edge 2: "  + maxCycleMST.determineMaxInCycle(test));


        lazyPrim.buildMST();

        System.out.println(lazyPrim.getMST().toString());

        System.out.println("------------------ mine");

        maxCycleMST.buildMST();

        System.out.println(maxCycleMST.getMST().toString());

    }

}