
public class BuyableClothing extends Buyable {
    // Added show details method
    private String size;
    
    public BuyableClothing(double price, String name, String size) {
        super(price, name, "Clothing");
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    @Override
    public void showDetails(){
        super.showDetails();
        System.out.println("Size: " + getSize());
    }

}
