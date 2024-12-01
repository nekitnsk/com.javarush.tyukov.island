package entity.animal;

abstract public class Animal {

    public Double weight;
    public Double satiety;

    public void eat(){
        System.out.println("Eat " + this.getClass().getSimpleName());

    }

    public void reproduce(){
        System.out.println("reproduce " + this.getClass().getSimpleName());


    }

    public void move(){
        System.out.println("move " + this.getClass().getSimpleName());
    }

}
