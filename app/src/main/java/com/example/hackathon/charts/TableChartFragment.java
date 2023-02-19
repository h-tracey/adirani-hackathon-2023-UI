package com.example.hackathon.charts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daivd.chart.core.LineChart;
import com.example.hackathon.R;
import com.example.hackathon.gson.TableDataSealed;

public class TableChartFragment extends Fragment {
    private String _scatter;
    private String _conversion;
    private String _touch;
    public static TableChartFragment newInstance(String scatter,String conversion,String touch) {

        TableChartFragment fragment = new TableChartFragment();
        Bundle args = new Bundle();
        args.putString("scatter", scatter);
        args.putString("conversion", conversion);
        args.putString("touch", touch);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _scatter = getArguments().getString("scatter");
            _conversion = getArguments().getString("conversion");
            _touch = getArguments().getString("touch");
        }
        initData();
    }

    private void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scatter_chart, container, false);
        findViews(view);
        setUI();
        return view;
    }

    private void findViews(View view) {
        view.findViewById(R.id.tv_show_chart_scatter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(requireContext(), ScatterChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("data", _scatter);
                intent.putExtras(bundle);
                if (intent.resolveActivity(requireContext().getPackageManager()) != null)
                    requireContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.tv_show_chart_touch_point).setVisibility(View.GONE);
//        view.findViewById(R.id.tv_show_chart_touch_point).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(requireContext(), LineChartActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("data", _touch);
//                intent.putExtras(bundle);
//                if (intent.resolveActivity(requireContext().getPackageManager()) != null)
//                    requireContext().startActivity(intent);
//            }
//        });
        view.findViewById(R.id.tv_show_chart_conversion_total).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(requireContext(), BarChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("data", _conversion);
                intent.putExtras(bundle);
                if (intent.resolveActivity(requireContext().getPackageManager()) != null)
                    requireContext().startActivity(intent);
            }
        });
    }

    private void setUI() {

    }


}
