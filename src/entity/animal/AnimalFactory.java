package entity.animal;

import entity.animal.herbivore.*;
import entity.animal.predator.*;

public class AnimalFactory {

    public Animal getAnimal(AnimalType type){
        Animal animal = null;

        animal = switch (type){
            case WOLF -> new Wolf();
            case BOA -> new Boa();
            case FOX -> new Fox();
            case BEAR -> new Bear();
            case EAGLE -> new Eagle();
            case HORSE -> new Horse();
            case DEER -> new Deer();
            case RABBIT -> new Rabbit();
            case MOUSE -> new Mouse();
            case GOAT -> new Goat();
            case SHEEP -> new Sheep();
            case BOAR -> new Boar();
            case BUFFALO -> new Buffalo();
            case DUCK -> new Duck();
            case CATERPILLAR -> new Caterpillar();
            default -> throw new IllegalArgumentException("Такого типа животного не существует: " + type);
        };

        return animal;
    }
}
