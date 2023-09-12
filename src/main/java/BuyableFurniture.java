// New type of buyable item I created
public class BuyableFurniture extends Buyable{

    private int personCapacity;

    public BuyableFurniture(double price, String name, int personCapacity){
        super(price, name, "Supplement");
        this.personCapacity = personCapacity;
    }

    public int getPersonCapacity() {
        return personCapacity;
    }

    @Override
    public void showDetails(){
        super.showDetails();
        System.out.println("Person Capacity: " + getPersonCapacity());
    }
}
