package com.helenusdb.index.suffix;

public class User {
	// The first name of the user.
	private String firstName;
	// The last name of the user.
	private String lastName;
	// The age of the user.
	private int age;
	// The city where the user lives.
	private String city;
	
	public User(String firstName, String lastName, int age, String city)
	{
		this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + age + ") - " + city;
	}
}
