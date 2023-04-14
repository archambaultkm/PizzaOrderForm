package com.example.pizzaorderform;

import static java.sql.Types.NULL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SQLiteAdapter extends SQLiteOpenHelper {

    private static SQLiteAdapter adapter;

    private static final String DATABASE_NAME = "pizzaDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String PIZZA_TABLE_NAME = "pizza";
    private static final String PIZZA_ID_FIELD = "pizzaID";
    private static final String SIZE_FIELD = "size";
    private static final String CRUST_FIELD = "crust";
    private static final String CHEESE_FIELD = "cheese";
    private static final String T1_FIELD = "topping1";
    private static final String T2_FIELD = "topping2";
    private static final String T3_FIELD = "topping3";

    private static final String CUSTOMER_TABLE_NAME = "customer";
    private static final String CUSTOMER_ID_FIELD = "customerID";
    private static final String NAME_FIELD = "name";
    private static final String PHONE_FIELD = "phone";
    private static final String ADDRESS_FIELD = "address";
    private static final String CITY_FIELD = "city";
    private static final String POSTCODE_FIELD = "postcode";

    private static final String ORDER_TABLE_NAME = "orders";
    private static final String ORDER_ID_FIELD = "orderID";
    private static final String ORDER_PIZZA_FIELD = "pizzaID";
    private static final String ORDER_CUSTOMER_FIELD = "customerID";
    private static final String ORDER_DATE_FIELD = "date";

    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.CANADA);

    public SQLiteAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteAdapter instanceOfDatabase(Context context) {

        if (adapter == null)
            adapter = new SQLiteAdapter(context);

        return adapter;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder pizzaSQL;
        pizzaSQL = new StringBuilder()
                .append("CREATE TABLE ")
                .append(PIZZA_TABLE_NAME)
                .append(" (")
                .append(PIZZA_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(SIZE_FIELD)
                .append(" INT, ")
                .append(CRUST_FIELD)
                .append(" INT, ")
                .append(CHEESE_FIELD)
                .append(" INT, ")
                .append(T1_FIELD)
                .append(" INT, ")
                .append(T2_FIELD)
                .append(" INT, ")
                .append(T3_FIELD)
                .append(" INT);");

        try {
            db.execSQL(pizzaSQL.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuilder customerSQL;
        customerSQL = new StringBuilder()
                .append("CREATE TABLE ")
                .append(CUSTOMER_TABLE_NAME)
                .append(" (")
                .append(CUSTOMER_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(PHONE_FIELD)
                .append(" TEXT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(ADDRESS_FIELD)
                .append(" TEXT, ")
                .append(CITY_FIELD)
                .append(" TEXT, ")
                .append(POSTCODE_FIELD)
                .append(" TEXT);");

        try {
            db.execSQL(customerSQL.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuilder orderSQL;
        orderSQL = new StringBuilder()
                .append("CREATE TABLE ")
                .append(ORDER_TABLE_NAME)
                .append(" (")
                .append(ORDER_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ORDER_PIZZA_FIELD)
                .append(" INT, ")
                .append(ORDER_CUSTOMER_FIELD)
                .append(" TEXT, ")
                .append(ORDER_DATE_FIELD)
                .append(" TEXT,")

                .append(" FOREIGN KEY (")
                .append(ORDER_PIZZA_FIELD)
                .append(") REFERENCES ")
                .append(PIZZA_TABLE_NAME)
                .append("(")
                .append(PIZZA_ID_FIELD)
                .append("),")

                .append(" FOREIGN KEY (")
                .append(ORDER_CUSTOMER_FIELD)
                .append(") REFERENCES ")
                .append(CUSTOMER_TABLE_NAME)
                .append("(")
                .append(PHONE_FIELD)
                .append(") );");

        try {
            db.execSQL(orderSQL.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+PIZZA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CUSTOMER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ORDER_TABLE_NAME);
        onCreate(db);
    }

    public void addOrderToDatabase(Order order) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cvPizza = new ContentValues();
        ContentValues cvCustomer = new ContentValues();
        ContentValues cvOrder = new ContentValues();

        //add to the pizza table
        cvPizza.put(PIZZA_ID_FIELD, order.getPizza().getID());
        cvPizza.put(SIZE_FIELD, order.getPizza().getSize());
        cvPizza.put(CRUST_FIELD, order.getPizza().getCrust());
        cvPizza.put(CHEESE_FIELD, order.getPizza().getCheese());
        cvPizza.put(T1_FIELD, order.getPizza().getToppingsList().get(0));
        if (order.getPizza().getToppingsList().size() > 1)
            cvPizza.put(T2_FIELD, order.getPizza().getToppingsList().get(1));
        else
            cvPizza.put(T2_FIELD, NULL);
        if (order.getPizza().getToppingsList().size() > 2)
            cvPizza.put(T3_FIELD, order.getPizza().getToppingsList().get(2));
        else
            cvPizza.put(T3_FIELD, NULL);

        db.insert(PIZZA_TABLE_NAME, null, cvPizza);

        //add to the customer table
        cvCustomer.put(CUSTOMER_ID_FIELD, order.getCustomer().getID());
        cvCustomer.put(PHONE_FIELD, order.getCustomer().getPhoneNumber());
        cvCustomer.put(NAME_FIELD, order.getCustomer().getName());
        cvCustomer.put(ADDRESS_FIELD, order.getCustomer().getAddress());
        cvCustomer.put(CITY_FIELD, order.getCustomer().getCity());
        cvCustomer.put(POSTCODE_FIELD, order.getCustomer().getPostalCode());

        db.insert(CUSTOMER_TABLE_NAME, null, cvCustomer);

        //add to the order table
        cvOrder.put(ORDER_ID_FIELD, order.getID());
        cvOrder.put(ORDER_PIZZA_FIELD, order.getPizza().getID());
        cvOrder.put(ORDER_CUSTOMER_FIELD, order.getCustomer().getID());
        cvOrder.put(ORDER_DATE_FIELD, dateToString(order.getDate()));

        db.insert(ORDER_TABLE_NAME, null, cvOrder);

        db.close();
    }

    //identical to add order except calls update instead of insert
    public void updateOrderInDatabase(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cvPizza = new ContentValues();
        ContentValues cvCustomer = new ContentValues();
        ContentValues cvOrder = new ContentValues();

        //add to the pizza table
        cvPizza.put(PIZZA_ID_FIELD, order.getPizza().getID());
        cvPizza.put(SIZE_FIELD, order.getPizza().getSize());
        cvPizza.put(CRUST_FIELD, order.getPizza().getCrust());
        cvPizza.put(CHEESE_FIELD, order.getPizza().getCheese());
        cvPizza.put(T1_FIELD, order.getPizza().getToppingsList().get(0));
        if (order.getPizza().getToppingsList().size() > 1)
            cvPizza.put(T2_FIELD, order.getPizza().getToppingsList().get(1));
        else
            cvPizza.put(T2_FIELD, NULL);
        if (order.getPizza().getToppingsList().size() > 2)
            cvPizza.put(T3_FIELD, order.getPizza().getToppingsList().get(2));
        else
            cvPizza.put(T2_FIELD, NULL);

        db.update(PIZZA_TABLE_NAME, cvPizza, PIZZA_ID_FIELD + " =? ", new String[]{String.valueOf(order.getPizza().getID())});

        //add to the customer table
        cvCustomer.put(CUSTOMER_ID_FIELD, order.getCustomer().getID());
        cvCustomer.put(PHONE_FIELD, order.getCustomer().getPhoneNumber());
        cvCustomer.put(NAME_FIELD, order.getCustomer().getName());
        cvCustomer.put(ADDRESS_FIELD, order.getCustomer().getAddress());
        cvCustomer.put(CITY_FIELD, order.getCustomer().getCity());
        cvCustomer.put(POSTCODE_FIELD, order.getCustomer().getPostalCode());

        db.update(CUSTOMER_TABLE_NAME, cvCustomer, CUSTOMER_ID_FIELD + " =? ", new String[]{String.valueOf(order.getCustomer().getID())});

        //add to the order table
        cvOrder.put(ORDER_ID_FIELD, order.getID());
        cvOrder.put(ORDER_PIZZA_FIELD, order.getPizza().getID());
        cvOrder.put(ORDER_CUSTOMER_FIELD, order.getCustomer().getID());
        //cvOrder.put(ORDER_DATE_FIELD, dateToString(order.getDate())); this apparently shouldn't change if they update it (?)

        db.update(ORDER_TABLE_NAME, cvOrder, ORDER_ID_FIELD + " =? ", new String[]{String.valueOf(order.getID())});
        
        db.close();
    }

    public void deleteOrderFromDatabase(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PIZZA_TABLE_NAME, PIZZA_ID_FIELD + " =? ", new String[]{String.valueOf(order.getPizza().getID())});
        db.delete(CUSTOMER_TABLE_NAME, CUSTOMER_ID_FIELD + " =? ", new String[]{String.valueOf(order.getCustomer().getID())});
        db.delete(ORDER_TABLE_NAME, ORDER_ID_FIELD + " =? ", new String[]{String.valueOf(order.getID())});
    }

    //add values from the database into an order arraylist that can be viewed in the app on startup
    public void populateOrderList() {

        SQLiteDatabase db = this.getReadableDatabase();

        //set up the order
        try (Cursor orderResult = db.rawQuery("SELECT * FROM "+ORDER_TABLE_NAME, null)) {

            if(orderResult.getCount() != 0) {

                while(orderResult.moveToNext()) {

                    //these values should reflect the order of columns in the Order table
                    int orderID = orderResult.getInt(0);
                    int pizzaID = orderResult.getInt(1);
                    int customerID = orderResult.getInt(2);
                    Date date = stringToDate(orderResult.getString(3));

                    Pizza pizza = null;
                    Customer customer = null;

                    //set up the pizza connected to that order
                    try (Cursor pizzaResult = db.rawQuery("SELECT * FROM "+PIZZA_TABLE_NAME+" WHERE "+PIZZA_ID_FIELD+"="+pizzaID, null)) {

                        while(pizzaResult.moveToNext()) {

                            int id = pizzaResult.getInt(0);
                            int size = pizzaResult.getInt(1);
                            int crust = pizzaResult.getInt(2);
                            int cheese = pizzaResult.getInt(3);
                            int t1 = pizzaResult.getInt(4);
                            int t2 = pizzaResult.getInt(5);
                            int t3 = pizzaResult.getInt(6);

                            ArrayList<Integer> toppingsList = new ArrayList<>();
                            toppingsList.add(t1);
                            if (t2 != NULL)
                                toppingsList.add(t2);
                            if (t3 != NULL)
                                toppingsList.add(t3);

                            pizza = new Pizza(id, size, crust, cheese, toppingsList);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.d("LOG", "populateOrderList: error querying pizza table");
                    }

                    //set up the customer connected to the order
                    try (Cursor customerResult = db.rawQuery("SELECT * FROM "+CUSTOMER_TABLE_NAME+" WHERE "+CUSTOMER_ID_FIELD+"="+customerID, null)) {

                        while(customerResult.moveToNext()) {

                            int id = customerResult.getInt(0);
                            String phone = customerResult.getString(1);
                            String name = customerResult.getString(2);
                            String address = customerResult.getString(3);
                            String city = customerResult.getString(4);
                            String postCode = customerResult.getString(5);

                            customer = new Customer(id, phone, name, address, city, postCode);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.d("LOG", "populateOrderList: error querying customer table");
                    }

                    Order order = new Order(orderID, pizza, customer, date);
                    //add that order to the list used for recyclerview
                    OrderRecordActivity.orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("LOG", "populateOrderList: error querying order table");
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
    }

    private Date stringToDate(String date) {

        try {
            return dateFormat.parse(date);
        } catch (ParseException | NullPointerException e){
            return null;
        }
    }

    private String dateToString(Date date) {

        return dateFormat.format(date);
    }
}