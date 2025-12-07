// Kemudian kelas QuestNode bisa dibuat:
public class QuestNode {
    String namaArea;
    QuestItem quest; // Sekarang menggunakan QuestItem
    boolean dikunjungi;
    QuestNode next;
    
    public QuestNode(String namaArea, QuestItem quest) {
        this.namaArea = namaArea;
        this.quest = quest;
        this.dikunjungi = false;
        this.next = null;
    }
}