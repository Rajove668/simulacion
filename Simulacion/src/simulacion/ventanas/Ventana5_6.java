package simulacion.ventanas;

import java.awt.Color;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Ventana5_6 extends javax.swing.JFrame {

    public Ventana5_6(Map<Float, Float> costos_P1, Map<Float, Float> costos_P2, int N_CORRIDAS_5_6) {
        super("Costos entre Politica 1 y 2");
        XYSeriesCollection datos = new XYSeriesCollection();
        XYSeries politica1 = new XYSeries("Politica 1");
        for (Float costo : costos_P1.keySet()) {
            politica1.add(costos_P1.get(costo), costo);
        }
        datos.addSeries(politica1);
        XYSeries politica2 = new XYSeries("Politica 2");
        for (Float costo : costos_P2.keySet()) {
            politica2.add(costos_P2.get(costo), costo);
        }
        datos.addSeries(politica2);
        JFreeChart grafica = ChartFactory.createScatterPlot(
                "Costos entre Politica 1 y 2 (" + N_CORRIDAS_5_6 + " Corridas)",
                "# De veces", "Costo [$]", datos);
        XYPlot plot = (XYPlot) grafica.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));
        ChartPanel panel = new ChartPanel(grafica);
        setContentPane(panel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
