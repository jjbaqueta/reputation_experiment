package entities.model;

import java.util.List;

import jason.asSyntax.Literal;

public class GoodSeller extends Seller{

	public GoodSeller(String name, int amountOfItems, List<Product> products) {
		super(name, amountOfItems, products);
	}

	@Override
	public Literal computeRealDeliveryConditions(Offer offerAgreement) {
		return Literal.parseLiteral("p(" + offerAgreement.getProduct() + "," + offerAgreement.getPrice() + "," + offerAgreement.getQuality() + "," + offerAgreement.getDeliveryTime() + ")");
	}

}
