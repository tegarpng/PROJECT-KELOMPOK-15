public class Character {
    String orang;
    int physicaldefense;
    int magicdefense;
    int physicaldamage;
    int magicpower;
    int health;
    int gold;
    String role;
    Character next;
    Character prev;
    Weapon weapon;
    Armor armorplayer;
    SkillList mySkills;
    public Character(String orang, int health, String role,int physicaldamage, int magicpower, int physicaldefense, int magicdefense) {
        this.orang = orang;
        this.health = health;
        this.gold = 100;
        this.role = role;
        this.physicaldamage = physicaldamage;
        this.magicpower = magicpower;
        this.physicaldefense = physicaldefense;
        this.magicdefense = magicdefense;
        this.mySkills = new SkillList();
        this.weapon = null;
        this.armorplayer = null;
        this.next = null;
        this.prev = null;
    }
}

class Boss {
    String namaboss;
    int health;
    int physicaldamage;
    int magicpower;
    Boss next;
    Boss prev;
    public Boss(String namaboss, int health, int physicaldamage, int magicpower) {
        this.namaboss = namaboss;
        this.health = health;
        this.physicaldamage = physicaldamage;
        this.magicpower = magicpower;
        this.next = null;
        this.prev = null;
    }
}

