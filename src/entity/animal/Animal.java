package entity.animal;


import entity.Entity;
import entity.Plant;
import island.Cell;
import island.Island;
import settings.Settings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

abstract public class Animal extends Entity {

    private Double weight;
    private Double maxWeight;
    private Integer maxPopulation;
    private Double food;
    private Integer speed;
    public AnimalType animalType;
    public Boolean isAvailable = true;
    Settings settings = Settings.getInstance();

    public Animal(AnimalType type){

        Map<String, Object> animalProperty = settings.getAnimalProperty(this.getClass().getSimpleName().toString());
        this.weight = Double.parseDouble(animalProperty.get("weight").toString()) / 2;
        this.maxWeight = Double.parseDouble(animalProperty.get("weight").toString());
        this.maxPopulation = Integer.valueOf(animalProperty.get("maxPopulation").toString());
        this.food = Double.parseDouble(animalProperty.get("food").toString());
        if(animalProperty.get("speed") != null) {
            this.speed = Integer.valueOf(animalProperty.get("speed").toString());
        }

        this.animalType = type;

    }

    public Boolean eat(Cell currentCell){
        currentCell.getLock().lock();
        isAvailable = false;
        Map<String, Object> probabilityOfEating = settings.probabilityOfEating(this.animalType.toString().toLowerCase());

        Map<String, Object> animalCanEat = probabilityOfEating
                .entrySet()
                .stream()
                .filter(animal -> (Integer) animal.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Animal> currentCellAnimals = currentCell
                .getAnimals()
                .stream()
                .filter(animal -> animal.isAvailable)
                .toList();
        List<Animal> currentCellAnimalsCanEat = currentCellAnimals
                .stream()
                .filter(animal -> animalCanEat.containsKey(animal.animalType.toString().toLowerCase()))
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
        isAvailable = true;
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



    public void reproduce(Cell currentCell){
        currentCell.getLock().lock();

        List<Animal> animalForReproduce = currentCell.getAnimals(animalType);

        if(animalForReproduce.size() > 1 && animalForReproduce.size() < maxPopulation){

                currentCell.addNewAnimal(animalType);
        }

        currentCell.getLock().unlock();
    }

    public void move(Island island, Cell currentCell){
        if(speed == 0 ) return;

        currentCell.getLock().lock();

        int populationInCell = currentCell.getAnimals(this.animalType).size();
        int column = currentCell.getMyColumn();
        int row = currentCell.getMyRow();
        Cell[][] cells = island.getCells();

        int maxRow = cells.length-1;
        int maxColumn = cells[maxRow].length-1;

        int newColumn = 0;
        int newRow = 0;

        int rnd = ThreadLocalRandom.current().nextInt(0, 3); //0 - up, 1 - down, 2 - left, 3- right

        switch (rnd){
            case 0: {
                newColumn = column - speed;
                if(newColumn < 0){
                    newColumn = 0;
                }
                newRow = row;
            }
            case 1: {
                newColumn = column + speed;
                if(newColumn > maxColumn){
                    newColumn = maxColumn;
                }
                newRow = row;
            }
            case 2: {
                newRow = row - speed;
                if(newRow < 0){
                    newRow = 0;
                }
                newColumn = column;
            }
            case 3: {
                newRow = row + speed;
                if(newRow > maxRow){
                    newRow = maxRow;
                }
                newColumn = column;

            }
        }

        Cell destCell = cells[newRow][newColumn];

        if(destCell.getAnimals(this.animalType).size() < populationInCell) {
            destCell.getLock().lock();
            if(destCell.getAnimals().add(this)) {
                currentCell.getAnimals().remove(this);
            }
            destCell.getLock().unlock();
        }

        currentCell.getLock().unlock();
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

}
