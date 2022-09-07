package Chart;

import java.text.NumberFormat;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StatisticalLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import data.SrimDataPoint;

public class RangeChart extends ApplicationFrame {
	
	JFreeChart chartErrorBars;

	public RangeChart(String title, ArrayList<SrimDataPoint> data, String name) {
		super(title);
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		double[][] set = new double[data.size()][2];
		int count = 0;
		//add data
		for(SrimDataPoint i : data) {
			dataset.add(i.getRange(), i.getStraggling(), "Energy", String.valueOf(i.getIonEnergy()));
			double[] tmp = {i.getIonEnergy(), i.getRange()};
			set[count++] = tmp;
		}
		chartErrorBars = ChartFactory.createLineChart(
				"Range vs Energy", 
				"Energy (KeV)", 
				"Range (A)", 
				dataset, PlotOrientation.VERTICAL, 
				false, true, true);
		StatisticalLineAndShapeRenderer statisticalRenderer = new StatisticalLineAndShapeRenderer(false, true);
		chartErrorBars.getCategoryPlot().setRenderer(statisticalRenderer);
		statisticalRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
		statisticalRenderer.setDefaultItemLabelsVisible(true);
		System.out.println("Range = " + Regression.getOLSRegression(set)[1] + " x + " + Regression.getOLSRegression(set)[0]);
		ChartPanel chartPanel = new ChartPanel(chartErrorBars);
		chartPanel.setPreferredSize(new java.awt.Dimension(1080,720));
		setContentPane(chartPanel);
	}

	public JFreeChart getJFreeChart() {
		return chartErrorBars;
	}
	
}
