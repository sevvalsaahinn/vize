import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static List<User> generalUsers = new ArrayList<>();
    private static List<User> eliteUsers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);


        File fileElite = new File("elite_users.txt");
        File fileGeneral = new File("general_users.txt");

        getUsersFromFile(fileElite, fileGeneral);

        FileWriter fileWriterElite = new FileWriter(fileElite, true);
        FileWriter fileWriterGeneral = new FileWriter(fileGeneral, true);


        while (true) {
            System.out.print("" + "1. Create elite user\n" + "2. Create general user\n" + "3. Send email\n" + "4. Exit\n" + "Enter your choice: ");

            Integer choice = scanner.nextInt();

            switch (choice) {
                case 1, 2 -> generateUser(scanner, generalUsers, eliteUsers, fileWriterElite, fileWriterGeneral, choice);
                case 3 -> sendEmail(scanner);
                case 4 -> {
                    fileWriterElite.close();
                    fileWriterGeneral.close();
                    scanner.close();
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void getUsersFromFile(File fileElite, File fileGeneral) throws FileNotFoundException {
        if (fileElite.exists()) {
            Scanner fileScanner = new Scanner(fileElite);
            while (fileScanner.hasNextLine()) {
                String[] user = fileScanner.nextLine().split("\t");
                eliteUsers.add(new EliteUser(user[0], user[1], user[2]));
            }
            fileScanner.close();
        }

        if (fileGeneral.exists()) {
            Scanner fileScanner = new Scanner(fileGeneral);
            while (fileScanner.hasNextLine()) {
                String[] user = fileScanner.nextLine().split("\t");
                generalUsers.add(new GeneralUser(user[0], user[1], user[2]));
            }
            fileScanner.close();
        }
    }

    private static void sendEmail(Scanner scanner) {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost("smtp.gmail.com");
        emailSender.setPort(587);

        emailSender.setUsername("vizeicin123@gmail.com");
        emailSender.setPassword("kabuletsin9");

        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");


        System.out.print("" + "1. Send email to all elite users\n" + "2. Send email to all general users\n" + "3. Send email to all users\n" + "Enter your choice: ");
        Integer emailChoice = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Enter email context: ");
        String context = scanner.nextLine();

        switch (emailChoice) {
            case 1 -> sendEmailToList(emailSender, context, eliteUsers);
            case 2 -> sendEmailToList(emailSender, context, generalUsers);
            case 3 -> {
                sendEmailToList(emailSender, context, eliteUsers);
                sendEmailToList(emailSender, context, generalUsers);
            }
            default -> System.out.println("Invalid choice");
        }
    }

    private static void sendEmailToList(JavaMailSenderImpl emailSender, String context, List<User> users) {
        for (User user : users) {
            System.out.println("\nSending email to " + user.getEmail());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mintlymailsender@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Test Simple Email");
            message.setText(context);
            emailSender.send(message);

            System.out.println("Email sent successfully\n");
        }
    }

    private static void generateUser(Scanner scanner, List<User> generalUsers, List<User> eliteUsers, FileWriter fileWriterElite, FileWriter fileWriterGeneral, Integer choice) throws IOException {
        scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (choice == 1) {
            EliteUser eliteUser = new EliteUser(name, surname, email);
            eliteUsers.add(eliteUser);
            fileWriterElite.write(eliteUser.toString());
            fileWriterElite.write(System.lineSeparator());
        } else {
            GeneralUser generalUser = new GeneralUser(name, surname, email);
            generalUsers.add(generalUser);
            fileWriterGeneral.write(generalUser.toString());
            fileWriterGeneral.write(System.lineSeparator());
        }

        System.out.println("\nUser created successfully\n\n");
    }
}
