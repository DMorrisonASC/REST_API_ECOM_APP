package com.codewithdaeshaun.store.dtos;

import lombok.Data;
import java.util.List;

@Data
public class BulkUserResponse {
    private int successCount;
    private int failureCount;
    private List<UserDto> successes;
    private List<String> failures;  // Error messages for failed creations
    private String batchId;         // For tracking this batch

    public BulkUserResponse(List<UserDto> successes, List<String> failures, String batchId) {
        this.successes = successes;
        this.failures = failures;
        this.successCount = successes.size();
        this.failureCount = failures.size();
        this.batchId = batchId;
    }
}