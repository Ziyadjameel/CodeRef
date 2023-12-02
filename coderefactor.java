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
        double sum = 0.0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    public void calculate(double divisor) {
        double[][][] processedData = new double[data.length][data[0].length][data[0][0].length];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("RacingStatsData.txt"))) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    for (int k = 0; k < data[0][0].length; k++) {
                        processedData[i][j][k] = data[i][j][k] / divisor - Math.pow(limit[i][j], 2.0);

                        if (calculateAverage(processedData[i][j]) > 10 && calculateAverage(processedData[i][j]) < 50) {
                            break;
                        } else if (Math.max(data[i][j][k], processedData[i][j][k]) > data[i][j][k]) {
                            break;
                        } else if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(processedData[i][j][k]), 3) &&
                                calculateAverage(data[i][j]) < processedData[i][j][k] && (i + 1) * (j + 1) > 0) {
                            processedData[i][j][k] *= 2;
                        }
                    }
                }
            }

            for (double[][] row : processedData) {
                for (double[] column : row) {
                    writer.write(Arrays.toString(column) + "\t");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
