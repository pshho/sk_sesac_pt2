package org.example.hacking02_sk.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("bank")
@Data
public class Banking {
    int myacc, mymoney, myaccpw;
    String myid, mybank;
}
