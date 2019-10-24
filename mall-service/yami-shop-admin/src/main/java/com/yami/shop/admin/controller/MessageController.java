/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.admin.controller;

import java.util.Arrays;
import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.yami.shop.common.util.PageParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yami.shop.bean.enums.MessageStatus;
import com.yami.shop.bean.model.Message;
import com.yami.shop.service.MessageService;

import cn.hutool.core.util.StrUtil;


/**
 * @author lgh on 2018/10/15.
 */
@RestController
@RequestMapping("/admin/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 分页获取
     */
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:message:page')")
    public ResponseEntity<IPage<Message>> page(Message message,PageParam<Message> page) {
        IPage<Message> messages = messageService.page(page, new LambdaQueryWrapper<Message>()
                .like(StrUtil.isNotBlank(message.getUserName()), Message::getUserName, message.getUserName())
                .eq(message.getStatus() != null, Message::getStatus, message.getStatus()));
        return ResponseEntity.ok(messages);
    }

    /**
     * 获取信息
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('admin:message:info')")
    public ResponseEntity<Message> info(@PathVariable("id") Long id) {
        Message message = messageService.getById(id);
        return ResponseEntity.ok(message);
    }

    /**
     * 保存
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin:message:save')")
    public ResponseEntity<Void> save(@RequestBody Message message) {
        messageService.save(message);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin:message:update')")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        messageService.updateById(message);
        return ResponseEntity.ok().build();
    }

    /**
     * 公开留言
     */
    @PutMapping("/release/{id}")
    @PreAuthorize("@pms.hasPermission('admin:message:release')")
    public ResponseEntity<Void> release(@PathVariable("id") Long id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(MessageStatus.RELEASE.value());
        messageService.updateById(message);
        return ResponseEntity.ok().build();
    }

    /**
     * 取消公开留言
     */
    @PutMapping("/cancel/{id}")
    @PreAuthorize("@pms.hasPermission('admin:message:cancel')")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(MessageStatus.CANCEL.value());
        messageService.updateById(message);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@pms.hasPermission('admin:message:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long[] ids) {
        messageService.removeByIds(Arrays.asList(ids));
        return ResponseEntity.ok().build();
    }
}
