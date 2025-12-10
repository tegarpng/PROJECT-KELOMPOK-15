public class Quest {
    String namaQuest;
    String jenis; // "MAIN" atau "SIDE"
    String deskripsi;
    String tujuan; // "BOSS", "PUZZLE", "COLLECT", "EXPLORE"
    int rewardGold;
    String rewardSkill; // null jika tidak ada
    boolean selesai;
    Quest next;
    
    public Quest(String namaQuest, String jenis, String deskripsi, String tujuan, int rewardGold, String rewardSkill) {
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

class QuestAreaNode {
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

class QuestItem {
    String namaQuest;
    String jenis;
    String deskripsi;
    String tujuan;
    int rewardGold;
    SkillTreeGen rewardSkill;
    boolean selesai;
    QuestItem next;  // Ganti dari Quest menjadi QuestItem
    
    public QuestItem(String namaQuest, String jenis, String deskripsi, String tujuan, int rewardGold, SkillTreeGen rewardSkill) {
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

class QuestNode {
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