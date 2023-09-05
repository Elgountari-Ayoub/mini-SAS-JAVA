public class Book {
    private String isbn;
    private String title;
    private String author;


    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;

    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    //CRUD operations
    //Create a book
    public boolean store(){
    //if the book not stored
        return false;
    }

    public boolean update(){
        return false;
    }

    @Override
    public String toString (){
        return "Book info" +
                "\nTitle : " + this.title +
                "\nAuthor : " + this.author;
    }

    public static String generateReport(){
        return "Statistics on the Books";
    }
}
