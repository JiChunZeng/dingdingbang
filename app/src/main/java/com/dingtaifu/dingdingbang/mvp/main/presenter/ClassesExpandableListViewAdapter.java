package com.dingtaifu.dingdingbang.mvp.main.presenter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.mvp.main.model.Classes;
import com.dingtaifu.dingdingbang.mvp.main.model.Students;
import com.dingtaifu.dingdingbang.utils.Logs;

import java.util.List;

import static android.R.attr.value;

public class ClassesExpandableListViewAdapter extends BaseExpandableListAdapter {
    // 班级的集合
    private List<Classes> classes;

    // 创建布局使用
    public ClassesExpandableListViewAdapter(List<Classes> classes, Activity activity) {
        Logs.e(classes.toString());
        this.classes = classes;
        this.mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getGroupCount() {
        // 获取一级条目的数量  就是班级的大小
        return classes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 获取对应一级条目下二级条目的数量，就是各个班学生的数量
        return classes.get(groupPosition).students.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // 获取一级条目的对应数据  ，感觉没什么用
        return classes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // 获取对应一级条目下二级条目的对应数据  感觉没什么用
        return classes.get(groupPosition).students.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // 直接返回，没什么用
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // 直接返回，没什么用
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // 谁知道这个是干什么。。。。
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // 获取对应一级条目的View  和ListView 的getView相似

        return getGenericView1( groupPosition, convertView);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // 获取对应二级条目的View  和ListView 的getView相似
        return getGenericView2(groupPosition,childPosition,  convertView);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // 根据方法名，此处应该表示二级条目是否可以被点击   先返回true 再讲
        return true;
    }


    /**
     * 根据字符串生成布局，，因为我没有写layout.xml 所以用java 代码生成
     *
     *      实际中可以通过Inflate加载自己的自定义布局文件，设置数据之后并返回
     * @param string
     * @return
     */
    private LayoutInflater mInflater;

    class ViewHolder{
        public TextView title;
        public TextView value;
    }

    private View getGenericView1(int position,  View convertView) {

        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();

            convertView = mInflater.inflate(R.layout.layout_item_child, null);
            holder.title = convertView.findViewById(R.id.tv_child_title);
            holder.value = convertView.findViewById(R.id.tv_child_value);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }


        holder.title.setText(classes.get(position).name);
        holder.value.setText(classes.get(position).value);
        return convertView;
    }

    private View getGenericView2(int groupPosition,int childPosition,  View convertView) {

        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();

            convertView = mInflater.inflate(R.layout.layout_item_child_child, null);
            holder.title =convertView.findViewById(R.id.tv_child_title);
            holder.value = convertView.findViewById(R.id.tv_child_value);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(classes.get(groupPosition).students.get(childPosition).getName());
        holder.value.setText(classes.get(groupPosition).students.get(childPosition).getValue());
        return convertView;
    }
}
