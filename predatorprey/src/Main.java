import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Main {

    public static void main(String[] args) {

        try {
            boolean debug = true;
            FileWriter myWriter = null;

            // Grid environment
            int numXcells = 5;
            int numYcells = 4;
            float backgroundColor = 0.7f;


            // Create the population of wolves and rabbits with their initial positions and attributes.


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

            // create new FileWriter
            myWriter = new FileWriter("filename.txt");

            for (int timestep = 0; timestep < num_timesteps; timestep++) {

                //write to file
                for (int i = 0; i< animals.size(); i++) {
                    Animal a = animals.get(i);
                    if (a.getClass() == Wolf.class){
                        // timestep,iswolf,id,posx,posy,age,color,hunger,vision
                        String str = String.format("%d,1,%d,%d,%d,%f,,%f,%f\n",timestep, a.id, a.pos_x, a.pos_y,a.age,  ((Wolf) a).hunger,((Wolf) a).vision);
                        myWriter.write(str);
                    }else{
                        // timestep,iswolf,id,posx,posy,age,color,hunger,vision
                        String str = String.format("%d,0,%d,%d,%d,%f,%f,,\n",timestep, a.id, a.pos_x, a.pos_y,a.age, ((Rabbit) a).color);
                        myWriter.write(str);
                    }
                }



                if (debug)
                    System.out.println(timestep);

                time_in_hours += hours_per_timestep;

                // Update age of all animals (increases by hours_per_timestep)
                for (int i = 0; i< animals.size(); i++){
                    System.out.println("age");
                    animals.get(i).updateAge(hours_per_timestep);
                }

                // update hunger of wolves
                for (int i = 0; i< animals.size(); i++){
                    System.out.println("hunger");
                    Animal a = animals.get(i);
                    if(a.getClass() == Wolf.class){
                        Wolf wolf = (Wolf) a;
                        wolf.updateHunger(hours_per_timestep);
                    }
                }

                // update position of all animals
                for (int i = 0; i < animals.size(); i++) {
                    System.out.println("position");
                    animals.get(i).updatePosition();
                }

                // detect encounters
                for (int i = 0; i < animals.size(); i++) {
                    System.out.println("encounter");
                    Animal a = animals.get(i);
                    for(int j = i+1; j < animals.size(); j++){
                        Animal b = animals.get(j);
                        if(a.pos_x == b.pos_x && a.pos_y == b.pos_y){
                            if(a.getClass() != b.getClass()){
                                //predation
                                boolean aiswolf = a.getClass()==Wolf.class;

                                if(aiswolf){
                                    boolean wolfASeesRabbit = (backgroundColor -  ((Wolf) a).vision)/(backgroundColor - ((Rabbit) b).color) > 2;
                                    if(wolfASeesRabbit) {
                                        animals.remove(b);
                                        ((Wolf) a).eat_a_rabbit();
                                    }
                                }
                                else{
                                    boolean wolfBSeesRabbit = (backgroundColor -  ((Wolf) b).vision)/(backgroundColor - ((Rabbit) a).color) > 2;
                                    if(wolfBSeesRabbit) {
                                        animals.remove(a);
                                        ((Wolf) b).eat_a_rabbit();
                                    }
                                }
                            //if two animals (a and b) are the same species
                            }else{
                                ArrayList<Animal> children = reproduce(a, b);
                                animals.addAll(children);
                            }
                        }
                    }

                }

                // death of age or hunger
                for (int i = 0; i < animals.size(); i++) {
                    System.out.println("death");
                    boolean dies = animals.get(i).check_death();
                    if(dies){
                        animals.remove(i);
                    }
                }


            }

            //close file
            myWriter.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // create children objects from parents pA and pB
    private static ArrayList<Animal> reproduce(Animal pA, Animal pB) {

        ArrayList<Animal> children = new ArrayList<>();
        int num_children_wolves = 4;
        int num_children_rabbits = 5;


        if(pA.getClass() == Wolf.class){
            Wolf wA = (Wolf) pA;
            Wolf wB = (Wolf) pB;

            for(int i=0; i< num_children_wolves; i++){
                children.add(new Wolf(i, wA.pos_x, wA.pos_y, 0.5f*(wA.vision + wB.vision)));
            }
        }
        if(pA.getClass() == Rabbit.class){
            Rabbit rA = (Rabbit) pA;
            Rabbit rB = (Rabbit) pB;
            for(int i=0; i< num_children_rabbits; i++){
                children.add(new Rabbit(i, rA.pos_x, rA.pos_y, 0.5f*(rA.color + rB.color)));
            }
        }

        return children;


    }
}