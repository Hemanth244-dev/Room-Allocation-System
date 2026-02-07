package p1;

import java.io.*;
import java.util.Scanner;

class Student {
    int id;
    String name;
    String gender;

    Student(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender.toLowerCase();
    }
}

class HostelManager {

	private static final String FILE_NAME = "C:\\javaexamples\\hackathon\\hostel.txt";


    private static final int MAX_ROOMS = 20;
    private static final int ROOM_CAPACITY = 3;

    void allocateRoom(Student s) {

        if (!s.gender.equals("b") && !s.gender.equals("g")) {
            System.out.println("Invalid gender!");
            return;
        }

        String prefix = s.gender.equals("b") ? "B" : "G";

        for (int i = 1; i <= MAX_ROOMS; i++) {
            String roomId = prefix + i;

            if (countStudentsInRoom(roomId) < ROOM_CAPACITY) {
                saveStudent(roomId, s);
                System.out.println("\nRoom Allocated: " + roomId);
                return;
            }
        }

        System.out.println("No room available!");
    }

    int countStudentsInRoom(String roomId) {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(roomId + " || ")) {
                    count++;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    void saveStudent(String roomId, Student s) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            String genderText = s.gender.equals("b") ? "Boy" : "Girl";
            bw.write(roomId + " || " + genderText + " || " + s.id + " || " + s.name);
            bw.newLine();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void deallocateRoom(int studentId) {

        try (
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))
        ) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                if (!line.contains(" || " + studentId + " || ")) {
                    bw.write(line);
                    bw.newLine();
                } else {
                    found = true;
                }
            }

            new File(FILE_NAME).delete();
            new File("temp.txt").renameTo(new File(FILE_NAME));

            if (found)
                System.out.println("\nRoom Deallocated");
            else
                System.out.println("\nStudent not found!");
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }

    void displayRooms() {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            System.out.println("\nRoom Details:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void report() {

        int totalStudents = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            while (br.readLine() != null) {
                totalStudents++;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int totalBeds = MAX_ROOMS * 2 * ROOM_CAPACITY;

        System.out.println("\nTotal Rooms: 40");
        System.out.println("Room Capacity: 3 students");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Total Beds: " + totalBeds);
        System.out.println("Vacant Beds: " + (totalBeds - totalStudents));
    }

    void search(int studentId) {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(" || " + studentId + " || ")) {
                    System.out.println("\nStudent Found:");
                    System.out.println(line);
                    return;
                }
            }
            System.out.println("\nStudent not found!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}


public class example1 {

    public static void main(String[] args) {
    	System.out.println("Running from: " + System.getProperty("user.dir"));

        HostelManager manager = new HostelManager();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n1. Allocate Room");
            System.out.println("2. Deallocate Room");
            System.out.println("3. Display Rooms");
            System.out.println("4. Report");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("\nID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Gender (b/g): ");
                    String gender = sc.nextLine();

                    manager.allocateRoom(new Student(id, name, gender));
                    break;

                case 2:
                    System.out.print("\nStudent ID: ");
                    manager.deallocateRoom(sc.nextInt());
                    break;

                case 3:
                    manager.displayRooms();
                    break;

                case 4:
                    manager.report();
                    break;

                case 5:
                    System.out.print("\nStudent ID: ");
                    manager.search(sc.nextInt());
                    break;

                case 6:
                    System.out.println("Exit");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
