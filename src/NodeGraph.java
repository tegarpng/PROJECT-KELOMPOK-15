public class NodeGraph {
    String asprak;
    NodeGraph next, parent;
    EdgeNode edgehead;
    int jarak;
    boolean visited,printed;

    public NodeGraph(String asprak) {
        this.asprak = asprak;
        this.next = null;
        edgehead = null;
        jarak = Integer.MAX_VALUE;
        visited = false;
    } 

    public void addedge (NodeGraph tujuan, int bobot){
        EdgeNode newedge = new EdgeNode(tujuan, bobot);
        newedge.next = edgehead;
        edgehead = newedge;
    }
}
