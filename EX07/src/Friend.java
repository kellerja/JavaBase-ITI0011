import java.util.Arrays;
import java.util.List;

/**
 * Created by Jaanus on 1.03.16.
 */
public class Friend implements Comparable {
    /**Friend's first name.*/
    private String firstName = null;
    /**Friend's last name.*/
    private String lastName = null;
    /**
     * @param name - first name
     */
    public final void setFirstName(String name) {
        if (name != null) {
            firstName = name;
        }
    }
    /**
     * @param name - last name
     */
    public final void setLastName(String name) {
        if (name != null) {
            lastName = name;
        }
    }
    /**
     * @return last name of the friend stored in this object.
     */
    public final String getLastName() {
        return lastName;
    }
    /**
     * @return full name of the friend stored in this object.
     */
    public final String getFullName() {
        if (firstName == null || lastName == null) {
            return null;
        }
        return firstName + " " + lastName;
    }
    /**
     * @return list where the elements are first name names
     */
    public final List<String> getNames() {
        if (firstName == null) {
            return null;
        }
        return Arrays.asList(firstName.split(" "));
    }

    @Override
    public final int compareTo(Object o) {
        Friend comp = (Friend) o;
        return this.firstName.toLowerCase().compareTo(comp.firstName.toLowerCase());
    }
}
