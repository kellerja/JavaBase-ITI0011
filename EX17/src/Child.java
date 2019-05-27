import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaanus on 28.04.16.
 */
public class Child {
    /***/
    private String name;
    /***/
    private List<Child> playsWith = new ArrayList<>();

    /**
     * @param childName - child name.
     */
    public Child(String childName) {
        name = childName;
    }

    /**
     * @param children - children the child plays with.
     */
    public void playsWith(Child... children) {
        for (Child c : children) {
            if (c == null || this == c || c.getName() == null) {
                continue;
            }
            playsWith.add(c);
            //c.playsWith(children);
            //c.playsWith(this);
        }
    }

    /**
     * @return list of cildren playing with child.
     */
    public List<Child> getPlaysWith() {
        return playsWith;
    }

    /**
     * @return name of the child.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name - root of the tree of children playing together.
     */
    public static List<Child> getSandbox(Child name) {
        if (name == null || name.getName() == null) {
            return null;
        }
        List<Child> sandbox = new ArrayList<>();
        sandbox.add(name);
        for (Child c: name.getPlaysWith()) {
            sandbox.addAll(getSandbox(c));
        }
        return sandbox;
    }
}
