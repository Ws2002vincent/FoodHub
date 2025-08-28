package foodhub;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionTimeSeriesChart {

    public static JPanel createTransactionChartPanel() {
        TimeSeriesCollection dataset = loadTransactionData();

        // Create the time series chart
        JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(
                "Transaction Amount Time Series Analysis",
                "Date",
                "Total Amount (RM)",
                dataset,
                true, true, false
        );

        // Customize plot
        XYPlot plot = (XYPlot) timeSeriesChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Customize renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, new Color(79, 129, 189)); // Line color
        plot.setRenderer(renderer);

        // Customize date axis format
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

        ChartPanel chartPanel = new ChartPanel(timeSeriesChart);
        chartPanel.setPreferredSize(new Dimension(960, 540));

        return chartPanel;
    }

    private static TimeSeriesCollection loadTransactionData() {
        TimeSeries series = new TimeSeries("Total Transaction Amount");
        Map<String, Double> dailyTransactionMap = new TreeMap<>();

        String filePath = FilePaths.PAYMENT_FILE; // Adjust path if needed
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssa");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 5) continue;

                double amount = Double.parseDouble(parts[2]); // Transaction amount
                String dateTime = parts[3]; // Transaction date and time

                Date date = inputFormat.parse(dateTime);
                String dateOnly = new SimpleDateFormat("dd-MM-yyyy").format(date);

                dailyTransactionMap.put(dateOnly, dailyTransactionMap.getOrDefault(dateOnly, 0.0) + amount);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error: Could not read Transaction.txt");
            e.printStackTrace();
        }

        // Add data to time series
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Map.Entry<String, Double> entry : dailyTransactionMap.entrySet()) {
            try {
                Date date = dateFormat.parse(entry.getKey());
                series.add(new Day(date), entry.getValue());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transaction Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(createTransactionChartPanel());
        frame.setVisible(true);
    }
}
