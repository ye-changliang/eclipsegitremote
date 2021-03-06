package com.tydic.bigdata.controller.shortlink;

import com.tydic.bigdata.service.shortlink.ShortLinkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LinksOutController {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 访问 短链接
     *
     * @param path
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{path}")
    public String viewShortLins(@PathVariable String path) {
        if (StringUtils.isEmpty(path)) {
            return "/";
        }
        String url = shortLinkService.getUrlByPath(path);
        if (StringUtils.isNotEmpty(url)) {
            return "redirect:" + url.trim();
        }
        return "error";
    }
}
