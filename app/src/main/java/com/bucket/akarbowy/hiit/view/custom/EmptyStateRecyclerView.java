package com.bucket.akarbowy.hiit.view.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class EmptyStateRecyclerView extends RecyclerView {
    private View emptyView;

    public EmptyStateRecyclerView(Context context) {
        super(context);
    }

    public EmptyStateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyStateRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter =  getAdapter();
            if(adapter != null && emptyView != null) {
                if(adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    EmptyStateRecyclerView.this.setVisibility(View.GONE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    EmptyStateRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        emptyObserver.onChanged();
        this.emptyView = emptyView;
    }
}