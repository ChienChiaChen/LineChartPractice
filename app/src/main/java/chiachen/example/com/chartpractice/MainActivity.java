package chiachen.example.com.chartpractice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chiachen.example.com.chartpractice.LineUtil.listener.LineChartOnValueSelectListener;
import chiachen.example.com.chartpractice.LineUtil.model.Axis;
import chiachen.example.com.chartpractice.LineUtil.model.AxisValue;
import chiachen.example.com.chartpractice.LineUtil.model.Line;
import chiachen.example.com.chartpractice.LineUtil.model.LineChartData;
import chiachen.example.com.chartpractice.LineUtil.model.PointValue;
import chiachen.example.com.chartpractice.LineUtil.model.SelectedValue;
import chiachen.example.com.chartpractice.LineUtil.model.ValueShape;
import chiachen.example.com.chartpractice.LineUtil.model.Viewport;
import chiachen.example.com.chartpractice.LineUtil.util.ChartUtils;
import chiachen.example.com.chartpractice.LineUtil.view.LineChartView;


public class MainActivity extends AppCompatActivity {
	private LineChartView chart;
	private LineChartData data;
	private int numberOfLines = 1;
	private int maxNumberOfLines = 4;
	private int numberOfPoints = 19;
	float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
	public String[] days = new String[] {
			"1/1", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9", "1/10", "1/11"};
	

	private boolean hasLines = true;
	private boolean hasPoints = true;
	private ValueShape shape = ValueShape.CIRCLE;
	private boolean isFilled = true;
	private boolean hasLabels = false;
	private boolean hasLabelForSelected = true;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		chart = (LineChartView) findViewById(R.id.chart);
		chart.setOnValueTouchListener(new ValueTouchListener());
		chart.setViewportCalculationEnabled(false);
		// Generate some random values.
		generateValues();
		generateData();
		
		findViewById(R.id.btn_one_day).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHandler.sendEmptyMessage(1);
			}
		});
		findViewById(R.id.btn_one_month).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacksAndMessages(null);
			}
		});
		
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				prepareDataAnimation();
				chart.startDataAnimation();
			}
		};
	}

	private void resetViewport(LineChartData data) {
		int length = data.getLines().get(0).getValues().size() - 1;
		final Viewport v = new Viewport(0, upper_bound, days.length, 0);
		final Viewport v1 = new Viewport(length - 5, upper_bound, length, 0);
		chart.setMaximumViewport(v);
		chart.setCurrentViewport(v1);
		chart.setValueSelectionEnabled(true);
	}

	private void generateValues() {
		for (int i = 0; i < maxNumberOfLines; ++i) {
			for (int j = 0; j < numberOfPoints; ++j) {
				randomNumbersTab[i][j] = (float) Math.random() * 100f;
			}
		}
	}
	
	private void generateData() {
		int numValues =  days.length;
		List<AxisValue> axisValues = new ArrayList< >();
		List<PointValue> values = new ArrayList< >();
		for (int i = 0; i < numValues; ++i) {
			values.add(new PointValue(i, upper_bound/2));
			axisValues.add(new AxisValue(i).setLabel(days[i]));
		}
		
		Line line = new Line(values);
		line.setColor(ChartUtils.COLOR_VIOLET);
		line.setShape(shape);
		line.setFilled(isFilled);// area
		line.setHasLabels(hasLabels);
		line.setHasLabelsOnlyForSelected(hasLabelForSelected);
		line.setHasLines(hasLines);
		line.setHasPoints(hasPoints);
		line.setSelectedColor(ChartUtils.COLOR_BLUE);

		List<Line> lines = new ArrayList<>();
		lines.add(line);
		
		data = new LineChartData(lines);
		data.setAxisXBottom(new Axis(axisValues).setTextColor(ChartUtils.COLOR_RED).setLineColor(ChartUtils.COLOR_ORANGE).setSelectedColor(ChartUtils.COLOR_BLUE));
		chart.setLineChartData(data);
		chart.getAxesRenderer().setAxesSelected(numValues - 1);
		chart.setZoomEnabled(false);
		chart.selectValue(new SelectedValue(lines.size() - 1, numValues - 1, SelectedValue.SelectedValueType.LINE));

		resetViewport(data);
	}
	
	int upper_bound =100;
	

	private class ValueTouchListener implements LineChartOnValueSelectListener {
		
		@Override
		public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
			Toast.makeText(MainActivity.this, "lineIndex: "+lineIndex+"\npointIndex: "+pointIndex+"\nSelected: " + value, Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onValueDeselected() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void prepareDataAnimation() {
		for (Line line : data.getLines()) {
			for (PointValue value : line.getValues()) {
				// Here I modify target only for Y values but it is OK to modify X targets as well.
				value.setTarget(value.getX(), (float) Math.random() * 80);
			}
		}
	}
}
