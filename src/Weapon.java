public class Weapon {
    int id = 1;
    String namasenjata;
    int cost;
    int physicaldamage;
    int magicpower;
    Weapon next;
    Weapon prev;
    String role;
    public Weapon(String namasenjata, String role, int physicaldamage, int magicpower, int cost, int id) {
        this.role = role;
        this.namasenjata = namasenjata;
        this.physicaldamage = physicaldamage;
        this.magicpower = magicpower;
        this.cost = cost;
        this.next = null;
        this.prev = null;
        this.id = id;
    }

    public Weapon(String namasenjata, String role, int physicaldamage, int magicpower, int cost) {
        this.role = role;
        this.namasenjata = namasenjata;
        this.physicaldamage = physicaldamage;
        this.magicpower = magicpower;
        this.cost = cost;
        this.next = null;
        this.prev = null;
    }
}

class Armor {
    String namaarmor;
    int physicaldefense;
    int magicdefense;
    int cost;
    Armor next;
    Armor prev;
    public Armor(String namaarmor, int physicaldefense, int magicdefense, int cost) {
        this.namaarmor = namaarmor;
        this.cost = cost;
        this.physicaldefense = physicaldefense;
        this.magicdefense = magicdefense;
        this.next = null;
        this.prev = null;
    }
}
