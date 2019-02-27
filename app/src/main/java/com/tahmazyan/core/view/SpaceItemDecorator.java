package com.tahmazyan.core.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecorator extends RecyclerView.ItemDecoration {

    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;
    private final int spacing;
    private final int orientation;

    public SpaceItemDecorator(int spacing, int orientation) {
        this.spacing = spacing;
        this.orientation = orientation;
    }

    public SpaceItemDecorator(int spacing) {
        this(spacing, VERTICAL);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (orientation == VERTICAL) {
            outRect.top = spacing;
            if (position == parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = spacing;
            } else {
                outRect.bottom = 0;
            }
        } else {
            outRect.left = spacing;
            if (position == parent.getAdapter().getItemCount() - 1) {
                outRect.right = spacing;
            } else {
                outRect.right = 0;
            }
        }
    }
}
