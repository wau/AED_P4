package aed.graphs;
import java.util.LinkedList;

public class MaxCycleMST {


    public UndirectedWeightedGraph mstGraph;
    int vCount;
   // UndirectedWeightedGraph mstGraph;

    MaxCycleMST(UndirectedWeightedGraph g) {
        mstGraph = new UndirectedWeightedGraph(g.vCount());
        vCount = g.vCount();
    }

    UndirectedWeightedGraph buildMST() {
        return null;

        mstGraph.par
    }
   /* UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        retur
    }



    UndirectedWeightedGraph getMST() {

    }*/

    public static void main(String[] args) {
        UndirectedWeightedGraph test = new UndirectedWeightedGraph(2);
      //  test.ad(new UndirectedEdge(0, 1, 3));

        MaxCycleMST mst = new MaxCycleMST(test);

        System.out.println(mst.vCount);


     //   System.out.println(test.toString());

    }

}
