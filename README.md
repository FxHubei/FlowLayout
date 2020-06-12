# FlowLayout
![image](https://github.com/fuxianglxf/FlowLayout/blob/master/Screenshot_1591961571.png)

##  更加简易的流式布局 FlowLayout
### 既可以在布局中添加子view 也可以动态加载
###### 布局中写死
```
   <com.gakm.flowlayoutlib.XFlowLayout
        android:id="@+id/root_container"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </com.gakm.flowlayoutlib.XFlowLayout>
        
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
        adapter.addItemLongClickListener(new FlowAdapter.OnViewLongClickListener() {
            @Override
            public void onViewLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "addItemLongClickListener===" + position, Toast.LENGTH_SHORT).show();
            }
        });
        
```
#### item 添加或者移除
```
     addData(@NonNull List<K> list) 
        
     addData(@NonNull K data)
     
     removeItem(int position)
     
    removeItem(int position, int count)
```
### item 数据刷新
```
notifyDataSetChanged()

notifyItemRangeInserted(int positionStart, int itemCount)

notifyItemRangeRemoved(int positionStart, int itemCount) {
   
```
### 自定义属性 

| 属性  |  类型| 描述 |
| :-----| ----: | :----: |
| itemClick     | boole | false 全部不实现点击 反之 |
| itemLongClick | boole | false 全部不实现点击 反之 |
