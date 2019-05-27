/**
 * Created by Jaanus on 6.03.16.
 */
public class Mission {
    /***/
    private String codeName;
    /***/
    private boolean isCompleted = false;
    /***/
    private int requiredMissionsCompleted;
    /**
     * @param codeName - set codename in constructor.
     */
    public Mission(String codeName) {
        this.codeName = codeName;
    }
    /***/
    public Mission() {
    }
    /**
     * @param team - team to check.
     * @return whether the team could complete the mission.
     */
    public boolean receiveTeam(Team team) {
        if (team != null && team.getNumberOfSoldiers() != 0) {
            if (team.averageMissionsCompleted() >= (double) requiredMissionsCompleted) {
                return true;
            }
        }
        return false;
    }
    /**
     * @return mission name.
     */
    public String toString() {
        if (codeName != null) {
            return "Operation " + codeName;
        }
        return null;
    }
    /**
     * @return mission codename.
     */
    public String getCodeName() {
        return codeName;
    }
    /**
     * @param codeName - set name for mission.
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    /**
     * @return requiredMissionsCompleted
     */
    public int getRequiredMissionsCompleted() {
        return requiredMissionsCompleted;
    }
    /**
     * @param requiredMissionsCompleted - int
     */
    public void setRequiredMissionsCompleted(int requiredMissionsCompleted) {
        this.requiredMissionsCompleted = requiredMissionsCompleted;
    }
    /**
     * @return whether mission is completed.
     */
    public boolean isCompleted() {
        return isCompleted;
    }
    /**
     * @param completed - finish mission.
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
