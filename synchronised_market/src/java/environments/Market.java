package environments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entities.model.Product;
import entities.model.buyers.Buyer;
import entities.model.buyers.GeneralOrientedBuyer;
import entities.model.sellers.Seller;
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
import reputationModels.Reputation;
import reputationModels.ReputationModel;

public class Market extends Environment 
{
	/** Constants (experiment parameters): */

//	private static final int BAD_SELLERS = 0;
//	private static final int GOOD_SELLERS = 0;
//	private static final int NEUTRAL_SELLERS = 0;
//	private static final int GENERAL_SELLERS = 3;
//	private static final int ITEMS_SOLD_BY_SELLER = 5;	//at most 25
//
//	private static final int PRICE_BUYERS = 5;
//	private static final int QUALITY_BUYERS = 5;
//	private static final int DELIVERY_BUYERS = 5;
//	private static final int GENERAL_BUYERS = 5;
//	private static final int ORDERS_BY_BUYER = 5;
	
	
	private static final int BAD_SELLERS = 0;
	private static final int GOOD_SELLERS = 1;
	private static final int GENERAL_SELLERS = 0;
	private static final int ITEMS_SOLD_BY_SELLER = 25;	//at most 25

	private static final int PRICE_BUYERS = 1;
	private static final int QUALITY_BUYERS = 0;
	private static final int DELIVERY_BUYERS = 0;
	private static final int GENERAL_BUYERS = 0;
	private static final int ORDERS_BY_BUYER = 5;
	
	public static final int TOTAL_RESQUESTS = (PRICE_BUYERS + QUALITY_BUYERS + DELIVERY_BUYERS + GENERAL_BUYERS) * ORDERS_BY_BUYER;

	/** This file is used to save the sale informations for posterior analysis */
	
	public static File fileSales = new File("sale.txt");
	public static File fileRep = new File("rep.txt");
	public static File fileImg = new File("img.txt");
	
	/** Static attributes: */

	public static Seller[] sellers = new Seller[BAD_SELLERS + GOOD_SELLERS + GENERAL_SELLERS];
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
			// Deleting the 'sale.txt' file, if it exists
			if(fileSales.exists())
				fileSales.delete();
			
			if(fileRep.exists())
				fileRep.delete();
			
			if(fileImg.exists())
				fileImg.delete();

			
			// Defining the criteria used by model of reputation
			ReputationModel.insertNewCriteria(CriteriaType.PRICE.getValue(), Double.class);
			ReputationModel.insertNewCriteria(CriteriaType.QUALITY.getValue(), Double.class);
			ReputationModel.insertNewCriteria(CriteriaType.DELIVERY.getValue(), Integer.class);

			// Creating a complete list of products
//			availableProducts = ProductsFacade.generateCompleteListOfProducts();
			availableProducts = ProductsFacade.generateListOfTVs();
			
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
				sellers[j++] = SellerFactory.getSeller(SellerType.BAD, "seller" + j, ITEMS_SOLD_BY_SELLER, 0.5, 0.2, 0.2, availableProducts);

			for (int i = 0; i < GOOD_SELLERS; i++)
				sellers[j++] = SellerFactory.getSeller(SellerType.GOOD, "seller" + j, ITEMS_SOLD_BY_SELLER, 0.2, 0.0, 0.0, availableProducts);
			
			for (int i = 0; i < GENERAL_SELLERS; i++)
				sellers[j++] = SellerFactory.getSeller(SellerType.GENERAL, "seller" + j, ITEMS_SOLD_BY_SELLER, 0.0, 0.0, 0.0, availableProducts);
			
//			/************************************
//			 *  Creating a full customized seller
//			 ************************************/
//			
//			Seller s1 = SellerFactory.getSeller(SellerType.GENERAL, "seller" + j, ITEMS_SOLD_BY_SELLER, 0.0, 0.0, 0.0, availableProducts);
//			Product[] s1Products =  (Product[]) s1.getProductsForSale().toArray();
//			
//			s1Products[0].setProductBehaviors(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS), 
//											  BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS),
//											  BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
//			
//			s1Products[1].setProductBehaviors(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS), 
//					  						  BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS),
//					  						  BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
//			
//			s1Products[2].setProductBehaviors(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS), 
//					  						  BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS),
//					  						  BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.TOTAL_RESQUESTS));
//			
//			sellers[j++] = s1;

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
			
			
			// Writing in the output file the initial sale state of each seller
			
			long time = System.currentTimeMillis();
			
			for(Seller seller : sellers)
				writeSaleStatus(seller, time);
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
		clearPercepts("manager");
		addPercept("manager", Literal.parseLiteral("create(sellers)"));
		addPercept("manager", Literal.parseLiteral("create(buyers)"));
		
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
				buyers[index].endActivities();		
			}
		}
		else if(action.equals(Literal.parseLiteral("check_status(end)")))
		{
			if(MarketFacade.isMarketEnd())
			{
				showFinalReport();
				writeReputations();
			}
		}
		else if(action.equals(Literal.parseLiteral("purchase(completed)")))
		{
			int index = MarketFacade.getBuyerIdFrom(agName);
			buyers[index].increasePurchaseCompleteCount();
		}
		else if (action.equals(Literal.parseLiteral("purchase(aborted)"))) 
		{
			int index = MarketFacade.getBuyerIdFrom(agName);
			buyers[index].increasePurchaseAbortedCount();
		}
		else if(action.equals(Literal.parseLiteral("lost(sale)")))
		{
			int index = MarketFacade.getSellerIdFrom(agName);
			sellers[index].increaseSaleLost();
		}
		else if(action.equals(Literal.parseLiteral("made(sale)")))
		{	
			int index = MarketFacade.getSellerIdFrom(agName);
			sellers[index].increaseSaleMade();
			
			long time = System.currentTimeMillis();
			
			for(Seller seller : sellers)
				writeSaleStatus(seller, time);
		}
		else 
			logger.warning("executing: " + action + ", but not implemented!");

		return true;
	}

	@Override
	public void stop() 
	{
		super.stop();
	}
	
	/*
	 * This method shows informations about buyers and sellers after the end of negotiations 
	 */
	public void showFinalReport() 
	{	
		System.out.println("\n---------------------- FINAL REPORT --------------------------");
		System.out.println(" -> Number of purchase requests: " + TOTAL_RESQUESTS + "\n");
		
		System.out.println("Informations about sellers:");
		
		for(Seller seller : sellers)
			System.out.println(" ->" + seller.getName() +"("+ seller.getMyType() +")" + " {sales made: " + seller.getSaleMadeCount() + "; sales lost: " + seller.getSaleLostCount() + "}");
		
		System.out.println("\nInformations about buyers:");
		
		for(Buyer buyer : buyers)
			System.out.println(" ->" + buyer.getName() +"("+ buyer.getMyType() +")" + " {purchases completed: " + buyer.getPurchaseCompleteCount() + "; purchases aborted: " + buyer.getPurchaseAbortedCount() + "}");
	}
	
	/*
	 * This method writes in the output file 'sales.txt' the current sale state of each seller
	 * @param seller represents the seller who will be write his sale state
	 * @param time current time, it is used to sort the writing events
	 */
	public void writeSaleStatus(Seller seller, long time)
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileSales, true));
			writer.append(seller.getName() + ";" + seller.getMyType() + ";" + time + ";" + seller.getSaleMadeCount() +"\n");			     
		    writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * This method writes in the output file 'sales.txt' the current sale state of each seller
	 * @param seller represents the seller who will be write his sale state
	 * @param time current time, it is used to sort the writing events
	 */
	public void writeReputations()
	{
		try 
		{		
			BufferedWriter writer = new BufferedWriter(new FileWriter(Market.fileRep, true));
			
			int maxSize = 0;
			long maxTime = 0;
			
			// Getting the size of the largest list
			for(Seller seller : sellers)
			{
				int currentSize = seller.getReputations().size();
				
				if(maxSize < currentSize)
				{
					maxSize = currentSize;
					maxTime = seller.getReputations().get(seller.getReputations().size() - 1).getTime();
				}
			}
			
			// Writing reputation in file
			for(Seller seller : sellers)
			{		
				for(Reputation rep : seller.getReputations())
				{
					writer.append(rep.getAgent().getName() + ";" + 
								  rep.getTime() + ";" + 
								  rep.getReputationRatings().get(CriteriaType.PRICE.getValue()) + ";" +
							      rep.getReputationRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
							      rep.getReputationRatings().get(CriteriaType.DELIVERY.getValue()) + ";" +
							      rep.getReliabilityRatings().get(CriteriaType.PRICE.getValue()) + ";" +
							      rep.getReliabilityRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
							      rep.getReliabilityRatings().get(CriteriaType.DELIVERY.getValue()) + "\n");
				}
				
				if(seller.getReputations().size() < maxSize)
				{
					Reputation lastRep = seller.getReputations().get(seller.getReputations().size() - 1);
						
					writer.append(lastRep.getAgent().getName() + ";" + 
								  maxTime + ";" + 
								  lastRep.getReputationRatings().get(CriteriaType.PRICE.getValue()) + ";" +
								  lastRep.getReputationRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
								  lastRep.getReputationRatings().get(CriteriaType.DELIVERY.getValue()) + ";" +
								  lastRep.getReliabilityRatings().get(CriteriaType.PRICE.getValue()) + ";" +
								  lastRep.getReliabilityRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
								  lastRep.getReliabilityRatings().get(CriteriaType.DELIVERY.getValue()) + "\n");
				}
			}
		    writer.close();    
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}