package com.example.sql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import com.example.sql.Adapter.MyAdapter;
import Databases.DbHelper;
import Model.ListItem;

public class MainActivity<id> extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<ListItem> arrayList;
    MyAdapter myAdapter;
    DbHelper dbhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        dbhelper = new DbHelper(this);


        arrayList = dbhelper.getAllInformation();

        if (arrayList.size() > 0) {
            myAdapter = new MyAdapter(arrayList, this);
            recyclerView.setAdapter(myAdapter);

        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }



       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
            final int position = viewHolder.getAdapterPosition();
            ListItem listItem=arrayList.get(position);

               dbhelper.deleteRow(listItem.getId());

               arrayList.remove(position);

               myAdapter.notifyItemRemoved(position);
               myAdapter.notifyItemRangeChanged(position, arrayList.size());

           }
       }).attachToRecyclerView(recyclerView);

        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("");


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }
        });
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.th);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "No Result found", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = dbhelper.insertData(result.getFormatName(), result.getContents());

                if (isInserted) {
                    arrayList.clear();
                    arrayList = dbhelper.getAllInformation();
                    myAdapter = new MyAdapter(arrayList, this);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do You Want to Exit?");
            builder.setCancelable(true);

            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            return true;
        }
        else
        if (id == R.id.Settings) {
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent);

            return true;
        }
        else
        if (id == R.id.PrivacyPolicy) {
            Intent intent = new Intent(MainActivity.this,PrivacyPolicy.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
