package Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

public class ACS extends Application {
    private static final int MAX_DATA_POINTS = 50;//最大

    private Series series;
    private float xSeriesData = 0;
    private final ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private Timeline timeline2;
    private NumberAxis xAxis;

    private void init(final Stage primaryStage) {
        xAxis = new NumberAxis(0, MAX_DATA_POINTS, 1);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);

        final NumberAxis yAxis = new NumberAxis(0, 50, 1);
        yAxis.setAutoRanging(false);

        // -- Chart
        final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis) {
            // Override to remove symbols on each data point
            @Override
            protected void dataItemAdded(final Series<Number, Number> series, final int itemIndex, final Data<Number, Number> item) {
            }
        };
        sc.setAnimated(false);
        sc.setId("liveAreaChart");
        sc.setTitle("Animated Area Chart");
        // -- Chart Series
        series = new LineChart.Series<Number, Number>();
        series.setName("Area Chart Series");
        sc.getData().add(series);

        final Scene scene = new Scene(sc, 800, 800);
        scene.getStylesheets().add("site.css");
        sc.getStyleClass().add("thick-chart");

        primaryStage.setScene(scene);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();

        // -- Prepare Executor Services
        executor = Executors.newCachedThreadPool();
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        // -- Prepare Timeline
        prepareTimeline();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    private class AddToQueue implements Runnable {
        double PERIOD = 20;
        double SCALE = 20;
        int pos = 0;

        @Override
        public void run() {
            try {
                final double Min = 20;
                final double Max = 55;
                // add a item of random data to queue
//uncomment the line below to generate a normal graph
                // dataQ.add(Min + (Math.random() * ((Max - Min))));
                dataQ.add(((Math.sin((++pos * 2 * Math.PI) / PERIOD) * (SCALE / 2)) + (SCALE / 2)));
                Thread.sleep(50);
                executor.execute(this);
            } catch (final InterruptedException ex) {
                // Logger.getLogger(ACS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // -- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(final long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
            if (dataQ.isEmpty()) {
                break;
            }
            // series.getData().add(new LineChart.Data(xSeriesData++, dataQ.remove()));

            final Number datapoint = dataQ.remove();
            xSeriesData = xSeriesData + 1;
            System.out.println(xSeriesData + "  " + datapoint);
            series.getData().add(new LineChart.Data(xSeriesData, datapoint));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series.getData().size() > (MAX_DATA_POINTS * 10)) {
            series.getData().remove(0, series.getData().size() - (MAX_DATA_POINTS * 10));
        }
        // update
        xAxis.setLowerBound(xSeriesData - (MAX_DATA_POINTS));
        xAxis.setUpperBound(xSeriesData - 1);
    }
}
