import java.util.concurrent.ThreadLocalRandom;

public class Animal {

    public int id;
    public int pos_x;
    public int pos_y;
    public float age;

    public Animal(int id, int pos_x, int pos_y) {
        this.id = id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.age = 0f;
    }

    public void describe(){
        System.out.println("pos_x = " + pos_x + ", pos_y = " + pos_y);
    }

    public void updateAge(float time){
        this.age += time;
    }

    public void death(){
        //if age gets too high
        //if wolf hunger gets too high
    }

    public void updatePosition(){
        //move pos_x right or left or stay (randomly) on the grid
        this.pos_x += ThreadLocalRandom.current().nextInt(-1,2);
        //move pos_y up or down or stay (randomly) on the grid
        this.pos_y += ThreadLocalRandom.current().nextInt(-1,2);
    }



    }




}
