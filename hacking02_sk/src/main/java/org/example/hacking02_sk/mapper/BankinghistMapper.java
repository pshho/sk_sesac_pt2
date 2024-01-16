package org.example.hacking02_sk.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.hacking02_sk.model.SendBanking;

@Mapper
public interface BankinghistMapper {
    int insert(SendBanking sendBanking);
}
