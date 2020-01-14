package entities.model;

public class Product {
	
	private String name;
	private Double price;
	private Double quality;
	private Integer deliveryTime;
	
	public Product(String name, Double price, Double quality, Integer deliveryTime) {
		super();
		this.name = name;
		this.price = price;
		this.quality = quality;
		this.deliveryTime = deliveryTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuality() {
		return quality;
	}

	public void setQuality(Double quality) {
		this.quality = quality;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", quality=" + quality + ", deliveryTime=" + deliveryTime
				+ "]";
	}
	
}
