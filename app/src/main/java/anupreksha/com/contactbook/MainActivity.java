package anupreksha.com.contactbook;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    public static final String add="com.Anupreksha.contactbook";
    Button Add,retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Add = findViewById(R.id.button);
        retrieve=findViewById(R.id.button2);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent;
                myIntent = new Intent(MainActivity.this,
                        addActivity.class);
                myIntent.putExtra(add, "true");
                startActivity(myIntent);

            }
        });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent;
                myIntent = new Intent(MainActivity.this,
                        addActivity.class);
                myIntent.putExtra(add, "false");
                startActivity(myIntent);
            }
        });

    }


}