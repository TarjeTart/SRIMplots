package Chart;

import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import data.SrimDataPoint;

public class StraggleChart extends ApplicationFrame {

	public StraggleChart(String title, ArrayList<SrimDataPoint> data, String name) {
		super(title);
		XYSeries series = new XYSeries(name);
		double[][] set = new double[data.size()][2];
		int count = 0;
		//add data
		for(SrimDataPoint i : data) {
			series.add(i.getIonEnergy(), i.getStraggling());
			double[] tmp = {i.getIonEnergy(), i.getRange()};
			set[count++] = tmp;
		}
		XYSeriesCollection collection = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart(
					"Straggle vs Energy",
					"Energy (KeV)",
					"Straggle (A)",
					collection,
					PlotOrientation.VERTICAL,
					true,
					true,
					false
				);
		System.out.println("Straggle = " + Regression.getPowerRegression(set)[0] + " x^(" + 
				Regression.getPowerRegression(set)[1] + ")");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500,270));
		setContentPane(chartPanel);
	}

	
	
}