package entities.model;

import java.util.List;

import jason.asSyntax.Literal;

public interface SellerContract {

	/*
	 * This method must select all products that will be sold by seller
	 * @param products List of products available to selection
	 * @return beliefs about the sold products, example: 'sell(product_attributes), ..'
	 */
	public List<Literal> whatToSell(int amountOfItems, List<Product> products);
	
	/*
	 * This method must computes the penalties on the contract fulfillment according to seller's type
	 * @param offer Offer represents an agreement defined between the buyer and seller
	 * @return a belief about the product delivery conditions, which may or not be according to initial offer
	 */
	public Literal computeRealDeliveryConditions(Offer offer);
	
}
