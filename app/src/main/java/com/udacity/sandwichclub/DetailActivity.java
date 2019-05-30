package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView placeOfOriginLabelTextView;
    private TextView placeOfOriginTextView;
    private TextView alsoKnownAsLabelTextView;
    private TextView alsoKnownAsTextView;
    private TextView ingredientsLabelTextView;
    private TextView ingredientsTextView;
    private TextView descriptionLabelTextView;
    private TextView descriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Call findViewByID to populate the views
        ingredientsIv = findViewById(R.id.image_iv);
        placeOfOriginLabelTextView = findViewById(R.id.place_of_origin_label_tv);
        placeOfOriginTextView = findViewById(R.id.place_of_origin_tv);
        alsoKnownAsLabelTextView = findViewById(R.id.also_known_as_label_tv);
        alsoKnownAsTextView = findViewById(R.id.also_known_as_tv);
        ingredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        descriptionLabelTextView = findViewById(R.id.description_label_tv);
        descriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Set the image View with Picasso
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        //Set the also_known_as textView
        if(sandwich.getAlsoKnownAs()!= null && !(sandwich.getAlsoKnownAs().isEmpty())) {

            String alsoKnownAsString = TextUtils.join(", ", sandwich.getAlsoKnownAs());
            alsoKnownAsTextView.setText(alsoKnownAsString);
        } else {
            alsoKnownAsLabelTextView.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);
        }

        //Set the place_of_origin text view
        if(sandwich.getPlaceOfOrigin() != null && !(sandwich.getPlaceOfOrigin().isEmpty())) {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginLabelTextView.setVisibility(View.GONE);
            placeOfOriginTextView.setVisibility(View.GONE);
        }

        //Set the description textView
        if(sandwich.getDescription()!=null && !(sandwich.getDescription().isEmpty())) {
            descriptionTextView.setText(sandwich.getDescription());
        } else {
            descriptionTextView.setVisibility(View.GONE);
            descriptionLabelTextView.setVisibility(View.GONE);
        }

        //Set the Ingredients text view
        if(sandwich.getIngredients() != null && !(sandwich.getIngredients().isEmpty())) {
            String ingredientsString = TextUtils.join(", ", sandwich.getIngredients());
            ingredientsTextView.setText(ingredientsString);
        } else {
            ingredientsTextView.setVisibility(View.GONE);
            ingredientsLabelTextView.setVisibility(View.GONE);
        }
    }
}
