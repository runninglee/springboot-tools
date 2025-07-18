package com.julan.tools.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * @Project : tools
 * @Package : com.julan.tools.entity
 * @ClassName : ApiLog
 * @Description : ApiLog Model
 * @Author : huilee
 * @CreateTime : 2025/7/18 13:54
 * @Version : V1.0.0
 */


@Entity
@Table(name = "api_log", schema = "public")
@Data
public class ApiLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //发起、接收
    @Column(name = "category")
    private String category;

    //任务类型
    @Column(name = "task")
    private String task;

    //接口名称
    @Column(name = "name")
    private String name;

    //关联模型
    @Column(name = "rel_type")
    private String relType;

    //关联模型 ID
    @Column(name = "rel_id")
    private String relId;

    //请求主机
    @Column(name = "host")
    private String host;

    //请求头
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "header", columnDefinition = "jsonb")
    private Object header;

    //请求方式
    @Column(name = "method")
    private String method;

    //请求体
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "body", columnDefinition = "jsonb")
    private Object body;

    //响应结果
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "response", columnDefinition = "jsonb")
    private Object response;

    //创建时间
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //更新时间
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
