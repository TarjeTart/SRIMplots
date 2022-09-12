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
import data.units;

public class RangeChart extends ApplicationFrame {
	
	JFreeChart chartErrorBars;

	public RangeChart(String title, ArrayList<SrimDataPoint> data, String name, String lengthUnit, String energyUnit) {
		super(title);
		units lengthU = units.getUnit(lengthUnit);
		units energyU = units.getUnit(energyUnit);
		//data to be plotted
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		//set of data used for line of best fit
		double[][] set = new double[data.size()][2];
		int count = 0;
		//add data to the dataset
		for(SrimDataPoint i : data) {
			dataset.add(units.convertLength(i.getRange(), i.getRangeUnit(), lengthU),
					units.convertLength(i.getStraggling(), i.getStraggleUnit(), lengthU), "Energy", 
					String.valueOf(units.toKeV(i.getIonEnergy(), i.getEnergyUnit())));
			double[] tmp = {i.getIonEnergy(), i.getRange()};
			set[count++] = tmp;
		}
		//create line chart with error bars
		chartErrorBars = ChartFactory.createLineChart(
				"Range vs Energy", 
				"Energy (" + units.toString(energyU) + ")", 
				"Range (" + units.toString(lengthU) + ")", 
				dataset, PlotOrientation.VERTICAL, 
				false, true, true);
		//for chart first arg removes lines from line chart, second arg keeps mean point
		StatisticalLineAndShapeRenderer statisticalRenderer = new StatisticalLineAndShapeRenderer(false, true);
		chartErrorBars.getCategoryPlot().setRenderer(statisticalRenderer);
		//how to label chart points and make them visible
		statisticalRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(
				"{2} " + units.toString(lengthU), NumberFormat.getNumberInstance()));
		statisticalRenderer.setDefaultItemLabelsVisible(true);
		System.out.println("Range = " + Regression.getOLSRegression(set)[1] + " x + " + Regression.getOLSRegression(set)[0]);
		//add chart to a jpanel
		ChartPanel chartPanel = new ChartPanel(chartErrorBars);
		//initial size of panel and add to content pane
		chartPanel.setPreferredSize(new java.awt.Dimension(1080,720));
		setContentPane(chartPanel);
	}

	public JFreeChart getJFreeChart() {
		return chartErrorBars;
	}
	
}
