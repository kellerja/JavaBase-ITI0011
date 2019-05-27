import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jaanus on 1.03.16.
 */
public class EX07 {
    /**List of Friend objects.*/
    private static List<Friend> friends;
    /**
     * @param fileName - file to read for friend names.
     * @return List object containing Friend objects.
     */
    public static List<Friend> readFriendsFromFile(String fileName) {
        if (fileName == null) {
            return null;
        }
        List<Friend> list = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("\uFEFF", "");
                String[] lineArray = line.split("[ ]+");
                if (lineArray.length < 2) {
                    continue;
                }
                String firstName = "";
                String lastName = lineArray[lineArray.length - 1];
                for (int i = 0; i < lineArray.length - 1; i++) {
                    firstName += lineArray[i];
                    if (i != lineArray.length - 2) {
                        firstName += " ";
                    }
                }
                Friend friend = new Friend();
                friend.setFirstName(firstName);
                friend.setLastName(lastName);
                list.add(friend);
            }
            reader.close();
            friends = list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * @param lastName - search friend with that last name.
     * @return full name of the friend, who was searched.
     */
    public static Friend findFriendByLastName(String lastName) {
        if (lastName == null) {
            return null;
        }
        Collections.sort(friends);
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getLastName().equals(lastName)) {
                return friends.get(i);
            }
        }
        return null;
    }
    /**
     * @param args - arguments from commandline
     */
    public static void main(String[] args) {
        readFriendsFromFile("C:\\Users\\Vahur Keller\\Desktop\\example.txt");
        System.out.println(findFriendByLastName("Tamm"));
        System.out.println(findFriendByLastName("Ain").getNames());
        System.out.println(findFriendByLastName("Punn").getFullName());
        System.out.println(findFriendByLastName("Rukis").getFullName());
        System.out.println(findFriendByLastName("Phentagon").getFullName());
        System.out.println(findFriendByLastName("Rukis").getNames());
        System.out.println("Deiv Phentagon".compareTo("aDeiv Phentagon"));
        for (Friend frnd: friends) {
            System.out.println(frnd.getFullName());
        }
    }
}
