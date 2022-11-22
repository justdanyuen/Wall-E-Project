public class Node {
    private double data; // f value
    private double g; // f value
    private Point p;
    private Node prev = null;

    public Node(Point p){
        this.p = p;
    }

    public double getF(){
        return this.data;
    }

    public void setF(double f){
        this.data = f;
    }

    public double getG(){
        return this.g;
    }

    public void setG(double g){
        this.g = g;
    }

    public Point getPoint(){
        return this.p;
    }

    // returns prev node
    public Node getPrev(){
        return this.prev;
    }

    public void setPrev(Node n){
        this.prev = n;
    }
}