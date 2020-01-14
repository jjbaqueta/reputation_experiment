package entities.model;

import java.util.List;

import jason.asSyntax.Literal;

public interface BuyerContract {

	/*
	 * This method must select a product from product list and set this one as the buyer's desire
	 * @param products List of products available to selection
	 * @return belief about the product desired, example: 'buy(product_name)'
	 */
	public Literal whatToBuy(List<Product> products);
}
