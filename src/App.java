public class App {
    public static void main(String[] args) throws Exception {
        ManagePlayer player = new ManagePlayer();
        player.addplayer("Aria", "Magic");
        player.addplayer("Borin", "Tank");
        player.GiveWeapon("Aria", "Lantern of Demon", 0, 24);
        player.GiveArmor("Aria", "Vest Level 2", 10, 10);
        player.GiveWeapon("Borin", "Gada", 10, 5);
        player.displayfighter();

        manageBoss boss = new manageBoss();
        boss.addboss("Goblin King", 1200, 30, 10);
        boss.displayboss();

        SenjataShop senjata = new SenjataShop();
        senjata.addweapon("Sword of Light", 25, 0, 100);
        senjata.addweapon("Staff of Wisdom", 0, 30, 150);

        senjata.display();
        player.buyweapon("Aria","Sword of Light", senjata);
        player.buyweapon("Aria","Staff of Wisdom", senjata);

        player.displayfighter();
    }
}
