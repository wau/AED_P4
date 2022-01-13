package aed.graphs;

public class MaxCycleMST {

    public class Cycle
    {
        private boolean[] marked;
        private boolean hasCycle;
        public Cycle(UndirectedWeightedGraph G)
        {
            marked = new boolean[G.V()];
            for (int s = 0; s < G.V(); s++)
                if (!marked[s])
                    dfs(G, s, s);
        }
        private void dfs(UndirectedWeightedGraph G, int v, int u)
        {
            marked[v] = true;
            for (UndirectedEdge w : G.adj(v))
                if (!marked[w])
                    dfs(G, w, v);
                else if (w != u) hasCycle = true;
        }

        public boolean hasCycle()
        { return hasCycle; }
    }

        private void visit(int v) {
            this.inCurrentPath[v] = true;
            this.visited[v] = true;
            for (UndirectedEdge adj : graph.adj(v)) {

                if (this.hasCycle) return;
                else if (!this.visited[adj]) visit(adj);

                else if (this.inCurrentPath[adj]) {
                    this.hasCycle = true;
                    return;
                }
            }
            this.inCurrentPath[v] = false;
        }

        public boolean hasCycle() {
            return this.hasCycle;
        }
    }


    private static UndirectedWeightedGraph mstGraph;
   // UndirectedWeightedGraph mstGraph;

    MaxCycleMST(UndirectedWeightedGraph g) {

    }

   /* UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        retur
    }

    UndirectedWeightedGraph buildMST() {

    }

    UndirectedWeightedGraph getMST() {

    }*/


    public static void main(String[] args) {
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(8);
        test.addEdge(new UndirectedEdge(0, 1, 3));
        test.addEdge(new UndirectedEdge(0, 7, 10));

        test.addEdge(new UndirectedEdge(1, 2, 4));
        test.addEdge(new UndirectedEdge(2, 3, 4));

        System.out.println(test.toString());

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
