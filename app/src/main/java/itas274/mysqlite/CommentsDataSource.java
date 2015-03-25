package itas274.mysqlite;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.ID_COLUMN,
            MySQLiteHelper.COLUMN_NAME };

    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, comment);
        long insertBreadId = database.insert(MySQLiteHelper.TABLE_BREAD, null,
                values);
        long insertDressingId = database.insert(MySQLiteHelper.TABLE_DRESSING, null,
                values);
        long insertCheeseId = database.insert(MySQLiteHelper.TABLE_CHEESE, null,
                values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BREAD,
                allColumns, MySQLiteHelper.ID_COLUMN + " = " + insertBreadId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_BREAD, MySQLiteHelper.ID_COLUMN
                + " = " + id, null);
    }

    public Comment getComment(int itemId, int id){
        String table;
        //switch to pull records from the correct table
        switch (itemId) {
            case 1: table = MySQLiteHelper.TABLE_BREAD;
                break;
            case 2: table = MySQLiteHelper.TABLE_DRESSING;
                break;
            case 3: table = MySQLiteHelper.TABLE_CHEESE;
                break;
            case 4: table = MySQLiteHelper.TABLE_SEASONING;
                break;
            case 5: table = MySQLiteHelper.TABLE_VEGGIE;
                break;
            case 6: table = MySQLiteHelper.TABLE_MEAT;
                break;
            default:table = MySQLiteHelper.TABLE_SUBS;
        }
        Cursor cursor = database.query(table,
                allColumns, MySQLiteHelper.ID_COLUMN + " = " + id , null, null, null, null);
        cursor.moveToFirst();
        Comment entry = cursorToComment(cursor);
        cursor.close();
        return entry;

    }

    public List<Comment> getAllComments(int[]ran) {

        List<Comment> comments = new ArrayList<Comment>();
        int min = 1;
        Random r = new Random();
        int[] arrayTwo = new int[14];

        //Size of Sub
        Comment size = new Comment();
        if(ran[0] == 1){
            size.setComment("6 inch");
        } else {
            size.setComment("Footlong");
        } comments.add(size);

        //Adding bacon
        if(ran[2] == 5){
            Comment bacon = new Comment();
            bacon.setComment("Add Bacon");
            comments.add(bacon);
        }

        //Adding meat and double meat
        if(ran[1] == 20){
            Comment doubleMeat = new Comment();
            doubleMeat.setComment("Double Meat");
            comments.add(doubleMeat);
            arrayTwo[3] = r.nextInt(20-min)+min;
            comments.add(getComment(6,arrayTwo[3]));
            comments.add(getComment(6,ran[3]));
        }else{
            comments.add(getComment(6,ran[3]));
        }

        //Bread type
        comments.add(getComment(1,ran[7]));

        //adding cheese and double cheese
        if(ran[8] == 1){
            Comment doubleCheese = new Comment();
            doubleCheese.setComment("Double Cheese");
            comments.add(doubleCheese);
            arrayTwo[12] = r.nextInt(5-min)+min;
            comments.add(getComment(3,arrayTwo[12]));
            comments.add(getComment(3,ran[12]));
        }else{
            comments.add(getComment(3,ran[12]));
        }

        //Is it Toasted
        if(ran[5] == 1){
            Comment toasted = new Comment();
            toasted.setComment("Toasted");
            comments.add(toasted);
        }

        //Number of Veggies and adding that Number
        //TODO: Shorten code using loops
        if(ran[4] == 1){
            Comment veg = new Comment();
            veg.setComment("No Veg");
            comments.add(veg);
        }else if(ran[4] >1 && ran[4] != 11){
            ArrayList<Integer> arrayThree = new ArrayList<Integer>();
            switch (ran[4]){
                case 2:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    while(arrayTwo[10] == ran[10]){arrayTwo[10] = r.nextInt(12-min)+min;}
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 3:
                    //first veg
                    arrayThree.add(ran[10]);
                    Log.d("test1","  "+ arrayThree.size());
                    //second veg
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    while(arrayThree.contains(arrayTwo[10])){arrayTwo[10] = r.nextInt(12-min)+min;}
                    arrayThree.add(arrayTwo[10]);
                    Log.d("test1","  "+ arrayThree.size());
                    //third veg
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    while(arrayThree.contains(arrayTwo[10])){arrayTwo[10] = r.nextInt(12-min)+min;}
                    arrayThree.add(arrayTwo[10]);
                    Log.d("test1","  "+ arrayThree.size());

                    //adding all veg
                    for(int i=0; i<arrayThree.size(); i++) {
                        Log.d("test",""+i+"  "+ arrayThree.get(i));
                        comments.add(getComment(5, arrayThree.get(i)));}
                    break;
                case 4:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 5:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 6:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 7:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 8:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 9:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                case 10:
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    arrayTwo[10] = r.nextInt(12-min)+min;
                    comments.add(getComment(5,arrayTwo[10]));
                    comments.add(getComment(5,ran[10]));
                    break;
                default:
                    comments.add(getComment(5,ran[10]));
            }
        }else if(ran[4] == 11){
            for(int i=1; i<12; i++){
                comments.add(getComment(5,i));
            }

        }
        //adding dressing(s)
        if(ran[6] == 1){
            comments.add(getComment(2,ran[11]));
        }else {
            arrayTwo[11] = r.nextInt(10-min)+min;
            while (arrayTwo[11] == ran[11]){
                arrayTwo[1] = r.nextInt(10-min)+min;
            }
            comments.add(getComment(2,arrayTwo[11]));
            comments.add(getComment(2,ran[11]));
        }

        //adding seasonings
        if(ran[13] == 1){
            comments.add(getComment(4,ran[9]));
        }else{
            if (ran[9] == 3){
                comments.add(getComment(4,ran[9]));
                comments.add(getComment(4,4));
            }else {
                arrayTwo[9] = r.nextInt(5 - min) + min;
                while (arrayTwo[9] == ran[9]) {
                    arrayTwo[9] = r.nextInt(5 - min) + min;
                }
                comments.add(getComment(4, arrayTwo[9]));
                comments.add(getComment(4, ran[9]));
            }
        }

        return comments;

      /**  Cursor cursorBread = database.query(MySQLiteHelper.TABLE_BREAD,
                allColumns, null, null, null, null, null);
        Cursor cursorDressing = database.query(MySQLiteHelper.TABLE_DRESSING,
               allColumns, null, null, null, null, null);
         Cursor cursorCheese = database.query(MySQLiteHelper.TABLE_CHEESE,
                 allColumns, null, null, null, null, null);
         Cursor cursorSeason = database.query(MySQLiteHelper.TABLE_SEASONING,
                allColumns, null, null, null, null, null);
         Cursor cursorVeggie = database.query(MySQLiteHelper.TABLE_VEGGIE,
                 allColumns, null, null, null, null, null);
         Cursor cursorMeat = database.query(MySQLiteHelper.TABLE_MEAT,
                 allColumns, null, null, null, null, null);

        cursorBread.moveToFirst();
        cursorDressing.moveToFirst();
        cursorCheese.moveToFirst();
       cursorSeason.moveToFirst();
        cursorVeggie.moveToFirst();
        cursorMeat.moveToFirst();

        while (!cursorBread.isAfterLast()) {
            Comment comment = cursorToComment(cursorBread);
            comments.add(comment);
            cursorBread.moveToNext();
        }
        while (!cursorDressing.isAfterLast()) {
            Comment comment = cursorToComment(cursorDressing);
            comments.add(comment);
            cursorDressing.moveToNext();
        }
        while (!cursorCheese.isAfterLast()) {
            Comment comment = cursorToComment(cursorCheese);
            comments.add(comment);
            cursorCheese.moveToNext();
        }
        while (!cursorSeason.isAfterLast()) {
            Comment comment = cursorToComment(cursorSeason);
            comments.add(comment);
            cursorSeason.moveToNext();
        }
        while (!cursorVeggie.isAfterLast()) {
            Comment comment = cursorToComment(cursorVeggie);
            comments.add(comment);
            cursorVeggie.moveToNext();
        }
        while (!cursorMeat.isAfterLast()) {
            Comment comment = cursorToComment(cursorMeat);
            comments.add(comment);
            cursorMeat.moveToNext();
        }
        // make sure to close the cursors
        cursorBread.close();
        cursorDressing.close();
        cursorCheese.close();
        cursorSeason.close();
        cursorVeggie.close();
        cursorMeat.close();**/
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}

