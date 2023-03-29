package com.example.pizzaorderform;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SQLiteAdapter extends SQLiteOpenHelper {

    private static SQLiteAdapter adapter;

    private static final String DATABASE_NAME = "order.db";
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

    private static final String ORDER_TABLE_NAME = "order";
    private static final String ORDER_ID_FIELD = "orderID";
    private static final String ORDER_PIZZA_FIELD = "pizzaID";
    private static final String ORDER_CUSTOMER_FIELD = "customerPhone";
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
                .append("(")
                .append(PIZZA_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ")
                .append(SIZE_FIELD)
                .append(" INT,")
                .append(CRUST_FIELD)
                .append(" INT,")
                .append(CHEESE_FIELD)
                .append(" INT,")
                .append(T1_FIELD)
                .append(" INT,")
                .append(T2_FIELD)
                .append(" INT,")
                .append(T3_FIELD)
                .append(" INT)");

        db.execSQL(pizzaSQL.toString());

        StringBuilder customerSQL;
        customerSQL = new StringBuilder()
                .append("CREATE TABLE ")
                .append(CUSTOMER_TABLE_NAME)
                .append("(")
                .append(PHONE_FIELD)
                .append(" TEXT PRIMARY KEY, ")
                .append(NAME_FIELD)
                .append(" TEXT,")
                .append(ADDRESS_FIELD)
                .append(" TEXT,")
                .append(CITY_FIELD)
                .append(" TEXT,")
                .append(POSTCODE_FIELD)
                .append(" TEXT)");

        db.execSQL(customerSQL.toString());

        StringBuilder orderSQL;
        orderSQL = new StringBuilder()
                .append("CREATE TABLE ")
                .append(ORDER_TABLE_NAME)
                .append("(")
                .append(ORDER_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ")
                .append(ORDER_PIZZA_FIELD)
                .append(" INT,")
                .append(ORDER_CUSTOMER_FIELD)
                .append(" TEXT,")
                .append(ORDER_DATE_FIELD)
                .append(" DATETIME,")

                .append(" FOREIGN KEY (")
                .append(ORDER_PIZZA_FIELD)
                .append(") REFERENCES ")
                .append(PIZZA_TABLE_NAME)
                .append(" (")
                .append(PIZZA_ID_FIELD)
                .append("), ")

                .append(" FOREIGN KEY (")
                .append(ORDER_CUSTOMER_FIELD)
                .append(") REFERENCES ")
                .append(CUSTOMER_TABLE_NAME)
                .append(" (")
                .append(PHONE_FIELD)
                .append("))");

        db.execSQL(orderSQL.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+PIZZA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CUSTOMER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS '"+ORDER_TABLE_NAME+"'"); //idk why it's making me escape this one
    }

    public void addOrderToDatabase(Order order) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cvPizza = new ContentValues();
        ContentValues cvCustomer = new ContentValues();
        ContentValues cvOrder = new ContentValues();

        //add to the pizza table
        //TODO figure out how autoincrementing values work here
        cvPizza.put(SIZE_FIELD, order.getPizza().getSize());
        cvPizza.put(CRUST_FIELD, order.getPizza().getCrust());
        cvPizza.put(CHEESE_FIELD, order.getPizza().getCheese());
        cvPizza.put(T1_FIELD, order.getPizza().getToppingsList().get(0));
        cvPizza.put(T2_FIELD, order.getPizza().getToppingsList().get(1)); //TODO figure out how to handle potential null
        cvPizza.put(T3_FIELD, order.getPizza().getToppingsList().get(2));

        db.insert(PIZZA_TABLE_NAME, null, cvPizza);

        //add to the customer table
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
    }

    //add values from the database into an order arraylist that can be viewed in the app on startup
    //TODO this might work?
    public void populateOrderList() {

        SQLiteDatabase db = this.getReadableDatabase();

        //set up the order
        try (Cursor orderResult = db.rawQuery("SELECT * FROM '"+ORDER_TABLE_NAME+"'", null)) {

            if(orderResult.getCount() != 0) {

                while(orderResult.moveToNext()) {

                    //these values should reflect the order of columns in the Order table
                    int orderID = orderResult.getInt(1);
                    int pizzaID = orderResult.getInt(2);
                    String customerID = orderResult.getString(3);
                    Date date = stringToDate(orderResult.getString(4));

                    Pizza pizza = null;
                    Customer customer = null;

                    //set up the pizza connected to that order
                    try (Cursor pizzaResult = db.rawQuery("SELECT * FROM "+PIZZA_TABLE_NAME+" WHERE "+PIZZA_ID_FIELD+"="+pizzaID, null)) {

                        if (pizzaResult.getCount() != 0) {

                            //no while for pizza or customer bc their id's should be unique and there should only be one
                            int id = pizzaResult.getInt(1);
                            int size = pizzaResult.getInt(2);
                            int crust = pizzaResult.getInt(3);
                            int cheese = pizzaResult.getInt(4);
                            int t1 = pizzaResult.getInt(5);
                            int t2 = pizzaResult.getInt(6);
                            int t3 = pizzaResult.getInt(7);

                            ArrayList<Integer> toppingsList = new ArrayList<>();
                            toppingsList.add(t1);
                            toppingsList.add(t2);
                            toppingsList.add(t3);

                            pizza = new Pizza(id, size, crust, cheese, toppingsList);
                        }
                    }

                    //set up the customer connected to the order
                    try (Cursor customerResult = db.rawQuery("SELECT * FROM "+CUSTOMER_TABLE_NAME+" WHERE "+CUSTOMER_ID_FIELD+"="+customerID, null)) {

                        if (customerResult.getCount() != 0) {

                            int id = customerResult.getInt(1);
                            String phone = customerResult.getString(2);
                            String name = customerResult.getString(3);
                            String address = customerResult.getString(4);
                            String city = customerResult.getString(5);
                            String postCode = customerResult.getString(6);

                            customer = new Customer(id, phone, name, address, city, postCode);
                        }
                    }

                    Order order = new Order(orderID, pizza, customer, date);
                    //add that order to the list used for recyclerview
                    Order.orderList.add(order); //TODO probably move this list out to the activity, not the order class? idk
                }
            }
        }
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
