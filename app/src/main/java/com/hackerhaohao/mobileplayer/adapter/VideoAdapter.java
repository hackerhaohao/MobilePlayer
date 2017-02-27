package com.hackerhaohao.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.po.MediaItem;
import com.hackerhaohao.mobileplayer.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHao on 2017/2/27.
 * 适配器
 */
public class VideoAdapter extends BaseAdapter{

    private List<MediaItem> list = new ArrayList<>();

    private Context context;

    private  LayoutInflater layoutInflater;

    private Utils utils;

    public VideoAdapter(List<MediaItem> mediaList, Context context) {
        this.list = mediaList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        utils = new Utils();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        if (position < list.size()){
            return list.get(position);
        }
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */

    /**
     * 思路：判断convertView是否是null,若是null在通过ViewHolder实例化布局文件，放入tag，若不是null则从tag拿到viewHolder
     *      然后根据position赋值，最后返回convertView
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if(null == convertView){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.video_pager_list_item, null);
            viewHolder.video_pager_list_item_tv_disPlayName = (TextView) convertView.findViewById(R.id.video_pager_list_item_tv_disPlayName);
            viewHolder.video_pager_list_item_tv_duRation = (TextView) convertView.findViewById(R.id.video_pager_list_item_tv_duRation);
            viewHolder.video_pager_list_item_tv_size = (TextView) convertView.findViewById(R.id.video_pager_list_item_tv_size);
            //放入到TAG
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据position来取出list中数据赋值
        MediaItem mediaItem = list.get(position);
        viewHolder.video_pager_list_item_tv_disPlayName.setText(mediaItem.getDisplayName());
        viewHolder.video_pager_list_item_tv_duRation.setText(utils.stringForTime(mediaItem.getDuration()));
        //使用AndroidAPI自带的文件大小格式化方法
        viewHolder.video_pager_list_item_tv_size.setText(Formatter.formatFileSize(this.context,mediaItem.getSize()));
        return convertView;
    }

    /**
     * 内部类优化适配器速度
     */
    static  class  ViewHolder{
        TextView video_pager_list_item_tv_disPlayName;
        TextView video_pager_list_item_tv_duRation;
        TextView video_pager_list_item_tv_size;
    }
}
