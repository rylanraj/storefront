public class AdminAccount extends BankAccount{

    // Admin accounts are simply bank accounts that have the option to access the store as admin
    public AdminAccount(float initialDeposit, String accountName){
        super(initialDeposit, true, accountName);
    }
}
