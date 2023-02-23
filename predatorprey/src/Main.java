import java.lang.Math;
import java.util.ArrayList;
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
        ArrayList<Animal> animals = new ArrayList<>();

        // create wolves with random initial position
        for (int i=0; i < numWolves; i++) {
            int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
            int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
            animals.add(new Wolf(i, posX, posY, (float) Math.random()));
        }

        // create rabbits with random initial position
        for (int i = numWolves; i < numWolves + numRabbits; i++) {
            int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
            int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
            animals.add(new Rabbit(i, posX, posY, (float) Math.random()));
        }

        //////////////////////////////////
        if (debug) {
            for (Animal x : animals) {
                x.describe();
            }
        }
        //////////////////////////////////


        // 2. loop through time
        int num_timesteps = 5;
        float time = 0;
        float hours_per_timestep = 2.5f;

        for (int timestep = 0; timestep < num_timesteps; timestep++) {

            if (debug)
                System.out.println(timestep);

            time += hours_per_timestep;

            // Update age of all animals (increases by hours_per_timestep)
            for (int i = 0; i< animals.size(); i++){
                animals.get(i).updateAge(hours_per_timestep);
            }

            // update hunger of wolves
            for (int i = 0; i< animals.size(); i++){
                Animal a = animals.get(i);
                if(a.getClass() == Wolf.class){
                    Wolf wolf = (Wolf) a;
                    wolf.updateHunger(hours_per_timestep);
                }
            }

            // update position of all animals
            for (int i = 0; i < animals.size(); i++) {
                animals.get(i).updatePosition();
            }

            // detect encounters
            for (int i = 0; i < animals.size(); i++) {
                Animal a = animals.get(i);
                for(int j = 0; j < animals.size(); j++){
                    Animal b = animals.get(j);
                    if(a.pos_x == b.pos_x && a.pos_y == b.pos_y){
                        if(a.getClass() != b.getClass()){
                            //predation
                            boolean aiswolf = a.getClass()==Wolf.class;
                            if(aiswolf){
                                // delete b
                                // feed a
                            }
                            else{
                                // delete a
                                // feed b
                            }
                        }else{
                            //reproduction
                        }
                    }
                }

            }

            // death of age or hunger
            for (int i = 0; i < animals.size(); i++) {
                animals.get(i).death();
            }


        }

    }
}