package Test;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;


class DynamaticBeifen {
    private TimeSeries x;
    private TimeSeries y;
    private TimeSeries z;

    public JPanel getDynamatic(int maxAge) {
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new BorderLayout());//边界布局
        jPanel.setPreferredSize(new Dimension(300,200));//Jpanel的长宽
        x = new TimeSeries("x轴", Millisecond.class);//设置x轴图例
        x.setMaximumItemAge(maxAge);
        y = new TimeSeries("y轴", Millisecond.class);//设置y轴图例
        y.setMaximumItemAge(maxAge);
        z = new TimeSeries("z轴", Millisecond.class);//设置z轴图例
        z.setMaximumItemAge(maxAge);

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        dataset.addSeries(x);
        dataset.addSeries(y);
        dataset.addSeries(z);

        DateAxis domain = new DateAxis("时间");
        NumberAxis range = new NumberAxis("幅值");
        domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        range.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesPaint(2,Color.black);
        renderer.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        XYPlot plot = new XYPlot(dataset, domain, range, renderer);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        domain.setAutoRange(true);
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);
        domain.setTickLabelsVisible(true);
        range.setAutoRange(true);
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        JFreeChart chart = new JFreeChart("节点波形", new Font("SansSerif", Font.BOLD, 12), plot, true);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
        jPanel.add(chartPanel);
        return jPanel;
    }

    private void addTotalObservation(double y) {
        this.x.add(new Millisecond(), y);
    }

    private void addFreeObservation(double y) {
        this.y.add(new Millisecond(), y);
    }


}