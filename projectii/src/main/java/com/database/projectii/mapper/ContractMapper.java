package com.database.projectii.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.database.projectii.model.Contract;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ContractMapper extends BaseMapper<Contract> {

    @Select(
        "select contract_manager, contract_manager, enterprise, center " +
            "from empty_contracts where contract_number = #{contractNumber}")
    List<Map<String, Object>> selectContractInfo(@Param("contractNumber") String contractNumber);
}
