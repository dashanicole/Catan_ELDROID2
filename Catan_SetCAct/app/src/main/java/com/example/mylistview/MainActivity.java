package com.example.mylistview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        showStatusBar();

        listView = findViewById(R.id.user_list);
        SearchView searchView = findViewById(R.id.searchView);

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

    // YAWA
    public void addUser(String name, Bitmap image) {
        User newUser = new User(name, image);
        userList.add(newUser);
        //userAdapter.notifyDataSetChanged();
//
        userAdapter = new UserAdapter(this, userList);
        listView.setAdapter(userAdapter);

        userAdapter.getFilter().filter("");
    }

    public void showStatusBar() {
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );
    }
}