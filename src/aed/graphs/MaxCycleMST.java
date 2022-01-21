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
                if (!this.visited[i]) visit(i, -1, null);
                if (this.hasCycle) return;
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

        public void betterSearch (int[] couple) {
            int vertices = this.graph.vCount();
            this.hasCycle = false;


            for (int i = 0; i < couple.length; i++) {
                if (this.hasCycle) return;
                //if (!this.visited[table.get(ve)]) betterVisit(table.get(ve), -1);
                if (!this.visited[couple[i]]) betterVisit(couple[i], -1);
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

        while (!minPQ.isEmpty()) {
            UndirectedEdge edge = minPQ.remove();
            mstGraph.addEdge(edge);


            BetterHelperClass betterHelperClass = new BetterHelperClass(mstGraph);
            betterHelperClass.betterSearch(new int[]{edge.v1(), edge.v2()});

            if (betterHelperClass.hasCycle()) {
                mstGraph.removeEdge(edge);
            }


            if (mstGraph.eCount()+1 == mstGraph.vCount())
                break;
        }

        return mstGraph;
    }


    public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
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

    static public UndirectedWeightedGraph generateRandomGraph(int vCount, int eCount) {
        UndirectedWeightedGraph graph = new UndirectedWeightedGraph(vCount);

        Random r = new Random();
      //  int edgePerV = eCount/vCount;
        for (int i = 0; i < eCount; i++) {
            graph.addEdge(new UndirectedEdge(r.nextInt(vCount), r.nextInt(vCount), r.nextInt(2000)));
        }
        /*for (int i = 0; i < vCount; i++) {

            for (int j = 0; j < edgePerV; j++) {
             //   int i2 = r.nextInt(vCount);
             //   if (hashMap.get(edge) == null || hashMap.get(edge) == false)
                graph.addEdge(new UndirectedEdge(i, r.nextInt(vCount), r.nextInt(2000)));
            }
        }*/
        return graph;
    }

    public static double calculateAverageExecutionTime(int n)
    {
        int trials = 30;
        double totalTime = 0;
        for(int i = 0; i < trials; i++)
        {
            UndirectedWeightedGraph test = generateRandomGraph(n/2, n);
            long time = System.currentTimeMillis();
            MaxCycleMST maxCycleMST = new MaxCycleMST(test);
            maxCycleMST.buildMST();
            totalTime += System.currentTimeMillis() - time;
        }
        return totalTime/trials;
    }
    static void testEfficiency() {
        int n = 100;
        double previousTime = calculateAverageExecutionTime(n);
        double newTime;
        double doublingRatio;
        for(int i = n*2; true; i*=2)
        {
            newTime = calculateAverageExecutionTime(i);
            if(previousTime > 0)
            {
                doublingRatio = newTime/previousTime;
            }
            else doublingRatio = 0;
            previousTime = newTime;
            System.out.println(i + "\t" + newTime + "\t" + doublingRatio);
        }

    }


    /*

n= numero de edges, (numero de vertices = n/2 edges)

n      time               ratio
200	0.36666666666666664	1.1
400	0.7	1.9090909090909092
800	2.4	3.428571428571429
1600	9.033333333333333	3.763888888888889
3200	49.46666666666667	5.476014760147602
6400	310.7	6.280997304582209
12800	1528.6666666666667	4.920072953545757
25600	7918.333333333333	5.17989533362407257*/



    //Complexidade Temporal:
    //No pior caso:
    //Todos os arcos E são adicionados à fila
    //Priority queue: insert: log2n / remove: 2log2n
    //~E*log2(E)+ E*2log2(E)
    //~3Elog(E)
    //=O(Elog(E))


    public static void main(String[] args) {
        UndirectedWeightedGraph test = generateRandomGraph(1000, 2000);//new UndirectedWeightedGraph(9);
        //UndirectedWeightedGraph test = new UndirectedWeightedGraph(9);

        testEfficiency();


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


        /*MaxCycleMST maxCycleMST = new MaxCycleMST(test);


        LazyPrim lazyPrim = new LazyPrim(test);



        lazyPrim.buildMST();

        System.out.println(lazyPrim.getMST().toString());

        System.out.println("------------------ mine");

        maxCycleMST.buildMST();

        System.out.println(maxCycleMST.getMST().toString());*/

    }

}