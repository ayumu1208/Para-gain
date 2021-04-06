package jp.co.handinhand.paragain;



import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;

public class MainActivity<mTextViewShowUploads> extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MovieFragment()).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AddFragment()).commit();

        showFragment(R.id.navigation_movie);




    }

    public void showFragment(int itemId) {
        Fragment selectedFragment = null;

        switch (itemId) {
            case R.id.navigation_movie:
                selectedFragment = new MovieFragment();
                break;
            case R.id.navigation_account:
                selectedFragment = new AccountFragment();
                break;
            case R.id.navigation_add:
                selectedFragment = new AddFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + itemId);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }





    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    showFragment(menuItem.getItemId());
                    return true;
                }
            };
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}