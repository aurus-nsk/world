package com.world.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Organization {

	private int id;
	private String name;
	private City city;
	private Street street;
	private String homeNumber;
	private String scope;
	private List<Phone> phone = new ArrayList<Phone>();
	private String website;
	private Date dateUpdate;
	
	public Organization(int id, String name, City city, Street street, String homeNumber, String scope,  String website, Date dateUpdate) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.street = street;
		this.homeNumber = homeNumber;
		this.scope = scope;
		this.website = website;
		this.dateUpdate = dateUpdate;
	}
	
	public Organization() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public List<Phone> getPhone() {
		return phone;
	}

	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}

	public void addPhone(Phone phone) {
		this.phone.add(phone);
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", city=" + city + ", street=" + street + ", homeNumber="
				+ homeNumber + ", scope=" + scope + ", phone=" + phone + ", website=" + website + ", dateUpdate="
				+ dateUpdate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result + ((homeNumber == null) ? 0 : homeNumber.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (homeNumber == null) {
			if (other.homeNumber != null)
				return false;
		} else if (!homeNumber.equals(other.homeNumber))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		return true;
	}
	
}