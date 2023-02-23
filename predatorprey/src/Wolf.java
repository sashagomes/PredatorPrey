public class Wolf extends Animal {

    public float vision;

    public Wolf(int id, int pos_x, int pos_y, float vision) {
        super(id, pos_x, pos_y);
        this.vision = vision;
    }

    public void howl(){
        System.out.println("Auuuu!");
    }
}
