package org.data.redroleplay.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/whitelist")
@RequiredArgsConstructor
public class WhiteListRequestController {

    @RequestMapping
    public String showWhitelistPage() {




        return "pages/whiteList/whitelist";
    }

    @RequestMapping("/request")
    public String showWhitelistRequestPage() {
        return "pages/whiteList/whitelist-request";
    }

}
