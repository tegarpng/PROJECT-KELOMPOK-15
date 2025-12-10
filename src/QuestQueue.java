import java.util.Scanner;

public class QuestQueue {
    private Quest front;
    private Quest rear;
    private Scanner input;
    private manageBoss bossData;
    private Boss currentBoss;
    
    public QuestQueue() {
        this.front = null;
        this.rear = null;
        this.input = new Scanner(System.in);
        this.bossData = new manageBoss(); 
        this.bossData.loadBoss(); 
        // Set boss pertama sebagai currentBoss
        this.currentBoss = this.bossData.head; 
    }

    // Enqueue - Tambah quest ke queue
    public void enqueue(Quest quest) {
        if (rear == null) {
            front = rear = quest;
        } else {
            rear.next = quest;
            rear = quest;
        }
    }
    
    // Dequeue - Ambil quest dari depan
    public Quest dequeue() {
        if (isEmpty()) {
            return null;
        }
        
        Quest temp = front;
        front = front.next;
        
        if (front == null) {
            rear = null;
        }
        
        return temp;
    }
    
    // Peek - Lihat quest di depan tanpa menghapus
    public Quest peek() {
        return front;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
    
    // Tampilkan semua quest yang aktif
    public void tampilkanQuestAktif() {
        System.out.println("\n++=========================== QUEST ACTIVE ===========================++");
        
        Quest current = front;
        int count = 1;
        
        while (current != null) {
            System.out.println("|| [" + current.jenis + "] " + count + ". " + current.namaQuest);
            System.out.println("||    " + current.deskripsi);
            System.out.println("||    Tipe: " + current.tujuan + " | Reward: " + current.rewardGold + " Gold" + 
                             (current.rewardSkill != null ? " + Skill: " + current.rewardSkill : ""));
            System.out.println("||    Status: " + (current.selesai ? "SELESAI" : "BELUM SELESAI"));
            System.out.println("++------------------------------------------------------------------++");
            
            current = current.next;
            count++;
        }
        
        if (isEmpty()) {
            System.out.println("||              Tidak ada quest yang aktif saat ini               ||");
            System.out.println("++==================================================================++");
        }
    }
    
    // Proses quest saat ini
    public void prosesQuest(Character player) {
        if (isEmpty()) {
            System.out.println("Tidak ada quest yang harus dikerjakan!");
            return;
        }
        
        Quest q = peek();

        if (q.selesai) {
            System.out.println("Quest '" + q.namaQuest + "' sudah selesai.");
            dequeue();
            if(isEmpty()) return;
            q = peek();
        }

        System.out.println("\n=== MENGERJAKAN QUEST ===");
        System.out.println("Quest: " + q.namaQuest);
        System.out.println("Deskripsi: " + q.deskripsi);

        if (q.jenis.equalsIgnoreCase("MAIN")) {
            // === LOGIKA MAIN QUEST (BOSS BATTLE) ===
            // Menggunakan BattleManager
            if (q.tujuan.equals("BOSS")) {
                System.out.println("(!) PERINGATAN: Aura Boss yang kuat terdeteksi!");
                
                // Panggil logika battle manager di sini
                if (melawanBoss(player, q)) {
                    selesaikanQuest(player, q);
                } else {
                    System.out.println("Anda Gagal! Silakan coba lagi setelah pulih/respawn.");
                }
            } else {
                // Fallback jika ada main quest bukan boss
                System.out.println("Mengerjakan tugas utama...");
                selesaikanQuest(player, q);
            }
        } 
        else {
            // === LOGIKA SIDE QUEST ===
            boolean success = false;
            
            switch(q.tujuan) {
                case "PUZZLE":
                    System.out.println("\nMemecahkan puzzle...");
                    if (pecahkanPuzzle()) {
                        success = true;
                    } else {
                        System.out.println("Puzzle belum terpecahkan!");
                    }
                    break;
                case "COLLECT":
                    System.out.println("\nMengumpulkan item...");
                    if (kumpulkanItem()) {
                        success = true;
                    } else {
                        System.out.println("Item belum lengkap!");
                    }
                    break;
                case "EXPLORE":
                    System.out.println("\nMenjelajahi area...");
                    if (jelajahiArea()) {
                        success = true;
                    } else {
                        System.out.println("Area belum sepenuhnya dijelajahi!");
                    }
                    break;
                default:
                    // Jika tipe quest tidak spesifik, anggap selesai otomatis (fetch quest sederhana)
                    success = true;
                    break;
            }
            
            if (success) {
                selesaikanQuest(player, q);
            } else {
                System.out.println("Gagal menyelesaikan misi sampingan.");
            }
        }
    }

    // Set Boss awal untuk lokasi ini (Opsional, jika ingin manual set boss)
public void setStartBoss(String bossName) {
        Boss temp = this.bossData.head;
        while (temp != null) {
            if (temp.namaboss.equalsIgnoreCase(bossName)) {
                this.currentBoss = temp; // Boss ditemukan & diset
                return;
            }
            temp = temp.next;
        }
        System.out.println("Error: Boss " + bossName + " tidak ditemukan di data.");
    }
    
    // === INTEGRASI BATTLE MANAGER DI SINI ===
    private boolean melawanBoss(Character player, Quest quest) {
        if (this.currentBoss == null) return true;
        
        System.out.println("MENANTANG BOSS: " + this.currentBoss.namaboss);
        BattleManager battle = new BattleManager();
        boolean menang = battle.startBattle(player, this.currentBoss);
        
        if (menang) {
            // Setelah menang, maju ke boss.next (jika ada multi-boss di satu map)
            // Tapi karena kita pindah lokasi, setStartBoss di PetaGame akan mereset ini nanti
            this.currentBoss = this.currentBoss.next; 
            return true;
        }
        return false;
    }

    
    // Method untuk puzzle sederhana
    private boolean pecahkanPuzzle() {
        System.out.println("\n++=========================== PUZZLE ===========================++");
        System.out.println("||   Sebuah prasasti kuno terpampang di depanmu:               ||");
        System.out.println("||   'Aku berbicara tanpa mulut,                               ||");
        System.out.println("||    Mendengar tanpa telinga,                                 ||");
        System.out.println("||    Tidak punya tubuh, tapi bisa bernapas.                   ||");
        System.out.println("||    Apakah aku?'                                             ||");
        System.out.println("++-------------------------------------------------------------++");
        
        System.out.print("\nJawaban (1 kata): ");
        // Bersihkan buffer jika perlu, tapi hati-hati dengan nextInt sebelumnya di menu lain
        String jawaban = "";
        try {
            jawaban = input.next().toLowerCase();
        } catch (Exception e) {
            input.nextLine();
        }
        
        return jawaban.equals("e") || jawaban.equals("echo") || jawaban.equals("gema") || jawaban.equals("angin");
    }
    
    // Method untuk mengumpulkan item
    private boolean kumpulkanItem() {
        System.out.println("\nMencari item di sekitar...");
        int collected = (int)(Math.random() * 6); // 0-5
        
        System.out.println("Item yang dikumpulkan: " + collected + "/5");
        
        if (collected >= 5) {
            System.out.println("Semua item berhasil dikumpulkan!");
            return true;
        } else {
            System.out.println("Masih kurang " + (5 - collected) + " item.");
            System.out.print("Lanjut mencari? (ya/tidak): ");
            String lanjut = input.next();
            
            if (lanjut.equalsIgnoreCase("ya")) {
                collected += (int)(Math.random() * 3) + 1;
                System.out.println("Item tambahan ditemukan! Total: " + collected + "/5");
                return collected >= 5;
            }
            return false;
        }
    }
    
    // Method untuk menjelajahi area
    private boolean jelajahiArea() {
        System.out.println("\n++========================= EXPLORATION ========================++");
        System.out.println("||    Anda memasuki area yang gelap dan misterius...          ||");
        System.out.println("||    Ada beberapa jalur yang bisa dipilih:                   ||");
        System.out.println("++-------------------------------------------------------------++");
        
        int areaTerselesaikan = 0;
        
        for (int i = 1; i <= 3; i++) {
            System.out.println("\n=== Area " + i + " ===");
            System.out.println("1. Periksa kiri");
            System.out.println("2. Periksa kanan");
            System.out.println("3. Lanjut ke depan");
            System.out.print("Pilihan: ");
            
            int pilihan = 0;
            if(input.hasNextInt()) pilihan = input.nextInt();
            else input.next(); 
            
            // Logika sederhana: jawaban benar beda-beda tiap area
            if ((i == 1 && pilihan == 2) || 
                (i == 2 && pilihan == 1) || 
                (i == 3 && pilihan == 3)) {
                System.out.println(">> Anda menemukan jalan yang benar!");
                areaTerselesaikan++;
            } else {
                System.out.println(">> Jalan buntu/tidak ada apa-apa.");
            }
        }
        
        System.out.println("\nJalur benar ditemukan: " + areaTerselesaikan + "/3");
        return areaTerselesaikan >= 2;
    }
    
    // Method untuk menyelesaikan quest
    private void selesaikanQuest(Character player, Quest quest) {
        quest.selesai = true;
        player.gold += quest.rewardGold;
        
        System.out.println("\n++====================== QUEST COMPLETE ======================++");
        System.out.println("|| Selamat! Quest '" + quest.namaQuest + "' Selesai.");
        System.out.println("|| Hadiah: " + quest.rewardGold + " Gold");
        
        // Cek jika ada reward skill (String di Quest.java, tapi dicek ke SkillList player)
        if (quest.rewardSkill != null && player.mySkills != null) {
            System.out.println("|| BONUS: Skill Point Diperoleh!");
            // Asumsi: Method pickAndUpgradeSkill ada di SkillList (Linked List skill player)
            player.mySkills.pickAndUpgradeSkill();
        }
        System.out.println("++============================================================++");
        
        dequeue(); // Hapus dari antrian
    }
    
    // Tampilkan statistik quest
    public void tampilkanStatistik() {
        System.out.println("\n=== QUEST STATISTICS ===");
        
        Quest current = front; // Hanya menghitung yang AKTIF di queue
        // Jika ingin menghitung history, perlu list terpisah 'completedQuests'
        
        if (current == null) {
            System.out.println("Tidak ada quest aktif.");
        } else {
            int count = 0;
            while (current != null) {
                count++;
                current = current.next;
            }
            System.out.println("Jumlah Quest Aktif: " + count);
        }
        
        if (bossData != null && currentBoss != null) {
             System.out.println("Boss Berikutnya: " + currentBoss.namaboss);
        } else {
             System.out.println("Semua Boss telah dikalahkan!");
        }
    }
}