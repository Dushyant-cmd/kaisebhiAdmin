package com.example.kaisebhiadmin.models

//map.put("date", spf.format(new Date(System.currentTimeMillis())));
//map.put("timestamp", System.currentTimeMillis());
//map.put("amount", sharedPrefManager.getsUser().getReward());
//map.put("type", "withdraw");
//map.put("remark", "");
//map.put("userId", sharedPrefManager.getUid());
//map.put("typeUpi", typeUpi);
//map.put("status", "pending");
//map.put("upiNumber", id);
data class WithdrawModel(
    val date: String,
    val timestamp: Long,
    val amount: Long,
    val type: String,
    val remark: String,
    val userId: String,
    val typeUpi: String,
    val status: String,
    val upiNumber: String) {
}