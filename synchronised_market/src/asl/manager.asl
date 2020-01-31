/*
 * This agent is responsible for the initialization and management of environment
 */

/* Plans ***************************************************/

/*
 * Create a set of seller from a list of names
 */ 
+create(sellers)
	<-	actions.managerInitializeSellers(Sellers_Names);
		!create_sellers(Sellers_Names).

/*
 * Create a set of buyers from a list of names
 */ 
+create(buyers)
	<-	actions.managerInitializeBuyers(Buyers_Names);
		!create_buyers(Buyers_Names).

/*
 * Create a seller at time  
 */
+!create_sellers([seller(Name)|T])
	<-	.create_agent(Name, "seller.asl");
   		!create_sellers(T).

/*
 * Nothing to do, list of sellers is empty
 */
+!create_sellers([]).

/*
 * Create a buyer at time  
 */
+!create_buyers([buyer(Name)|T])
	<-	.create_agent(Name, "buyer.asl");
   		!create_buyers(T).

/*
 * Nothing to do, list of buyers is empty
 */
+!create_buyers([]).