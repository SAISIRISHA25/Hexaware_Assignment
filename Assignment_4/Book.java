package Assignment_5;

class Book {
    int bookId;
    String title;
    String author;
    boolean isAvailable;

    Book(int id, String t, String a) {
        this.bookId = id;
        this.title = t;
        this.author = a;
        this.isAvailable = true; // Initially Available
    }
}