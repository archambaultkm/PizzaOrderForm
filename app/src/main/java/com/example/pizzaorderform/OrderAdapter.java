package com.example.pizzaorderform;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    protected final ArrayList<Order> orders;

    String[] enStrings, nlStrings;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd, h:mm aa", Locale.CANADA);

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
        Collections.reverse(this.orders); //will make most recent orders show up first

        enStrings = context.getResources().getStringArray(R.array.en_orderitem);
        nlStrings = context.getResources().getStringArray(R.array.nl_orderitem);
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //method gives a look to layout rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_order, parent, false);

        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {

        //assign values to the views created in item_order layout file
        //based on their position in the recyclerview
        if (MainActivity.getLanguage()) {
            holder.tvOrderNumber.setText(nlStrings[0]);
            holder.btnView.setText(nlStrings[1]);
            holder.btnDelete.setText(nlStrings[2]);
        } else {
            holder.tvOrderNumber.setText(enStrings[0]);
            holder.btnView.setText(enStrings[1]);
            holder.btnDelete.setText(enStrings[2]);
        }

        holder.tvOrderNumber.setText(holder.tvOrderNumber.getText() +
                String.valueOf(orders.get(position).getID()));
        holder.tvOrderDate.setText(dateFormat.format(orders.get(position).getDate()));


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO add popup to confirm they want to delete
                OrderRecordActivity.orders.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),OrderDetailsActivity.class);
                i.putExtra("order", OrderRecordActivity.orders.get(holder.getAdapterPosition()));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        //orders will be passed into the OrderAdapter constructor from the orderRecordActivity
        if (orders != null)
            return orders.size();

        return 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderNumber, tvOrderDate;
        Button btnView, btnDelete;

        public OrderViewHolder(@NonNull View itemView) {

            super(itemView);

            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);
            tvOrderDate = itemView.findViewById(R.id.tvDate);
            btnView = itemView.findViewById(R.id.btnView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
