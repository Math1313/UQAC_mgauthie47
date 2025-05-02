package uqac.dim.bestroommate.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.TreeFillingMethod;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.databinding.FragmentWelcomeBinding;
import uqac.dim.bestroommate.model.DataManager;
import uqac.dim.bestroommate.model.data.Tazz;
import uqac.dim.bestroommate.model.data.User;

public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding _binding;

    private Cartesian usersPointsPie;

    private final DataManager dataManager = DataManager.getInstance();

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        for(Tazz task : dataManager.getCollocation().getTasks())
        {
            Log.d("LIST_TASK", task.toString());
        }

        this.initialize();
        return this._binding.getRoot();

    }

    private void initialize() {
        ((MainActivity) this.requireActivity()).changeBottomNavigationVisibility(true);
        // Update the support action bar
        ((MainActivity) this.requireActivity()).updateSupportActionBar(getString(R.string.app_name), false, false);

        this.constructPieChartFromUserPoint();
    }

    // Construct the Chart to show the point per user in the collocation
    // It's just a builder for the Column chart
    private void constructPieChartFromUserPoint() {

        if(this.usersPointsPie == null){
            this.usersPointsPie = AnyChart.column();
        }
        usersPointsPie.title(
                super.getString(R.string.chart_name, this.dataManager.getCollocation().getName())
        );

        usersPointsPie.labels(true);

        List<DataEntry> pieChartData = new ArrayList<>();
        for (User user : dataManager.getCollocation().getUsers()) {
            pieChartData.add(new ValueDataEntry(user.toString(), user.getPoints()));
        }
        usersPointsPie.data(pieChartData);

        this._binding.pieChartUsers.setChart(usersPointsPie);
    }

}