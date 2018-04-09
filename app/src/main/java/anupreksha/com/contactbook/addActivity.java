package anupreksha.com.contactbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


public class addActivity extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    EditText firstName , Id;
    EditText lastName;
    EditText Address;
    EditText Phone;
    EditText email;
    ProgressBar progressBar;
    ListView listView;
    RelativeLayout rlayout;
    Button buttonAddUpdate;
    LinearLayout layout;
    List<Contact> contactList;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent myIntent= getIntent();
        String message = myIntent.getStringExtra(MainActivity.add);
        layout=findViewById(R.id.linearLayout);
        Id = findViewById(R.id.editTextId);
        firstName =  findViewById(R.id.editTextfirstName);
        lastName =  findViewById(R.id.editTextlastName);
        Address = findViewById(R.id.editTextAddress);
        Phone = findViewById(R.id.editTextPhone);
        email =  findViewById(R.id.editTextemail);
        buttonAddUpdate =  findViewById(R.id.buttonAddUpdate);
        rlayout=findViewById(R.id.relativeLayout);
        progressBar = findViewById(R.id.progressbar);
        listView=findViewById(R.id.listViewContact);
        contactList = new ArrayList<>();
        if(message.equals("false"))
        {
            layout.setVisibility(GONE);
        }
        if(message.equals("true")){
            listView.setVisibility(GONE);
        }
        buttonAddUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //if it is updating
                if (isUpdating) {
                    updateContact();
                } else {
                    //if it is not updating
                    //that means it is creating
                    //so calling the method create hero
                    createContact();
                }
            }
        });
        readContacts();
        }
    private void createContact() {
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String add = Address.getText().toString().trim();
        String phone = Phone.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(fname)) {
            firstName.setError("Please enter name");
            firstName.requestFocus();
            return;
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("firstName",fname );
        params.put("lastName", lname);
        params.put("Phone", phone);
        params.put("Address", add);
        params.put("email", mail);


        //Calling the createContact API
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_CONTACT, params, CODE_POST_REQUEST);
        request.execute();
    }
    //inner class to perform network request extending an AsyncTask
  private  class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                   Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshContactList(object.getJSONArray("contactlist"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    class ContactAdapter extends ArrayAdapter<Contact> {
        List<Contact> contactList;


        //constructor to get the list
        public ContactAdapter(List<Contact> contactList) {
            super(addActivity.this, R.layout.layout_list, contactList);
            this.contactList = contactList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_list, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewfName);
            TextView textViewlName = listViewItem.findViewById(R.id.textViewlName);
            TextView textViewAddress = listViewItem.findViewById(R.id.textViewAddress);
            TextView textViewPhone = listViewItem.findViewById(R.id.textViewPhone);
            TextView textViewemail = listViewItem.findViewById(R.id.textViewemail);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Contact contact = contactList.get(position);

            textViewName.setText(contact.getFirstName());
            textViewlName.setText(contact.getLastName());
            textViewAddress.setText(contact.getAddress());
            textViewPhone.setText(contact.getPhone());
            textViewemail.setText(contact.getEmail());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    layout.setVisibility(View.VISIBLE);
                    isUpdating = true;

                    //we will set the selected hero to the UI elements
                    Id.setText(String.valueOf(contact.getId()));
                    firstName.setText(contact.getFirstName());
                    lastName.setText(contact.getLastName());
                    Address.setText(contact.getAddress());
                    Phone.setText(String.valueOf(contact.getPhone()));
                    email.setText(contact.getEmail());
                    buttonAddUpdate.setText(R.string.update);
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(addActivity.this);

                    builder.setTitle("Delete " + contact.getFirstName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   deleteContact(contact.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
    private void readContacts() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_CONTACT, null, CODE_GET_REQUEST);
        request.execute();
    }
    public void refreshContactList(JSONArray contactlist) throws JSONException {
        //clearing previous heroes
        contactList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < contactlist.length(); i++) {
            //getting each contact object
            JSONObject obj = contactlist.getJSONObject(i);

            //adding the contact to the list
            contactList.add(new Contact(
                    obj.getInt("id"),
                    obj.getString("firstName"),
                    obj.getString("lastName"),
                    obj.getString("Address"),
                    obj.getString("Phone"),
                    obj.getString("email")
            ));
        }

        //creating the adapter and setting it to the listview
       ContactAdapter adapter=new ContactAdapter(contactList);
        listView.setAdapter(adapter);
    }
    private void updateContact() {
        String id = Id.getText().toString();
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String phone = Phone.getText().toString();
        String address = Address.getText().toString().trim();
        String mail = email.getText().toString().trim();



        if (TextUtils.isEmpty(fname)) {
            firstName.setError("Please enter name");
            firstName.requestFocus();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("firstName", fname);
        params.put("lastName", lname);
        params.put("Phone", phone);
        params.put("Address", address);
        params.put("email", mail);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_CONTACT, params, CODE_POST_REQUEST);
        request.execute();
        layout.setVisibility(GONE);
       /* buttonAddUpdate.setText("Add");

        firstName.setText("");
        lastName.setText("");
        Address.setText("");
        Phone.setText("");
        email.setText("");
        isUpdating = false;**/
    }
    private void deleteContact(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_CONTACT + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    }
