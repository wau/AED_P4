package aed.graphs;

import java.util.HashMap;

import javafx.util.Pair;

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

        for (int i  = 0; i < costs.length; i++) {
            for (int j = 0; j < costs.length; j++) {

            }
        }


    }
    UndirectedWeightedGraph calculateSolution(UndirectedWeightedGraph g) {

    }
    UndirectedWeightedGraph calculateSolution() {

    }
    UndirectedWeightedGraph getMST() {

    }

    public static void main(String[] args) {

    }
}
