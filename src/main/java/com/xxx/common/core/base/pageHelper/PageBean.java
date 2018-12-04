package com.xxx.common.core.base.pageHelper;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * pageHelper结果集自定义pageBean
 *
 * @author xujingyang
 * @date 2018/05/28
 */
@ApiModel(value = "PageBean", description = "分页数据封装")
public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;

    @ApiModelProperty(value = "总记录数")
    private long total;        //总记录数

    @ApiModelProperty(value = "结果集")
    private List<T> list;    //结果集

    @ApiModelProperty(value = "第几页")
    private int pageNum;    // 第几页

    @ApiModelProperty(value = "每页记录数")
    private int pageSize;    // 每页记录数

    @ApiModelProperty(value = "总页数")
    private int pages;        // 总页数

    @ApiModelProperty(value = "当前页的数量 ，应该 <= pageSize，该属性来自ArrayList的size属性")
    private int size;        // 当前页的数量 ，应该 <= pageSize，该属性来自ArrayList的size属性


    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     *
     * @param list page结果
     */
    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
        }
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
