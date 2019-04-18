# tv_demo

# 演示一些TV的界面，
## 1.比如焦点居中的list
[code](https://github.com/yuanbin3136/tv_demo/blob/master/app/src/main/java/com/wind/yuanbin/demo/ChannelActivity.java)
效果图：
![Renderings](https://github.com/yuanbin3136/tv_demo/raw/master/app/img/GIF.gif "““鼠标移到这上面显示文字？”“")   
## 2.瀑布流
效果图：多种类型条目的瀑布流，可以很方便的修改几个参数来调整每种模块的显示效果
![png](https://github.com/yuanbin3136/tv_demo/raw/master/app/img/Screenshot_1555258209.png?raw=true"")
![png](https://github.com/yuanbin3136/tv_demo/raw/master/app/img/waterfall2.png?raw=true"")
### 1.多滚动一段的RecyclerView: 	RecyclerView_MoreScroll
* [md](./app/md/RecyclerView_moreScrll.md)

### 2.枚举类型，把多模块的数据统一管理

### 3.在RecyclerView_MoreScroll(竖)中 嵌套 RecyclderView(Grid)的结构

### 4.焦点条目放大时被其他条目阻挡的问题//未完成，可能从绘制顺序上处理，还有父布局的父布局中修改clipChildren属性。