import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;

class Main {
  public static void main(String[] args) {

    boolean debug = true;

    // Grid environment
    int numXcells = 5;
    int numYcells = 4;


    // 1. Create the population of wolves and rabbits with their initial positions and attributes.
    
    
    // Store all of the animals in an array of animals
    int numWolves = 2;
    int numRabbits = 2;
    Animal [] animals = new Animal[numWolves+numRabbits];

    for(int i=0; i< numWolves; i++){
      int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
      int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
      animals[i] = new Wolf(i, posX, posY, (float) Math.random());
    }
    
    for(int i=numWolves; i< numWolves + numRabbits; i++){
      int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
      int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
      animals[i] = new Rabbit(i, posX, posY, (float) Math.random());
    }

    //////////////////////////////////
    if(debug) {
      for (int i=0 ; i<animals.length ; i++) {
        Animal animal = animals[i];
        animal.describe();
      }
    }
    //////////////////////////////////

    
    // 2. loop through time
    int num_timesteps = 5;
    float time = 0;
    float hours_per_timestep = 2.5f;

    for(int timestep=0;timestep<num_timesteps;timestep++){

      if(debug)
        System.out.println(timestep);

      time += hours_per_timestep;

      // Update age of all animals (increases by hours_per_timestep)
    
      // update hunger of wolves

      // update position of all animals
      for (int i=0 ; i<animals.length ; i++) {
        animals[i].update_position();
      }

      // detect encounters

      // if encounter is same species, possibly reproduce

      // if encounter is wolf/rabbit, then predation happens

      // if age is too large, then die





    }

  }
}