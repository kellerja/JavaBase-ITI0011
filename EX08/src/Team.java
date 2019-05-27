import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jaanus on 6.03.16.
 */
public class Team {
    /***/
    private String codeName = null;
    /***/
    private List<SoldierOfFortune> members = new ArrayList<>();
    /***/
    private List<Mission> completedMissions = new ArrayList<>();
    /**
     * @param soldier - soldier to add to team.*/
    public void addSoldierToTeam(SoldierOfFortune soldier) {
        if (soldier != null && !members.contains(soldier)) {
            members.add(soldier);
        }
    }
    /**
     * @return average missions completed.
     */
    public double averageMissionsCompleted() {
        if (members == null) {
            return Double.NaN;
        } else if (this.getNumberOfSoldiers() == 0) {
            return 0.0;
        }
        double missionCompletion = 0;
        for (SoldierOfFortune member: members) {
            if (member != null) {
                missionCompletion += member.getNumberOfMissionsCompleted();
            }
        }
        double averageCompetionRate = missionCompletion / this.getNumberOfSoldiers();
        return averageCompetionRate;
    }
    /**
     * @return - name and members of the team.
     */
    public String toString() {
        String name = "";
        if (codeName != null) {
            name += codeName + ": ";
        }
        if (members == null) {
            return null;
        }
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) != null) {
                name += members.get(i).getCodeName();
                if (i != members.size() - 1) {
                    name += ", ";
                }
            }
        }
        if (name.length() > 0) {
            return name;
        }
        return null;
    }
    /**
     * @param mission - send team to mission.
     * @return true if mission is successful, false if not.
     */
    public boolean sendToMission(Mission mission) {
        if (mission != null && members != null) {
            if (!mission.isCompleted() && mission.receiveTeam(this)) {
                mission.setCompleted(true);
                completedMissions.add(mission);
                for (SoldierOfFortune member: members) {
                    if (member != null) {
                        member.incrementMissionsCompleted();
                    }
                }
                return true;
            }
        }
        return false;
    }
    /**
     * @return number of members in team.
     */
    public int getNumberOfSoldiers() {
        Set uniqueMembers = new HashSet<SoldierOfFortune>();
        if (members != null) {
            for (SoldierOfFortune member: members) {
                if (member != null) {
                    uniqueMembers.add(member);
                }
            }
            return uniqueMembers.size();
        } else {
            return -1;
        }
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
     * @return members.
     */
    public List<SoldierOfFortune> getMembers() {
        return members;
    }
    /**
     * @param members - set members.
     */
    public void setMembers(List<SoldierOfFortune> members) {
        this.members = members;
    }
    /**
     * @return completed missions.
     */
    public List<Mission> getCompletedMissions() {
        return completedMissions;
    }
    /**
     * @param completedMissions - set missions.
     */
    public void setCompletedMissions(List<Mission> completedMissions) {
        this.completedMissions = completedMissions;
    }
}
