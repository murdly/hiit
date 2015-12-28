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
import com.bucket.akarbowy.hiit.view.enums.TechDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by akarbowy on 11.12.2015.
 */
public class EventsSectionedAdapter extends RecyclerView.Adapter<EventsSectionedAdapter.SimpleViewHolder> {

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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
        @Bind(R.id.location)
        TextView location;
        @Bind(R.id.info)
        TextView info;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.info_area)
        LinearLayout clickableView;
        @Bind(R.id.info_bar_canceled)
        TextView canceledView;

        public SimpleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public EventsSectionedAdapter(Context context, List<EventModel> data) {
        mContext = context;
        if (data != null) mData = data;
        else mData = new ArrayList<>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_event_section_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        final EventModel event = mData.get(position);
        holder.icon.setImageDrawable(TechDrawable.getThumbnail(mContext, event.getTechnologyId()));
        holder.title.setText(event.getTitle());
        holder.location.setText(event.getLocalization());
        holder.time.setText(event.getTimeAsString());
        holder.info.setText(String.format("%s %s", event.getParticipantsCount(), mContext.getString(R.string.postfix_userCounter)));

        holder.canceledView.setVisibility(event.isCanceled() ? View.VISIBLE : View.GONE);
        holder.clickableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
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