package com.example.mealmonkey.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmonkey.Data.Model.Banner;
import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Data.Model.FoodModel;
import com.example.mealmonkey.Data.Model.FoodSize;
import com.example.mealmonkey.Network.ViewModel.NetworkViewModel;
import com.example.mealmonkey.Network.ViewModel.RoomViewModel;
import com.example.mealmonkey.R;
import com.example.mealmonkey.RatingModel;
import com.example.mealmonkey.Recelerview.Adapter.BannerAdapter;
import com.example.mealmonkey.Recelerview.Adapter.FoodSizeAdapter;
import com.example.mealmonkey.Recelerview.Adapter.UserReviewAdapter;
import com.example.mealmonkey.databinding.FooddetailsBinding;
import com.example.mealmonkey.databinding.FoodsizeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodDetails extends AppCompatActivity {

    private FooddetailsBinding binding;
    private int FoodCount=1;
    private int FoodPrice;
    private int TotalFoodPrice;
    private List<FoodSize> foodSizeList = new ArrayList<>();
    private NetworkViewModel networkViewModel;
    private List<Banner> bannerList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    private Banner bannerone, bannertwo, bannerthree, bannerfour;
    private RoomViewModel roomViewModel;
    private UserReviewAdapter reviewAdapter;

    private long FoodID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.fooddetails);
        networkViewModel = new ViewModelProvider(this).get(NetworkViewModel.class);
        FoodID = getIntent().getLongExtra(DataManager.FoodID, 0);
        binding.Toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        reviewAdapter = new UserReviewAdapter();


        InitView();
        getfood_data(FoodID);
        GetRating();
    }

    private void InitView(){
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setAdapter(reviewAdapter);
    }

    private void GetRating(){
        networkViewModel.GetRatingByFoodID(String.valueOf(FoodID), 5).observe(this, new Observer<List<RatingModel>>() {
            @Override
            public void onChanged(List<RatingModel> ratingModels) {
                if(ratingModels != null){
                    reviewAdapter.setList(ratingModels);
                    reviewAdapter.notifyDataSetChanged();
                }else {
                    reviewAdapter.setList(null);
                    reviewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getfood_data(long foodID) {
        binding.BackButton.setOnClickListener(v ->{
            finish();
            Animatoo.animateSlideRight(FoodDetails.this);
        });

        networkViewModel.FoodGET(String.valueOf(foodID))
                .observe(this, new Observer<FoodData>() {
                    @Override
                    public void onChanged(FoodData foodData) {
                        if(foodData != null){
                            binding.FoodName.setText(foodData.getFoodTitle());
                            binding.Rating.setRating(foodData.getRating()/2);
                            binding.RatingCount.setText(String.valueOf(foodData.getRating()) +" Star Rating");
                            binding.Price.setText(String.valueOf("\u09F3 "+foodData.getFoodPrice()));
                            binding.CartPriceText.setText(String.valueOf("\u09F3 "+foodData.getFoodPrice()));
                            binding.Description.setText(foodData.getFoodDetails());
                            binding.ToolbarTitle.setText(foodData.getFoodTitle());

                            if(foodData.getImageOne() != null){
                                bannerone = new Banner();
                                bannerone.setFoodImage(foodData.getImageOne());
                                bannerList.add(bannerone);
                            }
                            if(foodData.getImageTwo() != null){
                                bannertwo = new Banner();
                                bannertwo.setFoodImage(foodData.getImageTwo());
                                bannerList.add(bannertwo);
                            }
                            if(foodData.getImageThree() != null){
                                bannerthree = new Banner();
                                bannerthree.setFoodImage(foodData.getImageThree());
                                bannerList.add(bannerthree);
                            }
                            if(foodData.getImageFour() != null){
                                bannerfour = new Banner();
                                bannerfour.setFoodImage(foodData.getImageFour());
                                bannerList.add(bannerfour);
                            }
                            bannerAdapter = new BannerAdapter(bannerList, getApplicationContext());
                            binding.ViewPagerID.setAdapter(bannerAdapter);
                            binding.TabIndacator.setupWithViewPager(binding.ViewPagerID);
                            bannerAdapter.notifyDataSetChanged();


                            TotalFoodPrice = Integer.valueOf(foodData.getFoodPrice());
                            FoodPrice = Integer.valueOf(foodData.getFoodPrice());
                            binding.MinusButton.setOnClickListener(v ->{

                                if(FoodCount >1){
                                    FoodCount = FoodCount-1;
                                    binding.FoodCountResult.setText(String.valueOf(FoodCount));
                                    TotalFoodPrice = TotalFoodPrice-FoodPrice;
                                    binding.CartPriceText.setText(String.valueOf("\u09F3 "+TotalFoodPrice));
                                }
                            });
                            binding.PlusButton.setOnClickListener(v ->{

                                if(FoodCount < 10){
                                    FoodCount = FoodCount+1;
                                    binding.FoodCountResult.setText(String.valueOf(FoodCount));
                                    TotalFoodPrice = TotalFoodPrice+FoodPrice;
                                    binding.CartPriceText.setText(String.valueOf("\u09F3 "+TotalFoodPrice));
                                }
                            });

                            CheckBookMark(foodID);
                            binding.BookMarkButton.setOnClickListener(v ->{
                                SetBookMark(foodID, foodData);
                            });

                            binding.AddCartButton.setOnClickListener(view -> {
                                roomViewModel.InsertCat(new CartModel( foodData.getFoodLocation(), foodData.getFoodMadeBy(), foodData.getFoodPrice(), foodData.getFoodTitle(), foodData.getImageOne(), foodData.getFoodID(), FoodPrice, FoodCount));
                                Toast.makeText(getApplicationContext(), "Cart added success", Toast.LENGTH_LONG).show();
                            });

                        }else {

                        }
                    }
                });
    }

    private void CheckBookMark(long FoodID){
        networkViewModel.GETBookMarkExists(FoodID).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.BookMarkButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_loveselected));
                }else {
                    binding.BookMarkButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lovedeselected));
                }
            }
        });


    }

    private void SetBookMark(long FoodID, FoodData foodData){
           networkViewModel.SetBookMark(FoodID, foodData).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(), "BookMark success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "BookMark remove", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideRight(FoodDetails.this);
    }
}