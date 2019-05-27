/**
 * Created by Jaanus on 18.03.16.
 */
public class OrderedPackageFilter implements PackageFilter {
    /***/
    private static final int MAX_PRIORITY = 1000;
    /**
     * @param p - package for test.
     * @return true if package p has the same data for sender and receiver.
     */
    private boolean hasSameData(Package p) {
        if (p.getSender().getName().equals(p.getReceiver().getName())
                && p.getSender().getAddress().equals(p.getReceiver().getAddress())
                && p.getSender().getPriority() == p.getReceiver().getPriority()) {
            return true;
        }
        return false;
    }
    @Override
    public boolean isValid(Package p) {
        if (p == null || p.getSender() == null || p.getReceiver() == null
                || p.getSender().getName() == null || p.getReceiver().getName() == null
                || p.getSender().getAddress() == null || p.getReceiver().getAddress() == null
                || p.getPackageNumber() == null || p.getPackageNumber().replaceAll("[ ]+", "").equals("")
                || p.getSender().getPriority() <= 0 || p.getReceiver().getPriority() <= 0
                || p.getSender().getPriority() >= MAX_PRIORITY
                || p.getReceiver().getPriority() >= MAX_PRIORITY
                || p.getSender().getName().replaceAll("[ ]+", "").equals("")
                || p.getReceiver().getName().replaceAll("[ ]+", "").equals("")
                || p.getSender().getAddress().replaceAll("[ ]+", "").equals("")
                || p.getReceiver().getAddress().replaceAll("[ ]+", "").equals("")
                || p.getHeight() <= 0 || p.getWidth() <= 0
                || p.getHeight() >= MAX_PRIORITY || p.getWidth() >= MAX_PRIORITY
                || p.getSender() == p.getReceiver() || hasSameData(p)) {
            return false;
        }
        if (p instanceof PremiumPackage) {
            PremiumPackage pp = (PremiumPackage) p;
            if (pp.getPriority() <= 0 || pp.getPriority() >= MAX_PRIORITY) {
                return false;
            }
        }
        return true;
    }
}
