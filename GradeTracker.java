import java.io.*;
import java.util.*;
public class GradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        int n;
        int subjectCount = 0;
        // Inputs number of students
        while (true) {
            System.out.print("Enter number of students: ");
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                scanner.nextLine();
                if (n > 0) break;
                else System.out.println("Number must be positive.");
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
        // Input each student data
        for (int i = 0; i < n; i++) {
            String name;
            while (true) {
                System.out.print("Enter student name: ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z ]+")) break;
                else System.out.println("Invalid name. Please enter only alphabetic characters.");
            }
            int numSubjects;
            while (true) {
                System.out.print("Enter number of subjects: ");
                if (scanner.hasNextInt()) {
                    numSubjects = scanner.nextInt();
                    if (numSubjects > 0) {
                        if (i == 0) subjectCount = numSubjects;
                        else if (numSubjects != subjectCount) {
                            System.out.println("All students must have the same number of subjects.");
                            continue;
                        }
                        break;
                    } else System.out.println("Number must be positive.");
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next();
                }
            }
            int[] marks = new int[numSubjects];
            for (int j = 0; j < numSubjects; j++) {
                while (true) {
                    System.out.print("Enter mark for subject " + (j + 1) + ": ");
                    if (scanner.hasNextInt()) {
                        int mark = scanner.nextInt();
                        if (mark >= 0 && mark <= 100) {
                            marks[j] = mark;
                            break;
                        } else {
                            System.out.println("Please enter marks between 0 and 100.");
                        }
                    } else {
                        System.out.println("Invalid mark. Please enter an integer.");
                        scanner.next();
                    }
                }
            }
            scanner.nextLine();
            students.add(new Student(name, marks));
        }
        // Sort based on average for ranking
        students.sort((a, b) -> Double.compare(b.getAverage(), a.getAverage()));
        // Output of the results
        try {
            PrintWriter writer = new PrintWriter("StudentReport.txt");
            // --- Student Report Table ---
            System.out.println("\n===== Student Report with Marks =====");
            writer.println("\n===== Student Report with Marks =====");
            // Header lines
            StringBuilder border = new StringBuilder("+------------");
            for (int i = 0; i < subjectCount; i++) border.append("+---------");
            border.append("+---------+--------+---------+-------+");
            StringBuilder title = new StringBuilder("| Name       ");
            for (int i = 0; i < subjectCount; i++) title.append(String.format("| Sub%-3d  ", i + 1));
            title.append("| Highest | Lowest | Average | Grade |");
            System.out.println(border);
            writer.println(border);
            System.out.println(title);
            writer.println(title);
            System.out.println(border);
            writer.println(border);
            for (Student s : students) {
                StringBuilder row = new StringBuilder();
                row.append(String.format("| %-10s ", s.name));
                for (int mark : s.marks) {
                    row.append(String.format("| %-8d", mark));
                }
                row.append(String.format("| %-7d | %-6d | %-7.2f | %-5s |",
                        s.getHighest(), s.getLowest(), s.getAverage(), s.getGrade()));
                System.out.println(row);
                writer.println(row);
            }
            System.out.println(border);
            writer.println(border);
            // --- Ranking Table ---
            System.out.println("\n=== Ranking Based on Average Marks ===");
            writer.println("\n=== Ranking Based on Average Marks ===");
            String rankLine = "+------+------------+---------+-------+";
            String rankHeader = String.format("| %-4s | %-10s | %-7s | %-5s |", "Rank", "Name", "Average", "Grade");
            System.out.println(rankLine);
            writer.println(rankLine);
            System.out.println(rankHeader);
            writer.println(rankHeader);
            System.out.println(rankLine);
            writer.println(rankLine);
            int rank = 1;
            for (Student s : students) {
                String row = String.format("| %-4d | %-10s | %-7.2f | %-5s |",
                        rank++, s.name, s.getAverage(), s.getGrade());
                System.out.println(row);
                writer.println(row);
            }
            System.out.println(rankLine);
            writer.println(rankLine);
            writer.close();
            System.out.println("\nReport successfully saved to StudentReport.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        scanner.close();
    }
}
// Student class
class Student {
    String name;
    int[] marks;
    public Student(String name, int[] marks) {
        this.name = name;
        this.marks = marks;
    }
    public double getAverage() {
        int sum = 0;
        for (int mark : marks) sum += mark;
        return (double) sum / marks.length;
    }
    public String getGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else if (avg >= 50) return "E";
        else return "F";
    }
    public int getHighest() {
        int max = marks[0];
        for (int mark : marks) if (mark > max) max = mark;
        return max;
    }
    public int getLowest() {
        int min = marks[0];
        for (int mark : marks) if (mark < min) min = mark;
        return min;
    }
}