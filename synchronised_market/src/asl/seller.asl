/* Initial rules ********************************************/

format_products_on_list(Products) 
	:-	.findall(p(Name, Price, Quality, Delivery), sell(Name, Price, Quality, Delivery), Products).

find_product_by_name(P_name, Products)
	:-	.findall(p(P_name, Price, Quality, Delivery), sell(P_name, Price, Quality, Delivery), Products) & 
		Products \== [].

/* Initial perceptions *************************************/

/* Initial goals *******************************************/

!start_activities.

/* Plans ***************************************************/

+!start_activities
	<-	!register.	

// The seller is added on net as a participant.
+!register
	:	format_products_on_list(Products) 
	<-	.df_register("participant");
    	.df_subscribe("initiator");
    	.print("sell: ", Products).

// Answering a buying request
@c1 
+cfp(CNPId, P_name)[source(Ag)]
	:	provider(Ag, "initiator") &
		find_product_by_name(P_name, Products)
   <-	.nth(0, Products, Offer);		 				// Get the first element of list
   		+proposal(CNPId, P_name, Offer); 				// Remember my proposal
      	.send(Ag, tell, propose(CNPId, Offer));
      	.print("Proposal sent to ", Ag, " {proposal: ", Offer, "}").

@r1 +accept_proposal(CNPId)
   :  proposal(CNPId, P_name, Offer)
   <- .print("I won CNP", CNPId).
      
      // Do the task and report to initiator - Send the product and fulfill the agreement

@r2 +reject_proposal(CNPId)
   <- .print("I lost CNP ",CNPId);
      -proposal(CNPId,_,_). 							// Clear memory

/******************** Plans of support **********************/

// Checks whether a given product is in the seller's belief base
+!has_product(P_name)
	:	find_product_by_name(P_name, Products)
	<-	.print(Products).
	
+!has_product(P_name)
	:	not find_product_by_name(P_name, Products)
	<-	.print("product not found!").
	
/******************** Plans for debugging **********************/

// Shows the list of product when verbose is activated
+!print_products
	:	format_products_on_list(List)
	<-	.printf("---------------- (INFO) -----------------");
		.print("List of products (size: ",.length(List),") - format{p(name, price, quality, delivery time)}:");
		!show_products_prices(List).

+!print_products.

+!show_products_prices([])
	<-	.printf("------------------------------------------\n").

+!show_products_prices([H|T])
	:	H \== []
	<-	.print("-> ", H);
		!show_products_prices(T).	