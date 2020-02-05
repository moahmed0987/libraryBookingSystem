package librarybookingsystem;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryBookingSystem {

    static Scanner input = new Scanner(System.in);
    // change to read/write to text file
    static ArrayList<Books> listOfBooks = new ArrayList();
    static ArrayList<Borrowers> listOfBorrowers = new ArrayList();
    static DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    public static void main(String[] args) {
        Borrowers testBorrower = new Borrowers("testBorrower", "242362");
        Books testBook = new Books("testBook", testBorrower, "145346", LocalDate.of(2020, 01, 01));
        listOfBorrowers.add(testBorrower);
        listOfBooks.add(testBook);

        Borrowers testBorrowerToday = new Borrowers("testBorrowerToday", "352");
        Books testBookToday = new Books("testBookToday", testBorrowerToday, "534634", LocalDate.now());
        listOfBorrowers.add(testBorrowerToday);
        listOfBooks.add(testBookToday);

        // login to system as "admin" -- extend person class        
        while (true) {
            menu();
        }
    }

    public static void menu() {
        System.out.println();
        System.out.println("NEWLOAN - Loan a book to a borrower.");
        System.out.println("RETURNBOOK - End the loan of a book.");
        System.out.println();
        System.out.println("ADDBOOK - Add a new book to the system.");
        System.out.println("EDITBOOK - Edit the details of a book.");
        System.out.println("DELETEBOOK - Delete a book from the system.");
        System.out.println();
        System.out.println("NEWBORROWER - Add a new borrower to the system.");
        System.out.println("EDITBORROWER - Edit the details of a borrower.");
        System.out.println("VIEWBORROWER - View the books loaned by a borrower.");
        System.out.println("DELETEBORROWER - Delete a borrower from the system.");
        System.out.println();
        System.out.println("VIEWLOANED - Display books being loaned only.");
        System.out.println("VIEWBOOKS - Display all books in alphabetically with their details.");
        System.out.println("VIEWNAMES - Display all borrowers' names alphabetically.");
        System.out.println("VIEWDUETODAY - Display all books that are due to be returned today.");
        System.out.println("VIEWLATE - Display all books that are overdue to be returned.");

        String choice = input.next();

        switch (choice.toUpperCase()) {
            case "NEWLOAN":
                newLoan();
                break;
            case "RETURNBOOK":
                endLoan();
                break;
            case "ADDBOOK": {
                System.out.print("Book's name: ");
                String name = input.next();
                System.out.print("Book's ISBN number: ");
                String ISBNNumber = input.next();
                Books book = new Books(name, ISBNNumber);
                listOfBooks.add(book);
                emptyConsole("Book \"" + name + "\" with ISBN number " + ISBNNumber + " added.");
                break;
            }
            case "EDITBOOK": {
                while (true) {
                    System.out.println("Edit book's name or ISBN number? (NAME/ISBN)");
                    String userChoice = input.next();
                    if (userChoice.equalsIgnoreCase("NAME")) {
                        editBookName();
                        break;
                    } else if (userChoice.equalsIgnoreCase("ISBN")) {
                        editBookISBN();
                        break;
                    } else {
                        System.out.println("Error, please re-enter");
                    }
                }
                break;
            }
            case "DELETEBOOK":
                deleteBook();
                break;
            case "NEWBORROWER": {
                System.out.print("Borrower's name: ");
                input.nextLine();
                String name = input.nextLine();
//                input.nextLine();
                System.out.print("Borrower's phone number: ");
                String phoneNumber = input.next();
                Borrowers borrower = new Borrowers(name, phoneNumber);
                listOfBorrowers.add(borrower);
                emptyConsole("New borrower created.\nName: " + borrower.getName() + "\nPhone Number: " + borrower.getPhoneNumber());
                break;
            }
            case "EDITBORROWER": {
                while (true) {
                    System.out.println("Edit borrower's name, or phone number? (NAME/PN)");
                    String userChoice = input.next();
                    if (userChoice.equalsIgnoreCase("NAME")) {
                        editBorrowerName();
                        break;
                    } else if (userChoice.equalsIgnoreCase("PN")) {
                        editBorrowerPN();
                        break;
                    } else {
                        System.out.println("Error, please re-enter");
                        input.next();
                    }
                }
                break;
            }
            case "VIEWBORROWER":
                viewBorrower();
                break;
            case "DELETEBORROWER":
                deleteBorrower();
                break;
            case "VIEWLOANED": {
                ArrayList<Books> booksBorrowed = new ArrayList<>();
                for (Books book : listOfBooks) {
                    if (book.getCurrentBorrower() != null) {
                        booksBorrowed.add(book);
                    }
                }
                if (booksBorrowed.isEmpty()) {
                    emptyConsole("No books have been borrowed.");
                } else {
                    emptyConsole(null);
                    System.out.format("%20s : %20s : %20s", "Book name", "Borrower", "Return date");
                    System.out.println();
                    for (Books book : booksBorrowed) {
                        System.out.format("%20s : %20s : %20s", book.getName(), book.getCurrentBorrower().getName(), book.getReturnDate().format(dt));
                        System.out.println();
                    }
                }
                break;
            }
            case "VIEWBOOKS": {
                emptyConsole(null);
                Collections.sort(listOfBooks, Books.bookNameComparator);
                System.out.format("%20s : %20s : %20s", "Book name", "Borrower", "ISBN Number");
                System.out.println();
                for (Books book : listOfBooks) {
                    if (book.getCurrentBorrower() == null) {
                        System.out.format("%20s : %20s : %20s", book.getName(), "Unloaned", book.getISBNNumber());
                        System.out.println();
                    } else {
                        System.out.format("%20s : %20s : %20s", book.getName(), book.getCurrentBorrower().getName(), book.getISBNNumber());
                        System.out.println();
                    }
                }
                break;
            }
            case "VIEWNAMES": {
                emptyConsole(null);
                Collections.sort(listOfBorrowers, Borrowers.borrowerNameComparator);
                for (Borrowers borrower : listOfBorrowers) {
                    System.out.println(borrower.getName());
                }
                break;
            }
            case "VIEWDUETODAY": {
                ArrayList<Books> booksDue = new ArrayList<>();
                for (Books book : listOfBooks) {
                    if (DAYS.between(book.getReturnDate(), LocalDate.now()) == 0) {
                        booksDue.add(book);
                    }
                }
                if (booksDue.isEmpty()) {
                    emptyConsole("No books are due today.");
                } else {
                    emptyConsole(null);
                    System.out.format("%20s : %20s : %20s", "Book name", "Borrower", "Return date");
                    System.out.println();
                    for (Books book : booksDue) {
                        System.out.format("%20s : %20s : %20s", book.getName(), book.getCurrentBorrower().getName(), book.getReturnDate().format(dt));
                        System.out.println();
                    }
                }
                break;
            }
            case "VIEWLATE": {
                ArrayList<Books> booksLate = new ArrayList<>();
                for (Books book : listOfBooks) {
                    if (DAYS.between(book.getReturnDate(), LocalDate.now()) >= 1) {
                        booksLate.add(book);
                    }
                }
                if (booksLate.isEmpty()) {
                    emptyConsole("No books are late.");
                } else {
                    emptyConsole(null);
                    System.out.format("%20s : %20s : %20s : %20s", "Book name", "Borrower", "Return date", "Days overdue");
                    System.out.println();
                    for (Books book : booksLate) {
                        System.out.format("%20s : %20s : %20s : %20s", book.getName(), book.getCurrentBorrower().getName(), book.getReturnDate().format(dt), DAYS.between(book.getReturnDate(), LocalDate.now()));
                        System.out.println();
                    }
                }
                break;
            }
            case "X": {
                saveBooksArrayList();
                getBooksArrayList();
            }
        }
    }

    public static void editBookName() { // enter books isbn, enter new name, name changed from __ to __
        Books bookToEdit = null;
        while (true) {
            boolean found = false;
            System.out.print("Book's old name: ");
            String oldName = input.next();

            for (Books book : listOfBooks) {
                if (book.getName().equalsIgnoreCase(oldName)) {
                    bookToEdit = book;
                    found = true;
                    System.out.println("Book found");
                    break;
                }
            }

            if (found == false) {
                System.out.println("Book not found, please re-enter.");
            } else {
                break;
            }
        }

        System.out.println("Book's new name: ");
        String newName = input.next();

        String oldName = bookToEdit.getName();
        int locationOfBook = listOfBooks.indexOf(bookToEdit);
        listOfBooks.get(locationOfBook).setName(newName);
        emptyConsole("Name changed from \"" + oldName + "\" to \"" + listOfBooks.get(locationOfBook).getName() + "\"");
    }

    public static void editBookISBN() {
        Books bookToEdit = null;
        while (true) {
            boolean found = false;
            System.out.print("Book's old ISBN number: ");
            String oldISBN = input.next();

            for (Books book : listOfBooks) {
                System.out.println("isbn : " + book.getISBNNumber());
                if (book.getISBNNumber().equalsIgnoreCase(oldISBN)) {
                    bookToEdit = book;
                    found = true;
                    System.out.println("Book found");
                    break;
                }
            }

            if (found == false) {
                System.out.println("Book not found, please re-enter.");
            } else {
                break;
            }
        }

        System.out.print("Book's new ISBN number: ");
        String newISBN = input.next();

        String oldISBN = bookToEdit.getISBNNumber();
        int locationOfBook = listOfBooks.indexOf(bookToEdit);
        listOfBooks.get(locationOfBook).setISBNNumber(newISBN);
        emptyConsole("ISBN changed from \"" + oldISBN + "\" to \"" + listOfBooks.get(locationOfBook).getISBNNumber() + "\"");
    }

    public static void deleteBook() {
        Books bookToDelete = null;
        boolean found = false;
        while (true) {
            System.out.print("Enter the book to delete's name: ");
            String bookName = input.next();
            for (Books book : listOfBooks) {
                if (book.getName().equalsIgnoreCase(bookName)) {
                    bookToDelete = book;
                    found = true;
                    break;
                }
            }
            if (found == false) {
                System.out.println("Book not found, please re-enter.");
            } else {
                break;
            }
        }

        if (bookToDelete.getCurrentBorrower() != null) {
            for (Borrowers b : listOfBorrowers) {
                b.removeBooksBorrowed(bookToDelete);
            }
        }

        listOfBooks.remove(bookToDelete);
        emptyConsole("Book " + bookToDelete.getName() + " deleted.");
    }

    public static void editBorrowerName() {
        Borrowers borrowerToEdit = null;
        while (true) {
            boolean found = false;
            System.out.print("Borrower's old name: ");
            String oldName = input.next();

            for (Borrowers borrower : listOfBorrowers) {
                if (borrower.getName().equalsIgnoreCase(oldName)) {
                    borrowerToEdit = borrower;
                    found = true;
                    break;
                }
            }

            if (found == false) {
                System.out.println("Borrower not found, please re-enter.");
            } else {
                break;
            }
        }

        System.out.print("Borrower's new name: ");
        String newName = input.next();

        String oldName = borrowerToEdit.getName();

        int indexOfBorrower = listOfBorrowers.indexOf(borrowerToEdit);
        listOfBorrowers.get(indexOfBorrower).setName(newName);

        emptyConsole("Name changed from \"" + oldName + "\" to \"" + listOfBorrowers.get(indexOfBorrower).getName() + "\"");
    }

    public static void editBorrowerPN() {
        Borrowers borrowerToEdit = null;
        while (true) {
            boolean found = false;
            System.out.print("Borrower's name: ");
            String name = input.next();

            for (Borrowers borrower : listOfBorrowers) {
                if (borrower.getName().equalsIgnoreCase(name)) {
                    borrowerToEdit = borrower;
                    found = true;
                    break;
                }
            }

            if (found == false) {
                System.out.println("Borrower not found, please re-enter.");
            } else {
                break;
            }
        }

        System.out.print("Borrower's new phone number: ");
        String newNumber = input.next();

        String oldNumber = borrowerToEdit.getPhoneNumber();

        int indexOfBorrower = listOfBorrowers.indexOf(borrowerToEdit);
        listOfBorrowers.get(indexOfBorrower).setPhoneNumber(newNumber);

        emptyConsole(borrowerToEdit.getName() + "'s phone number changed from \"" + oldNumber + "\" to \"" + listOfBorrowers.get(indexOfBorrower).getPhoneNumber() + "\"");
    }

    public static void viewBorrower() {
        Borrowers borrowerToView = null;
        while (true) {
            boolean found = false;
            System.out.print("Enter the borrower's name: ");
            String borrowersName = input.next();

            for (Borrowers borrower : listOfBorrowers) {
                if (borrower.getName().equalsIgnoreCase(borrowersName)) {
                    borrowerToView = borrower;
                    found = true;
                    break;
                }
            }

            if (found == false) {
                System.out.println("Borrower not found, please re-enter.");
            } else {
                break;
            }
        }
        if (borrowerToView.getBooksBorrowed().isEmpty()) {
            System.out.println("This borrower has not borrowed any books.");
        } else {
            System.out.println("This borrower has borrowed: ");
            for (Books book : borrowerToView.getBooksBorrowed()) {
                System.out.println(book.getName());
            }
        }
    }

    public static void deleteBorrower() {
        Borrowers borrowerToDelete = null;
        boolean found = false;
        while (true) {
            System.out.print("Enter the borrower to delete's name: ");
            String borrowersName = input.next();
            for (Borrowers borrower : listOfBorrowers) {
                if (borrower.getName().equalsIgnoreCase(borrowersName)) {
                    borrowerToDelete = borrower;
                    found = true;
                    break;
                }
            }
            if (found == false) {
                System.out.println("Borrower not found, please re-enter.");
            } else {
                break;
            }
        }

        listOfBorrowers.remove(borrowerToDelete);
        emptyConsole("Book " + borrowerToDelete.getName() + " deleted.");
    }

    public static void newLoan() {
        boolean found = false;
        String bookName;
        Borrowers borrowerLoaningBook = null;
        Books bookToLoan = null;

        while (true) {
            System.out.print("Enter the book to loan's name: ");
            bookName = input.next();
            for (Books book : listOfBooks) {
                if (book.getName().equalsIgnoreCase(bookName)) {
                    bookToLoan = book;
                    found = true;
                    break;
                }
            }
            if (found == false) {
                System.out.println("Book not found, please re-enter.");
            } else {
                break;
            }
        }

        boolean found2 = false;
        while (true) {
            System.out.print("Enter the borrower's name: ");
            String borrowersName = input.next();
            for (Borrowers borrower : listOfBorrowers) {
                if (borrower.getName().equalsIgnoreCase(borrowersName)) {
                    borrowerLoaningBook = borrower;
                    found2 = true;
                    break;
                }
            }
            if (found2 == false) {
                while (true) {
                    System.out.println("Borrower not found. Re-enter or add borrower? (RE/ADD)");
                    String choice = input.next();
                    if (choice.equalsIgnoreCase("R")) {
                        break;
                    } else if (choice.equalsIgnoreCase("ADD")) {
                        System.out.print("Enter the borrower's phone number: ");
                        String phoneNumber = input.next();
                        Borrowers borrower = new Borrowers(borrowersName, phoneNumber);
                        listOfBorrowers.add(borrower);
                        break;
                    }
                }
            } else {
                break;
            }
        }

        bookToLoan.setReturnDate(LocalDate.now().plusWeeks(3));

        for (Books book : listOfBooks) {
            if (book.equals(bookToLoan)) {
                book.setCurrentBorrower(borrowerLoaningBook);
                emptyConsole(bookToLoan.getName() + " has been loaned to " + borrowerLoaningBook.getName() + " for 3 weeks.");
            }
        }
        for (Borrowers borrower : listOfBorrowers) {
            if (borrower.equals(borrowerLoaningBook)) {
                borrower.addBooksBorrowed(bookToLoan);
            }
        }

    }

    public static void endLoan() {
        Borrowers borrowerEndingLoan = null;
        boolean found = false;
        while (true) {
            System.out.print("Enter the borrower who is returning a book: ");
            String borrowersName = input.next();
            for (Borrowers borrower : listOfBorrowers) {
                if (borrower.getName().equalsIgnoreCase(borrowersName)) {
                    borrowerEndingLoan = borrower;
                    found = true;
                    break;
                }
            }
            if (found == false) {
                System.out.println("Borrower not found, please re-enter.");
            } else {
                break;
            }
        }

        ArrayList<Books> borrowedBooks = borrowerEndingLoan.getBooksBorrowed();

        if (borrowedBooks.isEmpty()) {
            emptyConsole("This borrower has no books loaned.");
        } else {
            for (int book = 0; book < borrowedBooks.size(); book++) {
                System.out.println((book + 1) + ". " + borrowedBooks.get(book).getName());
            }
            Books bookToReturn;
            while (true) {
                try {
                    System.out.println("Which book is the borrower returning? (enter the number)");
                    int location = input.nextInt() - 1;
                    bookToReturn = borrowedBooks.get(location);
                    break;
                } catch (Exception e) {
                    System.out.println("Error, please re-enter.");
                    input.next();
                }
            }
            int location = listOfBooks.indexOf(bookToReturn);
            listOfBooks.get(location).setCurrentBorrower(null);
            emptyConsole("Book \"" + bookToReturn.getName() + "\" returned.");
        }

    }

    private static void emptyConsole(String textToOutput) {
        try {
            Robot robot = new Robot();
            //press
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            //release
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (java.awt.AWTException e) {
        }

        // wait 0.1 seconds to prevent text being outputted from being cleared
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        // print text that has been passed
        if (textToOutput != null) {
            System.out.println(textToOutput);
        }
    }

    private static void getBooksArrayList() {
        ArrayList<Books> newBooks = new ArrayList<>();
        ArrayList<ArrayList<String>> booksAsString = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Books.txt"));
            String bookDetails;
            while ((bookDetails = br.readLine()) != null) {
                bookDetails = bookDetails.substring(1, (bookDetails.length() - 1)); // remove brackets
                ArrayList<String> bookAsList = new ArrayList(Arrays.asList(bookDetails.split(", ")));
                booksAsString.add(bookAsList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found exception");
        } catch (IOException e) {
            System.out.println("io exception");
        }
    }

    private static void saveBooksArrayList() {
        try {
            FileWriter fw = new FileWriter("Books.txt"); // open to overwrite
            fw.write(""); // clear the file
            fw = new FileWriter("Books.txt", true); // open to append
            for (Books book : listOfBooks) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(book.getName());
                temp.add(book.getISBNNumber());
                temp.add(Long.toString(book.getCurrentBorrower().getIDNumber()));
                temp.add(book.getReturnDate().toString());
                fw.write(temp.toString() + "\n");
                System.out.println("Adding: " + temp.toString() + " to \"Books.txt\" file.");
//                        + "," + book.getISBNNumber() + "," + book.getCurrentBorrower());
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            //file not found, create file?
        }

    }

}
