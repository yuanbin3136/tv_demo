
# 可以多滚动一段的Rv控件
* [code](https://github.com/yuanbin3136/tv_demo/blob/master/app/src/main/java/com/wind/yuanbin/demo/waterfall/RecyclerView_MoreScroll.java)

焦点在RV边缘的时候继续按键，Rv会根据下一个焦点位置的item是否在处在完全可见的状态，如果不可见则需要调用smoothScrollBy()滚动至相应位置。
![png](https://github.com/yuanbin3136/tv_demo/raw/master/app/img/RecyclerView_MoreScroll0.png?raw=true"RecyclerView")
通过在这个方法中修改滚动的值，让Rv多滚动一段距离。
还需要监听滚动的方向，来确定多滚动的值是否为负数。
