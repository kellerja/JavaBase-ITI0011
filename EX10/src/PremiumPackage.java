/**
 * Created by Jaanus on 15.03.16.
 */
public class PremiumPackage extends Package implements Comparable {
    /***/
    private int priority;
    /***/
    public PremiumPackage() {
    }
    /**
     * PremiumPackage constructor.
     *
     * @param packageNumber Package number printed on package
     * @param width         Package width in cm
     * @param height        Package height in cm
     */
    public PremiumPackage(String packageNumber, int width, int height) {
        super(packageNumber, width, height);
    }
    /**
     * PremiumPackage constructor.
     *
     * @param packageNumber Package number printed on package
     * @param width         Package width in cm
     * @param height        Package height in cm
     * @param priority      Package priority
     */
    public PremiumPackage(int priority, String packageNumber, int width, int height) {
        super(packageNumber, width, height);
        this.priority = priority;
    }
    /**
     * @return premium package priority.
     */
    public int getPriority() {
        return priority;
    }
    /**
     * @param priority - set package priority.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    /*
    @Override
    public int compareTo(Object o) {
        PremiumPackage target = (PremiumPackage) o;
        if (priority == target.priority) {
            int myPriority = super.sender.getPriority() + super.receiver.getPriority();
            int targetPriority = target.sender.getPriority() + target.receiver.getPriority();
            if (myPriority == targetPriority) {
                return 0;
            } else if (myPriority > targetPriority) {
                return 1;
            } else {
                return -1;
            }
        } else if (priority > target.priority) {
            return 1;
        } else {
            return -1;
        }
    }
    */
}
