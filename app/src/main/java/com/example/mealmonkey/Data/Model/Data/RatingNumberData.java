package com.example.mealmonkey.Data.Model.Data;

import com.example.mealmonkey.Data.Model.RatingNumberModel;

import java.util.ArrayList;
import java.util.List;

public class RatingNumberData {
    public static List<RatingNumberModel> list;

    public static List<RatingNumberModel> GetRatingData(){
        list = new ArrayList<>();

        var one = new RatingNumberModel(1);
        var two = new RatingNumberModel(2);
        var three = new RatingNumberModel(3);
        var four = new RatingNumberModel(4);
        var five = new RatingNumberModel(5);
        var six = new RatingNumberModel(6);
        var seven = new RatingNumberModel(7);
        var eight = new RatingNumberModel(8);
        var nine = new RatingNumberModel(9);
        var ten = new RatingNumberModel(10);

        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(five);
        list.add(six);
        list.add(seven);
        list.add(eight);
        list.add(nine);
        list.add(ten);

        return list;
    }
}
