package environments;
import java.util.List;
import java.util.logging.Logger;

import entities.model.Buyer;
import entities.model.Product;
import entities.model.Seller;
import entities.services.ProductsFacade;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

public class Market extends Environment{
	
	// Parameters for experiments:
	public static final int NUMBER_OF_SELLERS = 15;
	public static final int NUMBER_OF_BUYERS = 1;
	public static final int AMOUNT_OF_ITEMS_SELL = 3;		//at most 5
	public static final int AMOUNT_OF_ITEMS_BUY = 1;
	
	// Attributes;
	private Buyer[] buyers;
	private Seller[] sellers;
	private List<Product> availableProducts;
    private Logger logger;
    
    /** Called before the MAS execution with the args informed in .mas2j */
    
    @Override
    public void init(String[] args) {
        
    	super.init(args);
    	
		try {
			// Creating products
			availableProducts = ProductsFacade.generateCompleteListOfProducts();
			ProductsFacade.showProducts(availableProducts);
			
			// Initializing the agents
			buyers = new Buyer[NUMBER_OF_BUYERS];
			sellers = new Seller[NUMBER_OF_SELLERS];
			
			for(int i = 0; i < NUMBER_OF_BUYERS; i++)
			{
				buyers[i] = new Buyer("buyer" + i);
				buyers[i].addItemsToBuy(AMOUNT_OF_ITEMS_BUY, availableProducts);			
			}
			
			for(int i = 0; i < NUMBER_OF_SELLERS; i++)
			{
				sellers[i] = new Seller("seller" + (i + 1));
				sellers[i].setProductsForSale(AMOUNT_OF_ITEMS_SELL, availableProducts);
			}	
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
        //Loading logger to show messages
        logger = Logger.getLogger("Log messages for Class: " + Market.class.getName());
        
        updatePercepts();
    }
    
    //This method defines action points which triggers perceptions within environment
    public void updatePercepts()
    {
    	for(Buyer b: buyers)
    	{
    		clearPercepts(b.getName());
    		addPercept(b.getName(), b.getProductsToBuy().pop());
    	}
    	
    	for(int i = 0; i < NUMBER_OF_SELLERS; i++)
		{
    		clearPercepts(sellers[i].getName());
    		
    		for(Literal l : sellers[i].getProductsForSale())
    		{
//    			System.out.println(sellers[i].getName() + ", " + l);
    			addPercept(sellers[i].getName(), l);
    		}
		}	
    }

    //This method call some specific actions implemented in Java according to some conditions
    @Override
    public boolean executeAction(String agName, Structure action) {
        
    	logger.info("agent: " + agName + " is executing: " + action);
    	
//    	//The negotiations may finish when the buyer doesn't have more money or when there aren't offers
//    	if(action.equals(Literal.parseLiteral("finish(negotiations)")))
//    	{
//    		buyer.finishNegotiation();
//    	}	
//	    else {
//	        logger.warning("executing: " + action + ", but not implemented!");
//	    }
       
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
