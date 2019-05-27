import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Created by Jaanus K on 15.03.16.
 */
public class OrderedPackageProvider implements PackageProvider {
    /**
     * list of packages.
     */
    private NavigableSet<Package> packages = new TreeSet<>();
    /***/
    private PackageFilter packageFilter;
    /***/
    private PackageProvider trash;

    @Override
    public Package getNextPackage() {
        Package nextPackage;
        if (!(hasNextPackage())) {
            return null;
        }
        nextPackage = packages.pollFirst();
        //packages.remove(nextPackage);
        return nextPackage;
    }

    @Override
    public void addPackage(Package packageToAdd) {
        if (packageToAdd != null && !packages.contains(packageToAdd)
                && (packageFilter == null || packageFilter.isValid(packageToAdd))) {
            packages.add(packageToAdd);
            //Collections.sort(packages);
        } else if (trash != null) {
            trash.addPackage(packageToAdd);
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
        this.packageFilter = packageFilter;
    }

    @Override
    public PackageFilter getPackageFilter() {
        return packageFilter;
    }

    @Override
    public void setBrokenPackageProvider(PackageProvider packageProvider) {
        if (packageProvider != this && !(packageProvider instanceof OrderedPackageProvider)) {
            this.trash = packageProvider;
        }
    }

    @Override
    public List<Package> getPackages() {
        List<Package> newPackages = new ArrayList<>();
        for (Package pack: packages) {
            newPackages.add(pack);
        }
        return newPackages;
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

    @Override
    public PackageProvider getBrokenPackageProvider() {
        return trash;
    }
}
