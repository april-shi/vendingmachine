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


public class Model {
	//Define fields (all instance variables)

	private int cokePrice = 50;
	private int pepsiPrice = 60;
	
	private View view;         // Model must tell View when to update itself
	
	private int    cokeLeft = 5;
	private int    pepsiLeft = 5;
	
	private int    quartersLeft = 10 , dimesLeft = 10 , nickelsLeft = 10;

	private int quartersCurrentUser, dimesCurrentUser, nickelsCurrentUser;

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

		int total = nickelsCurrentUser * 5 + dimesCurrentUser * 10 + quartersCurrentUser * 25;

		double number = (double) total / 100d;

		String formattedNumber = String.format("%.2f", number);

		return "" + formattedNumber;

	}

	int getDepositedAsInt() {

		int total = nickelsCurrentUser * 5 + dimesCurrentUser * 10 + quartersCurrentUser * 25;

		return total;

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

		int unaccounted = 0;

		String msg = "";

		nickelsLeft = nickelsLeft - nickelsCurrentUser;
		if (nickelsCurrentUser == 1) {

			msg = nickelsCurrentUser + " nickel, ";
		} else {

			msg = nickelsCurrentUser + " nickels, ";
		}

		nickelsCurrentUser = 0;

		dimesLeft = dimesLeft - dimesCurrentUser;

		if (dimesLeft < 0) {

			unaccounted = 5 * dimesLeft * -1;
			dimesLeft = 0;

		}



		if (dimesCurrentUser == 1) {

			msg += dimesCurrentUser + " dime, ";

		} else {

			msg += dimesCurrentUser + " dimes, ";
		}

		dimesCurrentUser = 0;

		quartersLeft = quartersLeft - quartersCurrentUser;

		if (quartersCurrentUser == 1) {

			msg += "and " + quartersCurrentUser + " quarter returned";

		} else {

			msg += "and " + quartersCurrentUser + " quarters returned";
		}

		quartersCurrentUser = 0;

		while (unaccounted > 0) {

			if (unaccounted >= 25 && quartersLeft > 25) {

				unaccounted -= 25;
				quartersLeft--;

			} else if (unaccounted >= 10 && dimesLeft > 10) {

				unaccounted -= 10;
				dimesLeft--;

			} else if (unaccounted >= 5 && nickelsLeft > 5) {

				unaccounted -= 5;
				nickelsLeft--;

			} else {

				System.out.println("ERROR OH NO!!!!");
				break;

			}

		}

		setMessage(msg);


		view.update();

		// is called when the cancel button is pressed.
		// It should terminate any pending sale and return whatever coins the user has deposited.
		// When the view is updated, it should explain this action in the message window at the bottom.

	}

	public void deposit(int amount) {

		if (amount == 5) {

			this.amount += 5;
			nickelsLeft += 1;
			nickelsCurrentUser += 1;
			setMessage("1 nickel received");


		} else if (amount == 10) {

			this.amount += 10;
			dimesLeft += 1;
			dimesCurrentUser += 1;
			setMessage("1 dime received");


		} else if (amount == 25) {

			this.amount += 25;
			quartersLeft += 1;
			quartersCurrentUser += 1;
			setMessage("1 quarter received");

		}

		view.update();

	}

	public void buy (String product) {

		System.out.println("buy: " + product);

		if (product.equals("Pepsi")) {

			if (pepsiPrice <= getDepositedAsInt()) {

                if (giveChange(pepsiPrice)) {

					pepsiLeft--;
					cancel();
					setMessage("Pepsi bought: change = " + message);

				}

			} else {

				// cant buy
				setMessage("you don't have enough money");

			}

		}

		if (product.equals("Coke")) {

			if (cokePrice <= moneyLeft()) {

				if (giveChange(cokePrice)) {

					cokeLeft--;
					cancel();
					setMessage("Coke bought: change = " + message);

				}

			} else {

				// cant buy
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
	private boolean giveChange(int purchasePrice){
		int nickelSpent = 0;
		int dimeSpent = 0;
		int quarterSpent = 0;

		int tempQ = quartersLeft;
		int tempD = dimesLeft;
		int tempN = nickelsLeft;

		int userMoney = getDepositedAsInt();

		int remaining = purchasePrice;
		int wasRemaining = remaining;

		while (remaining > 0){

			wasRemaining = remaining;

			if (tempQ > 0 && remaining >= 25){

				tempQ--;
				//quartersLeft--;
				quarterSpent++;
				remaining -= 25;


			} else if (tempD > 0 && remaining >= 10){

				tempD--;
				//dimesLeft--;
				dimeSpent++;
				remaining -= 10;

			} else if (tempN > 0 && remaining >= 5){

				tempN--;
				//nickelsLeft--;
				nickelSpent++;
				remaining -= 5;

			}

			if (remaining == wasRemaining) {

				setMessage("can't give exact change");
				return false;

			}

		}


		quartersLeft -= quarterSpent;
		quartersCurrentUser -= quarterSpent;
		nickelsLeft -= nickelSpent;
		nickelsCurrentUser -= nickelSpent;
		dimesLeft -= dimeSpent;
		dimesCurrentUser -= dimeSpent;
		return true;

	}

}
