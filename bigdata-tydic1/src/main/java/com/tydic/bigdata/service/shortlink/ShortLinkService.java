package com.tydic.bigdata.service.shortlink;

import com.tydic.bigdata.domain.shortlink.ShortLink;
import com.tydic.bigdata.repository.shortlink.ShortLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.util.*;

@Service
public class ShortLinkService {

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    /**
     * 列表数据
     *
     * @param pageable
     * @param shortLink
     * @return
     */
    public Page<ShortLink> findAll(Pageable pageable, ShortLink shortLink) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("valid", match -> match.stringMatcher(ExampleMatcher.StringMatcher.DEFAULT))
                .withMatcher("privilgeLeve", match -> match.stringMatcher(ExampleMatcher.StringMatcher.DEFAULT));
        shortLink.setValid(1);
        shortLink.setPrivilgeLeve(1);
        return shortLinkRepository.findAll(Example.of(shortLink, exampleMatcher), pageable);
    }

    /**
     * 新增短链接记录
     *
     * @param shortLink
     * @return
     */
    public boolean save(ShortLink shortLink) {
        if (Objects.equals(shortLink.getId(), null)) {
            Long id = shortLinkRepository.findMaxId();
            if (id != null) {
                shortLinkRepository.saveLink(id, shortLink.getName(), shortLink.getUrl(), shortLink.getShortUrl());
                return true;
            } else {
                return false;
            }
        } else {
            Optional<ShortLink> shortLinkOptional = shortLinkRepository.findById(shortLink.getId());
            if (shortLinkOptional.isPresent()) {
                ShortLink shortLinkO = shortLinkOptional.get();
                shortLinkO.setShortUrl(shortLink.getShortUrl());
                shortLinkO.setName(shortLink.getName());
                shortLinkO.setUrl(shortLink.getUrl());
                shortLinkRepository.save(shortLinkO);
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * 删除 记录
     *
     * @param links
     * @return
     */
    public boolean delete(ShortLink[] links) {
        List<Long> ids = new ArrayList<>();
        Arrays.stream(links).forEach(link ->
                ids.add(link.getId())
        );
        return shortLinkRepository.deleteByIds(ids) > 0;
    }


    public String getUrlByPath(String path) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        ShortLink shortLink = new ShortLink();
        shortLink.setShortUrl(path);
        Optional<ShortLink> shortLinkOptional = shortLinkRepository.findOne(Example.of(shortLink, exampleMatcher));
        if (shortLinkOptional.isPresent()) {
            return shortLinkOptional.get().getUrl();
        }
        return "";
    }
}
