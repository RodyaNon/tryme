package com.rodya.sequence.service;

import com.rodya.sequence.aspect.annotation.SeqBeforeBtw;
import org.springframework.stereotype.Service;

@Service
public class SeqBtwService {
    @SeqBeforeBtw
    public void seq() {
        System.out.println("结论:切面间类名排序,如果使用@Order注解,按照order排序");
    }
}
