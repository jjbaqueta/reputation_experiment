package environments;

import java.util.logging.Logger;

import entities.model.buyers.Buyer;
import entities.model.sellers.Seller;
import entities.services.MarketFacade;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

public class Market extends Environment 
{
	/** Static attributes: */
	
	public static int numberBuyingRequest;
	
	public static Seller[] sellers;
	public static Buyer[] buyers;
	
	/** Class attributes: */

	private Logger logger = Logger.getLogger("Log messages for Class: " + Market.class.getName());;
	
	@Override
	public void init(String[] args) 
	{
		super.init(args);

		try 
		{	
			Experiment experiment = new Experiment();
			
//			experiment.startAutomaticallyExperiment();
			
//			experiment.startExperiment1();
			
//			experiment.startExperiment2();
			
			experiment.startExperiment3();
			
			
			experiment.showBuyersAndSellersInformations();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

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
				buyers[index].setEndOfActivities();		
			}
		}
		else if(action.equals(Literal.parseLiteral("check_status(end)")))
		{
			if(MarketFacade.isMarketEnd())
			{
				showFinalReport();
				Files.writeReputations();
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
				Files.writeSaleStatus(seller, time);
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
		System.out.println(" -> Number of purchase requests: " + numberBuyingRequest + "\n");
		
		System.out.println("Informations about sellers:");
		
		for(Seller seller : sellers)
			System.out.println(" ->" + seller.getName() +"("+ seller.getMyType() +")" + " {sales made: " + seller.getSaleMadeCount() + "; sales lost: " + seller.getSaleLostCount() + "}");
		
		System.out.println("\nInformations about buyers:");
		
		for(Buyer buyer : buyers)
			System.out.println(" ->" + buyer.getName() +"("+ buyer.getMyType() +")" + " {purchases completed: " + buyer.getPurchaseCompleteCount() + "; purchases aborted: " + buyer.getPurchaseAbortedCount() + "}");
	}
}