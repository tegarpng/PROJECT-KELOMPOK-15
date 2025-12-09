import java.util.Scanner;

public class WeaponStackManager {
    Character character;
    Weapon tempsenjata;
    Weapon senjatanow;
    Scanner scanner = new Scanner(System.in);

    public WeaponStackManager(Character character) {
        this.character = character;
    }

    // PUSH ke stack
    public void push(Weapon senjata) {
        if (senjata == null) return;
        // buat salinan supaya stack terpisah dari linked list utama
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

    public boolean isEmpty(){
        return tempsenjata == null;
    }

    // EQUIP WEAPON
    public void equipWeapon() {
        if (character == null || character.weapon == null) {
            System.out.println("No weapons available to equip!");
            return;
        }

        System.out.println("\n========== EQUIP WEAPON ==========");
        System.out.println("Currently equipped: " + character.weapon.namasenjata);
        System.out.println("\nAvailable weapons to equip:");

        // tampilkan semua weapon kecuali head
        Weapon cursor = character.weapon.next;
        int idx = 1;
        if (cursor == null) {
            System.out.println("No other weapons available.");
            System.out.println("==================================\n");
            return;
        }
        while (cursor != null) {
            System.out.println(idx + ". " + cursor.namasenjata + " Physical Damage :" + cursor.physicaldamage + " - Magic Power : " + cursor.magicpower+ ")");
            cursor = cursor.next;
            idx++;
        }

        System.out.print("Choose weapon number: ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice >= idx) {
            System.out.println("Invalid choice.");
            return;
        }

        // temukan selected node pada linked list karakter
        Weapon selected = character.weapon.next;
        int count = 1;
        while (selected != null && count < choice) {
            selected = selected.next;
            count++;
        }
        if (selected == null) {
            System.out.println("Weapon not found.");
            return;
        }

        // buat salinan dari head untuk history
        Weapon headCopy = new Weapon(character.weapon.namasenjata, character.weapon.role, character.weapon.physicaldamage, character.weapon.magicpower, character.weapon.cost, character.weapon.id);

        // swap data antara head dan selected
        String tmpName = character.weapon.namasenjata;
        String tmpRole = character.weapon.role;
        int tmpPhys = character.weapon.physicaldamage;
        int tmpMag = character.weapon.magicpower;
        int tmpCost = character.weapon.cost;
        int tmpId = character.weapon.id;

        character.weapon.namasenjata = selected.namasenjata;
        character.weapon.role = selected.role;
        character.weapon.physicaldamage = selected.physicaldamage;
        character.weapon.magicpower = selected.magicpower;
        character.weapon.cost = selected.cost;
        character.weapon.id = selected.id;

        selected.namasenjata = tmpName;
        selected.role = tmpRole;
        selected.physicaldamage = tmpPhys;
        selected.magicpower = tmpMag;
        selected.cost = tmpCost;
        selected.id = tmpId;

        // push headCopy (old weapon) ke history
        push(headCopy);

        System.out.println(character.orang + " successfully equipped: " + character.weapon.namasenjata);
    }

    // Remove and return the original Weapon (not a copy) from character linked list by id (searches head.next onward

    // Tampilkan riwayat stack
    public void showWeaponHistory() {
        if (tempsenjata == null) {
            System.out.println("Weapon history is empty.");
            return;
        }

        System.out.println("Weapon History (Stack):");
        Weapon temp = tempsenjata;
        while (temp != null) {
            System.out.println("-   Menggunakan " + temp.namasenjata);
            temp = temp.next;
        }
    }
}
