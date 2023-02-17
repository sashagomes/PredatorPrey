import java.lang.Math;

class Main {
  public static void main(String[] args) {

  // 1. Create the population of wolves and rabbits with their initial positions and attributes.
    
    
    // Store all of the animals in an array of animals
    int numWolves = 2;
    int numRabsits = 2;
    arr Animals = [];

    for(int i=0; i< numWolves; i++){
      Animals[i] == new Wolf(Math.Random()*100, Math.Random()*100, Math.Random()*100)
      i++
    }
    
    for(int i=numWolves+1; i< numWolves + numRabbits; i++){
      Animals[i] == new Rabbit(Math.Random()*100, Math.Random()*100, Math.Random()*100)
      i++
    }

    
    // 2. loop through time
    int final_time = 100;
    for(int time=0;time<final_time;time++){

      // Update age of all animals
    
      // update hunger of wolves

      // update position of all animals

      // detect encounters

      // if encounter is same species, possibly reproduce

      // if encounter is wolf/rabbit, then predation happens

      // if age is too large, then die





    }

  }
}