package com.tydic.bigdata.controller.shortlink;

import com.tydic.bigdata.domain.shortlink.ShortLink;
import com.tydic.bigdata.service.shortlink.ShortLinkService;
import com.tydic.bigdata.utils.ShortLinksCreatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/short-links")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 跳转短链接管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index() {
        return "shortlink/index";
    }


    /**
     * 短链接 列表数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/shortLinks")
    public ResponseEntity shortLinks(ShortLink shortLink
            , @PageableDefault(page = 1) Pageable pageable) {
        Page<ShortLink> page = shortLinkService.findAll(pageable, shortLink);
        return ResponseEntity.ok(page);
    }

    /**
     * 保存操作
     *
     * @param link
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public boolean save(@ModelAttribute ShortLink link) {
        return shortLinkService.save(link);
    }


    /**
     * 生成短链接
     *
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/getShortUrl")
    public ResponseEntity getShortUrl(String url) {
        return ResponseEntity.ok(ShortLinksCreatorUtils.getShortUrl(url));
    }


    /**
     * 删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity delete(@RequestBody ShortLink[] links) {
        return ResponseEntity.ok(shortLinkService.delete(links));
    }
}
