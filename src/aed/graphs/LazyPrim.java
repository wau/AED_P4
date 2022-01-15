package aed.graphs;

import java.util.PriorityQueue;

public class LazyPrim {
    private boolean[] visited;
    private PriorityQueue<UndirectedEdge> pq;
    private UndirectedWeightedGraph originalG;
    private UndirectedWeightedGraph mst;

    public LazyPrim(UndirectedWeightedGraph g) {
        this.originalG = g;
    }

    public void buildMST() {
        this.pq = new PriorityQueue<>();
        this.visited = new boolean[this.originalG.vCount()];
        this.mst = new UndirectedWeightedGraph(this.originalG.vCount());
        visit(0);
        while (!pq.isEmpty()) {
            UndirectedEdge e = pq.remove();
            int v1 = e.v1();
            int v2 = e.v2();
            if (!this.visited[v1]) {
                this.mst.addEdge(e);
                visit(v1);
            } else if (!this.visited[v2]) {
                this.mst.addEdge(e);
                visit(v2);
            }
        }
    }

    private void visit(int v)
    {
        int otherVertex;
        this.visited[v] = true;
        for(UndirectedEdge e : this.originalG.adj(v))
        {
            otherVertex = e.other(v);
            if(!this.visited[otherVertex]) pq.add(e);
        }
    }
    public UndirectedWeightedGraph getMST()
    {
        return this.mst;
    }
}