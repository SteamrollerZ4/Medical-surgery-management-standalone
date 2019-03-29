package edu.surgery.logic;


//This exception is throw if the requested username is not found
public class UserNotFound extends Exception {
	public UserNotFound() {
		super("Username not found");
	}
}
