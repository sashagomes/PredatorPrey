public class runConfig {
    int numWolves;
    int numRabbits;
    int num_timesteps;
    float hours_per_timestep;

    public runConfig(int numWolves, int numRabbits, int num_timesteps, float hours_per_timestep) {
        this.numWolves = numWolves;
        this.numRabbits = numRabbits;
        this.num_timesteps = num_timesteps;
        this.hours_per_timestep = hours_per_timestep;
    }
}
