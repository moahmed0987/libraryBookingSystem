package librarybookingsystem;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class Books implements Serializable{

    private String name, ISBNNumber;
    private Borrowers currentBorrower;
    private LocalDate returnDate;

    public Books(String name, Borrowers currentBorrower, String ISBNNumber, LocalDate returnDate) {
        this.name = name;
        this.currentBorrower = currentBorrower;
        this.ISBNNumber = ISBNNumber;
        this.returnDate = returnDate;
    }
    
    public static Comparator<Books> bookNameComparator = new Comparator<Books>() {

        
	public int compare(Books b1, Books b2) {
	   String bookName1 = b1.getName().toUpperCase();
	   String bookName2 = b2.getName().toUpperCase();

	   return bookName1.compareTo(bookName2);
    }};

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Books(String name, String ISBNNumber) {
        this(name, null, ISBNNumber, null);
    }

    public String getISBNNumber() {
        return ISBNNumber;
    }

    public void setISBNNumber(String ISBNNumber) {
        this.ISBNNumber = ISBNNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Borrowers getCurrentBorrower() {
        return currentBorrower;
    }

    public void setCurrentBorrower(Borrowers currentBorrower) {
        this.currentBorrower = currentBorrower;
    }
}
