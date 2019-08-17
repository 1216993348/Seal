package com.bcsfxy.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

/**
 * 定义公共的DAO操作接口标准，基本的功能包括：增加、修改全部、删除数据、根据编号查询、查询全部、分页显示、数据统计
 * @author xhy
 * @param <K> 表示要操作的主键类型，由子接口实现
 * @param <V> 表示要操作的VO类型，由子接口实现
 */
public interface IDAO<K,V> {
	/**
	 * 实现数据的增加操作
	 * @param vo 包含了要增加数据的VO对象
	 * @return 数据保存成功返回true，否则返回false
	 */
	public boolean doCreate(V vo);
	/**
	 * 实现数据的修改操作，本次修改是根据id进行全部字段数据的修改
	 * @param vo 包含了要修改数据的信息，一定要提供有ID内容
	 * @return 数据修改成功返回true，否则返回false
	 */
	public boolean doUpdate(V vo)  ;
	/**
	 * 执行数据的批量删除操作，所有要删除的数据以Set集合的形式保存
	 * @param ids 包含了所有要删除的数据ID，不包含有重复内容
	 * @return 删除成功返回true（删除的数据个数与要删除的数据个数相同），否则返回false。
	 * @ SQL执行异常
	 */
	public Integer doRemoveBatch(@Param("ids") Set<K> ids)  ;
	/**
	 * 根据id删除指定数据
	 * @param id
	 * @return
	 */
	public boolean doRemoveById(K id);
	/**
	 * 根据雇员编号查询指定的雇员信息
	 * @param id 要查询的雇员编号
	 * @return 如果信息存在，则将数据以VO类对象的形式返回，如果雇员数据不存在，则返回null
	 */
	public V findById(K id)  ;
	/**
	 * 查询指定数据表的全部记录，并且以集合的形式返回
	 * @return 如果表中有数据，则所有的数据会封装为VO对象而后利用List集合返回，<br>
	 * 如果没有数据，那么集合的长度为0（size() == 0，不是null）
	 */
	public List<V> findAll()  ;
	/**
	 * 分页进行数据的模糊查询，查询结果以集合的形式返回
	 * @param map 包含如下数据：<br>
	 * <li> key = column 、value=（要查询的数据列）</li>
	 * <li> key = keyword 、value=（检索关键字）</li>
	 * <li> key = start 、value=（分页起始数据行数）</li>
	 * <li> key = pageSize 、value=（一页所包含的数据条数）</li>
	 */
	public List<V> findSplit(Map<String, Object> map)
			;
	/**
	 * 进行模糊查询数据量的统计，如果表中没有记录统计的结果就是0
	 * @param map 包含如下数据：<br>
	 * <li> key = column 、value=（要查询的数据列）</li>
	 * <li> key = keyword 、value=（检索关键字）</li>
	 * @return 返回表中的数据量，如果没有数据返回0
	 */
	public Integer getCount(Map<String, Object> map)  ;
}
