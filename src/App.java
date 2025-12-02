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
    }
}
