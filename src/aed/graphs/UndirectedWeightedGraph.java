package aed.graphs;

import java.util.LinkedList;
import java.util.Scanner;

public class UndirectedWeightedGraph {

    private int vCount;
    private int eCount;
    private LinkedList<UndirectedEdge>[] adj;

    @SuppressWarnings("unchecked")
    public UndirectedWeightedGraph(int vCount)
    {
        this.vCount = vCount;
        this.eCount = 0;
        this.adj = (LinkedList<UndirectedEdge>[]) new LinkedList[vCount];

        //Initialise all adjacency lists
        for(int i = 0 ; i < vCount; i++)
        {
            this.adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(UndirectedEdge e)
    {
        this.adj[e.v1()].add(e);
        this.adj[e.v2()].add(e);
        this.eCount++;
    }

    public void removeEdge(UndirectedEdge e)
    {
        this.adj[e.v1()].remove(e);
        this.adj[e.v2()].remove(e);
        this.eCount--;
    }

    public Iterable<UndirectedEdge> adj(int v)
    {
        return this.adj[v];
    }

    public int vCount()
    {
        return this.vCount;
    }

    public int eCount()
    {
        return this.eCount;
    }

    public int degree(int v)
    {
        return this.adj[v].size();
    }

    public int maxDegree()
    {
        int max = 0;
        int degree;
        for(int i = 0; i < this.vCount ; i++)
        {
            degree = degree(i);
            if(degree > max) max = degree;
        }

        return max;
    }

    public UndirectedWeightedGraph shallowCopy()
    {
        UndirectedWeightedGraph copy = new UndirectedWeightedGraph(this.vCount);

        copy.eCount = this.eCount;
        for(int i = 0; i < this.vCount; i++)
        {
            copy.adj[i].addAll(this.adj[i]);
        }

        return copy;
    }



    public String toString()
    {
        String s = "V: " + this.vCount + " E: " + this.eCount + "\n";
        for(int i = 0; i < this.vCount ; i++)
        {
            s+= i + ": ";
            for(UndirectedEdge e : this.adj[i])
            {
                s+= e.toString();
            }
            s += "\n";
        }

        return s;
    }

    public static UndirectedWeightedGraph parse(Scanner sc)
    {
        UndirectedWeightedGraph g = new UndirectedWeightedGraph(sc.nextInt());
        int v1,v2;
        float weight;
        g.eCount = sc.nextInt();

        for(int i = 0; i < g.eCount; i++)
        {
            v1 = sc.nextInt();
            v2 = sc.nextInt();
            weight = sc.nextFloat();
            g.addEdge(new UndirectedEdge(v1,v2,weight));
        }

        return g;
    }
}