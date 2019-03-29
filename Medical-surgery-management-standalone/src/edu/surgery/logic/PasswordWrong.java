package edu.surgery.logic;

//This exception is thrown if entered password is wrong
public class PasswordWrong extends Exception {

	public PasswordWrong() {
		super("Password mismatch");
	}
}
