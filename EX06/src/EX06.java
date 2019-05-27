/**
 * Created by Jaanus on 27.02.16.
 */
public class EX06 {
    /**To get rid of magic numbers.*/
    private static final double[] TEST_VALUES = {101.0, 200.0, 100.0, 67.3};
    /**
     * @param args - arguments from command line.
     */
    public static void main(String[] args) {

        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();

        first.addMoney(TEST_VALUES[0]);
        second.addMoney(TEST_VALUES[1]);

        first.transferMoneyTo(second, TEST_VALUES[2]);
        System.out.println(second.getBalance()); // 300.0
        System.out.println(first.getBalance()); // 0.0
        second.withdrawMoney(TEST_VALUES[2 + 1]);
        System.out.println(second.getBalance()); // 232.7
    }
}
