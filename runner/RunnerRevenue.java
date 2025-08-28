package foodhub.runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class RunnerRevenue extends javax.swing.JPanel {

    private String runnerID;

    public RunnerRevenue(String runnerID) {
        initComponents();
        this.runnerID = runnerID;
        loadMonthlyChart();
        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(monthlyRadioButton);
        filterGroup.add(yearlyRadioButton);
        filterGroup.add(quarterlyRadioButton);
    }

    private void loadMonthlyChart() {
        CategoryDataset dataset = createMonthlyDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Revenue", "Month", "Revenue",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );

        // Create chart panel with proper size
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));

        // Use BorderLayout
        chartPnl.setLayout(new java.awt.BorderLayout());
        chartPnl.removeAll();
        chartPnl.add(chartPanel, java.awt.BorderLayout.CENTER);

        // Make sure to validate and repaint
        chartPnl.validate();
        chartPnl.repaint();
    }

    private CategoryDataset createMonthlyDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> revenuePerMonth = new HashMap<>();

        // Update formatter to match your actual date format including time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String filePath = "C:\\FoodHub\\src\\foodhub\\Database\\Runnerfee.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Reading data for runner: " + runnerID);

            while ((line = br.readLine()) != null) {
                // Add debug print
                System.out.println("Processing line: " + line);

                String[] data = line.split("\\|");
                if (data.length < 3) {
                    continue;
                }

                String fileRunnerID = data[0].trim();
                String revenueStr = data[1].trim();
                String dateStr = data[2].trim();

                if (!fileRunnerID.equals(runnerID)) {
                    continue;
                }

                try {
                    // Parse the date string
                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    String monthYearKey = date.getYear() + "-" + String.format("%02d", date.getMonthValue());

                    // Parse revenue as double
                    double revenue = Double.parseDouble(revenueStr);

                    // Add debug print
                    System.out.println("Adding revenue " + revenue + " for month " + monthYearKey);

                    revenuePerMonth.put(monthYearKey,
                            revenuePerMonth.getOrDefault(monthYearKey, 0.0) + revenue);
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    System.err.println("Error details: " + e.getMessage());
                    continue;
                }
            }

            System.out.println("Revenue per month: " + revenuePerMonth);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Sort the months chronologically
        revenuePerMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> dataset.addValue(entry.getValue(), "Revenue", entry.getKey()));

        return dataset;
    }

    private void loadYearlyChart() {
        CategoryDataset dataset = createYearlyDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Yearly Revenue", "Year", "Revenue",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));

        chartPnl.setLayout(new java.awt.BorderLayout());
        chartPnl.removeAll();
        chartPnl.add(chartPanel, java.awt.BorderLayout.CENTER);
        chartPnl.validate();
        chartPnl.repaint();
    }

    private CategoryDataset createYearlyDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> revenuePerYear = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String filePath = "C:\\FoodHub\\src\\foodhub\\Database\\Runnerfee.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Reading yearly data for runner: " + runnerID);

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 3) {
                    continue;
                }

                String fileRunnerID = data[0].trim();
                String revenueStr = data[1].trim();
                String dateStr = data[2].trim();

                if (!fileRunnerID.equals(runnerID)) {
                    continue;
                }

                try {
                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    String yearKey = String.valueOf(date.getYear());

                    double revenue = Double.parseDouble(revenueStr);
                    System.out.println("Adding revenue " + revenue + " for year " + yearKey);

                    revenuePerYear.put(yearKey,
                            revenuePerYear.getOrDefault(yearKey, 0.0) + revenue);
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    System.err.println("Error details: " + e.getMessage());
                    continue;
                }
            }

            System.out.println("Revenue per year: " + revenuePerYear);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Sort the years chronologically
        revenuePerYear.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> dataset.addValue(entry.getValue(), "Revenue", entry.getKey()));

        return dataset;
    }

    private void loadQuarterlyChart() {
        CategoryDataset dataset = createQuarterlyDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Quarterly Revenue", "Quarter", "Revenue",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));

        chartPnl.setLayout(new java.awt.BorderLayout());
        chartPnl.removeAll();
        chartPnl.add(chartPanel, java.awt.BorderLayout.CENTER);
        chartPnl.validate();
        chartPnl.repaint();
    }

    private CategoryDataset createQuarterlyDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> revenuePerQuarter = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String filePath = "C:\\FoodHub\\src\\foodhub\\Database\\Runnerfee.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Reading quarterly data for runner: " + runnerID);

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 3) {
                    continue;
                }

                String fileRunnerID = data[0].trim();
                String revenueStr = data[1].trim();
                String dateStr = data[2].trim();

                if (!fileRunnerID.equals(runnerID)) {
                    continue;
                }

                try {
                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    // Calculate quarter (1-4)
                    int quarter = ((date.getMonthValue() - 1) / 3) + 1;
                    String quarterKey = date.getYear() + "-Q" + quarter;

                    double revenue = Double.parseDouble(revenueStr);
                    System.out.println("Adding revenue " + revenue + " for quarter " + quarterKey);

                    revenuePerQuarter.put(quarterKey,
                            revenuePerQuarter.getOrDefault(quarterKey, 0.0) + revenue);
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    System.err.println("Error details: " + e.getMessage());
                    continue;
                }
            }

            System.out.println("Revenue per quarter: " + revenuePerQuarter);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Sort the quarters chronologically
        revenuePerQuarter.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> dataset.addValue(entry.getValue(), "Revenue", entry.getKey()));

        return dataset;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPnl = new javax.swing.JPanel();
        stallName = new javax.swing.JLabel();
        filterPnl = new javax.swing.JPanel();
        monthlyRadioButton = new javax.swing.JRadioButton();
        yearlyRadioButton = new javax.swing.JRadioButton();
        quarterlyRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        chartPnl = new javax.swing.JPanel();

        headerPnl.setBackground(new java.awt.Color(51, 153, 255));

        stallName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        stallName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stallName.setText("Revenue Dashboard");

        javax.swing.GroupLayout headerPnlLayout = new javax.swing.GroupLayout(headerPnl);
        headerPnl.setLayout(headerPnlLayout);
        headerPnlLayout.setHorizontalGroup(
            headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPnlLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(stallName, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(748, Short.MAX_VALUE))
        );
        headerPnlLayout.setVerticalGroup(
            headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stallName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        monthlyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        monthlyRadioButton.setText("Monthly");
        monthlyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyRadioButtonActionPerformed(evt);
            }
        });

        yearlyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        yearlyRadioButton.setText("Yearly");
        yearlyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearlyRadioButtonActionPerformed(evt);
            }
        });

        quarterlyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        quarterlyRadioButton.setText("Quarterly");
        quarterlyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quarterlyRadioButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Filter:");

        javax.swing.GroupLayout filterPnlLayout = new javax.swing.GroupLayout(filterPnl);
        filterPnl.setLayout(filterPnlLayout);
        filterPnlLayout.setHorizontalGroup(
            filterPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(quarterlyRadioButton)
                    .addComponent(monthlyRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearlyRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        filterPnlLayout.setVerticalGroup(
            filterPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPnlLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quarterlyRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthlyRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearlyRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(276, Short.MAX_VALUE))
        );

        chartPnl.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout chartPnlLayout = new javax.swing.GroupLayout(chartPnl);
        chartPnl.setLayout(chartPnlLayout);
        chartPnlLayout.setHorizontalGroup(
            chartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 992, Short.MAX_VALUE)
        );
        chartPnlLayout.setVerticalGroup(
            chartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(chartPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(996, Short.MAX_VALUE)
                    .addComponent(filterPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 62, Short.MAX_VALUE)
                    .addComponent(filterPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void monthlyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyRadioButtonActionPerformed
        loadMonthlyChart(); // Call method to reload chart
    }//GEN-LAST:event_monthlyRadioButtonActionPerformed

    private void yearlyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearlyRadioButtonActionPerformed
        loadYearlyChart();
    }//GEN-LAST:event_yearlyRadioButtonActionPerformed

    private void quarterlyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quarterlyRadioButtonActionPerformed
        loadQuarterlyChart();
    }//GEN-LAST:event_quarterlyRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPnl;
    private javax.swing.JPanel filterPnl;
    private javax.swing.JPanel headerPnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton monthlyRadioButton;
    private javax.swing.JRadioButton quarterlyRadioButton;
    private javax.swing.JLabel stallName;
    private javax.swing.JRadioButton yearlyRadioButton;
    // End of variables declaration//GEN-END:variables
}
