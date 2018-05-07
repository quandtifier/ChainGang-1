package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.support.constraint.Constraints.TAG;

public class CreateAccountActivity extends AppCompatActivity {

    /**
     * Constant with a link to php code which will handle adding a new user to database
     */
    private final static String USER_ADD_URL
            = "https://chaingang.000webhostapp.com/addUser.php?";

    /**
     * Variable to keep a user first name
     */
    private EditText mFirstName;

    /**
     * Variable to keep a user last name
     */
    private EditText mLastName;

    /**
     * Variable to keep a user email address
     */
    private EditText mEmail;

    /**
     * Variable to keep a user login
     */
    private EditText mLogin;

    /**
     * Variable to keep a user password
     */
    private EditText mPassword;

    /**
     * Variable to keep a user confirmation of the given password
     */
    private EditText mConfirmPassword;

    private UserAddListener mListener;

    /**
     * Required empty public constructor
     */
    public CreateAccountActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_create_account, container, false);

        mFirstName = (EditText) v.findViewById(R.id.firstname);
        mLastName = (EditText) v.findViewById(R.id.lastname);
        mEmail = (EditText) v.findViewById(R.id.email);
        mLogin = (EditText) v.findViewById(R.id.login);
        mPassword = (EditText) v.findViewById(R.id.password);
        mConfirmPassword = (EditText) v.findViewById(R.id.confirmpassword);


        /*Button button= (Button)findViewById(R.id.buttonId);
button.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view) {
    // click handling code
   }
});*/

        Button addUserButton = (Button) v.findViewById(R.id.createAccountButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildUserURL(v);
                mListener.addUser(url);
            }
        });


        return v;
    }


    /**
     *
     */
    public interface UserAddListener {
        public void addUser(String url);
    }

    /**
     *
     * @param v
     * @return
     */
    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(USER_ADD_URL);

        try {

            String firstName = mFirstName.getText().toString();
            sb.append("email=");
            sb.append(URLEncoder.encode(firstName, "UTF-8"));


            String lastName = mLastName.getText().toString();
            sb.append("&firstname=");
            sb.append(URLEncoder.encode(lastName, "UTF-8"));


            String email = mEmail.getText().toString();
            sb.append("&lastname=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String login = mLogin.getText().toString();
            sb.append("&login=");
            sb.append(URLEncoder.encode(login, "UTF-8"));

            String password = mPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            Log.i(TAG, sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     *
     */
    private class AddUserTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add a user, reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "User successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    //@Override
    public void addUser(String url) {

        AddUserTask task = new AddUserTask();
        task.execute(new String[]{url.toString()});

// Takes you back to the previous fragment by popping the current fragment out.
//        getSupportFragmentManager().popBackStackImmediate();

        //gotoChooseChain();
    }



    /**
     * Method to call choose chain activity
     * @param view
     */
    public void gotoChooseChain(View view) {
        Intent intent = new Intent(this, ChooseChainActivity.class);
        startActivity(intent);
    }


}
