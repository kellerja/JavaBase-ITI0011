import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaanus on 10.03.16.
 */
public class Order {
    /***/
    private static int nextOrderNumber = 0;
    /***/
    private String clientName;
    /***/
    private String deliveryAddress;
    /***/
    private int orderNumber;
    /**[FairTradeRoses, Roses, Tulips].*/
    private int[] flowerCount = new int[2 + 1];
    /***/
    private List<Flower> flowers = new ArrayList<>();
    /**
     * @param clientName - name of the client.
     * @param deliveryAddress - address to send flowers.
     */
    public Order(String clientName, String deliveryAddress) {
        this.clientName = clientName;
        this.deliveryAddress = deliveryAddress;
    }
    /**
     * @param flower - add flower to order.
     * @return did the order go through.
     */
    public boolean add(Flower flower) {
        if (flower != null) {
            flowers.add(flower);
            return true;
        }
        return false;
    }
    /**
     * @param flower - add flower to order.
     * @param count - flower count.
     * @return did the order go through.
     */
    public boolean add(Flower flower, int count) {
        if (flower != null && count > 0) {
            for (int i = 0; i < count; i++) {
                flowers.add(flower);
            }
            return true;
        }
        return false;
    }
    /**
     * @return price of the order.
     */
    public double getTotalPrice() {
        double totalPrice = 0;
        countFlowers();
        if (flowers != null) {
            for (Flower flower: flowers) {
                if (flower instanceof FairTradeRose) {
                    totalPrice += ((FairTradeRose) flower).getPrice();
                } else if (flower instanceof Tulip) {
                    totalPrice += ((Tulip) flower).getPrice(flowerCount[2]);
                } else if (flower instanceof Rose) {
                    totalPrice += ((Rose) flower).getPrice(flowerCount[1]);
                } else {
                    totalPrice += flower.getPrice();
                }
            }
        }
        return totalPrice;
    }
    /**
     * @return current order number.
     */
    public int getOrderNumber() {
        return orderNumber;
    }
    /**
     * @return highest order number + 1
     */
    public static int getNextOrderNumber() {
        nextOrderNumber += 1;
        return nextOrderNumber;
    }
    /**
     * @return string that shows what has been bought.*/
    private String[] countFlowersForPayfull() {
        String result = "";
        List<Flower> keysToObjects = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        HashMap<String, Integer> flowerCount = new HashMap<>();
        for (Flower flower: flowers) {
            if (flower.getPrice() <= 0 || flower == null) {
                continue;
            }
            String flowerInstance = "";
            if (flower instanceof Rose) {
                flowerInstance += "Rose";
                if (((Rose) flower).hasThorns()) {
                    flowerInstance += " with thorns";
                } else {
                    flowerInstance += " without thorns";
                }
            } else if (flower instanceof Tulip) {
                if (((Tulip) flower).getColor() == null) {
                    flowerInstance += "Tulip";
                } else {
                    flowerInstance += ((Tulip) flower).getColor() + " tulip";
                }
            }
            flowerInstance += "|" + flower.getRealPrice();
            if (flowerCount.containsKey(flowerInstance)) {
                flowerCount.put(flowerInstance, flowerCount.get(flowerInstance) + 1);
            } else {
                keysToObjects.add(flower);
                keys.add(flowerInstance);
                flowerCount.put(flowerInstance, 1);
            }
        }
        double totalPrice = 0;
        for (int i = 0; i < keys.size(); i++) {
            double price = 0;
            if (keysToObjects.get(i) instanceof Rose) {
                price = ((Rose) keysToObjects.get(i)).getPrice(
                        flowerCount.get(keys.get(i)));
            } else if (keysToObjects.get(i) instanceof Tulip) {
                price = ((Tulip) keysToObjects.get(i)).getPrice(
                        flowerCount.get(keys.get(i)));
            }
            String[] flowerNameInHashMap = keys.get(i).split("\\|");
            totalPrice += price * flowerCount.get(keys.get(i));
            result += "Item " + (i + 1) + ": " + flowerNameInHashMap[0]
                    + " " + flowerCount.get(keys.get(i)) + " x "
                    + String.format("%.2f", price) + "€";
            if (i != keys.size() - 1) {
                result += System.lineSeparator();
            }
        }
        if (result.length() != 0) {
            result += System.lineSeparator();
        }
        String[] rtrn = new String[2];
        rtrn[0] = result;
        rtrn[1] = "" + totalPrice;
        return rtrn;
    }
    /**
     * @return string that shows what flowers and how many times have been bought.*/
    private String countFlowers() {
        String result = "";
        int countRose = 0;
        int countTulip = 0;
        int countFairRoses = 0;
        if (flowers != null) {
            for (Flower flower : flowers) {
                if (flower instanceof FairTradeRose && flower.getRealPrice() >= 0) {
                    countFairRoses += 1;
                } else if (flower instanceof Tulip && flower.getRealPrice() >= 0) {
                    countTulip += 1;
                } else if (flower instanceof Rose && flower.getRealPrice() >= 0) {
                    countRose += 1;
                }
            }
            flowerCount[0] = countFairRoses;
            flowerCount[1] = countRose;
            flowerCount[2] = countTulip;
            if (countFairRoses != 0) {
                result += "Fair-trade roses: " + countFairRoses;
            }
            if (countRose != 0) {
                if (countFairRoses != 0) {
                    result += System.lineSeparator();
                }
                result += "Roses: " + countRose;
            }
            if (countTulip != 0) {
                if (countRose != 0 || countFairRoses != 0) {
                    result += System.lineSeparator();
                }
                result += "Tulips: " + countTulip;
            }
        }
        if (!result.equals("")) {
            result += System.lineSeparator();
        }
        return result;
    }
    /**
     * @return proof of payment.
     */
    public String pay() {
        orderNumber = getNextOrderNumber();
        String orderPrint = "";
        String newline = System.lineSeparator();
        if (clientName != null && deliveryAddress != null) {
            orderPrint = "Order: " + orderNumber + newline
                    + "Client: " + clientName + newline
                    + "Address: " + deliveryAddress + newline
                    + countFlowers() + String.format("%.2f", getTotalPrice()) + "€";
        }
        return orderPrint;
    }
    /**
     * @return proof of payment.
     */
    public String payFull() {
        orderNumber = getNextOrderNumber();
        String orderPrint = "";
        String[] flowersWithPrice = countFlowersForPayfull();
        double price = 0;
        if (Double.parseDouble(flowersWithPrice[1]) >= 0) {
            price = Double.parseDouble(flowersWithPrice[1]);
        }
        String newline = System.lineSeparator();
        if (clientName != null && deliveryAddress != null) {
            orderPrint += "Order: " + orderNumber + newline
                    + "Client: " + clientName + newline
                    + "Address: " + deliveryAddress + newline
                    + flowersWithPrice[0]
                    + "Total: " + String.format("%.2f", price) + "€";
        }
        return orderPrint;
    }
    /**
     * Made since I am not sure if renaming payFull would break anything.
     *
     * @return result of payFull.
     */
    public String payItems() {
        return payFull();
    }
    /**
     * Cheap way to do it.
     *
     * @return price of items with counts.
     */
    public double getTotalPriceItems() {
        double price = 0;
        if (flowers != null) {
            String[] tempCount = countFlowersForPayfull();
            price = Double.parseDouble(tempCount[1]);
        }
        return price;
    }
    /**
     * @param orders - list of orders.
     * @return name or names of the clients with most fair trade rose purchases.
     */
    public static String findTheMostCaringCustomer(List<Order> orders) {
        String result = "";
        if (orders != null) {
            HashMap<String, Integer> clientsAndRoses = new HashMap<>();
            for (Order order: orders) {
                if (order == null) {
                    continue;
                }
                if (clientsAndRoses.containsKey(order.clientName)) {
                    clientsAndRoses.put(order.clientName, clientsAndRoses.get(order.clientName) + order.flowerCount[0]);
                } else {
                    clientsAndRoses.put(order.clientName, order.flowerCount[0]);
                }
            }
            int maximumValue = 1;
            for (String customer: clientsAndRoses.keySet()) {
                if (clientsAndRoses.get(customer) > maximumValue) {
                    result = customer;
                    maximumValue = clientsAndRoses.get(customer);
                } else if (clientsAndRoses.get(customer) == maximumValue) {
                    result += System.lineSeparator() + customer;
                }
            }
        }
        return result;
    }
    /**
     * @return name of the client.
     */
    public String getClient() {
        return clientName;
    }
    /**
     * @return address of the client.
     */
    public String getAddress() {
        return deliveryAddress;
    }
}
