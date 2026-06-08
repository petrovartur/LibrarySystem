package service;

import model.Book;
import model.BorrowRecord;
import model.Reader;

import java.time.LocalDate;
import java.util.*;

public class LibraryService {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Reader> readers = new ArrayList<>();
    private ArrayList<BorrowRecord> records = new ArrayList<>();

    private HashMap<Integer, Book> bookMap = new HashMap<>();
    private HashMap<Integer, Reader> readerMap = new HashMap<>();

    public void addBook(Book book) {
        books.add(book);
        bookMap.put(book.getId(), book);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
        readerMap.put(reader.getId(), reader);
    }

    public void borrowBook(int readerId, int bookId) {

        Book book = bookMap.get(bookId);
        Reader reader = readerMap.get(readerId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (reader == null) {
            System.out.println("Reader not found.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is not available.");
            return;
        }

        book.setAvailable(false);

        records.add(new BorrowRecord(
                bookId,
                readerId,
                LocalDate.now().toString(),
                null
        ));

        System.out.println("Book issued successfully.");
    }

    public void returnBook(int readerId, int bookId) {

        Book book = bookMap.get(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        for (BorrowRecord record : records) {

            if (record.getBookId() == bookId
                    && record.getReaderId() == readerId
                    && record.getReturnDate() == null) {

                record.setReturnDate(LocalDate.now().toString());
                book.setAvailable(true);

                System.out.println("Book returned.");
                return;
            }
        }

        System.out.println("Borrow record not found.");
    }

    public List<Book> getReaderBooks(int readerId) {

        List<Book> result = new ArrayList<>();

        for (BorrowRecord record : records) {

            if (record.getReaderId() == readerId
                    && record.getReturnDate() == null) {

                result.add(bookMap.get(record.getBookId()));
            }
        }

        return result;
    }

    public List<Book> searchBooks(String keyword) {

        List<Book> result = new ArrayList<>();

        keyword = keyword.toLowerCase();

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(keyword)
                    || book.getAuthor().toLowerCase().contains(keyword)) {

                result.add(book);
            }
        }

        return result;
    }

    public List<Book> getAvailableBooks() {

        List<Book> result = new ArrayList<>();

        for (Book book : books) {

            if (book.isAvailable()) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Reader> getReadersWithBooks() {

        Set<Integer> readerIds = new HashSet<>();

        for (BorrowRecord record : records) {

            if (record.getReturnDate() == null) {
                readerIds.add(record.getReaderId());
            }
        }

        List<Reader> result = new ArrayList<>();

        for (Integer id : readerIds) {
            result.add(readerMap.get(id));
        }

        return result;
    }

    public List<Reader> getTopReaders() {

        HashMap<Integer, Integer> countMap = new HashMap<>();

        for (BorrowRecord record : records) {

            countMap.put(
                    record.getReaderId(),
                    countMap.getOrDefault(record.getReaderId(), 0) + 1
            );
        }

        List<Map.Entry<Integer, Integer>> list =
                new ArrayList<>(countMap.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        List<Reader> result = new ArrayList<>();

        int limit = Math.min(3, list.size());

        for (int i = 0; i < limit; i++) {
            result.add(readerMap.get(list.get(i).getKey()));
        }

        return result;
    }
}
