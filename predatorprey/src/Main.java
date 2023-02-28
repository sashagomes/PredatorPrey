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
        float time_in_hours = 0;
        float hours_per_timestep = 2.5f;

        for (int timestep = 0; timestep < num_timesteps; timestep++) {

            if (debug)
                System.out.println(timestep);

            time_in_hours += hours_per_timestep;

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
                                // TODO delete b
                                ((Wolf) a).eat_a_rabbit();
                            }
                            else{
                                // TODO delete a
                                ((Wolf) b).eat_a_rabbit();
                            }
                        }else{
                            ArrayList<Animal> children = reproduce(a, b);
                        }
                    }
                }

            }

            // death of age or hunger
            for (int i = 0; i < animals.size(); i++) {
                boolean dies = animals.get(i).check_death();
                if(dies){
                    // TODO delete the dead animal
                }
            }


        }

    }

    // create children objects from parents pA and pB
    private static ArrayList<Animal> reproduce(Animal pA, Animal pB){

        ArrayList<Animal> children = new ArrayList<>();
        int num_children_wolves = ; //TODO research this
        int num_children_rabitts = ; //TODO research this


        if(pA.getClass() == Wolf.class){
            for(int i=0; i< num_children_wolves, i++){
                children.add(new Wolf(i, pA.pos_X, pA.pos_Y, 0.5*(pA.vision + pB.vision));
            }
        }
        if(pA.getClass() == Rabbit.class){

        }

        return children;
    }
}