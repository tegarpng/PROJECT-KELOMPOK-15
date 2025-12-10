import java.util.Scanner;

public class PetaGame {
    Lokasi head;   // linked list dari semua lokasi
    QuestQueue quest;
    Lokasi currentLocation;  // Lokasi saat ini yang sedang diakses/dimainkan
    
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
        lokasi5.nextLokasi = null;

// --- LOKASI 1: CASTLE (Boss: Death Knight) ---
        lokasi1.Quest.setStartBoss("Death Knight"); // Set Boss Spesifik
        lokasi1.Quest.enqueue(new Quest("Pengumpulan Bahan", "SIDE", 
            "Kumpulkan 5 herbal langka", "COLLECT", 100, null));
        lokasi1.Quest.enqueue(new Quest("Pembukaan Gerbang", "MAIN", 
            "Kalahkan Death Knight untuk membuka gerbang", "BOSS", 500, "Double Strike"));

        // --- LOKASI 2: FOREST (Boss: Euroboros) ---
        lokasi2.Quest.setStartBoss("Euroboros"); // Boss otomatis ganti ke Euroboros di sini
        lokasi2.Quest.enqueue(new Quest("Pembasmi Monster", "SIDE", 
            "Kalahkan 10 monster kecil", "PUZZLE", 150, null));
        lokasi2.Quest.enqueue(new Quest("Misteri Hutan Terlarang", "MAIN", 
            "Kalahkan Euroboros sang Naga Hutan", "BOSS", 700, "Magic Shield"));

        // --- LOKASI 3: HILL (Boss: Omen) ---
        lokasi3.Quest.setStartBoss("Omen"); // Boss ganti ke Omen
        lokasi3.Quest.enqueue(new Quest("Penjelajahan Gua", "SIDE", 
            "Jelajahi gua tersembunyi", "EXPLORE", 180, null));
        lokasi3.Quest.enqueue(new Quest("Mengungkap Misteri Danger Hill", "MAIN", 
            "Kalahkan OMEN si Iblis Bukit", "BOSS", 400, "Heal"));

        // --- LOKASI 4: ASTRAL (Boss: Chronos) ---
        lokasi4.Quest.setStartBoss("Chronos"); // Boss ganti ke Chronos
        lokasi4.Quest.enqueue(new Quest("Pengumpulan Mana", "SIDE", 
            "Kumpulkan partikel astral", "COLLECT", 100, null));
        lokasi4.Quest.enqueue(new Quest("Konfrontasi Astral", "MAIN", 
            "Hadapi Chronos Penguasa Waktu", "BOSS", 1000, "Ultimate Skill"));

        // --- LOKASI 5: DEATH LEAF (Boss: Aetherius) ---
        lokasi5.Quest.setStartBoss("Aetherius"); // Final Boss
        lokasi5.Quest.enqueue(new Quest("Teka-teki Batu", "SIDE", 
            "Selesaikan puzzle kuno", "PUZZLE", 250, null));
        lokasi5.Quest.enqueue(new Quest("Final Boss", "MAIN", 
            "Hadapi Sisi Gelap Aetherius", "BOSS", 2000, "God Mode"));


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

    // Cek apakah semua quest di lokasi saat ini sudah selesai
    public boolean areAllQuestsCompleted(){
        if(currentLocation == null) return false;
        
        Quest current = currentLocation.Quest.peek();
        while(current != null){
            if(!current.selesai){
                return false;  // Ada quest yang belum selesai
            }
            current = current.next;
        }
        return true;  // Semua quest sudah selesai
    }

    // Hitung quest yang sudah selesai di lokasi saat ini
    public int getCompletedQuestCount(){
        if(currentLocation == null) return 0;
        
        int count = 0;
        Quest current = currentLocation.Quest.peek();
        while(current != null){
            if(current.selesai) count++;
            current = current.next;
        }
        return count;
    }

    // Hitung total quest di lokasi saat ini
    public int getTotalQuestCount(){
        if(currentLocation == null) return 0;
        
        int count = 0;
        Quest current = currentLocation.Quest.peek();
        while(current != null){
            count++;
            current = current.next;
        }
        return count;
    }

    // Tampilkan progress quest di lokasi saat ini
    public void showQuestProgress(){
        if(currentLocation == null){
            System.out.println("Anda belum berada di lokasi manapun.");
            return;
        }
        
        int completed = getCompletedQuestCount();
        int total = getTotalQuestCount();
        
        String sep60 = "";
        for(int i = 0; i < 60; i++) sep60 += "=";
        System.out.println("\n" + sep60);
        System.out.println("LOKASI: " + currentLocation.nama);
        System.out.println("PROGRESS QUEST: " + completed + "/" + total);
        
        if(completed == total && total > 0){
            System.out.println("✓ SEMUA QUEST SELESAI! Anda bisa berpindah ke lokasi berikutnya.");
        } else if(total == 0){
            System.out.println("Tidak ada quest di lokasi ini.");
        } else {
            System.out.println("Masih ada " + (total - completed) + " quest yang perlu diselesaikan.");
        }
        System.out.println(sep60);
    }

    // Tampilkan lokasi saat ini dan quest yang tersedia
    public void yourcurrentlocation(){
        if(currentLocation == null){
            System.out.println("Anda belum berada di lokasi manapun.");
            return;
        }
        System.out.println("\n##==========================================================##");
        System.out.println("|| LOKASI SAAT INI: " + String.format("%-40s", currentLocation.nama) + "  ||");
        System.out.println("##==========================================================##");
        System.out.println("Quest yang tersedia di lokasi ini:");
        currentLocation.Quest.tampilkanQuestAktif();
        System.out.println("##==========================================================##\n");
    }

    // Set lokasi pemain saat ini
    public void setCurrentLocation(Lokasi loc){
        this.currentLocation = loc;
    }

    // Get lokasi pemain saat ini
    public Lokasi getCurrentLocation(){
        return this.currentLocation;
    }

    // Pindah ke lokasi tetangga (yang terhubung langsung via jalur)
    public boolean moveToLocation(String namaLokasi){
        if(currentLocation == null){
            System.out.println("Anda belum berada di lokasi manapun. Gunakan setCurrentLocation terlebih dahulu.");
            return false;
        }

        // PENTING: Cek apakah semua quest di lokasi saat ini sudah selesai
        if(!areAllQuestsCompleted()){
            int completed = getCompletedQuestCount();
            int total = getTotalQuestCount();
            System.out.println("\n❌ Anda tidak bisa berpindah lokasi!");
            System.out.println("Selesaikan semua quest di " + currentLocation.nama + " terlebih dahulu.");
            System.out.println("Progress: " + completed + "/" + total + " quest selesai.");
            return false;
        }

        // Cek apakah ada jalur dari lokasi saat ini ke lokasi tujuan
        if(!hasPath(currentLocation, namaLokasi)){
            System.out.println("Tidak ada jalur dari " + currentLocation.nama + " ke " + namaLokasi + ".");
            return false;
        }

        // Dapatkan lokasi tujuan
        Lokasi tujuan = cariLokasi(namaLokasi);
        if(tujuan == null){
            System.out.println("Lokasi " + namaLokasi + " tidak ditemukan pada peta.");
            return false;
        }

        // Dapatkan info jalur (untuk cek tipe jalur)
        Jalur j = getJalurFromTo(currentLocation, namaLokasi);
        if(j != null && j.teleport){
            System.out.println(">> Menggunakan TELEPORT dari " + currentLocation.nama + " ke " + namaLokasi + "!");
        } else {
            System.out.println(">> Berjalan dari " + currentLocation.nama + " ke " + namaLokasi + "...");
        }

        // Set lokasi baru
        this.currentLocation = tujuan;
        System.out.println(">> Tiba di lokasi: " + currentLocation.nama);
        return true;
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
    
        // Kembalikan Jalur dari 'from' ke lokasi bernama 'toName', atau null jika tidak ada
        public Jalur getJalurFromTo(Lokasi from, String toName) {
            if (from == null) return null;
            Jalur j = from.headJalur;
            while (j != null) {
                if (j.tujuan != null && j.tujuan.nama.equals(toName)) return j;
                j = j.next;
            }
            return null;
        }
    
        // Apakah ada jalur langsung dari 'from' ke 'toName'?
        public boolean hasPath(Lokasi from, String toName) {
            return getJalurFromTo(from, toName) != null;
        }
}
