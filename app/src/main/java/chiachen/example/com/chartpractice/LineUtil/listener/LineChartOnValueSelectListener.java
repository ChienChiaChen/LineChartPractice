package chiachen.example.com.chartpractice.LineUtil.listener;


import chiachen.example.com.chartpractice.LineUtil.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
