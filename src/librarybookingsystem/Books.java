package librarybookingsystem;

import java.time.LocalDate;

public class Books {

    private String name, ISBNNumber;
    private Borrowers currentBorrower;
    private LocalDate returnDate;

    public Books(String name, Borrowers currentBorrower, String ISBNNumber, LocalDate returnDate) {
        this.name = name;
        this.currentBorrower = currentBorrower;
        this.ISBNNumber = ISBNNumber;
        this.returnDate = returnDate;
    }

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
