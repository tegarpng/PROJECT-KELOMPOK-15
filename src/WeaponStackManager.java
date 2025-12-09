import java.util.Scanner;

public class WeaponStackManager {
    Character character;
    Weapon tempsenjata = null; // Stack (top)
    Scanner scanner = new Scanner(System.in);

    public WeaponStackManager(Character character) {
        this.character = character;
    }

    // PUSH ke stack
    public void push(Weapon senjata) {
        // Buat copy untuk stack agar tidak ada circular reference
        Weapon copy = new Weapon(senjata.namasenjata, senjata.role, senjata.physicaldamage, senjata.magicpower, senjata.cost, senjata.id);
        copy.next = tempsenjata; 
        tempsenjata = copy;
    }

    // POP stack
    public Weapon pop() {
        if (tempsenjata != null) {
            Weapon top = tempsenjata;
            tempsenjata = tempsenjata.next;
            top.next = null;
            return top;
        }
        return null;
    }

    // EQUIP WEAPON
    public void equipWeapon() {
        if (character.weapon == null) {
            System.out.println("❌ No weapons available to equip!");
            return;
        }
        
        System.out.println("\n========== EQUIP WEAPON ==========");
        System.out.println("Currently equipped: " + character.weapon.namasenjata);
        System.out.println("\nAvailable weapons to equip:");
        
        // Tampilkan semua weapon KECUALI yang sedang equipped (character.weapon)
        Weapon list = character.weapon.next; // Mulai dari weapon berikutnya
        int index = 1;
        
        if (list == null) {
            System.out.println("No other weapons available.");
            System.out.println("==================================\n");
            return;
        }
        
        // Tampilkan available weapons (kecuali yang sedang dipakai)
        while (list != null) {
            System.out.println(index + ". " + list.namasenjata + 
                               " (DMG: " + list.physicaldamage + 
                               ", MP: " + list.magicpower + ")");
            list = list.next;
            index++;
        }
        
        System.out.print("Choose weapon number: ");
        int choice = scanner.nextInt();
        
        // Validasi pilihan (1 sampai index-1)
        if (choice < 1 || choice >= index) {
            System.out.println("❌ Invalid choice.");
            System.out.println("==================================\n");
            return;
        }
        
        // Cari weapon yang dipilih dari available list
        list = character.weapon.next;
        int count = 1;
        while (count < choice && list != null) {
            list = list.next;
            count++;
        }
        
        if (list == null) {
            System.out.println("❌ Weapon not found.");
            System.out.println("==================================\n");
            return;
        }
        
        // SWAP LOGIC:
        // 1. Save current equipped weapon
        Weapon oldWeapon = character.weapon;
        
        // 2. Remove selected weapon dari available list
        // Cari node sebelum list dan update pointer-nya
        Weapon prev = character.weapon;
        while (prev != null && prev.next != list) {
            prev = prev.next;
        }
        
        if (prev != null) {
            prev.next = list.next; // Skip list dari linked list
        }
        
        // 3. Set selected weapon sebagai equipped (head)
        character.weapon = list;
        
        // 4. Insert old weapon ke posisi pertama di available list (character.weapon.next)
        oldWeapon.next = character.weapon.next;
        character.weapon.next = oldWeapon;
        
        // 5. Push old weapon ke history stack
        push(oldWeapon);
        
        System.out.println("✅ Successfully equipped: " + character.weapon.namasenjata);
        System.out.println("==================================\n");
    }

    // Tampilkan riwayat stack
    public void showWeaponHistory() {
        if (tempsenjata == null) {
            System.out.println("Weapon history is empty.");
            return;
        }

        System.out.println("Weapon History (Stack):");
        Weapon temp = tempsenjata;
        while (temp != null) {
            System.out.println("-   Menggunakan" + temp.namasenjata);
            temp = temp.next;
        }
    }
}
