package environments;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import entities.model.Buyer;
import entities.model.Seller;
import enums.NegotiationStatus;
import enums.Product;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

public class Market extends Environment{
	
	private List<Product> products = new ArrayList<Product>();	//List of products that may sold by a seller
    private Logger logger;  									//Simple logger to show log messages
    
    //Calling model entities
    Buyer buyer = new Buyer();
    Seller seller = new Seller();
    
    //Creating an id generator for identify exclusively each product order
    AtomicInteger seqId = new AtomicInteger();
    
    /** Called before the MAS execution with the args informed in .mas2j */
    
    @Override
    public void init(String[] args) {
        
    	super.init(args);
        
    	//Loading products used in this experiment  	
    	products.addAll(Arrays.asList(
        		Product.TV, 
        		Product.DESKTOP, 
        		Product.NOTEBOOK, 
        		Product.SMARTPHONE, 
        		Product.TABLET)
        );
    	
        Product.showProducts(products);
        
        //Loading logger to show messages
        logger = Logger.getLogger("Log messages for Class: " + Market.class.getName());
        
        updatePercepts();
    }
    
    //This method defines action points which triggers perceptions within environment
    public void updatePercepts()
    {
    	clearPercepts("seller");
    	clearPercepts("buyer");
    	
//    	System.out.println(seller.whatToSell(3, products));
    	addPercept("store_A", seller.whatToSell(3, products));
    	addPercept("store_B", seller.whatToSell(3, products));
    	
    	//Defining the initiator for negotiations 
    	//addPercept("buyer", Literal.parseLiteral("!register"));
    	
    	//Defining the providers for negotiations 
    	//addPercept("seller", Literal.parseLiteral("!register"));
    	
    	//If the buyer is open to negotiation, a new product order is generate
    	if(buyer.getNegotiationStatus() == NegotiationStatus.OPEN)
    	{
    		//addPercept("buyer", Literal.parseLiteral("!buy(" + seqId.incrementAndGet() + "," + buyer.whatToBuy(products) +")"));
    	}
    }

    //This method call some specific actions implemented in Java according to some conditions
    @Override
    public boolean executeAction(String agName, Structure action) {
        
    	logger.info("agent: " + agName + " is executing: " + action);
    	
    	//The negotiations may finish when the buyer doesn't have more money or when there aren't offers
    	if(action.equals(Literal.parseLiteral("finish(negotiations)")))
    	{
    		buyer.finishNegotiation();
    	}	
	    else {
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
