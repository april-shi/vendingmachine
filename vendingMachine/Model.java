//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : vendingMachine.Model
//
// Author         : Richard E. Pattis
//                  Computer Science Department
//                  Carnegie Mellon University
//                  5000 Forbes Avenue
//                  Pittsburgh, PA 15213-3891
//                  e-mail: pattis@cs.cmu.edu
//
// Maintainer     : Author
//
//
// Description:
//
//   The Model for the VendingMachine package implements the guts of the
// vending machine: it responds to presses of buttons created by the
// Conroller (deposit, cancel, buy), and tells the View when it needs
// to update its display (calling the update in view, which calls the
// accessor methods in this classes)
// 
//   Note that "no access modifier" means that the method is package
// friendly: this means the member is public to all other classes in
// the calculator package, but private elsewhere.
//
// Future Plans   : More Comments
//                  Increase price as stock goes down
//                  Decrease price if being outsold by competition
//                  Allow option to purchase even if full change cannot 
//                    be returned (purchaser pays a premium to quench thirst)
//                  Allow user to enter 2 x money and gamble: 1/2 time
//                    all money returned with product; 1/2 time no money and
//                    no product returned
//
// Program History:
//   9/20/01: R. Pattis - Operational for 15-100
//   2/10/02: R. Pattis - Fixed Bug in change-making method
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package vendingMachine;

import java.lang.Math;

import java.text.DecimalFormat;


public class Model {
	//Define fields (all instance variables)

	private int cokePrice = 50;
	private int pepsiPrice = 60;
	
	private View view;         // Model must tell View when to update itself
	
	private int    cokeLeft = 5;
	private int    pepsiLeft = 5;
	
	private int    quartersLeft, dimesLeft, nickelsLeft;

	private int quartersInputed, dimesInputed, nickelsInputed;

	public int refund;

	private String message;

	private int amount;
	
	//I defined about 10 more fields
	
	//Define constructor
	
	//Refer to the view (used to call update after each button press)
	public void addView(View v)
	{view = v;}
	
	//Define required methods: mutators (setters) and accessors (getters)

	public void addCoke() {

		cokeLeft++;

	}

	public void addPepsi() {

		pepsiLeft++;

	}
	String getDeposited() {

		int total = nickelsInputed * 5 + dimesInputed * 10 + quartersInputed * 25;

		refund += total;

		double number = (double) total / 100d;

		String formattedNumber = String.format("%.2f", number);

		return "" + formattedNumber;

	}

	String getMessage() {

		return message;

	}

	private void setMessage(String message) {

		this.message = message;

	}

	int getCokeLeft() {

		return cokeLeft;

	}

	int getPepsiLeft() {

		return pepsiLeft;

	}

	String getCokePrice() {

		double number = (double) cokePrice / 100d;

		String formattedNumber = String.format("%.2f", number);

		return "$" + formattedNumber;

	}

	String getPepsiPrice() {

		double number = (double) pepsiPrice / 100d;

		String formattedNumber = String.format("%.2f", number);

		return "$" + formattedNumber;

	}
	
	//Represent "interesting" state of vending machine
	public String toString()
	{
		return "Vending Machine State: \n" +
			"  Coke     Left      = " + cokeLeft     + "\n" +
			"  Pepsi    Left      = " + pepsiLeft    + "\n" +
			"  Quarters Left      = " + quartersLeft + "\n" +
			"  Dimes    Left      = " + dimesLeft    + "\n" +
			"  Nickels  Left      = " + nickelsLeft  + "\n";
		//Display any other instance variables that you declare too
	}
	
	//Define helper methods
	private int moneyLeft() {

		amount = nickelsLeft * 5 + dimesLeft * 10 + quartersLeft * 25;
		return amount;

	}

	public void cancel() {

		nickelsLeft = nickelsLeft - nickelsInputed;
		setMessage(nickelsInputed + "returned");
		nickelsInputed = 0;

		dimesLeft = dimesLeft - dimesInputed;
		setMessage(dimesInputed + "returned");
		dimesInputed = 0;

		quartersLeft = quartersLeft - quartersInputed;
		setMessage(quartersInputed + "returned");
		quartersInputed = 0;

		refund = 0;

		view.update();

		// is called when the cancel button is pressed.
		// It should terminate any pending sale and return whatever coins the user has deposited.
		// When the view is updated, it should explain this action in the message window at the bottom.

	}

	public void deposit(int amount) {

		if (amount == 5) {

			this.amount += 5;
			nickelsInputed += 1; //subset
			nickelsLeft += 1;
			setMessage("1 nickel received");


		} else if (amount == 10) {

			this.amount += 10;
			dimesInputed += 1;
			dimesLeft += 1;
			setMessage("1 dime received");


		} else if (amount == 25) {

			this.amount+=25;
			quartersInputed += 1;
			quartersLeft += 1;
			setMessage("1 quarter received");

		}

		view.update();

	}

	public void buy (String product) {

		System.out.println("buy: " + product);
		if (product == "pepsi") {
			if (pepsiPrice <= moneyLeft()) {
				//might have to zero out qarater, dime, smth
				//can buy
				quartersInputed = 0;
				nickelsInputed = 0;
				dimesInputed = 0;
                giveChange(pepsiPrice);

			} else {

				// cant buy
				getDeposited();
				setMessage("you don't have enough money");

			}

		}

		if (product == "coke") {
			if (cokeLeft <= moneyLeft()) {

				giveChange(cokePrice);

			} else {

				//can't buy
				getDeposited();
				setMessage("you don't have enough money");

			}

		}

		view.update();

  // is called when any of the buy buttons are pressed
  // (its parameter indicates which product is bought:
  // it is either "Pepsi" or "Coke"). When the view is updated,
  // it should explain this action in the message window at the bottom,
  // including how much change is returned. Note that when an item runs out, the button to buy that item become un-pressable.

	}

	//might have to change and create both to experiment both with
	private void giveChange(int purchasePrice){
		int nickelsReturned = 0;
		int dimessReturned = 0;
		int quartersReturned = 0;

		purchasePrice = 50;
		int remaining = purchasePrice;

		while (remaining > 0){
			if (quartersLeft  > 0){
				quartersLeft--;
				quartersReturned++;
				remaining -= 25;
			} else if (dimesLeft > 0){
				dimesLeft--;
				dimessReturned++;
				remaining -= 10;
			} else if (nickelsLeft > 0){
				nickelsLeft--;
				nickelsReturned++;
				remaining -= 5;
			}
		}

		if (quartersLeft < purchasePrice) {

			dimesLeft--;
			remaining -= 10;

		} else if (dimesLeft < purchasePrice) {

			nickelsLeft--;
			remaining -= 5;

		}

	}

}
