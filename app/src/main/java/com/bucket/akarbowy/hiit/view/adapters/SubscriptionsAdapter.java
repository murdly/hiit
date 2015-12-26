package com.bucket.akarbowy.hiit.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.model.TechnologyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.SubViewHolder> {
    protected Context mContext;
    protected List<TechnologyModel> mTechnologies;


    public interface OnCancelClickListener {
        void onUnsubscribeClicked(String techId, int position);
    }

    private OnCancelClickListener mOnCancelClickListener;

    public SubscriptionsAdapter(Context context, List<TechnologyModel> data) {
        mContext = context;
        if (data != null) mTechnologies = data;
        else mTechnologies = new ArrayList<>();
    }

    public void add(TechnologyModel technologyModel) {
        mTechnologies.add(technologyModel);
        notifyItemInserted(getItemCount());
    }

    @DebugLog
    public void remove(int position) {
        if (position < getItemCount()) {
            mTechnologies.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    public void setSubsList(List<TechnologyModel> subsTechnologiesList) {
        mTechnologies = subsTechnologiesList;
        notifyDataSetChanged();
    }
    public void setOnCancelClickListener(OnCancelClickListener onCancelSubClickListener) {
        mOnCancelClickListener = onCancelSubClickListener;
    }

    public static class SubViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.action_cancel_sub)
        ImageView unsubscribe;

        public SubViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_subscription_technology_item, parent, false);
        return new SubViewHolder(v);
    }


    @Override
    public void onBindViewHolder(SubViewHolder holder, final int position) {
        final TechnologyModel technology = mTechnologies.get(position);
        holder.name.setText(technology.getTitle());
        holder.unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnCancelClickListener != null) {
                    mOnCancelClickListener.onUnsubscribeClicked(technology.getId(), position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTechnologies == null ? 0 : mTechnologies.size();
    }
}