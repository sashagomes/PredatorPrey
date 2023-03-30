import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Main {

    static int largest_id = -1;

    public static void main(String[] args) {

        ArrayList<runConfig> runConfigs = new ArrayList<>();

//        runConfigs.add(new runConfig(20, 20,
//                20, 2.5f,
//                4, 5,
//                100,100,0.7f,
//                50, 25.3f,3.4f));
//
//        runConfigs.add(new runConfig(50, 50,
//                100, 5f,
//                4, 5,
//                1000,1000,0.7f,
//                75, 25.3f,3.4f));

        runConfigs.add(new runConfig(70, 100,
                1000, 2.5f,
                2, 5,
                250,250,0.3f,
                168, 50f,0f));

        try {

            FileWriter cfgWriter = new FileWriter("simout/cfg.txt");
            cfgWriter.write(runConfig.header);

            for(int i=0;i<runConfigs.size();i++) {
                System.out.println("run "+i);
                runConfig run = runConfigs.get(i);
                System.out.println(String.format("\tTotal days: %.2f",run.num_timesteps*run.hours_per_timestep/24f));
                cfgWriter.write(run.asString());
                run_simulation(i,run.numWolves, run.numRabbits,
                               run.num_timesteps, run.hours_per_timestep,
                               run.num_children_wolves, run.num_children_rabbits,
                               run.numXcells, run.numYcells, run.backgroundColor,
                                run.max_hunger, run.hunger_points_per_rabbit, run.delta_hunger_per_hour);
            }

            cfgWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Done");

    }

    private static void run_simulation(int runid, int numWolves, int numRabbits,
                                       int num_timesteps, float hours_per_timestep,
                                       int num_children_wolves, int num_children_rabbits,
                                       int numXcells, int numYcells, float backgroundColor,
                                       float max_hunger, float hunger_points_per_rabbit, float delta_hunger_per_hour) throws IOException {

        FileWriter simWriter = null;
        FileWriter logWriter = null;

        // Store all of the animals in an array of animals
        ArrayList<Animal> animals = new ArrayList<>();

        // create wolves with random initial position
        for (int i=0; i < numWolves; i++) {
            int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
            int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
            animals.add(new Wolf(generate_id(), posX, posY, (float) Math.random(), max_hunger));
        }

        // create rabbits with random initial position
        for (int i = numWolves; i < numWolves + numRabbits; i++) {
            int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
            int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
            animals.add(new Rabbit(generate_id(), posX, posY, (float) Math.random()));
        }

        // create new FileWriter
        simWriter = new FileWriter(String.format("simout/%d.txt",runid));
        simWriter.write("timestep,iswolf,id,posx,posy,age,color,hunger,vision\n");

        logWriter = new FileWriter(String.format("simout/%d_log.txt",runid));

        for (int timestep = 0; timestep < num_timesteps; timestep++) {

            if(timestep%100==0)
                System.out.println(timestep);

            ArrayList<Animal> remove_these = new ArrayList<>();
            ArrayList<Animal> add_these = new ArrayList<>();

            //write to file
            for (int i = 0; i< animals.size(); i++) {
                Animal a = animals.get(i);
                if (a.getClass() == Wolf.class){
                    // timestep,iswolf,id,posx,posy,age,color,hunger,vision
                    String str = String.format("%d,1,%d,%d,%d,%f,,%f,%f\n",timestep, a.id, a.pos_x, a.pos_y,a.age,  ((Wolf) a).hunger,((Wolf) a).vision);
                    simWriter.write(str);
                }
                else{
                    // timestep,iswolf,id,posx,posy,age,color,hunger,vision
                    String str = String.format("%d,0,%d,%d,%d,%f,%f,,\n",timestep, a.id, a.pos_x, a.pos_y,a.age, ((Rabbit) a).color);
                    simWriter.write(str);
                }
            }

            // Update age of all animals (increases by hours_per_timestep)
            for (int i = 0; i< animals.size(); i++){
                animals.get(i).updateAge(hours_per_timestep);
            }

            // update hunger of wolves
            for (int i = 0; i< animals.size(); i++){
                Animal a = animals.get(i);
                if(a.getClass() == Wolf.class){
                    Wolf wolf = (Wolf) a;
                    wolf.updateHunger(hours_per_timestep,delta_hunger_per_hour);
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
                                boolean wolfASeesRabbit = true; //(backgroundColor -  ((Wolf) a).vision)/(backgroundColor - ((Rabbit) b).color) > 2;
                                if(wolfASeesRabbit) {
                                    remove_these.add(b);

                                    logWriter.write(String.format("Wolf %d ate rabbit %d\n",a.id,b.id));
                                    ((Wolf) a).eat_a_rabbit(hunger_points_per_rabbit);
                                }
                            }
                            else{
                                boolean wolfBSeesRabbit = true; // (backgroundColor -  ((Wolf) b).vision)/(backgroundColor - ((Rabbit) a).color) > 2;
                                if(wolfBSeesRabbit) {
                                    remove_these.add(a);
                                    logWriter.write(String.format("Wolf %d ate rabbit %d\n",b.id,a.id));
                                    ((Wolf) b).eat_a_rabbit(hunger_points_per_rabbit);
                                }
                            }
                            //if two animals (a and b) are the same species
                        }else{
                            ArrayList<Animal> children = reproduce(a, b, num_children_wolves, num_children_rabbits, numXcells, numYcells,max_hunger,logWriter);
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
            if(remove_these.size()>0) {
                for(Animal a : remove_these)
                    logWriter.write(String.format("%d Animal %d (%s) was removed\n",timestep,a.id,a.getClass().getSimpleName()));
                animals.removeAll(remove_these);
            }

            //add children
            if(add_these.size()>0) {
                for (Animal a : add_these)
                    logWriter.write(String.format("%d Animal %d (%s) was added\n",timestep, a.id,a.getClass().getSimpleName()));
                animals.addAll(add_these);
            }

        }

        //close file
        simWriter.close();
        logWriter.close();

    }

    // create children objects from parents pA and pB
    private static ArrayList<Animal> reproduce(Animal pA, Animal pB, int num_children_wolves, int num_children_rabbits,
                                               int numXcells, int numYcells, float max_hunger, FileWriter logWriter) throws IOException{

        ArrayList<Animal> children = new ArrayList<>();

        if(pA.getClass() == Wolf.class){

            logWriter.write(String.format("Wolf reproduction: %d,%d\n",pA.id,pB.id));

            Wolf wA = (Wolf) pA;
            Wolf wB = (Wolf) pB;

            for(int i=0; i< num_children_wolves; i++){
                int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
                int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
                children.add(new Wolf(generate_id(), posX, posY, 0.5f*(wA.vision + wB.vision), max_hunger));
            }

        }
        if(pA.getClass() == Rabbit.class){

            logWriter.write(String.format("Rabbit reproduction: %d,%d\n",pA.id,pB.id));

            Rabbit rA = (Rabbit) pA;
            Rabbit rB = (Rabbit) pB;
            for(int i=0; i< num_children_rabbits; i++){
                int posX = ThreadLocalRandom.current().nextInt(0, numXcells);
                int posY = ThreadLocalRandom.current().nextInt(0, numYcells);
                children.add(new Rabbit(generate_id(), posX, posY, 0.5f*(rA.color + rB.color)));
            }
        }

        return children;

    }


    private static int generate_id(){
        largest_id += 1;
        return largest_id;
    }

}