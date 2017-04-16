import java.io.*;
import java.util.Scanner;

/*************************************************************************
 * Simulates a real life vending machine with stock read from a file.
 * 
 * CSCE 155A Spring 2017
 * Assignment 4
 * @file VendingMachine.java
 * @author Jeremy Suing
 * @version 1.0
 * @date March 6, 2017
 *************************************************************************/
public class VendingMachine {
	
	//data members
	private Item[] stock;  //Array of Item objects in machine
	private double money;  //Amount of revenue earned by machine

	/*********************************************************************
	 * This is the constructor of the VendingMachine class that take a
	 * file name for the items to be loaded into the vending machine.
	 *
	 * It creates objects of the Item class from the information in the 
	 * file to populate into the stock of the vending machine.  It does
	 * this by looping the file to determine the number of items and then
	 * reading the items and populating the array of stock. 
	 * 
	 * @param filename Name of the file containing the items to stock into
	 * this instance of the vending machine. 
	 * @throws FileNotFoundException If issues reading the file.
	 *********************************************************************/
	public VendingMachine(String filename) throws FileNotFoundException {
		//Open the file to read with the scanner
		File file = new File(filename);
		Scanner scan = new Scanner(file);

		//Determine the total number of items listed in the file
		int totalItem = 0;
		while (scan.hasNextLine()) {
			scan.nextLine();
			totalItem++;
		} //End while another item in file
		//Create the array of stock with the appropriate number of items
		stock = new Item[totalItem];
		scan.close();

		//Open the file again with a new scanner to read the items
		scan = new Scanner(file);
		int itemQuantity = -1;
		double itemPrice = -1;
		String itemDesc = "";
		int count = 0;
		String line = "";

		//Read through the items in the file to get their information
		//Create the item objects and put them into the array of stock
		while(scan.hasNextLine()) {
			line = scan.nextLine();
			String[] tokens = line.split(",");
			try {
				itemDesc = tokens[0];
				itemPrice = Double.parseDouble(tokens[1]);
				itemQuantity = Integer.parseInt(tokens[2]);

				stock[count] = new Item(itemDesc, itemPrice, itemQuantity);
				count++;
			} catch (NumberFormatException nfe) {
				System.out.println("Bad item in file " + filename + 
						" on row " + (count+1) + ".");
			}
		} //End while another item in file
		scan.close();
		
		//Initialize the money data variable.
		money = 0.0;
	} //End VendingMachine constructor

	public boolean vend(int index){
		if(getStock()[index].getQuantity()>0){
			if(getStock()[index].getPrice()<=money){
				getStock()[index].setQuantity(getStock()[index].getQuantity()-1);
				money=money-getStock()[index].getPrice();
				return true;
			}
		}
		return false;
	}

	public Item[] getStock() {
		return stock;
	}

	public void setStock(Item[] stock) {
		this.stock = stock;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
} //End VendingMachine class definition
