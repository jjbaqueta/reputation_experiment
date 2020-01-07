package environments;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import entities.model.Buyer;
import enums.Product;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.environment.Environment;

public class Market extends Environment{
	
	private List<Product> products = new ArrayList<Product>();	//list of products that may sold by a seller
    private Logger logger;  									//simple logger to show log messages
    
    //Calling model entities
    Buyer buyer = new Buyer();
    
    //Actions
    Term end = Literal.parseLiteral("finish(neg)");
    
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
    
    //this method defines action points which triggers perceptions within environment
    public void updatePercepts()
    {
    	clearPercepts("seller");
    	clearPercepts("buyer");
    	
    	//if the buyer doesn't have buying at the moment and is open to negotiation, he still can accept new offers by items
    	if(!buyer.checkNegotiationStatus())
    	{
    		addPercept("buyer", Literal.parseLiteral("my_wish(buy, "+ buyer.whatToBuy(products) +")"));
    	}
    }

    //this method call some specific actions implemented in Java according to some conditions
    @Override
    public boolean executeAction(String agName, Structure action) {
        
    	logger.info("agent: " + agName + " is executing: " + action);
    	
    	//if the buyer doesn't receive at least one offer at the end of each turn, the simulation ends due to lack of offers
    	if(action.equals(end))
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
