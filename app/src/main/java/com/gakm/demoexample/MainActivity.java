package com.gakm.demoexample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gakm.flowlayoutlib.FlowAdapter;
import com.gakm.flowlayoutlib.L;
import com.gakm.flowlayoutlib.XFlowLayout;

/**
 * @author DemoExample
 * @company 广安科贸
 * @date 2020/6/12 0012 17:11
 */
public class MainActivity extends AppCompatActivity {
    private int[] array = new int[]{R.color.colorPrimary,
            R.color.FF8EE5EE,
            R.color.colorAccent,
            R.color.A52A2A};
    private String[] title = new String[]{"宋江提出招安",
            "武松和李逵都反对",
            "为何宋江",
            "杀李逵？", "宋江提出招安",
            "武松和李逵都反对",
            "为何宋江",
            "杀李逵？", "宋江提出招安",
            "武松和李逵都反对",
            "为何宋江",
            "杀李逵？", "宋江提出招安",
            "武松和李逵都反对",
            "为何宋江",
            "杀李逵？", "宋江提出招安",
            "武松和李逵都反对",
            "为何宋江",
            "杀李逵？", "宋江提出招安",
            "武松和李逵都反对",
            "为何宋江",
            "杀李逵？"};
    private FlowAdapter<String> stringFlowAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFlaTabLayout();

    }

    private void initFlaTabLayout() {
        XFlowLayout flowLayout = findViewById(R.id.root_container);
        stringFlowAdapter = new FlowAdapter<String>(title) {
            @NonNull
            @Override
            protected View getItemView(int position) {
                L.d("position===" + position);
                TextView apply = new TextView(MainActivity.this);
                apply.setText(stringFlowAdapter.getItems().get(position % 4) + "+ \"==$position\"");
                apply.setBackgroundColor(
                        ContextCompat.getColor(
                                MainActivity.this,
                                array[position % 4]
                        )
                );
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) apply.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                }
                layoutParams.topMargin = 20;
                layoutParams.leftMargin = 20;
                layoutParams.rightMargin = 20;
                layoutParams.bottomMargin = 20;
                apply.setLayoutParams(layoutParams);
                return apply;
            }
        };
        flowLayout.setAdapter(stringFlowAdapter);

        stringFlowAdapter.addItemClickListener(new FlowAdapter.OnViewClickListener() {
            @Override
            public void onViewClick(View v, int position) {
                Toast.makeText(MainActivity.this, "addItemClickListener===" + position, Toast.LENGTH_SHORT).show();
            }
        });
        stringFlowAdapter.addItemLongClickListener(new FlowAdapter.OnViewLongClickListener() {
            @Override
            public void onViewLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "addItemLongClickListener===" + position, Toast.LENGTH_SHORT).show();
            }
        });
        final TextView viewById = findViewById(R.id.tv_onclick);

        viewById
                .setOnClickListener(new View.OnClickListener() {
                    int count = 0;

                    @Override
                    public void onClick(View v) {
                        String s = viewById.getText().toString();
                        if (s.isEmpty()) {
                            stringFlowAdapter.addData("我愛中國你了");
                        }
                        if (s.equals("3")){
                            stringFlowAdapter.addData("我愛中國你了");
                        }
                        viewById.setText(String.valueOf(count));
                        count++;

                    }
                });
    }
}
