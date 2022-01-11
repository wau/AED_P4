package aed.graphs;

public class MaxCycleMST {


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
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(2);
        mstGraph.addEdge(new UndirectedEdge(0, 1, 3));

        System.out.println(mstGraph.toString());

    }

}
