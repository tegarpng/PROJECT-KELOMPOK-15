public class Lokasi {
    String nama;
    Jalur headJalur;
    Lokasi nextLokasi;
    QuestQueue Quest;

    public Lokasi(String nama) {
        this.nama = nama;
        this.headJalur = null;
        this.nextLokasi = null;
    }
}
