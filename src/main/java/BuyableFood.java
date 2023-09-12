
public class BuyableFood extends Buyable {
    // Added show details method
    private double weight;
    
    public BuyableFood(double price, String name, double weight) {
        super(price, name, "Food");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public void showDetails(){
        super.showDetails();
        System.out.println("Weight: " + getWeight() + "g");
    }
}
