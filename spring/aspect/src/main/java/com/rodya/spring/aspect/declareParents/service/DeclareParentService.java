package com.rodya.spring.aspect.declareParents.service;

import org.springframework.stereotype.Service;

@Service
public class DeclareParentService implements DpService{

    public void play(){
        System.out.println("===========业务1==========");
    }
}
