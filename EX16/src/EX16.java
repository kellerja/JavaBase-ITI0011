/**
 * Created by Jaanus on 20.04.16.
 */
public class EX16 {
    /***/
    private static final int BASE = 10;
    /***/
    private static final int COND1 = 9;
    /***/
    private static final int COND2 = 8;

    /**
     * @param n - int where count takes place.
     * @return count of 8 and 9.
     */
    public static int count98(int n) {
        if (n == 0) return 0;
        if (n % BASE == COND1 || n % BASE == COND2) {
            return 1 + count98(n / BASE);
        } else {
            return count98(n / BASE);
        }
    }

    /**
     * @param n - int where count takes place.
     * @return count of 8, 9 and sums that result 8 and 9.
     */
    public static int count98Harder(int n) {
        if (n == 0) return 0;
        if (n % BASE + n / BASE % BASE == COND1
                || n % BASE + n / BASE % BASE == COND2) return 1 + count98Harder(n / BASE);
        if (n % BASE == COND1 || n % BASE == COND2) {
            return 1 + count98Harder(n / BASE);
        } else {
            return count98Harder(n / BASE);
        }
    }

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        /*
        System.out.println(count98(90818)); // => 3
        System.out.println(count98Harder(90818)); // => 5
        //TEST YOUR IMPLEMENTATION HERE
        System.out.println(count98(9919));
        System.out.println(count98Harder(9919));
        */
    }
}
