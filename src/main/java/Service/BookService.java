package Service;

import com.lib_for_mentor.lib_for_mentor.Entity.Book;
import com.lib_for_mentor.lib_for_mentor.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookCrudRepository) {
        this.bookRepository = bookCrudRepository;
    }

    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void updateBookInfo(int id, String description, int pages, int published_year, String title) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(description);
        book.setPages(pages);
        book.setPublished_year(published_year);
        book.setTitle(title);
        bookRepository.save(book);
    }

    @Transactional
    public Book deleteById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        bookRepository.delete(book);
        return book;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> findAllByTitle(String title) {
        List<Book> Books = bookRepository.findAllByTitle(title);

        if (Books.isEmpty()) {
            throw new RuntimeException("Book with title [" + title + "] not found");
        }

        return Books;
    }
}
