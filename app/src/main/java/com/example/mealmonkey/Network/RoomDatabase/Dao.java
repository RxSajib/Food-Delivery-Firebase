package com.example.mealmonkey.Network.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Data.Model.PaymentCardModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertCartItem(CartModel cartModel);

    @Query("SELECT * FROM CartDB")
    public LiveData<List<CartModel>> GetCart();

    @Query("DELETE FROM CartDB WHERE FoodID = :FoodID")
    void DeleteByFoodID(long FoodID);

    @Query("DELETE  FROM CartDB")
    public void  DeleteCart();





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void AddCard(PaymentCardModel paymentCardModel);


    @Query("SELECT * FROM PaymentDB")
    public LiveData<List<PaymentCardModel>> getPaymentcard();

    @Query("DELETE FROM PaymentDB")
    public void DeleteCard();

    @Query("DELETE FROM PaymentDB WHERE CardNumber = :CardNumber")
    public void DeleteByCardNumber(String CardNumber);
}
