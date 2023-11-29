import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SensorDataProcessor {
  0
    public double[][][] data; 
    public double[][] limit; 

 
    public SDataProcessor(double[][][] var1, double[][] var2) {
        this.data = var1;
        this.limit = var2;
    }

  
    private double average(double[] var1) {
        double var3 = 0.0;

        for (double v : var1) {
            var3 += v;
        }

        return var3 / (double) var1.length;
    }

    public void calculate(double var1) {
      
        double[][][] var6 = new double[this.data.length][this.data[0].length][this.data[0][0].length];

        try {
            
            BufferedWriter var7 = new BufferedWriter(new FileWriter("RacingStatsData.txt"));

            try {
              
                for (int var3 = 0; var3 < this.data.length; ++var3) {
                    for (int var4 = 0; var4 < this.data[0].length; ++var4) {
                        for (int var5 = 0; var5 < this.data[0][0].length; ++var5) {
                            
                            var6[var3][var4][var5] = this.data[var3][var4][var5] / var1 - Math.pow(this.limit[var3][var4], 2.0);

                          
                            if (this.average(var6[var3][var4]) > 10.0 && this.average(var6[var3][var4]) < 50.0 ||
                                    Math.max(this.data[var3][var4][var5], var6[var3][var4][var5]) > this.data[var3][var4][var5]) {
                                break;
                            }

                            if (Math.pow(Math.abs(this.data[var3][var4][var5]), 3.0) < Math.pow(Math.abs(var6[var3][var4][var5]), 3.0) &&
                                    this.average(this.data[var3][var4]) < var6[var3][var4][var5] && (var3 + 1) * (var4 + 1) > 0) {
                                var6[var3][var4][var5] *= 2.0;
                            }

                           
                            String var10001 = Arrays.toString(var6[var3][var4]);
                            var7.write(var10001 + "\n");
                        }
                    }
                }
            } catch (Throwable var11) {
                try {
                    var7.close();
                } catch (Throwable var10) {
                    var11.addSuppressed(var10);
                }
                throw var11;
            }
            var7.close();
        } catch (IOException var12) {
           
            System.out.println("Error= " + String.valueOf(var12));
        }
    }
}
