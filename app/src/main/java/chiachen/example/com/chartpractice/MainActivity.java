package chiachen.example.com.chartpractice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chiachen.example.com.chartpractice.LineUtil.gestire.ZoomType;
import chiachen.example.com.chartpractice.LineUtil.listener.LineChartOnValueSelectListener;
import chiachen.example.com.chartpractice.LineUtil.model.Axis;
import chiachen.example.com.chartpractice.LineUtil.model.AxisValue;
import chiachen.example.com.chartpractice.LineUtil.model.Line;
import chiachen.example.com.chartpractice.LineUtil.model.LineChartData;
import chiachen.example.com.chartpractice.LineUtil.model.PointValue;
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
	
	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasLines = true;
	private boolean hasPoints = true;
	private ValueShape shape = ValueShape.CIRCLE;
	private boolean isFilled = false;
	private boolean hasLabels = false;
	private boolean isCubic = false;
	private boolean hasLabelForSelected = false;
	private boolean pointsHaveDifferentColor;
	// private boolean hasGradientToTransparent = false;
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
		resetViewport();
		
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
				mHandler.sendMessageDelayed(mHandler.obtainMessage(),300);
			}
		};
	}

	private int widthEnd = 2;
	private int widthStart = 2;
	private void resetViewport() {
		// Reset viewport height range to (0,100)
		final Viewport v = new Viewport(0, upper_bound, 6, 0);
		chart.setMaximumViewport(v);
		// chart.setCurrentViewport(v);
		final Viewport v1 = new Viewport(widthStart, upper_bound, widthEnd, 0);
		chart.setCurrentViewportWithAnimation(v1);

		// chart.setValueSelectionEnabled(true);
	}
	private void generateValues() {
		for (int i = 0; i < maxNumberOfLines; ++i) {
			for (int j = 0; j < numberOfPoints; ++j) {
				randomNumbersTab[i][j] = (float) Math.random() * 100f;
			}
		}
	}
	
	private void generateData() {
		// int numValues = 7;
		//
		// List<AxisValue> axisValues = new ArrayList<AxisValue>();
		// List<PointValue> values1 = new ArrayList<PointValue>();
		// for (int i = 0; i < numValues; ++i) {
		// 	values1.add(new PointValue(i, 0));
		// 	axisValues.add(new AxisValue(i).setLabel(days[i]));
		// }
		//
		//
		// List<Line> lines = new ArrayList<>();
		// for (int i = 0; i < numberOfLines; ++i) {
		//
		// 	List<PointValue> values = new ArrayList<>();
		// 	for (int j = 0; j < numberOfPoints; ++j) {
		// 		values.add(new PointValue(j, randomNumbersTab[i][j]));
		// 	}
		//
		// 	Line line = new Line(values);
		// 	line.setColor(ChartUtils.COLORS[i]);
		// 	line.setShape(shape);
		// 	line.setCubic(isCubic=true);//curve line
		// 	line.setFilled(isFilled=true);// area
		// 	line.setHasLabels(hasLabels);
		// 	line.setHasLabelsOnlyForSelected(hasLabelForSelected=true);
		// 	line.setHasLines(hasLines);
		// 	line.setHasPoints(hasPoints);
		// 	// line.setHasGradientToTransparent(hasGradientToTransparent);
		// 	if (pointsHaveDifferentColor=true){
		// 		line.setPointColor(ChartUtils.COLORS[1]);
		// 	}
		// 	lines.add(line);
		// }
		//
		// data = new LineChartData(lines);
		
		// if (hasAxes) {
		// 	Axis axisX = new Axis();
		// 	Axis axisY = new Axis().setHasLines(true);
		// 	if (hasAxesNames) {
		// 		axisX.setName("Axis X");
		// 		axisY.setName("Axis Y");
		// 	}
		// 	data.setAxisXBottom(axisX);
		// 	data.setAxisYLeft(axisY);
		// } else {
		// 	data.setAxisXBottom(null);
		// 	data.setAxisYLeft(null);
		// }
		
		// data.setBaseValue(Float.NEGATIVE_INFINITY);
		
		int numValues = 7;
		List<AxisValue> axisValues = new ArrayList< >();
		List<PointValue> values = new ArrayList< >();
		for (int i = 0; i < numValues; ++i) {
			values.add(new PointValue(i, upper_bound/2));
			axisValues.add(new AxisValue(i).setLabel(days[i]));
		}
		
		Line line = new Line(values);
		// line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);
		line.setColor(ChartUtils.COLOR_VIOLET);
		line.setShape(shape);
		// line.setCubic(isCubic=true);//curve line
		line.setFilled(isFilled=true);// area
		line.setHasLabels(hasLabels);
		line.setHasLabelsOnlyForSelected(hasLabelForSelected=true);
		line.setHasLines(hasLines);
		line.setHasPoints(hasPoints);
		
		List<Line> lines = new ArrayList<>();
		lines.add(line);
		
		data = new LineChartData(lines);
		data.setAxisXBottom(new Axis(axisValues).setHasLines(true));
		data.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));
		
		chart.setLineChartData(data);
		chart.setZoomType(ZoomType.HORIZONTAL);

	}
	
	int upper_bound =100;
	
	public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};
	private class ValueTouchListener implements LineChartOnValueSelectListener {
		
		@Override
		public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
			Toast.makeText(MainActivity.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
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
