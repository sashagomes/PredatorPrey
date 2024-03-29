public class runConfig {

    static String header = "numWolves,numRabbits,num_timesteps,hours_per_timestep,num_children_wolves,num_children_rabbits,numXcells,numYcells,backgroundColor,max_hunger,hunger_points_per_rabbit,delta_hunger_per_hour\n";

    int numWolves;
    int numRabbits;
    int num_timesteps;
    float hours_per_timestep;
    int num_children_wolves;
    int num_children_rabbits;
    int numXcells;
    int numYcells;
    float backgroundColor;
    float max_hunger; //of wolves
    float hunger_points_per_rabbit;
    float delta_hunger_per_hour;

    public runConfig(int numWolves, int numRabbits, int num_timesteps, float hours_per_timestep,  int num_children_wolves,
                     int num_children_rabbits, int numXcells, int numYcells, float backgroundColor,
                     float max_hunger, float hunger_points_per_rabbit, float delta_hunger_per_hour){

        this.numWolves = numWolves;
        this.numRabbits = numRabbits;
        this.num_timesteps = num_timesteps;
        this.hours_per_timestep = hours_per_timestep;
        this.num_children_wolves = num_children_wolves;
        this.num_children_rabbits = num_children_rabbits;
        this.numXcells = numXcells;
        this.numYcells = numYcells;
        this.backgroundColor = backgroundColor;
        this.max_hunger = max_hunger;
        this.hunger_points_per_rabbit = hunger_points_per_rabbit;
        this.delta_hunger_per_hour = delta_hunger_per_hour;

    }

    public String asString() {
        return String.format("%d,%d,%d,%f,%d,%d,%d,%d,%f,%f,%f,%f\n",
                numWolves, numRabbits, num_timesteps, hours_per_timestep,
                num_children_wolves,num_children_rabbits,numXcells,numYcells,
                backgroundColor,max_hunger, hunger_points_per_rabbit, delta_hunger_per_hour);
    }
}
