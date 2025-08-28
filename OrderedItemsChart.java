package foodhub;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import org.jfree.chart.ui.HorizontalAlignment;

public class OrderedItemsChart {

    public static JPanel createOrderedItemsChartPanel() {
        Map<String, Integer> itemFrequencyMap = loadOrderedItemsFrequency();

        // Sort items by frequency (descending)
        List<Map.Entry<String, Integer>> sortedItems = itemFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10) // Show top 10 ordered items
                .collect(Collectors.toList());

        // Create dataset for chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : sortedItems) {
            dataset.addValue(entry.getValue(), "Orders", entry.getKey());
        }

        // Create bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Ordered Items Frequency Report",
                "Item",
                "Order Count",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        // Customize title
        TextTitle chartTitle = new TextTitle("Ordered Items Frequency Report");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        chartTitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        barChart.setTitle(chartTitle);

        // Customize chart appearance
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint(Color.gray);

        // Customize bar appearance
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setSeriesPaint(0, new Color(79, 129, 189)); // Custom color

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(960, 540));

        return chartPanel;
    }

    // Load order data and calculate item frequency
    private static Map<String, Integer> loadOrderedItemsFrequency() {
        Map<String, Integer> itemFrequency = new HashMap<>();
        String filePath = FilePaths.ORDERHISTORY_FILE; // Adjust path if needed

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 5) continue;

                String orderItems = parts[4]; // Items are stored in column 5
                orderItems = orderItems.replace("[", "").replace("]", ""); // Remove brackets

                String[] items = orderItems.split(",");
                for (String item : items) {
                    String[] details = item.split(":");
                    if (details.length < 2) continue;

                    String itemName = details[0].trim();
                    int quantity = Integer.parseInt(details[1].trim());

                    itemFrequency.put(itemName, itemFrequency.getOrDefault(itemName, 0) + quantity);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Could not read Orderhistory.txt");
            e.printStackTrace();
        }

        return itemFrequency;
    }

    // For testing the chart display
    public static void main(String[] args) {
        JFrame frame = new JFrame("FoodHub Analytics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(createOrderedItemsChartPanel());
        frame.setVisible(true);
    }
}
