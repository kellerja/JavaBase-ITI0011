/**
 * Created by Jaanus on 6.03.16.
 */
public class EX08 {
    /**
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        SoldierOfFortune soldier1 = new SoldierOfFortune();
        /*SoldierOfFortune soldier1 = new SoldierOfFortune("Gerald Albert", "G.A.", "Garacus");
        Ise kirjutatud konstruktoriga*/
        soldier1.setFirstName("Gerald Albert");
        soldier1.setLastName("Garacus");
        soldier1.setCodeName("G.A.");

        SoldierOfFortune soldier2 = new SoldierOfFortune();
        soldier2.setFirstName("Templeton");
        soldier2.setLastName("Peck");
        soldier2.setCodeName("Bottom");

        SoldierOfFortune soldier3 = new SoldierOfFortune();
        soldier3.setFirstName("John");
        soldier3.setLastName("Bro Hard");
        soldier3.setCodeName("McClean");

        SoldierOfFortune soldier4 = new SoldierOfFortune();
        soldier4.setFirstName("John");
        soldier4.setLastName("Rambro");
        soldier4.setCodeName("Your Worst Nightmare");
        SoldierOfFortune soldier5 = new SoldierOfFortune();
        soldier5.setFirstName("Arnie");
        soldier5.setLastName("Blackman");
        soldier5.setCodeName("The Initiator");

        Team bTeam = new Team();
        bTeam.setCodeName("B-Team");
        Team cTeam = new Team();
        cTeam.setCodeName("C-Team");

        bTeam.addSoldierToTeam(soldier1);
        bTeam.addSoldierToTeam(soldier2);
        bTeam.addSoldierToTeam(soldier3);
        cTeam.addSoldierToTeam(soldier3); // Soldier 3 on kahes tiimis
        cTeam.addSoldierToTeam(soldier4);
        cTeam.addSoldierToTeam(soldier5);

        System.out.println(soldier1); // -> Gerald Albert "G.A." Garacus
        System.out.println(bTeam); // -> B-Team: G.A, Bottom, Bro Hard
        System.out.println(bTeam.getNumberOfSoldiers()); // -> 3
        System.out.println(cTeam.getNumberOfSoldiers()); // -> 3

        //Lisaosa näide
        Mission mission1 = new Mission();
        mission1.setCodeName("Delta");
        mission1.setRequiredMissionsCompleted(0);
        bTeam.sendToMission(mission1);
        System.out.println(bTeam.getCompletedMissions().size()); // -> 1
        System.out.println(bTeam.getMembers().get(0));
    }
}
