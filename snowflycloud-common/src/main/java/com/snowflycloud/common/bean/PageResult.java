package com.snowflycloud.common.bean;/**
 * Created by xflig on 2019/6/22.
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PageResult
 * @Description: 分页查询返回结果集.
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/6/22 22:45
 * @Vserion 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult implements Serializable {

    private Integer pageSize;

    private Integer currentPage;

    private Long totalCount;

    private Integer totalPage;

    private List list;

    public PageResult(Integer currentPage, Integer pageSize, Long totalCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        // 如果没有指定当前查哪页, 则默认查第1页
        if (currentPage == null) {
            this.currentPage = 1;
        }
        // 如果没有指定每页多少条数据, 则默认为5条
        if (pageSize == null) {
            this.pageSize = 10;
        }
        // 获取总页数
        this.totalPage = (int) Math.ceil(1.0 * this.totalCount / this.pageSize);
        // 当前页不能小于1
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }
        // 当前页不能大于总页数
        if (this.currentPage > this.totalPage && this.totalPage != 0) {
            this.currentPage = this.totalPage;
        }
    }

    /**
     * 获取分页查询的起始参数
     *
     * @return
     */
    public int getStart() {
        return (this.currentPage - 1) * this.pageSize;
    }




}
