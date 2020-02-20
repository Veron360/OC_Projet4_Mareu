package com.example.oc_projet4_maru.DI;

import com.example.oc_projet4_maru.Service.ApiService;
import com.example.oc_projet4_maru.Service.MeetingApiService;

public class Injection {

    private static ApiService service;

    public static ApiService getMeetingApiService() {
        return service;
    }

    public static ApiService getNewInstanceApiService(){
        service = new MeetingApiService();
        return service;
    }
}
