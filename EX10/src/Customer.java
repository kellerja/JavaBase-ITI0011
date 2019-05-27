/**
 * Created by Jaanus on 15.03.16.
 * Customer class.
 */
public class Customer {
    /**
     * Customer priority.
     * 0 < priority < 1000
     */
    private int priority;
    /**
     * Customer name.
     */
    private String name;
    /**
     * Customer address.
     */
    private String address;

    /**
     * Customer constructor without arguments.
     */
    public Customer() {
    }

    /**
     * Customer constructor.
     *
     * @param priority Customer prority
     * @param name     Customer name
     * @param address  Customer address
     */
    public Customer(int priority, String name, String address) {
        this.priority = priority;
        this.name = name;
        this.address = address;
    }


    /**
     * Get customer address.
     *
     * @return Customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set customer address.
     *
     * @param address Customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get customer name.
     *
     * @return Customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Set customer name.
     *
     * @param name Customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get customer priority.
     *
     * @return Customer priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Set customer priority.
     *
     * @param priority Customer priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
