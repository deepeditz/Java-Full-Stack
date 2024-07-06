package com.daily.expense.Bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Adjust table name as per your database schema
public class UserBean extends BaseBean {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNo;
    private String roleName;
    private long roleId;

    // Getters and setters for all attributes

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    // @Override
    // public String getKey() {
    //     return String.valueOf(id);
    // }

    // @Override
    // public String getValue() {
    //     return firstName + " " + lastName;
    // }

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