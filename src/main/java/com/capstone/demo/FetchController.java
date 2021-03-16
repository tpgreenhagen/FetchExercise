package com.capstone.demo;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class implements the request handling functionality.
 * Should be able to make requests with http://localhost:8080
 *'http://localhost:8080/fetch/ maps to everything in the class
 *'http://localhost:8080/fetch/add with payer, points, and timestamp value adds a new Transaction
 *'http://localhost:8080/fetch/get does not need any data and returns a list of all payers and their points
 *'http://localhost:8080/fetch/spend needs a points value and returns a list of balances changes
 * 
 * @author Tanner Greenhagen
 *
 */

@RestController
@RequestMapping("/fetch/")
public class FetchController {

	//List of payers and their net balances
	private Vector<Payer> payerList = new Vector<Payer>();
	
	//List of transactions
	private Vector<Transaction> transactionList = new Vector<Transaction>();
	
	/**
	 * Takes a Transaction as a parameter with date format yyyy-MM-ddTHH:mm:ssZ. Adds the 
	 * new transaction to a global list and then checks if the payer has been added to the
	 * system yet. If they have then the new points are added to that payer's existing balance.
	 * If the payer has yet to be added a new Payer object is created and added to the payer
	 * global list. The original transaction is returned as proof of success.
	 * 
	 * @param transaction
	 * @return
	 */
	@RequestMapping("add")
	public Transaction addPayer(@DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ") @RequestBody Transaction transaction){
		
		transactionList.add(transaction);
		
		//Flag for checking if payer has already been added
		boolean in = false;
		for(int i = 0 ; i <payerList.size();i++) {
			
			//Updates payer's balance if payer already in list
			if (payerList.get(i).getName().equals(transaction.getName())) {
				
				payerList.get(i).setBalance(payerList.get(i).getBalance()+transaction.getPoints());
				i = payerList.size();
				in = true;
			}
			
		}
		
		//Adds payer if not in list
		if (!in) {
			Payer payer = new Payer(transaction.getName(),transaction.getPoints());
			payerList.add(payer);
		}
		return transaction;
	}
	
	/**
	 * This method just returns a list of all payers and their balances
	 * 
	 * @return
	 */
	@RequestMapping("get")
	public Vector<Payer> getPayers(){
		return payerList;
	}
	
	/**
	 * This method takes a SpendRequest object as a parameter. It first
	 * sorts all of the transactions by date. Then calls the spending
	 * method which takes money out of accounts until the points have
	 * been spent. Then the global balances are adjusted based on which
	 * transactions had their money spent. The accounts and the amounts
	 * they are changed are returned. 
	 * 
	 * @param sr
	 * @return
	 */
	@RequestMapping("spend")
	public Vector<Payer> getPayers(@RequestBody SpendRequest sr){
		Vector<Payer> payersChanged = new Vector<Payer>();
		
		//Sorts based on timestamp
		Collections.sort(transactionList, new Comparator<Transaction>() {
			public int compare(Transaction T1, Transaction T2) {
				return T1.getDate().compareTo(T2.getDate());
			}
		});
		
		//SpendRequest should be greater than 0
		if(sr.getPoints()<=0) {
			return payersChanged;
		}
		
		spending(payersChanged,sr.getPoints());
		
		//Updates payers' balances
		for(int i = 0; i <payerList.size();i++) {
			for(int j = 0; j <payersChanged.size();j++) {
				if(payerList.get(i).getName().equals(payersChanged.get(j).getName())) {
					payerList.get(i).setBalance(payerList.get(i).getBalance()+payersChanged.get(j).getBalance());
					j = payersChanged.size();
				}
			}
		}
		
		return payersChanged;
	}
	
	/**
	 * This function takes a list of payers which will be the payers
	 * who have a change in their amount and the amount of points. 
	 * In order of date, transactions are used to pay for the points
	 * until the points needing spending is 0 or negative. If negative 
	 * some points are added back to the last used transaction. Payers
	 * and the amount changed are added to the payersChanged list based
	 * on if one of their transactions were used. Transactions that are
	 * zeroed are then deleted so they do not need to be checked again.
	 * 
	 * @param payersChanged
	 * @param points
	 */
	private void spending(Vector<Payer> payersChanged, int points) {
		
		int added = 0;
		int transactionsUsed = 0;
		
		//Trying to pay the points until there are no points left or no transactions left
		while(points > 0 && transactionsUsed < transactionList.size()) {
			
			points -= transactionList.get(transactionsUsed).getPoints();

			//If points is negative some money should be given back
			if(points < 0) {
				added = -1*points;
			}
			
			//Flag for if the current transaction's payer has been added to payersChanged
			boolean in = false;
			
			//Updates payers who have already been added to payersChanged
			for(int i = 0; i <payersChanged.size();i++) {
				if(transactionList.get(transactionsUsed).getName().equals(payersChanged.get(i).getName())) {
					in = true;
					payersChanged.get(i).setBalance(added+payersChanged.get(i).getBalance()-transactionList.get(transactionsUsed).getPoints());
				}
			}
			
			//Adds payers to payersChanged if they were not in it
			if(!in) {
				Payer payer = new Payer(transactionList.get(transactionsUsed).getName(),added-transactionList.get(transactionsUsed).getPoints());
				payersChanged.add(payer);
			}
			
			//Updates the transaction's points
			transactionList.get(transactionsUsed).setPoints(added);
			
			transactionsUsed++;
		}

		//Checks if all used transactions were used fully
		if (added!=0) {
			added=1;
		}

		//Removes used transactions
		for(int i = 0; i <transactionsUsed-added;i++) {
			transactionList.remove(0);
		}
	}
	
	
}
