package hu.p6atrk.massage_appointment.appointment;

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

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import hu.p6atrk.massage_appointment.R;
import hu.p6atrk.massage_appointment.book.Book;

public class AppointmentItemAdapter extends RecyclerView.Adapter<AppointmentItemAdapter.ViewHolder> {

    private static final int REQUEST_APPOINTMENT = 0;
    private List<AppointmentItem> data;
    private Context context;
    private FirebaseFirestore firestore;

    // data is passed into the constructor
    AppointmentItemAdapter(Context context, List<AppointmentItem> data) {
        this.context = context;
        this.data = data;
        //firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppointmentItem item = data.get(position);
        holder.appointmentMassageNameLabel.setText(item.getMassageName());
        holder.appointmentMasseurNameLabel.setText(item.getMasseurName());
        holder.appointmentMassagePriceLabel.setText(String.valueOf(item._getPrice()) + " Ft");
        holder.appointmentMassageTimeLabel.setText(String.valueOf(item._getTime()) + " perc");
        holder.appointmentDateLabel.setText(item.getDate().split(" ")[0]);
        holder.appointmentTimeLabel.setText(item.getDate().split(" ")[1]);
        String dateString = item.getDate();
        try {
            if(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(item.getDate()).before(Calendar.getInstance().getTime())) {
                holder.appointmentDeleteButton.setVisibility(View.INVISIBLE);
                holder.appointmentSettingsButton.setVisibility(View.INVISIBLE);
            } else {
                holder.appointmentDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore.getInstance().collection("appointment").document(item._getId()).delete();
                        removeAt(holder.getAdapterPosition());
                    }
                });
                holder.appointmentSettingsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Book.class);
                        intent.putExtra("appointment", item);
                        intent.putExtra("date", dateString);
                        ((Activity) context).startActivityForResult(intent, REQUEST_APPOINTMENT);
                    }
                });
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_slide);
        holder.itemView.startAnimation(animation);
    }

    private void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appointmentMassageNameLabel;
        TextView appointmentMassagePriceLabel;
        TextView appointmentMassageTimeLabel;
        TextView appointmentMasseurNameLabel;
        TextView appointmentDateLabel;
        TextView appointmentTimeLabel;
        ImageButton appointmentDeleteButton;
        ImageButton appointmentSettingsButton;

        ViewHolder(View itemView) {
            super(itemView);
            appointmentMassageNameLabel = itemView.findViewById(R.id.appointmentMassageNameLabel);
            appointmentMassagePriceLabel = itemView.findViewById(R.id.appointmentMassagePriceLabel);
            appointmentMassageTimeLabel = itemView.findViewById(R.id.appointmentMassageTimeLabel);
            appointmentMasseurNameLabel = itemView.findViewById(R.id.appointmentMasseurNameLabel);
            appointmentDateLabel = itemView.findViewById(R.id.appointmentDateLabel);
            appointmentTimeLabel = itemView.findViewById(R.id.appointmentTimeLabel);
            appointmentDeleteButton = itemView.findViewById(R.id.appointmentDeleteButton);
            appointmentSettingsButton = itemView.findViewById(R.id.appointmentSettingsButton);
        }
    }
}