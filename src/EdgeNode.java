public class EdgeNode {
    NodeGraph tujuan;
    int bobot;
    EdgeNode next;

    public EdgeNode (NodeGraph tujuan, int bobot){
        this.tujuan = tujuan;
        this.bobot = bobot;
        next = null;
    }
}
