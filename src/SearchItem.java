public class SearchItem {

    public Weapon searchWeaponById(Weapon head, int id) {
        Weapon curr = head;
        while (curr != null) {
            if (curr.id == id) {
                System.out.println("Weapon ditemukan: " + curr.namasenjata);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Weapon dengan ID " + id + " tidak ditemukan!");
        return null;
    }

    public Weapon searchWeaponByName(Weapon head, String nama) {
        Weapon curr = head;
        while (curr != null) {
            if (curr.namasenjata.equalsIgnoreCase(nama)) {
                System.out.println("Weapon ditemukan: " + curr.namasenjata);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Weapon dengan nama \"" + nama + "\" tidak ditemukan!");
        return null;
    }

    public Weapon searchWeaponByPhysicalDamage(Weapon head, int dmg) {
        Weapon curr = head;
        while (curr != null) {
            if (curr.physicaldamage == dmg) {
                System.out.println("Weapon ditemukan (Physical Damage " + dmg + "): " + curr.namasenjata);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Weapon dengan physical damage " + dmg + " tidak ditemukan!");
        return null;
    }

    public Weapon searchWeaponByMagicPower(Weapon head, int mp) {
        Weapon curr = head;
        while (curr != null) {
            if (curr.magicpower == mp) {
                System.out.println("Weapon ditemukan (Magic Power " + mp + "): " + curr.namasenjata);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Weapon dengan magic power " + mp + " tidak ditemukan!");
        return null;
    }

    public Weapon searchWeaponByCost(Weapon head, int cost) {
        Weapon curr = head;
        while (curr != null) {
            if (curr.cost == cost) {
                System.out.println("Weapon ditemukan (Cost " + cost + "): " + curr.namasenjata);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Weapon dengan harga " + cost + " tidak ditemukan!");
        return null;
    }

    public Armor searchArmorByName(Armor head, String nama) {
        Armor curr = head;
        while (curr != null) {
            if (curr.namaarmor.equalsIgnoreCase(nama)) {
                System.out.println("Armor ditemukan: " + curr.namaarmor);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Armor dengan nama \"" + nama + "\" tidak ditemukan!");
        return null;
    }

    public Armor searchArmorByPhysicalDefense(Armor head, int physical) {
        Armor curr = head;
        while (curr != null) {
            if (curr.physicaldefense == physical) {
                System.out.println("Armor ditemukan (Physical Defense " + physical + "): " + curr.namaarmor);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Armor dengan physical defense " + physical + " tidak ditemukan!");
        return null;
    }

    public Armor searchArmorByMagicDefense(Armor head, int magic) {
        Armor curr = head;
        while (curr != null) {
            if (curr.magicdefense == magic) {
                System.out.println("Armor ditemukan (Magic Defense " + magic + "): " + curr.namaarmor);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Armor dengan magic defense " + magic + " tidak ditemukan!");
        return null;
    }

    public Armor searchArmorByCost(Armor head, int cost) {
        Armor curr = head;
        while (curr != null) {
            if (curr.cost == cost) {
                System.out.println("Armor ditemukan (Cost " + cost + "): " + curr.namaarmor);
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Armor dengan harga " + cost + " tidak ditemukan!");
        return null;
    }
}
