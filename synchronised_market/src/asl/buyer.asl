/* Initial rules ********************************************/

update_nb_offers(CNPId)
	:- 	nb_participants(CNPId, NP) &				// Retrieves the number of participants for CNPId
		.count(propose(CNPId,_)[source(_)], NO) &	// Counts the number of proposals received until now 
		NP = NO.									// Updates the amount participants considering only those that sent a proposal

/* Initial perceptions *************************************/

/* Initial goals *******************************************/

!register.

/* Plans ***************************************************/

// The buyer is added on net as an initiator
+!register 
	<- .df_register(initiator).

// End of negotiations
+buy(nothing).

// Start the CNP
+buy(Id, P_name)
	<-	
		// Waiting for participants
		.print("REQUEST CNPId: ", Id, ", PRODUCT: ", P_name);
		.print("Waiting for sellers ...");
      	.wait(2000); 
      
	    // Searching for sellers
      	+cnp_state(Id, propose);   						// Updates the status of the CNP (protocol's state) 
      	.df_search("participant", Sellers);				// Loads a list with the possible participants (Sellers)
      	+nb_participants(Id, .length(Sellers));			// Updates the amount of participants
      	.send(Sellers, tell, cfp(Id, P_name));			// Sends a call of proposal (CFP) to each participant found
      	.print("Sending the calls for proposal ...");
      	.print("Participants notified: ", Sellers);
      	
		// Waiting for all proposals arrives or by 4 seconds has passed
      	.wait(update_nb_offers(Id), 4000, _);
      	!contract(Id).

// This plan needs to be atomic to not accept proposals while contracting
@lc1[atomic]
+!contract(CNPId)
 	:	cnp_state(CNPId, propose)						// Checks if the state of CNP is in propose
	<-	-cnp_state(CNPId,_);							// Updating the state of CNP
      	+cnp_state(CNPId, contract);
      	
      	// Loading all proposals sent
      	.findall(offer(Offer, Ag), propose(CNPId, Offer)[source(Ag)], Offers);
      	
      	// Constraint: If exist at least one offer, the plan must continue
      	Offers \== []; 									
      	.print("Offers recevied: ", Offers);     	
      	
      	// Take decision: evaluate all proposals and choose one seller to make a deal
      	.my_name(N);
      	actions.buyerEvaluateOffer(Offers, N, Ag_winner);
      	.print("The best offer came from: ", Ag_winner);
      	.print("Notifying participants about decision ...");
      	      	
		// Notifying all participants about decision taken
		+winner(CNPId, Ag_winner);
      	!announce_result(CNPId, Offers, Ag_winner).

// Nothing to do, the current phase is not 'propose'
@lc2 +!contract(_).

// The execution of the contract (plan: @lc1) has failed
-!contract(CNPId)
	<-	.print("CNP ",CNPId," has failed! - There were not proposals for request: ", CNPId);
		-cnp_state(CNPId,_);
		+cnp_state(CNPId, finished);
		buy(finished).

// Announce to the winner
+!announce_result(CNPId,[offer(_, Ag)|T], Ag_winner)
	:	Ag == Ag_winner
	<-	.send(Ag, tell, accept_proposal(CNPId));
		!announce_result(CNPId, T, Ag_winner).
      
// Announce to others
+!announce_result(CNPId,[offer(_, Ag)|T], Ag_winner)
	:	Ag \== Ag_winner
	<-	.send(Ag, tell, reject_proposal(CNPId));
		!announce_result(CNPId, T, Ag_winner).

+!announce_result(_,[],_).

+delivered(CNPId, NewOffer)[source(Ag)]
	:	cnp_state(CNPId, contract)
	<-	!evaluate(CNPId, NewOffer, Ag);
		-cnp_state(CNPId,_);
		+cnp_state(CNPId, finished);
		buy(finished).
		
+!evaluate(CNPId, NewOffer, Seller)
	:	propose(CNPId, Offer)[source(Seller)]
	<-	.my_name(N);
		actions.buyerEvaluateContract(N, Seller, Offer, NewOffer, Rating);
		//.send(Seller, tell, Rating);
		//+rating(Seller, Rating)
		.print("evaluating ...").
//call rep model


/******************** Plans for debugging **********************/

+!print_list([H|T])
	<-	.print("-> ", H);
		!print_list(T).
		
+!print_list([]).