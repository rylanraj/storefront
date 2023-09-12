import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    Scanner scan = new Scanner(System.in);
    
    // User data variables
    private BankAccount myBankAccount;
    private boolean makeAdmin;
    private boolean stillShopping;
    private ArrayList<Buyable> myStuff;
    private ArrayList<Buyable> myShoppingCart;
    
    // Store data variables
    private StoreInventory storeInventory;
    private boolean addedOwnedObjects = false;
    private ArrayList<BankAccount> accounts;
    
    public Store()
    {
        accounts = new ArrayList<>();
        System.out.println("Welcome to my storefont!");
        setupAccounts();
        setupStore();
        presentShoppingMenu();
    }

    // Checker for if the user selects to create an admin or normal account
    private void adminChecker(){
        System.out.println("Would you like to create a regular account or create an admin account?");
        System.out.println("1. Admin account");
        System.out.println("2. Normal account");
        try{
            int selection = scan.nextInt();
            switch (selection){
                case 1:
                    makeAdmin = true;
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Please choose one of the options!");
                    adminChecker();
                    break;
            }
        }
        catch (Exception e){
            scan.nextLine();
            System.out.println("Your input caused an exception, please give valid inputs: " + e);
            adminChecker();
        }

    }

    // When the user is admin they can access admin tools
    private void adminToolsMenu(){
        System.out.println("What would you like to do as an admin?");
        System.out.println("1. Add a new item to the store");
        System.out.println("2. See sold items database");
        System.out.println("3. Change the balance of existing accounts");
        System.out.println("4. Exit Menu");
        try {
            int selection = scan.nextInt();
            scan.nextLine();
            switch (selection){
                case 1:
                    adminAddNewItem();
                    break;
                case 2:
                    storeInventory.seeSoldItemsDatabase();
                case 3:
                    adminChangeBalanceOfAccount();
                    break;
                case 4:
                    break;
            }
        }
        catch (Exception e){
            System.out.println("Your input caused an exception, please give valid inputs: " + e);
            scan.nextLine();
            adminToolsMenu();
        }

    }
    // Admins of the store can change balance of every account in the store's system
    private void adminChangeBalanceOfAccount(){
        // Display all accounts
        System.out.println("Please select an account to change balance of (input name)");
        for(BankAccount account : accounts){
            System.out.println(account.getAccountName() + "");
        }
        // Get the user to choose one
        String chosenAccount = scan.nextLine();
        for (BankAccount account : accounts){
            if (account.getAccountName().equals(chosenAccount)){
                System.out.println("You have chosen " + account.getAccountName() + " (Balance: " + account.getBalance() + ")");
                System.out.println("Enter how much you'd like to change the account's balance by");
                double balanceChange = scan.nextDouble();
                scan.nextLine();
                account.depositMoney(balanceChange, this);
                // Account balance's can should not be negative!
                if (account.getBalance() < 0){
                    account.setBalance(0);
                }
                System.out.println("Successfully changed balance to: " + account.getBalance());
            }
        }
    }

    // Admins of the store can add new items, use the input of the user to create the item
    private void adminAddNewItem() {
        System.out.println("Enter the price of your item");
        double price = scan.nextDouble();
        scan.nextLine();
        System.out.println("Enter the name of your item");
        String name = scan.nextLine();
        System.out.println("Enter any additional information of your item");
        String additionalInformation = scan.nextLine();
        String category = "Custom";
        Buyable buyable = new Buyable(price, name, category, additionalInformation);
        storeInventory.getFullInventoryList().add(buyable);
        storeInventory.getCustomsInventory().add(buyable);
    }

    // Method to log into an existing account
    private void logIntoExistingAccount(){
        boolean accountFound = false;
        // Present the accounts the user has created
        System.out.println("You have created the following accounts");
        for (BankAccount account : accounts){
            // Show if it's an admin account or not
            if (account.isAdmin()){
                System.out.println(account.getAccountName() + " (Admin) (Balance: " + account.getBalance() + ")");
            }
            else {
                System.out.println(account.getAccountName() + " (Balance: " + account.getBalance() + ")");
            }
        }
        // Let them chose an account from the arraylist
        System.out.println("Which account would you like to log into? (only enter account name!)");
        String accountSelection = scan.nextLine();
        System.out.println("You have selected " + accountSelection);
        for (BankAccount account : accounts){
            // If they made a valid selection
            if (accountSelection.equalsIgnoreCase(account.getAccountName())){
                accountFound = true;
                // Ask for account password
                System.out.println("Please enter the password for " + account.getAccountName());
                String passwordAttempt = scan.nextLine();
                // If the password matches
                if (passwordAttempt.equals(account.getPassword())){
                    // Change the current account
                    myBankAccount = account;
                    // Change the stuff the to what the new account owns
                    myStuff = myBankAccount.getOwnedObjects();
                    // This is to prevent owned objects being added twice
                    presentShoppingMenu();
                    emptyShoppingCart();
                    break;

                }
                else {
                    // Take user back to logout screen
                    System.out.println("Incorrect password");
                    logout();
                }
            }
        }
        if (!accountFound){
            System.out.println("No account found with name of: " + accountSelection);
            logout();
        }
    }

    // Method that will create a new account when option is selected in logout menu
    private void createNewAccount(){
        setupBankAccount();
        duplicateAccountNameChecker();
        presentShoppingMenu();
    }

    // Add objects that the user bought to their individual account
    private void addObjectsToAccount(){
        for (Buyable item : myStuff){
            // If the account has the item don't add as it would duplicate it
            if (!myBankAccount.getOwnedObjects().contains(item)){
                myBankAccount.getOwnedObjects().add(item);
            }
        }
    }

    // Log the user out of their account
    private void logout(){
        // They are no longer shopping
        stillShopping = false;
        // Add owned objects to the users account, once to avoid duplicating their objects;
        if (!addedOwnedObjects){
            addObjectsToAccount();
            addedOwnedObjects = true;
        }
        // Reset myStuff for the next account
        myStuff = new ArrayList<>();
        // Prompt the user
        System.out.println("Would you like to:");
        System.out.println("1. Log into existing account");
        System.out.println("2. Create new account");
        System.out.println("3. Exit Program (In case you forgot your password for all your accounts)");
        try{
            int selection = scan.nextInt();
            scan.nextLine();
            switch (selection) {
                case 1 -> logIntoExistingAccount();
                case 2 -> createNewAccount();
                case 3 -> System.exit(0);
                default -> {
                    System.out.println("Invalid selection");
                    logout();
                }
            }
        }
        catch (Exception e){
            System.out.println("Your input caused an exception, please give valid inputs: " + e);
            scan.nextLine();
            logout();
        }

    }

    // Methods to setup attributes (you made these)
    private void setupAccounts()
    {
        setupBankAccount();
        myStuff = new ArrayList<>();
        myShoppingCart = new ArrayList<>();
    }

    private void setupStore()
    {
        storeInventory = new StoreInventory();
    }

    // Duplicate account name checker, this is to avoid two accounts having the same name
    private void duplicateAccountNameChecker(){
        // Create another array list which is the same as accounts
        ArrayList<BankAccount> accountsDuplicate = new ArrayList<>(accounts);
        // For every account in accounts
        for (BankAccount account : accounts){
            // For every account in the duplicate accounts
            for (BankAccount duplicateAccount : accountsDuplicate){
                // If the name is equal to the duplicate account name
                if (account.getAccountName().equals(duplicateAccount.getAccountName())
                        // And the indexes are different (so that the account doesn't check its duplicate version)
                        && accounts.indexOf(account) != accountsDuplicate.indexOf(duplicateAccount)){
                    // The two accounts must have the same name, change the name of the account
                    System.out.println("Duplicate account detected, one of the account names has been altered");
                    account.setAccountName(account.getAccountName() + "(" + accounts.indexOf(account) + ")");

                }
            }
        }
    }

    // This was originally your method, but I have altered it so that admin accounts can be made
    private void setupBankAccount()
    {
        adminChecker();
        // A person can only create an admin or a bank account
        AdminAccount adminAccount;
        BankAccount regularAccount;
        // Prompt
        System.out.println("To begin, please set up a bank account.");
        System.out.println("How much money should your account contain?");
        try{
            int depositAmount = scan.nextInt();
            // Accounts will have a name, so they can be identified and logged into
            System.out.println("What should the name of your account be?");
            String accountName = scan.next();
            // If the user selected/not selected admin in the admin checker
            if (!makeAdmin){
                // Make a regular account
                regularAccount = new BankAccount(depositAmount, false, accountName);
                // Add it to the list of accounts
                accounts.add(regularAccount);
                // Make it the current account
                myBankAccount = regularAccount;
            }
            else {
                // Make an admin account
                adminAccount = new AdminAccount(depositAmount, accountName);
                // Add it to the list of accounts
                accounts.add(adminAccount);
                // Make it the current account
                myBankAccount = adminAccount;
                makeAdmin = false;
            }
            scan.nextLine();

        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSED AN EXCEPTION: " + e);
            scan.nextLine();
            setupBankAccount();
        }

    }

    // I added more menu options, including the hidden admin tools and returning items to the store
    private void presentShoppingMenu()
    {
        stillShopping = true;
        while(stillShopping)
        {
            System.out.println("\n****************************************************** ");
            System.out.println("Please choose from one of the following menu options: ");
            System.out.println("Current account: " + myBankAccount.getAccountName());
            System.out.println("1. View catalog of items to buy");
            System.out.println("2. Buy an item");
            System.out.println("3. View your cart of held items");
            System.out.println("4. Review the items you already own");
            System.out.println("5. View the status of your financials");
            System.out.println("6. Logout");
            System.out.println("7. Exit program");
            System.out.println("8. Return item to store");
            if (myBankAccount.isAdmin()){
                System.out.println("9. Admin Tools");
            }
            try{
                int input = scan.nextInt();
                scan.nextLine(); // buffer clear

                switch(input){
                    case 1:
                        viewCatalog();
                        break;
                    case 2:
                        buyItem();
                        break;
                    case 3:
                        reviewMyShoppingCart();
                        break;
                    case 4:
                        reviewMyInventory();
                        break;
                    case 5:
                        reviewFinancials();
                        break;
                    case 6:
                        logout();
                        break;
                    case 7:
                        System.out.println("Thanks for shopping! Now exiting program ... ");
                        System.exit(0);
                        break;
                    case 8:
                        returnItemToStore();
                        break;
                    case 9:
                        if (myBankAccount.isAdmin()){
                            adminToolsMenu();
                        }
                        break;
                    default:
                        System.out.println("Incorrect input. Choose again!");
                        break;
                }
            }
            catch (Exception e){
                System.out.println("YOUR INPUT CAUSED AN EXCEPTION: " + e);
                scan.nextLine();
                presentShoppingMenu();
            }

            
        }
    }

    // Return an item to the store
    private void returnItemToStore(){
        System.out.println("Please choose an item to return, you can only return your the last three items you purchased: ");
        System.out.println("(If no items show up then you do not have any items to return!)");
        // Holder items
        Buyable item1 = null;
        Buyable item2 = null;
        Buyable item3 = null;
        for (Buyable item : myStuff){
            // Get the last three items purchased (the highest index in the arraylist)
            if (myStuff.indexOf(item) == myStuff.size() - 1){
                System.out.println("1. " + item.getItemName());
                item1 = item;
            }
            if (myStuff.indexOf(item) == myStuff.size() - 2){
                System.out.println("2. " + item.getItemName());
                item2 = item;
            }
            if (myStuff.indexOf(item) == myStuff.size() - 3){
                System.out.println("3. " + item.getItemName());
                item3 = item;
            }
        }
        System.out.println("4. Exit menu");
        try{
            int selection = scan.nextInt();
            scan.nextLine();
            switch (selection){
                case 1:
                    returnItem(item1);
                    break;
                case 2:
                    returnItem(item2);
                    break;
                case 3:
                    returnItem(item3);
                    break;
                case 4:
                    break;
                default:
                    System.out.println(" You gave an invalid input, please try again");
                    returnItemToStore();
                    break;
            }
        }
        catch (Exception e){
            scan.nextLine();
            System.out.println("Your input caused an exception, please give valid inputs: " + e);
            returnItemToStore();
        }



    }

    // Return the chosen item to the store (continuation of method above)
    private void returnItem(Buyable item){
        if (item != null){
            // Make is custom false to prevent the item being put in multiple categories
            // Returned items should be in the returned category
            item.setIsReturn(true);
            item.setIsCustom(false);
            // Tell the user their balance before the return
            System.out.println("Balance before return: " + myBankAccount.getBalance());
            // Add the price of the item back to their account balance
            myBankAccount.setBalance((float) (myBankAccount.getBalance() + item.getPrice()));
            // The store takes the item back to its returns inventory
            storeInventory.getReturnedInventory().add(item);
            // The user no longer owns the item, remove it from their stuff
            myStuff.remove(item);
            myBankAccount.getOwnedObjects().remove(item);
            System.out.println("Item successfully returned your account now has the balance: " + myBankAccount.getBalance());
        }
        else{
            System.out.println("Return could not be completed as no item was provided, returning to main menu");
            System.out.println("(You may have put an incorrect input by accident)");
        }
    }
    // Modified original viewCatalog() method to solve tier 2 problem, see how the method is broken further below
    private void viewCatalog(){
        System.out.println("Which category of items would you like to look at?");
        System.out.println("1. Clothing");
        System.out.println("2. Food");
        System.out.println("3. Furniture ");
        System.out.println("4. Games");
        System.out.println("5. Custom");
        System.out.println("6. Returned/Refurbished Items");
        System.out.println("7. Exit view catalog");


        try {
            int selection = scan.nextInt();
            switch (selection){
                case 1:
                    viewClothingCatalog();
                    break;
                case 2:
                    viewFoodCatalog();
                    break;
                case 3:
                    viewFurnitureCatalog();
                    break;
                case 4:
                    viewGamesCatalog();
                    break;
                case 5:
                    viewCustomCatalog();
                    break;
                case 6:
                    viewReturnedItems();
                case 7:
                    break;
                default:
                    System.out.println("Invalid selection");
                    viewCatalog();
            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSED AN EXCEPTION, please give valid inputs: " + e);
            scan.nextLine();
        }

    }

    private void viewClothingCatalog(){
        for(BuyableClothing item : storeInventory.getClothesInventory()){
            System.out.println("" + item.getItemName());
        }
        seeItemInformationPrompt();
    }

    private void viewFoodCatalog(){
        for(BuyableFood item : storeInventory.getFoodInventory()){
            System.out.println("" + item.getItemName());
        }
        seeItemInformationPrompt();
    }

    private void viewFurnitureCatalog(){
        for(BuyableFurniture item: storeInventory.getFurnitureInventory()){
            System.out.println("" + item.getItemName());
        }
        seeItemInformationPrompt();
    }

    private void viewGamesCatalog(){
        for(BuyableGame item : storeInventory.getGamesInventory()){
            System.out.println("" + item.getItemName());
        }
        seeItemInformationPrompt();
    }

    private void viewCustomCatalog(){
        for (Buyable item : storeInventory.getCustomsInventory()){
            System.out.println("" + item.getItemName());
        }
        seeItemInformationPrompt();
    }

    private void viewReturnedItems(){
        for (Buyable item : storeInventory.getReturnedInventory()){
            System.out.println("" + item.getItemName());
        }
    }

    // After viewing a catalog of items, a user will be prompt on whether or not they'd like to see the details of that item
    private void seeItemInformationPrompt(){
        System.out.println("Would you like to see the details of an item?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        try{
            int selection = scan.nextInt();
            scan.nextLine();
            switch (selection){
                case 1:
                    findChosenItem();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid selection");
                    seeItemInformationPrompt();

            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSED AN EXCEPTION: " + e);
            scan.nextLine();
            seeItemInformationPrompt();
        }
    }

    // Choose an item, find it and show its specific details (tier 3)
    private void findChosenItem(){
        boolean itemFound = false;
        System.out.println("Input the name of the item");
        String input = scan.nextLine();
        // Search for the item
        for (Buyable item : storeInventory.getFullInventoryList()){
            // If the item could be found and a version of it hasn't been found yet
            if (item.getItemName().equals(input) && !itemFound){
                // Show the details of the chosen item
                item.showDetails();
                // Makes sure it finds only the chosen item
                itemFound = true;
            }
        }
        // Else tell the user that their item couldn't be found
        if (!itemFound){
            System.out.println("Could not find item, details can not be gathered");
        }
    }

    // Your buy item method
    private void buyItem()
    {
        System.out.println("Please type in the name of the item you wish to buy!");
        
        // Get user input
        String itemName = scan.nextLine();
        
        // Holding variable for the desired item, if found
        Buyable itemToBuy = null;
        
        // Look through the full inventory to see if the item is present
        // Convert both item name and user input to lower case to prevent case issues!
        for(Buyable item: storeInventory.getFullInventoryList()) 
        {
            if(item.getItemName().equalsIgnoreCase(itemName))
            {
                itemToBuy = item;
                break;
            }
        }
        
        // If a suitable item was found, give them the option to buy it!
        if(itemToBuy != null)
        {
            System.out.println("We have " + itemToBuy.getItemName() + " in stock!");
            System.out.println("Type 1 to BUY NOW or 2 to PLACE IN YOUR SHOPPING CART.");
            
            int input = scan.nextInt();
            scan.nextLine(); // buffer clear
            
            if(input == 1)
            {
                makePurchaseFromStore(itemToBuy);
            }
            else if(input == 2)
            {
                System.out.println("We'll hold onto this item for you.");
                moveItemToShoppingCart(itemToBuy);
            }
            else
            {
                System.out.println("Incorrect input. Purchase cancelled.");
            }
            
        }
        else // No suitable item found
        {
            System.out.println("The item you are looking for is sold out or does not exist, sorry!");
        }
        
    }

    // Your review inventory method
    private void reviewMyInventory()
    {
        System.out.println("Here is a list of the items you now own: ");
        for (Buyable buyable : myStuff) {
            System.out.println("" + buyable.getItemName());
        }
    }

    // Your method for reviewing financials of an account
    private void reviewFinancials()
    {
        myBankAccount.balanceReport();
    }

    // SHOPPING CART METHODS
    // Modified original method, did not have the option to remove item from cart
    private void reviewMyShoppingCart()
    {
        if(!myShoppingCart.isEmpty())
        {
             System.out.println("Here are all of the items being held in your shopping cart: ");
             for(Buyable item: myShoppingCart)
             {
                 System.out.println("" + item.getItemName());
             }

            System.out.println("What would you like to do?");
            System.out.println("1. Buy item from cart");
            System.out.println("2. Remove item from cart");
            System.out.println("3. Exit cart menu");

            try{
                int selection = scan.nextInt();
                scan.nextLine();
                switch (selection){
                    case 1:
                        buyItemInShoppingCart();
                        break;
                    case 2:
                        removeItemFromShoppingCart();
                        break;
                    case 3:
                        System.out.println("Leaving shopping cart as is and returning to the storefront ... ");
                        break;
                }
            }
            catch (Exception e){
                scan.nextLine();
                System.out.println("Your input caused an exception, please give valid inputs: " + e);
            }

        }
        else
        {
            System.out.println("Your shopping cart is empty! Nothing to see here ... ");
        }
        
    }

    // Buy item in shopping cart (mostly unchanged)
    private void buyItemInShoppingCart()
    {
        System.out.println("Type in the name of the item you want to buy from the shopping cart: ");
        String userChoice = scan.nextLine();
        
        for(Buyable itemInCart: myShoppingCart)
        {
            if(itemInCart.getItemName().equalsIgnoreCase(userChoice))
            {
                makePurchaseFromShoppingCart(itemInCart);
                break;
            }
            else
            {
                System.out.println("Item could not be found in shopping cart.");
            }
        }
        
    }

    // This method was unused in the original code, I managed to implement for its intention
    private void removeItemFromShoppingCart()
    {
        System.out.println("Which item would you like to remove from your shopping cart?");
        
        String userChoice = scan.nextLine();
        
        for(Buyable cartItem: myShoppingCart)
        {
            if(cartItem.getItemName().equalsIgnoreCase(userChoice))
            {
                System.out.println("You have removed " + cartItem.getItemName() + " from your shopping cart.");
                moveItemFromShoppingCartToInventory(cartItem);
                break;
            }
            else
            {
                System.out.println("Item could not be found in your shopping cart.");
            }
        }
    }
    
    // Move item from inventory to shopping cart
    private void moveItemToShoppingCart(Buyable item)
    {
        myShoppingCart.add(item);
        storeInventory.removeItemFromInventory(item);
    }
    
    private void moveItemFromShoppingCartToInventory(Buyable item)
    {
        storeInventory.restockItemToInventory(item);
        myShoppingCart.remove(item);
    }
    
    // Fixed this method, original method did not work as intended, would purchase the item even if password was incorrect
    private void makePurchaseFromStore(Buyable item)
    {
        // If you can afford the item, buy it and remove it from the store
        if(myBankAccount.canAfford(item.getPrice()))
        {
            if (myBankAccount.makePurchase(item.getPrice())){
                System.out.println("Purchase complete! You now own " + item.getItemName());
                myStuff.add(item);
                // Add the item to the sold items database and remove it from the inventory
                // But if it was a return then don't add it since it already exists in the database
                if (!item.isReturn()){
                    storeInventory.getSoldItems().add(item);
                }
                storeInventory.removeItemFromInventory(item);
            }
        }
        else
        {
            System.out.println("You can't afford that item ... ");
        }
    }
    
    private void makePurchaseFromShoppingCart(Buyable item)
    {
        // If you can afford the item, buy it and remove it from the store
        if(myBankAccount.canAfford(item.getPrice()))
        {
            myBankAccount.makePurchase(item.getPrice());
            System.out.println("Purchase complete! You now own " + item.getItemName());
            myStuff.add(item);
            myShoppingCart.remove(item);
        }
        else
        {
            System.out.println("You can't afford that item ... ");
        }        
    }

    // Empty the entire shopping cart
    private void emptyShoppingCart(){
        // Put all items in the shopping cart back into the store's inventory
        for (Buyable cartItem : myShoppingCart){
            moveItemFromShoppingCartToInventory(cartItem);

        }
    }

    public BankAccount getMyBankAccount() {
        return myBankAccount;
    }
}
