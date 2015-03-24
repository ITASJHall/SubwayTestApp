package itas274.mysqlite;


import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        datasource = new CommentsDataSource(this);
        datasource.open();

        //List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
       ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        adapter.clear();
        Comment comment = null;
        switch (view.getId()) {
            case R.id.random:
                int min = 1;
                Random r = new Random();
                int[] arrayOne = new int[14];

                arrayOne[0] = r.nextInt(3-min)+min; //size
                arrayOne[1] = r.nextInt(21-min)+min; //double meat
                arrayOne[2] = r.nextInt(21-min)+min; //add bacon
                arrayOne[3] = r.nextInt(20-min)+min; //meat
                arrayOne[4] = r.nextInt(13-min)+min; //number of veg
                arrayOne[5] = r.nextInt(3-min)+min; //toasted
                arrayOne[6] = r.nextInt(3-min)+min; //number of dressings
                arrayOne[7] = r.nextInt(9-min)+min; //bread
                arrayOne[8] = r.nextInt(18-min)+min; //double cheese
                arrayOne[9] = r.nextInt(5-min)+min; //seasons
                arrayOne[10] = r.nextInt(12-min)+min; //veggies
                arrayOne[11] = r.nextInt(10-min)+min; //dressing
                arrayOne[12] = r.nextInt(5-min)+min; //cheese
                arrayOne[13] = r.nextInt(3-min)+min; //number of seasonings


                List<Comment> values= datasource.getAllComments(arrayOne);
                adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1,values);
                setListAdapter(adapter);

                // save the new comment to the database
               /** comment = datasource.getComment(1,arrayOne[7]);
                adapter.add(comment);
                comment = datasource.getComment(2,arrayOne[11]);
                adapter.add(comment);
                comment = datasource.getComment(3,arrayOne[12]);
                adapter.add(comment);
                comment = datasource.getComment(4,arrayOne[9]);
                adapter.add(comment);
                comment = datasource.getComment(5,arrayOne[10]);
                adapter.add(comment);
                comment = datasource.getComment(6,arrayOne[3]);
                adapter.add(comment);**/
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}

