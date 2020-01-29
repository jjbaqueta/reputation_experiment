package environments;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entities.model.Buyer;
import entities.model.GeneralOrientedBuyer;
import entities.model.Product;
import entities.model.Seller;
import entities.services.BuyerFactory;
import entities.services.MarketFacade;
import entities.services.ProductsFacade;
import entities.services.SellerFactory;
import enums.BuyerType;
import enums.CriteriaType;
import enums.SellerType;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import reputationModels.ReputationModel;

public class Market extends Environment 
{
	/** Constants (experiment parameters): */

	private static final int BAD_SELLERS = 4;
	private static final int GOOD_SELLERS = 0;
	private static final int NEUTRAL_SELLERS = 0;
	private static final int ITEMS_SOLD_BY_SELLER = 10;

	private static final int PRICE_BUYERS = 0;
	private static final int QUALITY_BUYERS = 0;
	private static final int DELIVERY_BUYERS = 0;
	private static final int GENERAL_BUYERS = 4;
	private static final int ORDERS_BY_BUYER = 5;

	/** Static attributes: */

	public static Seller[] sellers = new Seller[BAD_SELLERS + NEUTRAL_SELLERS + GOOD_SELLERS];
	public static Buyer[] buyers = new Buyer[PRICE_BUYERS + QUALITY_BUYERS + DELIVERY_BUYERS + GENERAL_BUYERS];

	/** Class attributes: */

	private List<Product> availableProducts;
	private Logger logger;

	@Override
	public void init(String[] args) 
	{
		super.init(args);

		try 
		{
			// Defining the criteria used by model of reputation
			ReputationModel.insertNewCriteria(CriteriaType.PRICE.getValue(), Double.class);
			ReputationModel.insertNewCriteria(CriteriaType.QUALITY.getValue(), Double.class);
			ReputationModel.insertNewCriteria(CriteriaType.DELIVERY.getValue(), Integer.class);

			// Creating a complete list of products
			availableProducts = ProductsFacade.generateCompleteListOfProducts();

			// Creating a logger to show messages
			logger = Logger.getLogger("Log messages for Class: " + Market.class.getName());

			/** Initializing sellers: */
			/*
			 * The products sold by a seller are selected randomly from the available product list. 
			 * The kind of seller (bad, neutral or good) is associated to his cheating profile:
			 *  -> Bad sellers always lie in an agreement.
			 *  -> Neutral sellers may lie in an agreement.
			 *  -> Good sellers never lie in an agreement.
			 */
			int j = 0;

			for (int i = 0; i < BAD_SELLERS; i++)
				sellers[j++] = SellerFactory.getSeller(SellerType.BAD, "seller" + j, ITEMS_SOLD_BY_SELLER, availableProducts);

			for (int i = 0; i < NEUTRAL_SELLERS; i++)
				sellers[j++] = SellerFactory.getSeller(SellerType.NEUTRAL, "seller" + j, ITEMS_SOLD_BY_SELLER, availableProducts);

			for (int i = 0; i < GOOD_SELLERS; i++)
				sellers[j++] = SellerFactory.getSeller(SellerType.GOOD, "seller" + j, ITEMS_SOLD_BY_SELLER, availableProducts);

			/** Initializing buyers: */
			/*
			 * Each buyer has his own shopping list, which is represented by a stack of perceptions about the products that will be purchased. 
			 * The kind of buyer (price oriented, quality oriented, delivery oriented or general) is associated to his buying preferences:
			 *  -> buyers oriented to price want to buy the cheapest products.
			 *  -> buyers oriented to quality want to buy the highest quality products.
			 *  -> buyers oriented to delivery want to buy with the best delivery time.
			 *  -> general buyers has its buying criteria (price, quality and delivery time) customized according to the necessity.
			 */
			Random rand = new Random();
			j = 0;

			for (int i = 0; i < PRICE_BUYERS; i++)
				buyers[j++] = BuyerFactory.getBuyer(BuyerType.PRICE_ORIENTED, "buyer" + j, ORDERS_BY_BUYER, availableProducts);

			for (int i = 0; i < QUALITY_BUYERS; i++)
				buyers[j++] = BuyerFactory.getBuyer(BuyerType.QUALITY_ORIENTED, "buyer" + j, ORDERS_BY_BUYER, availableProducts);

			for (int i = 0; i < DELIVERY_BUYERS; i++)
				buyers[j++] = BuyerFactory.getBuyer(BuyerType.DELIVERY_ORIENTED, "buyer" + j, ORDERS_BY_BUYER, availableProducts);

			for (int i = 0; i < GENERAL_BUYERS; i++)
				buyers[j++] = new GeneralOrientedBuyer("buyer" + j, rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), ORDERS_BY_BUYER, availableProducts);

			/** Case exist only one agent of each type: */

			if (sellers.length == 1)
				sellers[0].setName("seller");

			if (buyers.length == 1)
				buyers[0].setName("buyer");		
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		/** Informations for debug: */
		
		ProductsFacade.showProducts(availableProducts);
		MarketFacade.showBuyersAndSellersInformations();

		System.out.println("\n--------------------- STARTING JASON APPLICATION --------------------\n");
		updatePercepts();
	}

	//	Setting the initial perceptions
	public void updatePercepts() 
	{
		for (Buyer buyer : buyers) 
		{
			clearPercepts(buyer.getName());
			addPercept(buyer.getName(), buyer.getProductsToBuy().pop());
		}
		
		for (Seller seller : sellers) 
		{
			clearPercepts(seller.getName());

			for (Literal product : seller.getProductsAsLiteralList()) 
				addPercept(seller.getName(), product);
		}
	}

	// Defining the simple actions that may be performed by agents
	@Override
	public boolean executeAction(String agName, Structure action) 
	{
		logger.info("agent: " + agName + " is executing: " + action);

		if (action.equals(Literal.parseLiteral("purchase(finished)"))) 
		{
			int index = MarketFacade.getBuyerIdFrom(agName);
			clearPercepts(agName);

			if (buyers[index].hasBuyingDesire()) 
				addPercept(agName, buyers[index].getProductsToBuy().pop());
			else 
			{
				addPercept(agName, Literal.parseLiteral("buy(nothing)"));
				logger.info("The buyer: " + agName + " ended his purchases (-- CONCLUDED --)");
			}
		}	
		else 
			logger.warning("executing: " + action + ", but not implemented!");

		return true;
	}

	@Override
	public void stop() {
		super.stop();
	}
}