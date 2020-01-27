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

public class Market extends Environment{
	
	// Constants:
	private static final int BAD_SELLERS = 4;
	private static final int GOOD_SELLERS = 0;
	private static final int NEUTRAL_SELLERS = 0;
	private static final int ITEMS_SOLD_BY_SELLER = 10;

	private static final int PRICE_BUYERS = 0;
	private static final int QUALITY_BUYERS = 0;
	private static final int DELIVERY_BUYERS = 0;
	private static final int GENERAL_BUYERS = 4;
	private static final int ORDERS_BY_BUYER = 5;
			
	// Attributes;
	private List<Product> availableProducts;
    private Logger logger;
    
	public static Buyer[] buyers;
	public static Seller[] sellers;
    
    /** Called before the MAS execution with the args informed in .mas2j */
    
    @Override
    public void init(String[] args) {
        
    	super.init(args);
    	
		try {
			
			// Creating products
			availableProducts = ProductsFacade.generateCompleteListOfProducts();
			
			// Initializing the criteria used on reputation model 
			ReputationModel.insertNewCriteria(CriteriaType.PRICE.getValue(), Double.class);
			ReputationModel.insertNewCriteria(CriteriaType.QUALITY.getValue(), Double.class);
			ReputationModel.insertNewCriteria(CriteriaType.DELIVERY.getValue(), Integer.class);
			
			// Initializing sellers		
			int j = 0;
			sellers = new Seller[BAD_SELLERS + NEUTRAL_SELLERS + GOOD_SELLERS];
			
			for(int i = 0; i < BAD_SELLERS; i++)
			{
				sellers[j] = SellerFactory.getSeller(SellerType.BAD, "seller" + (j + 1), ITEMS_SOLD_BY_SELLER, availableProducts);
				j++;
			}
	
			for(int i = 0; i < NEUTRAL_SELLERS; i++)
			{
				sellers[j] = SellerFactory.getSeller(SellerType.NEUTRAL, "seller" + (j + 1), ITEMS_SOLD_BY_SELLER, availableProducts);
				j++;
			}
			
			for(int i = 0; i < GOOD_SELLERS; i++)
			{
				sellers[j] = SellerFactory.getSeller(SellerType.GOOD, "seller" + (j + 1), ITEMS_SOLD_BY_SELLER, availableProducts);
				j++;
			}
			
			// Initializing buyers	
			j = 0;
			buyers = new Buyer[PRICE_BUYERS + QUALITY_BUYERS + DELIVERY_BUYERS + GENERAL_BUYERS];
			
			for(int i = 0; i < PRICE_BUYERS; i++)
			{
				buyers[j] = BuyerFactory.getBuyer(BuyerType.PRICE_ORIENTED, "buyer" + (j + 1), ORDERS_BY_BUYER, availableProducts);
				j++;
			}
			
			for(int i = 0; i < QUALITY_BUYERS; i++)
			{
				buyers[j] = BuyerFactory.getBuyer(BuyerType.QUALITY_ORIENTED, "buyer" + (j + 1), ORDERS_BY_BUYER, availableProducts);
				j++;
			}
			
			for(int i = 0; i < DELIVERY_BUYERS; i++)
			{
				buyers[j] = BuyerFactory.getBuyer(BuyerType.DELIVERY_ORIENTED, "buyer" + (j + 1), ORDERS_BY_BUYER, availableProducts);
				j++;
			}
			
			Random rand = new Random();
			for(int i = 0; i < GENERAL_BUYERS; i++)
			{
				buyers[j] = new GeneralOrientedBuyer(ORDERS_BY_BUYER, availableProducts, "buyer" + (j + 1), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
				j++;
			}
			
			// When only one agent is defined, there is not a id after the agent's name
			if(sellers.length == 1)
				sellers[0].setName("seller");
			
			if(buyers.length == 1)
				buyers[0].setName("buyer");	
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
        //Loading logger to show messages
        logger = Logger.getLogger("Log messages for Class: " + Market.class.getName());
        
        //Showing informations for debugging
		ProductsFacade.showProducts(availableProducts);
		MarketFacade.showBuyersAndSellersInformations();
        
		System.out.println("\n--------------------- STARTING JASON APPLICATION --------------------\n");
        updatePercepts();
    }
    
    //This method defines action points which triggers perceptions within environment
    public void updatePercepts()
    {
    	for(Buyer buyer : buyers)
    	{
    		clearPercepts(buyer.getName());
    		addPercept(buyer.getName(), buyer.getProductsToBuy().pop());
    	}
    	
    	for(Seller seller : sellers)
    	{
    		clearPercepts(seller.getName());
    		
    		for(Literal literal : seller.getProductsAsLiteral())
    		{
    			addPercept(seller.getName(), literal);
    		}
    	}
    }

    //This method call some specific actions implemented in Java according to some conditions
    @Override
    public boolean executeAction(String agName, Structure action) {
        
    	logger.info("agent: " + agName + " is executing: " + action);
    	
    	// Continue buying until the order list is empty
    	if(action.equals(Literal.parseLiteral("buy(finished)")))
    	{
    		int index = MarketFacade.getBuyerIdFrom(agName);
    		
    		if(buyers[index].hasBuyingDesire())
    		{
    			clearPercepts(agName);
        		addPercept(agName, buyers[index].getProductsToBuy().pop());
    		}
    		else
    		{
    			clearPercepts(agName);
        		addPercept(agName, Literal.parseLiteral("buy(nothing)"));
        		logger.info("agent: " + agName + " finished his negotiations");
    		}
    	}	
	    else 
	    {
	        logger.warning("executing: " + action + ", but not implemented!");
	    }
       
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
