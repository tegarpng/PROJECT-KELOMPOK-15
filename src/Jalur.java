public class Jalur {
    Lokasi tujuan;          // weight
    boolean teleport;   // apakah jalur teleport
    Jalur next;

    public Jalur(Lokasi tujuan, boolean teleport) {
        this.tujuan = tujuan;
        this.teleport = teleport;
        this.next = null;
    }
}

class Lokasi {
    String nama;
    Jalur headJalur;
    Lokasi nextLokasi;
    QuestQueue Quest;

    public Lokasi(String nama) {
        this.nama = nama;
        this.headJalur = null;
        this.nextLokasi = null;
        this.Quest = new QuestQueue();
    }
}