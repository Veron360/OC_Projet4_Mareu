package com.example.oc_projet4_maru.Model;

public class Room {

    private int id;
    private String Room;
    private int ImageRoom;


    public Room(int id, String room, int imageRoom) {
        this.id = id;
        this.Room = room;
        this.ImageRoom = imageRoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public int getImageRoom() {
        return ImageRoom;
    }

    public void setImageRoom(int imageRoom) {
        ImageRoom = imageRoom;
    }
}
