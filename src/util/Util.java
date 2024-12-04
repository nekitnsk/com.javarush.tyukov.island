package util;

import entity.animal.AnimalType;

public final class Util {

    public static String returnTypeAsString(AnimalType animalType){
        String type = animalType.toString().substring(0,1).toUpperCase() + animalType.toString().substring(1).toLowerCase();
        return type;
    }
}
