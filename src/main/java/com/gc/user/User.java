package com.gc.user;

public class User {

	
    private final Integer userId;
    
    //@NotBlank
	//@Size(min = 2, max = 18, message = "User name must be between 2 and 18")
    private final String userName;

    public User(Integer userId,
                   String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}


