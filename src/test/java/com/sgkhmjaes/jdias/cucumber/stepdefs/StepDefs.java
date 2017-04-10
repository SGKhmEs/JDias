package com.sgkhmjaes.jdias.cucumber.stepdefs;

import com.sgkhmjaes.jdias.JDiasApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = JDiasApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
