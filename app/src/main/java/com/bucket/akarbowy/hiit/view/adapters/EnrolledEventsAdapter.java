package com.bucket.akarbowy.hiit.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.utils.EventSectioner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by akarbowy on 11.12.2015.
 */
public class EnrolledEventsAdapter extends RecyclerView.Adapter<EnrolledEventsAdapter.SimpleViewHolder> {

    public interface OnItemClickListener {
        void onEventItemClicked(EventModel eventModel);
    }

    private final Context mContext;
    private List<EventModel> mData;

    private OnItemClickListener mOnItemClickListener;

    public void add(EventModel event, int position) {
        position = position == -1 ? getItemCount() : position;
        mData.add(position, event);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public SectionedRecyclerAdapter.Section[] defineSections() {
        List<SectionedRecyclerAdapter.Section>
                sections = EventSectioner.section(mData);

        SectionedRecyclerAdapter.Section[] container =
                new SectionedRecyclerAdapter.Section[sections.size()];

        return sections.toArray(container);
    }

    public void setEventsList(List<EventModel> eventModelsList) {
        mData = eventModelsList;
        notifyDataSetChanged();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.info_area)
        LinearLayout clickableView;
        @Bind(R.id.info_bar_canceled)
        TextView canceledView;

        public SimpleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public EnrolledEventsAdapter(Context context, List<EventModel> data) {
        mContext = context;
        if (data != null) mData = data;
        else mData = new ArrayList<EventModel>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_enrolled_event_section_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        final EventModel event = mData.get(position);
        holder.title.setText(event.getTitle());
        holder.canceledView.setVisibility(event.isCanceled() ? View.VISIBLE : View.GONE);
        holder.clickableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onEventItemClicked(event);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}