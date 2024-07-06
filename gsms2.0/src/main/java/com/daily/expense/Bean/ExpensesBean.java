package com.daily.expense.Bean;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "expenses") // Adjust table name as per your database schema
public class ExpensesBean extends BaseBean {

    private String title;
    private LocalDateTime date;
    private long price;
    private String description;
    private long userId;
    private String email;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getkey'");
	}

	@Override
	public String getvalue() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getvalue'");
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
	}

}