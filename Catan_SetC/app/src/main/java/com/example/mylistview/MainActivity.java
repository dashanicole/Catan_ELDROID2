package com.example.mylistview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    ListView listView;

    private final ActivityResultLauncher<Intent> addUserLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String name = result.getData().getStringExtra("name");
                    byte[] byteArray = result.getData().getByteArrayExtra("image");

                    if (name != null && byteArray != null) {
                        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        addUser(name, image);
                    }
                }
            });

    private final ActivityResultLauncher<Intent> editUserLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String updatedName = result.getData().getStringExtra("name");
                    byte[] byteArray = result.getData().getByteArrayExtra("image");
                    int position = result.getData().getIntExtra("position", -1);

                    if (updatedName != null && byteArray != null && position >= 0) {
                        Bitmap updatedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        updateUser(position, updatedName, updatedImage);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBar();

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.user_list);
        registerForContextMenu(listView);
        SearchView searchView = findViewById(R.id.searchView);

        userList = dbHelper.getAllUsers();
        userAdapter = new UserAdapter(this, userList);
        listView.setAdapter(userAdapter);

        findViewById(R.id.addBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            addUserLauncher.launch(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void setStatusBar() {
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        getMenuInflater().inflate(R.menu.user_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        User selectedUser = (User) userAdapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(this, EditUserActivity.class);
                intent.putExtra("id", selectedUser.getId());
                intent.putExtra("name", selectedUser.getName());
                intent.putExtra("position", info.position);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedUser.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                intent.putExtra("image", stream.toByteArray());

                editUserLauncher.launch(intent);
                return true;

            case R.id.menu_delete:
                dbHelper.deleteUser(selectedUser.getId());
                userList.remove(info.position);
                userAdapter.getFilter().filter("");
                userAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Deleted " + selectedUser.getName(), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void updateUser(int position, String name, Bitmap image) {
        User user = userList.get(position);
        dbHelper.updateUser(user.getId(), name, image);
        userList.set(position, new User(user.getId(), name, image));
        userAdapter.getFilter().filter("");
        userAdapter.notifyDataSetChanged();
    }

    public void addUser(String name, Bitmap image) {
        long id = dbHelper.addUser(name, image);
        if (id != -1) {
            userList.clear();
            userList.addAll(dbHelper.getAllUsers());
            userAdapter.getFilter().filter("");
            userAdapter.notifyDataSetChanged();
        }
    }
}
