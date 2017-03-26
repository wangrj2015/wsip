package com.loavne.wsip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class TestRegex {

    public static void main(String[] args){
        Pattern pattern = Pattern.compile("Digest (.*?,*\\s*)*");
        Matcher matcher = pattern.matcher(" Digest realm=\"127.0.0.1\", nonce=\"1490450415005\", username=\"001\",  uri=\"sip:localhost:10003;transport=tcp\", response=\"6c1d87330a62c0d5b82a51fef6939827\"");
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }

    }
}
