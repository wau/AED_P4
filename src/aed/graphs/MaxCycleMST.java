package aed.graphs;


// minimum spanning tree

public class MaxCycleMST {

    static public class CycleDetector {
        private boolean[] visited;
      //  private boolean[] inCurrentPath;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;

        public CycleDetector(UndirectedWeightedGraph g) {
            this.graph = g;
            this.visited = new boolean[g.vCount()];
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

        private void visit(int v, int parent)
        {
            //this.inCurrentPath[v] = true;
            this.visited[v] = true;
            for(UndirectedEdge adj : graph.adj(v))
            {
                if(this.hasCycle) return;

                int v1 = adj.other(v);
                //int v2 = adj.v2();

                if (visited[v1] && v1 != parent) {
                    this.hasCycle = true;
                    return;
                }
              /*  else if (visited[v2] && v2 != parent) {
                    this.hasCycle = true;
                    return;
                }*/

                if (!visited[v1])
                    visit(v1, v);

             //   if (!visited[v2])
               //     visit(v2, v);
            }
          //  this.inCurrentPath[v] = false;
        }
        public boolean hasCycle()
        {
            return this.hasCycle;
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

        return null;
    }
   /* UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        retur
    }



    UndirectedWeightedGraph getMST() {

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
        test.addEdge(new UndirectedEdge(4, 6, 4));
       // test.addEdge(new UndirectedEdge(5, 6, 4));



        CycleDetector cycleDetector = new CycleDetector(test);
        cycleDetector.search();

        System.out.println(cycleDetector.hasCycle());

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

        //System.out.println(mstGraph.toString());

    }

}
