//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : vendingMachine.Controller
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
//   The Controller for the VendingMachine package creates JButtons,
// which when pressed call methods in the the Model (sometimes with an
// appropriate parameter) to implement the semantics of their actions.
//
//   For debugging purposes, when each button is pressed it displays
// some information on the console. These statements can be commented-out
// in the working program.
//
//   Note that "no access modifier" means that the method is package
// friendly: this means the member is public to all other classes in
// the calculator package, but private elsewhere.
//
// Future Plans   : More Comments
//
// Program History:
//   9/20/01: R. Pattis - Operational for 15-100
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package vendingMachine;


import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Controller {

  //Trivial constructor (could be automatically supplied by Java)
  public Controller() {

  }

  
  //Refer to the nodel (used in all the button methods, to call
  //  methods in the model) 
  public void addModel(Model m) {

	  model = m;

  }
  
  
  
  //Build/Return a coin-deposit button (with label amount, which must be
  //  final): it calls the deposit method in model (passing it amount)
  JButton getDepositButton(final int amount) {
	  JButton b = new JButton();
	  
	  b.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {

		    System.out.println("Debug-Controller: " + amount + " (deposit) button pressed");
		    model.deposit(amount);

		  }

	  });
	  return b;
  }
    

     
  //Build/Return a buy button (with label product, which must be
  //  final): it calls the buy method in model (passing it product)
  JButton getBuyButton(final String product)
  {
	  JButton b = new JButton();
	  
	  b.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e)
		  {
		    System.out.println("Debug-Controller: " + product + " (buy) button pressed");
		    model.buy(product);
		  }
	  });
	  
	  return b;
  }
    
  //Build/Return the Cancel button: it calls the cancel method in model
  JButton getCancelButton()
  {
	  JButton b = new JButton();
	  
	  b.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e)
		  {
		    System.out.println("Debug-Controller: " + "Cancel button pressed");
		    model.cancel();
		  }

	  });
	  
	  return b;
  }
    
  //Instance Variable  
  
  //Controller must call the appropriate methods in the Model when
  //  buttons are pressed; this reference, set in setModel, stores
  //  the connection.
  private Model model;

}


