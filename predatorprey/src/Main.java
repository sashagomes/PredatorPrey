import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Main {

    // Grid environment
    static int numXcells = 5;
    static int numYcells = 4;
    static float backgroundColor = 0.7f;

    public static void main(String[] args) {

        ArrayList<runConfig> runConfigs = new ArrayList<>();
        runConfigs.add(new runConfig(2, 2, 5, 2.5f));
        runConfigs.add(new runConfig(2, 2, 5, 2.5f));
        runConfigs.add(new runConfig(2, 2, 5, 2.5f));
        runConfigs.add(new runConfig(2, 2, 5, 2.5f));

        try {
            for(int i=0;i<runConfigs.size();i++) {
                runConfig run = runConfigs.get(i);
                run_simulation(i,run.numWolves, run.numRabbits, run.num_timesteps, run.hours_per_timestep);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void run_simulation(int runid,int numWolves,int numRabbits, int num_timesteps,float hours_per_timestep) throws IOException {


        boolean debug = true;
        FileWriter myWriter = null;

        // Store all of the animals in an array of animals
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

        // create new FileWriter
        myWriter = new FileWriter(String.format("simout/%d.txt",runid));
        myWriter.write("timestep,iswolf,id,posx,posy,age,color,hunger,vision\n");

//        float time_in_hours = 0f;
        for (int timestep = 0; timestep < num_timesteps; timestep++) {

            ArrayList<Animal> remove_these = new ArrayList<>();
            ArrayList<Animal> add_these = new ArrayList<>();

            //write to file
            for (int i = 0; i< animals.size(); i++) {
                Animal a = animals.get(i);
                if (a.getClass() == Wolf.class){
                    // timestep,iswolf,id,posx,posy,age,color,hunger,vision
                    String str = String.format("%d,1,%d,%d,%d,%f,,%f,%f\n",timestep, a.id, a.pos_x, a.pos_y,a.age,  ((Wolf) a).hunger,((Wolf) a).vision);
                    myWriter.write(str);
                }
                else{
                    // timestep,iswolf,id,posx,posy,age,color,hunger,vision
                    String str = String.format("%d,0,%d,%d,%d,%f,%f,,\n",timestep, a.id, a.pos_x, a.pos_y,a.age, ((Rabbit) a).color);
                    myWriter.write(str);
                }
            }

            if (debug)
                System.out.println(timestep);

//            time_in_hours += hours_per_timestep;

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
                for(int j = i+1; j < animals.size(); j++){
                    Animal b = animals.get(j);
                    if(a.pos_x == b.pos_x && a.pos_y == b.pos_y){
                        if(a.getClass() != b.getClass()){
                            //predation
                            boolean aiswolf = a.getClass()==Wolf.class;

                            if(aiswolf){
                                boolean wolfASeesRabbit = (backgroundColor -  ((Wolf) a).vision)/(backgroundColor - ((Rabbit) b).color) > 2;
                                if(wolfASeesRabbit) {
                                    remove_these.add(b);
                                    ((Wolf) a).eat_a_rabbit();
                                }
                            }
                            else{
                                boolean wolfBSeesRabbit = (backgroundColor -  ((Wolf) b).vision)/(backgroundColor - ((Rabbit) a).color) > 2;
                                if(wolfBSeesRabbit) {
                                    remove_these.add(a);
                                    ((Wolf) b).eat_a_rabbit();
                                }
                            }
                            //if two animals (a and b) are the same species
                        }else{
                            ArrayList<Animal> children = reproduce(a, b);
                            add_these.addAll(children);
                        }
                    }
                }

            }

            // death of age or hunger
            for (int i = 0; i < animals.size(); i++) {
                Animal a = animals.get(i);
                boolean dies = a.check_death();
                if(dies){
                    remove_these.add(a);
                }
            }


            // remove removed animals
            animals.removeAll(remove_these);

            //add children
            animals.addAll(add_these);


        }

        //close file
        myWriter.close();

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
                int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
                int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
                children.add(new Wolf(i, posX, posY, 0.5f*(wA.vision + wB.vision)));
            }
        }
        if(pA.getClass() == Rabbit.class){
            Rabbit rA = (Rabbit) pA;
            Rabbit rB = (Rabbit) pB;
            for(int i=0; i< num_children_rabbits; i++){
                int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
                int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
                children.add(new Rabbit(i, posX, posY, 0.5f*(rA.color + rB.color)));
            }
        }

        return children;


    }
}