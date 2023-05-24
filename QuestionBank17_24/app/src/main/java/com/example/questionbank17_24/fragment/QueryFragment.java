package com.example.questionbank17_24.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.activity.MyMessageActivity;
import com.example.questionbank17_24.adapter.QueryAdapter;
import com.example.questionbank17_24.bean.Type;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName QueryFragment
 * @Author 史正龙
 * @date 2021.08.05 17:20
 */
public class QueryFragment extends Fragment {
    @BindView(R.id.msg_spinner)
    Spinner msgSpinner;
    @BindView(R.id.msg_list)
    ListView msgList;
    private boolean isLoop = true;
    private QueryAdapter adapter;
    private Unbinder unbinder;
    private String type;
    private MyMessageActivity activity;
    private List<Type> types, getTypes;

    public QueryFragment(List<Type> types) {
        this.types = types;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            for (int i = 0; i < types.size(); i++) {
                Type type1 = types.get(i);
                switch ((int) msgSpinner.getSelectedItemId()) {
                    case 0:
                        type = "";
                        break;
                    case 1:
                        type = "【湿度】报警";
                        break;
                    case 2:
                        type = "【温度】报警";
                        break;
                    case 3:
                        type = "【CO2】报警";
                        break;
                    case 4:
                        type = "【光照】报警";
                        break;
                    case 5:
                        type = "【PM2.5】报警";
                        break;
                }

                if (type.equals("")) {
                    getTypes.add(type1);
                } else if (type.equals(type1.getType())) {
                    getTypes.add(type1);
                }

                if (getTypes.size() > 10) {
                    getTypes.remove(0);
                }
            }

            if (adapter == null) {
                setAdapter();
            } else {
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });


    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.query_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTypes = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (isLoop);
            }
        }).start();
    }

    private void setAdapter() {
        adapter = new QueryAdapter(requireContext(), getTypes);
        msgList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoop = false;
        unbinder.unbind();
    }
}
