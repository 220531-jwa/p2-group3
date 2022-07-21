package dev.group3.model;

public class Dog {
	
	private int id;
	private String user_email;
	private Boolean status;
	private String dog_name;
	private String breed;
	private int dog_age;
	private boolean vaccinated;
	
	public Dog() {}
	
	public Dog(int id, String user_email, Boolean status, String dog_name, String breed, int dog_age,
			boolean vaccinated) {
		super();
		this.id = id;
		this.user_email = user_email;
		this.status = status;
		this.dog_name = dog_name;
		this.breed = breed;
		this.dog_age = dog_age;
		this.vaccinated = vaccinated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDog_name() {
		return dog_name;
	}

	public void setDog_name(String dog_name) {
		this.dog_name = dog_name;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public int getDog_age() {
		return dog_age;
	}

	public void setDog_age(int dog_age) {
		this.dog_age = dog_age;
	}

	public boolean isVaccinated() {
		return vaccinated;
	}

	public void setVaccinated(boolean vaccinated) {
		this.vaccinated = vaccinated;
	}

	@Override
	public String toString() {
		return "Dog [id=" + id + ", user_email=" + user_email + ", status=" + status + ", dog_name=" + dog_name
				+ ", breed=" + breed + ", dog_age=" + dog_age + ", vaccinated=" + vaccinated + "]";
	}
	
	
}
