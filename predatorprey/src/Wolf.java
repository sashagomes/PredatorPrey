public class Wolf extends Animal {

    public float vision;

    public Wolf(int pos_x, int pos_y, float vision) {
        super(pos_x, pos_y);
        this.vision = vision;
    }

    public void howl(){
        System.out.println("Auuuu!");
    }
}
