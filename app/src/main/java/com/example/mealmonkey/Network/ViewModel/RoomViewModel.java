package com.example.mealmonkey.Network.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Data.Model.PaymentCardModel;
import com.example.mealmonkey.Network.RoomDatabase.Dao;
import com.example.mealmonkey.Network.RoomDatabase.Database;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomViewModel extends AndroidViewModel {

    private Dao cardDao;
    private Database database;
    private Executor executor;


    public RoomViewModel(@NonNull Application application) {
        super(application);

        database = Database.getdatabase(application);
        cardDao = database.cardDao();
        executor = Executors.newSingleThreadExecutor();
    }

    //todo cart data
    public void InsertCat(CartModel cartModel){
        executor.execute(() -> cardDao.InsertCartItem(cartModel));
    }

    public LiveData<List<CartModel>> GetCartData(){
        return cardDao.GetCart();
    }

    public void DeleteCartItem(long ID){
        executor.execute(() -> cardDao.DeleteByFoodID(ID));
    }

    public void DeleteCart(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cardDao.DeleteCart();
            }
        });
    }
    //todo cart data



    public LiveData<List<PaymentCardModel>> getCardData(){
        return cardDao.getPaymentcard();
    }

    public void InsertCardData(PaymentCardModel paymentCardModel){
        new InsertCardData(cardDao).execute(paymentCardModel);
    }

    public void DeleteCardByNumber(String cardnumber){
        new DeleteCardByNumber(cardDao).execute(cardnumber);
    }

    class InsertCardData extends AsyncTask<PaymentCardModel, Void, Void>{

        private Dao cardDao;

        public InsertCardData(Dao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(PaymentCardModel... paymentCardModels) {
            cardDao.AddCard(paymentCardModels[0]);
            return null;
        }
    }



    class DeleteCardByNumber extends AsyncTask<String, Void, Void>{

        private Dao cardDao;

        public DeleteCardByNumber(Dao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            cardDao.DeleteByCardNumber(strings[0]);
            return null;
        }
    }
}
