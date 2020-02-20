package com.example.oc_projet4_maru.Service;

import com.example.oc_projet4_maru.Model.Room;
import com.example.oc_projet4_marumaru.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RoomGenerator {

    private static final List<Room> Room_GENERATOR = Arrays.asList(

            new Room(1,"Salle n°1", R.drawable.round_brightness_1),
            new Room(2,"Salle n°2", R.drawable.round_brightness_2),
            new Room(3,"Salle n°3", R.drawable.round_brightness_3),
            new Room(4,"Salle n°4", R.drawable.round_brightness_4),
            new Room(5,"Salle n°5", R.drawable.round_brightness_5),
            new Room(6,"Salle n°6", R.drawable.round_brightness_6),
            new Room(7,"Salle n°7", R.drawable.round_brightness_7),
            new Room(8,"Salle n°8", R.drawable.round_brightness_8),
            new Room(9,"Salle n°9", R.drawable.round_brightness_9),
            new Room(10,"Salle n°10", R.drawable.round_brightness_10));

    public static List<Room> generateRooms() {
        return new ArrayList<>(Room_GENERATOR);
    }
}
