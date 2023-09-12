

public class Buyable {
    
    // Sellable generic variables
    private double price;
    private String itemName;
    private String itemCategory;
    // I added this so that the admin can create a generic item but still provide it with information
    private String additionalInformation;
    // isCustom is only true if the item is created by the admin, the item goes into the custom category in the store
    private boolean isCustom;
    // isReturn is only true if the item is returned, it puts the item in the returns category in the store
    private boolean isReturn;
    
    public Buyable(double price, String name, String category)
    {
        isCustom = false;
        isReturn = false;
        this.price = price;
        itemName = name;
        itemCategory = category;
    }
    // This constructor is only used to create custom items by the admin while the program is running
    public Buyable(double price, String name, String category, String additionalInformation){
        isCustom = true;
        isReturn = false;
        this.price = price;
        itemName = name;
        itemCategory = category;
        this.additionalInformation = additionalInformation;

    }
    // Show details is different for every item, this is why it's overridden in its subclasses
    public void showDetails(){
        System.out.println("The details for " + getItemName() + ":");
        System.out.println("Price: " + getPrice());
        System.out.println("Category: " + getItemCategory());
        if (additionalInformation != null){
            System.out.println(additionalInformation);
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean custom) {
        isCustom = custom;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setIsReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}
