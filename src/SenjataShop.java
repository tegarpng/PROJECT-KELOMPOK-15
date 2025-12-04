public class SenjataShop {
    Weapon head;

    public void addweapon(String nama, int physicaldamage, int magicpower, int cost){
        Weapon weapon = new Weapon(nama, physicaldamage, magicpower, cost);
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

    public Weapon getweapon(String nama){
        Weapon curr = head;
        while(curr != null){
            if(curr.namasenjata.equals(nama)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public void display(){
        Weapon curr = head;
        int count = 1;
        while(curr != null){
            System.out.println(count +". "+ curr.namasenjata + " Damage : " + curr.physicaldamage + " Magic Power : " + curr.magicpower + " Cost : " + curr.cost);
            count++;
            curr = curr.next;
        }
    }
}
