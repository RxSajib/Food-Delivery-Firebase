package com.example.mealmonkey.Recelerview.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmonkey.Data.Model.Data.DataManager;
import com.example.mealmonkey.RatingModel;
import com.example.mealmonkey.Recelerview.ViewHolder.UserReviewVH;
import com.example.mealmonkey.databinding.UserratingitemBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewVH> {

    private List<RatingModel> list;

    public List<RatingModel> getList() {
        return list;
    }

    public void setList(List<RatingModel> list) {
        this.list = list;
    }
    private CollectionReference UserRef;

    @NonNull
    @Override
    public UserReviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserRef = FirebaseFirestore.getInstance().collection(DataManager.Users);
         var l = LayoutInflater.from(parent.getContext());
         var v = UserratingitemBinding.inflate(l, parent, false);
         return new UserReviewVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewVH holder, int position) {
        UserRef.document(list.get(position).getUID()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(value.exists()){
                    var photo = value.getString("ProfileImage");
                    Picasso.get().load(photo).into(holder.binding.ProfileImage);

                    var name = value.getString("Name");
                    holder.binding.UserName.setText(name);
                }
            }
        });

        holder.binding.Comment.setText(list.get(position).getComment());
        holder.binding.RatingBar.setRating(list.get(position).getNoOFRating());
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }else {
            return list.size();
        }
    }
}
