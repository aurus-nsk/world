package com.world.domain;

public class City {

		private int id;
		private String name;
		private double square;
		private int population;
		
		public City(int id, String name, double square, int population) {
			this.id = id;
			this.name = name;
			this.square = square;
			this.population = population;
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

		public double getSquare() {
			return square;
		}

		public void setSquare(double square) {
			this.square = square;
		}

		public int getPopulation() {
			return population;
		}

		public void setPopulation(int population) {
			this.population = population;
		}

		@Override
		public String toString() {
			return "City [id=" + id + ", name=" + name + ", square=" + square + ", population=" + population + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + population;
			long temp;
			temp = Double.doubleToLongBits(square);
			result = prime * result + (int) (temp ^ (temp >>> 32));
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
			City other = (City) obj;
			if (id != other.id)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (population != other.population)
				return false;
			if (Double.doubleToLongBits(square) != Double.doubleToLongBits(other.square))
				return false;
			return true;
		}
		
}