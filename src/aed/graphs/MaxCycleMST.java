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
