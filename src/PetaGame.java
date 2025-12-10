public class PetaGame {
    Lokasi head;   // linked list dari semua lokasi
    QuestQueue quest;

    // Tambah lokasi
    public void listlokasi() {
        Lokasi lokasi1 = new Lokasi("Castle");
        Lokasi lokasi2 = new Lokasi("Forest");
        Lokasi lokasi3 = new Lokasi("Hill");
        Lokasi lokasi4 = new Lokasi("Astral");
        Lokasi lokasi5 = new Lokasi("Death Leaf");

        head = lokasi1;
        lokasi1.nextLokasi = lokasi2;
        lokasi2.nextLokasi = lokasi3;
        lokasi3.nextLokasi = lokasi4;
        lokasi4.nextLokasi = lokasi5;

        // Castle → Forest (jalur normal)
        lokasi1.headJalur = new Jalur(lokasi2, false);

        // Forest → Hill (jalur)
        lokasi2.headJalur = new Jalur(lokasi3, false);

        // Hill → Astral (jalur)
        lokasi3.headJalur = new Jalur(lokasi4, false);

        // Astral → Death Leaf (jalur)
        lokasi4.headJalur = new Jalur(lokasi5, false);

        // Death Leaf tidak punya jalur
        lokasi5.headJalur = null;
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

    // Hitung jumlah lokasi
    public int hitungLokasi() {
        int c = 0;
        Lokasi t = head;
        while (t != null) {
            c++;
            t = t.nextLokasi;
        }
        return c;
    }

    // Tampilkan keseluruhan peta beserta jalur dari setiap lokasi
    public void displayMap() {
        listlokasi();
        if (head == null) {
            System.out.println("Peta kosong.");
            return;
        }

        System.out.println("=== Peta Game ===");
        Lokasi cur = head;

        while (cur != null) {
            System.out.print("[ " + cur.nama + " ]");
            Jalur j = cur.headJalur;

            if (j == null) {
                System.out.println(" -> (Tidak ada jalan)");
            } else {
                while (j != null) {
                    System.out.print(" ---------> ");
                    j = j.next;
                }
            }

            cur = cur.nextLokasi;
        }
    }
}
