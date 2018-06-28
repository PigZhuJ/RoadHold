package waveForm;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static NodeData.getWaveData.getYData;

public class YDynamaticWave extends JPanel{
    private TimeSeries y;

    public YDynamaticWave(int maxAge) {
        super(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 300));//Jpanel的长宽

        this.y = new TimeSeries("y轴", Millisecond.class);//设置y轴图例
        this.y.setMaximumItemAge(maxAge);


        TimeSeriesCollection dataset = new TimeSeriesCollection();

        dataset.addSeries(y);

        DateAxis domain = new DateAxis("时间");
        NumberAxis range = new NumberAxis("幅值");
        domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        range.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesPaint(2, Color.black);
        renderer.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
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
//        range.setRange(new Range(-256,256));
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        JFreeChart chart = new JFreeChart("节点波形", new Font("SansSerif", Font.BOLD, 10), plot, true);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
        add(chartPanel);
    }
    private void addyObservation(double y) {
        this.y.add(new Millisecond(), y);
    }
    class DataYGenerator extends Timer implements ActionListener {
        DataYGenerator(int interval) {
            super(interval, null);
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent event) {
            int y = getYData();
            addyObservation(y);
        }
    }
}

