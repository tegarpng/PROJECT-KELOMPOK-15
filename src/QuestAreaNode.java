public class QuestAreaNode {
    String namaArea;
    String questName;  // Simpan nama quest saja
    String questType;  // "MAIN" atau "SIDE"
    boolean dikunjungi;
    QuestAreaNode next;
    
    public QuestAreaNode(String namaArea, String questName, String questType) {
        this.namaArea = namaArea;
        this.questName = questName;
        this.questType = questType;
        this.dikunjungi = false;
        this.next = null;
    }
}