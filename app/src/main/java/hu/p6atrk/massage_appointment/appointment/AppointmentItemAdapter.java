package hu.p6atrk.massage_appointment.appointment;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;

import hu.p6atrk.massage_appointment.R;

public class AppointmentItemAdapter extends RecyclerView.Adapter<AppointmentItemAdapter.ViewHolder> {

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
        // TODO these
        holder.appointmentMassagePriceLabel.setText(String.valueOf(item._getPrice()));
        holder.appointmentMassageTimeLabel.setText(String.valueOf(item._getTime()));
        holder.appointmentDateLabel.setText(DateFormat.format("yyyy-MM-dd", item.getDate()));
        holder.appointmentTimeLabel.setText(DateFormat.format("HH:mm", item.getDate()));
        if(item.getDate().before(Calendar.getInstance().getTime())) { // TODO Ez nem müködik
            holder.appointmentDeleteButton.setVisibility(View.INVISIBLE);
        } else {
            holder.appointmentDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO delete appointment EZ N
                    //firestore.collection("")
                }
            });
        }
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

        ViewHolder(View itemView) {
            super(itemView);
            appointmentMassageNameLabel = itemView.findViewById(R.id.appointmentMassageNameLabel);
            appointmentMassagePriceLabel = itemView.findViewById(R.id.appointmentMassagePriceLabel);
            appointmentMassageTimeLabel = itemView.findViewById(R.id.appointmentMassageTimeLabel);
            appointmentMasseurNameLabel = itemView.findViewById(R.id.appointmentMasseurNameLabel);
            appointmentDateLabel = itemView.findViewById(R.id.appointmentDateLabel);
            appointmentTimeLabel = itemView.findViewById(R.id.appointmentTimeLabel);
            appointmentDeleteButton = itemView.findViewById(R.id.appointmentDeleteButton);
        }
    }
}