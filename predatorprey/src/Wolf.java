import java.util.concurrent.ThreadLocalRandom;

public class Wolf extends Animal {

    public float vision;  // property of individual wolves
    public float hunger;
    public float max_hunger;

    public Wolf(int id, int pos_x, int pos_y, float vision, float max_hunger) {
        super(id, pos_x, pos_y);
        this.vision = vision;
        this.hunger = 0f;
        this.life_expectancy = 1000;
        this.age = ThreadLocalRandom.current().nextFloat()*this.life_expectancy;
        this.max_hunger = max_hunger;
    }

    public void updateHunger(float time_in_hours, float delta_hunger_per_hour) {
        // hunger increase by time multiplied by ratio (hunger increase per hour
        hunger += delta_hunger_per_hour * time_in_hours;
    }

    public void eat_a_rabbit(float hunger_points_per_rabbit){
        this.hunger -= hunger_points_per_rabbit;
    }

    @Override
    public boolean check_death() {
        boolean died_of_age =  super.check_death();
        boolean died_of_hunger = this.hunger > this.max_hunger;
        return died_of_age | died_of_hunger;
    }
}
