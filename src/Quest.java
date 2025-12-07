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

