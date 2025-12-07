public class Map {
    NodeGraph mapHead;
    
    public void addlocation(String namamap){
        NodeGraph newMap = new NodeGraph(namamap);
        if(mapHead == null){
            mapHead = newMap;
        }else{
            NodeGraph curr = mapHead;
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = newMap;
        }
    }
    
}
