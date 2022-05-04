package hu.p6atrk.massage_appointment.book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.p6atrk.massage_appointment.R;

public class MasseurItemAdapter extends RecyclerView.Adapter<MasseurItemAdapter.ViewHolder> {

    private List<MasseurItem> mData;
    private Context context;

    // data is passed into the constructor
    MasseurItemAdapter(Context context, List<MasseurItem> data) {
        this.context = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.masseur_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MasseurItem item = mData.get(position);
        holder.masseurItemNameLabel.setText(item.getName());
        holder.masseurItemDescLabel.setText(item.getDesc());
        holder.masseurItemBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", item.getName());
                ((Activity)context).setResult(Activity.RESULT_OK, intent);
                ((Activity)context).finish();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_alpha);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView masseurItemNameLabel;
        TextView masseurItemDescLabel;
        ImageButton masseurItemBookButton;

        ViewHolder(View itemView) {
            super(itemView);
            masseurItemNameLabel = itemView.findViewById(R.id.masseurItemNameLabel);
            masseurItemDescLabel = itemView.findViewById(R.id.masseurItemDescLabel);
            masseurItemBookButton = itemView.findViewById(R.id.masseurItemBookButton);
        }
    }
}