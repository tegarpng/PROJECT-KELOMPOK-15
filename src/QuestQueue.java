import java.util.Scanner;

public class QuestQueue {
    private Quest front;
    private Quest rear;
    private Scanner input;
    
    public QuestQueue() {
        this.front = null;
        this.rear = null;
        this.input = new Scanner(System.in);
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
    
    // Inisialisasi semua quest
    public void inisialisasiQuest() {
        // MAIN QUESTS
        enqueue(new Quest("Pembukaan Gerbang", "MAIN", 
            "Kalahkan Guardian untuk membuka gerbang istana", "BOSS", 500, "Double Strike"));
        enqueue(new Quest("Misteri Hutan Terlarang", "MAIN",
            "Temukan artefak kuno di dalam hutan", "PUZZLE", 300, "Magic Shield"));
        enqueue(new Quest("Penyelamatan Tawanan", "MAIN",
            "Selamatkan tawanan dari penjara bawah tanah", "COLLECT", 400, "Heal"));
        enqueue(new Quest("Konfrontasi Akhir", "MAIN",
            "Hadapi Raja Kegelapan di tahtanya", "BOSS", 1000, "Ultimate Skill"));
        
        // SIDE QUESTS
        enqueue(new Quest("Pengumpulan Bahan", "SIDE",
            "Kumpulkan 5 herbal langka untuk penyembuh", "COLLECT", 200, null));
        enqueue(new Quest("Pembasmi Monster", "SIDE",
            "Kalahkan 10 monster kecil di hutan", "BOSS", 150, null));
        enqueue(new Quest("Teka-teki Batu", "SIDE",
            "Selesaikan puzzle batu kuno untuk mendapat harta", "PUZZLE", 250, null));
        enqueue(new Quest("Penjelajahan Gua", "SIDE",
            "Jelajahi seluruh area gua tersembunyi", "EXPLORE", 180, null));
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
        
        Quest questSekarang = peek();
        System.out.println("\n=== MENGERJAKAN QUEST ===");
        System.out.println("Quest: " + questSekarang.namaQuest);
        System.out.println("Deskripsi: " + questSekarang.deskripsi);
        
        switch (questSekarang.tujuan) {
            case "BOSS":
                System.out.println("\nMelawan BOSS...");
                if (melawanBoss(player)) {
                    selesaikanQuest(player, questSekarang);
                } else {
                    System.out.println("Gagal mengalahkan boss! Coba lagi nanti.");
                }
                break;
                
            case "PUZZLE":
                System.out.println("\nMemecahkan puzzle...");
                if (pecahkanPuzzle()) {
                    selesaikanQuest(player, questSekarang);
                } else {
                    System.out.println("Puzzle belum terpecahkan!");
                }
                break;
                
            case "COLLECT":
                System.out.println("\nMengumpulkan item...");
                if (kumpulkanItem()) {
                    selesaikanQuest(player, questSekarang);
                } else {
                    System.out.println("Item belum lengkap!");
                }
                break;
                
            case "EXPLORE":
                System.out.println("\nMenjelajahi area...");
                if (jelajahiArea()) {
                    selesaikanQuest(player, questSekarang);
                } else {
                    System.out.println("Area belum sepenuhnya dijelajahi!");
                }
                break;
        }
    }
    
    // Method untuk melawan boss
    private boolean melawanBoss(Character player) {
        System.out.println("\n++======================== BOSS BATTLE ========================++");
        System.out.println("Player HP: " + player.health);
        System.out.println("Player Damage: " + player.physicaldamage);
        System.out.println("++-------------------------------------------------------------++");
        
        // Simulasi pertarungan sederhana
        int bossHP = 100;
        int bossDamage = 20;
        
        while (bossHP > 0 && player.health > 0) {
            System.out.println("\n1. Serang");
            System.out.println("2. Bertahan");
            System.out.println("3. Gunakan Skill");
            System.out.print("Pilihan: ");
            int pilihan = input.nextInt();
            
            switch (pilihan) {
                case 1:
                    int damage = player.physicaldamage + (int)(Math.random() * 10);
                    bossHP -= damage;
                    System.out.println("Anda memberikan " + damage + " damage kepada boss!");
                    break;
                case 2:
                    System.out.println("Anda bertahan, mengurangi damage musuh!");
                    bossDamage /= 2;
                    break;
                case 3:
                    if (player.magicpower > 0) {
                        int magicDamage = player.magicpower + (int)(Math.random() * 15);
                        bossHP -= magicDamage;
                        System.out.println("Anda menggunakan skill magic, memberikan " + magicDamage + " damage!");
                    } else {
                        System.out.println("Magic power tidak cukup!");
                    }
                    break;
            }
            
            // Boss menyerang
            if (bossHP > 0) {
                player.health -= Math.max(0, bossDamage - player.physicaldefense);
                System.out.println("Boss menyerang! HP anda tersisa: " + player.health);
            }
        }
        
        return bossHP <= 0;
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
        
        System.out.println("\nJawaban (1 huruf): ");
        input.nextLine(); // Clear buffer
        String jawaban = input.nextLine().toLowerCase();
        
        return jawaban.equals("e") || jawaban.equals("echo") || jawaban.equals("gema");
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
            System.out.println("Lanjut mencari? (ya/tidak): ");
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
            int pilihan = input.nextInt();
            
            if ((i == 1 && pilihan == 2) || 
                (i == 2 && pilihan == 1) || 
                (i == 3 && pilihan == 3)) {
                System.out.println("Anda menemukan sesuatu yang menarik!");
                areaTerselesaikan++;
            } else {
                System.out.println("Tidak ada yang istimewa di sini...");
            }
        }
        
        System.out.println("\nArea yang berhasil dijelajahi: " + areaTerselesaikan + "/3");
        return areaTerselesaikan >= 2;
    }
    
    // Method untuk menyelesaikan quest (DIPERBAIKI)
    private void selesaikanQuest(Character player, Quest quest) {
        quest.selesai = true;
        player.gold += quest.rewardGold;
        
        System.out.println("\n++======================== QUEST COMPLETED ========================++");
        System.out.println("||    Quest '" + quest.namaQuest + "' berhasil diselesaikan!        ||");
        System.out.println("||    Anda mendapatkan " + quest.rewardGold + " Gold!                ||");
        
        // --- LOGIC SKILL TREE DIINTEGRASIKAN ---
        if (player.mySkills != null) {
            // Memanggil method yang ada di SkillList.java (Uploaded)
            player.mySkills.pickAndUpgradeSkill();
        }
        // ---------------------------------------

        System.out.println("||    Gold total: " + player.gold + "                             ||");
        System.out.println("++==================================================================++");
        
        // Dequeue quest yang sudah selesai
        dequeue();
    }
    
    // Tampilkan statistik quest
    public void tampilkanStatistik() {
        System.out.println("\n=== QUEST STATISTICS ===");
        
        Quest current = front;
        int totalMain = 0, selesaiMain = 0;
        int totalSide = 0, selesaiSide = 0;
        
        while (current != null) {
            if (current.jenis.equals("MAIN")) {
                totalMain++;
                if (current.selesai) selesaiMain++;
            } else {
                totalSide++;
                if (current.selesai) selesaiSide++;
            }
            current = current.next;
        }
        
        System.out.println("Main Quest: " + selesaiMain + "/" + totalMain + " selesai");
        System.out.println("Side Quest: " + selesaiSide + "/" + totalSide + " selesai");
        System.out.println("Total Progress: " + (selesaiMain + selesaiSide) + "/" + (totalMain + totalSide));
        
        if (selesaiMain == totalMain && totalMain > 0) {
            System.out.println("\n*** SELAMAT! Semua Main Quest telah diselesaikan! ***");
        }
    }
}