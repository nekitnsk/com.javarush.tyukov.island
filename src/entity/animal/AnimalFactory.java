package entity.animal;

import entity.animal.herbivore.*;
import entity.animal.predator.*;

public class AnimalFactory {

    public Animal getAnimal(AnimalType type){
        Animal animal = null;

        animal = switch (type){
            case WOLF -> new Wolf(type);
            case BOA -> new Boa(type);
            case FOX -> new Fox(type);
            case BEAR -> new Bear(type);
            case EAGLE -> new Eagle(type);
            case HORSE -> new Horse(type);
            case DEER -> new Deer(type);
            case RABBIT -> new Rabbit(type);
            case MOUSE -> new Mouse(type);
            case GOAT -> new Goat(type);
            case SHEEP -> new Sheep(type);
            case BOAR -> new Boar(type);
            case BUFFALO -> new Buffalo(type);
            case DUCK -> new Duck(type);
            case CATERPILLAR -> new Caterpillar(type);
            default -> throw new IllegalArgumentException("Такого типа животного не существует: " + type);
        };

        return animal;
    }
}
