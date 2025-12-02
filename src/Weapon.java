public class Weapon {
    String namasenjata;
    int physicaldamage;
    int magicpower;
    Weapon next;
    Weapon prev;
    public Weapon(String namasenjata, int physicaldamage, int magicpower) {
        this.namasenjata = namasenjata;
        this.physicaldamage = physicaldamage;
        this.magicpower = magicpower;
        this.next = null;
        this.prev = null;
    }
}
class Armor {
    String namaarmor;
    int physicaldefense;
    int magicdefense;
    Armor next;
    Armor prev;
    public Armor(String namaarmor, int physicaldefense, int magicdefense) {
        this.namaarmor = namaarmor;
        this.physicaldefense = physicaldefense;
        this.magicdefense = magicdefense;
        this.next = null;
        this.prev = null;
    }
}
