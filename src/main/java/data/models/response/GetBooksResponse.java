package data.models.response;

import java.util.List;

public class GetBooksResponse{
	private List<BooksItem> books;

	public void setBooks(List<BooksItem> books){
		this.books = books;
	}

	public List<BooksItem> getBooks(){
		return books;
	}
}