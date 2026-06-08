package main;

import model.Book;
import model.Reader;
import service.LibraryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LibraryService library = new LibraryService();

        while (true) {

            System.out.println("\n===== LIBRARY =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Reader");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Reader Books");
            System.out.println("6. Find Book");
            System.out.println("7. Available Books");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Author: ");
                    String author = scanner.nextLine();

                    System.out.print("Year: ");
                    int year = scanner.nextInt();

                    library.addBook(
                            new Book(bookId, title, author, year, true)
                    );

                    break;

                case 2:

                    System.out.print("ID: ");
                    int readerId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();

                    library.addReader(
                            new Reader(readerId, name, phone)
                    );

                    break;

                case 3:

                    System.out.print("Reader ID: ");
                    int r1 = scanner.nextInt();

                    System.out.print("Book ID: ");
                    int b1 = scanner.nextInt();

                    library.borrowBook(r1, b1);

                    break;

                case 4:

                    System.out.print("Reader ID: ");
                    int r2 = scanner.nextInt();

                    System.out.print("Book ID: ");
                    int b2 = scanner.nextInt();

                    library.returnBook(r2, b2);

                    break;

                case 5:

                    System.out.print("Reader ID: ");
                    int r3 = scanner.nextInt();

                    library.getReaderBooks(r3)
                            .forEach(System.out::println);

                    break;

                case 6:

                    System.out.print("Keyword: ");
                    String keyword = scanner.nextLine();

                    library.searchBooks(keyword)
                            .forEach(System.out::println);

                    break;

                case 7:

                    library.getAvailableBooks()
                            .forEach(System.out::println);

                    break;

                case 0:

                    System.exit(0);

                default:

                    System.out.println("Invalid option.");
            }
        }
    }
}
