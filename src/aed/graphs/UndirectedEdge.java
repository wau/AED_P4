package aed.graphs;

public class UndirectedEdge implements Comparable<UndirectedEdge>{

    private int v1;
    private int v2;
    private float weight;

    public UndirectedEdge(int v1, int v2, float weight)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public float weight()
    {
        return this.weight;
    }

    public int v1()
    {
        return this.v1;
    }

    public int v2()
    {
        return this.v2;
    }

    public int other(int v)
    {
        if(v == this.v1) return this.v2;
        else return this.v1;
    }

    public int compareTo(UndirectedEdge other)
    {
        if(this.weight < other.weight) return -1;
        else if(this.weight > other.weight) return 1;
        else return 0;
    }

    public String toString()
    {
        return this.v1 + "-" + this.v2 + " " + this.weight;
    }
}