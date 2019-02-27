package com.tydic.bigdata.service.apiconfig;

import com.tydic.bigdata.domain.apiconfig.ApiConfigMenu;
import com.tydic.bigdata.repository.apiconfig.ApiConfigMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ApiConfigMenuService {

    @Autowired
    private ApiConfigMenuRepository apiConfigMenuRepository;

    /**
     * 根据名称 模糊匹配 记录 分页
     *
     * @param pageable
     * @param apiConfigMenu
     * @return
     */
    public Page<ApiConfigMenu> findAll(Pageable pageable, ApiConfigMenu apiConfigMenu) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("apiName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("status", match -> match.stringMatcher(ExampleMatcher.StringMatcher.DEFAULT));
        apiConfigMenu.setStatus(1);
        return apiConfigMenuRepository.findAll(Example.of(apiConfigMenu, exampleMatcher), pageable);
    }


    /**
     * 保存修改
     *
     * @param apiConfigMenu
     * @return
     */
    public Boolean save(ApiConfigMenu apiConfigMenu) {
        ApiConfigMenu finalApiConfigMenu = apiConfigMenu;
        if (apiConfigMenu.getId() != null) {
            Optional<ApiConfigMenu> apiOption = apiConfigMenuRepository.findById(apiConfigMenu.getId());
            apiOption.ifPresent(config -> {
                finalApiConfigMenu.setApiCode(config.getApiCode());
            });
        }
        apiConfigMenu = apiConfigMenuRepository.save(finalApiConfigMenu);
        return apiConfigMenu.getId() != null;

    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public Boolean delete(Long[] ids) {
        return apiConfigMenuRepository.deleteByIds(Arrays.asList(ids)) > 0;
    }
}
