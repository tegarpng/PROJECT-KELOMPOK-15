public class SenjataPlayer {
    Weapon head;
    public void addweapon(String nama, int physicaldamage, int magicpower){
        Weapon weapon = new Weapon(nama, physicaldamage, magicpower);
        if(head == null){
            head = weapon;
        }else{
            Weapon curr = head;
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = weapon;
        }
    }
    public void display(){
        Weapon curr = head;
        while(curr != null){
            System.out.println(curr.namasenjata + " Damage : " + curr.physicaldamage);
            curr = curr.next;
        }
    }
}
