package com.rodya.spring.aspect.sequence.service;


import com.rodya.spring.aspect.sequence.aspect.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class SeqInService {

    @SeqBefore_1
    @SeqBefore_2
    @SeqBefore_3
    @SeqAfter_1
    @SeqAfter_2
    @SeqAfter_3
    @SeqAround_1
    @SeqAround_2
    @SeqAround_3
    public void seq() {
        System.out.println("结论:切面内是按照方法名排序的");
    }
}
