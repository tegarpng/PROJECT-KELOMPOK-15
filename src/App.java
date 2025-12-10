import java.util.Scanner;

public class App {
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
        manageBoss bossManager = new manageBoss();
        BattleManager battleManager = new BattleManager();
        SenjataShop shop = new SenjataShop();
        Scanner input = new Scanner(System.in);
        PetaGame peta = new PetaGame();
        peta.displayMap();
        

        shop.loadweapon();
        shop.loadrmor();
        bossManager.loadBoss();

        loadbanner();
        System.out.println("++  ++=============++                                                                                                                                                     ++");
        System.out.println("++  || 1. PLAY     ||                                                                                                                                                     ++");
        System.out.println("++  ++=============++                                                                                                                                                     ++");
        System.out.println("++  || 2. ABOUT US ||                                                                                                                                                     ++");
        System.out.println("++  ++=============++                                                                                                                                                     ++");
        System.out.println("++  || 3. EXIT     ||                                                                                                                                                     ++");
        System.out.println("++  ++=============++                                                                                                                                                     ++");
        System.out.println("++========================================================================================================================================================================++\n");

        System.out.println("Masukkan pilihan anda : ");
        int choice = input.nextInt();
        System.out.flush();

        if(choice == 1){
            loadbanner();
            // --- BAGIAN 1: PEMBUATAN KARAKTER ---
            input.nextLine(); // Membersihkan buffer enter
            System.out.print("\nMasukkan Nama Hero : ");
            String nama = input.nextLine();
            
            System.out.println("\nPilih Class:");
            System.out.println("1. Fighter (HP Tinggi, Melee)");
            System.out.println("2. Magic (HP Rendah, Spell Kuat)");
            System.out.println("3. Archer (Balanced, Ranged)");
            System.out.print("Pilihan (1-3) : ");
            int roleChoice = input.nextInt();
        
            String role = "Fighter"; // Default
            if(roleChoice == 2) role = "Magic";
            else if(roleChoice == 3) role = "Archer";

            player.addplayer(nama, role);
            
            // --- BAGIAN 2: SHOP PERTAMA (PERSIAPAN) ---
            System.out.println("\n\n");
            System.out.println("=============================================");
            System.out.println("   SELAMAT DATANG DI KOTA AWAL (PERSIAPAN)   ");
            System.out.println("=============================================");
            System.out.println("Silakan beli perlengkapan sebelum bertarung!");
            
            // Membuka menu shop. Player terjebak disini sampai memilih menu "0" (Lanjut)
            player.openShopMenu(shop); 

            // Otomatis pasang senjata/armor yang baru dibeli (opsional)
            if(player.weaponStackManager != null) {
                player.weaponStackManager.equipWeapon();
            }

            // --- BAGIAN 3: GAME LOOP (BATTLE -> REWARD -> SHOP -> NEXT BATTLE) ---
            Boss currentBoss = bossManager.head; // Mulai dari boss pertama
            int stage = 1;

            // Loop ini akan berjalan selama masih ada Boss yang harus dikalahkan
            while(currentBoss != null) {
                
                // 1. Tampilkan Banner Stage
                System.out.println("\n\n");
                System.out.println("#############################################");
                System.out.println("          MEMASUKI STAGE " + stage + " : ARENA " + currentBoss.namaboss.toUpperCase());
                System.out.println("#############################################");
                System.out.println("Bersiaplah untuk bertarung...");
                try { Thread.sleep(2000); } catch(Exception e){} // Jeda sebentar biar dramatis

                // 2. MULAI BATTLE DISINI
                // Method startBattle akan menjalankan pertarungan ronde demi ronde
                // Jika menang, return true. Jika kalah, return false.
                boolean win = battleManager.startBattle(player.head, currentBoss); // <--- INI KODE YANG MEMICU BATTLE

                // 3. Cek Hasil Battle
                if (win) {
                    // --- JIKA MENANG ---
                    System.out.println("\nVICTORY! " + currentBoss.namaboss + " Telah dikalahkan!");
                    
                    // Berikan Reward
                    int rewardGold = 500 * stage;
                    player.head.gold += rewardGold;
                    System.out.println("Reward: Anda mendapatkan " + rewardGold + " Gold!");

                    // Cek apakah ini boss terakhir?
                    if (currentBoss.next != null) {
                        System.out.println("\n--- ISTIRAHAT DI CAMP ---");
                        System.out.println("Pedagang keliling menghampiri anda.");
                        System.out.println("Gunakan Gold anda untuk persiapan boss selanjutnya!");
                        
                        // Buka Shop Lagi sebelum lanjut ke boss berikutnya
                        player.openShopMenu(shop); 
                        
                        // Equip senjata baru jika ada
                        player.weaponStackManager.equipWeapon();
                        
                        // Pindah ke Boss Berikutnya
                        currentBoss = currentBoss.next;
                        stage++;
                    } else {
                        // Boss Habis (Tamat)
                        System.out.println("\n\n============================================");
                        System.out.println("         SELAMAT! ANDA MENAMATKAN GAME      ");
                        System.out.println("           SEMUA BOSS TELAH KALAH           ");
                        System.out.println("============================================");
                        break; // Keluar dari loop while
                    }

                } else {
                    // --- JIKA KALAH ---
                    System.out.println("\nGAME OVER. Perjuangan anda terhenti di Stage " + stage);
                    System.out.println("Silakan coba lagi dari awal.");
                    break; // Keluar dari loop while
                }
            }
        }else if(choice == 2){
            System.out.println("Belum ada");
        }else{
            input.close();
            return;
        }
    }
}
