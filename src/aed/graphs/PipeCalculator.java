package aed.graphs;

import java.util.HashMap;


public class PipeCalculator {

    private int n;
    private float [] well;
    private float[][] costs;

    //class Pair

    public UndirectedWeightedGraph mstGraph;
    public PipeCalculator(int n, float[] well, float[][] costs) {
        this.n = n;
        this.well = well;
        this.costs = costs;
        this.mstGraph = new UndirectedWeightedGraph(n+1);
    }
    public UndirectedWeightedGraph createGraph(int n, float[] well, float[][] costs) {
      //  HashMap<int i, int j, Boolean> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            this.mstGraph.addEdge(new UndirectedEdge(i, n+1, well[i]));
        }

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                this.mstGraph.addEdge(new UndirectedEdge(i, j, costs[i][j]));
            }
        }
        return this.mstGraph;
    }

    public UndirectedWeightedGraph calculateSolution(UndirectedWeightedGraph g) {
        return null;
    }
    public UndirectedWeightedGraph calculateSolution() {
        return null;
    }
    public UndirectedWeightedGraph getMST() {
        return null;
    }

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
