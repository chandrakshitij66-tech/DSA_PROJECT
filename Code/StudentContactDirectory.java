import java.io.*;
import java.util.*;
class Contact {
 private String name, phone, email;
 Contact(String name, String phone, String email) {
 this.name = name;
 this.phone = phone;
 this.email = email;
 }
 String getName() { return name; }
 String getPhone() { return phone; }
 String getEmail() { return email; }
 void setPhone(String phone) { this.phone = phone; }
 public String toString() {
 return name + " | " + phone + " | " + email;
 }
}
class ContactDirectory {
 private ArrayList<Contact> contacts = new ArrayList<>();
 private HashMap<String, Contact> phoneMap = new HashMap<>();
 private final String fileName = "contacts.txt";
 ContactDirectory() { loadContacts(); }
 void addContact(String name, String phone, String email) {
 if (!phone.matches("\\\\d{10}")) {
 System.out.println("Invalid phone number. Enter 10 digits.");
 return;
 }
 if (phoneMap.containsKey(phone)) {
 System.out.println("Phone number already exists.");
 return;
 }
 Contact c = new Contact(name, phone, email);
 contacts.add(c);
 phoneMap.put(phone, c);
 System.out.println("Contact added.");
 }
 void displayContacts() {
 if (contacts.isEmpty()) {
 System.out.println("Directory is now empty.");
 return;
 }
 for (Contact c : contacts) System.out.println(c);
 }
 void searchContact(String key) {
 Contact found = phoneMap.get(key);
 if (found == null) {
 for (Contact c : contacts) {
 if (c.getName().equalsIgnoreCase(key)) {
 found = c;
 break;
 }
 }
 }
 System.out.println(found == null ? "Contact not found." : "Found: " + found);
 }
 void deleteContact(String name) {
 for (int i = 0; i < contacts.size(); i++) {
 Contact c = contacts.get(i);
 if (c.getName().equalsIgnoreCase(name)) {
 contacts.remove(i);
 phoneMap.remove(c.getPhone());
 System.out.println("Contact deleted.");
 return;
 }
 }
 System.out.println("Contact not found.");
 }
 void updatePhone(String name, String newPhone) {
 if (!newPhone.matches("\\\\d{10}") ||
 (phoneMap.containsKey(newPhone) &&
 !phoneMap.get(newPhone).getName().equalsIgnoreCase(name))) {
 System.out.println("Invalid or duplicate phone number.");
 return;
 }
 for (Contact c : contacts) {
 if (c.getName().equalsIgnoreCase(name)) {
 phoneMap.remove(c.getPhone());
 c.setPhone(newPhone);
 phoneMap.put(newPhone, c);
 System.out.println("Contact updated.");
 return;
 }
 }
 System.out.println("Contact not found.");
 }
 private void loadContacts() {
 File file = new File(fileName);
 if (!file.exists()) return;
 try (Scanner sc = new Scanner(file)) {
 while (sc.hasNextLine()) {
 String[] p = sc.nextLine().split(",", 3);
 if (p.length == 3 && p[1].matches("\\\\d{10}")
 && !phoneMap.containsKey(p[1])) {
 Contact c = new Contact(p[0], p[1], p[2]);
 contacts.add(c);
 phoneMap.put(p[1], c);
 }
 }
 } catch (IOException e) {
 System.out.println("Could not load contacts.");
 }
 }
 private void saveContacts() {
 try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
 for (Contact c : contacts) {
 out.println(c.getName() + "," + c.getPhone() + "," + c.getEmail());
 }
 } catch (IOException e) {
 System.out.println("Could not save contacts.");
 }
 }
 void saveAndExit() { saveContacts(); }
}
public class StudentContactDirectory {
 public static void main(String[] args) {
 Scanner input = new Scanner(System.in);
 ContactDirectory directory = new ContactDirectory();
 while (true) {
 System.out.println("\\n===== STUDENT CONTACT DIRECTORY =====");
 System.out.println("1. Add Contact");
 System.out.println("2. Display All Contacts");
 System.out.println("3. Search Contact");
 System.out.println("4. Delete Contact");
 System.out.println("5. Update Phone");
 System.out.println("6. Exit");
 System.out.print("Enter choice: ");
 int choice;
 try {
 choice = Integer.parseInt(input.nextLine());
 } catch (NumberFormatException e) {
 System.out.println("Enter a valid menu number.");
 continue;
 }
 switch (choice) {
 case 1:
 System.out.print("Name: ");
 String name = input.nextLine();
 System.out.print("Phone: ");
 String phone = input.nextLine();
 System.out.print("Email: ");
 String email = input.nextLine();
 directory.addContact(name, phone, email);
 break;
 case 2:
 directory.displayContacts();
 break;
 case 3:
 System.out.print("Enter name or phone: ");
 directory.searchContact(input.nextLine());
 break;
 case 4:
 System.out.print("Enter name to delete: ");
 directory.deleteContact(input.nextLine());
 break;
 case 5:
 System.out.print("Enter name: ");
 String updateName = input.nextLine();
 System.out.print("Enter new phone: ");
 directory.updatePhone(updateName, input.nextLine());
 break;
 case 6:
 directory.saveAndExit();
 System.out.println("Directory saved. Goodbye!");
 input.close();
 return;
 default:
 System.out.println("Invalid choice.");
 }
 }
 }
}