package librarybookingsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Borrowers extends Person implements Serializable {

    private ArrayList<Books> booksBorrowed = new ArrayList<>();
    private long IDNumber;

    public Borrowers(String name, String phoneNumber) {
        super(name, phoneNumber);
        Random rand = new Random(2147483647);
        this.IDNumber = rand.nextLong();
    }
    
    public Borrowers(String name, String phoneNumber, long IDNumber) {
        this(name,phoneNumber);
        this.IDNumber = IDNumber;
    }

    public static Comparator<Borrowers> borrowerNameComparator = new Comparator<Borrowers>() {
        @Override
        public int compare(Borrowers b1, Borrowers b2) {
            String borrowerName1 = b1.getName().toUpperCase();
            String borrowerName2 = b2.getName().toUpperCase();

            return borrowerName1.compareTo(borrowerName2);
        }
    };

    public ArrayList<Books> getBooksBorrowed() {
        return booksBorrowed;
    }

    public void setBooksBorrowed(ArrayList<Books> bookBorrowed) {
        this.booksBorrowed = bookBorrowed;
    }

    public void addBooksBorrowed(Books book) {
        this.booksBorrowed.add(book);
    }

    public void removeBooksBorrowed(Books book) {
        this.booksBorrowed.remove(book);
    }

    public long getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(long IDNumber) {
        this.IDNumber = IDNumber;
    }
}
