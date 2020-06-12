# FlowLayout
更加简易的流式布局 FlowLayout
### 在布局中引入即可 可以动态加载子View 或者在xml 中固定写死几个
###### 布局中写死
```
<com.gakm.demoexample.XFlowLayout
        android:id="@+id/root_container"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        >
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />
            ..........
    </com.gakm.demoexample.XFlowLayout>
        
```
#### 动态加载 
```
adapter = new FlowAdapter<String>(title) {
            @NonNull
            @Override
            protected View getItemView(int position) {       
                return new TextView(context);
            }
        };
        mlowLayout.setAdapter(adapter)
```
#### item 的点击事件和长按事件
```
 adapter.addItemClickListener(new FlowAdapter.OnViewClickListener() {
            @Override
            public void onViewClick(View v, int position) {
                Toast.makeText(MainActivity.this, "addItemLongClickListener===" + position, Toast.LENGTH_SHORT).show();
            }
        });
        stringFlowAdapter.addItemLongClickListener(new FlowAdapter.OnViewLongClickListener() {
            @Override
            public void onViewLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "addItemLongClickListener===" + position, Toast.LENGTH_SHORT).show();
            }
        });
        ```
