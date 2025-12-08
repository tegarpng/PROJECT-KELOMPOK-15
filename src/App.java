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
        Scanner input = new Scanner(System.in);
        

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
            input.nextLine();
            System.out.println("Masukkan Nama anda : ");
            String nama = input.nextLine();
            
            System.out.println("Masukkan Role anda : \n1. Fighter\n2. Magic\n3. Archer\n4. Tank\nMasukkan Pilihan anda :");
            int ambilrole = input.nextInt();
        
            String role;
            if(ambilrole == 1){
                role = "Fighter";
            }else if(ambilrole == 2){
                role = "Magic";
            }else if(ambilrole == 3){
                role = "Archer";
            }else if(ambilrole == 4){
                role = "Tank";
            }else{
                System.out.println("WOII GA ADA");
                return;
            }

            player.addplayer(nama, role);
            player.displayfighter();
            
            manageBoss boss = new manageBoss();
            boss.loadBoss();
            boss.displayboss();
            
            SenjataShop senjata = new SenjataShop();
            senjata.loadweapon();
            player.buyweapon(senjata);

        }else if(choice == 2){
            System.out.println("Belum ada");
        }else{
            input.close();
            return;
        }
    }
}
