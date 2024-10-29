package com.example.mealmonkey.Network.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Data.Model.CovidCountry;
import com.example.mealmonkey.Data.Model.EmailValidationResponse;
import com.example.mealmonkey.Data.Model.FoodData;
import com.example.mealmonkey.Data.Model.NetworkResponse;
import com.example.mealmonkey.Data.Model.OrderItem;
import com.example.mealmonkey.Data.Model.OrderModel;
import com.example.mealmonkey.Data.Model.ProfileImageDownloadUri;
import com.example.mealmonkey.Data.Model.User;
import com.example.mealmonkey.Network.Repository.BeveragesItemSize;
import com.example.mealmonkey.Network.Repository.BookMarkDataGET;
import com.example.mealmonkey.Network.Repository.BookMarkItemRemovePOST;
import com.example.mealmonkey.Network.Repository.BookMarkPOST;
import com.example.mealmonkey.Network.Repository.BookMarkSETGET;
import com.example.mealmonkey.Network.Repository.CheckUserLogin;
import com.example.mealmonkey.Network.Repository.CountryFoodGET;
import com.example.mealmonkey.Network.Repository.CovidBangladeshDataGET;
import com.example.mealmonkey.Network.Repository.CurrentProfileGET;
import com.example.mealmonkey.Network.Repository.DessertsItemSize;
import com.example.mealmonkey.Network.Repository.EmailValidationGET;
import com.example.mealmonkey.Network.Repository.FoodDataGET;
import com.example.mealmonkey.Network.Repository.FoodGET;
import com.example.mealmonkey.Network.Repository.FoodItemSizeGET;
import com.example.mealmonkey.Network.Repository.FoodOrderPOST;
import com.example.mealmonkey.Network.Repository.GoogleSignInUserPOST;
import com.example.mealmonkey.Network.Repository.LoginGET;
import com.example.mealmonkey.Network.Repository.LogoutPOST;
import com.example.mealmonkey.Network.Repository.OfferFoodGET;
import com.example.mealmonkey.Network.Repository.OrderDetailsGET;
import com.example.mealmonkey.Network.Repository.OrderFoodGET;
import com.example.mealmonkey.Network.Repository.ProfileImagePOST;
import com.example.mealmonkey.Network.Repository.ProfileImageStoragePOST;
import com.example.mealmonkey.Network.Repository.PromotionSizeGET;
import com.example.mealmonkey.Network.Repository.PromotionsGET;
import com.example.mealmonkey.Network.Repository.PromotionsItemSizeGET;
import com.example.mealmonkey.Network.Repository.RatingByFoodIDGET;
import com.example.mealmonkey.Network.Repository.RatingPOST;
import com.example.mealmonkey.Network.Repository.SearchFoodGET;
import com.example.mealmonkey.Network.Repository.SignUpPOST;
import com.example.mealmonkey.Network.Repository.UserPOST;
import com.example.mealmonkey.RatingModel;

import java.util.List;

public class NetworkViewModel extends AndroidViewModel {

    private RatingByFoodIDGET ratingByFoodIDGET;
    private OrderFoodGET orderFoodGET;
    private OrderDetailsGET orderDetailsGET;
    private FoodOrderPOST foodOrderPOST;
    private CheckUserLogin checkUserLogin;
    private LoginGET loginGET;
    private LogoutPOST logoutPOST;
    private SignUpPOST signUpPOST;
    private CurrentProfileGET currentProfileGET;
    private UserPOST userPOST;
    private ProfileImageStoragePOST profileImageStoragePOST;
    private GoogleSignInUserPOST googleSignInUserPOST;
    private ProfileImagePOST profileImagePOST;

    private FoodItemSizeGET foodItemSizeGET;
    private BeveragesItemSize beveragesItemSize;
    private DessertsItemSize dessertsItemSize;
    private PromotionsItemSizeGET promotionsItemSizeGET;
    private PromotionSizeGET promotionSizeGET;
    private FoodDataGET foodDataGET;
    private FoodGET foodGET;
    private SearchFoodGET searchFoodGET;
    private PromotionsGET promotionsGET;
    private OfferFoodGET offerFoodGET;
    private CountryFoodGET countryFoodGET;

    private BookMarkPOST bookMarkPOST;
    private BookMarkSETGET bookMarkSETGET;
    private BookMarkDataGET bookMarkDataGET;
    private BookMarkItemRemovePOST bookMarkItemRemovePOST;

    private CovidBangladeshDataGET covidBangladeshDataGET;
    private EmailValidationGET emailValidationGET;
    private RatingPOST ratingPOST;


    public NetworkViewModel(@NonNull Application application) {
        super(application);

        ratingByFoodIDGET = new RatingByFoodIDGET(application);
        ratingPOST = new RatingPOST(application);
        orderFoodGET = new OrderFoodGET(application);
        orderDetailsGET = new OrderDetailsGET(application);
        foodOrderPOST = new FoodOrderPOST(application);
        checkUserLogin = new CheckUserLogin(application);
        loginGET = new LoginGET(application);
        logoutPOST = new LogoutPOST(application);
        signUpPOST = new SignUpPOST(application);
        currentProfileGET = new CurrentProfileGET(application);
        userPOST = new UserPOST(application);
        profileImageStoragePOST = new ProfileImageStoragePOST(application);
        googleSignInUserPOST = new GoogleSignInUserPOST(application);
        profileImagePOST = new ProfileImagePOST(application);

        foodItemSizeGET = new FoodItemSizeGET(application);
        beveragesItemSize = new BeveragesItemSize(application);
        dessertsItemSize = new DessertsItemSize(application);
        promotionsItemSizeGET = new PromotionsItemSizeGET(application);
        foodDataGET = new FoodDataGET(application);
        foodGET = new FoodGET(application);
        searchFoodGET = new SearchFoodGET(application);
        promotionsGET = new PromotionsGET(application);
        promotionSizeGET = new PromotionSizeGET(application);
        offerFoodGET = new OfferFoodGET(application);
        countryFoodGET = new CountryFoodGET(application);
        bookMarkPOST = new BookMarkPOST(application);
        bookMarkSETGET = new BookMarkSETGET(application);
        bookMarkDataGET = new BookMarkDataGET(application);
        bookMarkItemRemovePOST = new BookMarkItemRemovePOST(application);

        covidBangladeshDataGET = new CovidBangladeshDataGET(application);
        emailValidationGET = new EmailValidationGET(application);
    }

    public LiveData<List<RatingModel>> GetRatingByFoodID(String FoodID, int Size){
        return ratingByFoodIDGET.GetRatingByFoodID(FoodID, Size);
    }
    public LiveData<Boolean> SendRating(String FoodID, String Message, int NoOFRating){
        return ratingPOST.SendRating(FoodID, Message, NoOFRating);
    }
    public LiveData<List<OrderItem>> GetOrderItem(String UID){
        return orderFoodGET.OrderItem(UID);
    }
    public LiveData<List<OrderModel>> GetOrder(){
        return orderDetailsGET.GetOrderDetails();
    }
    public LiveData<Boolean> OrderFood(String FirstName, String LastName, String PhoneNumber, String StreetAddress, String City, String Area, String Note, List<CartModel> list){
        return foodOrderPOST.OrderFood(FirstName, LastName, PhoneNumber, StreetAddress, City, Area, Note, list);
    }
    public LiveData<Integer> CheckUserLogin() {
        return checkUserLogin.CheckUserLoginAccount();
    }

    public LiveData<NetworkResponse> LoginEmailPassword(String Email, String Password) {
        return loginGET.LoginEmailPassword(Email, Password);
    }

    public LiveData<Integer> LogoutAccount() {
        return logoutPOST.LogOutAccount();
    }

    public LiveData<NetworkResponse> SignUpAccount(String Name, String Email, String Mobile, String Address, String Password, String ConfirmPassword) {
        return signUpPOST.SignUpAccount(Name, Email, Mobile, Address, Password, ConfirmPassword);
    }

    public LiveData<User> getCurrentUser() {
        return currentProfileGET.getCurrentUser();
    }

    public LiveData<NetworkResponse> SendUserPost(String Name, String Email, String Address, String Phone, String Birthday) {
        return userPOST.SendUserData(Name, Email, Address, Phone, Birthday);
    }

    public LiveData<ProfileImageDownloadUri> setupprofileimage(Uri imageuri) {
        return profileImageStoragePOST.upload_profileimage(imageuri);
    }

    public LiveData<NetworkResponse> GoogleSignUser(String Email, String Name, String Phone, String PhotoUri) {
        return googleSignInUserPOST.UploadProfile(Email, Name, Phone, PhotoUri);
    }

    public LiveData<NetworkResponse> UpdateProfileImage(String ImageUri) {
        return profileImagePOST.ProfileImagePOST(ImageUri);
    }

    public LiveData<Integer> GetFoodItemSize(String FoodItem) {
        return foodItemSizeGET.GetFoodSize(FoodItem);
    }

    public LiveData<Integer> GetBeveragesSize(String FoodItem) {
        return beveragesItemSize.getBeveragesFoodSize(FoodItem);
    }

    public LiveData<Integer> GetDessertsSize(String FoodItem) {
        return dessertsItemSize.getSizeDessertsFoodSize(FoodItem);
    }

    public LiveData<Integer> GetPromotionsSize(String FoodType) {
        return promotionsItemSizeGET.getPromotionsItemSize(FoodType);
    }

    public LiveData<List<FoodData>> GetFoodData(String FoodType, int Limit) {
        return foodDataGET.GetFoodData(FoodType, Limit);
    }

    public LiveData<FoodData> FoodGET(String FoodID) {
        return foodGET.GetFood(FoodID);
    }

    public LiveData<List<FoodData>> SearchFood(String FoodName, int Limit) {
        return searchFoodGET.SearchFood(FoodName, Limit);
    }

    public LiveData<List<FoodData>> GetPromotionFood(String Data, int Limit) {
        return promotionsGET.GetPromotionFood(Data, Limit);
    }

    public LiveData<Integer> GetPromotionSize(String Data) {
        return promotionSizeGET.PromotionSize(Data);
    }

    public LiveData<List<FoodData>> GetOfferFood(String Data, int Limit) {
        return offerFoodGET.GetOfferFood(Data, Limit);
    }

    public LiveData<List<FoodData>> GetCountryFood(String CountryName, int Limit){
        return countryFoodGET.getCountryFood(CountryName, Limit);
    }
    public LiveData<Boolean> SetBookMark(long FoodID, FoodData foodData){
        return bookMarkPOST.SetBookMark(FoodID, foodData);
    }
    public LiveData<Boolean> GETBookMarkExists(long FoodID){
        return bookMarkSETGET.GetBookMarkExists(FoodID);
    }

    public LiveData<List<FoodData>> GetBookMarkData(){
        return bookMarkDataGET.GETBookMarkData();
    }

    public LiveData<Boolean> BookMarkRemove(long FoodID){
        return bookMarkItemRemovePOST.RemoveBookMark(FoodID);
    }

    public LiveData<CovidCountry> GetCovidData(){
        return covidBangladeshDataGET.GetBangladeshCovidData();
    }

    public LiveData<EmailValidationResponse> EmailValidation(String ApiKey, String EmailAddress){
        return emailValidationGET.getEmailValidation(ApiKey, EmailAddress);
    }
}
