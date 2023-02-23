public class Animal {

    public int id;
    public int pos_x;
    public int pos_y;

    public Animal(int id, int pos_x, int pos_y) {
        this.id = id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public void describe(){
        System.out.println("pos_x = " + pos_x + ", pos_y = " + pos_y);
    }

    public void update_position(){
        System.out.println("updating position of animal " + this.id);
    }

}
