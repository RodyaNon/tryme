package com.rodya.spring.aspect;

import com.rodya.spring.aspect.sequence.service.SeqBtwService;
import com.rodya.spring.aspect.sequence.service.SeqInService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SequenceTest {

    @Autowired
    SeqInService seqInService;

    @Autowired
    SeqBtwService seqBtwService;

    @Test
    void testSeq(){
        seqInService.seq();
    }

    @Test
    void testSeqBtw(){
        seqBtwService.seq();
    }
}
