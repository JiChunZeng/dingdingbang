package com.dingtaifu.dingdingbang.mvp.main.presenter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.mvp.main.model.College;
import com.dingtaifu.dingdingbang.utils.Logs;

import java.util.List;

public class SimpleExpandableListViewAdapter extends BaseExpandableListAdapter {
    // 大学的集合
    private List<College> colleges;
    private Activity activity;

    public SimpleExpandableListViewAdapter(List<College> colleges, Activity activity) {
        Logs.e(colleges.toString());
        this.colleges = colleges;
        this.activity = activity;
        this.mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getGroupCount() {
        return colleges.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 很关键，，一定要返回  1
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return colleges.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return colleges.get(groupPosition).classList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        return getGenericView(groupPosition, convertView);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // 返回子ExpandableListView 的对象  此时传入是该父条目，即大学的对象（有歧义。。）
        return getGenericExpandableListView(colleges.get(groupPosition));
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private LayoutInflater mInflater;

    class ViewHolder {
        public TextView title;
        public ImageView iv_icon;
    }

    private View getGenericView(int position, View convertView) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_item_group, null);
            holder.title = (TextView) convertView.findViewById(R.id.tv_child_title);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(colleges.get(position).name);
        holder.iv_icon.setImageResource(colleges.get(position).icon);
        return convertView;
    }


    /**
     * 返回子ExpandableListView 的对象  此时传入的是该大学下所有班级的集合。
     *
     * @param college
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ExpandableListView getGenericExpandableListView(College college) {
        CustomExpandableListView view = new CustomExpandableListView(activity);
        // 加载班级的适配器
        ClassesExpandableListViewAdapter adapter = new ClassesExpandableListViewAdapter(college.classList, activity);
        view.setAdapter(adapter);
        view.setPadding(20, 0, 0, 0);
        return view;
    }
}
