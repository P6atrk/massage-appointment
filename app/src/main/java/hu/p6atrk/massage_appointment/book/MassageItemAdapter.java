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

public class MassageItemAdapter extends RecyclerView.Adapter<MassageItemAdapter.ViewHolder> {

    private List<MassageItem> mData;
    private Context context;

    // data is passed into the constructor
    MassageItemAdapter(Context context, List<MassageItem> data) {
        this.context = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.massage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MassageItem item = mData.get(position);
        holder.massageItemNameLabel.setText(item.getName());
        holder.massageItemPriceLabel.setText(item.getPrice() + " Ft");
        holder.massageItemTimeLabel.setText(item.getTime() + " perc");
        holder.massageItemBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", item.getName());
                intent.putExtra("price", item.getPrice());
                intent.putExtra("time", item.getTime());
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
        TextView massageItemNameLabel;
        TextView massageItemPriceLabel;
        TextView massageItemTimeLabel;
        ImageButton massageItemBookButton;

        ViewHolder(View itemView) {
            super(itemView);
            massageItemNameLabel = itemView.findViewById(R.id.massageItemNameLabel);
            massageItemPriceLabel = itemView.findViewById(R.id.massageItemPriceLabel);
            massageItemTimeLabel = itemView.findViewById(R.id.massageItemTimeLabel);
            massageItemBookButton = itemView.findViewById(R.id.massageItemBookButton);
        }
    }
}