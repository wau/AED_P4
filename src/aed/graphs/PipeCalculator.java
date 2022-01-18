package aed.graphs;

import java.util.HashMap;


public class PipeCalculator {

    private int n;
    private float [] well;
    private float[][] costs;

    boolean calculateSolutionCalled = false;
    boolean createGraphCalled = false;
    //class Pair

    public UndirectedWeightedGraph og;
    public UndirectedWeightedGraph mstGraph;
    public PipeCalculator(int n, float[] well, float[][] costs) {
        this.n = n;
        this.well = well;
        this.costs = costs;
        this.mstGraph = new UndirectedWeightedGraph(n+1);
        this.og = new UndirectedWeightedGraph(n+1);
    }
    public UndirectedWeightedGraph createGraph(int n, float[] well, float[][] costs) {
      //  HashMap<int i, int j, Boolean> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            this.og.addEdge(new UndirectedEdge(i, n, well[i]));
        }

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                this.og.addEdge(new UndirectedEdge(i, j, costs[i][j]));
            }
        }
        return this.og;
    }

    public UndirectedWeightedGraph calculateSolution(UndirectedWeightedGraph g) {
        calculateSolutionCalled = true;
       // if (!createGraphCalled)
         //   createGraph();

        MaxCycleMST maxCycleMST = new MaxCycleMST(g);
        maxCycleMST.buildMST();

        return maxCycleMST.getMST();
    }
    public UndirectedWeightedGraph calculateSolution() {
        calculateSolutionCalled = true;
        if (!createGraphCalled)
            createGraph(n, well, costs);

        MaxCycleMST maxCycleMST = new MaxCycleMST(og);
        maxCycleMST.buildMST();

        return maxCycleMST.getMST();
    }

    public UndirectedWeightedGraph getMST() {
        if (calculateSolutionCalled)
            return calculateSolution();
        else
            return null;
    }

    public static void main(String[] args) {

        int n = 5;

        float[] wellArray = {1};
        float[][] costsArray = new float[1][1];

        costsArray[0][0] = 1;
        PipeCalculator pipe = new PipeCalculator(1, wellArray, costsArray);

       // System.out.println(pipe.createGraph(1, wellArray, costsArray).toString());

      //  pipe.calculateSolution();

        System.out.println(pipe.calculateSolution().toString());

       /* for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                //this.mstGraph.addEdge(new UndirectedEdge(i, j, costs[i][j]));
                System.out.println(i + " " + j);
            }


        }*/

    }
}
