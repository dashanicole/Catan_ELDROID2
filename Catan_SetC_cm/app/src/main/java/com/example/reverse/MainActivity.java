package com.example.reverse;
import com.example.reverse.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    PersonAdapter adapter;
    ArrayList<Person> list = new ArrayList<>();
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editSearch = findViewById(R.id.editSearch);

        // Initialize the adapter with the list
        adapter = new PersonAdapter(this, list);
        listView.setAdapter(adapter);

        // Register the ListView for the context menu
        registerForContextMenu(listView);

        // Add a TextWatcher to the EditText for search functionality
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list based on the search text
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, MainActivity2.class);
        this.startActivityForResult(intent, 0); // Request code 0 for adding new item
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            String name = b.getString("myname");
            String image = b.getString("image");

            if (requestCode == 0) {
                // Adding a new item
                list.add(new Person(list.size(), image, name));
            } else if (requestCode == 1) {
                // Editing an existing item
                int position = b.getInt("position", -1);
                if (position != -1) {
                    Person person = list.get(position);
                    person.setName(name);
                    person.setImage(image);
                }
            }

            adapter.notifyDataSetChanged(); // Refresh the list
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Inflate the context menu from a menu resource
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get the position of the clicked item
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        int itemId = item.getItemId();

        if (itemId == R.id.menu_delete) { // Use if-else instead of switch
            // Delete the item from the list
            list.remove(position);
            adapter.notifyDataSetChanged(); // Refresh the list
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show(); // Show a toast message
            return true;
        } else if (itemId == R.id.menu_edit) {
            // Edit the item: Pass the item's data to MainActivity2
            Person selectedPerson = list.get(position);
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("edit_mode", true); // Indicate that we're in edit mode
            intent.putExtra("position", position); // Pass the position of the item
            intent.putExtra("name", selectedPerson.getName()); // Pass the name
            intent.putExtra("image", selectedPerson.getImage()); // Pass the image URI
            startActivityForResult(intent, 1); // Use a different request code for editing
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}