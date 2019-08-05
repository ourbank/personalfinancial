package com.icbc.index.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

/*
 * 控制器，前后台以json数据格式交互
 * @author mychen
 */

@Controller
@EnableScheduling
public class Control {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

}

