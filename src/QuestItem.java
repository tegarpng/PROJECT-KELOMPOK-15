// Ganti nama kelas Quest menjadi QuestItem
class QuestItem {
    String namaQuest;
    String jenis;
    String deskripsi;
    String tujuan;
    int rewardGold;
    String rewardSkill;
    boolean selesai;
    QuestItem next;  // Ganti dari Quest menjadi QuestItem
    
    public QuestItem(String namaQuest, String jenis, String deskripsi, String tujuan, int rewardGold, String rewardSkill) {
        this.namaQuest = namaQuest;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.tujuan = tujuan;
        this.rewardGold = rewardGold;
        this.rewardSkill = rewardSkill;
        this.selesai = false;
        this.next = null;
    }
}

