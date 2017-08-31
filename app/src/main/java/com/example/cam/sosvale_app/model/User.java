package com.example.cam.sosvale_app.model;

public class User {  
	
	private String username;
	private String email;
	private String password;
	private String fullname;
	private String cpf;
	private byte accountType = 1; // 0 = admin / 1 = user
	
	public User() {
		super();
	}
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	//FIXME
	public void setPassword(String password) {
		/*if (!User.passwordIsValid(password)) {
			throw new RuntimeException("Senha inválida. \n"
    				+ "Precisa conter: \n"
    				+ "De 8 à 32 caracteres;"
    				+ "Um número; \n"
    				+ "Uma letra minuscula; \n"
    				+ "Uma letra maiuscula; \n"
    				+ "Um caracter especial.");
		}*/
		this.password = password;
	}

	public String getFullName() {
		return fullname;
	}

	public void setFullName(String fullName) {
		this.fullname = fullName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		/*if (!cpfIsValid(cpf)) {
			throw new RuntimeException("Cpf inválido.");
		}*/
		this.cpf = cpf;
	}

	public byte getAccountType() {
		return accountType;
	}

	public void setAccountType(byte accountType) {
		this.accountType = accountType;
	}

	public boolean matchesEmail(String email, String password) {
		if (this.email.equalsIgnoreCase(email) && this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	public boolean matchesUserName(String username, String password) {
		if (this.username.equalsIgnoreCase(username) && this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	public boolean matchesCpf(String cpf, String password) {
		if (this.cpf.equalsIgnoreCase(cpf) && this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	public boolean matches(String cpf, String email, String username, String password) {
		if ((this.cpf.equalsIgnoreCase(cpf) ||
				this.email.equalsIgnoreCase(email) ||
				this.username.equalsIgnoreCase(username)) 
				&& this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	protected static boolean passwordIsValid(String password) {
		final int MIN_PASSWORD_LENGTH = 8;
		final int MAX_PASSWORD_LENGTH = 32;
		final String REQUIRED_CHARACTERS = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{"+ MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}";
		/*
		 * (?=.*[0-9]) a digit must occur at least once
		 * (?=.*[a-z]) a lower case letter must occur at least once
		 * (?=.*[A-Z]) an upper case letter must occur at least once 
		 * (?=.*[@#$%^&+=]) a special character must occur at least once 
		 * (?=\\S+$) no whitespace allowed in the entire string 
		 * .{0,10} at least 0 characters and no more than 10 characteres
		 */
		
		return (password.matches(REQUIRED_CHARACTERS));
		
	}
	
	// FIXME traduzir
	// FIXME ARRUMAR VALIDAÇÃO DE CPF
	private static String generateCPFControlNumber(String cpf) {
		String nineDigitsCpf = cpf.substring(0, 9);
		String c;
		int s = 0; 
		int tenthDigit = 0; 
		int eleventhDigit = 0;
		
		//Calcular 10º digito
		for (int x = 1; x < 10; x++) {
			c = String.valueOf(nineDigitsCpf.charAt(x -1));
			s += Integer.parseInt((c)) * x;
		}
		if (s % 11 != 10) {
			tenthDigit = s % 11;
		}
		nineDigitsCpf += String.valueOf(tenthDigit);
		
		//Calcular 11ª digito
		s = 0;
		for (int x = 1; x < 11; x++) {
			c = String.valueOf(nineDigitsCpf.charAt(x - 1));
			s += Integer.parseInt((c)) * x;
		}
		if (s % 11 != 10) {
			eleventhDigit = s % 11;
		}
		nineDigitsCpf += String.valueOf(eleventhDigit);		
		
		return nineDigitsCpf;
	}
	
	protected static boolean cpfIsValid (String cpf) {
		if (cpf.length() != 11) {
			return false;
		}
		String cpfGerado = User.generateCPFControlNumber(cpf);
		if (String.valueOf(cpf).equals(cpfGerado)) {
			return true;
		}
		return false;
	}
}
