package org.example.hacking02_sk.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.example.hacking02_sk.mapper.BankingMapper;
import org.example.hacking02_sk.mapper.BankinghistMapper;
import org.example.hacking02_sk.model.Banking;
import org.example.hacking02_sk.model.SendBanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/banking")
public class BankingController {
    @Autowired
    BankingMapper bankingMapper;

    @Autowired
    BankinghistMapper bankinghistMapper;

    @ModelAttribute("accList")
    List<Banking> accList(HttpSession session){
        //String myid = (String) session.getAttribute();
        return bankingMapper.myid("test");
    }

    @ModelAttribute("bankList")
    String[] bankList(){
        String banks = "머니스트,KEB하나은행,SC제일은행,국민은행,신한은행,외환은행,우리은행,한국시티은행,농협,기업은행";
        String[] list = banks.split(",");
        return list;
    }

    @RequestMapping("{page}")
    String bankingInOut(
            @PathVariable String page,
            HttpSession session,
            Model model){
        //String myid = session.getAttribute();
        if (page.equals("myaccount")) {
            List<Banking> banking = bankingMapper.myid("test");
            model.addAttribute("accs", banking);
        }
        return "banking/" + page;
    }

    @PostMapping("getmyacc")
    @ResponseBody
    Map<String, Integer> getmyacc(@RequestBody Map<String, String> myacc){
        Banking bank = bankingMapper.myacc(Integer.parseInt(myacc.get("myacc")));
        Map<String, Integer> acc = new HashMap<>();
        acc.put("money", bank.getMymoney());
        return acc;
    };

    @GetMapping("selectacc")
    String select(int check) {
        System.out.println(check);
        return "banking/inout";
    }

    @PostMapping("sendBank")
    String sendBank(SendBanking sendBanking, Model model) {
        String msg = "";
        Banking banking = bankingMapper.myacc(sendBanking.getMyacc());
        int submoney = banking.getMymoney() - sendBanking.getMyaccbalance();

        if (submoney < 0) {
            msg = "잔고가 부족합니다.";
            model.addAttribute("msg", msg);
            return "banking/alert";
        }else if (sendBanking.getMyaccpw() != banking.getMyaccpw()) {
            msg = "비밀번호를 다시 입력해주세요.";
            model.addAttribute("msg", msg);
            return "banking/alert";
        }

        sendBanking.setMyaccout(submoney);
        bankingMapper.submoney(sendBanking.getMyaccbalance(), sendBanking.getMyacc());
        bankinghistMapper.insert(sendBanking);

        if (sendBanking.getMysendbank().equals("머니스트")) {
            Banking banking2 = bankingMapper.myacc(sendBanking.getMysendacc());
            int addmoney = banking2.getMymoney() + sendBanking.getMyaccbalance();
            sendBanking.setMyaccin(addmoney);
            sendBanking.setMyaccout(0);
            sendBanking.setMysendacc(sendBanking.getMyacc());
            sendBanking.setMyacc(banking2.getMyacc());
            bankingMapper.addmoney(sendBanking.getMyaccbalance(), sendBanking.getMyacc());
            bankinghistMapper.insert(sendBanking);
        }
        //System.out.println(sendBanking);
        return "index";
    }
}
