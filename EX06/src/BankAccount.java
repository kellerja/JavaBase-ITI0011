/**
 * Created by Jaanus.
 */
public class BankAccount {
    /**money on bank account.*/
    private double balance;
    /**interest for sending money.*/
    private static final double INTEREST = 0.01;

    /**
     * @return money on bank account.
     */
    public final double getBalance() {
        return balance;
    }
    /**
     * @param amount - money to remove from account.
     * @return money removed from account or NaN if amount is bigger than balance.
     */
    public final double withdrawMoney(double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            return balance;
        }
        return Double.NaN;
    }
    /**
     * @param amount  - money to add to account.
     */
    public final void addMoney(double amount) {
        balance += amount;
    }
    /**
     * @param targetAccount - BankAccount object.
     * @param amount - money to sent to target object.
     * @return true if transfer goes through false if not.
     */
    public final boolean transferMoneyTo(BankAccount targetAccount, double amount) {
        if (targetAccount != null) {
            double money = withdrawMoney(amount + amount * INTEREST);
            if (!Double.isNaN(money)) {
                targetAccount.addMoney(amount);
                return true;
            }
        }
        return false;
    }
}
