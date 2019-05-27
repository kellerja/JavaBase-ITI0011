import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Jaanus on 15.03.16.
 */
public class PremiumPackageProvider implements PackageProvider {
    /**
     * list of packages.
     */
    private List<Package> packages = new ArrayList<>();
    /***/
    private SortedSet<PremiumPackage> premiumPackages = new TreeSet<>();
    /***/
    private PackageFilter packageFilter;

    @Override
    public Package getNextPackage() {
        Package nextPackage;
        if (hasNextPackage()) {
            return null;
        }
        if (premiumPackages != null && !premiumPackages.isEmpty()) {
            nextPackage = premiumPackages.first();
            premiumPackages.remove(nextPackage);
            packages.remove(nextPackage);
            return nextPackage;
        } else {
            nextPackage = packages.get(0);
            packages.remove(0);
            return nextPackage;
        }
    }

    @Override
    public void addPackage(Package packageToAdd) {
        if (packageToAdd != null) {
            if (packageToAdd instanceof PremiumPackage) {
                packages.add(packageToAdd);
                premiumPackages.add((PremiumPackage) packageToAdd);
            }
        }
    }

    @Override
    public boolean hasNextPackage() {
        if (packages != null && !packages.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void setPackageFilter(PackageFilter packageFilter) {
        if (packageFilter != null) {
            this.packageFilter = packageFilter;
        }
    }

    @Override
    public PackageFilter getPackageFilter() {
        return packageFilter;
    }

    @Override
    public void setBrokenPackageProvider(PackageProvider packageProvider) {
    }

    @Override
    public PackageProvider getBrokenPackageProvider() {
        return null;
    }

    @Override
    public List<Package> getPackages() {
        return packages;
    }

    @Override
    public List<Package> findAllPackagesBySender(Customer customer) {
        List<Package> packagesBySender = new ArrayList<>();
        if (hasNextPackage() && customer != null) {
            for (Package pack: packages) {
                if (pack == null) {
                    continue;
                }
                if (pack.sender.equals(customer)) {
                    packagesBySender.add(pack);
                }
            }
        }
        return packagesBySender;
    }

    @Override
    public List<Package> findAllPackagesByReceiver(Customer customer) {
        List<Package> packagesByReceiver = new ArrayList<>();
        if (hasNextPackage() && customer != null) {
            for (Package pack: packages) {
                if (pack == null) {
                    continue;
                }
                if (pack.receiver.equals(customer)) {
                    packagesByReceiver.add(pack);
                }
            }
        }
        return packagesByReceiver;
    }
}
