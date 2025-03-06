import java.util.*;

// RoomOperations Interface - Abstraction for Room Operations
interface RoomOperations {
    void bookRoom(Customer customer);
    boolean isAvailable();
    void vacateRoom();
}

// Concrete Room class implementing RoomOperations
class Room implements RoomOperations {
    private int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void bookRoom(Customer customer) {
        if (!isAvailable) {
            System.out.println("Room is already booked!");
        } else {
            isAvailable = false;
            System.out.println("Room " + roomNumber + " has been booked for " + customer.getName());
        }
    }

    @Override
    public void vacateRoom() {
        isAvailable = true;
        System.out.println("Room " + roomNumber + " is now available.");
    }
}

// Customer class
class Customer {
    private String name;
    private int age;
    private String contactNumber;

    public Customer(String name, int age, String contactNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }
}

// Booking Eligibility Interface
interface BookingEligibility {
    boolean isEligible(Customer customer);
}

// Implementations of BookingEligibility
class AllCustomersAllowed implements BookingEligibility {
    @Override
    public boolean isEligible(Customer customer) {
        return true;
    }
}

class OnlyVIPAllowed implements BookingEligibility {
    @Override
    public boolean isEligible(Customer customer) {
        return customer.getName().startsWith("VIP");
    }
}

// RoomBooking Interface - Abstraction for booking rooms
interface RoomBooking {
    void bookRoom(RoomOperations room, Customer customer);
}

// Abstract Room Booking class - Implements DIP
abstract class AbstractRoomBooking implements RoomBooking {
    protected BookingEligibility eligibility;

    public AbstractRoomBooking(BookingEligibility eligibility) {
        this.eligibility = eligibility;
    }

    @Override
    public void bookRoom(RoomOperations room, Customer customer) {
        if (room.isAvailable() && eligibility.isEligible(customer)) {
            room.bookRoom(customer);
        } else {
            System.out.println("Room cannot be booked due to eligibility or availability.");
        }
    }
}

// Standard and VIP Room Booking
class StandardRoomBooking extends AbstractRoomBooking {
    public StandardRoomBooking(BookingEligibility eligibility) {
        super(eligibility);
    }
}

class VIPRoomBooking extends AbstractRoomBooking {
    public VIPRoomBooking(BookingEligibility eligibility) {
        super(eligibility);
    }
}

// FoodOrderable Interface
interface FoodOrderable {
    void addItem(String item, int quantity);
    void displayOrder();
}

// FoodOrder Class
class FoodOrder implements FoodOrderable {
    private Map<String, Integer> orders = new HashMap<>();

    @Override
    public void addItem(String item, int quantity) {
        orders.put(item, orders.getOrDefault(item, 0) + quantity);
    }

    @Override
    public void displayOrder() {
        System.out.println("Food Order Details:");
        orders.forEach((item, quantity) -> System.out.println(item + " x " + quantity));
    }
}

// Billable Interface
interface Billable {
    void generateBill(Customer customer, int roomCharge, FoodOrderable foodOrder);
}

// Billing Class
class Billing implements Billable {
    @Override
    public void generateBill(Customer customer, int roomCharge, FoodOrderable foodOrder) {
        System.out.println("Generating bill for " + customer.getName());
        System.out.println("Room Charges: " + roomCharge);
        foodOrder.displayOrder();
        System.out.println("Total amount due: " + (roomCharge + 500)); // Example calculation
    }
}

// Dependency Injector Class
class DependencyInjector {
    public static RoomOperations provideRoom(int roomNumber) {
        return new Room(roomNumber);
    }

    public static RoomBooking provideStandardRoomBooking() {
        return new StandardRoomBooking(new AllCustomersAllowed());
    }

    public static RoomBooking provideVIPRoomBooking() {
        return new VIPRoomBooking(new OnlyVIPAllowed());
    }

    public static FoodOrderable provideFoodOrder() {
        return new FoodOrder();
    }

    public static Billable provideBilling() {
        return new Billing();
    }
}

// Main Class
public class DIP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RoomOperations standardRoom = DependencyInjector.provideRoom(1);
        RoomOperations vipRoom = DependencyInjector.provideRoom(2);

        RoomBooking standardBooking = DependencyInjector.provideStandardRoomBooking();
        RoomBooking vipBooking = DependencyInjector.provideVIPRoomBooking();

        FoodOrderable foodOrder = DependencyInjector.provideFoodOrder();
        Billable billing = DependencyInjector.provideBilling();

        while (true) {
            System.out.println("\n1. Book Standard Room\n2. Book VIP Room\n3. Order Food\n4. Checkout\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter name, age, contact number:");
                    scanner.nextLine();
                    Customer customer = new Customer(scanner.nextLine(), scanner.nextInt(), scanner.next());
                    standardBooking.bookRoom(standardRoom, customer);
                    break;

                case 2:
                    System.out.println("Enter name, age, contact number:");
                    scanner.nextLine();
                    Customer vipCustomer = new Customer(scanner.nextLine(), scanner.nextInt(), scanner.next());
                    vipBooking.bookRoom(vipRoom, vipCustomer);
                    break;

                case 3:
                    System.out.println("Enter food item and quantity:");
                    foodOrder.addItem(scanner.next(), scanner.nextInt());
                    break;

                case 4:
                    billing.generateBill(new Customer("John Doe", 30, "1234567890"), 1000, foodOrder);
                    standardRoom.vacateRoom();
                    vipRoom.vacateRoom();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
