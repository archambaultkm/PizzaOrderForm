package com.example.pizzaorderform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private final ArrayList<Order> orders;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
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

        holder.tvOrderNumber.setText(String.valueOf(orders.get(position).getID()));
        holder.tvOrderDate.setText(String.valueOf(orders.get(position).getDate())); //TODO use a date formatter instead of string valueof
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

            //the buttons will always have the same text
            //TODO make this language friendly
            btnView.setText("View");
            btnDelete.setText("Delete");
        }
    }
}
