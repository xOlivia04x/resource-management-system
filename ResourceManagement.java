import java.io.*;
import java.util.*;

/* ResourceManagement
 *
 * Stores the information needed to decide which items to purchase for the given budget and departments
 */
public class ResourceManagement
{
    private PriorityQueue<Department> departmentPQ; /* priority queue of departments */
    private Double remainingBudget;                 /* the budget left after purchases are made (should be 0 after the constructor runs) */
    private Double budget;                          /* the total budget allocated */

  /* TO BE COMPLETED BY YOU
   * Fill in your name in the function below
   */  
  public static void printNames( )
  {
    /* TODO : Fill in your name and your partner's name */
    System.out.println("This solution was completed by:");
    System.out.println("Olivia Hand");
    System.out.println("N/A");
  }

  /* Constructor for a ResourceManagement object
   * TODO
   * Simulates the algorithm from the pdf to determine what items are purchased
   * for the given budget and department item lists.
   */
  public ResourceManagement( ArrayList<String> fileNames, Double budget )
  {
    /* Create a department for each file listed in fileNames */
	  this.budget = budget;
	  this.remainingBudget = budget;
	  departmentPQ = new PriorityQueue<Department>();
	  
	  for (String fileName : fileNames) {
	   Department department = new Department(fileName);
	   departmentPQ.add(department);
	  }
  }
  
  /* Provided code - DO NOT CHANGE THIS METHOD 
   * findAndPrintBudget
   * Executes the budget distribution algorithm and prints a record of which items were purchased
   */    
  public void findAndPrintBudget(  ){
    /* Simulate the algorithm from the PDF */
    allocateBudget(  );
    System.out.print( "\n\n" );
    
    /* Summary of how the budget was distributed to the departments */
    printSummaryOfDistribution(  );
  } 

  /* allocateBudget
   * TODO
   * Executes the budget distribution algorithm and prints a record of which items were purchased
   */    
  private void allocateBudget(  ){
    /* Simulate the algorithm for picking the items to purchase */
    /* Print a record of each item as they are purchased (see PDF and sample output for example tables) */
	  System.out.println("ITEMS PURCHASED");
	  System.out.println("----------------------------");
	  while (remainingBudget > 0 && !departmentPQ.isEmpty()) {
       Department dept = departmentPQ.poll();
	  
	  while (!dept.itemsDesired.isEmpty() &&
		       dept.itemsDesired.peek().price > remainingBudget) {
		    Item skipped = dept.itemsDesired.poll();
		    dept.itemsRemoved.add(skipped);
		}
	  double amount;
	  Item purchased;
	  if (dept.itemsDesired.isEmpty()) {
	     amount = Math.min(1000.0, remainingBudget);
	     purchased = new Item("Scholarship", amount);
		} else {
		  purchased = dept.itemsDesired.poll();
		  amount = purchased.price;
		}
	  
	  dept.priority += amount;
	  remainingBudget -= amount;
	  dept.itemsReceived.add(purchased);
	  System.out.printf("Department of %-30s - %-30s - %29s%n",
	  dept.name, purchased.name,
	  String.format("$%.2f", purchased.price));
	  departmentPQ.add(dept);
	  }
  } 

  /* printSummaryOfDistribution
   * TODO
   * Print a summary of what each department received and did not receive
   */    
  private void printSummaryOfDistribution() {

    while (!departmentPQ.isEmpty()) {
	 Department dept = departmentPQ.poll();
	 double percentage = 0.0;

	 if (budget > 0) {
	 percentage = (dept.priority / budget) * 100.0;
	 }

	 System.out.printf("Department of %s%n", dept.name);
	 System.out.printf("Total Spent       = $%.2f%n", dept.priority);
	 System.out.printf("Percent of Budget = %.2f%%%n", percentage);
	 System.out.println("----------------------------");

	 if (!dept.itemsReceived.isEmpty()) {
	 System.out.println("ITEMS RECEIVED");

	  for (Item item : dept.itemsReceived) {
	  System.out.printf("%-30s - %29s%n",
	  item.name, String.format("$%.2f", item.price));
	  }

	  System.out.println();
	 }
	 
	 while (!dept.itemsDesired.isEmpty()) {
	 dept.itemsRemoved.add(dept.itemsDesired.poll());
	 }

	 System.out.println("ITEMS NOT RECEIVED");

	 for (Item item : dept.itemsRemoved) {
	 System.out.printf("%-30s - %29s%n",
	 item.name, String.format("$%.2f", item.price));
	 }

	 System.out.println();
	 System.out.println();
	 }
	}
}

/* Department
 *
 * Stores the information associated with a Department at the university
 */
class Department implements Comparable<Department>
{
  String name;                /* name of this department */
  Double priority;            /* total money spent on this department */
  Queue<Item> itemsDesired;   /* list of items this department wants */
  Queue<Item> itemsReceived;  /* list of items this department received */
  Queue<Item> itemsRemoved;   /* list of items that were skipped because they exceeded the remaining budget */

  /* TODO
   * Constructor to build a Department from the information in the given fileName
   */
  public Department( String fileName ){
    /* Open the fileName, create items based on the contents, and add those items to itemsDesired */
	  priority = 0.0;

	  itemsDesired = new LinkedList<Item>();
	  itemsReceived = new LinkedList<Item>();
	  itemsRemoved = new LinkedList<Item>();
	  
	  try {
		    Scanner input = new Scanner(new File(fileName));
		    name = input.next();
		    while (input.hasNextDouble()) {

		        double price = input.nextDouble();
		        input.next(); // Skip the comma
		        String itemName = input.next();

		        Item item = new Item(itemName, price);
		        itemsDesired.add(item);
		    }
		    input.close();		    
		} catch (FileNotFoundException e) {
		    System.out.println("File not found: " + fileName);
		}
	  
  }
    
  /*
   * Compares the data in the given Department to the data in this Department
   * Returns -1 if this Department comes first
   * Returns 0 if these Departments have equal priority
   * Returns 1 if the given Department comes first
   *
   * This function is to ensure the departments are sorted by the priority when put in the priority queue 
   */
  public int compareTo( Department dept ){
    return this.priority.compareTo( dept.priority );
  }

  public boolean equals( Department dept ){
    return this.name.compareTo( dept.name ) == 0;
  }

  @Override 
  public boolean equals(Object aThat) {
    if (this == aThat) //Shortcut the future comparisons if the locations in memory are the same
      return true;
    if (!(aThat instanceof Department))
      return false;
    Department that = (Department)aThat;
    return this.equals( that ); //Use above equals method
  }
  
  @Override
  public int hashCode() {
    return name.hashCode(); /* use the hashCode for data stored in this name */
  }

  /* Debugging tool
   * Converts this Department to a string
   */		
  public String toString() {
    return "NAME: " + name + "\nPRIORITY: " + priority + "\nDESIRED: " + itemsDesired + "\nRECEIVED " + itemsReceived + "\nREMOVED " + itemsRemoved + "\n";
  }
}

/* Item
 *
 * Stores the information associated with an Item which is desired by a Department
 */
class Item
{
  String name;    /* name of this item */
  Double price;   /* price of this item */

  /*
   * Constructor to build a Item
   */
  public Item( String name, Double price ){
    this.name = name;
    this.price = price;
  }

  /* Debugging tool
   * Converts this Item to a string
   */		
  public String toString() {
    return "{ " + name + ", " + price + " }";
  }
}
