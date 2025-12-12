import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);

    public static String repeatString(String str, int count) {
        String result = "";
        for(int i = 0; i < count; i++) {
            result += str;
        }
        return result;
    }

    public static void pause() {
        System.out.print("\nTekan ENTER untuk melanjutkan...");
        sc.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void loadbanner(){
        System.out.println("++========================================================================================================================================================================++");
        System.out.println("|| :::::::::: ::::::::::: :::::::::: :::::::::  ::::    :::     :::     :::               ::::::::  :::    :::     :::     :::::::::   ::::::::  :::       :::  ::::::::  ||\r\n" + //
        "|| :+:            :+:     :+:        :+:    :+: :+:+:   :+:   :+: :+:   :+:              :+:    :+: :+:    :+:   :+: :+:   :+:    :+: :+:    :+: :+:       :+: :+:    :+: ||\r\n" + //
        "|| +:+            +:+     +:+        +:+    +:+ :+:+:+  +:+  +:+   +:+  +:+              +:+        +:+    +:+  +:+   +:+  +:+    +:+ +:+    +:+ +:+       +:+ +:+        ||\r\n" + //
        "|| +#++:++#       +#+     +#++:++#   +#++:++#:  +#+ +:+ +#+ +#++:++#++: +#+              +#++:++#++ +#++:++#++ +#++:++#++: +#+    +:+ +#+    +:+ +#+  +:+  +#+ +#++:++#++ ||\r\n" + //
        "|| +#+            +#+     +#+        +#+    +#+ +#+  +#+#+# +#+     +#+ +#+                     +#+ +#+    +#+ +#+     +#+ +#+    +#+ +#+    +#+ +#+ +#+#+ +#+        +#+ ||\r\n" + //
        "|| #+#            #+#     #+#        #+#    #+# #+#   #+#+# #+#     #+# #+#              #+#    #+# #+#    #+# #+#     #+# #+#    #+# #+#    #+#  #+#+# #+#+#  #+#    #+# ||\r\n" + //
        "|| ##########     ###     ########## ###    ### ###    #### ###     ### ##########        ########  ###    ### ###     ### #########   ########    ###   ###    ########  ||\r\n" + //
        "||  ::::::::   ::::::::  :::        ::::::::::: ::::::::::: :::    ::: :::::::::  ::::::::::       :::::::::  ::::::::::: :::     ::: :::::::::: :::::::::   ::::::::     ||\r\n" + //
        "|| :+:    :+: :+:    :+: :+:            :+:         :+:     :+:    :+: :+:    :+: :+:              :+:    :+:     :+:     :+:     :+: :+:        :+:    :+: :+:    :+:    ||\r\n" + //
        "|| +:+        +:+    +:+ +:+            +:+         +:+     +:+    +:+ +:+    +:+ +:+              +:+    +:+     +:+     +:+     +:+ +:+        +:+    +:+ +:+           ||\r\n" + //
        "|| +#++:++#++ +#+    +:+ +#+            +#+         +#+     +#+    +:+ +#+    +:+ +#++:++#         +#++:++#:      +#+     +#+     +:+ +#++:++#   +#++:++#:  +#++:++#++    ||\r\n" + //
        "||        +#+ +#+    +#+ +#+            +#+         +#+     +#+    +#+ +#+    +#+ +#+              +#+    +#+     +#+      +#+   +#+  +#+        +#+    +#+        +#+    ||\r\n" + //
        "|| #+#    #+# #+#    #+# #+#            #+#         #+#     #+#    #+# #+#    #+# #+#              #+#    #+#     #+#       #+#+#+#   #+#        #+#    #+# #+#    #+#    ||\r\n" + //
        "||  ########   ########  ########## ###########     ###      ########  #########  ##########       ###    ### ###########     ###     ########## ###    ###  ########     ||\r" );
        System.out.println("++========================================================================================================================================================================++");
    }
    public static void main(String[] args) throws Exception {
        ManagePlayer player = new ManagePlayer();
        BattleManager battleManager = new BattleManager();
        PetaGame peta = new PetaGame();
        Scanner input = new Scanner(System.in);
        
        boolean run = true;
        while(run){
            loadbanner();
            System.out.println("++  ++=============++                                                                                                                                                     ++");
            System.out.println("++  || 1. PLAY     ||                                                                                                                                                     ++");
            System.out.println("++  ++=============++                                                                                                                                                     ++");
            System.out.println("++  || 2. ABOUT US ||                                                                                                                                                     ++");
            System.out.println("++  ++=============++                                                                                                                                                     ++");
            System.out.println("++  || 3. EXIT     ||                                                                                                                                                     ++");
            System.out.println("++  ++=============++                                                                                                                                                     ++");
            System.out.print("++========================================================================================================================================================================++\n");
            System.out.print("Masukkan pilihan anda : ");
            int choice = input.nextInt();
            
            if(choice == 1){
                SenjataShop shop = new SenjataShop();
                manageBoss bossManager = new manageBoss();
                shop.loadweapon();
                shop.loadrmor();
                bossManager.loadBoss();
                // --- BAGIAN 1: PEMBUATAN KARAKTER ---
                input.nextLine();
                System.out.print("\nMasukkan Nama Hero : ");
                String nama = input.nextLine();
                
                System.out.println("\nPilih Role:");
                System.out.println("1. Fighter (HP Tinggi, Melee)");
                System.out.println("2. Magic (HP Rendah, Spell Kuat)");
                System.out.println("3. Archer (Balanced, Ranged)");
                System.out.print("Pilihan (1-3) : ");
                int roleChoice = input.nextInt();
                
                String role = "Fighter"; // Default
                if(roleChoice == 2) role = "Magic";
                else if(roleChoice == 3) role = "Archer";
                
                clearScreen();
                player.addplayer(nama, role);
                player.shop = shop;
                
                // --- BAGIAN 2: INISIALISASI PETA DAN LOKASI AWAL ---
                peta.listlokasi();
                // Set lokasi pemain di peta (mulai dari Castle)
                peta.setCurrentLocation(peta.cariLokasi("Castle"));
                
                System.out.println("\n=== SELAMAT DATANG DI GAME ===");
                System.out.println("Anda telah tiba di Castle sebagai poin awal petualangan!");
                peta.yourcurrentlocation();
                
                // Membuka menu shop awal. Player berbelanja persiapan
                System.out.println("\n--- SHOP PERSIAPAN AWAL ---");
                player.openShopMenu(shop);

                // --- BAGIAN 3: GAME LOOP (QUEST -> MOVEMENT -> BATTLE -> REWARD -> REPEAT) ---
                Boss currentBoss = bossManager.head; // Mulai dari boss pertama
                int stage = 1;
                boolean gameRunning = true;

                // Loop ini akan berjalan selama masih ada Boss yang harus dikalahkan
                while(gameRunning && currentBoss != null) {
                    
                    
                    // 1. STAGE QUEST - Player harus menyelesaikan semua quest di lokasi sebelum bertarung
                    System.out.println("\n\n===============================================");
                    System.out.println("     STAGE " + stage + " - QUEST DI " + peta.getCurrentLocation().nama.toUpperCase());
                    System.out.println("===============================================");
                    System.out.println("Sebelum menghadapi boss, selesaikan semua quest di lokasi ini!");
                    
                    // Player explore lokasi dan menjalankan quest
                    player.exploreLocation(peta);

                    // Cek apakah semua quest selesai, jika belum ulangi
                    while(!peta.areAllQuestsCompleted()) {
                        System.out.println("\nAnda belum menyelesaikan semua quest di lokasi ini!");
                        System.out.println("Silakan kembali ke lokasi untuk menyelesaikan quest yang tersisa.");
                        player.exploreLocation(peta);
                    }

                    // 2. PERSIAPAN BATTLE
                    System.out.println("\n===============================================");
                    System.out.println("     ARENA PERTARUNGAN DENGAN " + currentBoss.namaboss.toUpperCase());
                    System.out.println("===============================================");
                    System.out.println("Semua quest selesai! Sekarang saatnya menghadapi boss...");
                    System.out.println("Bersiaplah untuk bertarung melawan: " + currentBoss.namaboss);
                    try { Thread.sleep(2000); } catch(Exception e){} 

                    // 3. MULAI BATTLE
                    boolean win = battleManager.startBattle(player.head, currentBoss);

                    // 4. CEK HASIL BATTLE
                    if (win) {
                        // --- JIKA MENANG ---
                        System.out.println("\n" + repeatString("=", 47));
                        System.out.println("VICTORY! " + currentBoss.namaboss + " Telah dikalahkan!");
                        System.out.println(repeatString("=", 47));
                        
                        // Berikan Reward Gold
                        int rewardGold = 500 * stage;
                        player.head.gold += rewardGold;
                        System.out.println("Reward: " + rewardGold + " Gold!");

                        // Cek apakah ini boss terakhir?
                        if (currentBoss.next != null) {
                            System.out.println("\n--- ISTIRAHAT DI CAMP ---");
                            System.out.println("Pedagang keliling menghampiri anda untuk berbisnis.");
                            System.out.println("Gunakan Gold untuk persiapan boss selanjutnya!");
                            
                            // Buka Shop sebelum pindah lokasi
                            player.openShopMenu(shop); 
                            
                            // PINDAH KE LOKASI BERIKUTNYA (otomatis reset quest)
                            String nextLocation = peta.getCurrentLocation().headJalur != null ? 
                                                peta.getCurrentLocation().headJalur.tujuan.nama : null;
                            if(nextLocation != null) {
                                System.out.println("\n--- BERPERGIAN KE LOKASI BERIKUTNYA ---");
                                peta.moveToLocation(nextLocation);
                                peta.yourcurrentlocation();
                            }
                            
                            // Pindah ke Boss Berikutnya
                            currentBoss = currentBoss.next;
                            stage++;
                        } else {
                            // Boss Habis (Tamat Game)
                            System.out.println("\n\n" + repeatString("=", 50));
                            System.out.println("              SELAMAT! ANDA MENANG!            ");
                            System.out.println("          SEMUA BOSS TELAH KALAH              ");
                            System.out.println("           GAME SELESAI - TERIMA KASIH!        ");
                            System.out.println(repeatString("=", 50));
                            gameRunning = false;
                            currentBoss = null;
                            break;
                        }
                    }
                }
            }else if(choice == 2){
                System.out.println("Anda adalah seorang pahlawan (Fighter/Mage/Archer) yang kembali ke tanah kelahiran setelah sekian lama, hanya untuk menemukan kerajaan Anda telah hancur. Sebuah kekuatan");
                System.out.println("jahat telah merebut tahta dan membagi wilayah kerajaan menjadi zona-zona berbahaya yang dikuasai oleh para jenderal iblis. Misi Anda hanyalah satu: Pembalasan Dendam");
                System.out.println("dan merebut kembali kerajaan yang menjadi hak Anda.");
                System.out.println("Dibuat oleh Mahasiswa Angkatan 24 untuk memenuhi tugas projek ALGODAT:");
                System.out.println("Nama : LALU TAUFIK DEWO BAYUAJI");
                System.out.println("NIM  : F1D02410069");
                System.out.println("Nama : MUHAMMAD TEGAR BIJANTA");
                System.out.println("NIM  : F1D02410081");
                System.out.println("Nama : REVANO JANUAR ADIGUNA PRAWIRA");
                System.out.println("NIM  : F1D02410146");
                System.out.println("Nama : NYOMAN ADHI DHIRA PURNOMO ");
                System.out.println("NIM  : F1D02410132");
                System.out.println("Nama : IMAS NAZALIA RAHMAWATI");
                System.out.println("NIM  : F1D02410055");
                System.out.println("Nama : ANNISA MAKARIMA AHLAQ");
                System.out.println("NIM  : F1D02410106");
                System.out.println("Nama : BAIQ SABRINA RAMADHANI");
                System.out.println("NIM  : F1D02410040");
                pause();
                clearScreen();
            }else{
                System.out.println("Terima kasih telah bermain!");
                return;
            }
        }
        input.close();
    }
}
