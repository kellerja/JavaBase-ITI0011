/**
 * Created by Jaanus on 6.03.16.
 */
public class SoldierOfFortune {
    /***/
    private String lastName;
    /***/
    private String firstName;
    /***/
    private String codeName;
    /***/
    private int numberOfMissionsCompleted = 0;
    /**
     * @param codeName - sets code name.
     * @param firstName - sets first name.
     * @param lastName - sets last name.
     */
    public SoldierOfFortune(String firstName, String codeName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.codeName = codeName;
    }
    /***/
    public SoldierOfFortune() {
    }
    /**
     * @return full name of the soldier with codename.
     */
    public String toString() {
        String printFirstName = "";
        String printCodeName = "";
        String printLastName = "";
        if (firstName != null) {
            printFirstName = firstName + " ";
        }
        if (codeName != null) {
            printCodeName = "\"" + codeName + "\"";
            if (lastName != null) {
                printCodeName += " ";
            }
        }
        if (lastName != null) {
            printLastName = lastName;
        }
        return printFirstName + printCodeName + printLastName;
    }
    /**
     * @return name of the soldier.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName - set soldier last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return soldier first name.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName - set first name of soldier.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return codename of the soldier.
     */
    public String getCodeName() {
        return codeName;
    }
    /**
     * @param codeName - set soldier codename.
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    /**
     * @return soldier completion rate.
     */
    public int getNumberOfMissionsCompleted() {
        return numberOfMissionsCompleted;
    }
    /**
     * @param numberOfMissionsCompleted - set soldier completion rate.
     */
    public void setNumberOfMissionsCompleted(int numberOfMissionsCompleted) {
        this.numberOfMissionsCompleted = numberOfMissionsCompleted;
    }
    /***/
    public void incrementMissionsCompleted() {
        numberOfMissionsCompleted += 1;
    }
}
