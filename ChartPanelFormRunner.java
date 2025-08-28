
package foodhub;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.StandardGradientPaintTransformer;


public class ChartPanelFormRunner extends JPanel {

  private static final String ratingPaths = "C:\\FoodHub\\src\\foodhub\\Database\\JoinedRunnerRating.txt";
  

    public ChartPanelFormRunner() {
        // Create and add the chart
        setLayout(new BorderLayout());
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<RunnerRecord> records = readFile(ratingPaths);

        // Filter role 2 and sort by revenue in descending order
        List<RunnerRecord> filteredRecords = new ArrayList<>();
        for (RunnerRecord record : records) {
                filteredRecords.add(record);
        }

        // Sort by revenue in descending order
        filteredRecords.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));

        // Take the top 5
        int count = 0;
        for (RunnerRecord record : filteredRecords) {
            if (count >= 5) break;
            dataset.setValue(record.getRating(), "Rating", record.getName());
            count++;
        }

        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Top 5 Rating by Runners", 
                "Username",                   
                "Rating",         
                dataset,                  
                PlotOrientation.VERTICAL, 
                false,                    
                true,                     
                false                     
        );
        
        
    // Edit chart appearance
    chart.setBorderPaint(Color.WHITE);  
    chart.setBackgroundPaint(new Color(245, 245, 245));  
    
    // Title customization
    chart.getTitle().setPaint(new Color(51, 51, 51));  
    chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));  
    
    // Category plot customization 
    CategoryPlot plot = chart.getCategoryPlot();
    
    // Set background color for plot area
    plot.setBackgroundPaint(Color.WHITE); 
    // Customize gridlines 
    plot.setDomainGridlinePaint(new Color(220, 220, 220));  
    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);   
    
    // Customize domain (X-axis) and range (Y-axis) axis lines and labels
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setLabelFont(new Font("Arial", Font.PLAIN, 12)); 
    domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 10)); 
    domainAxis.setTickLabelPaint(Color.DARK_GRAY); 
    
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setLabelFont(new Font("Arial", Font.PLAIN, 12));
    rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 10)); 
    rangeAxis.setTickLabelPaint(Color.DARK_GRAY); 
    
    // Edit Bar
    BarRenderer renderer = new BarRenderer();
    renderer.setSeriesPaint(0, new Color(255, 128, 81));  
    renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL));
    renderer.setDrawBarOutline(true);
    renderer.setSeriesOutlinePaint(0, Color.DARK_GRAY); 
    
    
    
    plot.setRenderer(renderer);
    plot.setDomainGridlinesVisible(false); 
    plot.setRangeGridlinesVisible(true);   
    
    return chart;
    }

    private List<RunnerRecord> readFile(String vendorsPath) {
        List<RunnerRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(vendorsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    String runnerId = parts[0].trim();
                    String name = parts[1].trim();
                    double rating = Double.parseDouble(parts[2].trim());
                    

                    records.add(new RunnerRecord(runnerId, name, rating));
                }
            }
        } catch (IOException e) {
            DialogManager dialogManager = new DialogManager("Can't read text file data!");
            dialogManager.showInfoMessage("Please report to the Administrator");     
        }
        return records;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
