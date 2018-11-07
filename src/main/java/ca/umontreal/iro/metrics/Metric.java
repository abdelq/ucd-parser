package ca.umontreal.iro.metrics;

public interface Metric {
    /**
     * Description of the metric.
     *
     * @return description of the metric
     */
    String getDescription();

    /**
     * Calculated value of the metric.
     *
     * @return value of the metric
     */
    Number getValue();
}
