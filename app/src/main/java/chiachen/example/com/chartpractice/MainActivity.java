package chiachen.example.com.chartpractice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

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
	
	private void resetViewport() {
		// Reset viewport height range to (0,100)
		final Viewport v = new Viewport(chart.getMaximumViewport());
		v.bottom = 0;
		v.top = 100;//y axis end
		v.left = 0;
		v.right = numberOfPoints - 1;//x axis end
		chart.setMaximumViewport(v);
		// chart.setCurrentViewport(v);
		chart.setCurrentViewportWithAnimation(v);
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
		
		List<Line> lines = new ArrayList<>();
		for (int i = 0; i < numberOfLines; ++i) {
			
			List<PointValue> values = new ArrayList<>();
			for (int j = 0; j < numberOfPoints; ++j) {
				values.add(new PointValue(j, randomNumbersTab[i][j]));
			}
			
			Line line = new Line(values);
			line.setColor(ChartUtils.COLORS[i]);
			line.setShape(shape);
			line.setCubic(isCubic=true);//curve line
			line.setFilled(isFilled=true);// area
			line.setHasLabels(hasLabels);
			line.setHasLabelsOnlyForSelected(hasLabelForSelected=true);
			line.setHasLines(hasLines);
			line.setHasPoints(hasPoints);
			// line.setHasGradientToTransparent(hasGradientToTransparent);
			if (pointsHaveDifferentColor){
				line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
			}
			lines.add(line);
		}
		
		data = new LineChartData(lines);
		
		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("Axis X");
				axisY.setName("Axis Y");
			}
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}
		
		data.setBaseValue(Float.NEGATIVE_INFINITY);
		chart.setLineChartData(data);
		
	}
	
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
				value.setTarget(value.getX(), (float) Math.random() * 100);
			}
		}
	}
}
