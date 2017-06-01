package chiachen.example.com.chartpractice.LineUtil.model;

import android.graphics.Color;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import chiachen.example.com.chartpractice.LineUtil.formatter.AxisValueFormatter;
import chiachen.example.com.chartpractice.LineUtil.formatter.SimpleAxisValueFormatter;
import chiachen.example.com.chartpractice.LineUtil.util.ChartUtils;

/**
 * Single axis model. By default axis is auto-generated. Use {@link #setAutoGenerated(boolean)} to disable axis values
 * generation and set values manually using {@link #setValues(List)}. If Axis is auto-generated {@link #setValues(List)}
 * will be ignored but if you set some values Axis will switch to manual mode. Change how axis labels are displayed by
 * changing formatter {@link #setFormatter(lecho.lib.hellocharts.formatter.AxisValueFormatter)}. Axis can have a name
 * that should be displayed next to
 * labels(that depends on renderer implementation), you can change name using {@link #setName(String)}, by default axis
 * name is empty and therefore not displayed.
 */
public class Axis {
    public static final int DEFAULT_TEXT_SIZE_SP = 12;
    public static final int DEFAULT_MAX_AXIS_LABEL_CHARS = 3;
    /**
     * Text size for axis labels and name.
     */
    private int textSize = DEFAULT_TEXT_SIZE_SP;
    /**
     * Maximum number of characters used for this axis. Used to determine axis dimensions.
     */
    private int maxLabelChars = DEFAULT_MAX_AXIS_LABEL_CHARS;
    /**
     * Axis values, each value will be used to calculate its label position.
     */
    private List<AxisValue> values = new ArrayList<AxisValue>();
    /**
     * Name for this axis.
     */
    private String name;
    /**
     * If true axis will be generated to automatically fit chart ranges. *
     */
    private boolean isAutoGenerated = true;
    /**
     * If true renderer will draw lines(grid) for this axis.
     */
    private boolean hasLines = false;
    /**
     * If true axis labels will be drown inside chart area.
     */
    private boolean isInside = false;
    /**
     * Axis labels and name text color.
     */
    private int textColor = Color.LTGRAY;
    /**
     * Axis grid lines color.
     */
    private int lineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
    /**
     * Typeface for labels and name text.
     */
    private Typeface typeface;

    /**
     * Formatter used to format labels.
     */
    private AxisValueFormatter formatter = new SimpleAxisValueFormatter();

    /**
     * If true draws a line between the labels and the graph *
     */
    private boolean hasSeparationLine = true;

    private boolean hasTiltedLabels = false;

    private int selectedColor;

    /**
     * Creates auto-generated axis without name and with default formatter.
     */
    public Axis() {
    }

    /**
     * Creates axis with given values(not auto-generated) without name and with default formatter.
     */
    public Axis(List<AxisValue> values) {
        setValues(values);
    }

    public Axis(Axis axis) {
        this.name = axis.name;
        this.isAutoGenerated = axis.isAutoGenerated;
        this.hasLines = axis.hasLines;
        this.isInside = axis.isInside;
        this.textColor = axis.textColor;
        this.lineColor = axis.lineColor;
        this.textSize = axis.textSize;
        this.maxLabelChars = axis.maxLabelChars;
        this.typeface = axis.typeface;
        this.formatter = axis.formatter;
        this.hasSeparationLine = axis.hasSeparationLine;

        for (AxisValue axisValue : axis.values) {
            this.values.add(new AxisValue(axisValue));
        }
    }

    /**
     * Generates Axis with values from start to stop inclusive.
     */
    public static Axis generateAxisFromRange(float start, float stop, float step) {

        List<AxisValue> values = new ArrayList<AxisValue>();
        for (float value = start; value <= stop; value += step) {
            AxisValue axisValue = new AxisValue(value);
            values.add(axisValue);
        }

        Axis axis = new Axis(values);
        return axis;
    }

    /**
     * Generates Axis with values from given list.
     */
    public static Axis generateAxisFromCollection(List<Float> axisValues) {
        List<AxisValue> values = new ArrayList<AxisValue>();
        int index = 0;
        for (float value : axisValues) {
            AxisValue axisValue = new AxisValue(value);
            values.add(axisValue);
            ++index;
        }

        Axis axis = new Axis(values);
        return axis;
    }

    /**
     * Generates Axis with values and labels from given lists, both lists must have the same size.
     */
    public static Axis generateAxisFromCollection(List<Float> axisValues, List<String> axisValuesLabels) {
        if (axisValues.size() != axisValuesLabels.size()) {
            throw new IllegalArgumentException("Values and labels lists must have the same size!");
        }

        List<AxisValue> values = new ArrayList<AxisValue>();
        int index = 0;
        for (float value : axisValues) {
            AxisValue axisValue = new AxisValue(value).setLabel(axisValuesLabels.get(index));
            values.add(axisValue);
            ++index;
        }

        Axis axis = new Axis(values);
        return axis;
    }

    public List<AxisValue> getValues() {
        return values;
    }

    public Axis setValues(List<AxisValue> values) {
        if (null == values) {
            this.values = new ArrayList<AxisValue>();
        } else {
            this.values = values;
        }

        this.isAutoGenerated = false;
        return this;
    }

    public String getName() {
        return name;
    }

    public Axis setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isAutoGenerated() {
        return isAutoGenerated;
    }

    public Axis setAutoGenerated(boolean isAutoGenerated) {
        this.isAutoGenerated = isAutoGenerated;
        return this;
    }

    public boolean hasLines() {
        return hasLines;
    }

    public Axis setHasLines(boolean hasLines) {
        this.hasLines = hasLines;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public Axis setTextColor(int color) {
        this.textColor = color;
        return this;
    }

    /**
     * @see #setInside(boolean)
     */
    public boolean isInside() {
        return isInside;
    }

    /**
     * Set to true if you want axis values to be drawn inside chart area(axis name still will be drawn outside), by
     * default this is set to false and axis is drawn outside chart area.
     */
    public Axis setInside(boolean isInside) {
        this.isInside = isInside;
        return this;
    }

    public int getLineColor() {
        return lineColor;
    }

    public Axis setLineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public Axis setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getMaxLabelChars() {
        return maxLabelChars;
    }

    /**
     * Set maximum number of characters for axis labels, min 0, max 32.
     */
    public Axis setMaxLabelChars(int maxLabelChars) {
        if (maxLabelChars < 0) {
            maxLabelChars = 0;
        } else if (maxLabelChars > 32) {
            maxLabelChars = 32;
        }
        this.maxLabelChars = maxLabelChars;
        return this;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public Axis setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public AxisValueFormatter getFormatter() {
        return formatter;
    }

    public Axis setFormatter(AxisValueFormatter formatter) {
        if (null == formatter) {
            this.formatter = new SimpleAxisValueFormatter();
        } else {
            this.formatter = formatter;
        }
        return this;
    }

    /**
     * Set true if you want to draw separation line for this axis, set false to hide separation line, by default true.
     */
    public Axis setHasSeparationLine(boolean hasSeparationLine) {
        this.hasSeparationLine = hasSeparationLine;
        return this;
    }

    public boolean hasSeparationLine() {
        return hasSeparationLine;
    }

    public boolean hasTiltedLabels() {
        return hasTiltedLabels;
    }

    public Axis setHasTiltedLabels(boolean hasTiltedLabels) {
        this.hasTiltedLabels = hasTiltedLabels;
        return this;
    }

    public int getSelectedColor() {
        return selectedColor;
    }
    
    public Axis setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        return this;
    }
}
