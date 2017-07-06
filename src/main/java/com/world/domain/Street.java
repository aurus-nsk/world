package com.world.domain;

public class Street {
	
	private int id;
	private String name;
	private int extent;

	public Street(int id, String name, int extent) {
		this.id = id;
		this.name = name;
		this.extent = extent;
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

	public int getExtent() {
		return extent;
	}

	public void setExtent(int extent) {
		this.extent = extent;
	}

	@Override
	public String toString() {
		return "Street [id=" + id + ", name=" + name + ", extent=" + extent + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + extent;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Street other = (Street) obj;
		if (extent != other.extent)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}