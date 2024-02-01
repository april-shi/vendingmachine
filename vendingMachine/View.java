//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : vendingMachine.View
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
//   This View for the vending machine package formats buttons to deposit
// money, buy a product, cancel a purchase; it also displays the amount of
// money deposited (for the current sale) and a status window at the
// bottom, to report the action of pressing a button.
//
//   This class uses many Swing classes in fairly typical ways. The
// entire View itself extends a JFrame (holding all the displays/buttons).
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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.Font;
import java.awt.Component;
import java.awt.Container;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


//Overall view: 
//  North  = Cancel/Entered
//  Center = Buy(vertical)/Coin(vertical)
//  South  = Message

public class View extends JFrame {

  //Trivial constructor (could be automatically supplied by Java)
	public View()
	{}
	
	
	
  //Refer to the model (used in the update method, to call various
  //  accessors methods in the model) 
	public void addModel(Model m)
	{model = m;}
	
	
  //Refer to the controller (used to build the buttons the view will
  //  place in the view) 
	public void addController(Controller c)
	{controller = c;}
	
	
	
  //build does the heavy lifting; it builds the view, populating it
  //  with the appropriate sdisplays and buttons
  //GUI applications have lots of little details to specify to make
  //  them look nice, and this method is in charge of them all
	public void build()
	{
	  //When a window close icon is pressed, exit the entire program
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		//Size the frame to a reasobnable size and label it
		setSize(500, 250);
		setTitle("Vending Machine Simulator");

		//Create a font for all the buttons (save it in a field, so it can be
		//  referred to in methods called subsequently: buttonSetup)
		buttonFont = new Font("Monospaced", Font.BOLD, 24);

		//Get content pane and start creating/storing GUI components in it
		//Top    panel: Cancel + Deposited
		//Center panel: Buy + Deposit
		Container contentPane = getContentPane();
		contentPane.add(createTopPanel(),    "North");
		contentPane.add(createCenterPanel(), "Center");
		
		//Bottom panel: messages
 		message = new JTextField("", 60);
		message.setEditable(false);
		message.setBackground(Color.cyan);
		message.setFont(new Font("SansSerif", Font.PLAIN, 12));
		message.setHorizontalAlignment(JTextField.LEFT);
		contentPane.add(message, "South");
		update();
	}



  //Called only in createCoinPanel/crateBuyPanel below, to set the
  //  attributes of the buttons (their label, font) and add them to
  //  the GUI
  private void buttonSetup(JPanel  panelForButtons,
                           JButton b,
                           String  bLabel)
  {
		b.setText(bLabel);
		b.setFont(buttonFont);
		panelForButtons.add(b);
  }
  
  
  
  //Creates the panel for the Cancel buttons and Deposited text
  //It both creates a button (from the controller) and places it in 
  //  the view
	private JPanel createTopPanel()
	{
		JPanel top = new JPanel();
		JButton cancel = controller.getCancelButton();
		buttonSetup(top,cancel,"Cancel");
		
		deposited = new JTextField("0", 10);
		deposited.setEditable(false);
		deposited.setBackground(Color.cyan);
		deposited.setFont(buttonFont);
		deposited.setHorizontalAlignment(JTextField.RIGHT);
		top.add(deposited);
		
		return top;
  }
		
		
		
  //Creates the panel for the coin-deposit buttons
  //It both creates the buttons (from the controller) and places them in 
  //  the view (a vertical grid)
	private JPanel createCoinPanel()
	{
		JPanel coins = new JPanel();
		coins.setLayout(new GridLayout(3,1));
		buttonSetup(coins,controller.getDepositButton( 25),"$0.25");
		buttonSetup(coins,controller.getDepositButton( 10),"$0.10");
		buttonSetup(coins,controller.getDepositButton(  5),"$0.05");
		return coins;
	}



	//Creates the panel for the buy buttons
  //It both creates the buttons (from the controller) and places them in 
  //  the view (a vertical grid)
  private JPanel createBuyPanel()
  {
  	JPanel buy = new JPanel();
		buy.setLayout(new GridLayout(2,1));
		buttonSetup(buy, buyPepsi=controller.getBuyButton("Pepsi"), "");
		buttonSetup(buy, buyCoke= controller.getBuyButton("Coke"),  "");
		return buy;
  }



	//Creates the center panel, containing the subpanels for the
	//  coin-deposit buttons and the buy buttons (see above)
	private JPanel createCenterPanel()
	{
		JPanel center = new JPanel();
		center.add(createBuyPanel());
		center.add(createCoinPanel());
		return center;
	}
	
  //When the model changes, it call update, which determines how to
  //  view the model by calling its various accessor methods.
  //This seems a bit circular, but it isn't (you need to know
  //   more about the MVC pattern to understand better)
	void update()
	{
		System.out.println(model);  //Debugging
		
		deposited.setText(model.getDeposited());
		message.setText (model.getMessage());
		buyPepsi.setText("Buy Pepsi ("+model.getPepsiLeft()+")@"+model.getPepsiPrice());
		buyCoke.setText("Buy Coke ("+model.getCokeLeft()+")@"+model.getCokePrice());
		if (model.getPepsiLeft() == 0)
		  buyPepsi.setEnabled(false);
		if (model.getCokeLeft() == 0)
		  buyCoke.setEnabled(false);
		  
	}

  //Instance Variables
  
  private Controller controller;  //Controller creates button in View
	private Model      model;       //Model tells update what to display
	
	private JButton    buyPepsi, buyCoke;
	private JTextField deposited, message;
	private Font       buttonFont;  //set/used across methods
 }
