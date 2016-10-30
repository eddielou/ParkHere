package edu.usc.sunset.team7.www.parkhere.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.usc.sunset.team7.www.parkhere.R;
import edu.usc.sunset.team7.www.parkhere.Utils.Consts;
import edu.usc.sunset.team7.www.parkhere.Utils.Tools;
import edu.usc.sunset.team7.www.parkhere.objectmodule.Listing;
import edu.usc.sunset.team7.www.parkhere.objectmodule.ResultsPair;

/**
 * Created by kunal on 10/26/16.
 */
public class ListingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.listing_name)
    TextView listingNameTextView;
    @BindView(R.id.listing_details)
    TextView listingDetailsTextView;
    @BindView(R.id.provider_name)
    TextView providerNameTextView;
    @BindView(R.id.parking_image)
    ImageView parkingImageView;
    @BindView(R.id.book_listing_button)
    Button bookListingButton;
    @BindView(R.id.listing_details_toolbar) Toolbar postListingToolbar;

    private ResultsPair listingResult;
    private String providerFirstName;
    private static final String TAG = "ListingDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);
        ButterKnife.bind(this);
        listingResult = (ResultsPair) getIntent().getSerializableExtra(Consts.LISTING_RESULT_EXTRA);

        setSupportActionBar(postListingToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Temporary string, should replace with title of listing later
            getSupportActionBar().setTitle("Listing details");
        }

        ValueEventListener providerNameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                providerFirstName = (String) dataSnapshot.getValue();
                listingNameTextView.setText(providerFirstName);
                listingDetailsTextView.setText(listingDetailsString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadProviderName:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference providerNameRef = FirebaseDatabase.getInstance().getReference().child(Consts.USERS_DATABASE)
                .child(listingResult.getListing().getProviderID()).child(Consts.USER_FIRSTNAME);
        providerNameRef.addListenerForSingleValueEvent(providerNameListener);
    }

    private String listingDetailsString(){
        Listing listing = listingResult.getListing();
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Name of Listing: " + listing.getName());
        descriptionBuilder.append("\nListing Description: "  + listing.getDescription());
        descriptionBuilder.append("\nStart Time: " + Tools.convertUnixTimeToDateString(listing.getStartTime()));
        descriptionBuilder.append("\nEnd Time: " + Tools.convertUnixTimeToDateString(listing.getStopTime()));
        descriptionBuilder.append("\nDistance Away: " + listingResult.getDistance());
        descriptionBuilder.append("\nListing provider: " + providerFirstName);
        descriptionBuilder.append("\n\nParking Information");
        descriptionBuilder.append("\nPrice: " + listing.getPrice());
        descriptionBuilder.append("\nRefundable? " +listing.isRefundable());
        descriptionBuilder.append("\nHandicap? " + listing.isHandicap());
        descriptionBuilder.append("\nCovered? " + listing.isCovered());
        descriptionBuilder.append("\nCompact? " + listing.isCompact());

        return descriptionBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @OnClick(R.id.book_listing_button)
    protected void bookListing() {
        Intent intent = new Intent(ListingDetailsActivity.this, TransactionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Consts.LISTING_TO_BE_BOOKED, listingResult.getListing());
        bundle.putDouble(Consts.LISTING_DISTANCE, listingResult.getDistance());
        bundle.putString(Consts.LISTING_DETAILS_STRING, listingDetailsString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.provider_name)
    protected void displayProvider(){
        //Go to public user profile activity
        Intent intent = new Intent(ListingDetailsActivity.this, UserProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Consts.PROVIDER_ID, listingResult.getListing().getProviderID());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
