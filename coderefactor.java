import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SensorDataProcessor {

    private double[][][] data;
    private double[][] limit;

    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    private double calculateAverage(double[] array) {
        double sum = Arrays.stream(array).sum();
        return sum / array.length;
    }

    public void calculate(double divisor) {
        double[][][] processedData = new double[data.length][data[0].length][data[0][0].length];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("RacingStatsData.txt"))) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    for (int k = 0; k < data[i][j].length; k++) {
                        double normalizedValue = (data[i][j][k] / divisor) - Math.pow(limit[i][j], 2.0);

                        // Check for anomalies
                        double average = calculateAverage(processedData[i][j]);
                        if (average > 10 && average < 50) {
                            break;
                        } else if (Math.max(data[i][j][k], normalizedValue) > data[i][j][k]) {
                            break;
                        } else if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(normalizedValue), 3) &&
                                calculateAverage(data[i][j]) < normalizedValue && (i + 1) * (j + 1) > 0) {
                            normalizedValue *= 2;
                        }

                        processedData[i][j][k] = normalizedValue;
                    }
                }
            }

            for (double[][] row : processedData) {
                writer.write(Arrays.deepToString(row) + "\t");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
