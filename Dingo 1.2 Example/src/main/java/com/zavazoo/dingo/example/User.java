package com.zavazoo.dingo.example;
 
public class User {

	private Name name;
	private Gender gender;
	private int age;
	private boolean verified;

	public enum Gender { MALE, FEMALE };

	public class Name {

		private String first;
		private String last;

		public String getFirst() { return first; }
		public String getLast() { return last; }

		public void setFirst(String first) { this.first = first; }
		public void setLast(String last) { this.last = last; }

	}

	public Name getName() { return name; }
	public Gender getGender() { return gender; }
	public int getAge() { return age; }
	public boolean isVerified() { return verified; }

	public void setName(Name name) { this.name = name; }
	public void setGender(Gender gender) { this.gender = gender; }
	public void setAge(int age) { this.age = age; }
	public void setVerified(boolean verified) { this.verified = verified; }

}