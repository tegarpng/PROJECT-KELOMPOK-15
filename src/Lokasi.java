public class Lokasi {
    String nama;
    Jalur headJalur;
    Lokasi nextLokasi;

    // Untuk Dijkstra
    int dist;
    boolean visited;

    public Lokasi(String nama) {
        this.nama = nama;
        this.headJalur = null;
        this.nextLokasi = null;
        
        // default untuk Dijkstra
        this.dist = 999999;
        this.visited = false;
    }
}
