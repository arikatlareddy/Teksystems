package com.tek.interview.question;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Utilities {
	 public static double rounding(double value) {
	        DecimalFormat df = new DecimalFormat("#.##");
	        return Double.parseDouble(df.format(value));
	    }
}


class Item {
 
    private String description;
    private float price;
 
    public Item(String description, float price) {
        super();
        this.description = description;
        this.price = price;
    }
 
    public String getDescription() {
        return description;
    }
 
    public float getPrice() {
        return price;
    }
    
    
    public double getTax(){
    	
    	if (this.description.toUpperCase().contains("imported".toUpperCase())) {
            return  price * 0.15;                     
        } else {
            return price * 0.10;
        }
    }
    
    public double getTotalPrice(){
    	return price+ getTax();
    }
}

class OrderLine {
 
    private int quantity;
    private Item item;
 
    public OrderLine(String description, float price, int quantity) {    
        assert quantity > 0;
     
        this.item = new Item(description,price);
       
        this.quantity = quantity;
    }
 
    public Item getItem() {
        return item;
    }
 
    public int getQuantity() {
        return quantity;
    }
    
    public double getOrderLineTax()
    {
      return Utilities.rounding(this.item.getTax()) * this.quantity;
    }
    
    public double getOrderLineTotalPrice()
    {
    	return Utilities.rounding(this.item.getTotalPrice()) * this.quantity;
    }
    
    @Override
    public String toString() {
    	
    	return this.item.getDescription()+": "+ this.quantity+" * "+ Utilities.rounding(this.item.getTotalPrice()) +" = "+  Utilities.rounding(getOrderLineTotalPrice());
    }
    
   
    
}
 
class Order {

    private List<OrderLine> orderLines = new ArrayList<OrderLine>();
 
    public void add(String description, float price, int quantity){    
        orderLines.add(new OrderLine(description, price, quantity));
    }
 
    public int size() {
        return orderLines.size();
    }
 
    public OrderLine get(int i) {
        return orderLines.get(i);
    }
 
    public void clear() {
    
        this.orderLines.clear();
    }
    
    public double getOrderTotal()
    {
	    double total = 0;
  		for (int i = 0; i < orderLines.size(); i++) {
  			OrderLine ol = orderLines.get(i);
	            total += ol.getOrderLineTotalPrice();
			} 		
  		return Utilities.rounding(total);
    }
    
    
    @Override
    public String toString() {
		  double totalTax = 0;
	      double total = 0;
    	  StringBuilder str = new StringBuilder();
    		for (int i = 0; i < orderLines.size(); i++) {
    			OrderLine ol = orderLines.get(i);
				str.append(ol.toString());
				str.append("\n");
				totalTax += ol.getOrderLineTax();
	            total += ol.getOrderLineTotalPrice();
			}
    		str.append("Sales Tax: " + Utilities.rounding(totalTax)+"\n");
    		str.append("Total: " + Utilities.rounding(total)+"\n");
    		return str.toString();
    }
    
}
 
class calculator {
    public void calculate(Map<String, Order> o) {
 
        double grandtotal = 0;
        for (Map.Entry<String, Order> entry : o.entrySet()) {
            System.out.println("*******" + entry.getKey() + "*******");
            System.out.println(entry.getValue());  
            grandtotal += entry.getValue().getOrderTotal(); 
        }
       
        System.out.println("Sum of orders: " + Utilities.rounding(grandtotal));
    }
}
 
public class Foorefactor {
 
    public static void main(String[] args) throws Exception {
 
        Map<String, Order> o = new HashMap<String, Order>();
        Order c = new Order();
        c.add("book", (float) 12.49, 1);
        c.add("music CD", (float) 14.99, 1);
        c.add("chocolate bar", (float) 0.85, 1);
        o.put("Order 1", c);
 
      
        Order c1 = new Order();
        c1.add("imported box of chocolate", 10, 1);
        c1.add("imported bottle of perfume", (float) 47.50, 1);
        o.put("Order 2", c1);
 
        
        Order c2 = new Order();
        c2.add("Imported bottle of perfume", (float) 27.99, 1);
        c2.add("bottle of perfume", (float) 18.99, 1);
        c2.add("packet of headache pills", (float) 9.75, 1);
      
        c2.add("box of imported  chocolates", (float) 11.25, 1);
        o.put("Order 3", c2);
 
        new calculator().calculate(o);
 
    }
}


/*Expected Result:
 
	
	*******Order 3*******
	Imported bottle of perfume: 1 * 32.19 = 32.19
	bottle of perfume: 1 * 20.89 = 20.89
	packet of headache pills: 1 * 10.72 = 10.72
	box of imported  chocolates: 1 * 12.94 = 12.94
	Sales Tax: 8.77
	Total: 76.74

	*******Order 2*******
	imported box of chocolate: 1 * 11.5 = 11.5
	imported bottle of perfume: 1 * 54.62 = 54.62
	Sales Tax: 8.62
	Total: 66.12

	*******Order 1*******
	book: 1 * 13.74 = 13.74
	music CD: 1 * 16.49 = 16.49
	chocolate bar: 1 * 0.94 = 0.94
	Sales Tax: 2.84
	Total: 31.17

	Sum of orders: 174.03
*/