// gets the price for the product,
// the price value is defined according to file Product.java in the package 'enums'.

/* Initial rules */

/* Initial perceptions */

verbose(true).
//verbose(false).

!start_activities.

/* Plans */

+!start_activities
//	:	sell(List)
	<-	//!set_products_prices(List);
		!print_products.

//+!set_products_prices([]).
//
//+!set_products_prices([H|T])
//	:	H \== []
//	<-	!set_price(H);
//		!set_products_prices(T).
//		
//+!set_price(p(Name, Price))
//	<-	entities.services.sellerDefineOfferPrice(Name, Price).

/******************** Plans for testing **********************/

+!print_products
	:	verbose(X) & X == true & sell(List)
	<-	.print("--- (INFO) List of products - format{product(name, price)}:");
		!show_products_prices(List).

+!show_products_prices([]).

+!show_products_prices([H|T])
	:	H \== []
	<-	.print("------ ", H);
		!show_products_prices(T).
		
+!print_products.
			
//+!register 
//	<-	.df_register("participant");
//    	.df_subscribe("initiator").
//
//// answer to call for buying request
//@c1 +cfp(CNPId, Product)[source(Buyer)]
//	:	provider(Buyer, "initiator") &
//		price(Task,Offer)
//   <- +proposal(CNPId,Task,Offer); // remember my proposal
//      .send(A,tell,propose(CNPId,Offer)).
//
//@r1 +accept_proposal(CNPId)
//   :  proposal(CNPId,Task,Offer)
//   <- .print("My proposal '",Offer,"' won CNP ",CNPId, " for ",Task,"!").
//      // do the task and report to initiator
//
//@r2 +reject_proposal(CNPId)
//   <- .print("I lost CNP ",CNPId, ".");
//      -proposal(CNPId,_,_). // clear memory







//// Agent seller in project synchronised_market
//
///* Initial beliefs */
////NONE
//
///* Initial goals */
////NONE
//
///* Initial beliefs and rules */
////check buying power
//
///* Plans */
//
//+?request(Product)[source(buyer)]
//	:	sell(A, B, C) & (A == Product | B == Product | C == Product) 
//	<-	entities.services.sellerDefineOfferPrice(Product, Price);
//		.print("Price defined for: ", Product, " is:", Price);
//		.send(buyer, tell, offer(Product, Price));
//		.print("Offer sent to: ", buyer);
//		!hangOn.
//
//+deal(R)[source(buyer)]
//	: 	R == "accept"
//	<-	.print("The product will be sent to buyer as soon as possible!");
//		!hangOn.
//	
//+deal(R)[source(buyer)]
//	: 	R == "reject"
//	<-	!hangOn.
//
//+!hangOn
//	:	true
//	<-	true.