/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package sirius.kernel.health.metrics;

import sirius.kernel.commons.Amount;
import sirius.kernel.commons.Explain;
import sirius.kernel.commons.NumberFormat;

import java.util.Objects;

/**
 * Represents a measured value, recorded by the metrics framework.
 * <p>
 * Basically this combines a name along with a value and unit. Additionally an interpretation of the value is
 * given as state.
 */
public class Metric implements Comparable<Metric> {

    private final String unit;
    private String name;
    private double value;
    private MetricState state;

    /**
     * Creates a new metric using the given values
     *
     * @param name  the name of the metric
     * @param value the actual value
     * @param state the interpretation of the value
     * @param unit  the unit in which the value is measured or <tt>null</tt> if there is no unit
     */
    public Metric(String name, double value, MetricState state, String unit) {
        this.unit = unit;
        this.name = name;
        this.value = Double.isNaN(value) ? 0 : value;
        this.state = state;
    }

    /**
     * Returns the name of the metric.
     *
     * @return the name of the metric
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the actual value of the metric.
     *
     * @return the measured value of the metric
     */
    public double getValue() {
        return value;
    }

    /**
     * The unit in which the value is measured
     *
     * @return the unit of the value or <tt>null</tt> if there is no unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * String representation of the value along with its unit (is necessary)
     *
     * @return a string representation of the value
     */
    public String getValueAsString() {
        return Amount.of(value).toSmartRoundedString(NumberFormat.TWO_DECIMAL_PLACES).append(" ", unit).toString();
    }

    /**
     * Returns an interpretation of the value (@see MetricState).
     *
     * @return an interpretation of the value
     */
    public MetricState getState() {
        return state;
    }

    @Override
    @SuppressWarnings("squid:S1698")
    @Explain("Indentity against this is safe and a shortcut to speed up comparisons")
    public int compareTo(Metric o) {
        if (o == null) {
            return -1;
        }
        if (o == this) {
            return 0;
        }
        if (o.state != state) {
            return o.state.ordinal() - state.ordinal();
        }
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metric)) {
            return false;
        }
        Metric metric = (Metric) o;
        return Objects.equals(name, metric.name) && Objects.equals(state, metric.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, state);
    }
}
