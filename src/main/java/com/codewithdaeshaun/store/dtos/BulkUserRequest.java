package com.codewithdaeshaun.store.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BulkUserRequest {
    private List<RegisterUserRequest> users;
    private String batchId;  // For tracking
}
