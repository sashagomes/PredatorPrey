public class Wolf extends Animal {

    public float vision;
    public float hunger;

    public Wolf(int id, int pos_x, int pos_y, float vision) {
        super(id, pos_x, pos_y);
        this.vision = vision;
        this.hunger = 0f;
    }

    public void updateHunger(float time_in_hours) {
        float delta_hunger_per_hour = 3.4f;
        // hunger increase by time multiplied by ratio (hunger increase per hour
        hunger += delta_hunger_per_hour * time_in_hours;
    }

    public void eat_a_rabbit(){
        float hunger_points_per_rabbit = 25.3f;
        this.hunger -= hunger_points_per_rabbit;
    }



}
