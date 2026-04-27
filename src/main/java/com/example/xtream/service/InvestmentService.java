package com.example.xtream.service;

import com.example.xtream.dto.response.ResponseDTO;
import org.apache.coyote.Response;

public interface InvestmentService {
    ResponseDTO getAllInvestments();
}
