package com.pm.library.Main;

import com.pm.library.dao.implementations.BookImplementation;
import com.pm.library.dao.implementations.BookReferenceImplementation;
import com.pm.library.dao.implementations.MemberImplementation;
import com.pm.library.model.Book;
import com.pm.library.model.BookReference;
import com.pm.library.model.Member;

import javax.sound.midi.MetaMessage;
import java.rmi.server.ExportException;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    private static BookReference bookReference;
    private static BookReferenceImplementation bookReferenceImp = new BookReferenceImplementation();

    private static Book book;
    private static BookImplementation bookImp = new BookImplementation();

    private static Member member;
    private static MemberImplementation memberImp = new MemberImplementation();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;
        do {
            MAIN_MENU();
            choice = takeUserChoice();
            switch (choice) {
                // Books Management Menu
                case 1:
                    BOOK_MENU();
                    choice = takeUserChoice();
                    switch (choice) {
                        // Add A Book
                        case 1:
                            try {
                                bookReference = new BookReference(takeBookData());
                                System.out.println("Your Book Info\n");
                                System.out.println(bookReference.toString());
                                boolean exist = BookReferenceImplementation.isBookReferenceExist(bookReference.getIsbn());
                                if (exist) {
                                    String confirmartionMessage = yellowText("This book already exist!, Update the quantity by 1(Y/N)?");
                                    if (confirmation(confirmartionMessage)) {
                                        // Add a Book copy
                                        book = new Book();
                                        book.setIsbn(bookReference.getIsbn());

                                        bookReference.incrementQuantity(1);

                                        if (bookImp.add(book) && bookReferenceImp.update(bookReference)) {
                                            System.out.println(greenText("Book quantity in incremented successfully"));
                                        } else {
                                            System.err.println(yellowText("Something wrong please try again"));
                                        }
                                    }
                                } else {
                                    if (confirmation("Agree to save the book(Y/N)?")) {
                                        boolean isAdded = bookReferenceImp.add(bookReference);
                                        if (isAdded) {
                                            for (int i = 0; i < bookReference.getQuantity(); i++) {
                                                // Add a Book copy
                                                book = new Book();
                                                book.setIsbn(bookReference.getIsbn());

                                                bookImp.add(book);
                                            }
                                            System.out.println(greenText("The book saved successfully :)"));
                                        } else {
                                            System.err.println(yellowText("Something wrong please try again"));
                                        }
                                    } else {
                                        System.out.println(yellowText("The operation canceled :("));
                                    }
                                }
                            } catch (Exception e) {
                                System.err.println("Error saving the book try again");
                            }
                            pressToContinue();
                            break;
                        // Update An Existing Book
                        case 2:
                            String isbn = takeStringInputValue("Enter The Book ISBN to update: ");
                            try {
                                boolean exist = BookReferenceImplementation.isBookReferenceExist(isbn);
                                if (exist) {
                                    UPDATE_BOOK_MENU();
                                    choice = takeIntInputValue("Enter a choice");
                                    bookReference = new BookReference(bookReferenceImp.getBookReference(isbn));
                                    switch (choice) {
                                        //Update all info
                                        case 1:
                                            try {
                                                String newTitle = takeStringInputValue("Enter the new title: ");
                                                String newAuthor = takeStringInputValue("Enter the new author: ");
                                                int incrementQuantity = takeIntInputValue("Increment the quantity: ");

                                                bookReference.setTitle(newTitle);
                                                bookReference.setAuthor(newAuthor);
                                                bookReference.incrementQuantity(incrementQuantity);

                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    boolean bookUpdated = bookReferenceImp.update(bookReference);
                                                    if (bookUpdated) {
                                                        System.out.println(greenText("Book updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }

                                            } catch (Exception e) {
                                                System.err.println("Error updating the book");
                                            }
                                            pressToContinue();
                                            break;
                                        // Update the book title
                                        case 2:
                                            try {
                                                String newTitle = takeStringInputValue("Enter the new title: ");
                                                bookReference.setTitle(newTitle);

                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    boolean bookUpdated = bookReferenceImp.update(bookReference);
                                                    if (bookUpdated) {
                                                        System.out.println(greenText("Book updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Error updating the book title");
                                            }
                                            pressToContinue();
                                            break;
                                        // Update the book author
                                        case 3:
                                            try {
                                                String newAuthor = takeStringInputValue("Enter the new author: ");
                                                bookReference.setAuthor(newAuthor);
                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    boolean bookUpdated = bookReferenceImp.update(bookReference);
                                                    if (bookUpdated) {
                                                        System.out.println(greenText("Book updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Error updating the book");
                                            }
                                            pressToContinue();
                                            break;
                                        // Update the quantity
                                        case 4:
                                            try {
                                                int incrementQuantity = takeIntInputValue("Increment the quantity: ");
                                                bookReference.incrementQuantity(incrementQuantity);
                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    boolean bookUpdated = bookReferenceImp.update(bookReference);
                                                    if (bookUpdated) {
                                                        System.out.println(greenText("Book quantity updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Error updating the book quantity");
                                            }
                                            pressToContinue();
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    System.out.println(yellowText("There is no book with this ISBN in this library!"));
                                    pressToContinue();

                                }
                            } catch (Exception e) {
                                System.err.println("An error occurred => " + e.getMessage());
                                pressToContinue();
                            }
                            break;
                        // Delete An Existing Book
                        case 3:
                            isbn = takeStringInputValue("Enter The Book ISBN to delete: ");
                            try {
                                boolean exist = BookReferenceImplementation.isBookReferenceExist(isbn);
                                if (exist) {
                                    boolean operationConfirmed = confirmation("Confirm the delete operation(Y/N)");
                                    if (operationConfirmed) {
                                        if (bookReferenceImp.delete(isbn)) {
                                            System.out.println(greenText("The book deleted successfully :)"));
                                        } else {
                                            System.out.println(yellowText("Your book was not deleted :(, Could You try again :)"));
                                        }
                                    } else {
                                        System.out.println(yellowText("The operation canceled :("));
                                    }
                                } else {
                                    System.out.println(yellowText("There is no book with this ISBN in this library!"));
                                }
                            } catch (Exception e) {
                                System.err.println("Error occurred trying delete a book : " + e.getMessage());
                            }
                            pressToContinue();
                            break;
                        default:
                            break;
                    }
                    break;
                // Members management
                case 2:
                    MEMBER_MENU();
                    choice = takeUserChoice();
                    switch (choice) {
                        // ADD A MEMBER
                        case 1:
                            try {
                                member = new Member(takeMemberData());
                                System.out.println("Your Member Info");
                                System.out.println(member.toString());

                                if (confirmation("Agree to save the member(Y/N)?")) {
                                    if (memberImp.add(member)) {
                                        System.out.println(greenText("The member saved successfully :)"));
                                    } else {
                                        System.err.println(yellowText("Something wrong please try again"));
                                    }
                                } else {
                                    System.out.println(yellowText("The operation canceled :("));
                                }

                            } catch (Exception e) {
                                System.err.println("Error saving the member try again");
                            }
                            pressToContinue();
                            break;
                        // UPDATE A MEMBER
                        case 2:
                            String phoneNumber = takeStringInputValue("Enter The Member Phone Number to update: ");
                            try {
                                boolean isMemberExist = MemberImplementation.getMemberByPhone(phoneNumber) != null;
                                if (isMemberExist) {
                                    member = new Member(MemberImplementation.getMemberByPhone(phoneNumber));
                                    UPDATE_MEMBER_MENU();
                                    choice = takeIntInputValue("Enter a choice");
                                    switch (choice) {
                                        //Update all info
                                        case 1:
                                            try {
                                                String newName = takeStringInputValue("Enter the new name: ");
                                                String newPhoneNumber = takeStringInputValue("Enter the new phone number: ");
                                                String newAddress = takeStringInputValue("Enter the new address: ");

                                                member.setName(newName);
                                                member.setPhoneNumber(newPhoneNumber);
                                                member.setAddress(newAddress);

                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    if (memberImp.update(member)) {
                                                        System.out.println(greenText("Member updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }

                                            } catch (Exception e) {
                                                System.err.println("Error updating the member");
                                            }
                                            pressToContinue();
                                            break;
                                        // Update the member name
                                        case 2:
                                            try {
                                                String newName = takeStringInputValue("Enter the new title: ");
                                                member.setName(newName);

                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    if (memberImp.update(member)) {
                                                        System.out.println(greenText("Member name updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Error updating the member name");
                                            }
                                            pressToContinue();
                                            break;
                                        // Update the member phoneNumber
                                        case 3:
                                            try {
                                                String newPhoneNumber = takeStringInputValue("Enter the new author: ");
                                                member.setPhoneNumber(newPhoneNumber);
                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    if (memberImp.update(member)) {
                                                        System.out.println(greenText("Member phone number updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Error updating the member phone number");
                                            }
                                            pressToContinue();
                                            break;
                                        // Update the member address
                                        case 4:
                                            try {
                                                String newAddress = takeStringInputValue("Enter the new member address: ");
                                                member.setAddress(newAddress);
                                                boolean operationConfirmed = confirmation("Confirm the update operation(Y/N)?: ");
                                                if (operationConfirmed) {
                                                    if (memberImp.update(member)) {
                                                        System.out.println(greenText("Member address updated successfully"));
                                                    } else {
                                                        System.err.println(yellowText("Something wrong please try again"));
                                                    }
                                                } else {
                                                    System.out.println(yellowText("The operation canceled :("));
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Error updating the member address");
                                            }
                                            pressToContinue();
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    System.out.println(yellowText("There is no member with this phone number in this library!"));
                                    pressToContinue();

                                }
                            } catch (Exception e) {
                                System.out.println(yellowText("An error occurred => " + e.getMessage()));
                                pressToContinue();
                            }
                            break;
                        // Delete An Existing Member
                        case 3:
                            phoneNumber = takeStringInputValue("Enter The Member Phone Number to delete: ");
                            try {
                                boolean isMemberExist = MemberImplementation.getMemberByPhone(phoneNumber) != null;
                                if (isMemberExist) {
                                    boolean operationConfirmed = confirmation("Confirm the delete operation(Y/N)");
                                    if (operationConfirmed) {
                                        member = new Member(MemberImplementation.getMemberByPhone(phoneNumber));
                                        if (memberImp.delete(member.getId())) {
                                            System.out.println(greenText("The member deleted successfully :)"));
                                        } else {
                                            System.out.println(yellowText("Your member was not deleted :(, Could You try again :)"));
                                        }
                                    } else {
                                        System.out.println(yellowText("The operation canceled :("));
                                    }
                                } else {
                                    System.out.println(yellowText("There is no member with this phone number in this library!"));
                                }
                            } catch (Exception e) {
                                System.err.println("Error occurred trying delete this member : " + e.getMessage());
                            }
                            pressToContinue();
                            break;
                        default:
                            break;
                    }
                    break;
                    // Get A Book By ISBN
                case 3:
                    try {
                        String isbn = takeStringInputValue("Enter the book ISBN: ");
                        bookReference = bookReferenceImp.getBookReference(isbn);
                        if (bookReference != null) {
                            System.out.println(greenText("\n" + bookReference.toString() + "\n"));
                        } else {
                            System.out.println(yellowText("There is no book with this ISBN in this library"));
                        }
                    } catch (Exception e) {
                        System.out.println(yellowText(e.getMessage()));
                    }
                    pressToContinue();
                    break;
                // Borrow A Book
                case 5:
                    try {
                        // Take the borrower phone number
                        String phoneNumber;
                        do {
                            phoneNumber = takeStringInputValue("Enter The Member phone number: ");
                            member = MemberImplementation.getMemberByPhone(phoneNumber);
                            if (member == null) {
                                System.out.println(yellowText("This phone number not exist!, try another one."));
                            }
                        } while (member == null);

                        // Check if the member does not borrow any book
                        if (MemberImplementation.hasBook(member)){
                            System.out.println(yellowText("This member already have a book"));
                            pressToContinue();
                            break;
                        }

                        // Take the book isbn
                        String isbn;
                        do {
                            isbn = takeStringInputValue("Enter The Book isbn: ");
                            bookReference = bookReferenceImp.getBookReference(isbn);
                            if (bookReference == null) {
                                System.out.println(yellowText("No book found with this isbn in this library..!, try another one."));
                            }
                        } while (bookReference == null);

                        // Ckeck the book quantity
                        if (bookReference.getQuantity() > 0){
                            // get the 1st book you will meet in the books table with given ISBN and available status.
                            book = new Book(BookImplementation.findAvailableBookByISBN(isbn));

                            MemberImplementation.borrow(member, book);
                        }else{
                            System.out.println(yellowText("This book wasn't available for the moment :<"));
                            pressToContinue();
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println(yellowText(e.getMessage()));
                    }
                    break;
                case 8:
                    System.out.println("THANKS FOR THE VISIT...!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 8);
        scanner.close();
    }

    public static boolean confirmation(String confirmationMessage) {
        String response;
        do {
            response = takeStringInputValue(confirmationMessage);
        } while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n"));
        return response.equalsIgnoreCase("y");
    }

    public static String greenText(String originText) {
        String greenText = "\u001B[32m";
        String resetText = "\u001B[0m";
        originText = greenText + originText + resetText;
        return originText;
    }

    public static String yellowText(String originText) {
        String yellowText = "\u001B[33m";
        String resetText = "\u001B[0m";
        originText = yellowText + originText + resetText;
        return originText;
    }

    //    PROGRAM HELPERS METHODS
    public static int takeUserChoice() {
        int choice = -1;
        do {
            try {
                System.out.print("Enter a choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                scanner.next();
                choice = -1;
                System.out.println(yellowText("Invalid input, must be a number !"));
            }
        } while (choice < 1 || choice > 8);
        return choice;
    }

    public static String takeStringInputValue(String inputMessage) {
        String inputValue;
        try {
            System.out.print(inputMessage);
            inputValue = scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            scanner.next();
            inputValue = "";
        }
        return inputValue;
    }

    public static int takeIntInputValue(String inputMessage) {
        int inputValue = -1;
        do {
            try {
                System.out.print(inputMessage + ": ");
                inputValue = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                scanner.next();
                System.out.println("invalid input(must be a positive number)!");
            }
        } while (inputValue < 1);
        return inputValue;
    }

    public static void pressToContinue() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    //    BOOK METHED HELPERS
    public static BookReference takeBookData() {
        String isbn, title, author;
        int quantity = 0;
        BookReference bookReference = new BookReference();
        isbn = takeStringInputValue("Enter The Book ISBN: ");
        title = takeStringInputValue("Enter The Book title: ");
        author = takeStringInputValue("Enter The Book author: ");

        do {
            quantity = takeIntInputValue("Enter The Book quantity: ");
        } while (quantity <= 0);

        return new BookReference(isbn, title, author, quantity);
    }

    //    MENUSa
    public static void MAIN_MENU() {
        System.out.println(
                yellowText("\t\t\t\t\tLibrary Management System\n\n\n") +
                        "1- Books Management\n" +
                        "2- Members Management\n" +
                        "3- Get a Book By ISBN\n" +
                        "4- Display a Book\n" +
                        "5- Borrow a book\n" +
                        "6- Return a book\n" +
                        "7- Generate a book\n" +
                        "8- Exit\n"
        );
    }

    public static void BOOK_MENU() {
        System.out.println(
                "1- Add\n" +
                        "2- Update\n" +
                        "3- Delete\n"
        );
    }

    public static void UPDATE_BOOK_MENU() {
        System.out.println(
                "Update: \n" +
                        "1- All info\n" +
                        "2- title\n" +
                        "3- author\n" +
                        "4- quantity\n"
        );
    }

    //    MEMBERS---------------------------------------------
    public static void MEMBER_MENU() {
        System.out.println(
                "1- Add\n" +
                        "2- Update\n" +
                        "3- Delete\n"
        );
    }

    public static Member takeMemberData() throws SQLException {
        String name, phoneNumber, address;
        name = takeStringInputValue("Enter The Member name: ");

        do {
            phoneNumber = takeStringInputValue("Enter The Member phone number: ");
            if (MemberImplementation.getMemberByPhone(phoneNumber) != null) {
                System.out.println(yellowText("This phone number already exist!, try another one."));
            }
        } while (MemberImplementation.getMemberByPhone(phoneNumber) != null);


        address = takeStringInputValue("Enter The Member address: ");
        return new Member(name, phoneNumber, address);
    }

    public static void UPDATE_MEMBER_MENU() {
        System.out.println(
                "Update: \n" +
                        "1- All info\n" +
                        "2- name\n" +
                        "3- phone number\n" +
                        "4- address\n"
        );
    }

}
