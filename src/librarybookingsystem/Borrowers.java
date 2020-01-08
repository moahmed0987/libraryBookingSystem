package librarybookingsystem;

import java.util.ArrayList;

public class Borrowers extends Person {
    
    private ArrayList<Books> booksBorrowed = new ArrayList<>();

    public Borrowers(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

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
}