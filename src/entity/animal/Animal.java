package entity.animal;


import entity.Entity;
import entity.Plant;
import island.Cell;
import settings.Settings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

abstract public class Animal extends Entity {

    private Double weight;
    private Double maxWeight;
    public Double satiety;
    public Double food;
    public Integer speed;
    public AnimalType animalType;
    Settings settings = Settings.getInstance();

    public Animal(AnimalType type){

        Map<String, Object> animalProperty = settings.getAnimalProperty(this.getClass().getSimpleName().toString());
        this.weight = Double.parseDouble(animalProperty.get("weight").toString()) / 2;
        this.maxWeight = Double.parseDouble(animalProperty.get("weight").toString());
        this.satiety = 50.0D;
        this.food = Double.parseDouble(animalProperty.get("food").toString());
        if(animalProperty.get("speed") != null) {
            this.speed = Integer.valueOf(animalProperty.get("speed").toString());
        }

        this.animalType = type;

    }

    public Boolean eat(Cell currentCell){
        currentCell.getLock().lock();
        Map<String, Object> probabilityOfEating = settings.probabilityOfEating(this.animalType.toString().toLowerCase());

        Map<String, Object> animalCanEat = probabilityOfEating
                .entrySet()
                .stream()
                .filter(entry -> (Integer) entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Animal> currentCellAnimals = currentCell.getAnimals();
        List<Animal> currentCellAnimalsCanEat = currentCellAnimals
                .stream()
                .filter(entry -> animalCanEat.containsKey(entry.animalType.toString().toLowerCase()))
                .toList();

        List<Plant> currentCellPlantsCanEat = List.of();
        if(animalCanEat.containsKey("plants")) {
            currentCellPlantsCanEat = currentCell.getPlants();
        }

        Boolean foodIsFind = false;
        Animal victim = null;
        Plant plant = null;
        double foodWeight;

        if(currentCellAnimalsCanEat.size() > 0  || currentCellPlantsCanEat.size() > 0) {

            while (!foodIsFind) {

                int rndIndex = ThreadLocalRandom.current().nextInt(0, currentCellAnimalsCanEat.size() + currentCellPlantsCanEat.size());
//
                double needFood = Math.min((getMaxWeight() - getWeight()), getFood());
                if(rndIndex < currentCellAnimalsCanEat.size()) {
                    victim = currentCellAnimalsCanEat.get(rndIndex);

                    int probability = (int) animalCanEat.get(victim.animalType.toString().toLowerCase());

                    int rnd = ThreadLocalRandom.current().nextInt(0, 100);

                    if (rnd < probability) {
                        foodIsFind = true;
                    }

                    foodWeight = victim.getWeight();
                    double delta = Math.min(foodWeight, needFood);
                    setWeight(getWeight() + delta);

                    victim.dieAnimal(currentCell);

                }else{
                    plant = currentCellPlantsCanEat.get(rndIndex - currentCellAnimalsCanEat.size());
                    foodWeight = plant.getWeight();

                    int iter = 0;
                    if(needFood < currentCellPlantsCanEat.size()){
                        iter = (int) needFood;
                    }else {
                        iter = currentCellPlantsCanEat.size()-1;
                    }

                    for (Plant eatPlant : currentCellPlantsCanEat) {
                        iter--;
                        setWeight(getWeight() + foodWeight);
                        removePlant(currentCell, eatPlant);
                        if(iter <= 0 ){
                            foodIsFind = true;
                            break;
                        }
                    }
                }
            }

        }else{
            setWeight(getWeight() - getFood());
            if(getWeight() <= 0 ){

                dieAnimal(currentCell);
            }
        }

        currentCell.getLock().unlock();
        return true;
    }

    public boolean dieAnimal(Cell cell){
        cell.getLock().lock();

        try{
            return cell.getAnimals().remove(this);
        }finally {
            cell.getLock().unlock();
        }
    }

    public void removePlant(Cell cell, Plant plant){
        cell.getLock().lock();
        try{
            cell.getPlants().remove(plant);
        }finally {
            cell.getLock().unlock();
        }
    }

    public Double getWeight() {
        return weight;
    }

    public Double getFood() {
        return food;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public Animal(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void reproduce(Cell currentCell){


    }

    public void move(Cell currentCell){
    }

}
