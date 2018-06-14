package Test;

    import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
    import org.jfree.ui.RefineryUtilities;


public class sin extends ApplicationFrame implements ActionListener {

        public XYSeries series;

        public sin(final String title) {

            super(title);
            series = new XYSeries("Sine", true, true);
            XYSeriesCollection dataset = new XYSeriesCollection(series);
            final JFreeChart chart = createChart(dataset);

            final ChartPanel chartPanel = new ChartPanel(chart);
            final JButton button = new JButton("Add New Data Item");
            button.setActionCommand("ADD_DATA");
            button.addActionListener(this);

            final JPanel content = new JPanel(new BorderLayout());
            content.add(chartPanel);
            content.add(button, BorderLayout.SOUTH);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            setContentPane(content);

        }

        private JFreeChart createChart(final XYDataset dataset) {
            JFreeChart jfreechart = ChartFactory.createXYLineChart("Sin Curve", "Angle (Deg)", "Value", dataset, PlotOrientation.VERTICAL, true, true, true);
            jfreechart.setBackgroundPaint(Color.white);
            XYPlot xyplot = (XYPlot) jfreechart.getPlot();
            xyplot.setBackgroundPaint(Color.lightGray);
            xyplot.setDomainGridlinePaint(Color.white);
            xyplot.setRangeGridlinePaint(Color.white);
            return jfreechart;
        }

        public void actionPerformed(final ActionEvent e) {
            if (e.getActionCommand().equals("ADD_DATA")) {
                for (int i = 0; i < 100; i++) {
                    final double x = (i)/10.0 ;
                    final double y = Math.sin(x);
                    this.series.addOrUpdate(x, y);
                }
            }
        }

        public static void main(final String[] args) {

            final sin demo = new sin("Dynamic Data view");
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
        }
    }

