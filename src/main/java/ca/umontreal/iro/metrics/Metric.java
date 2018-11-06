package ca.umontreal.iro.metrics;

public interface Metric {
    /**
     * @return description of the metric
     */
    String getDescription();

    /**
     * @return calculated value of the metric
     */
    Number getValue();
}
