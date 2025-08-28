/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub.vendor;

/**
 *
 * @author User
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class OrderRevenueDataManager {
    private List<OrderRevenue> orders = new ArrayList<>();
    
    public void loadOrdersFromFile(String filePath) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssa");
        
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(" \\| ");
            String orderId = columns[0];
            String customerName = columns[1];
            String vendorId = columns[2];
            String type = columns[3];
            String foodItems = columns[4];
            Date orderDate = dateFormat.parse(columns[5]);
            double deliveryFee = Double.parseDouble(columns[6]);
            double foodPrice = Double.parseDouble(columns[7]);
            String status = columns[8];
            boolean isCompleted = Boolean.parseBoolean(columns[9]);
            
            OrderRevenue order = new OrderRevenue(orderId, customerName, vendorId, type, foodItems, orderDate, deliveryFee, foodPrice, status, isCompleted);
            orders.add(order);
            System.out.println("loadOrdersFromFile function added: " + order);
        }
    }
    
    public List<OrderRevenue> getOrdersByVendor(String vendorId) {
        List<OrderRevenue> vendorOrders = new ArrayList<>();
        for (OrderRevenue order : orders) {
            if (order.getVendorId().equals(vendorId)) {
                vendorOrders.add(order);
            }
        }
        System.out.println("read: " + vendorOrders);
        return vendorOrders;
    }
    
    public double getTotalRevenueForVendor(String vendorId) {
        double totalRevenue = 0;
        for (OrderRevenue order : getOrdersByVendor(vendorId)) {
            totalRevenue += order.getTotalRevenue();
        }
        System.out.println("getTotalRevenueForVendor: " + totalRevenue);
        return totalRevenue;
    }
    
    public double getTotalRevenueForMonth(String vendorId, int month) {
        double totalRevenue = 0;
        for (OrderRevenue order : getOrdersByVendor(vendorId)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int orderMonth = calendar.get(Calendar.MONTH) + 1; // 0-11 range, so add 1
            
            if (orderMonth == month) {
                totalRevenue += order.getTotalRevenue();
            }
        }
        System.out.println("getTotalRevenueForMonth: " + totalRevenue);
        return totalRevenue;
    }
}