package chiachen.example.com.chartpractice.LineUtil.formatter;


import chiachen.example.com.chartpractice.LineUtil.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
