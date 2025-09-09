package com.jiawa.train.number.mapper;

import com.jiawa.train.number.domain.Number;
import com.jiawa.train.number.domain.NumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NumberMapper {
    long countByExample(NumberExample example);

    int deleteByExample(NumberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Number record);

    int insertSelective(Number record);

    List<Number> selectByExample(NumberExample example);

    Number selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Number record, @Param("example") NumberExample example);

    int updateByExample(@Param("record") Number record, @Param("example") NumberExample example);

    int updateByPrimaryKeySelective(Number record);

    int updateByPrimaryKey(Number record);
}