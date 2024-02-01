//Application to build/connect components in VendingMachine package


import vendingMachine.Model;
import vendingMachine.View;
import vendingMachine.Controller;


public class Application
{
	public static void main(String[] args)
	{
    //Construct all the components
	  Model      model      = new Model();
	  Controller controller = new Controller();
	  View       view       = new View();
	  
	  //Notify each component of the other components it needs
	  model.addView(view);
	  controller.addModel(model);
	  view.addModel(model);
	  view.addController(controller);
	  
	  //Build the application, then show it on the screen
	  view.build();
	  view.show();

	}
}