public class App {
    public static void main(String[] args) throws Exception {
        ManagePlayer player = new ManagePlayer();
        player.addplayer("Aria");
        player.addplayer("Borin");
        player.GiveWeapon("Aria", "Sword", 25);
        player.GiveWeapon("Borin", "Shield", 10);
        player.GiveWeapon("Borin", "Axe", 30);
        player.displayfighter();
    }
}
