package ca.umontreal.iro.metrics;

public interface Metric {
    /**
     * Description of the metric.
     *
     * @return description of the metric
     */
    public String getDescription();

    /**
     * Calculated value of the metric.
     *
     * @return value of the metric
     */
    public Number getValue();
}
