public class PetaGame {
    Lokasi head;   // linked list dari semua lokasi

    // Tambah lokasi
    public void tambahLokasi(String nama) {
        Lokasi baru = new Lokasi(nama);
        if (head == null) {
            head = baru;
        } else {
            Lokasi t = head;
            while (t.nextLokasi != null) t = t.nextLokasi;
            t.nextLokasi = baru;
        }
    }

    // Cari lokasi berdasarkan nama
    public Lokasi cariLokasi(String nama) {
        Lokasi t = head;
        while (t != null) {
            if (t.nama.equals(nama)) return t;
            t = t.nextLokasi;
        }
        return null;
    }

    // Tambah jalur biasa
    public void tambahJalur(String dari, String ke, int jarak) {
        Lokasi A = cariLokasi(dari);
        Lokasi B = cariLokasi(ke);

        if (A == null || B == null) return;

        Jalur j = new Jalur(B, jarak, false);

        if (A.headJalur == null) A.headJalur = j;
        else {
            Jalur t = A.headJalur;
            while (t.next != null) t = t.next;
            t.next = j;
        }
    }

    // Tambah jalur teleport
    public void tambahTeleport(String dari, String ke) {
        Lokasi A = cariLokasi(dari);
        Lokasi B = cariLokasi(ke);

        if (A == null || B == null) return;

        Jalur j = new Jalur(B, 0, true);

        if (A.headJalur == null) A.headJalur = j;
        else {
            Jalur t = A.headJalur;
            while (t.next != null) t = t.next;
            t.next = j;
        }
    }

    // Hitung jumlah lokasi
    private int hitungLokasi() {
        int c = 0;
        Lokasi t = head;
        while (t != null) {
            c++;
            t = t.nextLokasi;
        }
        return c;
    }

    // Ambil lokasi dengan dist paling kecil yang belum visited
    private Lokasi getMinUnvisited() {
        Lokasi t = head;
        Lokasi min = null;
        int terbaik = 999999;

        while (t != null) {
            if (!t.visited && t.dist < terbaik) {
                terbaik = t.dist;
                min = t;
            }
            t = t.nextLokasi;
        }
        return min;
    }

    // 🔥 **Dijkstra Tanpa Array**
    public void dijkstra(String startNama) {

        Lokasi start = cariLokasi(startNama);
        if (start == null) return;

        // Reset semua node
        Lokasi t = head;
        while (t != null) {
            t.dist = 999999;
            t.visited = false;
            t = t.nextLokasi;
        }

        start.dist = 0;

        int jumlah = hitungLokasi();

        // Proses Dijkstra
        for (int i = 0; i < jumlah; i++) {

            Lokasi min = getMinUnvisited();
            if (min == null) break;

            min.visited = true;

            Jalur j = min.headJalur;
            while (j != null) {

                int jarakBaru = min.dist + j.jarak;

                if (jarakBaru < j.tujuan.dist) {
                    j.tujuan.dist = jarakBaru;
                }

                j = j.next;
            }

        }

        // Tampilkan hasil
        System.out.println("=== Hasil Dijkstra dari " + startNama + " ===");
        Lokasi x = head;
        while (x != null) {
            System.out.println(x.nama + " : " + x.dist);
            x = x.nextLokasi;
        }
    }

    // Tampilkan keseluruhan peta beserta jalur dari setiap lokasi
    public void displayMap(){
        if (head == null) {
            System.out.println("Peta kosong.");
            return;
        }

        System.out.println("=== Peta Game ===");
        Lokasi cur = head;
        while (cur != null) {
            System.out.println("Lokasi: " + cur.nama);
            Jalur j = cur.headJalur;
            if (j == null) {
                System.out.println("  - Tidak ada jalur dari lokasi ini.");
            } else {
                while (j != null) {
                    String tipe = j.teleport ? "Teleport" : "Jalur";
                    System.out.println("  -> " + j.tujuan.nama + " (Jarak: " + j.jarak + ") [" + tipe + "]");
                    j = j.next;
                }
            }
            System.out.println("--------------------------------");
            cur = cur.nextLokasi;
        }
    }
}
