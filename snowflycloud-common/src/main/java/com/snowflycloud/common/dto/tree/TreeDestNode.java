/**文件名: TreeDestNode.java
 * 版权: Hangzhou Hikvision digital technology Limited by Share Ltd copyright.
 * 描述: <描述信息>
 * 修改人: lixuefei
 * 修改时间: 2019/10/22 13:58:31
 * 修改单号: <修改单号>
 * 修改内容: 新增
 */

package com.snowflycloud.common.dto.tree;

import java.util.List;

import lombok.Data;

/**
 * @File : TreeDestNode.java
 * @Description :
 * @Copyright : Hangzhou Hikvision digital technology Limited by Share Ltd copyright.
 * @author : lixuefei
 * @Date : 2019/10/22 13:58:31
 * @Project : ids-common
 */

@Data
public class TreeDestNode<T extends TreeSourceNode> {

	private String id;

	private String label;

	private String parentId;

	private int level;

	private boolean disabled = false;

	private List<TreeDestNode<T>> children;

	private T meta;
}
