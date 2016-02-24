package com.dean.googleplay.recyclerview;

import android.view.View;

/**
 * 实现该接口的对象内部应包含一个视图和关联的数据，数据用于在视图上展示
 * 1.对象的构造方法中调用onCreateView()方法时返回一个视图
 * 2.在外部调用setData(T data)方法传入数据
 * 3.外部调用onUpdateView()方法更新视图，在该方法中通过getData()得到最新数据展示到视图界面
 */
public interface ViewDataBind<BindDataType> {
    /**
     * 1.创建视图
     *
     * @return
     */
    View onCreateView();

    /**
     * 2.初始化视图界面
     */
    void initView();

    /**
     * 3.设置视图绑定的数据对象
     *
     * @param data
     */
    void setData(BindDataType data);

    /**
     * 4.获取视图绑定的数据对象
     *
     * @return
     */
    BindDataType getData();

    /**
     * 5.当数据变更后通知视图更新界面
     */
    void onUpdateView();

    /**
     * 获取创建的根视图
     *
     * @return
     */
    View getView();
}
