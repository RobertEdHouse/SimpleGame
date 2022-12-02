package com.example.android_lab_1.service;


import com.example.android_lab_1.model.History;

import java.util.List;

public interface Observer {
    void saveHistory(History history);
    List<History> loadHistory();
}
