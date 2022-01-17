package aed.graphs;

import java.util.HashMap;


public class PipeCalculator {

    int n;
    float [] well;
    float[][] costs;

    //class Pair

    UndirectedWeightedGraph mstGraph;
    PipeCalculator(int n, float[] well, float[][] costs) {
        this.n = n;
        this.well = well;
        this.costs = costs;
        this.mstGraph = new UndirectedWeightedGraph(n+1);
    }
    UndirectedWeightedGraph createGraph(int n, float[] well, float[][] costs) {
      //  HashMap<int i, int j, Boolean> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            this.mstGraph.addEdge(new UndirectedEdge(i, n+1, well[i]));
        }


        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                this.mstGraph.addEdge(new UndirectedEdge(i, j, costs[i][j]));
                //System.out.println(i + " " + j);
            }


        }
        return null;


    }
   /* UndirectedWeightedGraph calculateSolution(UndirectedWeightedGraph g) {

    }
    UndirectedWeightedGraph calculateSolution() {

    }
    UndirectedWeightedGraph getMST() {

    }*/

    public static void main(String[] args) {

        int n = 5;

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                //this.mstGraph.addEdge(new UndirectedEdge(i, j, costs[i][j]));
                System.out.println(i + " " + j);
            }


        }

    }
}
