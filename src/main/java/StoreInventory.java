
import java.util.ArrayList;

public class StoreInventory {
    // All the different categories of inventory
    // I added furniture, custom, and the return category to expand the store's features
    
    private ArrayList<BuyableClothing> clothesForSale = new ArrayList<>();
    private ArrayList<BuyableFood> foodForSale = new ArrayList<>();
    private ArrayList<BuyableGame> gamesForSale = new ArrayList<>();
    private ArrayList<BuyableFurniture> furnitureForSale = new ArrayList<>();
    private ArrayList<Buyable> customForSale = new ArrayList<>();
    private ArrayList<Buyable> returnedForSale = new ArrayList<>();
    private ArrayList<Buyable> soldItems = new ArrayList<>();
    
    public StoreInventory()
    {
        populateClothesInventory();
        populateFoodInventory();
        populateGamesInventory();
        populateFurnitureInventory();
    }
    
    // Getters and setters for inventory lists
    
    public ArrayList<BuyableClothing> getClothesInventory()
    {
        return clothesForSale;
    }
    
    public ArrayList<BuyableFood> getFoodInventory()
    {
        return foodForSale;
    }
    
    public ArrayList<BuyableGame> getGamesInventory()
    {
        return gamesForSale;
    }

    public ArrayList<BuyableFurniture> getFurnitureInventory() {return furnitureForSale;}
    public ArrayList<Buyable> getCustomsInventory() {
        return customForSale;
    }

    public ArrayList<Buyable> getReturnedInventory() { return returnedForSale; }

    // Returns a master list of all inventory items at once
    public ArrayList<Buyable> getFullInventoryList()
    {
        ArrayList<Buyable> fullInventory = new ArrayList<>();
        fullInventory.addAll(clothesForSale);
        fullInventory.addAll(foodForSale);
        fullInventory.addAll(gamesForSale);
        fullInventory.addAll(furnitureForSale);
        fullInventory.addAll(customForSale);
        fullInventory.addAll(returnedForSale);
        
        return fullInventory;
    }

    public ArrayList<Buyable> getSoldItems() {
        return soldItems;
    }

    // This method shows every item that has ever been sold in the store and relevant info to go along with it
    public void seeSoldItemsDatabase(){
        for (Buyable item : soldItems){
            boolean returnedToStore = false;
            // If we have the item, that means it must've been returned to the store
            if (getFullInventoryList().contains(item)){
                returnedToStore = true;
            }
            System.out.println("Name: " + item.getItemName());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Was Returned? : " + returnedToStore);
        }
    }

    // Method has been extended to include the new arraylists
    public void removeItemFromInventory(Buyable item)
    {
        if(item instanceof BuyableClothing)
        {
            clothesForSale.remove((BuyableClothing)item);
        }
        else if(item instanceof BuyableFood)
        {
            foodForSale.remove((BuyableFood)item);
        }
        else if(item instanceof BuyableGame)
        {
            gamesForSale.remove((BuyableGame)item);
        }
        else if(item instanceof BuyableFurniture)
        {
            furnitureForSale.remove((BuyableFurniture)item);
        }
        else if (item != null){
            if (item.isCustom()){
                customForSale.remove(item);
            }
            if (item.isReturn()){
                returnedForSale.remove(item);
            }
        }
    }

    // Method has been extended to include the new arraylists
    public void restockItemToInventory(Buyable item)
    {
        if(item instanceof BuyableClothing)
        {
            clothesForSale.add((BuyableClothing)item);
        }
        else if(item instanceof BuyableFood)
        {
            foodForSale.add((BuyableFood)item);
        }
        else if(item instanceof BuyableGame)
        {
            gamesForSale.add((BuyableGame)item);
        }
        else if (item instanceof BuyableFurniture)
        {
            furnitureForSale.add((BuyableFurniture) item);
        }
    }
    
    // Methods to populate the inventory
    private void populateClothesInventory()
    {
        // Master list of all clothes held in the store on opening
        
        // Hoodies
        BuyableClothing smallHoodie = new BuyableClothing(59.99, "Hoodie", "small");
        clothesForSale.add(smallHoodie);
        BuyableClothing mediumHoodie = new BuyableClothing(59.99, "Hoodie", "medium");
        clothesForSale.add(mediumHoodie);
        BuyableClothing largeHoodie = new BuyableClothing(59.99, "Hoodie", "lage");
        clothesForSale.add(largeHoodie);
        
        // Shoes
        BuyableClothing dressShoes = new BuyableClothing(99.99, "Dress Shoes", "8");
        clothesForSale.add(dressShoes);
        BuyableClothing sandals = new BuyableClothing(9.99, "Sandals", "5");
        clothesForSale.add(sandals);
        
        // Gloves
        BuyableClothing gloves = new BuyableClothing(13.49, "Gloves", "Medium");
        addMultiple(gloves, 3);
        // Fix the names so that the user can see the sizes
        for (BuyableClothing clothing : clothesForSale){
            clothing.setItemName(clothing.getItemName() + "(" + clothing.getSize() + ")");
        }
    }
    
    private void populateFoodInventory()
    {
        // Master list of all food held in the store on opening
        
        // Perishables
        BuyableFood pizza = new BuyableFood(12.99, "Pizza", 400);
        foodForSale.add(pizza);
        BuyableFood lasagna = new BuyableFood(24.00, "Lasagna", 1000);
        foodForSale.add(lasagna);
        BuyableFood spinach = new BuyableFood(3.99, "Spinach", 250);
        foodForSale.add(spinach);
        
        // Non-perishables
        BuyableFood beans = new BuyableFood(1.49, "Beans", 300);
        foodForSale.add(beans);
        BuyableFood noodles = new BuyableFood(0.99, "Noodles", 125);
        foodForSale.add(noodles);
        BuyableFood rice = new BuyableFood(7.99, "Rice", 2000);
        addMultiple(rice, 5);
        // Fix the names so that the user can see the weight of the food
        for (BuyableFood food : foodForSale){
            food.setItemName(food.getItemName() + "(" + food.getWeight() + "g)");
        }
        
    }
    
    private void populateGamesInventory()
    {
        // Master list of all games held in the store on opening
        
        // Board games
        BuyableGame monopoly = new BuyableGame(19.99, "Monopoly", 4, "Board Game");
        gamesForSale.add(monopoly);
        BuyableGame scrabble = new BuyableGame(24.99, "Scrabble", 2, "Board Game");
        gamesForSale.add(scrabble);
              
        // Computer games
        BuyableGame breathOfTheWild = new BuyableGame(79.99, "Breath of the Wild", 1, "Video Game");
        gamesForSale.add(breathOfTheWild);
        BuyableGame forza = new BuyableGame(59.99, "Forza", 2, "Video Game");
        gamesForSale.add(forza);
    }

    private void populateFurnitureInventory(){
        // Master list of all furniture held in the store on opening

        // Indoor Furniture
        BuyableFurniture smallCouch = new BuyableFurniture(49.99, "Small Couch", 3);
        furnitureForSale.add(smallCouch);
        BuyableFurniture largeCouch = new BuyableFurniture(79.99, "Large Couch", 6);
        furnitureForSale.add(largeCouch);
        BuyableFurniture dinnerTable = new BuyableFurniture(99.99, "Dinner Table", 6);
        furnitureForSale.add(dinnerTable);
        BuyableFurniture coffeeTable = new BuyableFurniture(39.99, "Coffee Table", 2);
        furnitureForSale.add(coffeeTable);

        // Outdoor Furniture
        BuyableFurniture foldableChair = new BuyableFurniture(29.99, "Foldable Chair", 1);
        furnitureForSale.add(foldableChair);


    }
    
    // Helper method to add multiple copies of the same item to the inventory at once
    // (I altered this method so that it creates a new instance of the item and not just be a reference to the original)
    private void addMultiple(Buyable item, int numberToAdd)
    {
        if(item instanceof BuyableClothing)
        {
            for(int i = 0; i < numberToAdd; i++)
            {
                BuyableClothing b = new BuyableClothing(item.getPrice(), item.getItemName(), ((BuyableClothing) item).getSize());
                clothesForSale.add(b);
            }
        }
        else if(item instanceof BuyableFood)
        {
            for(int i = 0; i < numberToAdd; i++)
            {
                BuyableFood b = new BuyableFood(item.getPrice(), item.getItemName(), ((BuyableFood) item).getWeight());
                foodForSale.add(b);
            }            
        }
        else if(item instanceof BuyableGame)
        {
             for(int i = 0; i < numberToAdd; i++)
            {
                BuyableGame b = new BuyableGame(item.getPrice(), item.getItemName(), ((BuyableGame) item).getNumPlayers(), ((BuyableGame) item).getGenre());
                gamesForSale.add(b);
            }           
        }
        else if(item instanceof BuyableFurniture)
        {
            for(int i = 0; i < numberToAdd; i++)
            {
                BuyableFurniture b = new BuyableFurniture(item.getPrice(), item.getItemName(), ((BuyableFurniture) item).getPersonCapacity());
                furnitureForSale.add(b);
            }
        }
    }
}
