import java.util.concurrent.ThreadLocalRandom;

public class Rabbit extends Animal {

    public float color;

    public Rabbit(int id, int pos_x, int pos_y, float color) {
        super(id, pos_x, pos_y);
        this.color = color;
        this.life_expectancy = 730; // 2 years
        this.age = ThreadLocalRandom.current().nextFloat()*this.life_expectancy;

    }
}
