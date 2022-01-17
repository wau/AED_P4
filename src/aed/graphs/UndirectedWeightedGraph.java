package aed.graphs;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class UndirectedWeightedGraph {

    private int vCount;
    private int eCount;
    private Object[] adj;
    private Hashtable<Integer, UndirectedEdge> edges;

    @SuppressWarnings("unchecked")
    public UndirectedWeightedGraph(int vCount)
    {
        this.vCount = vCount;
        this.eCount = 0;
        this.adj = new Object[vCount];
        this.edges = new Hashtable<Integer,UndirectedEdge>();

        //Initialise all adjacency lists
        for(int i = 0 ; i < vCount; i++)
        {
            this.adj[i] = new LinkedList<UndirectedEdge>();
        }
    }

    @SuppressWarnings("unchecked")
    public void addEdge(UndirectedEdge e)
    {
        ((LinkedList<UndirectedEdge>)this.adj[e.v1()]).add(e);
        ((LinkedList<UndirectedEdge>)this.adj[e.v2()]).add(e);
        this.edges.put(e.hashCode(),e);
        this.eCount++;
    }

    @SuppressWarnings("unchecked")
    public void removeEdge(UndirectedEdge e)
    {
        ((LinkedList<UndirectedEdge>)this.adj[e.v1()]).remove(e);
        ((LinkedList<UndirectedEdge>)this.adj[e.v2()]).remove(e);
        this.edges.remove(e.hashCode());
        this.eCount--;
    }

    @SuppressWarnings("unchecked")
    public Iterable<UndirectedEdge> adj(int v)
    {
        return (LinkedList<UndirectedEdge>)this.adj[v];
    }

    public Iterable<UndirectedEdge> allEdges()
    {
        return this.edges.values();
    }

    public int vCount()
    {
        return this.vCount;
    }

    public int eCount()
    {
        return this.eCount;
    }

    @SuppressWarnings("unchecked")
    public int degree(int v)
    {
        return ((LinkedList<UndirectedEdge>)this.adj[v]).size();
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

    public float totalWeight()
    {
        float w = 0;
        for(UndirectedEdge e : this.edges.values())
        {
            w+=e.weight();
        }

        return w;
    }

    @SuppressWarnings("unchecked")
    public UndirectedWeightedGraph shallowCopy()
    {
        UndirectedWeightedGraph copy = new UndirectedWeightedGraph(this.vCount);

        copy.eCount = this.eCount;
        for(int i = 0; i < this.vCount; i++)
        {
            ((LinkedList<UndirectedEdge>)copy.adj[i]).addAll((LinkedList<UndirectedEdge>)this.adj[i]);
        }

        copy.edges = (Hashtable<Integer,UndirectedEdge>)this.edges.clone();

        return copy;
    }

    @SuppressWarnings("unchecked")
    public String toString()
    {
        String s = "V: " + this.vCount + " E: " + this.eCount + "\n";
        s += "Weight: " + totalWeight() + "\n";
        for(int i = 0; i < this.vCount ; i++)
        {
            s+= i + ": ";
            for(UndirectedEdge e : ((LinkedList<UndirectedEdge>)this.adj[i]))
            {
                s+= e.toString() + "; ";
            }
            s += "\n";
        }

        return s;
    }

    @SuppressWarnings("unchecked")
    public String summary()
    {
        int j;
        String s = "V: " + this.vCount + " E: " + this.eCount + "\n";
        s += "Weight: " + totalWeight() + "\n";
        for(int i = 0; i < this.vCount ; i++)
        {
            s+= i + ": ";
            j = 0;
            for(UndirectedEdge e : ((LinkedList<UndirectedEdge>)this.adj[i]))
            {
                s+= e.toString() + "; ";
                j++;
                if(j>=3)
                {
                    s+= "...;";
                    break;
                }
            }
            s += "\n";
            if(i >= 3)
            {
                s += "...\n";
                break;
            }
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