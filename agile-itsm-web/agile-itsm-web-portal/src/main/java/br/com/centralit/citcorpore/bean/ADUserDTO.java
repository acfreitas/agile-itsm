package br.com.centralit.citcorpore.bean;

public class ADUserDTO {
	/** CN=Guy Thomas. Actually, this LDAP attribute can be made up from givenName joined to SN. */
	private String CN;
	/** What you see in Active Directory Users and Computers. Not to be confused with displayName on the Users property sheet. */
	private String description;
	/** displayName = Guy Thomas. If you script this property, be sure you understand which field you are configuring. DisplayName can be confused with CN or description. */
	private String displayName;
	/** DN is simply the most important LDAP attribute. CN=Jay Jamieson, OU= Newport,DC=cp,DC=com */
	private String DN;
	/** Firstname also called Christian name */
	private String givenName;
	/** Home Folder : connect. Tricky to configure */
	private String homeDrive;
	/** name = Guy Thomas. Exactly the same as CN. */
	private String name;
	/** Defines the Active Directory Schema category. For example, objectCategory = Person */
	private String objectCategory;
	/** objectClass = User. Also used for Computer, organizationalUnit, even container. Important top level container. */
	private String objectClass;
	/** Office! on the user's General property sheet */
	private String physicalDeliveryOfficeName;
	/** Roaming profile path: connect. Trick to set up */
	private String profilePath;
	/** This is a mandatory property, sAMAccountName = guyt. The old NT 4.0 logon name, must be unique in the domain. */
	private String sAMAccountName;
	/** SN = Thomas. This would be referred to as last name or surname. */
	private String SN;
	/** Used to disable an account. A value of 514 disables the account, while 512 makes the account ready for logon. */
	private String userAccountControl;
	/**
	 * userPrincipalName = guyt@CP.com Often abbreviated to UPN, and looks like an email address. Very useful for logging on especially in a large Forest. Note UPN must be unique in the forest.
	 */
	private String userPrincipalName;

	/** Here is where you set the MailStore */
	private String homeMDB;
	/**
	 * Legacy distinguished name for creating Contacts. In the following example, Guy Thomas is a Contact in the first administrative group of GUYDOMAIN: /o=GUYDOMAIN/ou=first administrative
	 * group/cn=Recipients/cn=Guy Thomas
	 */
	private String legacyExchangeDN;
	/** An easy, but important attribute. A simple SMTP address is all that is required billyn@ourdom.com */
	private String mail;
	/** Indicates that a contact is not a domain user. */
	private String mAPIRecipient;
	/** Normally this is the same value as the sAMAccountName, but could be different if you wished. Needed for mail enabled contacts. */
	private String mailNickname;
	/** Country or Region */
	private String c;
	/** Company or organization name */
	private String company;
	/** Useful category to fill in and use for filtering */
	private String department;
	/** Home Phone number, (Lots more phone LDAPs) */
	private String homephone;
	/** L = Location. City ( Maybe Office */
	private String l;
	/** Important, particularly for printers and computers. */
	private String location;
	/** Boss, manager */
	private String manager;
	/** Mobile Phone number */
	private String mobile;
	/** Organizational unit. See also DN */
	private String OU;
	/** Zip or post code */
	private String postalCode;
	/** State, Province or County */
	private String st;
	/** First line of address */
	private String streetAddress;
	/** Office Phone */
	private String telephoneNumber;
	
	private String server;
	
	private String idGrupo;

	private String ldapAtributo;
	
	private boolean ativo;

	public String getCN() {
		return CN;
	}

	public void setCN(String cN) {
		CN = cN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDN() {
		return DN;
	}

	public void setDN(String dN) {
		DN = dN;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getHomeDrive() {
		return homeDrive;
	}

	public void setHomeDrive(String homeDrive) {
		this.homeDrive = homeDrive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public String getPhysicalDeliveryOfficeName() {
		return physicalDeliveryOfficeName;
	}

	public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public String getsAMAccountName() {
		if (sAMAccountName != null) {
			return sAMAccountName.trim();
		}
		return sAMAccountName;
	}

	public void setsAMAccountName(String sAMAccountName) {
		if (sAMAccountName != null) {
			this.sAMAccountName = sAMAccountName.trim();
		} else {
			this.sAMAccountName = sAMAccountName;
		}
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getUserAccountControl() {
		return userAccountControl;
	}

	public void setUserAccountControl(String userAccountControl) {
		this.userAccountControl = userAccountControl;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public String getHomeMDB() {
		return homeMDB;
	}

	public void setHomeMDB(String homeMDB) {
		this.homeMDB = homeMDB;
	}

	public String getLegacyExchangeDN() {
		return legacyExchangeDN;
	}

	public void setLegacyExchangeDN(String legacyExchangeDN) {
		this.legacyExchangeDN = legacyExchangeDN;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getmAPIRecipient() {
		return mAPIRecipient;
	}

	public void setmAPIRecipient(String mAPIRecipient) {
		this.mAPIRecipient = mAPIRecipient;
	}

	public String getMailNickname() {
		return mailNickname;
	}

	public void setMailNickname(String mailNickname) {
		this.mailNickname = mailNickname;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOU() {
		return OU;
	}

	public void setOU(String oU) {
		OU = oU;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getTelephoneNumber() {
		if (telephoneNumber == null) {
			telephoneNumber = "Não disponível";
		}
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getLdapAtributo() {
		return ldapAtributo;
	}

	public void setLdapAtributo(String ldapAtributo) {
		this.ldapAtributo = ldapAtributo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}